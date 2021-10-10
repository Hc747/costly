package com

import com.costly.cache.adapter.CacheAdapter
import com.costly.cache.middleware.CacheMiddleware
import com.costly.client.SuspendableHttpClient
import com.github.benmanes.caffeine.cache.CacheLoader
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject
import java.time.Duration

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/")
class ExampleController(@Inject val http: SuspendableHttpClient) {

//    private val cache: AsyncLoadingCache<String, String> = Caffeine
//        .newBuilder()
//        .expireAfterWrite(Duration.ofSeconds(5L))
//        .buildAsync { ticker, executor ->
//            println("${System.currentTimeMillis()} - Caching $ticker")
//            CompletableFuture.supplyAsync({ fetch("CoinBase", ticker) }, executor)
//        }

    private val cache = store(http)

    private fun fetch(@PathVariable exchange: String, @PathVariable ticker: String): String {
        return http.backend().retrieve(ENDPOINT + ticker).blockingFirst()
    }

    @Get("/cryptocurrency/{exchange}/{ticker}")
    fun cryptocurrency(@PathVariable exchange: String, @PathVariable ticker: String) = cache.get(ticker)

    companion object {

        const val ENDPOINT = "https://www.coinspot.com.au/pubapi/latest/"


        fun store(http: SuspendableHttpClient): LoadingCache<String, String> {
            val middleware: CacheMiddleware<String, String> = object : CacheMiddleware<String, String> {

                var store: Pair<String, String>? = null

                override fun get(key: String): String? {
                    val record = store
                    return if (record?.first == key) record.second else null
                }

                override fun put(key: String, value: String): String? {
                    val record = store
                    store = key to value
                    return record?.second
                }

                override fun remove(key: String): String? {
                    val record = store
                    store = null
                    return record?.second
                }
            }

            val loader: CacheLoader<String, String> = CacheLoader<String, String> { key -> http.backend().retrieve(ENDPOINT + key).blockingFirst() }

            val builder: Caffeine<String, String> = Caffeine.newBuilder().expireAfterWrite(Duration.ofSeconds(5L)) as Caffeine<String, String>

            return CacheAdapter.build(middleware, loader).adapt(builder)
        }
    }


}