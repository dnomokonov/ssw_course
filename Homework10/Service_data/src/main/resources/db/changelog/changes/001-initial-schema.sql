-- Создание таблицы orders
CREATE TABLE orders (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        user_sub VARCHAR(255) NOT NULL, -- Изменено на строковое поле
                        total_amount DECIMAL(10,2) NOT NULL
);

-- Создание таблицы products
CREATE TABLE products (
                          id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                          name VARCHAR(100),
                          price DECIMAL(10,2)
);