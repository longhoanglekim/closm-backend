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
                                                                                                    (1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147982/cac-mau-ao-t-shirt-copy_ixklfd.jpg', 'M', 'Red', 50, 'High-quality cotton T-shirt'),
                                                                                                    (1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/sgc13_cb1417b06f2c4de2a4afac376a3c5c22_master_flzg6c.webp', 'L', 'Blue', 30, 'High-quality cotton T-shirt'),
                                                                                                    (1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/ao-t-shirt-nam-loai-tron-mot-mau_hwydbo.webp', 'XL', 'White', 40, 'Breathable cotton T-shirt'),
                                                                                                    (1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/T-Shirt-la-gi-11_lvvhmd.jpg', 'S', 'Black', 20, 'Trendy style T-shirt'),

                                                                                                    -- Variants for Hoodie
                                                                                                    (2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743155560/vn-11134207-7r98o-lks529c5wgxse3_b03ond.jpg', 'M', 'Gray', 20, 'Thick hoodie, excellent for warmth'),
                                                                                                    (2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148505/mat-truoc-ao-hoodie-form-rong-mau-kem-c2e31eca-8c40-46af-a0b3-8f844bdde7e1_n4fcql.webp', 'L', 'Black', 25, 'Thick hoodie, excellent for warmth'),
                                                                                                    (2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148505/C0027D_ryifjz.jpg', 'XL', 'Navy Blue', 30, 'Sporty style hoodie'),
                                                                                                    (2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148504/ao_hoodie_sst_colorblock_djo_iy4859_21_model_7a9de26acfa640a2a1e1a7d36e012c01_master_mdjva1.jpg', 'S', 'Burgundy', 15, 'Oversized hoodie'),

                                                                                                    -- Variants for Socks
                                                                                                    (3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/11892--kem-chi-tiet-1_xwypdn.jpg', 'L', 'Olive Green', 15, 'Long socks for winter warmth'),
                                                                                                    (3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/7cd349af9226463d0f7b278ed4977d1e_rdqiuw.jpg', 'M', 'Black', 18, 'Sweat-absorbing sports socks'),
                                                                                                    (3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/383ff322a8d467b06c35c4295a868802_ssgdye.jpg', 'XL', 'White', 22, 'Comfortable cotton socks'),
                                                                                                    (3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/istockphoto-1292383850-612x612_nbr2d8.jpg', 'S', 'Blue', 12, 'High-quality knit socks'),

                                                                                                    -- Variants for Sweater
                                                                                                    (4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154604/ebd4c49798f79e7aef89eb3776268b92_jkc8yq.jpg', 'M', 'Red', 20, 'Warm turtleneck sweater'),
                                                                                                    (4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154603/ARTIE-basic-sweater-10_umcqz1.jpg', 'L', 'Navy Blue', 15, 'Soft knitted sweater'),
                                                                                                    (4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154602/107cab02-9f68-4980-961c-53e06f89a9bd_cgj675.webp', 'XL', 'White', 25, 'Minimalist round-neck sweater'),
                                                                                                    (4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154602/2d6522be6ebd121fef57910a02533ab6_nfj1ei.jpg', 'S', 'Beige', 18, 'Oversized sweater for comfort'),

                                                                                                    -- Variants for Shorts
                                                                                                    (5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154349/z3393358070033_bc1188db1776db4643142ecf5bd004ad_fe3b82444f984b568246dcfdb05f2dab_master_qm1syz.webp', 'M', 'Beige', 40, 'Trendy kaki shorts'),
                                                                                                    (5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154349/short-jean_mekkjg.webp', 'L', 'Green', 35, 'Comfortable shorts for summer'),
                                                                                                    (5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154348/Sandro_SHPBE00067-30_V_1_1_gstthy.webp', 'XL', 'Black', 25, 'Cooling sports shorts'),
                                                                                                    (5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154347/images_pxtez4.jpg', 'S', 'Brown', 28, 'Beach shorts'),

                                                                                                    -- Variants for Jeans
                                                                                                    (6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154590/Sandro_SFPJE00467-4785_V_1_k8tae3.webp', '32', 'Distressed Blue', 30, 'Streetwear ripped jeans'),
                                                                                                    (6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154590/quan-jeans-nam1_ourhpx.jpg', '34', 'Dark Blue', 25, 'Slim-fit stylish jeans'),
                                                                                                    (6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154589/quan-jean-nam-rach-ta-toi-04_u0zqqn.jpg', '30', 'Light Blue', 20, 'Straight-leg trendy jeans'),
                                                                                                    (6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154588/pvl-z-cac-mau-jean-nam-8_e6ac1f2fc78e4a37990f83a713c58c9f_owlarr.jpg', '28', 'White', 18, 'Basic jeans for easy matching'),

                                                                                                    -- Variants for Winter Pants
                                                                                                    (7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154579/winter-lam-nguoi-mau-cho-new-balance2-696x869_momymg.jpg', 'M', 'Black', 20, 'Thermal pants for winter'),
                                                                                                    (7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154578/images_qb4qfg.jpg', 'L', 'Gray', 15, 'Soft and cozy winter pants'),
                                                                                                    (7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154578/images_2_zawzxf.jpg', 'XL', 'Olive Green', 10, 'Wind-resistant warm pants'),
                                                                                                    (7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154577/images_1_nq9woy.jpg', 'S', 'Brown', 8, 'Lightly lined winter pants');
