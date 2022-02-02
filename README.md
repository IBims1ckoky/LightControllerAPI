# Multi-Module-Gradle-Project

This is a **multi-module** gradle template project.


# Setup

Setup this project by cloning the repository

## Create new modules

You can add new modules in the **settings.gradle.kts**. You only need to add this
```kotlin
include("your-module-name")
project(":your-module-name").name = "YourCustomModuleName"
```
## Add module specific plugins/dependencies/repositories

By splitting a project into multiple modules you are allowed to add module-specific plugins, repositories and dependencies to provide only the information needed for each module. You are also able to create dependencies between two or more modules within the same root project using `implementation(project(":your-module-name"))`. These additions need to be made inside the **build.gradle.kts** (an example could look like this):
```kotlin
plugins {
	
}
repositories {
    
}
dependencies {
	
}
```