create table orders(
	isbn varchar(30) NOT NULL,
	quantity int NOT NULL,
	price double(12,2) NOT NULL,
	customer varchar(30) NOT NULL,
	PRIMARY KEY(isbn)
)ENGINE=innodb DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;