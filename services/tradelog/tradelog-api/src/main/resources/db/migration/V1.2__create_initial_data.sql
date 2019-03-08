INSERT INTO action (name)
VALUES ('SELL_OPTION'),
       ('BUY_OPTION'),
       ('BUY_STOCK'),
       ('SELL_STOCK');


INSERT INTO action_type (name)
VALUES ('CASH_SECURED_PUT'),
       ('COVERED_CALL'),
       ('STOCK_PUT');


INSERT INTO simple_option (transaction_id, transaction_fk, account_fk, date, symbol, stock_price, strike_price, expiry_date, implied_volatility, implied_volatility_hist, profit_probability, contract_number, premium, action_fk, action_type_fk, broker_fees, mark)
VALUES
       ('90ef0965-0eba-4854-9ab5-12ef21be6464', null, 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-10-17 21:00:00.000000 +11:00', 'MAT', 14.83, 14.0, '2018-11-16 21:00:00.000000 +11:00', 53.6, null, 64.56, 1, 0.55, 'SELL_OPTION', 'CASH_SECURED_PUT', 0, 'A1'),
       ('b8480fe6-f4ce-4375-b508-feedbf9d3977', '90ef0965-0eba-4854-9ab5-12ef21be6464', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-11-03 21:00:00.000000 +11:00', 'MAT', 0, 14.0, '2018-11-16 21:00:00.000000 +11:00', null, null, null, 1, 0.30, 'BUY_OPTION', 'CASH_SECURED_PUT', 0, 'A1');


-- https://www.uuidgenerator.net/version4

-- b8480fe6-f4ce-4375-b508-feedbf9d3977
-- 5e4eb483-41ca-4c0c-8390-3df0137a1ac2
-- 4464bd1b-26e4-4e65-92a6-7f91d8c5eb11