CREATE TABLE ORDERS  (
	ISBN VARCHAR(30) NOT NULL PRIMARY KEY ,
	QUANTITY NUMBER NOT NULL,
	PRICE NUMBER(12,2) NOT NULL,
	CUSTOMER VARCHAR(30) NOT NULL
);