-- user
drop USER if EXISTS admin;
create user admin with ENCRYPTED password 'secret';

-- database
CREATE DATABASE cm;
grant all privileges on database cm to admin;