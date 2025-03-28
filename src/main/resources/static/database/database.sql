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
                                  name varchar(50),
                                  description varchar(255),
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
-- Insert sample product variants with the newly added "name" column
INSERT INTO product_variants (product_id, price, image_url, size, color, quantity, name, description) VALUES
                                                                                                          -- Variants for T-Shirt
                                                                                                          (1, 199000, 'https://picsum.photos/200', 'M', 'Red', 50, 'Basic Red T-Shirt', 'High-quality cotton T-shirt'),
                                                                                                          (1, 199000, 'https://picsum.photos/200', 'L', 'Blue', 30, 'Basic Blue T-Shirt', 'High-quality cotton T-shirt'),
                                                                                                          (1, 199000, 'https://picsum.photos/200', 'XL', 'White', 40, 'Basic White T-Shirt', 'Breathable cotton T-shirt'),
                                                                                                          (1, 199000, 'https://picsum.photos/200', 'S', 'Black', 20, 'Basic Black T-Shirt', 'Trendy style T-shirt'),

                                                                                                          -- Variants for Hoodie
                                                                                                          (2, 399000, 'https://picsum.photos/200', 'M', 'Gray', 20, 'Thick Gray Hoodie', 'Thick hoodie, excellent for warmth'),
                                                                                                          (2, 399000, 'https://picsum.photos/200', 'L', 'Black', 25, 'Thick Black Hoodie', 'Thick hoodie, excellent for warmth'),
                                                                                                          (2, 399000, 'https://picsum.photos/200', 'XL', 'Navy Blue', 30, 'Navy Blue Sporty Hoodie', 'Sporty style hoodie'),
                                                                                                          (2, 399000, 'https://picsum.photos/200', 'S', 'Burgundy', 15, 'Oversized Burgundy Hoodie', 'Oversized hoodie'),

                                                                                                          -- Variants for Socks
                                                                                                          (3, 599000, 'https://picsum.photos/200', 'L', 'Olive Green', 15, 'Olive Green Long Socks', 'Long socks for winter warmth'),
                                                                                                          (3, 599000, 'https://picsum.photos/200', 'M', 'Black', 18, 'Black Sports Socks', 'Sweat-absorbing sports socks'),
                                                                                                          (3, 599000, 'https://picsum.photos/200', 'XL', 'White', 22, 'White Cotton Socks', 'Comfortable cotton socks'),
                                                                                                          (3, 599000, 'https://picsum.photos/200', 'S', 'Blue', 12, 'Blue Knit Socks', 'High-quality knit socks'),

                                                                                                          -- Variants for Sweater
                                                                                                          (4, 299000, 'https://picsum.photos/200', 'M', 'Red', 20, 'Red Turtleneck Sweater', 'Warm turtleneck sweater'),
                                                                                                          (4, 299000, 'https://picsum.photos/200', 'L', 'Navy Blue', 15, 'Navy Blue Sweater', 'Soft knitted sweater'),
                                                                                                          (4, 299000, 'https://picsum.photos/200', 'XL', 'White', 25, 'White Minimalist Sweater', 'Minimalist round-neck sweater'),
                                                                                                          (4, 299000, 'https://picsum.photos/200', 'S', 'Beige', 18, 'Beige Oversized Sweater', 'Oversized sweater for comfort'),

                                                                                                          -- Variants for Shorts
                                                                                                          (5, 199000, 'https://picsum.photos/200', 'M', 'Beige', 40, 'Beige Kaki Shorts', 'Trendy kaki shorts'),
                                                                                                          (5, 199000, 'https://picsum.photos/200', 'L', 'Green', 35, 'Green Summer Shorts', 'Comfortable shorts for summer'),
                                                                                                          (5, 199000, 'https://picsum.photos/200', 'XL', 'Black', 25, 'Black Sports Shorts', 'Cooling sports shorts'),
                                                                                                          (5, 199000, 'https://picsum.photos/200', 'S', 'Brown', 28, 'Brown Beach Shorts', 'Beach shorts'),

                                                                                                          -- Variants for Jeans
                                                                                                          (6, 499000, 'https://picsum.photos/200', '32', 'Distressed Blue', 30, 'Distressed Blue Jeans', 'Streetwear ripped jeans'),
                                                                                                          (6, 499000, 'https://picsum.photos/200', '34', 'Dark Blue', 25, 'Dark Blue Slim-fit Jeans', 'Slim-fit stylish jeans'),
                                                                                                          (6, 499000, 'https://picsum.photos/200', '30', 'Light Blue', 20, 'Light Blue Straight-leg Jeans', 'Straight-leg trendy jeans'),
                                                                                                          (6, 499000, 'https://picsum.photos/200', '28', 'White', 18, 'White Basic Jeans', 'Basic jeans for easy matching'),

                                                                                                          -- Variants for Winter Pants
                                                                                                          (7, 599000, 'https://picsum.photos/200', 'M', 'Black', 20, 'Black Thermal Pants', 'Thermal pants for winter'),
                                                                                                          (7, 599000, 'https://picsum.photos/200', 'L', 'Gray', 15, 'Gray Winter Pants', 'Soft and cozy winter pants'),
                                                                                                          (7, 599000, 'https://picsum.photos/200', 'XL', 'Olive Green', 10, 'Olive Green Wind-resistant Pants', 'Wind-resistant warm pants'),
                                                                                                          (7, 599000, 'https://picsum.photos/200', 'S', 'Brown', 8, 'Brown Lightly-lined Pants', 'Lightly lined winter pants');
