/*
* ==========================================
* MySQL  script related to tables in
* Dialog Mobile Connect.
* Database: Connect database
* ==========================================
*/

--
-- Table structure for table `clientstatus`
--

DROP TABLE IF EXISTS `clientstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientstatus` (
  `SessionID` varchar(255) DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL,
  `pin` varchar(10) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mcx_cross_operator_transaction_log`
--

DROP TABLE IF EXISTS `mcx_cross_operator_transaction_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mcx_cross_operator_transaction_log` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `multiplepasswords`
--

DROP TABLE IF EXISTS `multiplepasswords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `multiplepasswords` (
  `username` varchar(255) NOT NULL,
  `pin` int(11) DEFAULT NULL,
  `attempts` int(11) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pendingussd`
--

DROP TABLE IF EXISTS `pendingussd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pendingussd` (
  `msisdn` bigint(20) unsigned NOT NULL,
  `requesttype` int(1) NOT NULL COMMENT '1-register, 2-login, 3-pinreset',
  PRIMARY KEY (`msisdn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons` (
  `PersonID` int(11) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `City` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pin`
--

DROP TABLE IF EXISTS `pin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pin` (
  `username` varchar(255) DEFAULT NULL,
  `pin` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `regstatus`
--

DROP TABLE IF EXISTS `regstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regstatus` (
  `username` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sp_login_history`
--

DROP TABLE IF EXISTS `sp_login_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_login_history` (
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
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-12 14:38:14
