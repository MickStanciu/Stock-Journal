description = "TradeLog API Spec"
group = "com.example.tradelog.api.spec"
version = "0.0.1-SNAPSHOT"

plugins {
    java
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.9.9.2")
    compile("org.immutables:value-annotations:2.7.5")
    compile("org.immutables:builder:2.7.5")
    annotationProcessor("org.immutables:value:2.7.5")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
