-- clean up
DROP TABLE IF EXISTS account_relationships;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS account_permissions;
DROP TABLE IF EXISTS account_roles;
DROP SEQUENCE IF EXISTS account_roles_seq;
DROP SEQUENCE IF EXISTS accounts_seq;

-- account_roles table
CREATE SEQUENCE account_roles_seq;
GRANT ALL PRIVILEGES ON SEQUENCE account_roles_seq TO admin;

CREATE TABLE account_roles (
  tenant_fk UUID NOT NULL,
  id INT PRIMARY KEY DEFAULT nextval('account_roles_seq'),
  name VARCHAR(64) NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE account_roles TO admin;


-- account_permissions table
-- todo: add PK
CREATE TABLE account_permissions (
  tenant_fk UUID NOT NULL,
  role_fk INT REFERENCES account_roles(id) NOT NULL,
  name VARCHAR(64) NOT NULL
  --   PRIMARY KEY(tenant_fk, role_fk)
);

GRANT ALL PRIVILEGES ON TABLE account_permissions TO admin;


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

-- account_relationships table
CREATE TABLE account_relationships (
  tenant_fk UUID NOT NULL,
  parent_fk BIGINT NOT NULL references accounts(id),
  child_fk BIGINT NOT NULL references accounts(id),
  depth INT NOT NULL,
  PRIMARY KEY(parent_fk, child_fk)
);

GRANT ALL PRIVILEGES ON TABLE account_relationships TO admin;
