group = "de.maxizink"
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
