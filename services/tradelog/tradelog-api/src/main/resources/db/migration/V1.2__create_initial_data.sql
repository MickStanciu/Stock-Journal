INSERT INTO action (name) VALUES
  ('SELL_OPTION'),
  ('BUY_OPTION');

INSERT INTO action_type (name) VALUES
  ('CASH_SECURED_PUT'),
  ('COVERED_CALL');

INSERT INTO public.tradelog (transaction_id, account_fk, date, symbol, mark, stock_price, implied_volatility, implied_volatility_hist, action_fk, action_type_fk, broker_fees) VALUES
  ('af94a639-e16f-463c-b2f3-129eae25db47', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-10-17 21:00:00.000000 +11:00', 'MAT', null, 14.83, 53.6, null, 'SELL_OPTION', 'CASH_SECURED PUT', 0),
  ('a29806c6-5e8b-4133-a56c-803e8fca2827', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-10-31 21:00:00.000000 +11:00', 'PGR', null, 60.87, 27.4, null, 'SELL_OPTION', 'CASH_SECURED PUT', 0);