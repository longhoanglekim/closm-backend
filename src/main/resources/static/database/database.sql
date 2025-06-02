-- 0. Xóa và tạo lại database closm
DROP DATABASE IF EXISTS closm;
CREATE DATABASE closm
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE closm;

-- 1. Tạo bảng users
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       full_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(255) UNIQUE NOT NULL,
                       role VARCHAR(255) not null default 'ROLE_USER'
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. Tạo bảng base_products
CREATE TABLE base_products (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               category VARCHAR(255) NOT NULL,
                               image_url varchar(255) not null
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE payment_method (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                method varchar(255) not null unique
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
INSERT INTO payment_method values (1, 'CASH'),
                                  (2, 'BANK_TRANSFER'),
                                  (3, 'MOMO'),
                                  (4, 'VNPAY');
-- 3. Tạo bảng orders

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        order_code VARCHAR(255) NOT NULL,
                        order_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                        discount_amount DECIMAL(19,2),
                        deliver_payment DECIMAL(19,2),
                        final_price DECIMAL(19,2),
                        payment_status VARCHAR(50) NOT NULL DEFAULT 'UNPAID',
                        payment_method_id bigint NOT NULL DEFAULT 1,
                        deliver_address TEXT,
                        cancelable_date DATE NOT NULL,
                        CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES users(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE product_items (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               base_product_id BIGINT NOT NULL,
                               price DECIMAL(10,2) NOT NULL,
                               image_url VARCHAR(255),
                               size VARCHAR(25) NOT NULL,
                               color VARCHAR(30) NOT NULL,
                               quantity INT NOT NULL,
                               tag VARCHAR(50),
                               description VARCHAR(255),
                               order_id BIGINT,
                               FOREIGN KEY (base_product_id) REFERENCES base_products(id) ON DELETE CASCADE,
                               FOREIGN KEY (order_id)       REFERENCES orders(id)        ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE orders_items (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              order_id BIGINT NOT NULL,
                              product_item_id BIGINT NOT NULL,
                              quantity INT NOT NULL,
                              CONSTRAINT fk_ov_order        FOREIGN KEY (order_id)        REFERENCES orders(id),
                              CONSTRAINT fk_ov_product_item FOREIGN KEY (product_item_id) REFERENCES product_items(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 6. Tạo bảng discounts
CREATE TABLE discounts (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           discount_percentage DECIMAL(5,2) NOT NULL,
                           discount_type VARCHAR(255) NOT NULL,
                           start_date DATE NOT NULL,
                           image_url varchar(255) not null,
                           end_date   DATE NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 7. Chèn dữ liệu mẫu vào discounts
INSERT INTO discounts (id,name, discount_percentage, discount_type, start_date, end_date, image_url) VALUES
                                                                                                         (1,'Spring Sale', 10.00, 'HOLIDAY', '2025-04-17', '2025-05-10', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbHcMwgO7An5UjnJu9WMqgA2yOZwwq-DmIzQ&s'),
                                                                                                         (2,'Flash Sale', 30.00, 'HOLIDAY', '2025-04-17', '2025-06-01', 'https://img.freepik.com/premium-vector/discounts-30-percent-off_573652-2667.jpg'),
                                                                                                         (3,'Summer Kickoff',  15.00, 'HOLIDAY', '2025-04-17', '2025-06-05','https://file.hstatic.net/200000489263/article/21-3107_sieu_sale_20__160x60__1140___788_px___3__d4a74fa6d89a44cdbcf833d72f7d8382.png'),
                                                                                                         (4,'Christmas Sale', 20.00, 'HOLIDAY', '2025-04-17', '2025-05-25', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQg5W23BMRoMiNNmxlSZORLYya676OFn4PnYA&s'),
                                                                                                         (5,'Early Bird Special',25.00,'HOLIDAY','2025-04-17', '2025-06-20','https://www.shutterstock.com/image-vector/early-bird-offer-vector-icon-260nw-2357846379.jpg'),
                                                                                                         (6,'Weekend Deals',     12.50, 'HOLIDAY', '2025-04-17', '2025-06-15', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHcwhgLv6Ydg-gXJ9ueiOK5_sYSAhMvT9aNA&s'),
                                                                                                         (7,'Mid-Year Sale',     22.00, 'HOLIDAY', '2025-04-17', '2025-06-30', 'https://static.vecteezy.com/system/resources/previews/006/897/064/non_2x/mid-year-sale-poster-free-vector.jpg'),
                                                                                                         (8,'Spring Frenzy',     17.50, 'HOLIDAY', '2025-04-17', '2025-06-20', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmLHjrKBxPJeEtzPtlAKAzK-zSOYr3AFZYIg&s');


-- 8. Chèn dữ liệu mẫu users
INSERT INTO users (id,full_name, email, password, phone, role) VALUES
                                                                   (1,'DungBeo', 'dung@gmail.com', '$2a$10$P/JowkpBHojSdwce5pKtfufi4OVMaYl1XisH.Kk8hFna60DSKQs4W', '0901234567', 'ROLE_USER'),
                                                                   (2,'LongHoang', 'long@gmail.com', '$2a$10$Cldi2H9mNhRfOe/AVsNgxOSi0JUSpQkr8jfNCr2h5efGF.lZjfuKq', '0901234561', 'ROLE_ADMIN');

-- 9. Chèn dữ liệu mẫu base_products
INSERT INTO base_products (id,category, image_url) VALUES
                                                       (1, 'T-Shirt','https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147982/cac-mau-ao-t-shirt-copy_ixklfd.jpg'),
                                                       (2,'Hoodie', 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743155560/vn-11134207-7r98o-lks529c5wgxse3_b03ond.jpg'),
                                                       (3,'Socks', 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/11892--kem-chi-tiet-1_xwypdn.jpg'),
                                                       (4,'Sweater','https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154604/ebd4c49798f79e7aef89eb3776268b92_jkc8yq.jpg'),
                                                       (5,'Shorts','https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154349/z3393358070033_bc1188db1776db4643142ecf5bd004ad_fe3b82444f984b568246dcfdb05f2dab_master_qm1syz.webp'),
                                                       (6,'Jeans','https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154590/Sandro_SFPJE00467-4785_V_1_k8tae3.webp'),
                                                       (7, 'Winter Pants','https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154579/winter-lam-nguoi-mau-cho-new-balance2-696x869_momymg.jpg');

-- 10. Chèn dữ liệu mẫu product_items (full)
INSERT INTO product_items (id,base_product_id, price, image_url, size, color, quantity, tag, description) VALUES
                                                                                                              -- T-Shirts (product_id = 1, category = T-Shirt)
                                                                                                              (1,1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147982/cac-mau-ao-t-shirt-copy_ixklfd.jpg', 'M', 'Red', 50, 'Basic T-Shirt', 'High-quality cotton T-shirt'),
                                                                                                              (2,1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/sgc13_cb1417b06f2c4de2a4afac376a3c5c22_master_flzg6c.webp', 'L', 'Blue', 30, 'Basic T-Shirt', 'High-quality cotton T-shirt'),
                                                                                                              (3,1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/ao-t-shirt-nam-loai-tron-mot-mau_hwydbo.webp', 'XL', 'White', 40, 'Basic T-Shirt', 'Breathable cotton T-shirt'),
                                                                                                              (4,1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/T-Shirt-la-gi-11_lvvhmd.jpg', 'S', 'Black', 20, 'Basic T-Shirt', 'Trendy style T-shirt'),
                                                                                                              (5,1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147982/cac-mau-ao-t-shirt-copy_ixklfd.jpg', 'M', 'Red', 50, 'Classic T-Shirt', 'High-quality cotton T-shirt with classic fit'),
                                                                                                              (6,1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/sgc13_cb1417b06f2c4de2a4afac376a3c5c22_master_flzg6c.webp', 'L', 'Blue', 30, 'Daily T-Shirt', 'Perfect T-shirt for everyday wear'),
                                                                                                              (7,1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/ao-t-shirt-nam-loai-tron-mot-mau_hwydbo.webp', 'XL', 'White', 40, 'Comfort T-Shirt', 'Soft and breathable cotton T-shirt'),
                                                                                                              (8,1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/T-Shirt-la-gi-11_lvvhmd.jpg', 'S', 'Black', 20, 'Casual T-Shirt', 'Simple design, ideal for casual looks'),
                                                                                                              (9,1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/summer-tshirt_zxc123.webp', 'M', 'Gray', 28, 'Urban T-Shirt', 'Trendy T-shirt for street style'),
                                                                                                              (10,1, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743147981/unisex-tshirt_asd789.webp', 'L', 'Navy', 36, 'Soft T-Shirt', 'Made from extra soft cotton for comfort'),

                                                                                                              (11,2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743155560/vn-11134207-7r98o-lks529c5wgxse3_b03ond.jpg', 'M', 'Gray', 20, 'Thick Hoodie', 'Thick hoodie, excellent for warmth'),
                                                                                                              (12,2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148505/mat-truoc-ao-hoodie-form-rong-mau-kem-c2e31eca-8c40-46af-a0b3-8f844bdde7e1_n4fcql.webp', 'L', 'Black', 25, 'Thick Hoodie', 'Thick hoodie, excellent for warmth'),
                                                                                                              (13,2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148505/C0027D_ryifjz.jpg', 'XL', 'Navy Blue', 30, 'Sporty Hoodie', 'Sporty style hoodie'),
                                                                                                              (14,2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148504/ao_hoodie_sst_colorblock_djo_iy4859_21_model_7a9de26acfa640a2a1e1a7d36e012c01_master_mdjva1.jpg', 'S', 'Burgundy', 15, 'Oversized Hoodie', 'Oversized hoodie'),

                                                                                                              (15,3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/11892--kem-chi-tiet-1_xwypdn.jpg', 'L', 'Olive Green', 15, 'Long Socks', 'Long socks for winter warmth'),
                                                                                                              (16,3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/7cd349af9226463d0f7b278ed4977d1e_rdqiuw.jpg', 'M', 'Black', 18, 'Sports Socks', 'Sweat-absorbing sports socks'),
                                                                                                              (17,3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/383ff322a8d467b06c35c4295a868802_ssgdye.jpg', 'XL', 'White', 22, 'Cotton Socks', 'Comfortable cotton socks'),
                                                                                                              (18,3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154009/istockphoto-1292383850-612x612_nbr2d8.jpg', 'S', 'Blue', 12, 'Knit Socks', 'High-quality knit socks'),

                                                                                                              (19,4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154604/ebd4c49798f79e7aef89eb3776268b92_jkc8yq.jpg', 'M', 'Red', 20, 'Turtleneck Sweater', 'Warm turtleneck sweater'),
                                                                                                              (20,4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154603/ARTIE-basic-sweater-10_umcqz1.jpg', 'L', 'Navy Blue', 15, 'Sweater Sweater', 'Soft knitted sweater'),
                                                                                                              (21,4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154602/107cab02-9f68-4980-961c-53e06f89a9bd_cgj675.webp', 'XL', 'White', 25, 'Minimalist Sweater', 'Minimalist round-neck sweater'),
                                                                                                              (22,4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154602/2d6522be6ebd121fef57910a02533ab6_nfj1ei.jpg', 'S', 'Beige', 18, 'Oversized Sweater', 'Oversized sweater for comfort'),

                                                                                                              (23,5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154349/z3393358070033_bc1188db1776db4643142ecf5bd004ad_fe3b82444f984b568246dcfdb05f2dab_master_qm1syz.webp', 'M', 'Beige', 40, 'Kaki Shorts', 'Trendy kaki shorts'),
                                                                                                              (24,5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154349/short-jean_mekkjg.webp', 'L', 'Green', 35, 'Summer Shorts', 'Comfortable shorts for summer'),
                                                                                                              (25,5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154349/short-jean_mekkjg.webp', 'XL', 'Green', 35, 'Summer Shorts', 'Comfortable shorts for summer'),
                                                                                                              (26,5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154348/Sandro_SHPBE00067-30_V_1_1_gstthy.webp', 'XL', 'Black', 25, 'Sports Shorts', 'Cooling sports shorts'),
                                                                                                              (27,5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154347/images_pxtez4.jpg', 'S', 'Brown', 28, 'Beach Shorts', 'Beach shorts'),

                                                                                                              (28,6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154590/Sandro_SFPJE00467-4785_V_1_k8tae3.webp', '32', 'Distressed Blue', 30, 'Jeans Jeans', 'Streetwear ripped jeans'),
                                                                                                              (29,6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154590/quan-jeans-nam1_ourhpx.jpg', '34', 'Dark Blue', 25, 'Slim-fit Jeans', 'Slim-fit stylish jeans'),
                                                                                                              (30,6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154589/quan-jean-nam-rach-ta-toi-04_u0zqqn.jpg', '30', 'Light Blue', 20, 'Straight-leg Jeans', 'Straight-leg trendy jeans'),
                                                                                                              (31,6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154588/pvl-z-cac-mau-jean-nam-8_e6ac1f2fc78e4a37990f83a713c58c9f_owlarr.jpg', '28', 'White', 18, 'Basic Jeans', 'Basic jeans for easy matching'),

                                                                                                              (32,7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154579/winter-lam-nguoi-mau-cho-new-balance2-696x869_momymg.jpg', 'M', 'Black', 20, 'Thermal Winter Pants', 'Thermal pants for winter'),
                                                                                                              (33,7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154578/images_qb4qfg.jpg', 'L', 'Gray', 15, 'Winter Winter Pants', 'Soft and cozy winter pants'),
                                                                                                              (34,7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154578/images_2_zawzxf.jpg', 'XL', 'Olive Green', 10, 'Wind-resistant Winter Pants', 'Wind-resistant warm pants'),
                                                                                                              (35,7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154577/images_1_nq9woy.jpg', 'S', 'Brown', 8, 'Lightly-lined Winter Pants', 'Lightly lined winter pants');
                                                                                                              (36, 2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148505/hoodie_red_model.jpg', 'M', 'Red', 22, 'Classic Hoodie', 'Classic style hoodie'),
    (37, 2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148506/hoodie_white_model.jpg', 'L', 'White', 20, 'Casual Hoodie', 'Casual everyday hoodie'),
    (38, 2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148506/hoodie_green_model.jpg', 'XL', 'Green', 18, 'Sport Hoodie', 'Lightweight sport hoodie'),
    (39, 2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148506/hoodie_blue_model.jpg', 'S', 'Blue', 17, 'Light Hoodie', 'Perfect for mild weather'),
    (40, 2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148507/hoodie_grey_model.jpg', 'M', 'Gray', 21, 'Urban Hoodie', 'Modern urban style'),
    (41, 2, 399000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743148507/hoodie_black_model.jpg', 'L', 'Black', 24, 'Premium Hoodie', 'Soft and warm premium hoodie');

    (42, 3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154010/socks_red_model.jpg', 'M', 'Red', 15, 'Running Socks', 'Comfortable socks for running'),
    (43, 3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154010/socks_white_model.jpg', 'L', 'White', 16, 'Casual Socks', 'Perfect for daily wear'),
    (44, 3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154011/socks_blue_model.jpg', 'XL', 'Blue', 12, 'Winter Socks', 'Warm socks for winter'),
    (45, 3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154011/socks_black_model.jpg', 'S', 'Black', 10, 'Sports Socks', 'Breathable sports socks'),
    (46, 3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154011/socks_gray_model.jpg', 'M', 'Gray', 14, 'Everyday Socks', 'Durable and comfortable'),
    (47, 3, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154012/socks_green_model.jpg', 'L', 'Green', 13, 'Fashion Socks', 'Trendy design socks');

    (48, 4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154605/sweater_red_model.jpg', 'M', 'Red', 20, 'Knitted Sweater', 'Warm knitted sweater'),
    (49, 4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154606/sweater_black_model.jpg', 'L', 'Black', 18, 'Classic Sweater', 'Classic and cozy'),
    (50, 4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154607/sweater_beige_model.jpg', 'XL', 'Beige', 22, 'Comfort Sweater', 'Soft and warm'),
    (51, 4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154607/sweater_gray_model.jpg', 'S', 'Gray', 16, 'Casual Sweater', 'Casual and stylish'),
    (52, 4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154608/sweater_blue_model.jpg', 'M', 'Blue', 15, 'Fashion Sweater', 'Trendy fashion sweater'),
    (53, 4, 299000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154609/sweater_white_model.jpg', 'L', 'White', 17, 'Minimal Sweater', 'Minimalist style sweater');

    (54, 5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154350/shorts_black_model.jpg', 'M', 'Black', 40, 'Casual Shorts', 'Comfortable casual shorts'),
    (55, 5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154351/shorts_beige_model.jpg', 'L', 'Beige', 35, 'Beach Shorts', 'Lightweight beach shorts'),
    (56, 5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154351/shorts_green_model.jpg', 'XL', 'Green', 30, 'Sport Shorts', 'Cool and breathable'),
    (57, 5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154352/shorts_gray_model.jpg', 'S', 'Gray', 28, 'Summer Shorts', 'Perfect for summer'),
    (58, 5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154352/shorts_blue_model.jpg', 'M', 'Blue', 25, 'Everyday Shorts', 'Durable everyday wear'),
    (59, 5, 199000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154353/shorts_navy_model.jpg', 'L', 'Navy', 20, 'Fashion Shorts', 'Trendy and stylish');

    (60, 6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154591/jeans_black_model.jpg', '32', 'Black', 25, 'Classic Jeans', 'Timeless style jeans'),
    (61, 6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154592/jeans_blue_model.jpg', '34', 'Blue', 30, 'Casual Jeans', 'Everyday casual jeans'),
    (62, 6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154593/jeans_light_blue_model.jpg', '30', 'Light Blue', 20, 'Light Jeans', 'Lightweight and comfortable'),
    (63, 6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154594/jeans_white_model.jpg', '28', 'White', 18, 'Fashion Jeans', 'Trendy white jeans'),
    (64, 6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154595/jeans_grey_model.jpg', '36', 'Gray', 22, 'Slim Fit Jeans', 'Slim fit and stylish'),
    (65, 6, 499000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154596/jeans_dark_blue_model.jpg', '38', 'Dark Blue', 15, 'Dark Denim', 'Deep color denim jeans');

    (66, 7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154580/winter_pants_blue_model.jpg', 'M', 'Blue', 18, 'Heavy Winter Pants', 'Extra warm for cold weather'),
    (67, 7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154581/winter_pants_black_model.jpg', 'L', 'Black', 22, 'Waterproof Pants', 'Waterproof and warm'),
    (68, 7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154582/winter_pants_gray_model.jpg', 'XL', 'Gray', 14, 'Insulated Pants', 'Well insulated for winter'),
    (69, 7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154583/winter_pants_brown_model.jpg', 'S', 'Brown', 12, 'Light Winter Pants', 'Lightweight for mild winters'),
    (70, 7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154584/winter_pants_olive_model.jpg', 'M', 'Olive', 10, 'Windproof Pants', 'Windproof and cozy'),
    (71, 7, 599000, 'https://res.cloudinary.com/dwddrjz3b/image/upload/v1743154585/winter_pants_navy_model.jpg', 'L', 'Navy', 8, 'Thermal Pants', 'Thermal lined for extra warmth');


-- Thêm vào bảng orders
INSERT INTO orders (user_id, order_code, order_date, order_status, discount_amount, deliver_payment, final_price, payment_status, payment_method_id, deliver_address, cancelable_date)
VALUES
    (1, '20250501100000', NOW(), 'PENDING', 50000, 20000, 550000, 'UNPAID', 1, '123 Main St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250502110000', NOW(), 'COMPLETED', 0, 15000, 400000, 'PAID', 2, '456 Elm St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250503123000', NOW(), 'DELIVERED', 10000, 25000, 620000, 'PAID', 1, '789 Oak St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250504100000', NOW(), 'PENDING', 0, 20000, 480000, 'UNPAID', 1, '321 Pine St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250505153000', NOW(), 'CANCELLED', 20000, 30000, 530000, 'UNPAID', 2, '654 Maple St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250506120000', NOW(), 'SHIPPED', 0, 10000, 360000, 'PAID', 1, '987 Birch St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250507140000', NOW(), 'PROCESSING', 15000, 15000, 410000, 'UNPAID', 2, '123 Main St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250508113000', NOW(), 'DELIVERED', 0, 20000, 450000, 'PAID', 1, '456 Elm St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250509100000', NOW(), 'PENDING', 10000, 25000, 500000, 'UNPAID', 2, '789 Oak St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250510150000', NOW(), 'COMPLETED', 0, 10000, 380000, 'PAID', 1, '321 Pine St', CURDATE() + INTERVAL 7 DAY),

    (1, '20250511120000', NOW(), 'PENDING', 5000, 20000, 420000, 'UNPAID', 1, '654 Maple St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250512133000', NOW(), 'SHIPPED', 0, 25000, 460000, 'PAID', 2, '987 Birch St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250513100000', NOW(), 'DELIVERED', 20000, 15000, 480000, 'PAID', 1, '123 Main St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250514143000', NOW(), 'PENDING', 0, 20000, 430000, 'UNPAID', 2, '456 Elm St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250515120000', NOW(), 'CANCELLED', 10000, 10000, 390000, 'UNPAID', 1, '789 Oak St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250516100000', NOW(), 'PROCESSING', 0, 15000, 440000, 'PAID', 2, '321 Pine St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250517143000', NOW(), 'DELIVERED', 5000, 20000, 470000, 'PAID', 1, '654 Maple St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250518120000', NOW(), 'PENDING', 0, 25000, 410000, 'UNPAID', 2, '987 Birch St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250519100000', NOW(), 'COMPLETED', 10000, 10000, 460000, 'PAID', 1, '123 Main St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250520143000', NOW(), 'SHIPPED', 0, 15000, 430000, 'PAID', 2, '456 Elm St', CURDATE() + INTERVAL 7 DAY),

    (1, '20250521120000', NOW(), 'PENDING', 5000, 20000, 440000, 'UNPAID', 1, '789 Oak St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250522100000', NOW(), 'DELIVERED', 0, 10000, 480000, 'PAID', 2, '321 Pine St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250523143000', NOW(), 'PROCESSING', 10000, 15000, 470000, 'UNPAID', 1, '654 Maple St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250524120000', NOW(), 'CANCELLED', 0, 20000, 390000, 'UNPAID', 2, '987 Birch St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250525100000', NOW(), 'COMPLETED', 5000, 10000, 460000, 'PAID', 1, '123 Main St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250526143000', NOW(), 'PENDING', 0, 25000, 420000, 'UNPAID', 2, '456 Elm St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250527120000', NOW(), 'DELIVERED', 10000, 15000, 490000, 'PAID', 1, '789 Oak St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250528100000', NOW(), 'PROCESSING', 0, 20000, 430000, 'UNPAID', 2, '321 Pine St', CURDATE() + INTERVAL 7 DAY),
    (1, '20250529143000', NOW(), 'COMPLETED', 5000, 10000, 470000, 'PAID', 1, '654 Maple St', CURDATE() + INTERVAL 7 DAY),
    (2, '20250530120000', NOW(), 'PENDING', 0, 15000, 410000, 'UNPAID', 2, '987 Birch St', CURDATE() + INTERVAL 7 DAY);

-- Thêm vào bảng orders_items
INSERT INTO orders_items (order_id, product_item_id, quantity)
VALUES
    (1, 1, 2),
    (1, 2, 1),
    (2, 3, 1),
    (3, 4, 2),
    (4, 15, 1),
    (5, 16, 3),
    (6, 11, 1),
    (7, 12, 2),
    (8, 23, 1),
    (9, 14, 1),
    (10, 5, 2),
    (11, 1, 1),
    (12, 2, 3),
    (13, 13, 2),
    (14, 4, 1),
    (15, 5, 1),
    (16, 6, 2),
    (17, 1, 1),
    (18, 2, 1),
    (19, 33, 2),
    (20, 4, 1),
    (21, 25, 1),
    (22, 6, 2),
    (23, 1, 2),
    (24, 22, 1),
    (25, 3, 1),
    (26, 4, 3),
    (27, 5, 1),
    (28, 6, 1),
    (29, 1, 2),
    (30, 22, 1);

