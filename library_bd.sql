CREATE TABLE Books(
    bookId int PRIMARY KEY AUTO_INCREMENT DEFAULT NULL,
    bookName varchar(15) NOT NULL,
    author varchar(15),
    publisher varchar(15),
    availability varchar(10),
    quantity int    
);

CREATE TABLE Users(
	userName varchar (15),
    PASSWORD varchar(30),
    email varchar(30)
);

CREATE TABLE Students(
	PASSWORD varchar (15),
     userName varchar(15),
    email varchar(30),
    matricule int(10) PRIMARY KEY,
    numberOfBooksBorrowed int 
);

CREATE TABLE Librarian(
	PASSWORD varchar (15),
     userName varchar(15),
    email varchar(30),
    libId int PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE Borrow(
    bookId int,
    matricule varchar(10),
    bookName varchar(15),
	userName varchar(15),
    date date,
    datelimit date,
    state varchar(10),
    quantity int,
    fines float,
    PRIMARY KEY (bookId, matricule)
);

CREATE TABLE modify(
	bookId int,
	libId int,
    bookName varchar(15),
	userName varchar(15),
    status varchar(6),
    PRIMARY KEY(libId,bookId)
);






