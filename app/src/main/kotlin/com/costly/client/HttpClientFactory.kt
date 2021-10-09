package com.costly.client

import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Factory
import io.micronaut.rxjava3.http.client.Rx3HttpClient
import jakarta.inject.Inject

@Factory
class HttpClientFactory(@Inject private val http: Rx3HttpClient) {

    @Context
    fun client(): SuspendableHttpClient = SuspendableHttpClient(http)
}