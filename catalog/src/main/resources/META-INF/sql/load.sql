INSERT INTO item (model, sku, price, currency_fk) VALUES
  ('iPhone 5', 'sku1234', 100.0001, 1),
  ('iPhone 5s', 'sku1234', 100.0005, 1),
  ('iPhone 6', 'sku1234', 100.0002, 1),
  ('iPhone 6 Plus', 'sku1234', 100.0002, 1),
  ('iPhone 6s', 'sku1234', 100.0006, 1),
  ('iPhone 6s Plus', 'sku1234', 100.0006, 1),
  ('iPhone 7', 'sku1234', 100.0003, 1),
  ('iPhone 7 Plus', 'sku1234', 100.0003, 1),
  ('iPhone 7s', 'sku1234', 100.0003, 1),
  ('iPhone 7s Plus', 'sku1234', 100.0003, 1),
  ('iPhone 8', 'sku1234', 100.0004, 1),
  ('iPhone 8 Plus', 'sku1234', 100.0004, 1);

SELECT *
FROM item;

INSERT INTO attribute (description, sort_order) VALUES
  ('memory', 1),
  ('resolution', 2),
  ('colour', 3);

INSERT INTO item_attribute (item_id, attribute_id, value) VALUES
  (7, 1, '32 GB'),
  (7, 3, 'Gold'),
  (7, 2, '720P');

INSERT INTO currency (short_name, long_name, symbol) VALUES
  ('AUD', 'Australian Dollar', '$');
