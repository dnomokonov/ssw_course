-- Создание таблицы users
CREATE TABLE users (
                       id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);

-- Создание таблицы orders
CREATE TABLE orders (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        user_id BIGINT NOT NULL,
                        total_amount DECIMAL(10,2) NOT NULL
);

-- Создание таблицы products
CREATE TABLE products (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        name VARCHAR(100),
                        price DECIMAL(10,2)
);