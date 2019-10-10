description = "Gateway API Spec"
group = "com.example.gateway.api.spec"
version = "0.0.1-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.3.50"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    sourceSets["main"].java {
        srcDirs("src/main/kotlin")
    }
}

