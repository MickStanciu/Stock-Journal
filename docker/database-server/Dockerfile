#BUILD INSTRUCTIONS
# docker build -t postgresql-experiment .
FROM postgres:11.1

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD secret
ADD create_user_and_db.sql /docker-entrypoint-initdb.d/
ADD create_work_user_and_db.sql /docker-entrypoint-initdb.d/