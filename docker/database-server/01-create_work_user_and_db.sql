-- user
CREATE USER dev WITH ENCRYPTED PASSWORD 'dev';

-- database
CREATE DATABASE int_partner_service;
GRANT ALL PRIVILEGES ON DATABASE int_partner_service TO dev;

