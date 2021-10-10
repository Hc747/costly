package com.costly.cache.adapter.middleware

import com.costly.cache.middleware.CacheMiddleware
import com.github.benmanes.caffeine.cache.RemovalCause
import com.github.benmanes.caffeine.cache.RemovalListener

class MiddlewareAwareRemovalListener<K, V>(
    private val middleware: CacheMiddleware<K, V>
) : RemovalListener<K, V> {

    override fun onRemoval(key: K?, value: V?, cause: RemovalCause) {
        if (key == null || cause != RemovalCause.EXPLICIT) return
        else middleware.remove(key)
    }
}