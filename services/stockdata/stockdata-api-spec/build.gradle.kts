import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

description = "StockData API Spec"
group = "com.example.stockdata.api.spec"
version = "0.0.1-SNAPSHOT"

plugins {
    java
    id ("com.google.protobuf")
}

repositories {
    jcenter()
    mavenCentral()
}

val protobufVersion = "3.10.0"

dependencies {
    implementation ("com.google.protobuf:protobuf-java:$protobufVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    main {
        java.srcDir("src/main/java")
        java.srcDir("build/generated/source/proto/main/java")
        java.srcDir("src/main/kotlin")
    }
}

protobuf {
    protoc {
        // The artifact spec for the Protobuf Compiler
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }
}
