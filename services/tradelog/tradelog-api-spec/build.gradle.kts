description = "TradeLog API Spec"
group = "com.example.tradelog.api.spec"
version = "0.0.1-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.3.50"
    id ("org.openapi.generator") version "4.1.2"
    checkstyle
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.9.9.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    sourceSets["main"].java {
        srcDirs("src/main/kotlin")
    }
}

openApiGenerate {
    generatorName.set("spring")
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