INSERT INTO transaction_log (id, account_fk, date, symbol, transaction_type_fk) VALUES ('425193bb-2dc7-4f4f-af92-f222569a0c41', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2019-05-04 10:00:01.000000', 'CVS', 'DIVIDEND');
INSERT INTO transaction_log (id, account_fk, date, symbol, transaction_type_fk) VALUES ('1be2cb91-7d3d-41e2-bdf5-1a7deb2e4dd0', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2019-06-04 10:00:01.000000', 'WFC', 'DIVIDEND');
INSERT INTO transaction_log (id, account_fk, date, symbol, transaction_type_fk) VALUES ('0121b8b9-8c2d-4302-962a-06b8f9cabbbd', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2019-06-12 10:00:01.000000', 'SWKS', 'DIVIDEND');
INSERT INTO transaction_log (id, account_fk, date, symbol, transaction_type_fk) VALUES ('9540788b-b0bc-421d-8032-f8f2303490ba', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2019-06-13 10:00:01.000000', 'WBA', 'DIVIDEND');
INSERT INTO transaction_log (id, account_fk, date, symbol, transaction_type_fk) VALUES ('1a67dc56-709d-4877-8374-923af190bd6f', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2019-06-14 10:00:01.000000', 'AMAT', 'DIVIDEND');

INSERT INTO dividend_log (transaction_fk, dividend, quantity) VALUES ('425193bb-2dc7-4f4f-af92-f222569a0c41', 0.5, 100);
INSERT INTO dividend_log (transaction_fk, dividend, quantity) VALUES ('1be2cb91-7d3d-41e2-bdf5-1a7deb2e4dd0', 0.45, 200);
INSERT INTO dividend_log (transaction_fk, dividend, quantity) VALUES ('0121b8b9-8c2d-4302-962a-06b8f9cabbbd', 0.38, 100);
INSERT INTO dividend_log (transaction_fk, dividend, quantity) VALUES ('9540788b-b0bc-421d-8032-f8f2303490ba', 0.44, 100);
INSERT INTO dividend_log (transaction_fk, dividend, quantity) VALUES ('1a67dc56-709d-4877-8374-923af190bd6f', 0.21, 100);