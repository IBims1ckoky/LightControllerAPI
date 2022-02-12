plugins {
    `java-library`
    id("org.sonarqube") version "3.3"
}

group = "de.maxizink"
version = "1.0-SNAPSHOT"

subprojects {

    apply(plugin="java")

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.22")
        annotationProcessor("org.projectlombok:lombok:1.18.20")
        implementation("org.jetbrains:annotations:16.0.2")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
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