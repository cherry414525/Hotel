CREATE DATABASE IF NOT EXISTS hotel;

USE hotel;

drop table if exists room;
drop table if exists user;
drop table if exists booking;

create table if not exists room(
    room_id INT PRIMARY KEY,
    type_id INT,
    FOREIGN KEY (type_id) REFERENCES roomtype(type_id)
);

create table if not exists user(
	user_id int auto_increment primary key,
    name varchar(50),
    birthday date ,
    gender varchar(10),
    phone varchar(50),
    email varchar(100),
    salt varchar(100),
    password varchar(100)
    
);

create table if not exists booking(
	booking_id int auto_increment primary key,
	room_id INT,
    user_id INT,
	price double,
	start_date date,
	end_date date,
	status varchar(20),
    createdate timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    updatedate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	FOREIGN KEY (room_id) REFERENCES room(room_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

create table if not exists roomtype(
    type_id INT PRIMARY KEY,
    name VARCHAR(50),
    price DOUBLE,
    capacity INT,
    photo VARCHAR(100)
);