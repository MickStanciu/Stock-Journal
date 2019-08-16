object Versions {
    const val jacksonDatabind = "2.9.9.2"
}

plugins {
    java
}

description = "Account API Spec"

group = "com.example.tradelog.api.spec"
version = "0.0.1-SNAPSHOT"

dependencies {
    "implementation"("com.fasterxml.jackson.core:jackson-databind:${Versions.jacksonDatabind}")
}

repositories {
    jcenter()
    mavenCentral()
}

