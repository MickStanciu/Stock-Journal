-- clean up
DROP TABLE IF EXISTS prices;

CREATE TABLE prices (
  date DATE NOT NULL,
  symbol VARCHAR(10) NOT NULL,
  day_open NUMERIC DEFAULT -1,
  day_high NUMERIC DEFAULT -1,
  day_low NUMERIC DEFAULT -1,
  day_close NUMERIC DEFAULT -1,
  day_adj_close NUMERIC DEFAULT -1,
  volume NUMERIC DEFAULT -1
);

GRANT ALL PRIVILEGES ON TABLE prices TO admin;