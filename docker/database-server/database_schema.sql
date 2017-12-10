-- init extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- discrimination table Tenants
DROP TABLE IF EXISTS tenants;
CREATE TABLE tenants (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  name VARCHAR(64)
);

INSERT INTO tenants (name) VALUES ('DEMO');

GRANT ALL PRIVILEGES ON TABLE tenants TO admin;

-- user roles table
DROP TABLE IF EXISTS account_roles;
CREATE TABLE account_roles (
  id INT PRIMARY KEY,
  name VARCHAR(10)
);
GRANT ALL PRIVILEGES ON TABLE account_roles TO admin;
INSERT INTO account_roles (id, name) VALUES (1, 'L1'), (2, 'L2'), (3, 'L3'), (4, 'L4'), (5, 'L5');

-- account role info
DROP TABLE IF EXISTS account_role_info;
CREATE TABLE account_role_info (
  tenant_fk UUID REFERENCES tenants(id),
  role_fk INT REFERENCES account_roles(id),
  PRIMARY KEY(tenant_fk, role_fk),
  name VARCHAR(64)
);
GRANT ALL PRIVILEGES ON TABLE account_role_info TO admin;

-- user accounts table
DROP SEQUENCE IF EXISTS accounts_seq;
CREATE SEQUENCE accounts_seq;
GRANT ALL PRIVILEGES ON SEQUENCE accounts_seq TO admin;

DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
  id BIGINT PRIMARY KEY DEFAULT nextval('accounts_seq'),
  tenant_fk UUID REFERENCES tenants(id),
  role_fk INT REFERENCES account_roles(id),
  name VARCHAR(64),
  email VARCHAR(64),
  password VARCHAR(64)
);


INSERT INTO accounts (tenant_fk, name, email, password) VALUES
  ((SELECT id from tenants LIMIT 1), 'mircea.stanciu', 'mick@jadebaboon.com', 'secret');

GRANT ALL PRIVILEGES ON TABLE accounts TO admin;

