val javadocJar = tasks.register("javadocJar", Jar::class.java) {
    archiveClassifier.set("javadoc")
}

val sonatypeUsername: String? = System.getenv("SONATYPE_USERNAME")
val sonatypePassword: String? = System.getenv("SONATYPE_PASSWORD")

group = "io.github.ibims1ckoky"
version = "1.0-SNAPSHOT"

description = "LightController-api"

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("maven-publish")
    id("signing")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.compileJava {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}

repositories {
    gradlePluginPortal() // To use 'maven-publish' and 'signing' plugins in our own plugin
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4")
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")

    api("ch.qos.logback:logback-core:1.4.1")
    api("ch.qos.logback:logback-classic:1.4.4")
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
        create<MavenPublication>("maven")  {
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

            artifact(tasks["shadowJar"])

            pom.withXml {
                val dependenciesNode = asNode().appendNode("dependencies");
                configurations.filter {
                    it.name == "api" || it.name == "implementation"
                }.forEach { configuration ->
                    configuration.dependencies.forEach { dependency ->
                        if (dependency !is SelfResolvingDependency || dependency is ProjectDependency) {
                            val dependencyNode = dependenciesNode.appendNode("dependency")
                            dependencyNode.appendNode("groupId", dependency.group)
                            dependencyNode.appendNode("artifactId", dependency.name.toLowerCase())
                            dependencyNode.appendNode("version", dependency.version)
                            dependencyNode.appendNode("scope", configuration.name)
                        }
                    }
                }
            }
        }

        repositories {
            maven {
                name = "oss"
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")

                credentials {
                    username = sonatypeUsername
                    password = sonatypePassword
                }

                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl;
            }
        }
    }
}