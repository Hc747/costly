package com.costly.cache.adapter

import com.costly.cache.adapter.middleware.MiddlewareAwareCacheLoader
import com.costly.cache.adapter.middleware.MiddlewareAwareRemovalListener
import com.costly.cache.middleware.CacheMiddleware
import com.github.benmanes.caffeine.cache.*

//https://github.com/ben-manes/caffeine/issues/204
//TODO: ... on write
//TODO: asynchronous version
object CacheAdapter {

    fun <K, V> adapt(
        caffeine: Caffeine<K, V>,
        middleware: CacheMiddleware<K, V>,
        loader: CacheLoader<K, V>
    ): LoadingCache<K, V> {
        val notifier = MiddlewareAwareRemovalListener(middleware)
        val resolver = MiddlewareAwareCacheLoader(middleware, loader)
        return caffeine.removalListener(notifier).evictionListener(notifier).build(resolver)
    }
}