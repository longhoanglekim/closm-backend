-- Delete the old database if it exists and create a new one
DROP DATABASE IF EXISTS closm;
CREATE DATABASE closm CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE closm;

-- Products table
CREATE TABLE products (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          category VARCHAR(255) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Product variants table
CREATE TABLE product_variants (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  product_id BIGINT NOT NULL,
                                  price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
                                  image_url VARCHAR(255),
                                  size VARCHAR(25) NOT NULL,
                                  color VARCHAR(30) NOT NULL,
                                  quantity INT NOT NULL CHECK (quantity >= 0),
                                  description TEXT,
                                  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Insert sample products data
INSERT INTO products (name, category) VALUES
                                          ('Basic T-Shirt', 'T-Shirt'),
                                          ('Hoodie', 'Hoodie'),
                                          ('High Socks', 'Socks'),
                                          ('Turtleneck Sweater', 'Sweater'),
                                          ('Kaki Shorts', 'Shorts'),
                                          ('Ripped Jeans', 'Jeans'),
                                          ('Winter Pants', 'Winter Pants');

-- Insert sample product variants
INSERT INTO product_variants (product_id, price, image_url, size, color, quantity, description) VALUES
                                                                                                    -- Variants for T-Shirt
                                                                                                    (1, 199000, 'https://picsum.photos/200', 'M', 'Red', 50, 'High-quality cotton T-shirt'),
                                                                                                    (1, 199000, 'https://picsum.photos/200', 'L', 'Blue', 30, 'High-quality cotton T-shirt'),
                                                                                                    (1, 199000, 'https://picsum.photos/200', 'XL', 'White', 40, 'Breathable cotton T-shirt'),
                                                                                                    (1, 199000, 'https://picsum.photos/200', 'S', 'Black', 20, 'Trendy style T-shirt'),

                                                                                                    -- Variants for Hoodie
                                                                                                    (2, 399000, 'https://picsum.photos/200', 'M', 'Gray', 20, 'Thick hoodie, excellent for warmth'),
                                                                                                    (2, 399000, 'https://picsum.photos/200', 'L', 'Black', 25, 'Thick hoodie, excellent for warmth'),
                                                                                                    (2, 399000, 'https://picsum.photos/200', 'XL', 'Navy Blue', 30, 'Sporty style hoodie'),
                                                                                                    (2, 399000, 'https://picsum.photos/200', 'S', 'Burgundy', 15, 'Oversized hoodie'),

                                                                                                    -- Variants for Socks
                                                                                                    (3, 599000, 'https://picsum.photos/200', 'L', 'Olive Green', 15, 'Long socks for winter warmth'),
                                                                                                    (3, 599000, 'https://picsum.photos/200', 'M', 'Black', 18, 'Sweat-absorbing sports socks'),
                                                                                                    (3, 599000, 'https://picsum.photos/200', 'XL', 'White', 22, 'Comfortable cotton socks'),
                                                                                                    (3, 599000, 'https://picsum.photos/200', 'S', 'Blue', 12, 'High-quality knit socks'),

                                                                                                    -- Variants for Sweater
                                                                                                    (4, 299000, 'https://picsum.photos/200', 'M', 'Red', 20, 'Warm turtleneck sweater'),
                                                                                                    (4, 299000, 'https://picsum.photos/200', 'L', 'Navy Blue', 15, 'Soft knitted sweater'),
                                                                                                    (4, 299000, 'https://picsum.photos/200', 'XL', 'White', 25, 'Minimalist round-neck sweater'),
                                                                                                    (4, 299000, 'https://picsum.photos/200', 'S', 'Beige', 18, 'Oversized sweater for comfort'),

                                                                                                    -- Variants for Shorts
                                                                                                    (5, 199000, 'https://picsum.photos/200', 'M', 'Beige', 40, 'Trendy kaki shorts'),
                                                                                                    (5, 199000, 'https://picsum.photos/200', 'L', 'Green', 35, 'Comfortable shorts for summer'),
                                                                                                    (5, 199000, 'https://picsum.photos/200', 'XL', 'Black', 25, 'Cooling sports shorts'),
                                                                                                    (5, 199000, 'https://picsum.photos/200', 'S', 'Brown', 28, 'Beach shorts'),

                                                                                                    -- Variants for Jeans
                                                                                                    (6, 499000, 'https://picsum.photos/200', '32', 'Distressed Blue', 30, 'Streetwear ripped jeans'),
                                                                                                    (6, 499000, 'https://picsum.photos/200', '34', 'Dark Blue', 25, 'Slim-fit stylish jeans'),
                                                                                                    (6, 499000, 'https://picsum.photos/200', '30', 'Light Blue', 20, 'Straight-leg trendy jeans'),
                                                                                                    (6, 499000, 'https://picsum.photos/200', '28', 'White', 18, 'Basic jeans for easy matching'),

                                                                                                    -- Variants for Winter Pants
                                                                                                    (7, 599000, 'https://picsum.photos/200', 'M', 'Black', 20, 'Thermal pants for winter'),
                                                                                                    (7, 599000, 'https://picsum.photos/200', 'L', 'Gray', 15, 'Soft and cozy winter pants'),
                                                                                                    (7, 599000, 'https://picsum.photos/200', 'XL', 'Olive Green', 10, 'Wind-resistant warm pants'),
                                                                                                    (7, 599000, 'https://picsum.photos/200', 'S', 'Brown', 8, 'Lightly lined winter pants');
