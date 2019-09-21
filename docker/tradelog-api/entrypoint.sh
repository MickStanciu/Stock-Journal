#!/bin/sh
while ! nc -z personal-db 5432 ; do
    echo "Waiting for upcoming Database Server"
    sleep 5
done
/usr/lib/jvm/jdk-11/bin/java -jar /app/app.jar
