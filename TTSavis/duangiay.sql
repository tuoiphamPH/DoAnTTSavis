
create database duangiay
use duangiay
drop database duangiay



CREATE TABLE Account (
                           id BIGINT PRIMARY KEY IDENTITY(1,1),
                           username NVARCHAR(255) NOT NULL,
                           email NVARCHAR(255) NOT NULL,
                           fullname NVARCHAR(255) NOT NULL,
						  status BIT NOT NULL,
						   role NVARCHAR(255) NOT NULL,
                          point float NOT NULL,
						   facebook_oauth_id BIGINT,
						   google_oauth_id BIGINT,
                           picture NVARCHAR(255),
                           password NVARCHAR(255) NOT NULL,
                            create_date DATETIME
);

CREATE TABLE CustomerInfo (
                       id BIGINT PRIMARY KEY IDENTITY(1,1),
                      phone NVARCHAR(255) NOT NULL,
                      status BIT NOT NULL,
					  address  NVARCHAR(255) NOT NULL,
				  account_id BIGINT,
                     FOREIGN KEY (account_id) REFERENCES Account(id)
					 );	
CREATE TABLE Role (
                            id BIGINT PRIMARY KEY IDENTITY(1,1),
                            name NVARCHAR(255) NOT NULL
);
CREATE TABLE Account_Role (
                            id BIGINT PRIMARY KEY IDENTITY(1,1),
                           role_id BIGINT,
                            account_id BIGINT,
                            FOREIGN KEY ( role_id) REFERENCES Role(id),
                            FOREIGN KEY (account_id) REFERENCES Account (id)
);







CREATE TABLE Cart (
                          id BIGINT PRIMARY KEY IDENTITY(1,1),
                          status BIT NOT NULL,
						   create_date DATETIME,
						    account_id BIGINT,
							   FOREIGN KEY (account_id) REFERENCES Account (id)
);

-- giỏ hàng
CREATE TABLE CartItem (
                         id BIGINT PRIMARY KEY IDENTITY(1,1),
                         quantity INT NOT NULL,
                         product_detail_id BIGINT,
							 cart_id BIGINT,
						  FOREIGN KEY (cart_id) REFERENCES Cart(id)
);


-- Create voucher
CREATE TABLE Voucher (
                            id BIGINT PRIMARY KEY IDENTITY(1,1),
                            amount INT NOT NULL,
                           required_value INT NOT NULL,
							status BIT NOT NULL,
							 expired_time DATETIME,
							  start_time DATETIME,
							   type NVARCHAR(255) NOT NULL
);

-- Create Rate
CREATE TABLE Rate (
                             id BIGINT PRIMARY KEY IDENTITY(1,1),
							 amount INT NOT NULL,
                             comment NVARCHAR(255) NOT NULL,
                             create_date DATETIME
);

CREATE TABLE Orders (
                             id BIGINT PRIMARY KEY IDENTITY(1,1),
							 total  FLOAT NOT NULL,
							 discount FLOAT NOT NULL,
                             memberOffer NVARCHAR(255) NOT NULL,
							 fee  FLOAT NOT NULL,
							 refund  FLOAT NOT NULL,
                             create_date DATETIME,
							 update_date DATETIME,
							  address NVARCHAR(255) NOT NULL,
							   fullname NVARCHAR(255) NOT NULL,
							    email NVARCHAR(255) NOT NULL,
								phone NVARCHAR(255) NOT NULL,
								sale_method NVARCHAR(255) NOT NULL,
								  order_stastus BIT NOT NULL,
								   account_id BIGINT,
                           voucher_id BIGINT,
                            FOREIGN KEY (  account_id) REFERENCES Account(id),
                            FOREIGN KEY (voucher_id) REFERENCES Voucher(id)
);




CREATE TABLE OrderTimeline (
                                    id BIGINT PRIMARY KEY IDENTITY(1,1),
                                    type NVARCHAR(255) NOT NULL,
									description NVARCHAR(255) NOT NULL,
									create_date DATETIME,
                                    order_id BIGINT,
                                    account_id BIGINT,
                                    FOREIGN KEY ( order_id) REFERENCES Orders(id),
                                    FOREIGN KEY ( account_id) REFERENCES Account(id)
);
-- giao dịch 
CREATE TABLE Transactions(
                                   id BIGINT PRIMARY KEY IDENTITY(1,1),
								   amount INT NOt Null,
                                   update_date DATE NOT NULL,
                                   create_date DATETIME,
								    type NVARCHAR(255) NOT NULL,
									payment_method NVARCHAR(255) NOT NULL,
									transaction_type NVARCHAR(255) NOT NULL,
                                   status INT NOT NULL,
                                   
                                   order_id BIGINT,
                                   account_id BIGINT,
                                   FOREIGN KEY (order_id) REFERENCES Orders(id),
                                   FOREIGN KEY ( account_id) REFERENCES Account(id)
);
--Loại
CREATE TABLE Category(
                         id BIGINT PRIMARY KEY IDENTITY(1,1),
                      
                         name NVARCHAR(255),
                        status INT NOT NULL
);
-- Thương hiệu
CREATE TABLE Brand (
                          id BIGINT PRIMARY KEY IDENTITY(1,1),
                   
                         name NVARCHAR(255),
                        status INT NOT NULL

);

CREATE TABLE Sole (
                                  id BIGINT PRIMARY KEY IDENTITY(1,1),
                                  
                         name NVARCHAR(255),
                        status INT NOT NULL

)


CREATE TABLE Material (
                                   id BIGINT PRIMARY KEY IDENTITY(1,1),
                              
                         name NVARCHAR(255),
                        status INT NOT NULL
);


CREATE TABLE Color (
                                      id BIGINT PRIMARY KEY IDENTITY(1,1),
                                    
                        code INT NOt Null,
                        status INT NOT NULL
);
-- sản phẩm
CREATE TABLE Product (
                          id BIGINT PRIMARY KEY IDENTITY(1,1),
						   name NVARCHAR(255),
                       create_date DATETIME,
					   update_date DATETIME,
					   description NVARCHAR(255) NOT NULL,
                          sell_price float NOT NULL,
						  product_gender NVARCHAR(255) NOT NULL,
						   status INT NOT NULL,
                         shoe_height INT NOT NULL,
						   benefit NVARCHAR(255),
						 shoe_feel NVARCHAR(255),
						  category_id BIGINT,
                           brand_id BIGINT,
						    sole_id BIGINT,
                           material_id BIGINT,
						    color_id BIGINT,
                            FOREIGN KEY (category_id) REFERENCES Category(id),
                            FOREIGN KEY ( brand_id) REFERENCES Brand(id),
							 FOREIGN KEY ( sole_id) REFERENCES Sole(id),
                            FOREIGN KEY (  material_id) REFERENCES Material(id),
							 FOREIGN KEY ( color_id) REFERENCES Color(id)
                           
                          
);
-- Create san_pham_anh table
CREATE TABLE ProductImage (
                              id BIGINT PRIMARY KEY IDENTITY(1,1),
							  url NVARCHAR(255),
                             product_id BIGINT,
                              
                              FOREIGN KEY (  product_id) REFERENCES Product(id)
);
-- Create chi tiết sp
CREATE TABLE ProductDetail (
                                   id BIGINT PRIMARY KEY IDENTITY(1,1),
								     size INT NOT NULL,
									   status INT NOT NULL,
									     quantity INT NOT NULL,
                                   product_id BIGINT,
                                 

                                   FOREIGN KEY (product_id) REFERENCES Product(id)
                                  
);
CREATE TABLE OrderItem (
                            id BIGINT PRIMARY KEY IDENTITY(1,1),
							price FLOAT NOT NULL,
							 quantity INT NOT NULL,
							   status INT NOT NULL,
								 order_id BIGINT,
                                   rate_id BIGINT,
								   product_detail_id BIGINT,
                                   FOREIGN KEY (order_id) REFERENCES Orders(id),
                                   FOREIGN KEY ( rate_id) REFERENCES Rate(id),
								    FOREIGN KEY ( product_detail_id) REFERENCES ProductDetail(id)
);



