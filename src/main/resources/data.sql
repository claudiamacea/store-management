CREATE TABLE IF NOT EXISTS  product_category (
    id INT  GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE  IF NOT EXISTS  products (
    active BOOLEAN NOT NULL,
    category_id INT,
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    price DOUBLE,
    quantity INT,
    description VARCHAR(255),
    image_url VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES product_category(id)
);

INSERT INTO product_category (name, description) values
('Produse de panificatie', 'Panificatie'),
('Papetarie si birotica', 'Papetarie');

INSERT INTO products (active, category_id, price, quantity, description, image_url, name) values
(true, 1, 4.5, 100, 'Paine cu cartof', '', 'Paine'),
(true, 1, 2.5, 40, 'Covrig cu mac', '', 'Covrig'),
(true, 2, 6.5, 0, 'Creioane colorate, set 12', '', 'Creioane colorare');