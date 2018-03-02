FROM alpine:3.7

ENV NGINX_VERSION 1.13.9
RUN export NGINX_CONFIG="--prefix=/etc/nginx --sbin-path=/usr/sbin/nginx --modules-path=/usr/lib/nginx/modules \
--conf-path=/etc/nginx/nginx.conf --error-log-path=/var/log/nginx/error.log --http-log-path=/var/log/nginx/access.log \
--pid-path=/var/run/nginx.pid --lock-path=/var/run/nginx.lock --http-client-body-temp-path=/var/cache/nginx/client_temp \
--http-proxy-temp-path=/var/cache/nginx/proxy_temp --http-fastcgi-temp-path=/var/cache/nginx/fastcgi_temp \
--http-uwsgi-temp-path=/var/cache/nginx/uwsgi_temp --http-scgi-temp-path=/var/cache/nginx/scgi_temp --user=nginx --group=nginx \
--with-http_ssl_module --with-http_realip_module --with-http_addition_module --with-http_sub_module --with-http_dav_module \
--with-http_flv_module --with-http_mp4_module --with-http_gunzip_module --with-http_gzip_static_module --with-http_random_index_module \
--with-http_secure_link_module --with-http_stub_status_module --with-http_auth_request_module --with-http_xslt_module=dynamic \
--with-http_image_filter_module=dynamic --with-http_geoip_module=dynamic --with-threads --with-stream --with-stream_ssl_module \
--with-stream_ssl_preread_module --with-stream_realip_module --with-stream_geoip_module=dynamic --with-http_slice_module \
--with-mail --with-mail_ssl_module --with-compat --with-file-aio --with-http_v2_module"
#ENV NJS_VERSION   1.13.9.0.1.15-1

RUN apk update
RUN apk upgrade
RUN apk add --no-cache --virtual .build-deps curl gnupg gcc libc-dev make pcre-dev zlib-dev openssl-dev linux-headers libxslt-dev gd-dev geoip-dev

RUN addgroup -S nginx
RUN adduser -D -S -h /var/cache/nginx -s /sbin/nologin -G nginx nginx

RUN curl -fSL https://nginx.org/download/nginx-$NGINX_VERSION.tar.gz -o nginx.tar.gz
#RUN curl -fSL https://nginx.org/download/nginx-$NGINX_VERSION.tar.gz.asc  -o nginx.tar.gz.asc


RUN mkdir -p /usr/src
RUN tar -zxC /usr/src -f nginx.tar.gz
RUN rm nginx.tar.gz
RUN cd /usr/src/nginx-$NGINX_VERSION
RUN ./configure $CONFIG
RUN make
RUN make install
RUN rm -rf /etc/nginx/html/
RUN mkdir -p /etc/nginx/conf.d/
RUN mkdir -p /usr/share/nginx/html/
RUN install -m644 html/index.html /usr/share/nginx/html/
RUN install -m644 html/50x.html /usr/share/nginx/html/
RUN ln -s ../../usr/lib/nginx/modules /etc/nginx/modules
RUN cd /
RUN rm -rf /usr/src/nginx-$NGINX_VERSION;

RUN apk del .build-deps

ENTRYPOINT sh

#docker build -t alpinetest .
#docker run -it alpinetest sh
#https://github.com/nginxinc/docker-nginx/blob/master/stable/alpine/Dockerfile
