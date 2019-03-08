INSERT INTO action (name)
VALUES ('SELL_OPTION'),
       ('BUY_OPTION'),
       ('BUY_STOCK'),
       ('SELL_STOCK');


INSERT INTO action_type (name)
VALUES ('CASH_SECURED_PUT'),
       ('COVERED_CALL'),
       ('STOCK_PUT');


INSERT INTO tradelog (transaction_id, account_fk, date, symbol, mark, stock_price, implied_volatility, implied_volatility_hist, action_fk, action_type_fk, broker_fees) VALUES
  ('90ef0965-0eba-4854-9ab5-12ef21be6464', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-10-17 21:00:00.000000 +11:00', 'MAT', 'A1', 14.83, 53.6, null, 'SELL_OPTION', 'CASH_SECURED_PUT', 0),
  ('d8b43a40-e83b-4bb4-aaae-88ddc081bf83', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-11-03 21:00:00.000000 +11:00', 'MAT', 'A1', 0, 0, null, 'BUY_OPTION', 'CASH_SECURED_PUT', 0),
  ('ff1cd646-59af-4da2-8e73-ecc585b5c3bf', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-10-31 21:00:00.000000 +11:00', 'PGR', 'A1', 60.87, 27.4, null, 'SELL_OPTION', 'CASH_SECURED_PUT', 0),
  ('0c5df2f7-a69b-41cd-b88e-d0f7d2a3d204', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-11-13 21:00:00.000000 +11:00', 'PGR', 'A1', 0, 0, null, 'BUY_OPTION', 'CASH_SECURED_PUT', 0),
  ('c1a43b97-06cb-4183-9b0f-1aabf7fdb31c', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-10-31 21:00:00.000000 +11:00', 'HIIQ', 'A1', 48.96, 74.2, null, 'SELL_OPTION', 'CASH_SECURED_PUT', 0),
  ('b6ce26da-e423-4f47-87bb-124ea8e31cfe', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-11-17 21:00:00.000000 +11:00', 'HIIQ', 'A1', 0, 0, null, 'BUY_STOCK', 'CASH_SECURED_PUT', 0),
  ('11aec9d3-530a-4811-a76f-418a55238758', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-11-17 21:00:00.000000 +11:00', 'HIIQ', 'A2', 0, 0, null, 'BUY_STOCK', 'STOCK_PUT', 0);

INSERT INTO simple_option (transaction_fk, strike_price, expiry_date, profit_probability, contract_number, premium) VALUES
  ('90ef0965-0eba-4854-9ab5-12ef21be6464', 14.0, '2018-11-16 21:00:00.000000 +11:00', 64.56, 3, 0.55),
  ('d8b43a40-e83b-4bb4-aaae-88ddc081bf83', 14.0, '2018-11-16 21:00:00.000000 +11:00', null, 3, 0.30),
  ('ff1cd646-59af-4da2-8e73-ecc585b5c3bf', 70.0, '2018-11-16 21:00:00.000000 +11:00', null, 1, 2.5),
  ('0c5df2f7-a69b-41cd-b88e-d0f7d2a3d204', 70.0, '2018-11-16 21:00:00.000000 +11:00', null, 1, 0.38),
  ('c1a43b97-06cb-4183-9b0f-1aabf7fdb31c', 45, '2018-11-16 21:00:00.000000 +11:00', null, 1, 1.85),
  ('b6ce26da-e423-4f47-87bb-124ea8e31cfe', 45, '2018-11-16 21:00:00.000000 +11:00', null, 1, 0);


-- https://www.uuidgenerator.net/version4

-- b8480fe6-f4ce-4375-b508-feedbf9d3977
-- 5e4eb483-41ca-4c0c-8390-3df0137a1ac2
-- 4464bd1b-26e4-4e65-92a6-7f91d8c5eb11