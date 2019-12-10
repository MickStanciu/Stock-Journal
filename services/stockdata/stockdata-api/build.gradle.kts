description = "StockData API"
group = "com.example.stockdata.api"
version = "0.0.1-SNAPSHOT"

plugins {
    java
    application
    id("io.spring.dependency-management")
    id("org.springframework.boot")
}

repositories {
    jcenter()
    mavenCentral()
}

object Version {
    var flywayDb = "5.2.4"
    var postgreSql = "42.2.5"
    var junit = "5.4.2"
    var immutables = "2.7.5"
    val protobufVersion = "3.10.0"
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
        exclude(group = "junit", module = "junit")
    }
}

dependencies {
    implementation(project(":services:common"))
    implementation(project(":services:stockdata:stockdata-api-spec"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-undertow")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("org.postgresql:postgresql:${Version.postgreSql}")
    implementation ("org.flywaydb:flyway-core:${Version.flywayDb}")

    implementation ("com.google.protobuf:protobuf-java-util:${Version.protobufVersion}")
    implementation ("com.google.code.findbugs:jsr305:3.0.2")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.10.0")
    compile("org.immutables:value-annotations:${Version.immutables}")
    compile("org.immutables:builder:${Version.immutables}")
    annotationProcessor("org.immutables:value:${Version.immutables}")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation ("org.junit.jupiter:junit-jupiter:${Version.junit}")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
}

sourceSets {
    main {
        java.srcDir("src/main/java")
    }
}

tasks {
    test {
        useJUnitPlatform()
        testLogging.showExceptions = true
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClassName = "com.example.stockdata.api.StockDataApi"
}

