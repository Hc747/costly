package com.costly.client

import io.micronaut.core.io.buffer.ByteBuffer
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.rxjava3.http.client.Rx3HttpClient
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.reactive.awaitSingle

class SuspendableHttpClient(private val http: Rx3HttpClient) : AutoCloseable {

    suspend fun <I, O, E> exchange(request: HttpRequest<I>, body: Argument<O>, error: Argument<E>): HttpResponse<O> {
        return await(http.exchange(request, body, error))
    }

    suspend fun <I, O, E> retrieve(request: HttpRequest<I>, body: Argument<O>, error: Argument<E>): O {
        return await(http.retrieve(request, body, error))
    }

    suspend fun <I> exchange(request: HttpRequest<I>): HttpResponse<ByteBuffer<*>> {
        return await(http.exchange(request))
    }

    suspend fun exchange(uri: String): HttpResponse<ByteBuffer<*>> {
        return await(http.exchange(uri))
    }

    suspend fun <O> exchange(uri: String, body: Class<O>): HttpResponse<O> {
        return await(http.exchange(uri, body))
    }

    suspend fun <I, O> exchange(request: HttpRequest<I>, body: Class<O>): HttpResponse<O> {
        return await(http.exchange(request, body))
    }

    suspend fun <I, O> retrieve(request: HttpRequest<I>, body: Argument<O>): O {
        return await(http.retrieve(request, body))
    }

    suspend fun <I, O> retrieve(request: HttpRequest<I>, body: Class<O>): O {
        return await(http.retrieve(request, Argument.of(body)))
    }

    suspend fun <I> retrieve(request: HttpRequest<I>): String {
        return await(http.retrieve(request))
    }

    suspend fun retrieve(uri: String): String {
        return await(http.retrieve(uri))
    }

    override fun close() = http.close()

    fun backend(): Rx3HttpClient = http

    private suspend fun <T> await(flow: Flowable<T>): T = flow.awaitSingle()
}