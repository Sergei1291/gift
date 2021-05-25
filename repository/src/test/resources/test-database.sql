create table tag(id INT AUTO_INCREMENT, name VARCHAR(50) NOT NULL UNIQUE, PRIMARY KEY (id));

create table gift_certificate(id INT AUTO_INCREMENT, name VARCHAR(50) NOT NULL UNIQUE,
description VARCHAR(200), price INT, duration INT, create_date VARCHAR(50) NOT NULL,
last_update_date VARCHAR(50), PRIMARY KEY (id));

create table gift_certificate_tag(certificate INT NOT NULL, tag INT NOT NULL);

create table user(id INT AUTO_INCREMENT, login VARCHAR(50) NOT NULL UNIQUE, PRIMARY KEY (id));

create table order_certificate(id INT AUTO_INCREMENT, cost INT, purchase_time VARCHAR(50) NOT NULL,
certificate_id INT NOT NULL, user_id INT NOT NULL, PRIMARY KEY (id));