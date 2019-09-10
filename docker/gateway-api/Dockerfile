FROM debian:stretch-slim

# prepare linux box
RUN apt-get update
RUN apt-get install -y wget

# install openjdk-11 x64
RUN wget https://download.java.net/openjdk/jdk11/ri/openjdk-11+28_linux-x64_bin.tar.gz -O /tmp/openjdk-11+28_linux-x64_bin.tar.gz
RUN mkdir /usr/lib/jvm
RUN tar xfvz /tmp/openjdk-11+28_linux-x64_bin.tar.gz --directory /usr/lib/jvm
RUN rm -f /tmp/openjdk-11+28_linux-x64_bin.tar.gz

# prepare java env
ENV JAVA_HOME="/usr/lib/jvm/jdk-11/bin"

# copy java app
VOLUME /tmp
COPY ./services/gateway/gateway-api/build/libs/gateway-api-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["/usr/lib/jvm/jdk-11/bin/java","-jar","/app/app.jar"]
