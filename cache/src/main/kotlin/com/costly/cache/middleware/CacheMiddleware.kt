package com.costly.cache.middleware

interface CacheMiddleware<K, V> {

    fun get(key: K): V?

    fun get(keys: Collection<K>): Collection<V?> {
        return keys.map { key -> get(key) }
    }

    fun put(key: K, value: V): V?

    fun put(map: Map<out K, V>) {
        for (entry in map.entries) {
            put(entry.key, entry.value)
        }
    }

    fun remove(key: K): V?

    fun remove(keys: Collection<K>) {
        for (key in keys) {
            remove(key)
        }
    }
}