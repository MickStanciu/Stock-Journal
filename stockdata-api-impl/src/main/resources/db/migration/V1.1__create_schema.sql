DROP TABLE IF EXISTS price;
DROP TABLE IF EXISTS symbol;
DROP TABLE IF EXISTS exchange;
DROP TABLE IF EXISTS country;

---

CREATE TABLE country (
  code VARCHAR (3) NOT NULL,
  full_name VARCHAR (50) NOT NULL,
  PRIMARY KEY(code)
);

ALTER TABLE country owner to admin;
GRANT ALL PRIVILEGES ON TABLE country TO admin;

---

CREATE TABLE exchange (
  country_fk VARCHAR (3) REFERENCES country(code) NOT NULL,
  name VARCHAR(20) NOT NULL,
  description VARCHAR(100) DEFAULT '',
  PRIMARY KEY(name)
);

ALTER TABLE exchange owner to admin;
GRANT ALL PRIVILEGES ON TABLE exchange TO admin;

---

CREATE TABLE symbol (
  exchange_fk VARCHAR(20) REFERENCES exchange(name) NOT NULL,
  name VARCHAR(10) NOT NULL,
  PRIMARY KEY(name)
);

ALTER TABLE symbol owner to admin;
GRANT ALL PRIVILEGES ON TABLE symbol TO admin;

---

CREATE TABLE price (
  date DATE NOT NULL,
  symbol_fk VARCHAR(10) REFERENCES symbol(name) NOT NULL,
  day_open NUMERIC DEFAULT -1,
  day_high NUMERIC DEFAULT -1,
  day_low NUMERIC DEFAULT -1,
  day_close NUMERIC DEFAULT -1,
  day_adj_close NUMERIC DEFAULT -1,
  volume NUMERIC DEFAULT -1,
  per_daily_return NUMERIC DEFAULT -1,
  processed BOOL DEFAULT FALSE,
  PRIMARY KEY(date, symbol_fk)
);

ALTER TABLE price owner to admin;
GRANT ALL PRIVILEGES ON TABLE price TO admin;

---