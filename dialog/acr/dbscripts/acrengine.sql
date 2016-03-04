/*
SQLyog Community v8.6 GA
MySQL - 5.6.14 : Database - acrengine
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`acrengine` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `acrengine`;

/*Table structure for table `acr` */

DROP TABLE IF EXISTS `acr`;

CREATE TABLE `acr` (
  `acr_id` int(11) NOT NULL AUTO_INCREMENT,
  `acr` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `expire_period` varchar(255) DEFAULT NULL,
  `msisdn` varchar(255) DEFAULT NULL,
  `acr_status` int(11) DEFAULT NULL,
  `acr_version` varchar(255) DEFAULT NULL,
  `appId` int(11) DEFAULT NULL,
  PRIMARY KEY (`acr_id`),
  KEY `FK17890724EFDFB` (`appId`),
  CONSTRAINT `FK17890724EFDFB` FOREIGN KEY (`appId`) REFERENCES `application` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `acr` */

insert  into `acr`(`acr_id`,`acr`,`created_date`,`expire_period`,`msisdn`,`acr_status`,`acr_version`,`appId`) values (1,'lWoeXF+rsrK5YJY/PIB2PakgB4dLLtA+OsVLG9iiqXrkuV3dC0qp3Dc3EhSc5ekw5eDUa1pkh1+ADvFS7NUiUA==','2014-04-09','365','tel:+94773059732',1,'1.0',1),(2,'lWoeXF+rsrK5YJY/PIB2PSBBjFbID8IkjdMqsyhDhvfkuV3dC0qp3Dc3EhSc5ekwMq+18qJxzXHC80U+sBqYBw==','2014-04-09','365','tel:+94772541784',1,'1.0',1),(3,'lAsy+njKkuVpD19cECNNCgikDWuiBF7gKV20/3NNXLSl3oDd2MkOLVDqMS7LCSnjPFc9Ig7CovF/UM2uUhfXWQ==','2014-04-17','365','tel:+94771111111',0,'1.0',6),(4,'lAsy+njKkuVpD19cECNNCtM9M425eBRvCZQKfJLPGBKl3oDd2MkOLVDqMS7LCSnjGP7GUH2ln1BVeKXagNLvsQ==','2014-04-17','365','tel:+94773876451',1,'1.0',6),(5,'JBOCEwTDNdsxcqv2Tzk7A8QljGgfhFlX4VC8qiX5jfTOlhZ65LNMk6XcltvFjfN5LGd+Ndzt7EzDhwxLUCyZcg==','2014-04-17','365','tel:+94771111111',0,'1.0',7),(6,'JBOCEwTDNdsxcqv2Tzk7A0ruNgHij1rEVjbxuJhFHFbOlhZ65LNMk6XcltvFjfN5/KfbL7iYZbF7I4zuOBB/bQ==','2014-04-17','365','tel:+94773876451',1,'1.0',7),(7,'y0QroQqkuVrCxjEd0kRwqiSgPafjknR5BXc2qFHkbNfT1tfcyRqejzGrS/ZOCwusnOxqv0Wa9nAlg4Jnc9LZ9w==','2014-04-17','365','tel:+94771111111',0,'1.0',8),(8,'y0QroQqkuVrCxjEd0kRwqpm8n+Ri0pA7vcJJBxGV/nXT1tfcyRqejzGrS/ZOCwus6sKpccmm/YmKXc0tE/xoIw==','2014-04-17','365','tel:+94773876451',1,'1.0',8),(9,'pURXSxZOKtHdqOv5KBX6FUhKyrdSY48j/JWrsyTxjo8koy/ZPsOLzkvRZFLGbvirc2UwrIYEbUG7t+fppmwkyw==','2014-04-17','365','tel:+94771111111',0,'1.0',9),(10,'pURXSxZOKtHdqOv5KBX6Fcc9v5H2u45EIlkPz++j9OYkoy/ZPsOLzkvRZFLGbvir8yCnLmpOnn07nZn1MKsG0Q==','2014-04-17','365','tel:+94773876451',101,'1.0',9),(11,'y0QroQqkuVrCxjEd0kRwqlnudc+fGtwd4CE7Z4Oxp2ecc/rzND6l5IZ8Do8IxDEqfVfjoEzyxzdgGs10jrRaXg==','2014-04-21','365','tel:+94773059732',1,'1.0',8),(12,'y0QroQqkuVrCxjEd0kRwqhiYLKlf7pccQiSGwE50Bdicc/rzND6l5IZ8Do8IxDEqA1E99agk9kgKfO0cCCK5IA==','2014-04-21','365','tel:+94772541784',1,'1.0',8),(13,'Us4ibT4Eais8zT8g0FwV9NY8K48V9/8+W2m1KJ+WW5RftQgRJsfnwr//sOP4kAz9WAREEXf0crgKSkgV+ykO7A==','2014-05-09','365','tel:+9477785942',1,'1.0',2),(14,'Us4ibT4Eais8zT8g0FwV9FR/mwLsA5kyFnyZ9DmdIuBVlSFy+BRBG9ytahECjCEEVvIwgQTXkM7A5cHXBPVpOw==','2014-05-09','365','tel:+94774745741',1,'1.0',2),(15,'Us4ibT4Eais8zT8g0FwV9A5Z6x3UYiyJtlbETdaAtXnAolAj6EIRlhlpX3ifNUaVL/nPs3Qf/txoNSqYEv218A==','2014-05-09','31556952000','tel:+9477785922',1,'1.0',2),(16,'Us4ibT4Eais8zT8g0FwV9Nr7BOxYvd7/1CGnSClLj+fDFc7ddBDVeWMrNZAP7fV6/E3fTMZyfGPmuC13O03f3g==','2014-05-09','31556952000','tel:+94774445741',1,'1.0',2),(17,'Us4ibT4Eais8zT8g0FwV9L6dGhDdDZ4/1nBnYiHLEi7YHkH1XiSEHYIaT4K4NSei/GTJHpdyWFnJnkNp7WQDQQ==','2014-09-12','31556952000','tel:+9477785777',1,'1.0',2),(18,'Us4ibT4Eais8zT8g0FwV9KNsJHdj0ZFNtfbUICJlolS4z3B0dL9AP1cf611JWfspIv3TGgKIuTpI6TYtYp9z1Q==','2014-09-12','31556952000','tel:+94774445722',1,'1.0',2);

/*Table structure for table `acr_history` */

DROP TABLE IF EXISTS `acr_history`;

CREATE TABLE `acr_history` (
  `acr_history_id` int(11) NOT NULL AUTO_INCREMENT,
  `effect_date` date DEFAULT NULL,
  `acr` varchar(255) DEFAULT NULL,
  `acr_status` int(11) DEFAULT NULL,
  `acr_version` varchar(255) DEFAULT NULL,
  `msisdn` varchar(255) DEFAULT NULL,
  `acrId` int(11) DEFAULT NULL,
  PRIMARY KEY (`acr_history_id`),
  KEY `FKAD5773E585B2AAEA` (`acrId`),
  CONSTRAINT `FKAD5773E585B2AAEA` FOREIGN KEY (`acrId`) REFERENCES `acr` (`acr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `acr_history` */

insert  into `acr_history`(`acr_history_id`,`effect_date`,`acr`,`acr_status`,`acr_version`,`msisdn`,`acrId`) values (1,'2014-04-17','lAsy+njKkuVpD19cECNNCgikDWuiBF7gKV20/3NNXLSl3oDd2MkOLVDqMS7LCSnjXxRFTfkkk3fkBtIyG0tFtg==',1,'1.0','tel:+94771111111',3),(2,'2014-04-17','lAsy+njKkuVpD19cECNNCgikDWuiBF7gKV20/3NNXLSl3oDd2MkOLVDqMS7LCSnjPFc9Ig7CovF/UM2uUhfXWQ==',1,'1.0','tel:+94771111111',3),(3,'2014-04-17','JBOCEwTDNdsxcqv2Tzk7A8QljGgfhFlX4VC8qiX5jfTOlhZ65LNMk6XcltvFjfN5IZs0FIjLDigJ/8/KDgcAUw==',1,'1.0','tel:+94771111111',5),(4,'2014-04-17','JBOCEwTDNdsxcqv2Tzk7A8QljGgfhFlX4VC8qiX5jfTOlhZ65LNMk6XcltvFjfN5LGd+Ndzt7EzDhwxLUCyZcg==',1,'1.0','tel:+94771111111',5),(5,'2014-04-17','y0QroQqkuVrCxjEd0kRwqiSgPafjknR5BXc2qFHkbNfT1tfcyRqejzGrS/ZOCwus5K4a4vyVZSmSYS2MuceIug==',1,'1.0','tel:+94771111111',7),(6,'2014-04-17','y0QroQqkuVrCxjEd0kRwqiSgPafjknR5BXc2qFHkbNfT1tfcyRqejzGrS/ZOCwusnOxqv0Wa9nAlg4Jnc9LZ9w==',1,'1.0','tel:+94771111111',7),(7,'2014-04-17','pURXSxZOKtHdqOv5KBX6FUhKyrdSY48j/JWrsyTxjo8koy/ZPsOLzkvRZFLGbvirpq2OklY2dz+UP+bgMArYKg==',1,'1.0','tel:+94771111111',9),(8,'2014-04-17','pURXSxZOKtHdqOv5KBX6FUhKyrdSY48j/JWrsyTxjo8koy/ZPsOLzkvRZFLGbvirc2UwrIYEbUG7t+fppmwkyw==',1,'1.0','tel:+94771111111',9),(9,'2014-04-21','pURXSxZOKtHdqOv5KBX6Fcc9v5H2u45EIlkPz++j9OYkoy/ZPsOLzkvRZFLGbvir8yCnLmpOnn07nZn1MKsG0Q==',1,'1.0','tel:+94773876451',10);

/*Table structure for table `application` */

DROP TABLE IF EXISTS `application`;

CREATE TABLE `application` (
  `app_id` int(11) NOT NULL AUTO_INCREMENT,
  `app_code` varchar(255) DEFAULT NULL,
  `app_description` varchar(255) DEFAULT NULL,
  `app_name` varchar(255) DEFAULT NULL,
  `app_creater` varchar(255) DEFAULT NULL,
  `provider_app_id` varchar(255) DEFAULT NULL,
  `app_status` int(11) DEFAULT NULL,
  `provider_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`app_id`),
  KEY `FK5CA40550EC8E8C1E` (`provider_id`),
  CONSTRAINT `FK5CA40550EC8E8C1E` FOREIGN KEY (`provider_id`) REFERENCES `serviceprovider` (`provider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `application` */

insert  into `application`(`app_id`,`app_code`,`app_description`,`app_name`,`app_creater`,`provider_app_id`,`app_status`,`provider_id`) values (1,'wEw7fO1','Test Messaging app2','TST App2',NULL,'aq12',1,1),(2,'g6rmr32','Test SMSapp2','SMS123',NULL,'s123s',1,1),(3,'UqJzr33','Test Messaging app3','TST App3',NULL,'aq12',1,2),(4,'ZgJEBp4','Frisby Test Messaging app2','JS Tester',NULL,'aaa123',1,2),(5,'CJkfNh5','Frisby Test Messaging app2','JS Tester',NULL,'aaa123',1,2),(6,'K4O8OW6','Test SMS News Application','Test SMS Application',NULL,'aX01We',1,1),(7,'hE6ALW7','Test SMS News Application','Test SMS Application',NULL,'aX01We',1,1),(8,'53NZwa8','Test SMS News Application','Test SMS Application',NULL,'aX01We',1,1),(9,'1GCe529','Test SMS News Application','Test SMS Application',NULL,'aX01We',1,1),(10,'1gqXLZ10','Test Messaging app3','TST App3',NULL,'aq12',1,2),(11,'fUPZYZ11','Test Messaging app3','TST App3',NULL,'aq12',1,2),(12,'Pnjhf312','Test Messaging app3','TST App3',NULL,'aq12',1,2),(13,'Z4bOFe13','Test Messaging app444','TST App444',NULL,'aq12',1,2),(14,'UVsM7Z14','Test Messaging app444','TST App444',NULL,'aq12',1,2),(15,'diTTQX15','Test Messaging appXXX','TST AppXXX',NULL,'aq12',1,2),(16,'3AFfgc16','Test Messaging appXXX','TST AppXXX',NULL,'aq12',1,2),(17,'nQVBJG17','Test Messaging appXXX','TST AppXXX',NULL,'aq12',1,2),(18,'Pgg0qL18','Test Messaging appXXX','TST AppXXX',NULL,'aq12',1,2),(19,'cVk1Lc19','Test Messaging appXXd','TST AppXXXd',NULL,'aq12',1,2),(20,'QPAEhv20','Test Messaging app3','TST App3',NULL,'aq12',1,2),(21,'ypEoZ621','Test Messaging app4','TST App4',NULL,'aq12',1,2),(22,'o42D0K22','Test Messaging app2','TST App2',NULL,'aq12',1,1),(23,'mvQVst23','Test Messaging app2','TST App2',NULL,'aq12',1,1),(24,'EGLy5a24','Test Messaging app2','TST App2',NULL,'aq12',1,1),(25,'EVwXrv25','Test Messaging app2','TST AppH',NULL,'aq12',1,1),(26,'cHG8ST26','Test Messaging app2','TST Appz',NULL,'aq12',1,1),(27,'XiYFY527','Test Messaging app2','TST Appzx',NULL,'aq12',1,1),(28,'Q1rD7v28','Test Messaging app5','TST Appzy',NULL,'aq15',1,1),(29,'fIkAH129','Test Messaging app5','TST Appzy3',NULL,'aq151',1,1);

/*Table structure for table `application_couple` */

DROP TABLE IF EXISTS `application_couple`;

CREATE TABLE `application_couple` (
  `couple_id` int(11) NOT NULL AUTO_INCREMENT,
  `coupling_app_id` int(11) DEFAULT NULL,
  `couple_status` int(11) DEFAULT NULL,
  `appId` int(11) DEFAULT NULL,
  PRIMARY KEY (`couple_id`),
  KEY `FKFCD358F724EFDFB` (`appId`),
  CONSTRAINT `FKFCD358F724EFDFB` FOREIGN KEY (`appId`) REFERENCES `application` (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `application_couple` */

/*Table structure for table `application_key` */

DROP TABLE IF EXISTS `application_key`;

CREATE TABLE `application_key` (
  `key_id` int(11) NOT NULL AUTO_INCREMENT,
  `app_key` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `key_version` varchar(255) DEFAULT NULL,
  `appId` int(11) DEFAULT NULL,
  PRIMARY KEY (`key_id`),
  KEY `FKB5ADAAF0724EFDFB` (`appId`),
  CONSTRAINT `FKB5ADAAF0724EFDFB` FOREIGN KEY (`appId`) REFERENCES `application` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `application_key` */

insert  into `application_key`(`key_id`,`app_key`,`created_date`,`duration`,`key_version`,`appId`) values (1,'V5Gbvau1r0vBjWxA','2014-04-09','365','1.0',1),(2,'76OfeN2Prz85tc2a','2014-04-09','365','1.0',2),(3,'6X6gD0x3KAT1AAC3','2014-04-17','365','1.0',3),(4,'5w7ThUu3n4dkNW74','2014-04-17','365','1.0',4),(5,'3YTC5P3PlyOJxH4b','2014-04-17','365','1.0',5),(6,'i60O4xthgMlAWhGY','2014-04-17','365','1.0',6),(7,'BY0do47fMFcMIEFN','2014-04-17','365','1.0',7),(8,'wkqCUNNRb8gsqy36','2014-04-17','365','1.0',8),(9,'wxPVxspAUPkmwtL9','2014-04-17','365','1.0',9),(10,'pcO0rvl6aJa1VpZc','2014-04-18','365','1.0',10),(11,'BKJTErb1k0ZpC1rq','2014-04-21','365','1.0',11),(12,'KFZI1HTmlum62dSr','2014-04-21','365','1.0',12),(13,'dt1rC31cKCMinhWf','2014-04-21','365','1.0',13),(14,'e6npUHCDf41DhpZt','2014-04-21','365','1.0',14),(15,'cG1c28AHrq1bZSH5','2014-04-21','365','1.0',15),(16,'yOUiVKgS2IG116A7','2014-04-21','365','1.0',16),(17,'V1WlM7W1tnPfnj3J','2014-04-21','365','1.0',17),(18,'lyyK1jLXR8he41oD','2014-04-21','365','1.0',18),(19,'VC1paekA9gFFXmWo','2014-04-21','365','1.0',19),(20,'b7W8oZB03DQVBq2k','2014-05-08','365','1.0',20),(21,'QX3D1uUPEYt1z27f','2014-05-08','365','1.0',21),(22,'Gs2K2KXTAmJURUx7','2014-05-09','365','1.0',22),(23,'pA2r2VtN83rFrIWE','2014-05-09','365','1.0',23),(24,'KCD4s21W5IQ6v2UI','2014-05-20','31556952000','1.0',24),(25,'b2Nigraleh58gCZH','2014-09-12','31556952000','1.0',25),(26,'o60X2FodDyQ1SVX4','2014-09-15','31556952000','1.0',26),(27,'VE7Q8g2e72d4Mjje','2014-09-18','31556952000','1.0',27),(28,'Nj2I8TAbVMbg26Vd','2014-09-19','31556952000','1.0',28),(29,'wh3dV21g8z9fMWd2','2014-10-17','31556952000','1.0',29);

/*Table structure for table `operatorapps` */

DROP TABLE IF EXISTS `operatorapps`;

CREATE TABLE `operatorapps` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `applicationid` int(11) DEFAULT NULL,
  `operatorid` int(11) DEFAULT NULL,
  `isactive` int(11) DEFAULT '0',
  `note` varchar(255) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  `refreshtoken` varchar(255) DEFAULT NULL,
  `tokenvalidity` double DEFAULT NULL,
  `tokentime` double DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `tokenurl` varchar(255) DEFAULT NULL,
  `tokenauth` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

/*Data for the table `operatorapps` */

insert  into `operatorapps`(`ID`,`applicationid`,`operatorid`,`isactive`,`note`,`created`,`created_date`,`lastupdated`,`lastupdated_date`,`refreshtoken`,`tokenvalidity`,`tokentime`,`token`,`tokenurl`,`tokenauth`) values (32,9,32,1,'note','axiata','2014-01-06 13:53:41','axiata',NULL,NULL,157680000,1395135145139,'fge1ZgL0ryQa','http://localhost:8280/token',NULL),(33,9,33,0,'note','axiata','2014-01-06 13:53:53','axiata',NULL,NULL,157680000,1395135145139,'fge1ZgL0ryQa','http://localhost:8280/token',NULL),(34,11,32,1,'note','axiata','2014-01-06 13:54:36','axiata',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(35,11,33,0,'note','axiata','2014-01-06 13:54:42','axiata',NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `operatorendpoints` */

DROP TABLE IF EXISTS `operatorendpoints`;

CREATE TABLE `operatorendpoints` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `operatorid` int(11) DEFAULT NULL,
  `api` varchar(25) DEFAULT NULL,
  `isactive` int(11) DEFAULT '0',
  `endpoint` varchar(255) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;

/*Data for the table `operatorendpoints` */

insert  into `operatorendpoints`(`ID`,`operatorid`,`api`,`isactive`,`endpoint`,`created`,`created_date`,`lastupdated`,`lastupdated_date`) values (36,32,'sms',1,'http://localhost:8081/mifeapiserver/SendSMSService/smsmessaging/1',NULL,'2014-03-04 06:06:23',NULL,NULL),(37,32,'payment',1,'http://localhost:8081/mifeapiserver/AmountChargeService/payment/1',NULL,'2014-03-04 06:06:58',NULL,NULL),(38,33,'sms',0,'http://localhost:8080/mifeapiserver/SendSMSService/smsmessaging/1',NULL,'2014-03-04 06:08:00',NULL,NULL),(39,33,'payment',0,'http://localhost:8080/mifeapiserver/AmountChargeService/payment/1',NULL,'2014-03-04 06:08:24',NULL,NULL);

/*Table structure for table `operators` */

DROP TABLE IF EXISTS `operators`;

CREATE TABLE `operators` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `operatorname` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

/*Data for the table `operators` */

insert  into `operators`(`ID`,`operatorname`,`description`,`created`,`created_date`,`lastupdated`,`lastupdated_date`) values (32,'DIALOG','Dialog Opearator','axatauser',NULL,'axatauser',NULL),(33,'CELCOM','Celcom Opearator','axatauser',NULL,'axatauser',NULL);

/*Table structure for table `operatorsubs` */

DROP TABLE IF EXISTS `operatorsubs`;

CREATE TABLE `operatorsubs` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `axiataid` int(11) DEFAULT NULL,
  `domainurl` varchar(255) DEFAULT NULL,
  `operator` varchar(45) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `operatorsubs` */

insert  into `operatorsubs`(`ID`,`axiataid`,`domainurl`,`operator`,`created`,`created_date`,`lastupdated`,`lastupdated_date`) values (2,16,'http://127.0.0.1:8081/mifeapiserver/SMSReceiptService/smsmessaging/1/inbound/subscriptions/sub6789','DIALOG',NULL,NULL,NULL,NULL),(3,17,'http://127.0.0.1:8081/mifeapiserver/SMSReceiptService/smsmessaging/1/inbound/subscriptions/sub6789','DIALOG',NULL,NULL,NULL,NULL);

/*Table structure for table `serviceprovider` */

DROP TABLE IF EXISTS `serviceprovider`;

CREATE TABLE `serviceprovider` (
  `provider_id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_code` varchar(255) DEFAULT NULL,
  `provider_name` varchar(255) DEFAULT NULL,
  `security_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`provider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `serviceprovider` */

insert  into `serviceprovider`(`provider_id`,`provider_code`,`provider_name`,`security_key`) values (1,'CON001','Connect','con123'),(2,'MIFE2','MIFE','xxx111');

/*Table structure for table `subscription_validator` */

DROP TABLE IF EXISTS `subscription_validator`;

CREATE TABLE `subscription_validator` (
  `application_id` int(11) NOT NULL,
  `api_id` int(11) NOT NULL,
  `validator_id` int(11) NOT NULL,
  PRIMARY KEY (`application_id`,`api_id`),
  KEY `validator_id` (`validator_id`),
  CONSTRAINT `subscription_validator_ibfk_1` FOREIGN KEY (`validator_id`) REFERENCES `validator` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `subscription_validator` */

insert  into `subscription_validator`(`application_id`,`api_id`,`validator_id`) values (9,5,2),(9,9,2);

/*Table structure for table `subscriptions` */

DROP TABLE IF EXISTS `subscriptions`;

CREATE TABLE `subscriptions` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `axiataid` int(11) DEFAULT NULL,
  `notifyurl` varchar(255) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

/*Data for the table `subscriptions` */

insert  into `subscriptions`(`ID`,`axiataid`,`notifyurl`,`created`,`created_date`,`lastupdated`,`lastupdated_date`) values (3,1,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(4,2,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(5,3,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(6,4,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(7,5,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(8,6,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(9,7,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(10,8,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(11,9,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(12,10,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(13,11,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(14,12,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(15,13,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(16,14,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(17,15,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(20,16,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(21,17,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(22,18,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(23,19,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL),(24,20,'http://localhost:18080/MediationTest/tnspoints/enpoint/smsdummy/outbound/7555/requests',NULL,NULL,NULL,NULL);

/*Table structure for table `validator` */

DROP TABLE IF EXISTS `validator`;

CREATE TABLE `validator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `class` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `validator` */

insert  into `validator`(`id`,`name`,`class`) values (1,'passthru','com.axiata.dialog.mife.validators.PassThroughValidator'),(2,'msisdn','com.axiata.dialog.mife.validators.MSISDNValidator');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
