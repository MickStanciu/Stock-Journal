FROM alpine:3.7

ENV NGINX_VERSION 1.13.9
ENV NGINX_CONFIG "--user=nginx --group=nginx --sbin-path=/usr/local/nginx/nginx --conf-path=/usr/local/nginx/nginx.conf --pid-path=/usr/local/nginx/nginx.pid --with-http_ssl_module --with-http_rewrite_module --with-http_proxy_module --with-threads"
#ENV NJS_VERSION   1.13.9.0.1.15-1

RUN apk update
RUN apk upgrade
RUN apk add --no-cache --virtual .build-deps curl gcc libc-dev make pcre-dev zlib-dev openssl-dev linux-headers libxslt-dev gd-dev geoip-dev

#NGINX
RUN addgroup -S nginx
RUN adduser -D -S -h /var/cache/nginx -s /sbin/nologin -G nginx nginx

RUN curl -fSL https://nginx.org/download/nginx-$NGINX_VERSION.tar.gz -o nginx.tar.gz
RUN mkdir -p /usr/src
RUN tar -zxC /usr/src -f nginx.tar.gz

RUN rm nginx.tar.gz
RUN cd /usr/src/nginx-$NGINX_VERSION
RUN ./configure $CONFIG
RUN make
RUN make install
RUN rm -rf /etc/nginx/html/
RUN mkdir -p /etc/nginx/conf.d/
RUN cd /
RUN rm -rf /usr/src/nginx-$NGINX_VERSION;

#CLEAN UP
RUN apk del .build-deps

ENTRYPOINT sh

#docker build -t alpinetest .
#docker run -it alpinetest sh
#https://github.com/nginxinc/docker-nginx/blob/master/stable/alpine/Dockerfile
