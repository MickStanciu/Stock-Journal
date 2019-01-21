#BUILD INSTRUCTIONS
FROM postgres:11.1-alpine

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD secret
ADD 00-create_user_and_db.sql /docker-entrypoint-initdb.d/
ADD 01-create_work_user_and_db.sql /docker-entrypoint-initdb.d/
ADD 02-test.sh /docker-entrypoint-initdb.d/