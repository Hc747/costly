plugins {
    val modules = arrayOf("jvm", "kapt", "plugin.allopen")
    for (module in modules) {
        kotlin(module) apply true version "1.5.30"
    }
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")

    val jdk = project.properties["jdk"]!!
    val language: JavaVersion = JavaVersion.toVersion(jdk)

    val kt = project.properties["org.jetbrains.kotlin.version"]
    val ktx = project.properties["org.jetbrains.kotlinx.version"]
    val l4j = project.properties["org.apache.logging.log4j.version"]
    val jxa = project.properties["javax.annotation.version"]

    repositories {
        mavenCentral()
        google()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }

    dependencies {
        implementation("org.apache.logging.log4j:log4j-bom:$l4j")
        implementation("org.jetbrains.kotlin:kotlin-bom:$kt")
        implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:$ktx"))

        val core = arrayOf("stdlib-jdk8", "reflect")
        for (module in core) {
            api("org.jetbrains.kotlin:kotlin-$module")
        }

        val extensions = arrayOf("coroutines-core", "coroutines-jdk8", "coroutines-jdk9", "coroutines-rx3")
        for (module in extensions) {
            api("org.jetbrains.kotlinx:kotlinx-$module")
        }

        implementation("javax.annotation:javax.annotation-api:$jxa")
        implementation("org.apache.logging.log4j:log4j-core:$l4j")
        runtimeOnly("org.apache.logging.log4j:log4j-api:$l4j")
        runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:$l4j")
    }

    java {
        sourceCompatibility = language
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                jvmTarget = language.toString()
            }
        }

        compileTestKotlin {
            kotlinOptions {
                jvmTarget = language.toString()
            }
        }
    }
}