CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- clean up
DROP TABLE IF EXISTS option_log;
DROP TABLE IF EXISTS shares_log;
DROP TABLE IF EXISTS dividend_log;
DROP TABLE IF EXISTS transaction_settings_log;
DROP TABLE IF EXISTS transaction_log;
DROP TABLE IF EXISTS shares_data;

DROP TABLE IF EXISTS action;
DROP TABLE IF EXISTS option_type;
DROP TABLE IF EXISTS transaction_type;
DROP TABLE IF EXISTS portfolio;

CREATE TABLE transaction_type (
    name VARCHAR(32) NOT NULL PRIMARY KEY
);
GRANT ALL PRIVILEGES ON TABLE transaction_type TO admin;

CREATE TABLE action (
  name VARCHAR(32) NOT NULL PRIMARY KEY
);
GRANT ALL PRIVILEGES ON TABLE action TO admin;


CREATE TABLE option_type (
  name VARCHAR(32) NOT NULL PRIMARY KEY
);
GRANT ALL PRIVILEGES ON TABLE option_type TO admin;

CREATE TABLE portfolio
(
    id         UUID DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT portfolio_log_pkey PRIMARY KEY,
    name       VARCHAR(64),
    account_fk UUID                            NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE portfolio TO admin;

CREATE TABLE transaction_log
(
    id                  UUID          DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT transaction_log_pkey PRIMARY KEY,
    account_fk          UUID                                     NOT NULL,
    date                TIMESTAMPTZ                              NOT NULL,
    symbol              VARCHAR(16)                              NOT NULL,
    transaction_type_fk VARCHAR(32)                              NOT NULL
        CONSTRAINT transaction_log_action_type_fk_fkey REFERENCES transaction_type (name),
    portfolio_fk        UUID                                     NOT NULL
        CONSTRAINT transaction_log_portfolio_fk_fkey REFERENCES portfolio (id),
    broker_fees         NUMERIC(8, 2) DEFAULT 0.00
);
GRANT ALL PRIVILEGES ON TABLE transaction_log TO admin;



CREATE TABLE shares_log
(
    transaction_fk UUID  NOT NULL
        CONSTRAINT transaction_log_pkey REFERENCES transaction_log (id) UNIQUE,
    price          NUMERIC(8,4) NOT NULL,
    quantity       INTEGER,
    action_fk      VARCHAR(32) REFERENCES action (name)
);

GRANT ALL PRIVILEGES ON TABLE shares_log TO admin;



CREATE TABLE option_log
(
    transaction_fk  UUID  NOT NULL
        CONSTRAINT transaction_log_pkey REFERENCES transaction_log (id) UNIQUE,
    stock_price     NUMERIC(8,2) DEFAULT 0.00,
    strike_price    NUMERIC(8,2) NOT NULL,
    expiry_date     DATE,
    contract_number INTEGER,
    premium         NUMERIC(8,2) NOT NULL,
    action_fk       VARCHAR(32) REFERENCES action (name),
    option_type_fk  VARCHAR(32) REFERENCES option_type (name)
);


GRANT ALL PRIVILEGES ON TABLE option_log TO admin;


create table if not exists dividend_log
(
    transaction_fk UUID NOT NULL
        CONSTRAINT transaction_log_pkey REFERENCES transaction_log (id) UNIQUE,
    dividend NUMERIC(8,4) NOT NULL,
    quantity INTEGER NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE dividend_log TO admin;


CREATE TABLE transaction_settings_log
(
    transaction_fk  UUID  NOT NULL
        CONSTRAINT transaction_log_pkey REFERENCES transaction_log (id) UNIQUE,
    preferred_price NUMERIC(8,2) DEFAULT NULL,
    group_selected  BOOLEAN DEFAULT TRUE,
    leg_closed      BOOLEAN DEFAULT FALSE
);

GRANT ALL PRIVILEGES ON TABLE transaction_settings_log TO admin;
