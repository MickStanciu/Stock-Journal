-- init extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- clean up
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS account_role_info;
DROP TABLE IF EXISTS account_roles;
DROP SEQUENCE IF EXISTS accounts_seq;
DROP TABLE IF EXISTS tenants;

-- discrimination table Tenants
CREATE TABLE tenants (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  name VARCHAR(64) NOT NULL
);

INSERT INTO tenants (id, name) VALUES ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'DEMO');
GRANT ALL PRIVILEGES ON TABLE tenants TO admin;

-- user roles table
CREATE TABLE account_roles (
  id INT PRIMARY KEY,
  name VARCHAR(10) NOT NULL
);
GRANT ALL PRIVILEGES ON TABLE account_roles TO admin;
INSERT INTO account_roles (id, name) VALUES (1, 'L1'), (2, 'L2'), (3, 'L3'), (4, 'L4'), (5, 'L5');

-- account role info
CREATE TABLE account_role_info (
  tenant_fk UUID REFERENCES tenants(id) NOT NULL,
  role_fk INT REFERENCES account_roles(id) NOT NULL,
  PRIMARY KEY(tenant_fk, role_fk),
  name VARCHAR(64)
);

INSERT INTO account_role_info (tenant_fk, role_fk, name) VALUES
  ((SELECT id from tenants LIMIT 1), 1, 'Administrator'),
  ((SELECT id from tenants LIMIT 1), 2, 'Supervisor'),
  ((SELECT id from tenants LIMIT 1), 3, 'Team leader'),
  ((SELECT id from tenants LIMIT 1), 4, 'User Level 2'),
  ((SELECT id from tenants LIMIT 1), 5, 'User Level 1');

GRANT ALL PRIVILEGES ON TABLE account_role_info TO admin;

-- user accounts table
CREATE SEQUENCE accounts_seq;
GRANT ALL PRIVILEGES ON SEQUENCE accounts_seq TO admin;


CREATE TABLE accounts (
  id BIGINT PRIMARY KEY DEFAULT nextval('accounts_seq'),
  tenant_fk UUID REFERENCES tenants(id) NOT NULL,
  role_fk INT REFERENCES account_roles(id) NOT NULL,
  name VARCHAR(64) NOT NULL,
  email VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL
);


INSERT INTO accounts (tenant_fk, role_fk, name, email, password) VALUES
  ((SELECT id from tenants LIMIT 1), 1, 'mircea.stanciu', 'mick@jadebaboon.com', 'secret');
GRANT ALL PRIVILEGES ON TABLE accounts TO admin;



