FROM alpine:latest
MAINTAINER 	Mick Stanciu <mick@jadebaboon.com>

ADD https://releases.hashicorp.com/consul/1.0.6/consul_1.0.6_linux_amd64.zip /tmp/consul.zip
RUN cd /bin && unzip /tmp/consul.zip && chmod +x /bin/consul && rm /tmp/consul.zip

EXPOSE 8500/tcp 8600

ENTRYPOINT consul agent --ui --data-dir=/tmp/consul -bind 127.0.0.1 -server -bootstrap
