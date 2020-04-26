-- clean up
DROP TABLE IF EXISTS fundamental;
DROP TABLE IF EXISTS price;

CREATE TABLE fundamental
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

GRANT ALL PRIVILEGES ON TABLE fundamental TO admin;

CREATE TABLE price
(
    symbol          VARCHAR(16)   NOT NULL
        CONSTRAINT price_pkey PRIMARY KEY,
    last_updated_on TIMESTAMPTZ   NOT NULL,
    last_failed_on TIMESTAMPTZ,
    last_close      NUMERIC(8, 4) NOT NULL,
    active          BOOLEAN DEFAULT TRUE NOT NULL
);


GRANT ALL PRIVILEGES ON TABLE price TO admin;
