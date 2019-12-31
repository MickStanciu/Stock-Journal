import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

description = "TradeLog API"
group = "com.example.tradelog.api"
version = "0.0.1-SNAPSHOT"

plugins {
    kotlin ("jvm")
    kotlin ("plugin.spring")
    application
    id ("io.spring.dependency-management")
    id ("org.springframework.boot")
}

repositories {
    jcenter()
    mavenCentral()
}

object Version {
    val flywayDb = "5.2.4"
    val postgreSql = "42.2.5"
    val junit = "5.4.2"
    val jackson = "2.10.0"
    val protobuf = "3.10.0"
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
        exclude(group = "junit", module = "junit")
    }
}

dependencies {
    implementation (kotlin("stdlib"))
    implementation (project(":services:common"))
    implementation (project(":services:tradelog:tradelog-api-spec"))

    implementation("org.springframework.boot:spring-boot-starter-web");
    implementation ("org.springframework.boot:spring-boot-starter-undertow")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("org.postgresql:postgresql:${Version.postgreSql}")
    implementation ("org.flywaydb:flyway-core:${Version.flywayDb}")

    implementation ("com.google.protobuf:protobuf-java-util:${Version.protobuf}")
    implementation ("com.google.code.findbugs:jsr305:3.0.2")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:${Version.jackson}")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.10.0")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation ("org.junit.jupiter:junit-jupiter:${Version.junit}")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
}

sourceSets {
    main {
        java.srcDir("src/main/java")
        java.srcDir("src/main/kotlin")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events = setOf(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.SKIPPED)
    }
    testLogging {
        events = setOf(
                org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED)
    }
}

application {
    mainClassName = "com.example.tradelog.api.TradeLogApi"
}
