package com.costly.cache.codec

interface CacheCodec<T, F> {
    fun encode(value: F): T

    fun decode(value: T): F
}