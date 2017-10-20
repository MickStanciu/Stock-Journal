-- clean
DROP TABLE IF EXISTS stock;

-- stock
CREATE TABLE stock (
  item_fk BIGINT PRIMARY KEY,
  quantity BIGINT,
  foreign key(item_fk)
  references item(id) match simple
);

GRANT ALL PRIVILEGES ON TABLE stock TO admin;