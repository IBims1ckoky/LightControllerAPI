group = "de.maxizink"
version = "1.0-SNAPSHOT"

description = "LightController-api"

plugins {
    `java-library`
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.1")
    implementation("io.reactivex.rxjava3:rxjava:3.1.3")

    api ("ch.qos.logback:logback-core:1.2.6")
    api ("ch.qos.logback:logback-classic:1.2.6")
}