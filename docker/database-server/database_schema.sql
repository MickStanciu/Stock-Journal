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


