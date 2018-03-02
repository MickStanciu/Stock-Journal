FROM alpine:3.7



#ENV NGINX_VERSION 1.13.9
#ENV NGINX_CONFIG "--user=nginx --group=nginx --sbin-path=/usr/local/nginx/nginx --conf-path=/usr/local/nginx/nginx.conf --pid-path=/usr/local/nginx/nginx.pid --with-http_ssl_module --with-http_rewrite_module --with-http_proxy_module --with-threads"
#--prefix=/var/lib/nginx --sbin-path=/usr/sbin/nginx --modules-path=/usr/lib/nginx/modules --conf-path=/etc/nginx/nginx.conf --pid-path=/run/nginx/nginx.pid --lock-path=/run/nginx/nginx.lock --http-client-body-temp-path=/var/tmp/nginx/client_body --http-proxy-temp-path=/var/tmp/nginx/proxy --http-fastcgi-temp-path=/var/tmp/nginx/fastcgi --http-uwsgi-temp-path=/var/tmp/nginx/uwsgi --http-scgi-temp-path=/var/tmp/nginx/scgi --with-perl_modules_path=/usr/lib/perl5/vendor_perl --user=nginx --group=nginx --with-threads --with-file-aio --with-http_ssl_module --with-http_v2_module --with-http_realip_module --with-http_addition_module --with-http_xslt_module=dynamic --with-http_image_filter_module=dynamic --with-http_geoip_module=dynamic --with-http_sub_module --with-http_dav_module --with-http_flv_module --with-http_mp4_module --with-http_gunzip_module --with-http_gzip_static_module --with-http_auth_request_module --with-http_random_index_module --with-http_secure_link_module --with-http_degradation_module --with-http_slice_module --with-http_stub_status_module --with-http_perl_module=dynamic --with-mail=dynamic --with-mail_ssl_module --with-stream=dynamic --with-stream_ssl_module --with-stream_realip_module --with-stream_geoip_module=dynamic --with-stream_ssl_preread_module --add-dynamic-module=/home/buildozer/aports/main/nginx/src/ngx_devel_kit-0.3.0 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/ngx_cache_purge-2.4.2 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/echo-nginx-module-0.61 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/ngx-fancyindex-0.4.2 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/headers-more-nginx-module-0.32 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/lua-nginx-module-0.10.10 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/lua-upstream-nginx-module-0.07 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/nchan-1.1.7 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/nginx-http-shibboleth-2.0.1 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/redis2-nginx-module-0.14 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/set-misc-nginx-module-0.31 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/nginx-upload-progress-module-0.9.2 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/nginx-upstream-fair-0.1.3 --add-dynamic-module=/home/buildozer/aports/main/nginx/src/nginx-rtmp-module-1.2.0
#ENV NJS_VERSION   1.13.9.0.1.15-1

RUN apk update
RUN apk upgrade

#NODE
ENV NODE_VERSION 9.7.0
RUN addgroup -g 1000 node
RUN adduser -u 1000 -G node -s /bin/sh -D node
RUN apk add --no-cache libstdc++
RUN apk add --no-cache --virtual .build-deps curl g++ gcc libgcc linux-headers make python
RUN curl -SLO https://nodejs.org/dist/v$NODE_VERSION/node-v$NODE_VERSION.tar.xz
RUN tar -xJf node-v$NODE_VERSION.tar.xz
WORKDIR node-v$NODE_VERSION
RUN pwd
RUN ./configure
RUN make -j$(getconf _NPROCESSORS_ONLN)
RUN make install
WORKDIR /
RUN rm -Rf node-v$NODE_VERSION
RUN rm node-v$NODE_VERSION.tar.xz


#YARN
ENV YARN_VERSION 1.5.1
RUN curl -SLO https://yarnpkg.com/downloads/$YARN_VERSION/yarn-v$YARN_VERSION.tar.gz
RUN mkdir -p /opt/yarn
RUN tar -xzf yarn-v$YARN_VERSION.tar.gz -C /opt/yarn --strip-components=1
RUN ln -s /opt/yarn/bin/yarn /usr/local/bin/yarn
RUN ln -s /opt/yarn/bin/yarn /usr/local/bin/yarnpkg
RUN rm yarn-v$YARN_VERSION.tar.gz


RUN apk del .build-deps

#APP
WORKDIR /usr/app
COPY app/package.json .
RUN yarn install
COPY app/. .


#RUN apk add --update openssl nginx tzdata
#RUN mkdir -p /run/nginx

#RUN apk add --no-cache --virtual .build-deps curl gcc libc-dev make pcre-dev zlib-dev openssl-dev linux-headers libxslt-dev gd-dev geoip-dev

#NGINX
#RUN addgroup -S nginx
#RUN adduser -D -S -h /var/cache/nginx -s /sbin/nologin -G nginx nginx

#RUN curl -fSL https://nginx.org/download/nginx-$NGINX_VERSION.tar.gz -o nginx.tar.gz
#RUN mkdir -p /usr/src
#RUN tar -zxC /usr/src -f nginx.tar.gz

#RUN rm nginx.tar.gz
#RUN cd /usr/src/nginx-$NGINX_VERSION
#RUN ./configure $CONFIG
#RUN make
#RUN make install
#RUN rm -rf /etc/nginx/html/
#RUN mkdir -p /etc/nginx/conf.d/
#RUN cd /
#RUN rm -rf /usr/src/nginx-$NGINX_VERSION;

#CLEAN UP
#RUN apk del .build-deps

#ENTRYPOINT ["/usr/local/nginx/sbin/nginx", "-g", "daemon off;"]
CMD ["yarn", "run" ,"dev"]

#docker build -t alpinetest .
#docker run -it alpinetest sh
#https://github.com/nginxinc/docker-nginx/blob/master/stable/alpine/Dockerfile
#https://hub.docker.com/r/matriphe/alpine-nginx/~/dockerfile/
#https://nodejs.org/en/docs/guides/nodejs-docker-webapp/
