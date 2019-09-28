//plugins {
//    id "java"
//    id "org.openapi.generator" version "4.1.2"
//}
//
//description = "TradeLog API Spec"
//group = "com.example.tradelog.api.spec"
//version = "0.0.1-SNAPSHOT"
//
//sourceCompatibility = 11
//targetCompatibility = 11
//tasks.withType(JavaCompile) {
//    options.encoding = "UTF-8"
//}
//
//dependencies {
//    implementation ("com.fasterxml.jackson.core:jackson-databind:2.9.9.2")
//    compile("org.immutables:value-annotations:2.7.5")
//    compile("org.immutables:builder:2.7.5")
//    annotationProcessor("org.immutables:value:2.7.5")
//}
//
//tasks.openApiGenerate {
//    generatorName = "spring" as Property<String>
//    inputSpec = "$projectDir/openapi.yaml" as Property<String>
//    apiPackage = "com.example.tradelog.api.spec.gen.api" as Property<String>
//    modelPackage = "com.example.tradelog.api.spec.gen.model" as Property<String>
//    outputDir = "$buildDir/generated" as Property<String>
//}
//
//repositories {
//    jcenter()
//    mavenCentral()
//}

description = "TradeLog API Spec"
group = "com.example.tradelog.api.spec"
version = "0.0.1-SNAPSHOT"

plugins {
    java
    id ("org.openapi.generator") version "4.1.2"
    checkstyle
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

//tasks.openApiGenerate {
//    generatorName = "spring" as Property<String>
//    inputSpec = "$projectDir/openapi.yaml" as Property<String>
//    apiPackage = "com.example.tradelog.api.spec.gen.api" as Property<String>
//    modelPackage = "com.example.tradelog.api.spec.gen.model" as Property<String>
//    outputDir = "$buildDir/generated" as Property<String>
//}
//
//configure<org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension> {
//    this.generatorName = "spring" as Property<String>
//}

configure<CheckstyleExtension> {
    configFile = file("${project.rootDir}/config/checkstyle/google_checks.xml")
    isIgnoreFailures = true
}