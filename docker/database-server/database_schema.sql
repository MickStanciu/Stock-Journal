----------------------------------
-- discrimination table Tenants --
----------------------------------

--clean up
DROP TABLE IF EXISTS tenants;
-- init extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE tenants (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  name VARCHAR(64) NOT NULL
);

INSERT INTO tenants (id, name) VALUES ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'DEMO');
GRANT ALL PRIVILEGES ON TABLE tenants TO admin;


-----------------------------------
-- discrimination table Accounts --
-----------------------------------

-- clean up
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS account_role_info;
DROP TABLE IF EXISTS account_roles;
DROP SEQUENCE IF EXISTS accounts_seq;

-- init extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- user roles table
CREATE TABLE account_roles (
  id INT PRIMARY KEY,
  name VARCHAR(10) NOT NULL
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
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 1, 'mircea.stanciu', 'mick@jadebaboon.com', 'secret', true);
GRANT ALL PRIVILEGES ON TABLE accounts TO admin;


-----------------------------------
-- discrimination table Customer --
-----------------------------------

-- clean up
DROP TABLE IF EXISTS customers;
DROP SEQUENCE IF EXISTS customers_seq;

-- customer table
CREATE SEQUENCE customers_seq;
GRANT ALL PRIVILEGES ON SEQUENCE customers_seq TO admin;

CREATE TABLE customers (
  id BIGINT PRIMARY KEY DEFAULT nextval('customers_seq'),
  tenant_fk UUID NOT NULL,
  first_name VARCHAR(64) DEFAULT 'unknown',
  alias VARCHAR(64) DEFAULT '',
  last_name VARCHAR(64) DEFAULT 'unknown',
  email VARCHAR(64) DEFAULT 'unknown',
  mobile DECIMAL(10),
  birthdate DATE NOT NULL,
  active boolean DEFAULT false NOT NULL
  -- guardian_fk
  -- photo
);

INSERT INTO customers (tenant_fk, first_name, alias, last_name, email, mobile, birthdate, active) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Lola', '', 'White', 'lola.white@domain.com', '0400000000', '2000-01-01', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Lola', '', 'Blue', 'lola.blue@domain.com', '0400000000', '2000-01-01', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Lola', '', 'Red', 'lola.red@domain.com', '0400000000', '2000-01-01', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Lola', '', 'Purple', 'lola.purple@domain.com', '0400000000', '2000-01-01', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Lola', '', 'Yellow', 'lola.yellow@domain.com', '0400000000', '2000-01-01', true);

GRANT ALL PRIVILEGES ON TABLE customers TO admin;