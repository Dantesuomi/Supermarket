CREATE DATABASE IF NOT EXISTS supermarket;

USE supermarket;

CREATE TABLE IF NOT EXISTS customers(
	id int auto_increment primary key, -- autofill id column
    name varchar(100),
    email varchar(100),
    password_hash varchar(100),
    balance decimal(10,2)
);

CREATE TABLE IF NOT EXISTS sales_managers(
	id int auto_increment primary key, -- autofill id column
    name varchar(100),
    email varchar(100),
    password_hash varchar(100),
    balance decimal(10,2)
);

CREATE TABLE IF NOT EXISTS products(
	id int auto_increment primary key, -- autofill id column
    name varchar(100),
    available_quantity decimal(10,2),
    item_quantity decimal(10,2),
    purchase_price decimal(10,2),
    retail_price decimal(10,2)
);

CREATE TABLE IF NOT EXISTS purchase_history(
	id int auto_increment primary key, -- autofill id column
    customer_id int,
    product_id int,
    amount_sold decimal(10,2),
    total decimal(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
