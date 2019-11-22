import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

description = "Experiment"
group = "com.example.experiment"
version = "0.0.1-SNAPSHOT"

plugins {
    kotlin ("jvm")
    id ("com.google.protobuf")
    id ("io.spring.dependency-management")
    id ("org.springframework.boot")
//    id ("application")
}

repositories {
    jcenter()
    mavenCentral()
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
}

val mySqlVersion = "8.0.18"
val junitVersion = "5.4.2"
val jacksonKotlinVersion = "2.10.0"
val protobufVersion = "3.10.0"

dependencies {
    implementation (kotlin("stdlib"))
    implementation ("com.google.protobuf:protobuf-java:$protobufVersion")
    implementation ("com.google.protobuf:protobuf-java-util:$protobufVersion")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-undertow")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("mysql:mysql-connector-java:$mySqlVersion")
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
        java.srcDir("build/generated/source/proto/main/java")
        java.srcDir("src/main/kotlin")
    }
}

//application {
//    mainClassName = "com.example.experiment.Application"
//}

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

protobuf {
    protoc {
        // The artifact spec for the Protobuf Compiler
        artifact = "com.google.protobuf:protoc:3.10.0"
    }
}
