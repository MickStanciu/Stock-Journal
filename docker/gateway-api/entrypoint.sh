#!/bin/sh
while ! nc -z tradelog-api 8080 ; do
    echo "Waiting for upcoming TradeLog Server"
    sleep 5
done
/usr/lib/jvm/jdk-11/bin/java -jar /app/app.jar
