#BUILD INSTRUCTIONS
FROM postgres:11.1-alpine

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD secret
ADD 00-create_user_and_db.sql /docker-entrypoint-initdb.d/
ADD 99-test.sh /docker-entrypoint-initdb.d/

EXPOSE 8432

#CONFIGURE BACKUPS
ADD backup.sh /etc/periodic/daily/