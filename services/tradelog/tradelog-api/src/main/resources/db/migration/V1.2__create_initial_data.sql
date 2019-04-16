INSERT INTO action (name)
VALUES ('SELL'),
       ('BUY');


INSERT INTO action_type (name)
VALUES ('STOCK'),
       ('CALL_OPTION'),
       ('PUT_OPTION');


INSERT INTO simple_option (transaction_id, transaction_fk, account_fk, date, symbol, stock_price, strike_price, expiry_date, implied_volatility, implied_volatility_hist, profit_probability, contract_number, premium, action_fk, action_type_fk, broker_fees, mark)
VALUES
       ('90ef0965-0eba-4854-9ab5-12ef21be6464', null, 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-10-17 21:00:00.000000 +11:00', 'MAT', 14.83, 14.0, '2018-11-16 21:00:00.000000 +11:00', 53.6, null, 64.56, 1, 0.55, 'SELL', 'PUT_OPTION', 0, 'A1'),
       ('b8480fe6-f4ce-4375-b508-feedbf9d3977', '90ef0965-0eba-4854-9ab5-12ef21be6464', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-11-03 21:00:00.000000 +11:00', 'MAT', 0, 14.0, '2018-11-16 21:00:00.000000 +11:00', null, null, null, 1, 0.30, 'BUY', 'PUT_OPTION', 0, 'A1');

INSERT INTO simple_option (transaction_id, transaction_fk, account_fk, date, symbol, stock_price, strike_price, expiry_date, implied_volatility, implied_volatility_hist, profit_probability, contract_number, premium, action_fk, action_type_fk, broker_fees, mark)
VALUES
       ('4464bd1b-26e4-4e65-92a6-7f91d8c5eb11', null, 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-11-08 21:00:00.000000 +10:00', 'TWTR', 34.79, 35, '2018-12-21 21:00:00.000000 +10:00', 41.8, null, null, 2, 2.11, 'SELL', 'PUT_OPTION', 0, 'A1'),
       ('5e4eb483-41ca-4c0c-8390-3df0137a1ac2', '4464bd1b-26e4-4e65-92a6-7f91d8c5eb11', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-12-12 21:00:00.000000 +10:00', 'TWTR', 0, 35, '2018-12-21 21:00:00.000000 +10:00', null, null, null, 2, 0.70, 'BUY', 'PUT_OPTION', 0, 'A1');

INSERT INTO simple_option (transaction_id, transaction_fk, account_fk, date, symbol, stock_price, strike_price, expiry_date, implied_volatility, implied_volatility_hist, profit_probability, contract_number, premium, action_fk, action_type_fk, broker_fees, mark)
VALUES
       ('654af390-4205-4f41-b210-38bc616d8e5e', null, 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-11-08 21:00:00.000000 +10:00', 'CYBR', 70.63, 70, '2018-12-21 21:00:00.000000 +10:00', 55.2, null, null, 1, 4.35, 'SELL', 'PUT_OPTION', 0, 'A1'),
       ('f3040f85-6940-4f50-9346-205ac9917fd6', '654af390-4205-4f41-b210-38bc616d8e5e', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-12-12 21:00:00.000000 +10:00', 'CYBR', 0, 70, '2018-12-21 21:00:00.000000 +10:00', null, null, null, 1, 1.53, 'BUY', 'PUT_OPTION', 0, 'A1');

-- https://www.uuidgenerator.net/version4
-- 704d4679-1a27-491f-aceb-0948810e4a4b
-- 296d35f2-9225-40e7-a38b-1003d9fc75ce