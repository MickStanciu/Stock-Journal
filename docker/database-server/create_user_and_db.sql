-- user
drop USER if EXISTS admin;
create user admin with ENCRYPTED password 'secret';

-- database
CREATE DATABASE toystore;
grant all privileges on database toystore to admin;