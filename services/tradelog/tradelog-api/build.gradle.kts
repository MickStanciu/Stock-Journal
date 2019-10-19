import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

description = "TradeLog API"
group = "com.example.tradelog.api"
version = "0.0.1-SNAPSHOT"

plugins {
    kotlin ("jvm")
//    kotlin ("plugin.spring") version "1.3.50"
    id ("io.spring.dependency-management")
    id ("org.springframework.boot")
    id ("application")
}

repositories {
    jcenter()
    mavenCentral()
}

val flywayDbVersion = "5.2.4"
val postgreSqlVersion = "42.2.5"
val junitVersion = "5.4.2"
val jacksonKotlinVersion = "2.10.0"

dependencies {
    implementation (kotlin("stdlib"))
    implementation (project(":services:common"))
    implementation (project(":services:tradelog:tradelog-api-spec"))

    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot:spring-boot-starter-tomcat")
    }
    implementation ("org.springframework.boot:spring-boot-starter-undertow")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("org.postgresql:postgresql:$postgreSqlVersion")
    implementation ("org.flywaydb:flyway-core:$flywayDbVersion")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonKotlinVersion")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation ("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation ("org.springframework.boot:spring-boot-starter-test") {
        exclude("junit:junit")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
}

sourceSets {
    main {
        java.srcDir("src/main/java")
        java.srcDir("src/main/kotlin")
    }
}

application {
    mainClassName = "com.example.tradelog.api.TradeLogApi"
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
}
