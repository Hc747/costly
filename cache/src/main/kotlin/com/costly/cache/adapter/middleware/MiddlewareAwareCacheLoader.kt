package com.costly.cache.adapter.middleware

import com.costly.cache.middleware.CacheMiddleware
import com.github.benmanes.caffeine.cache.CacheLoader

class MiddlewareAwareCacheLoader<K, V>(
    private val middleware: CacheMiddleware<K, V>,
    private val loader: CacheLoader<K, V>
) : CacheLoader<K, V> {

    override fun load(key: K): V? {
        var value = middleware.get(key)
        if (value == null) {
            value = loader.load(key)
            if (value != null) {
                middleware.put(key, value)
            }
        }
        return value
    }

    override fun loadAll(keys: Set<K>): Map<K, V> {
        val results = HashMap<K, V>()
        val ordered = keys.toList()
        val hits = middleware.get(ordered)
        val pending = HashSet<K>()

        for ((key, value) in ordered.zip(hits)) {
            if (value == null)
                pending.add(key)
            else
                results[key] = value
        }

        if (pending.isEmpty()) {
            return results
        }

        val lookups = bulk(pending)

        if (lookups.isNotEmpty()) {
            middleware.put(lookups)
            results.putAll(lookups)
        }

        return results
    }

    private fun bulk(keys: Set<K>): Map<out K, V> = try {
        fastpath(keys)
    } catch (e: UnsupportedOperationException) {
        slowpath(keys)
    }

    private fun fastpath(keys: Set<K>): Map<out K, V> = loader.loadAll(keys)

    private fun slowpath(keys: Set<K>): Map<K, V> {
        val results = HashMap<K, V>()
        for (key in keys) {
            val value = loader.load(key) ?: continue
            results[key] = value
        }
        return results
    }
}