-----------------------------------
-- discrimination table Accounts --
-----------------------------------

-- clean up
DROP TABLE IF EXISTS account_relationships;
DROP TABLE IF EXISTS accounts;
-- DROP TABLE IF EXISTS account_role_info;
DROP TABLE IF EXISTS account_roles;
DROP SEQUENCE IF EXISTS account_roles_seq;
-- DROP TYPE IF EXISTS role_priority;
DROP SEQUENCE IF EXISTS accounts_seq;
DROP EXTENSION if exists "uuid-ossp";

-- init extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- account_roles table
CREATE SEQUENCE account_roles_seq;
GRANT ALL PRIVILEGES ON SEQUENCE account_roles_seq TO admin;

CREATE TABLE account_roles (
  tenant_fk UUID NOT NULL,
  id BIGINT PRIMARY KEY DEFAULT nextval('account_roles_seq'),
  name VARCHAR(64) NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE account_roles TO admin;

INSERT INTO account_roles (tenant_fk, name) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Administrator'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'No Role');


-- accounts table
CREATE SEQUENCE accounts_seq;
GRANT ALL PRIVILEGES ON SEQUENCE accounts_seq TO admin;

-- todo: consider to make email PK
CREATE TABLE accounts (
  tenant_fk UUID NOT NULL,
  id BIGINT PRIMARY KEY DEFAULT nextval('accounts_seq'),
  role_fk INT REFERENCES account_roles(id) NOT NULL,
  -- parent_fk BIGINT REFERENCES accounts(id),
  name VARCHAR(64) NOT NULL,
  email VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL,
  active boolean DEFAULT false NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE accounts TO admin;

INSERT INTO accounts (tenant_fk, role_fk, name, email, password, active) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 1, 'Lola Admin', 'lola.admin@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 'Lola Ceo', 'lola.ceo@jadebaboon.com', 'secret', true);


-- account_relationships table
CREATE TABLE account_relationships (
  tenant_fk UUID NOT NULL,
  parent_fk BIGINT NOT NULL references accounts(id),
  child_fk BIGINT NOT NULL references accounts(id),
  depth INT NOT NULL,
  PRIMARY KEY(parent_fk, child_fk)
);

GRANT ALL PRIVILEGES ON TABLE account_relationships TO admin;


-- TODO: not sure if 1-1 type is necessary
INSERT INTO account_relationships (tenant_fk, parent_fk, child_fk, depth) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 1, 1, 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 2, 0);

INSERT INTO account_relationships (tenant_fk, parent_fk, child_fk, depth) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 1, 2, 1);

