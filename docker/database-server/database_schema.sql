-- init extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- discrimination table
DROP TABLE IF EXISTS tenants;
CREATE TABLE tenants (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  name VARCHAR(64)
);

INSERT INTO tenants (name) VALUES ('DEMO');

GRANT ALL PRIVILEGES ON TABLE tenants TO admin;

-- user accounts table
DROP SEQUENCE IF EXISTS accounts_seq;
CREATE SEQUENCE accounts_seq;
GRANT ALL PRIVILEGES ON SEQUENCE accounts_seq TO admin;

DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
  id BIGINT PRIMARY KEY DEFAULT nextval('accounts_seq'),
  tenant_fk UUID REFERENCES tenants(id),
  name VARCHAR(64),
  email VARCHAR(64),
  password VARCHAR(64)
  -- missing roles
);

;
INSERT INTO accounts (tenant_fk, name, email, password) VALUES
  ((SELECT id from tenants LIMIT 1), 'mircea.stanciu', 'mick@jadebaboon.com', 'secret');

GRANT ALL PRIVILEGES ON TABLE accounts TO admin;

