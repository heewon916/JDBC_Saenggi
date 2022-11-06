CREATE DATABASE /*!32312 IF NOT EXISTS*/`saengji` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `saengji`;

CREATE TABLE `user` (
    `ID` int  NOT NULL AUTO_INCREMENT,
    `userId` varchar(45)  NOT NULL ,
    `userPw` varchar(45)  NOT NULL ,
    `phoneNumber` varchar(45)  NOT NULL ,
    `birthday` date  NOT NULL ,
    `created` date  NOT NULL ,
    `userName` varchar(45)  NOT NULL ,
    PRIMARY KEY (
        `ID`
    )
);

CREATE TABLE `expense` (
    `ID` int  NOT NULL AUTO_INCREMENT,
    `userId` int  NOT NULL ,
    `expense` bigint  NOT NULL ,
    `expenseDate` date  NOT NULL ,
    `content` varchar(100)  NOT NULL ,
    `category` varchar(45),
    `accountId` int  NOT NULL ,
    PRIMARY KEY (
        `ID`
    )
);

CREATE TABLE `income` (
    `ID` int  NOT NULL AUTO_INCREMENT,
    `userId` int  NOT NULL ,
    `income` bigint  NOT NULL ,
    `incomeDate` date  NOT NULL ,
    `content` varchar(100)  NOT NULL ,
    `category` varchar(45),
    `accountId` int  NOT NULL ,
    PRIMARY KEY (
        `ID`
    )
);

CREATE TABLE `account` (
    `ID` int  NOT NULL AUTO_INCREMENT,
    `accountNum` varchar(45)  NOT NULL ,
    `bank` varchar(45)  NOT NULL ,
    `amount` bigint  NOT NULL ,
    `checkCredit` varchar(45)  NOT NULL ,
    `userId` int  NOT NULL ,
    `mainAccount` int NOT NULL,
    PRIMARY KEY (
        `ID`
    )
);

CREATE TABLE `goal` (
    `ID` int  NOT NULL AUTO_INCREMENT,
    `userId` int  NOT NULL ,
    `goalAmount` bigint  NOT NULL ,
    `goalStartDate` date  NOT NULL ,
    `goalEndDate` date  NOT NULL ,
    PRIMARY KEY (
        `ID`
    )
);

ALTER TABLE `expense` ADD CONSTRAINT `fk_expense_userId` FOREIGN KEY(`userId`)
REFERENCES `user` (`ID`);

ALTER TABLE `expense` ADD CONSTRAINT `fk_expense_accountId` FOREIGN KEY(`accountId`)
REFERENCES `account` (`ID`);

ALTER TABLE `income` ADD CONSTRAINT `fk_income_userId` FOREIGN KEY(`userId`)
REFERENCES `user` (`ID`);

ALTER TABLE `income` ADD CONSTRAINT `fk_income_accountId` FOREIGN KEY(`accountId`)
REFERENCES `account` (`ID`);

ALTER TABLE `account` ADD CONSTRAINT `fk_account_userId` FOREIGN KEY(`userId`)
REFERENCES `user` (`ID`);

ALTER TABLE `goal` ADD CONSTRAINT `fk_goal_userId` FOREIGN KEY(`userId`)
REFERENCES `user` (`ID`);