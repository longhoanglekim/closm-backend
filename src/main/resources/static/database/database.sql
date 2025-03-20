DROP DATABASE IF EXISTS closm;
CREATE DATABASE closm;
USE closm;

-- Bảng sản phẩm
CREATE TABLE products (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          category VARCHAR(255) NOT NULL
);

-- Bảng biến thể sản phẩm
CREATE TABLE product_variants (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  product_id BIGINT NOT NULL,
                                  price DECIMAL(10,2) NOT NULL,
                                  image_url VARCHAR(255),
                                  size VARCHAR(25) NOT NULL,
                                  color VARCHAR(30) NOT NULL,
                                  quantity INT NOT NULL,
                                  description VARCHAR(255), -- Chuyển description vào đây
                                  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Bảng đơn hàng
CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        customer_id varchar(255) NOT NULL,
                        order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                        order_status ENUM('Pending', 'Shipped', 'Delivered', 'Cancelled') NOT NULL DEFAULT 'Pending',
                        total_price DECIMAL(10,2) NOT NULL,
                        payment_status ENUM('Paid', 'Unpaid', 'Refunded') NOT NULL DEFAULT 'Unpaid',
                        shipping_address TEXT NOT NULL
);

-- Bảng liên kết đơn hàng - sản phẩm
CREATE TABLE orders_products (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 order_id BIGINT NOT NULL,
                                 product_variant_id BIGINT NOT NULL,
                                 FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                                 FOREIGN KEY (product_variant_id) REFERENCES product_variants(id) ON DELETE CASCADE
);

-- Chèn dữ liệu mẫu cho bảng sản phẩm
INSERT INTO products (id, name, category) VALUES
                                              (1, 'Áo Thun Cơ Bản', 'Áo Thun'),
                                              (2, 'Áo Hoodie Ấm Áp', 'Áo Hoodie'),
                                              (3, 'Quần Jean Slim Fit', 'Quần Jean');

-- Chèn dữ liệu mẫu cho bảng biến thể sản phẩm (đã thêm description)
INSERT INTO product_variants (product_id, price, image_url, size, color, quantity, description) VALUES
                                                                                                    (1, 199000, 'https://example.com/tshirt1.jpg', 'M', 'Đỏ', 50, 'Áo thun cotton chất lượng cao'),
                                                                                                    (1, 199000, 'https://example.com/tshirt2.jpg', 'L', 'Xanh', 30, 'Áo thun cotton chất lượng cao'),
                                                                                                    (2, 399000, 'https://example.com/hoodie1.jpg', 'L', 'Đen', 20, 'Áo hoodie dày dặn, giữ ấm tốt'),
                                                                                                    (3, 499000, 'https://example.com/jeans1.jpg', '32', 'Xanh', 40, 'Quần jean dáng ôm thời trang');

-- Chèn dữ liệu mẫu cho bảng đơn hàng
INSERT INTO orders (customer_id, order_status, total_price, payment_status, shipping_address) VALUES
                                                                                                  (101, 'Pending', 199000, 'Unpaid', '123 Đường Chính, Thành phố A'),
                                                                                                  (102, 'Shipped', 399000, 'Paid', '456 Đường Elm, Thành phố B');

-- Chèn dữ liệu mẫu cho bảng liên kết đơn hàng - sản phẩm
INSERT INTO orders_products (order_id, product_variant_id) VALUES
                                                               (1, 1), -- Đơn hàng 1 chứa Áo Thun Cơ Bản (M, Đỏ)
                                                               (2, 3); -- Đơn hàng 2 chứa Áo Hoodie Ấm Áp (L, Đen)
