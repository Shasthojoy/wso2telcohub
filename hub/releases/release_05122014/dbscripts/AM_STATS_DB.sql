/*
* ==========================================
* MySQL script related to custom tables in
* Api Manager Stats database
* Database: AM stats database
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
);

/*
* Table for tax type definition
*/
CREATE TABLE IF NOT EXISTS tax ( 
    id INT NOT NULL AUTO_INCREMENT,
    type VARCHAR(25) NOT NULL,
    effective_from DATE,
    effective_to DATE,
    value DECIMAL(7,6) NOT NULL,
    PRIMARY KEY (id)
);

/*
* Table for API subscriptions to tax type mapping
*/
CREATE TABLE IF NOT EXISTS subscription_tax ( 
    application_id INT NOT NULL,
    api_id INT NOT NULL,
    tax_type VARCHAR(25) NOT NULL,
    PRIMARY KEY (application_id, api_id, tax_type)
);


/*
* Table for API subscriptions to charge rate mapping
*/
CREATE TABLE IF NOT EXISTS subscription_rates ( 
    application_id INT NOT NULL,
    api_id INT NOT NULL,
    operator_name varchar(45) NOT NULL,
    rate_name varchar(50) DEFAULT NULL,
    PRIMARY KEY (application_id, api_id, operator_name)
);
