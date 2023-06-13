plugins {
    `java-library`
    id("org.sonarqube") version "4.2.1.3168"
}

group = "io.github.ibims1ckoky"
version = "1.0-SNAPSHOT"

subprojects {

    apply(plugin="java")

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
        implementation("org.jetbrains:annotations:24.0.1")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "IBims1ckoky_LightControllerAPI")
        property("sonar.organization", "ibims1ckoky")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}