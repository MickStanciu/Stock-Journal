description = "Product API"
group = "com.example.common"
version = "0.0.1-SNAPSHOT"

plugins {
  java
  kotlin("jvm")
}

repositories {
  jcenter()
  mavenCentral()
}

val junitVersion: String = rootProject.extra["junitVersion"] as String
val arrowVersion: String = rootProject.extra["arrowVersion"] as String

configurations {
  all {
    exclude(group = "junit", module = "junit")
  }
}

dependencies {
  implementation (kotlin("stdlib"))
  implementation (group = "io.arrow-kt", name = "arrow-core", version = arrowVersion)
  testImplementation ("org.junit.jupiter:junit-jupiter:$junitVersion")
}

sourceSets {
  main {
    java.srcDir("src/main/java")
    java.srcDir("src/main/kotlin")
  }
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}

tasks {
  test {
    useJUnitPlatform()
    testLogging.showExceptions = true
    testLogging {
      events = setOf(
              org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
              org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
              org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED)
    }
  }
}