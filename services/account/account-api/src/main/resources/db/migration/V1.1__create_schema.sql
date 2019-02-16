-- clean up
DROP TABLE IF EXISTS accounts;

-- todo: move password to different table
CREATE TABLE accounts (
  id UUID NOT NULL PRIMARY KEY,
  name VARCHAR(64) NOT NULL,
  email VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL,
  active boolean DEFAULT false NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE accounts TO admin;
