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

    override fun loadAll(keys: Set<K>): Map<out K, V> {
        //TODO: determine if this is the desired behaviour
        if (!middleware.isBulkOptimised()) return super.loadAll(keys)

        val results = HashMap<K, V>()
        val ordered = keys.toList() // convert to list as results are ordinal, therefore, iteration order has to be deterministic and consistent
        val hits = middleware.get(ordered)
        val pending = HashSet<K>()

        for ((key, value) in ordered.zip(hits)) {
            if (value == null)
                pending.add(key)
            else
                results[key] = value
        }

        if (pending.isEmpty()) return results
        val loaded = load(pending)
        if (loaded.isNotEmpty()) {
            middleware.put(loaded)
            results.putAll(loaded)
        }

        return results
    }

    private fun load(keys: Set<K>): Map<out K, V> = try {
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