plugins {
    kotlin ("jvm") version "1.3.50" apply false
    kotlin ("plugin.spring") version "1.3.50" apply false
    id ("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
    id ("org.springframework.boot") version "2.2.4.RELEASE" apply false
    id ("com.google.protobuf") version "0.8.10" apply false
}

buildscript {
    extra.set("protobufVersion", "3.11.1")
    extra.set("junitVersion", "5.6.0")
    extra.set("jacksonVersion", "2.10.2")
    extra.set("jwtVersion", "0.9.0")
    extra.set("flywayDbVersion", "6.2.0")
    extra.set("postgreSqlVersion", "42.2.9")
}
