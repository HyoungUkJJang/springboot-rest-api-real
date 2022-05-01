create table products
(
    product_id BINARY(16) PRIMARY KEY,
    product_name VARCHAR(20) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price bigint not null,
    description varchar(500) default null,
    created_at datetime(6) not null,
    updated_at datetime(6) DEFAULT null
);