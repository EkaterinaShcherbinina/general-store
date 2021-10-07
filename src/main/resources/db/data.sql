INSERT INTO product (title, quantity, price) VALUES ('cat toy', '3', '25.0'),
                                                    ('cat food', '2', '35.0');

INSERT INTO user (email, password) VALUES ('test@gmail.com', '$2a$10$EyY5d8es3uJN7H0Rw/yoou8vbMXZKDk2.eBUVz3hnCkaJeaP37xbC');


INSERT INTO orders (orderDate, status, userId) VALUES (CURRENT_TIMESTAMP , 'in_progress', '1');

INSERT INTO order_product (productId, orderId, quantity, price) VALUES (2 , '1', '1', 35.0);

INSERT INTO roles (name) VALUES ('ADMIN'), ('USER');

INSERT INTO users_roles (userId, roleId) VALUES (1, 1);