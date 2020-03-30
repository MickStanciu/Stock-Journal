CREATE EXTENSION "uuid-ossp";

-- clean up
DROP TABLE IF EXISTS account_detail;
DROP TABLE IF EXISTS account_secure;

CREATE TABLE account_secure
(
    id       UUID DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT account_pkey PRIMARY KEY,
    password VARCHAR(20)                     NOT NULL,
    rainbow  VARCHAR(64)                     NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE account_secure TO admin;

CREATE TABLE account_detail
(
    account_fk   UUID                  NOT NULL
        CONSTRAINT account_pkey REFERENCES account_secure (id),
    login_name   VARCHAR(20)           NOT NULL
        CONSTRAINT login_name_uq UNIQUE,
    display_name VARCHAR(20)           NOT NULL,
    email        VARCHAR(64)           NOT NULL,
    active       BOOLEAN DEFAULT FALSE NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE account_detail TO admin;

CREATE TABLE rainbow
(
    rainbow VARCHAR(64) NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE rainbow TO admin;