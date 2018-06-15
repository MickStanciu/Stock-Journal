-- clean up
DROP DATABASE IF EXISTS tenant;

-- create db
CREATE DATABASE tenant;
GRANT ALL PRIVILEGES ON DATABASE tenant TO admin;

-- init extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";