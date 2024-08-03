CREATE TABLE product_category (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE products (
    active BOOLEAN NOT NULL,
    category_id INT,
    id INT PRIMARY KEY,
    price DOUBLE,
    quantity INT,
    description VARCHAR(255),
    image_url VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES product_category(id)
);

insert into  product_category values (1,'Produse de panificatie','Panificatie');
insert into  product_category values (2,'Papetarie si birotica','Papetarie');

insert into  products values (true,1,1,4.5,100,'Paine cu cartof','','Paine');
insert into  products values (true,1,2,2.5,40,'Covrig cu mac','','Covrig');
insert into  products values (true,2,3,6.5,0,'Creioane colorate, set 12','','Creioane colorare');

