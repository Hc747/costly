package com.costly.service

import com.costly.client.SuspendableHttpClient
import io.micronaut.caffeine.cache.AsyncCacheLoader
import io.micronaut.caffeine.cache.AsyncLoadingCache
import io.micronaut.caffeine.cache.Caffeine
import jakarta.inject.Inject
import java.math.BigDecimal
import java.time.Duration
import kotlinx.coroutines.future.future
import kotlinx.coroutines.reactive.awaitSingle
import java.util.concurrent.CompletableFuture

data class Price(val currency: String, val price: BigDecimal)
data class CryptoCurrency(val exchange: String, val ticker: String, val price: Price?)

interface CryptoCurrencyService {

    suspend fun retrieve(exchange: String, ticker: String): CryptoCurrency?
}

interface CryptoCurrencyRepository {

//    fun retr
}

class CoinSpotCryptoCurrencyService(@Inject val http: SuspendableHttpClient, private val key: String) :
    CryptoCurrencyService {

//    private val cache: AsyncLoadingCache<String, CryptoCurrency> = Caffeine
//        .newBuilder()
//        .expireAfterWrite(Duration.ofSeconds(5L))
//        .buildAsync(AsyncCacheLoader { ticker, executor ->
////            val task = { retrieve(ticker) }
////            return@AsyncCacheLoader CompletableFuture.supplyAsync({ -> future { retrieve("", ticker) }}, executor)
////            return@AsyncCacheLoader http.backend().retrieve(ENDPOINT + "$ticker").
//            http.backend().retrieve(ENDPOINT + "$ticker").firs
//        })

    private companion object {
        const val ENDPOINT = "https://www.coinspot.com.au/pubapi/latest/"
    }

//    private fun retrieve(ticker: String): CryptoCurrency? = future {
//        return@future retrieve("bingas", ticker)
//    }

    override suspend fun retrieve(exchange: String, ticker: String): CryptoCurrency? {

        TODO("Not yet implemented")
    }
}