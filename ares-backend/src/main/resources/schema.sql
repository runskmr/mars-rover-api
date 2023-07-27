DROP DATABASE IF EXISTS aresdb;

create database aresdb;
use aresdb;

-- Table structure for user

CREATE TABLE user (
  id int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
);

-- Table structure for category

CREATE TABLE category (
  id int NOT NULL AUTO_INCREMENT,
  date DATE NOT NULL,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

-- Table structure for photo

CREATE TABLE photo (
  id int NOT NULL,
  imgSrc varchar(255) NOT NULL,
  camera varchar(255) NOT NULL,
  rover varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

-- Table structure for favorite

CREATE TABLE favorite (
  id int NOT NULL AUTO_INCREMENT,
  date DATE NOT NULL,
  categoryId int DEFAULT NULL,
  photoId int DEFAULT NULL,
  userId int DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT favorite_fk_1 FOREIGN KEY (categoryId) REFERENCES category (id),
  CONSTRAINT favorite_fk_2 FOREIGN KEY (photoId) REFERENCES photo (id),
  CONSTRAINT favorite_fk_3 FOREIGN KEY (userId) REFERENCES user (id)
);