group = "io.github.ibims1ckoky"
version = "1.0-SNAPSHOT"

description = "LightController-example"

plugins {
    `java-library`
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    api(project(":LightControllerAPI-api"))
    //implementation("com.github.IBims1ckoky:LightControllerAPI:master-SNAPSHOT");
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.compileJava {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}
