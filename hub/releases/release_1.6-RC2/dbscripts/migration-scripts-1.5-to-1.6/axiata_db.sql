/*
* ==========================================
* MySQL script related to tables in
* Axiata database
* Database: Axiata database
* ==========================================
*/
--
-- Table structure for table `ussd_request_entry`
--

CREATE TABLE IF NOT EXISTS `ussd_request_entry` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `axiataid` int(11) DEFAULT NULL,
  `notifyurl` varchar(255) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
);

--
-- Table structure for table `sendsms_reqid` used for delivery status query
--

CREATE TABLE IF NOT EXISTS `sendsms_reqid` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `hub_requestid` varchar(255) DEFAULT NULL,
  `sender_address` varchar(40) DEFAULT NULL,
  `delivery_address` varchar(40) DEFAULT NULL,
  `plugin_requestid` varchar(255) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
);
