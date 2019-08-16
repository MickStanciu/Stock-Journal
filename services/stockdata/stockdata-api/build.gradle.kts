plugins {
    `java-library`
    application
    id("io.spring.dependency-management")
    id("org.springframework.boot")
}

object Version {
    var flywayDb = "5.2.4"
    var postgreSql = "42.2.5"
    var junit = "5.4.2"
}

repositories {
    jcenter()
    mavenCentral()
}

description = "StockData API"
group = "com.example.stockdata.api"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":services:common"))
//    implementation(project(":services:stockdata:stockdata-api-spec"))

    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation ("org.springframework.boot:spring-boot-starter-undertow")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("org.postgresql:postgresql:${Version.postgreSql}")
    implementation ("org.flywaydb:flyway-core:${Version.flywayDb}")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation ("org.junit.jupiter:junit-jupiter:${Version.junit}")
    testImplementation ("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "junit", module = "junit")
    }
}

sourceSets {
    main {
        java.srcDir("src/main/java")
//        output.resourcesDir = "build/classes/java/main"
    }
}

tasks {
    test {
        useJUnitPlatform()
        testLogging.showExceptions = true
//    testLogging {
//        events "PASSED", "FAILED", "SKIPPED"
//    }
    }

//tasks.withType(JavaCompile) {
//    options.encoding = "UTF-8"
//}
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClassName = "com.example.stockdata.api.StockDataApi"
}

