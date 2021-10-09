package com.costly.annotation

enum class AccessLevel {
    PRIVATE,
    PUBLIC
}

@Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(AnnotationTarget.TYPE)
annotation class Data(val value: AccessLevel = AccessLevel.PRIVATE)
