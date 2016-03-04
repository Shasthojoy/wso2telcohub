/*
* ==========================================
* MySQL script related to custom tables in
* Api Manager Stats database
* Database: AM stats database
* ==========================================
*/

CREATE TABLE IF NOT EXISTS `SB_API_RESPONSE_SUMMARY` (
  `messageRowID` varchar(100) NOT NULL,
  `api` varchar(100) DEFAULT NULL,
  `api_version` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `apiPublisher` varchar(100) DEFAULT NULL,
  `consumerKey` varchar(100) DEFAULT NULL,
  `userId` varchar(100) DEFAULT NULL,
  `context` varchar(100) DEFAULT NULL,
  `serviceTime` int(11) DEFAULT NULL,
  `response_count` int(11) DEFAULT NULL,
  `hostName` varchar(100) DEFAULT NULL,
  `resourcePath` varchar(100) DEFAULT NULL,
  `method` varchar(10) DEFAULT NULL,
  `requestId` varchar(100) DEFAULT NULL,
  `operatorId` varchar(100) DEFAULT NULL,
  `responseCode` varchar(5) DEFAULT NULL,
  `msisdn` varchar(20) DEFAULT NULL,
  `operatorRef` varchar(100) DEFAULT NULL,
  `chargeAmount` varchar(20) DEFAULT NULL,
  `purchaseCategoryCode` varchar(40) DEFAULT NULL,
  `exceptionId` varchar(10) DEFAULT NULL,
  `exceptionMessage` varchar(255) DEFAULT NULL,
  `jsonBody` text,
  `year` smallint(6) DEFAULT NULL,
  `month` smallint(6) DEFAULT NULL,
  `day` smallint(6) DEFAULT NULL,
  `time` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`messageRowID`)
);

ALTER TABLE subscription_whitelist RENAME subscription_WhiteList;

ALTER TABLE whitelistmsisdn ADD UNIQUE (MSISDN);
