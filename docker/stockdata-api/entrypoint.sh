#!/bin/sh
while ! nc -z personal-db 54323 ; do
    echo "Waiting for upcoming Database Server"
    sleep 2
done
/usr/lib/jvm/jdk-11/bin/java -jar /app/app.jar
