package com.github.hc747

import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder
import io.micronaut.context.annotation.Factory
import org.apache.kafka.streams.kstream.KStream
import jakarta.inject.Named
import jakarta.inject.Singleton

@Factory
class ExampleFactory {
    @Singleton
    @Named("example")
    fun exampleStream(builder: ConfiguredStreamBuilder): KStream<String?, String?>? {
        return builder.stream("streams-plaintext-input")
    }
}
