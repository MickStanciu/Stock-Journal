-- user
drop USER if EXISTS admin;
create user admin with ENCRYPTED password 'secret';

-- database
CREATE DATABASE account;
grant all privileges on database account to admin;

CREATE DATABASE tenant;
grant all privileges on database tenant to admin;