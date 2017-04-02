create table player(
	player_id varchar(30) NOT NULL,
	last_name varchar(30) NOT NULL,
	first_name varchar(30) NOT NULL,
	position varchar(30) NOT NULL,
	birth_year int NOT NULL,
	debut_year int NOT NULL,
	PRIMARY KEY(player_id)
)ENGINE=innodb DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;