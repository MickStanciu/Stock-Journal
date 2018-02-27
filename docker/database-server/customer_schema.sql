-----------------------
-- Customer DataBase --
-----------------------

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
  mobile VARCHAR(10),
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