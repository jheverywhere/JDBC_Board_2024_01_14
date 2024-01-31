DROP DATABASE IF EXISTS text_board;
CREATE DATABASE text_board;
USE text_board;

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

#임시회원 
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = 'user1',
`name` = '리정환',
email = 'user1@test.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = 'user2',
`name` = '리정구',
email = 'user2@test.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`name` = '롯폰기',
email = 'admin@test.com';

#게시물 테이블에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT UNSIGNED NOT NULL;

#게시물 테이블에 hit 칼럼 추가
ALTER TABLE article ADD COLUMN hit INT UNSIGNED NOT NULL;

#멤버 테이블에 email 칼럼 추가
ALTER TABLE `member` ADD COLUMN email VARCHAR(200) NOT NULL UNIQUE;

DESC `member`;
DESC article;
#임시 게시물 추가
INSERT INTO article
SET regdate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1',
memberId = 1,
hit = 0;

INSERT INTO article
SET regdate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2',
memberId = 2,
hit = 0;

INSERT INTO article
SET regdate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3',
memberId = 2,
hit = 0;