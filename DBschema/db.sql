drop database if exists text_board;
create database text_board;

#게시물 테이블 생성
CREATE TABLE article(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
title CHAR(100) NOT NULL,
`body` TEXT NOT NULL
);

#회원 테이블 생성
CREATE TABLE `member`(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
loginId CHAR(100) NOT NULL,
loginPw CHAR(200) NOT NULL,
`name` CHAR(100) NOT NULL
);