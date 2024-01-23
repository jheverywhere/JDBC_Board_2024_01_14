
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

#loginId 칼럼에 unique 제약조건  추가
ALTER TABLE `member` MODIFY COLUMN loginId CHAR(200) NOT NULL UNIQUE;

#해당 로그인 아이디가 있으면 1(true)을 반환, 없으면 0(false)을 반환

SELECT COUNT(*)>0
FROM `member`
WHERE loginId = 'nighteolt'