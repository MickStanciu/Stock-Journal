CREATE EXTENSION "uuid-ossp";

-- clean up
DROP TABLE IF EXISTS action;
DROP TABLE IF EXISTS action_type;
DROP TABLE IF EXISTS option_log;
DROP TABLE IF EXISTS shares_log;
DROP TABLE IF EXISTS shares_data;

CREATE TABLE action (
  name VARCHAR(32) NOT NULL PRIMARY KEY
);
GRANT ALL PRIVILEGES ON TABLE action TO admin;


CREATE TABLE action_type (
  name VARCHAR(32) NOT NULL PRIMARY KEY
);
GRANT ALL PRIVILEGES ON TABLE action_type TO admin;

CREATE TABLE option_log
(
  transaction_id          UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
  transaction_fk          UUID REFERENCES option_log (transaction_id),
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
GRANT ALL PRIVILEGES ON TABLE option_log TO admin;


CREATE TABLE shares_log
(
    transaction_id UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    account_fk     UUID                                        NOT NULL,
    date           TIMESTAMPTZ                                 NOT NULL,
    symbol         VARCHAR(16)                                 NOT NULL,
    price          FLOAT                                       NOT NULL,
    quantity       INTEGER,
    action_fk      VARCHAR(32) REFERENCES action (name),
    action_type_fk VARCHAR(32) REFERENCES action_type (name),
    broker_fees    FLOAT            DEFAULT 0.0,
    mark           VARCHAR(16)      DEFAULT NULL
);
GRANT ALL PRIVILEGES ON TABLE shares_log TO admin;


CREATE TABLE shares_data
(
    symbol            VARCHAR(16) PRIMARY KEY NOT NULL,
    last_updated_on   TIMESTAMPTZ             NOT NULL,
    sector            VARCHAR(50) DEFAULT '',
    market_cap_b      FLOAT       DEFAULT 0.0,
    p_e_ratio         FLOAT       DEFAULT NULL,
    future_p_e_ratio  FLOAT       DEFAULT NULL,
    book_value        FLOAT       DEFAULT NULL,
    eps               FLOAT       DEFAULT NULL,
    future_eps        FLOAT       DEFAULT NULL,
    price             FLOAT                   NOT NULL,
    finviz_target     FLOAT       DEFAULT NULL,
    p_e_future_target FLOAT       DEFAULT NULL
);

GRANT ALL PRIVILEGES ON TABLE shares_data TO admin;