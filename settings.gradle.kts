rootProject.name = "LightControllerAPI"

include("LightControllerAPI-example")
project(":LightControllerAPI-example").projectDir = file("example")

include("LightControllerAPI-api")
project(":LightControllerAPI-api").projectDir = file("api")
