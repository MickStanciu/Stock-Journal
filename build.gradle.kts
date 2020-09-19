buildscript {
    extra.set("protobufVersion", "3.11.1")
    extra.set("junitVersion", "5.6.2")
    extra.set("jacksonVersion", "2.10.2")
    extra.set("jwtVersion", "0.9.0")
    extra.set("flywayDbVersion", "6.2.0")
    extra.set("postgreSqlVersion", "42.2.9")
    extra.set("jasyptVersion", "3.0.2")
    extra.set("kotlintestVersion", "2.0.7")
    extra.set("arrowVersion", "0.11.0")
    extra.set("isDev", "dev" == System.getProperty("spring_profiles_active"))

    repositories {
        jcenter()
        mavenCentral()
    }
}

plugins {
    kotlin ("jvm") version "1.4.0" apply false
    kotlin ("plugin.spring") version "1.4.0" apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
    id("org.springframework.boot") version "2.3.3.RELEASE" apply false
    id("com.google.protobuf") version "0.8.10" apply false
}

