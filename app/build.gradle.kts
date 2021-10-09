plugins {
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("io.micronaut.application") version "2.0.6"
}


micronaut {
    val micronaut: String = project.properties["io.micronaut.version"] as String

    version(micronaut)
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.costly.*")
    }
}

dependencies {
    implementation(project(":cache"))

    implementation(platform("io.micronaut:micronaut-bom:3.0.3"))

    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.security:micronaut-security-annotations")

    //    implementation("io.micronaut.aws:micronaut-aws-sdk-v2")
//    implementation("com.amazonaws:aws-java-sdk-elasticache:1.12.81")
//    implementation("io.micronaut:micronaut-http-client")
//    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-runtime")
//    implementation("io.micronaut.beanvalidation:micronaut-hibernate-validator")
//    implementation("io.micronaut.cache:micronaut-cache-caffeine")
//    implementation("io.micronaut.elasticsearch:micronaut-elasticsearch")
//    implementation("io.micronaut.graphql:micronaut-graphql")
    implementation("io.micronaut.kafka:micronaut-kafka")
    implementation("io.micronaut.kafka:micronaut-kafka-streams")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
//    implementation("io.micronaut.liquibase:micronaut-liquibase")
//    implementation("io.micronaut.micrometer:micronaut-micrometer-core")
//    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-prometheus")
//    implementation("io.micronaut.r2dbc:micronaut-r2dbc-core")
    implementation("io.micronaut.rxjava3:micronaut-rxjava3")
    implementation("io.micronaut.rxjava3:micronaut-rxjava3-http-client")
    implementation("io.micronaut.security:micronaut-security")
    implementation("io.micronaut.security:micronaut-security-jwt")
//    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
//    implementation("io.micronaut.views:micronaut-views-handlebars")
    implementation("io.micronaut:micronaut-validation")


    //    implementation(platform("software.amazon.awssdk:bom:2.15.0"))
//    implementation(platform("io.micronaut:micronaut-bom:3.0.3"))
//    implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.5.2"))
//
//
//    implementation("com.google.code.gson:gson:2.8.8")
//    implementation("redis.clients:jedis:3.7.0")
//    implementation("com.github.ben-manes.caffeine:caffeine:3.0.4")


////    runtimeOnly("dev.miku:r2dbc-mysql")
////    runtimeOnly("mysql:mysql-connector-java")
////    testImplementation("org.testcontainers:junit-jupiter")
////    testImplementation("org.testcontainers:mysql")
////    testImplementation("org.testcontainers:r2dbc")
////    testImplementation("org.testcontainers:testcontainers")
//
//    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}

application {
    mainClass.set("com.costly.ApplicationKt")
}