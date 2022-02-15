val javadocJar = tasks.register("javadocJar", Jar::class.java) {
    archiveClassifier.set("javadoc")
}

group = "io.github.ibims1ckoky"
version = "1.0-SNAPSHOT"

description = "LightController-api"

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("maven-publish")
    id("signing")
}

repositories {
    gradlePluginPortal() // To use 'maven-publish' and 'signing' plugins in our own plugin
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.1")
    implementation("io.reactivex.rxjava3:rxjava:3.1.3")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")

    api("ch.qos.logback:logback-core:1.2.10")
    api("ch.qos.logback:logback-classic:1.2.6")
}

signing {
    useInMemoryPgpKeys(
            System.getenv("GPG_PRIVATE_KEY"),
            System.getenv("GPG_PRIVATE_PASSWORD")
    )
    sign(publishing.publications)
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name.set("LightControllerAPI")
                description.set("API to control lights from PhillipsHUE with java")
                licenses {
                    license {
                        name.set("GPL-3.0")
                        url.set("https://opensource.org/licenses/GPL-3.0")
                    }
                }
                url.set("https://github.com/IBims1ckoky/LightControllerAPI")
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/IBims1ckoky/LightControllerAPI/issues")
                }
                scm {
                    connection.set("https://github.com/IBims1ckoky/LightControllerAPI.git")
                    url.set("https://github.com/IBims1ckoky/LightControllerAPI")
                }
                developers {
                    developer {
                        name.set("Maxi Zink")
                        email.set("business.maxi.zink@gmail.com")
                    }
                }
            }
        }

        repositories {
            maven {
                name = "oss"
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl;
            }
        }
    }
}