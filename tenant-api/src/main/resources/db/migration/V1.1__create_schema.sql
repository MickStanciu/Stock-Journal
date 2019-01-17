-- clean up
DROP table IF EXISTS tenants;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- create table
CREATE TABLE tenants (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  name VARCHAR(64) NOT NULL
);
