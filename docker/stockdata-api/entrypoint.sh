#!/bin/sh
while ! nc -z config-server 8080 ; do
    echo "Waiting for upcoming Config Server"
    sleep 2
done
/usr/lib/jvm/jdk-11/bin/java -jar /app/app.jar
