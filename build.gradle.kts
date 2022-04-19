plugins {
    `java-library`
    id("org.sonarqube") version "3.3"
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
        implementation("org.jetbrains:annotations:23.0.0")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
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