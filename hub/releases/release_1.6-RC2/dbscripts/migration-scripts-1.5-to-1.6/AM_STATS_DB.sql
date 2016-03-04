/*
* ==========================================
* MySQL script related to custom tables in
* Api Manager Stats database
* Database: AM stats database
* ==========================================
*/
DROP TABLE whitelistmsisdn;

ALTER TABLE subscription_whitelist RENAME subscription_WhiteList;

ALTER TABLE subscription_WhiteList ADD UNIQUE (msisdn);

ALTER TABLE app_approval_audit ADD APP_APPROVAL_ID INT UNSIGNED NOT NULL AUTO_INCREMENT,DROP PRIMARY KEY, ADD PRIMARY KEY (APP_APPROVAL_ID);

CREATE TABLE IF NOT EXISTS `admin_comments` (
  `TaskID` int(11) NOT NULL,
  `Comment` varchar(255) DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL,
  `Description` varchar(1000) NOT NULL,
  PRIMARY KEY (`TaskID`)
);

CREATE TABLE IF NOT EXISTS `subscription_comments` (
  `TaskID` varchar(255) NOT NULL,
  `Comment` varchar(1024) NOT NULL,
  `Status` varchar(255) NOT NULL,
  `Description` varchar(1024) NOT NULL,
  PRIMARY KEY (`TaskID`)
);
