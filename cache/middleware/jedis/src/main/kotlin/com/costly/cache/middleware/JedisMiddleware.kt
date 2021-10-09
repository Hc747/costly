package com.costly.cache.middleware

import com.costly.cache.codec.CacheCodec
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

abstract class JedisMiddleware<K, V>(
    private val keys: CacheCodec<String, K>,
    private val values: CacheCodec<String, V>
) : CacheMiddleware<K, V> {

    private class Singleton<K, V>(private val jedis: Jedis, keys: CacheCodec<String, K>, values: CacheCodec<String, V>) : JedisMiddleware<K, V>(keys, values) {
        override fun <T> client(action: (Jedis) -> T): T = action(jedis)
    }

    private class Pool<K, V>(private val pool: JedisPool, keys: CacheCodec<String, K>, values: CacheCodec<String, V>) : JedisMiddleware<K, V>(keys, values) {
        override fun <T> client(action: (Jedis) -> T): T = pool.resource.use(action)
    }

    protected abstract fun <T> client(action: (Jedis) -> T): T

    override fun get(key: K): V? {
        return client { jedis ->
            val value = jedis.get(key.toKey()) ?: return@client null
            return@client values.decode(value)
        }
    }

    override fun put(key: K, value: V): V? {
        return client { jedis ->
            jedis.set(key.toKey(), value.toValue())
            return@client null
        }
    }

    override fun remove(key: K): V? {
        return client { jedis ->
            jedis.del(key.toKey())
            return@client null
        }
    }

    private fun K.toKey(): String {
        return keys.encode(this)
    }

    private fun V.toValue(): String {
        return values.encode(this)
    }

    companion object {

        fun<K, V> cache(jedis: Jedis, keys: CacheCodec<String, K>, values: CacheCodec<String, V>): JedisMiddleware<K, V> {
            return Singleton(jedis, keys, values)
        }

        fun<K, V> cache(pool: JedisPool, keys: CacheCodec<String, K>, values: CacheCodec<String, V>): JedisMiddleware<K, V> {
            return Pool(pool, keys, values)
        }
    }
}