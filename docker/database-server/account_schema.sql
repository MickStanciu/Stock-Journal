
-----------------------------------
-- discrimination table Accounts --
-----------------------------------

-- clean up
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS account_role_info;
DROP TABLE IF EXISTS account_roles;
DROP TYPE IF EXISTS role_priority;
DROP SEQUENCE IF EXISTS accounts_seq;
DROP EXTENSION if exists "uuid-ossp";

-- init extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- user roles table
CREATE TYPE role_priority AS ENUM ('L1', 'L2', 'L3', 'L4', 'L5');

CREATE TABLE account_roles (
  id INT PRIMARY KEY,
  name role_priority NOT NULL
);
GRANT ALL PRIVILEGES ON TABLE account_roles TO admin;
INSERT INTO account_roles (id, name) VALUES (1, 'L1'), (2, 'L2'), (3, 'L3'), (4, 'L4'), (5, 'L5');

-- account role info
CREATE TABLE account_role_info (
  tenant_fk UUID NOT NULL,
  role_fk INT REFERENCES account_roles(id) NOT NULL,
  PRIMARY KEY(tenant_fk, role_fk),
  name VARCHAR(64)
);

INSERT INTO account_role_info (tenant_fk, role_fk, name) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 1, 'Administrator'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 'Supervisor'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 3, 'Team leader'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 4, 'User Level 2'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 'User Level 1');

GRANT ALL PRIVILEGES ON TABLE account_role_info TO admin;

-- user accounts table
CREATE SEQUENCE accounts_seq;
GRANT ALL PRIVILEGES ON SEQUENCE accounts_seq TO admin;


CREATE TABLE accounts (
  id BIGINT PRIMARY KEY DEFAULT nextval('accounts_seq'),
  tenant_fk UUID NOT NULL,
  role_fk INT REFERENCES account_roles(id) NOT NULL,
  name VARCHAR(64) NOT NULL,
  email VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL,
  active boolean DEFAULT false NOT NULL
);


INSERT INTO accounts (tenant_fk, role_fk, name, email, password, active) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 1, 'Lola Admin', 'lola.admin@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 'Lola Supervisor 1', 'lola.super1@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 'Lola Supervisor 2', 'lola.super2@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 3, 'Lola Team Leader 1', 'lola.tl1@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 3, 'Lola Team Leader 2', 'lola.tl2@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 3, 'Lola Team Leader 3', 'lola.tl3@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 4, 'Lola User Level2 1', 'lola.ul21@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 4, 'Lola User Level2 2', 'lola.ul22@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 4, 'Lola User Level2 3', 'lola.ul23@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 4, 'Lola User Level2 4', 'lola.ul24@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 'Lola User Level1 1', 'lola.ul11@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 'Lola User Level1 2', 'lola.ul12@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 'Lola User Level1 3', 'lola.ul13@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 'Lola User Level1 4', 'lola.ul14@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 'Lola User Level1 5', 'lola.ul15@jadebaboon.com', 'secret', true);

GRANT ALL PRIVILEGES ON TABLE accounts TO admin;
