CREATE TABLE producers (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  producer_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE products (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  product_name VARCHAR(255) NOT NULL,
  price DECIMAL(19,2) NOT NULL,
  producer_id BIGINT(20),
  PRIMARY KEY (id),
  FOREIGN KEY (producer_id) REFERENCES producers(id));

CREATE TABLE items (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  product_id BIGINT(20),
  quantity INT(11),
  PRIMARY KEY (id),
  FOREIGN KEY (product_id) REFERENCES products(id));

CREATE TABLE shopping_carts (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id));

CREATE TABLE users (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  address VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255),
  username VARCHAR(255) NOT NULL,
  shopping_cart_id BIGINT(20),
  PRIMARY KEY (`id`),
  FOREIGN KEY (shopping_cart_id) REFERENCES shopping_carts(id));

CREATE TABLE payments (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  is_paid BIT(1),
  PRIMARY KEY (id));

CREATE TABLE orders (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  canceled BIT(1),
  payment_id BIGINT(20),
  user_id BIGINT(20),
  PRIMARY KEY (id),
 FOREIGN KEY (payment_id) REFERENCES payments(id),
  FOREIGN KEY (user_id) REFERENCES users(id));

CREATE TABLE orders_items (
  order_id BIGINT(20) NOT NULL,
  items_id BIGINT(20) NOT NULL UNIQUE,
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (items_id) REFERENCES items(id));

  CREATE TABLE roles (
  user_id BIGINT(20) NOT NULL,
  role VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id));

CREATE TABLE shopping_carts_items (
  shopping_cart_id BIGINT(20) NOT NULL,
  items_id BIGINT(20) NOT NULL UNIQUE,
  FOREIGN KEY (shopping_cart_id) REFERENCES shopping_carts(id),
  FOREIGN KEY (items_id) REFERENCES items(id));

INSERT INTO producers (producer_name) VALUES ('Google');
INSERT INTO producers (producer_name) VALUES ('Microsoft');

INSERT INTO products (product_name, price, producer_id) VALUES ('Office', 49.99, 2);
INSERT INTO products (product_name, price, producer_id) VALUES ('Windows', 99.99, 2);
INSERT INTO products (product_name, price, producer_id) VALUES ('Lumia', 190, 2);

INSERT INTO items (product_id, quantity) VALUES (1, 1);
INSERT INTO items (product_id, quantity) VALUES (2, 1);
INSERT INTO items (product_id, quantity) VALUES (3, 2);
INSERT INTO items (product_id, quantity) VALUES (1, 1);

INSERT INTO shopping_carts (id) VALUES (null);
INSERT INTO shopping_carts (id) VALUES (null);

INSERT INTO users (address, name, username, password, shopping_cart_id) VALUES ('Munich', 'David', 'user1', 'pass1', 1);
INSERT INTO users (address, name, username, password, shopping_cart_id) VALUES ('Moscow', 'Wladimir', 'user2', 'pass2', 2);

INSERT INTO roles (user_id, role) VALUES (1, 'ROLE_USER');
INSERT INTO roles (user_id, role) VALUES (2, 'ROLE_ADMIN');
INSERT INTO roles (user_id, role) VALUES (2, 'ROLE_USER');

INSERT INTO payments (is_paid) VALUES (false);

INSERT INTO orders (canceled, payment_id, user_id) VALUES (false, 1, 2);

INSERT INTO orders_items (order_id, items_id) VALUES (1, 3);
INSERT INTO orders_items (order_id, items_id) VALUES (1, 4);

INSERT INTO shopping_carts_items (shopping_cart_id, items_id) VALUES (1, 1);
INSERT INTO shopping_carts_items (shopping_cart_id, items_id) VALUES (1, 2);