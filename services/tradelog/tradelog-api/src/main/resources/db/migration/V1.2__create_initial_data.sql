INSERT INTO action (name) VALUES
  ('SELL_OPTION'),
  ('BUY_OPTION');

INSERT INTO action_type (name) VALUES
  ('CASH_SECURED_PUT'),
  ('COVERED_CALL');

INSERT INTO tradelog (account_fk, date, symbol, mark, stock_price, implied_volatility, implied_volatility_hist, action_fk, action_type_fk, broker_fees) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-10-17 21:00:00.000000 +11:00', 'MAT', null, 14.83, 53.6, null, 'SELL_OPTION', 'CASH_SECURED_PUT', 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-10-31 21:00:00.000000 +11:00', 'PGR', null, 60.87, 27.4, null, 'SELL_OPTION', 'CASH_SECURED_PUT', 0);