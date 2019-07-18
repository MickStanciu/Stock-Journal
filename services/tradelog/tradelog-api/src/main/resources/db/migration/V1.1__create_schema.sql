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

CREATE TABLE transaction_log
(
    id                  UUID    DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT transaction_log_pkey PRIMARY KEY,
    account_fk          UUID                               NOT NULL,
    date                TIMESTAMPTZ                        NOT NULL,
    symbol              VARCHAR(16)                        NOT NULL,
    transaction_type_fk VARCHAR(32)                        NOT NULL
        CONSTRAINT transaction_log_action_type_fk_fkey REFERENCES transaction_type (name)
);

GRANT ALL PRIVILEGES ON TABLE transaction_log TO admin;



CREATE TABLE shares_log
(
    transaction_fk UUID  NOT NULL
        CONSTRAINT transaction_log_pkey REFERENCES transaction_log (id) UNIQUE,
    price          FLOAT NOT NULL,
    quantity       INTEGER,
    action_fk      VARCHAR(32) REFERENCES action (name),
    broker_fees    FLOAT DEFAULT 0.0
);

GRANT ALL PRIVILEGES ON TABLE shares_log TO admin;



CREATE TABLE option_log
(
    transaction_fk  UUID  NOT NULL
        CONSTRAINT transaction_log_pkey REFERENCES transaction_log (id) UNIQUE,
    stock_price     FLOAT NOT NULL,
    strike_price    FLOAT NOT NULL,
    expiry_date     DATE,
    contract_number INTEGER,
    premium         FLOAT,
    action_fk       VARCHAR(32) REFERENCES action (name),
    option_type_fk  VARCHAR(32) REFERENCES option_type (name),
    broker_fees    FLOAT DEFAULT 0.0
);


GRANT ALL PRIVILEGES ON TABLE option_log TO admin;


create table if not exists dividend_log
(
    transaction_fk UUID NOT NULL
        CONSTRAINT transaction_log_pkey REFERENCES transaction_log (id) UNIQUE,
    dividend FLOAT NOT NULL,
    quantity INTEGER NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE dividend_log TO admin;



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
    finviz_target     NUMERIC     DEFAULT NULL,
    p_e_future_target NUMERIC     DEFAULT NULL
);

GRANT ALL PRIVILEGES ON TABLE shares_data TO admin;


CREATE TABLE transaction_settings_log
(
    transaction_fk UUID NOT NULL
        CONSTRAINT transaction_log_pkey REFERENCES transaction_log (id) UNIQUE,
    group_selected BOOLEAN DEFAULT TRUE,
    leg_closed     BOOLEAN DEFAULT FALSE
);

GRANT ALL PRIVILEGES ON TABLE transaction_settings_log TO admin;