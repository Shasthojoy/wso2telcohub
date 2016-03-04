/*
* ==========================================
* MySQL script related to tables in
* Dialog Mobile Connect.
* Database: Connect database
* ==========================================
*/

--
-- Table structure for table `mcx_cross_operator_transaction_log`
--
CREATE TABLE IF NOT EXISTS `mcx_cross_operator_transaction_log` (
  `tx_id` varchar(200) NOT NULL,
  `tx_status` varchar(25) DEFAULT NULL,
  `batch_id` varchar(200) DEFAULT NULL,
  `api_id` varchar(25) DEFAULT NULL,
  `client_id` varchar(200) NOT NULL,
  `application_state` varchar(25) DEFAULT NULL,
  `sub_op_mcc` varchar(25) DEFAULT NULL,
  `sub_op_mnc` varchar(25) DEFAULT NULL,
  `timestamp_start` varchar(25) DEFAULT NULL,
  `timestamp_end` varchar(25) DEFAULT NULL,
  `exchange_response_code` int(25) DEFAULT NULL,
  PRIMARY KEY (`tx_id`)
);

--
-- Table structure for table `clientstatus`
--
CREATE TABLE IF NOT EXISTS `clientstatus` (
  `SessionID` varchar(255) DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL,
  `pin` varchar(10) DEFAULT '0'
);

--
-- Table structure for table `sp_login_history`
--
CREATE TABLE IF NOT EXISTS `sp_login_history` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `reqtype` varchar(20) DEFAULT NULL,
  `application_id` varchar(45) DEFAULT NULL,
  `authenticated_user` varchar(45) DEFAULT NULL,
  `isauthenticated` int(5) DEFAULT NULL,
  `authenticators` varchar(255) DEFAULT NULL,
  `ipaddress` varchar(20) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

