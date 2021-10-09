package com.costly.cache.middleware

interface CacheMiddleware<K, V> {
    fun get(key: K): V?

    fun put(key: K, value: V): V?

    fun remove(key: K): V?
}