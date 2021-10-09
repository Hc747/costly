package com

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
    val ctx = build().args(*args).packages("com.costly")
    ctx.start()
}

