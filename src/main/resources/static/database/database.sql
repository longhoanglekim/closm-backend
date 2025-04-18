-- Delete the old database if it exists and create a new one
DROP DATABASE IF EXISTS closm;
CREATE DATABASE closm CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE closm;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255) UNIQUE NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- Products table
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        order_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                        order_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                        items_total_price DECIMAL(19, 2),
                        discount_amount DECIMAL(19, 2),
                        deliver_payment DECIMAL(19, 2),
                        final_price DECIMAL(19, 2),
                        payment_status VARCHAR(50) NOT NULL DEFAULT 'UNPAID',
                        shipping_address TEXT,

                        CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES users(id)
);


-- Product variants table
CREATE TABLE product_variants (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    image_url VARCHAR(255),
    size VARCHAR(25) NOT NULL,
    color VARCHAR(30) NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0),
    tag VARCHAR(50),
    description VARCHAR(255),
    order_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE TABLE orders_variants (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 order_id BIGINT NOT NULL,
                                 product_variant_id BIGINT NOT NULL,

                                 CONSTRAINT fk_ov_order FOREIGN KEY (order_id) REFERENCES orders(id),
                                 CONSTRAINT fk_ov_product_variant FOREIGN KEY (product_variant_id) REFERENCES product_variants(id)
);
CREATE TABLE discounts (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           description VARCHAR(255) NOT NULL,
                           discount_percentage DECIMAL(5, 2) NOT NULL,  -- precision = 5, scale = 2
                           discount_type VARCHAR(255) NOT NULL,  -- Assuming DiscountType is stored as a string
                           start_date DATE NOT NULL,
                           end_date DATE NOT NULL
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

-- Insert sample product variants with updated tags including category

INSERT INTO product_variants (product_id, price, image_url, size, color, quantity, tag, description) VALUES
-- T-Shirts (product_id = 1, category = T-Shirt)
(1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147982/cac-mau-ao-t-shirt-copy_ixklfd.jpg', 'M', 'Red', 50, 'Basic T-Shirt', 'High-quality cotton T-shirt'),
(1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/sgc13_cb1417b06f2c4de2a4afac376a3c5c22_master_flzg6c.webp', 'L', 'Blue', 30, 'Basic T-Shirt', 'High-quality cotton T-shirt'),
(1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/ao-t-shirt-nam-loai-tron-mot-mau_hwydbo.webp', 'XL', 'White', 40, 'Basic T-Shirt', 'Breathable cotton T-shirt'),
(1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/T-Shirt-la-gi-11_lvvhmd.jpg', 'S', 'Black', 20, 'Basic T-Shirt', 'Trendy style T-shirt'),
(1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147982/cac-mau-ao-t-shirt-copy_ixklfd.jpg', 'M', 'Red', 50, 'Classic T-Shirt', 'High-quality cotton T-shirt with classic fit'),
(1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/sgc13_cb1417b06f2c4de2a4afac376a3c5c22_master_flzg6c.webp', 'L', 'Blue', 30, 'Daily T-Shirt', 'Perfect T-shirt for everyday wear'),
(1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/ao-t-shirt-nam-loai-tron-mot-mau_hwydbo.webp', 'XL', 'White', 40, 'Comfort T-Shirt', 'Soft and breathable cotton T-shirt'),
(1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/T-Shirt-la-gi-11_lvvhmd.jpg', 'S', 'Black', 20, 'Casual T-Shirt', 'Simple design, ideal for casual looks'),
(1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/summer-tshirt_zxc123.webp', 'M', 'Gray', 28, 'Urban T-Shirt', 'Trendy T-shirt for street style'),
(1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/unisex-tshirt_asd789.webp', 'L', 'Navy', 36, 'Soft T-Shirt', 'Made from extra soft cotton for comfort'),

-- Hoodies (product_id = 2, category = Hoodie)
(2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743155560/vn-11134207-7r98o-lks529c5wgxse3_b03ond.jpg', 'M', 'Gray', 20, 'Thick Hoodie', 'Thick hoodie, excellent for warmth'),
(2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148505/mat-truoc-ao-hoodie-form-rong-mau-kem-c2e31eca-8c40-46af-a0b3-8f844bdde7e1_n4fcql.webp', 'L', 'Black', 25, 'Thick Hoodie', 'Thick hoodie, excellent for warmth'),
(2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148505/C0027D_ryifjz.jpg', 'XL', 'Navy Blue', 30, 'Sporty Hoodie', 'Sporty style hoodie'),
(2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148504/ao_hoodie_sst_colorblock_djo_iy4859_21_model_7a9de26acfa640a2a1e1a7d36e012c01_master_mdjva1.jpg', 'S', 'Burgundy', 15, 'Oversized Hoodie', 'Oversized hoodie'),

-- Socks (product_id = 3, category = Socks)
(3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/11892--kem-chi-tiet-1_xwypdn.jpg', 'L', 'Olive Green', 15, 'Long Socks', 'Long socks for winter warmth'),
(3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/7cd349af9226463d0f7b278ed4977d1e_rdqiuw.jpg', 'M', 'Black', 18, 'Sports Socks', 'Sweat-absorbing sports socks'),
(3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/383ff322a8d467b06c35c4295a868802_ssgdye.jpg', 'XL', 'White', 22, 'Cotton Socks', 'Comfortable cotton socks'),
(3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/istockphoto-1292383850-612x612_nbr2d8.jpg', 'S', 'Blue', 12, 'Knit Socks', 'High-quality knit socks'),

-- Sweaters (product_id = 4, category = Sweater)
(4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154604/ebd4c49798f79e7aef89eb3776268b92_jkc8yq.jpg', 'M', 'Red', 20, 'Turtleneck Sweater', 'Warm turtleneck sweater'),
(4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154603/ARTIE-basic-sweater-10_umcqz1.jpg', 'L', 'Navy Blue', 15, 'Sweater Sweater', 'Soft knitted sweater'),
(4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154602/107cab02-9f68-4980-961c-53e06f89a9bd_cgj675.webp', 'XL', 'White', 25, 'Minimalist Sweater', 'Minimalist round-neck sweater'),
(4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154602/2d6522be6ebd121fef57910a02533ab6_nfj1ei.jpg', 'S', 'Beige', 18, 'Oversized Sweater', 'Oversized sweater for comfort'),

-- Shorts (product_id = 5, category = Shorts)
(5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154349/z3393358070033_bc1188db1776db4643142ecf5bd004ad_fe3b82444f984b568246dcfdb05f2dab_master_qm1syz.webp', 'M', 'Beige', 40, 'Kaki Shorts', 'Trendy kaki shorts'),
(5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154349/short-jean_mekkjg.webp', 'L', 'Green', 35, 'Summer Shorts', 'Comfortable shorts for summer'),
(5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154348/Sandro_SHPBE00067-30_V_1_1_gstthy.webp', 'XL', 'Black', 25, 'Sports Shorts', 'Cooling sports shorts'),
(5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154347/images_pxtez4.jpg', 'S', 'Brown', 28, 'Beach Shorts', 'Beach shorts'),

-- Jeans (product_id = 6, category = Jeans)
(6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154590/Sandro_SFPJE00467-4785_V_1_k8tae3.webp', '32', 'Distressed Blue', 30, 'Jeans Jeans', 'Streetwear ripped jeans'),
(6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154590/quan-jeans-nam1_ourhpx.jpg', '34', 'Dark Blue', 25, 'Slim-fit Jeans', 'Slim-fit stylish jeans'),
(6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154589/quan-jean-nam-rach-ta-toi-04_u0zqqn.jpg', '30', 'Light Blue', 20, 'Straight-leg Jeans', 'Straight-leg trendy jeans'),
(6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154588/pvl-z-cac-mau-jean-nam-8_e6ac1f2fc78e4a37990f83a713c58c9f_owlarr.jpg', '28', 'White', 18, 'Basic Jeans', 'Basic jeans for easy matching'),

-- Winter Pants (product_id = 7, category = Winter Pants)
(7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154579/winter-lam-nguoi-mau-cho-new-balance2-696x869_momymg.jpg', 'M', 'Black', 20, 'Thermal Winter Pants', 'Thermal pants for winter'),
(7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154578/images_qb4qfg.jpg', 'L', 'Gray', 15, 'Winter Winter Pants', 'Soft and cozy winter pants'),
(7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154578/images_2_zawzxf.jpg', 'XL', 'Olive Green', 10, 'Wind-resistant Winter Pants', 'Wind-resistant warm pants'),
(7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154577/images_1_nq9woy.jpg', 'S', 'Brown', 8, 'Lightly-lined Winter Pants', 'Lightly lined winter pants');

--- INSERT DATA INTO DISCOUNT

INSERT INTO discounts (description, discount_percentage, discount_type, start_date, end_date)
VALUES
    ('Spring Sale', 10.00, 'HOLIDAY', '2025-04-17', '2025-05-10'),
    ('Flash Sale', 30.00, 'HOLIDAY', '2025-04-17', '2025-06-01'),
    ('Summer Kickoff', 15.00, 'HOLIDAY', '2025-04-17', '2025-06-05'),
    ('Holiday Discount', 20.00, 'HOLIDAY', '2025-04-17', '2025-05-25'),
    ('Early Bird Special', 25.00, 'HOLIDAY', '2025-04-17', '2025-06-20'),
    ('Weekend Deals', 12.50, 'HOLIDAY', '2025-04-17', '2025-06-15'),
    ('Exclusive Offer', 18.00, 'HOLIDAY', '2025-04-17', '2025-06-10'),
    ('Mid-Year Sale', 22.00, 'HOLIDAY', '2025-04-17', '2025-06-30'),
    ('Spring Frenzy', 17.50, 'HOLIDAY', '2025-04-17', '2025-06-20'),
    ('End of Season', 14.00, 'HOLIDAY', '2025-04-17', '2025-06-25');
