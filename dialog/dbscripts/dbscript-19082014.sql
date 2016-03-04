/*
* ==========================================
* MySQL script related to workflow approval 
* audit data tables.
* Database: Dialog stats database
* ==========================================
*/

/*
* Table for application approval audit data.
*/
CREATE TABLE IF NOT EXISTS `app_approval_audit` (
	`APP_NAME` varchar(100) DEFAULT NULL,
	`APP_CREATOR` varchar(50) DEFAULT NULL,
	`APP_STATUS` varchar(50) DEFAULT 'ON_HOLD',
	`APP_APPROVAL_TYPE` varchar(50) DEFAULT NULL,
	`COMPLETED_BY_ROLE` varchar(50) DEFAULT NULL,
	`COMPLETED_BY_USER` varchar(50) DEFAULT NULL,
	`COMPLETED_ON` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`APP_NAME`, `APP_CREATOR`, `COMPLETED_BY_ROLE`)
);

/*
* Table for subscription approval audit data.
*/
CREATE TABLE IF NOT EXISTS `sub_approval_audit` (
	`API_PROVIDER` varchar(200) DEFAULT NULL,
  	`API_NAME` varchar(200) DEFAULT NULL,
  	`API_VERSION` varchar(30) DEFAULT NULL,
	`APP_ID` int(11) NOT NULL,
	`SUB_STATUS` varchar(50) DEFAULT 'ON_HOLD',
	`SUB_APPROVAL_TYPE` varchar(50) DEFAULT NULL,
	`COMPLETED_BY_ROLE` varchar(50) DEFAULT NULL,
	`COMPLETED_BY_USER` varchar(50) DEFAULT NULL,
	`COMPLETED_ON` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`APP_ID`, `API_PROVIDER`, `API_NAME`, `API_VERSION`, `COMPLETED_BY_ROLE`)
)
