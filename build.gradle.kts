@file:Suppress("OPT_IN_USAGE")

plugins {
    id("java")
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.power-assert") version "2.1.0"
}

group = "dev.jtkt"
version = "0.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

java.sourceSets["test"].java {
    srcDir("src/main/kotlin")
}

powerAssert {
    functions = listOf("kotlin.assert", "kotlin.test.assertEquals", "kotlin.test.assertTrue")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}