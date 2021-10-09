package com

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic

/**
 * kafka-streams requires at least one listener
 */
@KafkaListener(groupId = "ExampleListener")
class ExampleListener {
    @Topic("example")
    fun example() {
        println("example")
    }
}