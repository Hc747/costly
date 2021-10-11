package com.costly.cache.middleware

import com.costly.cache.codec.CacheCodec
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.params.SetParams

abstract class JedisMiddleware<K, V>(
    private val keys: CacheCodec<String, K>,
    private val values: CacheCodec<String, V>
) : CacheMiddleware<K, V> {

    private class Unpooled<K, V>(
        private val jedis: Jedis,
        keys: CacheCodec<String, K>,
        values: CacheCodec<String, V>
    ) : JedisMiddleware<K, V>(keys, values) {
        override fun <T> client(action: (Jedis) -> T): T = action(jedis)
    }

    private class Pooled<K, V>(
        private val pool: JedisPool,
        keys: CacheCodec<String, K>,
        values: CacheCodec<String, V>
    ) : JedisMiddleware<K, V>(keys, values) {
        override fun <T> client(action: (Jedis) -> T): T = pool.resource.use(action)
    }

    protected abstract fun <T> client(action: (Jedis) -> T): T

    override fun get(key: K): V? {
        val input = key.toKey()
        val output = client { jedis -> jedis.get(input) }
        return value(output)
    }

    override fun get(keys: Collection<K>): Collection<V?> {
        if (keys.isEmpty()) return emptyList()
        val inputs = keys.map { key -> key.toKey() }.toTypedArray()
        val outputs = client { jedis -> jedis.mget(*inputs) }
        return outputs.map(this::value)
    }

    override fun put(key: K, value: V): V? {
        val params = SetParams().get()
        val input = key.toKey() to value.toValue()
        val output = client { jedis -> jedis.set(input.first, input.second, params) }
        return value(output)
    }

    override fun put(map: Map<out K, V>) {
        if (map.isEmpty()) return
        val inputs = ArrayList<String>(map.size * 2)
        for (entry in map.entries) {
            inputs.add(entry.key.toKey())
            inputs.add(entry.value.toValue())
        }
        client { jedis -> jedis.mset(*inputs.toTypedArray()) }
    }

    override fun remove(key: K): V? {
        val input = key.toKey()
        val output = client { jedis -> jedis.getDel(input) }
        return value(output)
    }

    override fun remove(keys: Collection<K>) {
        if (keys.isEmpty()) return
        val inputs = keys.map { key -> key.toKey() }.toTypedArray()
        client { jedis -> jedis.del(*inputs) }
    }

    override fun isBulkOptimised(): Boolean = true

    private fun K.toKey(): String = keys.encode(this)

    private fun key(key: String?): K? = if (key.isNullOrEmpty()) null else keys.decode(key)

    private fun V.toValue(): String = values.encode(this)

    private fun value(value: String?): V? = if (value.isNullOrEmpty()) null else values.decode(value)

    companion object {

        fun<K, V> cache(jedis: Jedis, keys: CacheCodec<String, K>, values: CacheCodec<String, V>): JedisMiddleware<K, V> = Unpooled(jedis, keys, values)

        fun<K, V> cache(pool: JedisPool, keys: CacheCodec<String, K>, values: CacheCodec<String, V>): JedisMiddleware<K, V> = Pooled(pool, keys, values)
    }
}