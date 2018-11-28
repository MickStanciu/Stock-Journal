-- user
DROP USER IF EXISTS admin;
CREATE USER admin WITH ENCRYPTED PASSWORD 'secret';

-- database
CREATE DATABASE account;
GRANT ALL PRIVILEGES ON DATABASE account TO admin;

CREATE DATABASE tenant;
GRANT ALL PRIVILEGES ON DATABASE tenant TO admin;

CREATE DATABASE timesheet;
GRANT ALL PRIVILEGES ON DATABASE timesheet TO admin;

-- CREATE DATABASE customer;
-- GRANT ALL PRIVILEGES ON DATABASE customer TO admin;
