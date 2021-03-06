rootProject.name = "product-api"

include (":services:common")

include (":services:account:account-api-spec")
include (":services:account:account-api")

include (":services:tradelog:tradelog-api-spec")
include (":services:tradelog:tradelog-api")

include (":services:stockdata:stockdata-api-spec")
include (":services:stockdata:stockdata-api")

include (":services:gateway:gateway-api-spec")
include (":services:gateway:gateway-api")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}