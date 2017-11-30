-- init extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- discrimination table
DROP SEQUENCE IF EXISTS tenants_seq;
CREATE SEQUENCE tenants_seq;
GRANT ALL PRIVILEGES ON SEQUENCE tenants_seq TO admin;

DROP TABLE IF EXISTS tenants;
CREATE TABLE tenants (
  id BIGINT PRIMARY KEY DEFAULT nextval('tenants_seq'),
  name VARCHAR(64)
);

INSERT INTO tenants (name) VALUES ('DEMO');

GRANT ALL PRIVILEGES ON TABLE tenants TO admin;

-- user accounts table
DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  tenant_fk BIGINT REFERENCES tenants(id),
  name VARCHAR(64),
  email VARCHAR(64),
  password VARCHAR(64)
  -- missing roles
);

INSERT INTO accounts (tenant_fk, name, email, password) VALUES
  (1, 'mircea.stanciu', 'mick@jadebaboon.com', 'secret');

GRANT ALL PRIVILEGES ON TABLE accounts TO admin;

