INSERT INTO item (model, sku, price) VALUES
  ('iPhone 5', 'sku1234', 100.0001),
  ('iPhone 5s', 'sku1234', 100.0005),
  ('iPhone 6', 'sku1234', 100.0002),
  ('iPhone 6 Plus', 'sku1234', 100.0002),
  ('iPhone 6s', 'sku1234', 100.0006),
  ('iPhone 6s Plus', 'sku1234', 100.0006),
  ('iPhone 7', 'sku1234', 100.0003),
  ('iPhone 7 Plus', 'sku1234', 100.0003),
  ('iPhone 7s', 'sku1234', 100.0003),
  ('iPhone 7s Plus', 'sku1234', 100.0003),
  ('iPhone 8', 'sku1234', 100.0004),
  ('iPhone 8 Plus', 'sku1234', 100.0004);

INSERT INTO attribute (description, sort_order) VALUES
  ('memory', 1),
  ('resolution', 2),
  ('colour', 3);

INSERT INTO item_attribute (item_id, attribute_id, value) VALUES
  (7, 1, '32 GB'),
  (7, 3, 'Gold'),
  (7, 2, '720P');

