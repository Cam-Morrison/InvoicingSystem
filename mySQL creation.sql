CREATE DATABASE invoicingSystem;
USE invoicingSystem;

CREATE TABLE address (
    street_address         VARCHAR(64) NOT NULL,
    city                   VARCHAR(64) NOT NULL,
    zip_code               VARCHAR(9) NOT NULL,
    country                VARCHAR(32) NOT NULL,
    customer_customer_id   INT NOT NULL
);

ALTER TABLE address ADD CONSTRAINT address_pk PRIMARY KEY ( customer_customer_id );

CREATE TABLE customer (
    customer_id  INT NOT NULL,
    first_name   VARCHAR(15) NOT NULL,
    last_name    VARCHAR(15) NOT NULL,
    email        VARCHAR(129) NOT NULL
);

ALTER TABLE customer ADD CONSTRAINT customer_pk PRIMARY KEY ( customer_id );

CREATE TABLE invoice_item (
    purchase_quantity           TINYINT NOT NULL,
    item_description            VARCHAR(256) NOT NULL,
    discount                    DECIMAL(6, 2) NOT NULL,
    order_order_id              BIGINT NOT NULL,
    product_product_id          INT NOT NULL
);

ALTER TABLE invoice_item
    ADD CONSTRAINT invoice_item_pk PRIMARY KEY ( order_order_id,
                                                 product_product_id);

CREATE TABLE `ORDER` (
    order_id              BIGINT NOT NULL,
    order_date            DATETIME NOT NULL,
    total_cost            DECIMAL(6, 2) NOT NULL,
    due_date              DATETIME,
    customer_customer_id  INT NOT NULL,
    staff_staff_id        TINYINT NOT NULL
);

ALTER TABLE `ORDER`
    ADD CONSTRAINT order_pk PRIMARY KEY ( order_id,
                                          customer_customer_id,
                                          staff_staff_id );

CREATE TABLE product (
    product_id            INT NOT NULL,
    name                  VARCHAR(128) NOT NULL,
    quantity              INT NOT NULL,
    price                 DECIMAL(6, 2) NOT NULL,
    supplier_supplier_id  SMALLINT NOT NULL
);

ALTER TABLE product ADD CONSTRAINT product_pk PRIMARY KEY ( product_id,
                                                            supplier_supplier_id );

CREATE TABLE shipping (
    shipping_id                   BIGINT NOT NULL,
    shipping_method               VARCHAR(64) NOT NULL,
    estimated_delivery_date       DATETIME,
    order_order_id                BIGINT NOT NULL
);

ALTER TABLE shipping
    ADD CONSTRAINT shipping_pk PRIMARY KEY ( shipping_id,
                                             order_order_id);

CREATE TABLE staff (
    staff_id       TINYINT NOT NULL,
    first_name     VARCHAR(15) NOT NULL,
    last_name      VARCHAR(15) NOT NULL,
    phone_number   VARCHAR(20) NOT NULL,
    job            VARCHAR(50) NOT NULL,
    password_hash  VARCHAR(50) NOT NULL
);

ALTER TABLE staff ADD CONSTRAINT staff_pk PRIMARY KEY ( staff_id );

CREATE TABLE supplier (
    supplier_id  SMALLINT NOT NULL,
    name         VARCHAR(128) NOT NULL,
    phone        VARCHAR(50) NOT NULL,
    email        VARCHAR(129) NOT NULL
);

ALTER TABLE supplier ADD CONSTRAINT supplier_pk PRIMARY KEY ( supplier_id );

ALTER TABLE address
    ADD CONSTRAINT address_customer_fk FOREIGN KEY ( customer_customer_id )
        REFERENCES customer ( customer_id );

ALTER TABLE invoice_item
    ADD CONSTRAINT invoice_item_order_fk FOREIGN KEY (order_order_id)
		REFERENCES  `ORDER` ( order_id );

ALTER TABLE invoice_item
    ADD CONSTRAINT invoice_item_product_fk FOREIGN KEY ( product_product_id)
		REFERENCES product ( product_id );

ALTER TABLE `ORDER`
    ADD CONSTRAINT order_customer_fk FOREIGN KEY ( customer_customer_id )
        REFERENCES customer ( customer_id );

ALTER TABLE `ORDER`
    ADD CONSTRAINT order_staff_fk FOREIGN KEY ( staff_staff_id )
        REFERENCES staff ( staff_id );

ALTER TABLE product
    ADD CONSTRAINT product_supplier_fk FOREIGN KEY ( supplier_supplier_id )
        REFERENCES supplier ( supplier_id );

ALTER TABLE shipping
    ADD CONSTRAINT shipping_order_fk FOREIGN KEY ( order_order_id)
        REFERENCES `ORDER` ( order_id );




