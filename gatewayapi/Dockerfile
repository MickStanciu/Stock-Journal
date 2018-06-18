FROM openjdk:8-jdk-alpine
COPY target/gatewayapi.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar","/app.jar"]