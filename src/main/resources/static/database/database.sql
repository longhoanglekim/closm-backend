DROP DATABASE IF EXISTS closm;
CREATE DATABASE closm;
USE closm;

CREATE TABLE products (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          description VARCHAR(255),
                          category VARCHAR(255) NOT NULL,
                          price DECIMAL(10,2) NOT NULL,
                          image_url VARCHAR(255) NOT NULL,
                          size VARCHAR(10) NOT NULL,
                          color VARCHAR(25) NOT NULL,
                          stock INT NOT NULL
);

CREATE TABLE orders (
                        id bigint AUTO_INCREMENT PRIMARY KEY,
                        customer_id INT NOT NULL,
                        order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                        order_status ENUM('Pending', 'Shipped', 'Delivered', 'Cancelled') NOT NULL DEFAULT 'Pending',
                        total_price DECIMAL(10,2) NOT NULL,
                        payment_status ENUM('Paid', 'Unpaid', 'Refunded') NOT NULL DEFAULT 'Unpaid',
                        shipping_address TEXT NOT NULL
);

CREATE TABLE orders_products (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 order_id BIGINT NOT NULL,
                                 product_id BIGINT NOT NULL,
                                 FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                                 FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);
