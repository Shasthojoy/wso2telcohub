DROP TABLE IF EXISTS `outbound_operatorsubs`;
CREATE TABLE  `outbound_operatorsubs` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `axiataid` int(11) DEFAULT NULL,
  `domainurl` varchar(255) DEFAULT NULL,
  `operator` varchar(45) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `outbound_subscriptions`;
CREATE TABLE  `outbound_subscriptions` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `axiataid` int(11) DEFAULT NULL,
  `notifyurl` varchar(255) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=325 DEFAULT CHARSET=latin1;