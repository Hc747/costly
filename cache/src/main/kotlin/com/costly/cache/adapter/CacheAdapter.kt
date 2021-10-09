package com.costly.cache.adapter

import com.costly.cache.middleware.CacheMiddleware
import com.github.benmanes.caffeine.cache.*

//https://github.com/ben-manes/caffeine/issues/204
//TODO: ... on write
//TODO: asynchronous version
interface CacheAdapter<K, V> : CacheLoader<K, V>, RemovalListener<K, V> {

    private class DelegatedCacheAdapter<K, V>(
        private val middleware: CacheMiddleware<K, V>,
        private val loader: CacheLoader<K, V>
    ) : CacheAdapter<K, V> {

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

        override fun onRemoval(key: K?, value: V?, cause: RemovalCause) {
            if (key == null || cause != RemovalCause.EXPLICIT) return
            else middleware.remove(key)
        }
    }

    fun augment(caffeine: Caffeine<K, V>, async: Boolean = false): LoadingCache<K, V> {
        val builder = if (async) caffeine.removalListener(this) else caffeine.evictionListener(this)
        return builder.build(this)
    }

    companion object {

        fun <K, V> build(cache: CacheMiddleware<K, V>, loader: CacheLoader<K, V>): CacheAdapter<K, V> {
            return DelegatedCacheAdapter(cache, loader)
        }
    }
}