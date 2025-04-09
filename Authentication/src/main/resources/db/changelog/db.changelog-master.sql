-- liquibase formatted sql

-- changeset da4a:1
CREATE TABLE customers (
                           id BIGSERIAL PRIMARY KEY NOT NULL,
                           name VARCHAR(255) NOT NULL,
                           city VARCHAR(255),
                           street VARCHAR(255),
                           zipcode VARCHAR(20)
);

-- changeset da4a:2
CREATE TABLE items (
                       id BIGSERIAL PRIMARY KEY NOT NULL,
                       description VARCHAR(255),
                       shipping_weight_value DECIMAL(19,2),
                       shipping_weight_name VARCHAR(50),
                       shipping_weight_symbol VARCHAR(10)
);

-- changeset da4a:3
CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY NOT NULL,
                        date TIMESTAMP NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        customer_id BIGINT NOT NULL,
                        CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- changeset da4a:4
CREATE TABLE order_details (
                               order_id BIGINT NOT NULL,
                               id BIGSERIAL PRIMARY KEY NOT NULL,
                               quantity_value INT,
                               quantity_name VARCHAR(50),
                               quantity_symbol VARCHAR(10),
                               tax_status VARCHAR(50),
                               item_id BIGINT NOT NULL,
                               CONSTRAINT fk_orderdetails_order FOREIGN KEY (order_id) REFERENCES orders(id),
                               CONSTRAINT fk_orderdetails_item FOREIGN KEY (item_id) REFERENCES items(id)
);

-- changeset da4a:5
CREATE TABLE payments (
                          id BIGSERIAL PRIMARY KEY NOT NULL,
                          amount FLOAT NOT NULL,
                          order_id BIGINT NOT NULL UNIQUE,
                          payment_type VARCHAR(20) NOT NULL,
                          payment_status VARCHAR(20) NOT NULL,
                          CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- changeset da4a:6
CREATE TABLE cash_payments (
                               id BIGINT PRIMARY KEY NOT NULL,
                               cash_tendered FLOAT NOT NULL,
                               CONSTRAINT fk_cash_payment FOREIGN KEY (id) REFERENCES payments(id)
);

-- changeset da4a:7
CREATE TABLE check_payments (
                                id BIGINT PRIMARY KEY NOT NULL,
                                name VARCHAR(255) NOT NULL,
                                bank_id VARCHAR(50) NOT NULL,
                                CONSTRAINT fk_check_payment FOREIGN KEY (id) REFERENCES payments(id)
);

-- changeset da4a:8
CREATE TABLE credit_payments (
                                 id BIGINT PRIMARY KEY NOT NULL,
                                 number VARCHAR(50) NOT NULL,
                                 type VARCHAR(20) NOT NULL,
                                 exp_date TIMESTAMP NOT NULL,
                                 CONSTRAINT fk_credit_payment FOREIGN KEY (id) REFERENCES payments(id)
);

-- changeset da4a:9
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY NOT NULL,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL,
                       customer_id BIGINT,
                       CONSTRAINT fk_user_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- changeset da4a:10
CREATE TABLE auth_tokens (
                             id BIGSERIAL PRIMARY KEY NOT NULL,
                             token VARCHAR(255) NOT NULL UNIQUE,
                             expiry_date TIMESTAMP NOT NULL,
                             user_id BIGINT NOT NULL,
                             CONSTRAINT fk_token_user FOREIGN KEY (user_id) REFERENCES users(id)
);