CREATE DATABASE IF NOT EXISTS supermarket;

USE supermarket;

CREATE TABLE IF NOT EXISTS customers(
	id int auto_increment primary key, -- autofill id column
    name varchar(100),
    email varchar(100) UNIQUE,
    password_hash varchar(100),
    balance decimal(10,2)
);

CREATE TABLE IF NOT EXISTS sales_managers(
	id int auto_increment primary key, -- autofill id column
    name varchar(100),
    email varchar(100) UNIQUE,
    password_hash varchar(100),
    balance decimal(10,2)
);

CREATE TABLE IF NOT EXISTS products(
	id int auto_increment primary key, -- autofill id column
    name varchar(100) UNIQUE,
    available_quantity decimal(10,2),
    unit_size decimal(10,2),
    purchase_price decimal(10,2),
    retail_price decimal(10,2)
);

CREATE TABLE IF NOT EXISTS purchase_history(
	id int auto_increment primary key, -- autofill id column
    customer_email varchar(100),
    product_name varchar(100),
    amount_sold decimal(10,2),
    purchase_price decimal(10,2),
    retail_price decimal(10,2),
    total decimal(10,2),
    FOREIGN KEY (customer_email) REFERENCES customers(email),
    FOREIGN KEY (product_name) REFERENCES products(name)
);

SELECT * FROM customers;
SELECT * FROM sales_managers;
SELECT * FROM products;
SELECT * FROM purchase_history;

INSERT INTO products (name, available_quantity, unit_size, purchase_price, retail_price)
VALUES 
    ('Apple Juice', 50.00, 1.00, 1, 4.99),
    ('Peanut Butter', 25.00, 1.00, 1.25, 2.99),
    ('Greek Yogurt', 30.00, 1.00, 1.50, 3.49),
    ('Ground Beef', 15.00, 1.00, 5.00, 8.99),
    ('Brown Rice', 20.00, 1.00, 3.00, 5.99),
    ('Whole Wheat Bread', 40.00, 1.00, 1.75, 3.99),
    ('Parmesan Cheese', 10.00, 1.00, 2.50, 4.99),
    ('Salmon Filet', 12.00, 1.00, 8.00, 12.99),
    ('Romaine Lettuce', 35.00, 1.00, 1.50, 2.99),
    ('Granola', 18.00, 1.00, 2.50, 4.99),
    ('Black Beans', 25.00, 1.00, 1.50, 2.99),
    ('Sweet Potatoes', 20.00, 1.00, 2.00, 3.99),
    ('Orange Juice', 40.00, 1.00, 2.50, 4.99),
    ('Almond Milk', 30.00, 1.00, 2.00, 3.99),
    ('Chicken Breast', 12.00, 1.00, 5.00, 7.99),
    ('Cheddar Cheese', 15.00, 1.00, 2.50, 4.99),
    ('Canned Tuna', 20.00, 1.00, 2.00, 3.99),
    ('Oatmeal', 22.00, 1.00, 1.50, 2.99),
    ('Broccoli', 30.00, 1.00, 2.00, 3.99),
    ('Bagels', 25.00, 1.00, 1.00, 2.49);
