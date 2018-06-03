#!/bin/sh
mvn package -DskipTests=true
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8784 -jar ./target/core-hollow-swarm.jar ./target/core.war -s src/main/resources/project-defaults.yml
#mvn wildfly-swarm:run -DskipTests=true -Dswarm.debug.port=8784
