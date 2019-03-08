CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- clean up
DROP TABLE IF EXISTS action;
DROP TABLE IF EXISTS action_type;
DROP TABLE IF EXISTS tradelog;

CREATE TABLE action (
  name VARCHAR(32) NOT NULL PRIMARY KEY
);

GRANT ALL PRIVILEGES ON TABLE action TO admin;


CREATE TABLE action_type (
  name VARCHAR(32) NOT NULL PRIMARY KEY
);

GRANT ALL PRIVILEGES ON TABLE action_type TO admin;

CREATE TABLE simple_option
(
  transaction_id          UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
  transaction_fk          UUID REFERENCES simple_option (transaction_id),
  account_fk              UUID                                        NOT NULL,
  date                    TIMESTAMPTZ                                 NOT NULL,
  symbol                  VARCHAR(16)                                 NOT NULL,
  stock_price             FLOAT                                       NOT NULL,
  strike_price            FLOAT                                       NOT NULL,
  expiry_date             DATE,
  implied_volatility      FLOAT            DEFAULT NULL,
  implied_volatility_hist FLOAT            DEFAULT NULL,
  profit_probability      FLOAT            DEFAULT NULL,
  contract_number         INTEGER,
  premium                 FLOAT,
  action_fk               VARCHAR(32) REFERENCES action (name),
  action_type_fk          VARCHAR(32) REFERENCES action_type (name),
  broker_fees             FLOAT            DEFAULT 0.0,
  mark                    VARCHAR(16)      DEFAULT NULL
);







GRANT ALL PRIVILEGES ON TABLE simple_option TO admin;