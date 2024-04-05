CREATE DATABASE comicDB;

USE comicDB;

CREATE TABLE Users (
Userid int NOT NULL AUTO_INCREMENT,
Username varchar(255) UNIQUE NOT NULL,
Password varchar(255) NOT NULL,
PRIMARY KEY (Userid)
);

CREATE TABLE Comics (
Comicid int NOT NULL AUTO_INCREMENT,
Name varchar(255) UNIQUE NOT NULL,
Description varchar(255),
Rating int,
Category varchar(255),
Recommended boolean,
Userid int,
PRIMARY KEY (Comicid),
FOREIGN KEY (Userid) REFERENCES Users(Userid)
);

CREATE TABLE Characters (
Characterid int NOT NULL AUTO_INCREMENT,
Name varchar(255) UNIQUE NOT NULL,
Description varchar(255),
age int,
status varchar(255),
Comicid int,
PRIMARY KEY (Characterid),
FOREIGN KEY (Comicid) REFERENCES Comics(Comicid)
);