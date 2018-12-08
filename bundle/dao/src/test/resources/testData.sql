---------------- BOOK ----------------------

DROP TABLE IF EXISTS `book`;
CREATE TABLE IF NOT EXISTS `book` (
  `isbn` varchar(100) PRIMARY KEY NOT NULL,
  `author` varchar(100) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `language` varchar(100) DEFAULT NULL,
  `availableCopies` int(10) DEFAULT 0
);

DELETE FROM `book`;
INSERT INTO `book` (`isbn`, `author`, `title`, `description`, `language`, `availableCopies`) VALUES
	('0345391802', 'Douglas Adams', 'The Hitchhikers Guide to the Galaxy', 'description', 'English', 42),
	('9780345391803', 'George Orwell', '1984', 'The Party told you to reject the evidence of your eyes and ears. It was their final, most essential command.', 'English', 5);

---------------- USER ----------------------

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `userId` int(12) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `passwordHash` varchar(20) NOT NULL,
  `librarian` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) AUTO_INCREMENT=2 ;

DELETE FROM `user`;
INSERT INTO `user` (`userId`, `name`, `email`, `passwordHash`, `librarian`) VALUES
	(1, 'Test Thomas', 'test@donotreply.dont', '#22fdfsd', 1);

---------------- BORROWING ----------------------

DROP TABLE IF EXISTS `borrowings`;
CREATE TABLE IF NOT EXISTS `borrowings` (
  `borrowId` int(12) unsigned NOT NULL AUTO_INCREMENT,
  `status` varchar(12) DEFAULT NULL,
  `creatorId` int(12) unsigned NOT NULL,
  `bookIsbn` varchar(100) NOT NULL,
  `creationDate` date NOT NULL,
  PRIMARY KEY (`borrowId`),
  CONSTRAINT `book` FOREIGN KEY (`bookIsbn`) REFERENCES `book` (`isbn`),
  CONSTRAINT `creator` FOREIGN KEY (`creatorId`) REFERENCES `user` (`userId`)
) AUTO_INCREMENT=2;

DELETE FROM `borrowings`;
INSERT INTO `borrowings` (`borrowId`, `status`, `creatorId`, `bookIsbn`, `creationDate`) VALUES
	(1, 'requested', 1, '0345391802', '2018-11-23');