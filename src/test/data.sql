CREATE TABLE IF NOT EXISTS product(
    id VARCHAR(36),
    product_name VARCHAR(50),
    unit_price DECIMAL(5,2),
    stock INT,

     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_registry(
    id VARCHAR(36),
    expiration_date DATE,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orders(
    id VARCHAR(36),
    status VARCHAR,
    created_at DATE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_products(
    id VARCHAR(36),
    order_id VARCHAR(36),
    product_id VARCHAR(36),
    quantity INT,

    PRIMARY KEY(order_id)
);

insert into product (id, product_name, unit_price,stock) values ('7637cab3-69a3-440f-a63a-25c5339d800d', 'Orange', 1.74,31);
insert into orders (id,status,created_at) values ('82e6e579-2c6d-42ab-9358-a78f934abdfe','PENDING',NOW());
insert into order_products (id,order_id,product_id,quantity) values ('5cc62c75-aa0c-4004-ae36-8424bc464008','82e6e579-2c6d-42ab-9358-a78f934abdfe','7637cab3-69a3-440f-a63a-25c5339d800d',5);
insert into order_registry (id, expiration_date) values ('82e6e579-2c6d-42ab-9358-a78f934abdfe', NOW());
