-- MySQL dump 10.13  Distrib 5.1.61, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: wso2am7stat
-- ------------------------------------------------------
-- Server version	5.1.61

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_comments`
--

DROP TABLE IF EXISTS `admin_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_comments` (
  `TaskID` int(11) NOT NULL,
  `Comment` varchar(255) DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL,
  `Description` varchar(1000) NOT NULL,
  PRIMARY KEY (`TaskID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_comments`
--

LOCK TABLES `admin_comments` WRITE;
/*!40000 ALTER TABLE `admin_comments` DISABLE KEYS */;
INSERT INTO `admin_comments` VALUES (11,'adminComment','status','description'),(12,'ng','Rejected','gdfg'),(112,'hiii','done','save'),(666,'hiii','done','save'),(1126,'hiii','done','save'),(5555,'hiii','done','save'),(6112,'hiii','done','save'),(13243,'ng','Rejected','gdfg'),(34722,'gjgjj','APPROVED','Approved'),(34725,'dhhhh sgdjgsj sffsdkf sffsdgfs sgfg','APPROVED','Approved'),(34726,'dhhdsf','APPROVED','Approved'),(34736,'wferf 4324dfg fefaef','APPROVED','approved'),(34739,'uiuyiuy','APPROVED','approved'),(34742,'rtrt 34564b rtyrthy5 b54356  grty','APPROVED','approved'),(34745,'','APPROVED','approved'),(34748,'tytyt','APPROVED','Approve application [ dfgklllll ] creation request from application creator - admin with throttling tier - Default [ Application Description: dg ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(34749,'sfghsd ksFDKHDH aKFHDkljah wawdhj','APPROVED','Approve application [ sdfdsdwhhhh ] creation request from application creator - admin with throttling tier - Default [ Application Description: asdf ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(39801,'hghg','APPROVED','Approve application [ dfgf ] creation request from application creator - admin with throttling tier - Default [ Application Description: dfg ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(39806,'sghdgfhfg sadfgahgf bafsjghkjadfbfg bkabhgb bkabgb bbagbb','APPROVED','Approve application [ rtrtghrtdhy ] creation request from application creator - admin with throttling tier - Default [ Application Description: erwt ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(39809,'dfhdfhhdf','APPROVED','Approve application [ dfgsdef ] creation request from application creator - admin with throttling tier - Default [ Application Description: dseg ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(39812,'dfgdfsg fgfdg','APPROVED','Approve application [ fvgdfsg ] creation request from application creator - admin with throttling tier - Default [ Application Description: sdfg ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(39813,'sdfsd sdfsad','APPROVED','Approve application [ sdfsda ] creation request from application creator - admin with throttling tier - Default [ Application Description: sfdf ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(39818,'sdfsdafads safadsf','APPROVED','Approve application [ wsfew ] creation request from application creator - admin with throttling tier - Default [ Application Description: asfd ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(39821,'hfjfh','APPROVED','Approve application [ r6udyt ] creation request from application creator - admin with throttling tier - Default [ Application Description: ghj ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(39824,'gtgrt','APPROVED','Approve application [ dgfg ] creation request from application creator - admin with throttling tier - Default [ Application Description: dghdf ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(39825,'jgg','APPROVED','Approve application [ sdfsdwqqqqqqqq ] creation request from application creator - admin with throttling tier - Default [ Application Description: sdf ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(39826,'khgfhhg','APPROVED','Approve application [ gjgjfjhjk ] creation request from application creator - admin with throttling tier - Default [ Application Description: yiyu ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(39827,'sdfsda','APPROVED','Approve application [ sdfbttttttttttttttttttttwa ] creation request from application creator - admin with throttling tier - Default [ Application Description: dfgf ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(42451,'jtgj jhgj','APPROVED','Approve application [ fuckdd ] creation request from application creator - admin with throttling tier - Default [ Application Description: saf ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(42452,'sdf','APPROVED','Approve application [ dsg ] creation request from application creator - admin with throttling tier - Default [ Application Description: sdgfa ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(42453,'sdfdsf','APPROVED','Approve application [ 111112 ] creation request from application creator - admin with throttling tier - Default [ Application Description: asd ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(42454,'null','APPROVED','Approve application [ 111112sdfsd ] creation request from application creator - admin with throttling tier - Default [ Application Description: asd ]\n								Application Details\n								\n									\n										Tiers:\n										\n                        				BronzeGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(42467,'null','APPROVED','Approve application [ sdfdsgdgethrthjh ] creation request from application creator - admin with throttling tier - Default [ Application Description: dfg ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44301,'null','APPROVED','Approve application [ fd ] creation request from application creator - admin with throttling tier - Default [ Application Description: ds ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44308,'kuillk[','APPROVED','Approve application [ 77 ] creation request from application creator - admin with throttling tier - Default [ Application Description: lkjlk ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44309,'','APPROVED','Approve application [ de456564 ] creation request from application creator - admin with throttling tier - Default [ Application Description: d ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44314,'Bad Charging type','APPROVED','Approve application [ demoApp ] creation request from application creator - admin with throttling tier - Default [ Application Description: lklj; ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44317,'fhfd','APPROVED','Approve application [ currDemoApp ] creation request from application creator - admin with throttling tier - Default [ Application Description: fh ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44318,'tyhtryhrt','APPROVED','Approve application [ currRelease ] creation request from application creator - admin with throttling tier - Default [ Application Description: dgd ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44319,'ghkghk','APPROVED','Approve application [ currReleaseDApp ] creation request from application creator - admin with throttling tier - Default [ Application Description: - ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44320,'fhfjfg','APPROVED','Approve application [ hhjj ] creation request from application creator - admin with throttling tier - Default [ Application Description: tyry ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44325,'dgshshgh','APPROVED','Approve application [ CurrTodayApp ] creation request from application creator - admin with throttling tier - Default [ Application Description: - ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44328,'null','APPROVED','Approve API [ payment - v1 ] subscription creation request from subscriber - admin for the application - CurrTodayApp [ Application Description: - ]\n								Subscription Details\n								\n									\n										Tiers:\n										\n                        				SubscriptionBronzeRequestbasedUnlimitedGoldSilverPremium\n											\n												\n							'),(44334,'kugkiu','APPROVED','Approve application [ jjj34 ] creation request from application creator - admin with throttling tier - Default [ Application Description: fg ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44337,'null','APPROVED','Approve API [ payment - v1 ] subscription creation request from subscriber - admin for the application - jjj34 [ Application Description: fg ]\n								Subscription Details\n								\n									\n										Tiers:\n										\n                        				SubscriptionBronzeRequestbasedUnlimitedGoldSilverPremium\n											\n												\n							'),(44343,'null','APPROVED','Approve API [ payment - v1 ] subscription creation request from subscriber - admin for the application - currDemoApp [ Application Description: fh ]\n								Subscription Details\n								\n									\n										Tiers:\n										\n                        				SubscriptionBronzeRequestbasedUnlimitedGoldSilverPremium\n											\n												\n							'),(44346,'ewrtregt','APPROVED','Approve application [ vb ] creation request from application creator - admin with throttling tier - Default [ Application Description: wr ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(44349,'good App','APPROVED','Approve API [ payment - v1 ] subscription creation request from subscriber - admin for the application - vb [ Application Description: wr ]\n								Subscription Details\n								\n									\n										Tiers:\n										\n                        				SubscriptionBronzeRequestbasedUnlimitedGoldSilverPremium\n											\n												\n							'),(48952,'dfgjsgh','APPROVED','Approve application [ currReleaseDate ] creation request from application creator - admin with throttling tier - Default [ Application Description: - ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(48957,'gghgh','APPROVED','Approve application [ kkkkkkkkkkkkkkkkkkkkkkk ] creation request from application creator - admin with throttling tier - Default [ Application Description: sfd ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(50551,'fghgfh ddhy','APPROVED','Approve application [ 9000000000000 ] creation request from application creator - admin with throttling tier - Default [ Application Description: gyu ]\n								Application Details\n								\n									\n										Tiers\n										\n                        				BronzeDefaultGoldPremiumRequestbasedSilverSubscriptionUnlimited\n											\n								\n							'),(55332,'fdsfd','approveed','111'),(77777,'adminComment','status','111'),(1324334,'ng','Rejected','gdfg'),(175676571,'adminComment','status','description');
/*!40000 ALTER TABLE `admin_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_destination_summary`
--

DROP TABLE IF EXISTS `api_destination_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_destination_summary` (
  `api` varchar(100) NOT NULL DEFAULT '',
  `version` varchar(100) NOT NULL DEFAULT '',
  `apiPublisher` varchar(100) NOT NULL DEFAULT '',
  `context` varchar(100) NOT NULL DEFAULT '',
  `destination` varchar(100) NOT NULL DEFAULT '',
  `total_request_count` int(11) DEFAULT NULL,
  `hostName` varchar(100) NOT NULL DEFAULT '',
  `year` smallint(6) DEFAULT NULL,
  `month` smallint(6) DEFAULT NULL,
  `day` smallint(6) DEFAULT NULL,
  `time` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`api`,`version`,`apiPublisher`,`context`,`destination`,`hostName`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_destination_summary`
--

LOCK TABLES `api_destination_summary` WRITE;
/*!40000 ALTER TABLE `api_destination_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `api_destination_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_fault_summary`
--

DROP TABLE IF EXISTS `api_fault_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_fault_summary` (
  `api` varchar(100) NOT NULL DEFAULT '',
  `version` varchar(100) NOT NULL DEFAULT '',
  `apiPublisher` varchar(100) NOT NULL DEFAULT '',
  `consumerKey` varchar(100) DEFAULT NULL,
  `context` varchar(100) NOT NULL DEFAULT '',
  `total_fault_count` int(11) DEFAULT NULL,
  `hostName` varchar(100) NOT NULL DEFAULT '',
  `year` smallint(6) DEFAULT NULL,
  `month` smallint(6) DEFAULT NULL,
  `day` smallint(6) DEFAULT NULL,
  `time` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`api`,`version`,`apiPublisher`,`context`,`hostName`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_fault_summary`
--

LOCK TABLES `api_fault_summary` WRITE;
/*!40000 ALTER TABLE `api_fault_summary` DISABLE KEYS */;
INSERT INTO `api_fault_summary` VALUES ('smsmessaging','v1','carbon.super',NULL,'/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,12,'2014-08-12 12:28:00'),('smsmessaging','v1','carbon.super',NULL,'/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,13,'2014-08-13 09:31:00'),('smsmessaging','v1','carbon.super',NULL,'/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,13,'2014-08-13 09:33:00');
/*!40000 ALTER TABLE `api_fault_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_request_monthly_summary`
--

DROP TABLE IF EXISTS `api_request_monthly_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_request_monthly_summary` (
  `api` varchar(100) NOT NULL DEFAULT '',
  `api_version` varchar(100) NOT NULL DEFAULT '',
  `version` varchar(100) DEFAULT NULL,
  `consumerKey` varchar(100) NOT NULL DEFAULT '',
  `userId` varchar(100) NOT NULL DEFAULT '',
  `context` varchar(100) NOT NULL DEFAULT '',
  `max_request_time` bigint(20) DEFAULT NULL,
  `total_request_count` int(11) DEFAULT NULL,
  `hostName` varchar(100) NOT NULL DEFAULT '',
  `month` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`api`,`api_version`,`consumerKey`,`userId`,`context`,`hostName`,`month`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_request_monthly_summary`
--

LOCK TABLES `api_request_monthly_summary` WRITE;
/*!40000 ALTER TABLE `api_request_monthly_summary` DISABLE KEYS */;
INSERT INTO `api_request_monthly_summary` VALUES ('smsmessaging','smsmessaging:vv1','v1','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','admin','/smsmessaging',1407756962713,6,'06809-001D-PC.dialog.dialoggsm.com','2014-08'),('smsmessaging','smsmessaging:vv1','v1','OXebB4X8aWCWGWBW9EeytbXh6qwa','testusr','/smsmessaging',1407902587734,2,'06809-001D-PC.dialog.dialoggsm.com','2014-08'),('smsmessaging','smsmessaging:vv1','v1','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/smsmessaging',1407826682142,1,'06809-001D-PC.dialog.dialoggsm.com','2014-08');
/*!40000 ALTER TABLE `api_request_monthly_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_request_summary`
--

DROP TABLE IF EXISTS `api_request_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_request_summary` (
  `api` varchar(100) NOT NULL DEFAULT '',
  `api_version` varchar(100) NOT NULL DEFAULT '',
  `version` varchar(100) DEFAULT NULL,
  `apiPublisher` varchar(100) NOT NULL DEFAULT '',
  `consumerKey` varchar(100) NOT NULL DEFAULT '',
  `userId` varchar(100) NOT NULL DEFAULT '',
  `context` varchar(100) NOT NULL DEFAULT '',
  `max_request_time` bigint(20) DEFAULT NULL,
  `total_request_count` int(11) DEFAULT NULL,
  `hostName` varchar(100) NOT NULL DEFAULT '',
  `year` smallint(6) DEFAULT NULL,
  `month` smallint(6) DEFAULT NULL,
  `day` smallint(6) DEFAULT NULL,
  `time` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`api`,`api_version`,`apiPublisher`,`consumerKey`,`userId`,`context`,`hostName`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_request_summary`
--

LOCK TABLES `api_request_summary` WRITE;
/*!40000 ALTER TABLE `api_request_summary` DISABLE KEYS */;
INSERT INTO `api_request_summary` VALUES ('smsmessaging','smsmessaging:vv1','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','admin','/smsmessaging',1407754461782,1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:24:00'),('smsmessaging','smsmessaging:vv1','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','admin','/smsmessaging',1407754728398,1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:28:00'),('smsmessaging','smsmessaging:vv1','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','admin','/smsmessaging',1407756190304,1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:53:00'),('smsmessaging','smsmessaging:vv1','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','admin','/smsmessaging',1407756455307,2,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:57:00'),('smsmessaging','smsmessaging:vv1','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','admin','/smsmessaging',1407756962713,1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 17:06:00'),('smsmessaging','smsmessaging:vv1','v1','carbon.super','OXebB4X8aWCWGWBW9EeytbXh6qwa','testusr','/smsmessaging',1407902506133,1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,13,'2014-08-13 09:31:00'),('smsmessaging','smsmessaging:vv1','v1','carbon.super','OXebB4X8aWCWGWBW9EeytbXh6qwa','testusr','/smsmessaging',1407902587734,1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,13,'2014-08-13 09:33:00'),('smsmessaging','smsmessaging:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/smsmessaging',1407826682142,1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,12,'2014-08-12 12:28:00');
/*!40000 ALTER TABLE `api_request_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_resource_usage_summary`
--

DROP TABLE IF EXISTS `api_resource_usage_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_resource_usage_summary` (
  `api` varchar(100) NOT NULL DEFAULT '',
  `version` varchar(100) NOT NULL DEFAULT '',
  `apiPublisher` varchar(100) NOT NULL DEFAULT '',
  `consumerKey` varchar(100) NOT NULL DEFAULT '',
  `resourcePath` varchar(100) DEFAULT NULL,
  `context` varchar(100) NOT NULL DEFAULT '',
  `method` varchar(100) NOT NULL DEFAULT '',
  `total_request_count` int(11) DEFAULT NULL,
  `hostName` varchar(100) DEFAULT NULL,
  `year` smallint(6) DEFAULT NULL,
  `month` smallint(6) DEFAULT NULL,
  `day` smallint(6) DEFAULT NULL,
  `time` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`api`,`version`,`apiPublisher`,`consumerKey`,`context`,`method`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_resource_usage_summary`
--

LOCK TABLES `api_resource_usage_summary` WRITE;
/*!40000 ALTER TABLE `api_resource_usage_summary` DISABLE KEYS */;
INSERT INTO `api_resource_usage_summary` VALUES ('smsmessaging','v1','carbon.super','',NULL,'/smsmessaging','POST',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,12,'2014-08-12 12:28:00'),('smsmessaging','v1','carbon.super','',NULL,'/smsmessaging','POST',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,13,'2014-08-13 09:31:00'),('smsmessaging','v1','carbon.super','',NULL,'/smsmessaging','POST',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,13,'2014-08-13 09:33:00'),('smsmessaging','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','/inbound/subscriptions','/smsmessaging','POST',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:24:00'),('smsmessaging','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','/inbound/subscriptions','/smsmessaging','POST',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:28:00'),('smsmessaging','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','/inbound/subscriptions','/smsmessaging','POST',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:53:00'),('smsmessaging','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','/inbound/subscriptions','/smsmessaging','POST',2,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:57:00'),('smsmessaging','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','/inbound/subscriptions','/smsmessaging','POST',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 17:06:00');
/*!40000 ALTER TABLE `api_resource_usage_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_response_summary`
--

DROP TABLE IF EXISTS `api_response_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_response_summary` (
  `api_version` varchar(100) NOT NULL DEFAULT '',
  `apiPublisher` varchar(100) NOT NULL DEFAULT '',
  `context` varchar(100) NOT NULL DEFAULT '',
  `serviceTime` int(11) DEFAULT NULL,
  `total_response_count` int(11) DEFAULT NULL,
  `hostName` varchar(100) NOT NULL DEFAULT '',
  `year` smallint(6) DEFAULT NULL,
  `month` smallint(6) DEFAULT NULL,
  `day` smallint(6) DEFAULT NULL,
  `time` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`api_version`,`apiPublisher`,`context`,`hostName`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_response_summary`
--

LOCK TABLES `api_response_summary` WRITE;
/*!40000 ALTER TABLE `api_response_summary` DISABLE KEYS */;
INSERT INTO `api_response_summary` VALUES ('smsmessaging:vv1','carbon.super','/smsmessaging',20678,1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:24:00'),('smsmessaging:vv1','carbon.super','/smsmessaging',291,1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:28:00'),('smsmessaging:vv1','carbon.super','/smsmessaging',265,1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:53:00'),('smsmessaging:vv1','carbon.super','/smsmessaging',314,2,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:57:00'),('smsmessaging:vv1','carbon.super','/smsmessaging',266,1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 17:06:00');
/*!40000 ALTER TABLE `api_response_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_version_usage_summary`
--

DROP TABLE IF EXISTS `api_version_usage_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_version_usage_summary` (
  `api` varchar(100) NOT NULL DEFAULT '',
  `version` varchar(100) NOT NULL DEFAULT '',
  `apiPublisher` varchar(100) NOT NULL DEFAULT '',
  `context` varchar(100) NOT NULL DEFAULT '',
  `total_request_count` int(11) DEFAULT NULL,
  `hostName` varchar(100) NOT NULL DEFAULT '',
  `year` smallint(6) DEFAULT NULL,
  `month` smallint(6) DEFAULT NULL,
  `day` smallint(6) DEFAULT NULL,
  `time` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`api`,`version`,`apiPublisher`,`context`,`hostName`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_version_usage_summary`
--

LOCK TABLES `api_version_usage_summary` WRITE;
/*!40000 ALTER TABLE `api_version_usage_summary` DISABLE KEYS */;
INSERT INTO `api_version_usage_summary` VALUES ('smsmessaging','v1','carbon.super','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:24:00'),('smsmessaging','v1','carbon.super','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:28:00'),('smsmessaging','v1','carbon.super','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:53:00'),('smsmessaging','v1','carbon.super','/smsmessaging',2,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 16:57:00'),('smsmessaging','v1','carbon.super','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,11,'2014-08-11 17:06:00'),('smsmessaging','v1','carbon.super','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,12,'2014-08-12 12:28:00'),('smsmessaging','v1','carbon.super','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,13,'2014-08-13 09:31:00'),('smsmessaging','v1','carbon.super','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com',2014,8,13,'2014-08-13 09:33:00');
/*!40000 ALTER TABLE `api_version_usage_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_approval_audit`
--

DROP TABLE IF EXISTS `app_approval_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_approval_audit` (
  `APP_NAME` varchar(100) NOT NULL DEFAULT '',
  `APP_CREATOR` varchar(50) NOT NULL DEFAULT '',
  `APP_STATUS` varchar(50) DEFAULT 'ON_HOLD',
  `APP_APPROVAL_TYPE` varchar(50) DEFAULT NULL,
  `COMPLETED_BY_ROLE` varchar(50) NOT NULL DEFAULT '',
  `COMPLETED_BY_USER` varchar(50) DEFAULT NULL,
  `COMPLETED_ON` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`APP_NAME`,`APP_CREATOR`,`COMPLETED_BY_ROLE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_approval_audit`
--

LOCK TABLES `app_approval_audit` WRITE;
/*!40000 ALTER TABLE `app_approval_audit` DISABLE KEYS */;
INSERT INTO `app_approval_audit` VALUES ('111112','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 10:45:49'),('111112sdfsd','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 10:39:15'),('67u6','admin','REJECTED','HUB_ADMIN','admin','admin','2014-09-12 10:04:10'),('77','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 12:37:40'),('9000000000000','admin','APPROVED','HUB_ADMIN','admin','admin','2014-10-03 12:54:16'),('ABC7','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-03 12:05:27'),('ABC7','admin','APPROVED','HUB-OPCO-DIALOG','dialog-admin-role','dialog-user','2014-09-03 12:06:08'),('afgrefgesr','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 09:25:05'),('bbb','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 09:23:46'),('currDemoApp','admin','APPROVED','HUB_ADMIN','admin','admin','2014-10-02 06:55:49'),('currGameApp','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 12:45:44'),('currRelease','admin','APPROVED','HUB_ADMIN','admin','admin','2014-10-02 06:55:28'),('currReleaseDApp','admin','APPROVED','HUB_ADMIN','admin','admin','2014-10-02 05:29:39'),('currReleaseDate','admin','APPROVED','HUB_ADMIN','admin','admin','2014-10-03 12:45:02'),('currTestAppforApp','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 09:35:29'),('CurrTodayApp','admin','APPROVED','HUB_ADMIN','admin','admin','2014-10-02 06:19:56'),('de456564','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-22 05:49:31'),('demoApp','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-22 06:12:33'),('dfg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 06:05:31'),('dfgf','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 08:38:21'),('dfgfd','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 11:50:35'),('dfgfghhhhhhhhhhhhh','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 10:43:44'),('dfgklllll','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 08:38:07'),('dfgreykjk','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 09:48:30'),('dfgsdef','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 08:42:58'),('dgdfsfffsd','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 13:18:46'),('dgfg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-10-03 12:45:36'),('dhdg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 10:49:39'),('dhgd','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 05:52:48'),('dsfvg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 10:48:36'),('dsg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 10:45:56'),('erqeqqqqqqqqqqqqqqqqqqqqq','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 09:39:53'),('ert','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 12:02:40'),('ertwqwetfg111','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 13:33:03'),('etert','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 09:24:15'),('fd','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 12:07:10'),('fdg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 11:44:01'),('fdsgsdf','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 10:44:51'),('ffsd','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 11:56:31'),('fghf','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 12:44:02'),('fh','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 13:39:45'),('fsfre','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 10:53:27'),('fuckdd','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 10:50:20'),('fvgdfsg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 08:50:42'),('gfdsghshghgtytwy56uy56u','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 10:54:43'),('gg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 12:21:56'),('gguyg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 12:42:09'),('gjgjfjhjk','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 11:25:05'),('gsgerg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 05:07:35'),('gugyuy','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 12:01:12'),('hhhhhhhhhhh','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 13:16:27'),('hhhjjj','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 09:45:36'),('hhjj','admin','APPROVED','HUB_ADMIN','admin','admin','2014-10-02 05:29:27'),('iuhjkij','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 05:08:52'),('jfgjkg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 11:57:02'),('jhgj','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 12:42:55'),('jjj34','admin','APPROVED','HUB_ADMIN','admin','admin','2014-10-02 06:50:57'),('jjjjjjjjjj','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 09:51:02'),('jkgf','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 05:11:11'),('kkkkkkkkkkkkkkkkkkkkkkk','admin','APPROVED','HUB_ADMIN','admin','admin','2014-10-03 12:46:06'),('nmr','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 12:51:06'),('oipi','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 12:41:35'),('ouiiuy','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 11:53:25'),('r6udyt','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 08:56:04'),('refgtwer','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 11:44:05'),('rgreg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 12:48:16'),('rtre','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 09:25:36'),('rtrtghrtdhy','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 08:41:41'),('rtyetewt','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 10:42:08'),('sdf','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 13:41:49'),('sdfbttttttttttttttttttttwa','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 11:14:09'),('sdfdsdwhhhh','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 11:11:26'),('sdfdsgdgethrthjh','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 12:07:27'),('sdffffffffffffffffffhh','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 10:41:38'),('sdfsda','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 08:49:43'),('sdfsdwqqqqqqqq','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 12:08:53'),('sfgfsd','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 13:39:22'),('srtg','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 05:52:39'),('tesr','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 09:24:44'),('TestAppNew','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 09:24:23'),('Testnew','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 05:02:13'),('tryut','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 13:39:16'),('tsh','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 11:46:13'),('ttttttttttt','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-15 10:09:45'),('vb','admin','APPROVED','HUB_ADMIN','admin','admin','2014-10-02 07:50:10'),('wer','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 05:52:52'),('wsfew','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-17 08:51:53'),('xf','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-11 13:41:42'),('ytu','admin','APPROVED','HUB_ADMIN','admin','admin','2014-09-12 05:28:32');
/*!40000 ALTER TABLE `app_approval_audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sb_api_request_summary`
--

DROP TABLE IF EXISTS `sb_api_request_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sb_api_request_summary` (
  `messageRowID` varchar(100) NOT NULL,
  `api` varchar(100) DEFAULT NULL,
  `api_version` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `apiPublisher` varchar(100) DEFAULT NULL,
  `consumerKey` varchar(100) DEFAULT NULL,
  `userId` varchar(100) DEFAULT NULL,
  `context` varchar(100) DEFAULT NULL,
  `request_count` int(11) DEFAULT NULL,
  `hostName` varchar(100) DEFAULT NULL,
  `resourcePath` varchar(100) DEFAULT NULL,
  `method` varchar(10) DEFAULT NULL,
  `requestId` varchar(100) DEFAULT NULL,
  `operatorId` varchar(100) DEFAULT NULL,
  `chargeAmount` varchar(20) DEFAULT NULL,
  `jsonBody` text,
  `year` smallint(6) DEFAULT NULL,
  `month` smallint(6) DEFAULT NULL,
  `day` smallint(6) DEFAULT NULL,
  `time` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`messageRowID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sb_api_request_summary`
--

LOCK TABLES `sb_api_request_summary` WRITE;
/*!40000 ALTER TABLE `sb_api_request_summary` DISABLE KEYS */;
INSERT INTO `sb_api_request_summary` VALUES ('1409137532521::172.22.163.29::9445::56','smsmessaging','smsmessaging:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409135478603SM307','DIALOG',NULL,'{\"outboundSMSMessageRequest\":{\"clientCorrelator\":\"123456:1409135478603SM307\",\"callbackData\":\"\",\"address\":[\"tel:+94773335976\"],\"senderAddress\":\"tel:+94773335976\",\"senderName\":\"ACME Inc.\",\"outboundSMSTextMessage\":{\"message\":\"Hello World\"},\"receiptRequest\":{\"notifyURL\":\"http://application.example.com/notifications/DeliveryInfoNotification\",\"callbackData\":\"some-data-useful-to-the-requester\"}}}',2014,8,27,'2014-08-27 16:35:32'),('1409137744984::172.22.163.29::9445::3','smsmessaging','smsmessaging:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409135478603SM308','DIALOG',NULL,'{\"outboundSMSMessageRequest\":{\"clientCorrelator\":\"123456:1409135478603SM308\",\"callbackData\":\"\",\"address\":[\"tel:+94773335976\"],\"senderAddress\":\"tel:+94773335976\",\"senderName\":\"ACME Inc.\",\"outboundSMSTextMessage\":{\"message\":\"Hello World\"},\"receiptRequest\":{\"notifyURL\":\"http://application.example.com/notifications/DeliveryInfoNotification\",\"callbackData\":\"some-data-useful-to-the-requester\"}}}',2014,8,27,'2014-08-27 16:39:04'),('1409137797547::172.22.163.29::9445::5','smsmessaging','smsmessaging:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409135478603SM309','DIALOG',NULL,'{\"outboundSMSMessageRequest\":{\"clientCorrelator\":\"123456:1409135478603SM309\",\"callbackData\":\"\",\"address\":[\"tel:+94773335976\"],\"senderAddress\":\"tel:+94773335976\",\"senderName\":\"ACME Inc.\",\"outboundSMSTextMessage\":{\"message\":\"Hello World\"},\"receiptRequest\":{\"notifyURL\":\"http://application.example.com/notifications/DeliveryInfoNotification\",\"callbackData\":\"some-data-useful-to-the-requester\"}}}',2014,8,27,'2014-08-27 16:39:57'),('1409143861146::172.22.163.29::9445::8','smsmessaging','smsmessaging:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409135478603SM3010','DIALOG',NULL,'{\"outboundSMSMessageRequest\":{\"clientCorrelator\":\"123456:1409135478603SM3010\",\"callbackData\":\"\",\"address\":[\"tel:+94773335976\"],\"senderAddress\":\"tel:+94773335976\",\"senderName\":\"ACME Inc.\",\"outboundSMSTextMessage\":{\"message\":\"Hello World\"},\"receiptRequest\":{\"notifyURL\":\"http://application.example.com/notifications/DeliveryInfoNotification\",\"callbackData\":\"some-data-useful-to-the-requester\"}}}',2014,8,27,'2014-08-27 18:21:01'),('1409144445549::172.22.163.29::9445::19','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409144363883PA302','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409144363883PA302\",\"referenceCode\":\"REF-12345\"}}',2014,8,27,'2014-08-27 18:30:45'),('1409144585218::172.22.163.29::9445::23','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409144363883PA303','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409144363883PA303\",\"referenceCode\":\"REF-12345\"}}',2014,8,27,'2014-08-27 18:33:05'),('1409144594114::172.22.163.29::9445::27','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409144363883PA304','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409144363883PA304\",\"referenceCode\":\"REF-12345\"}}',2014,8,27,'2014-08-27 18:33:14'),('1409144714992::172.22.163.29::9445::31','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409144363883PA305','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335975\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409144363883PA305\",\"referenceCode\":\"REF-12345\"}}',2014,8,27,'2014-08-27 18:35:14'),('1409145188448::172.22.163.29::9445::34','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409145188404PA300','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335975\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409145188404PA300\",\"referenceCode\":\"REF-12345\"}}',2014,8,27,'2014-08-27 18:43:08'),('1409145202861::172.22.163.29::9445::37','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409145188404PA301','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409145188404PA301\",\"referenceCode\":\"REF-12345\"}}',2014,8,27,'2014-08-27 18:43:22'),('1409145327202::172.22.163.29::9445::41','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409145188404PA302','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409145188404PA302\",\"referenceCode\":\"REF-12345\"}}',2014,8,27,'2014-08-27 18:45:27'),('1409145584276::172.22.163.29::9445::45','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409145584231PA300','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409145584231PA300\",\"referenceCode\":\"REF-12345\"}}',2014,8,27,'2014-08-27 18:49:44'),('1409292883933::172.22.163.29::9445::10','payment','payment:vv1','v1','carbon.super','ZfnyIW2_7EXx9657tZNSfOENfRYa','celcomonly','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA1206','CELCOM','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+60773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409292578698PA1206\",\"referenceCode\":\"REF-12345\"}}',2014,8,29,'2014-08-29 11:44:43'),('1409292938688::172.22.163.29::9445::13','payment','payment:vv1','v1','carbon.super','ZfnyIW2_7EXx9657tZNSfOENfRYa','celcomonly','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA1207','CELCOM','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+60773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409292578698PA1207\",\"referenceCode\":\"REF-12345\"}}',2014,8,29,'2014-08-29 11:45:38'),('1409293039356::172.22.163.29::9445::17','payment','payment:vv1','v1','carbon.super','bfFwSyRFy4kmAhtMYRYDAR4glAsa','dialogonly','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA809','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409292578698PA809\",\"referenceCode\":\"REF-12345\"}}',2014,8,29,'2014-08-29 11:47:19'),('1409293088229::172.22.163.29::9445::21','smsmessaging','smsmessaging:vv1','v1','carbon.super','ZfnyIW2_7EXx9657tZNSfOENfRYa','celcomonly','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409292578698SM12010','CELCOM',NULL,'{\"outboundSMSMessageRequest\":{\"clientCorrelator\":\"123456:1409292578698SM12010\",\"callbackData\":\"\",\"address\":[\"tel:+60773335976\"],\"senderAddress\":\"tel:+94773335976\",\"senderName\":\"ACME Inc.\",\"outboundSMSTextMessage\":{\"message\":\"Hello World\"},\"receiptRequest\":{\"notifyURL\":\"http://application.example.com/notifications/DeliveryInfoNotification\",\"callbackData\":\"some-data-useful-to-the-requester\"}}}',2014,8,29,'2014-08-29 11:48:08'),('1409293238546::172.22.163.29::9445::25','smsmessaging','smsmessaging:vv1','v1','carbon.super','bfFwSyRFy4kmAhtMYRYDAR4glAsa','dialogonly','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com','/inbound/subscriptions','POST','1409292578698RS8012','DIALOG',NULL,'{\"subscription\":{\"clientCorrelator\":\"12345:1409292578698RS8012\",\"criteria\":\"Newtest2\",\"destinationAddress\":\"tel:+94773335976\",\"callbackReference\":{\"notifyURL\":\"https://gateway1a.mife.sla-mobile.com.my:8243/smsmessaging/v1/DeliveryInfoNotification/118\",\"callbackData\":\"doSomething()\"},\"notificationFormat\":\"JSON\"}}',2014,8,29,'2014-08-29 11:50:38'),('1409293260615::172.22.163.29::9445::28','smsmessaging','smsmessaging:vv1','v1','carbon.super','bfFwSyRFy4kmAhtMYRYDAR4glAsa','dialogonly','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com','/inbound/subscriptions','POST','1409292578698RS8013','DIALOG',NULL,'{\"subscription\":{\"clientCorrelator\":\"12345:1409292578698RS8013\",\"criteria\":\"Newtest2\",\"destinationAddress\":\"94773335976\",\"callbackReference\":{\"notifyURL\":\"https://gateway1a.mife.sla-mobile.com.my:8243/smsmessaging/v1/DeliveryInfoNotification/119\",\"callbackData\":\"doSomething()\"},\"notificationFormat\":\"JSON\"}}',2014,8,29,'2014-08-29 11:51:00'),('1409293283593::172.22.163.29::9445::31','smsmessaging','smsmessaging:vv1','v1','carbon.super','ZfnyIW2_7EXx9657tZNSfOENfRYa','celcomonly','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com','/inbound/subscriptions','POST','1409292578698RS12014','CELCOM',NULL,'{\"subscription\":{\"clientCorrelator\":\"12345:1409292578698RS12014\",\"criteria\":\"Newtest2\",\"destinationAddress\":\"60773335976\",\"callbackReference\":{\"notifyURL\":\"https://gateway1a.mife.sla-mobile.com.my:8243/smsmessaging/v1/DeliveryInfoNotification/120\",\"callbackData\":\"doSomething()\"},\"notificationFormat\":\"JSON\"}}',2014,8,29,'2014-08-29 11:51:23'),('1409293309668::172.22.163.29::9445::34','payment','payment:vv1','v1','carbon.super','N8K4fwGyIwkDg9CZvHr526SNZyYa','dialogcelcom','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA14015','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409292578698PA14015\",\"referenceCode\":\"REF-12345\"}}',2014,8,29,'2014-08-29 11:51:49'),('1409293331415::172.22.163.29::9445::38','smsmessaging','smsmessaging:vv1','v1','carbon.super','N8K4fwGyIwkDg9CZvHr526SNZyYa','dialogcelcom','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409292578698SM14016','CELCOM',NULL,'{\"outboundSMSMessageRequest\":{\"clientCorrelator\":\"123456:1409292578698SM14016\",\"callbackData\":\"\",\"address\":[\"tel:+60773335976\"],\"senderAddress\":\"tel:+94773335976\",\"senderName\":\"ACME Inc.\",\"outboundSMSTextMessage\":{\"message\":\"Hello World\"},\"receiptRequest\":{\"notifyURL\":\"http://application.example.com/notifications/DeliveryInfoNotification\",\"callbackData\":\"some-data-useful-to-the-requester\"}}}',2014,8,29,'2014-08-29 11:52:11'),('1409293350314::172.22.163.29::9445::41','smsmessaging','smsmessaging:vv1','v1','carbon.super','N8K4fwGyIwkDg9CZvHr526SNZyYa','dialogcelcom','/smsmessaging',1,'06809-001D-PC.dialog.dialoggsm.com','/inbound/subscriptions','POST','1409292578698RS14017','DIALOG',NULL,'{\"subscription\":{\"clientCorrelator\":\"12345:1409292578698RS14017\",\"criteria\":\"Newtest2\",\"destinationAddress\":\"94773335976\",\"callbackReference\":{\"notifyURL\":\"https://gateway1a.mife.sla-mobile.com.my:8243/smsmessaging/v1/DeliveryInfoNotification/121\",\"callbackData\":\"doSomething()\"},\"notificationFormat\":\"JSON\"}}',2014,8,29,'2014-08-29 11:52:30'),('1409293665119::172.22.163.29::9445::44','payment','payment:vv1','v1','carbon.super','bfFwSyRFy4kmAhtMYRYDAR4glAsa','dialogonly','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA8018','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409292578698PA8018\",\"referenceCode\":\"REF-12345\"}}',2014,8,29,'2014-08-29 11:57:45'),('1409293672601::172.22.163.29::9445::48','payment','payment:vv1','v1','carbon.super','bfFwSyRFy4kmAhtMYRYDAR4glAsa','dialogonly','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA8019','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409292578698PA8019\",\"referenceCode\":\"REF-12345\"}}',2014,8,29,'2014-08-29 11:57:52'),('1409294211632::172.22.163.29::9445::52','payment','payment:vv1','v1','carbon.super','ZfnyIW2_7EXx9657tZNSfOENfRYa','celcomonly','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B60773335976/transactions/amount/','POST','1409292578698PA12020','CELCOM','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+60773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409292578698PA12020\",\"referenceCode\":\"REF-12345\"}}',2014,8,29,'2014-08-29 12:06:51'),('1409553284647::172.22.163.29::9445::3','payment','payment:vv1','v1','carbon.super','N8K4fwGyIwkDg9CZvHr526SNZyYa','dialogcelcom','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409553230629PA1401','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409553230629PA1401\",\"referenceCode\":\"REF-12345\"}}',2014,9,1,'2014-09-01 12:04:44'),('1409553290658::172.22.163.29::9445::7','payment','payment:vv1','v1','carbon.super','N8K4fwGyIwkDg9CZvHr526SNZyYa','dialogcelcom','/payment',1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409553230629PA1402','DIALOG','2','{\"amountTransaction\":{\"paymentAmount\":{\"chargingMetaData\":{\"taxAmount\":\"0\",\"purchaseCategoryCode\":\"Game\",\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\"},\"chargingInformation\":{\"amount\":\"2\",\"description\":[\"Alien Invaders Game\",\"Test array 2\"],\"currency\":\"LKR\"}},\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Charged\",\"clientCorrelator\":\"54321:1409553230629PA1402\",\"referenceCode\":\"REF-12345\"}}',2014,9,1,'2014-09-01 12:04:50');
/*!40000 ALTER TABLE `sb_api_request_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sb_api_response_summary`
--

DROP TABLE IF EXISTS `sb_api_response_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sb_api_response_summary` (
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
  `operatorRef` varchar(100) DEFAULT NULL,
  `chargeAmount` varchar(20) DEFAULT NULL,
  `jsonBody` text,
  `year` smallint(6) DEFAULT NULL,
  `month` smallint(6) DEFAULT NULL,
  `day` smallint(6) DEFAULT NULL,
  `time` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`messageRowID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sb_api_response_summary`
--

LOCK TABLES `sb_api_response_summary` WRITE;
/*!40000 ALTER TABLE `sb_api_response_summary` DISABLE KEYS */;
INSERT INTO `sb_api_response_summary` VALUES ('1409137532616::172.22.163.29::9445::55','smsmessaging','smsmessaging:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/smsmessaging',100,1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409135478603SM307','DIALOG','201',NULL,NULL,'{\"outboundSMSMessageRequest\":{\"address\":[\"tel:+94773335976\"],\"receiptRequest\":{\"notifyURL\":\"http://application.example.com/notifications/DeliveryInfoNotification\",\"callbackData\":\"some-data-useful-to-the-requester\"},\"outboundSMSTextMessage\":{\"message\":\"Hello World\"},\"deliveryInfoList\":{\"deliveryInfo\":[{\"address\":\"tel:+94773335976\",\"deliveryStatus\":\"MessageWaiting\"}],\"resourceURL\":\"http://localhost:18080/mifesandbox/SendSMSService/smsmessaging/1/outbound/tel%3A%2B94773335976/requests/123456%3A1409135478603SM307\"},\"senderName\":\"ACME Inc.\",\"clientCorrelator\":\"123456:1409135478603SM307\",\"senderAddress\":\"tel:+94773335976\",\"resourceURL\":\"http://localhost:18080/mifesandbox/SendSMSService/smsmessaging/1/outbound/tel%3A%2B94773335976/requests/123456%3A1409135478603SM307\"}}',2014,8,27,'2014-08-27 16:35:32'),('1409137745108::172.22.163.29::9445::4','smsmessaging','smsmessaging:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/smsmessaging',124,1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409135478603SM308','DIALOG','201',NULL,NULL,'{\"outboundSMSMessageRequest\":{\"address\":[\"tel:+94773335976\"],\"receiptRequest\":{\"notifyURL\":\"http://application.example.com/notifications/DeliveryInfoNotification\",\"callbackData\":\"some-data-useful-to-the-requester\"},\"outboundSMSTextMessage\":{\"message\":\"Hello World\"},\"deliveryInfoList\":{\"deliveryInfo\":[{\"address\":\"tel:+94773335976\",\"deliveryStatus\":\"MessageWaiting\"}],\"resourceURL\":\"http://localhost:18080/mifesandbox/SendSMSService/smsmessaging/1/outbound/tel%3A%2B94773335976/requests/123456%3A1409135478603SM308\"},\"senderName\":\"ACME Inc.\",\"clientCorrelator\":\"123456:1409135478603SM308\",\"senderAddress\":\"tel:+94773335976\",\"resourceURL\":\"http://localhost:18080/mifesandbox/SendSMSService/smsmessaging/1/outbound/tel%3A%2B94773335976/requests/123456%3A1409135478603SM308\"}}',2014,8,27,'2014-08-27 16:39:05'),('1409137797629::172.22.163.29::9445::6','smsmessaging','smsmessaging:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/smsmessaging',82,1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409135478603SM309','DIALOG','201',NULL,NULL,'{\"outboundSMSMessageRequest\":{\"address\":[\"tel:+94773335976\"],\"receiptRequest\":{\"notifyURL\":\"http://application.example.com/notifications/DeliveryInfoNotification\",\"callbackData\":\"some-data-useful-to-the-requester\"},\"outboundSMSTextMessage\":{\"message\":\"Hello World\"},\"deliveryInfoList\":{\"deliveryInfo\":[{\"address\":\"tel:+94773335976\",\"deliveryStatus\":\"MessageWaiting\"}],\"resourceURL\":\"http://localhost:18080/mifesandbox/SendSMSService/smsmessaging/1/outbound/tel%3A%2B94773335976/requests/123456%3A1409135478603SM309\"},\"senderName\":\"ACME Inc.\",\"clientCorrelator\":\"123456:1409135478603SM309\",\"senderAddress\":\"tel:+94773335976\",\"resourceURL\":\"http://localhost:18080/mifesandbox/SendSMSService/smsmessaging/1/outbound/tel%3A%2B94773335976/requests/123456%3A1409135478603SM309\"}}',2014,8,27,'2014-08-27 16:39:57'),('1409143861192::172.22.163.29::9445::9','smsmessaging','smsmessaging:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/smsmessaging',46,1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409135478603SM3010','DIALOG','201',NULL,NULL,'{\"outboundSMSMessageRequest\":{\"address\":[\"tel:+94773335976\"],\"receiptRequest\":{\"notifyURL\":\"http://application.example.com/notifications/DeliveryInfoNotification\",\"callbackData\":\"some-data-useful-to-the-requester\"},\"outboundSMSTextMessage\":{\"message\":\"Hello World\"},\"deliveryInfoList\":{\"deliveryInfo\":[{\"address\":\"tel:+94773335976\",\"deliveryStatus\":\"MessageWaiting\"}],\"resourceURL\":\"http://localhost:18080/mifesandbox/SendSMSService/smsmessaging/1/outbound/tel%3A%2B94773335976/requests/123456%3A1409135478603SM3010\"},\"senderName\":\"ACME Inc.\",\"clientCorrelator\":\"123456:1409135478603SM3010\",\"senderAddress\":\"tel:+94773335976\",\"resourceURL\":\"http://localhost:18080/mifesandbox/SendSMSService/smsmessaging/1/outbound/tel%3A%2B94773335976/requests/123456%3A1409135478603SM3010\"}}',2014,8,27,'2014-08-27 18:21:01'),('1409144445879::172.22.163.29::9445::20','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',334,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409144363883PA302','DIALOG','201','','2','{\"amountTransaction\":{\"endUserId\":\"tel:+94773335976\",\"referenceCode\":\"REF-12345\",\"transactionOperationStatus\":\"Processing\",\"clientCorrelator\":\"54321:1409144363883PA302\",\"paymentAmount\":{\"chargingMetaData\":{\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0,\"purchaseCategoryCode\":\"Game\"},\"chargingInformation\":{\"currency\":\"LKR\",\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"amount\":2.0},\"totalAmountCharged\":1450.0}}}',2014,8,27,'2014-08-27 18:30:45'),('1409144585309::172.22.163.29::9445::24','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',91,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409144363883PA303','DIALOG','201','','2','{\"amountTransaction\":{\"endUserId\":\"tel:+94773335976\",\"referenceCode\":\"REF-12345\",\"transactionOperationStatus\":\"Processing\",\"clientCorrelator\":\"54321:1409144363883PA303\",\"paymentAmount\":{\"chargingMetaData\":{\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0,\"purchaseCategoryCode\":\"Game\"},\"chargingInformation\":{\"currency\":\"LKR\",\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"amount\":2.0},\"totalAmountCharged\":1450.0}}}',2014,8,27,'2014-08-27 18:33:05'),('1409144594532::172.22.163.29::9445::28','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',417,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409144363883PA304','DIALOG','201','','2','{\"amountTransaction\":{\"endUserId\":\"tel:+94773335976\",\"referenceCode\":\"REF-12345\",\"transactionOperationStatus\":\"Processing\",\"clientCorrelator\":\"54321:1409144363883PA304\",\"paymentAmount\":{\"chargingMetaData\":{\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0,\"purchaseCategoryCode\":\"Game\"},\"chargingInformation\":{\"currency\":\"LKR\",\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"amount\":2.0},\"totalAmountCharged\":1450.0}}}',2014,8,27,'2014-08-27 18:33:14'),('1409144715010::172.22.163.29::9445::32','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',18,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409144363883PA305','DIALOG','400',NULL,'2','{\"requestError\":{\"policyException\":{\"text\":\"User has not been provisioned for Charge Amount\",\"messageId\":\"POL1009\",\"variables\":null}}}',2014,8,27,'2014-08-27 18:35:15'),('1409145188477::172.22.163.29::9445::35','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',31,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409145188404PA300','DIALOG','400',NULL,'2','{\"requestError\":{\"policyException\":{\"text\":\"User has not been provisioned for Charge Amount\",\"messageId\":\"POL1009\",\"variables\":null}}}',2014,8,27,'2014-08-27 18:43:08'),('1409145202965::172.22.163.29::9445::38','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',104,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409145188404PA301','DIALOG','201','','2','{\"amountTransaction\":{\"endUserId\":\"tel:+94773335976\",\"referenceCode\":\"REF-12345\",\"transactionOperationStatus\":\"Processing\",\"clientCorrelator\":\"54321:1409145188404PA301\",\"paymentAmount\":{\"chargingMetaData\":{\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0,\"purchaseCategoryCode\":\"Game\"},\"chargingInformation\":{\"currency\":\"LKR\",\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"amount\":2.0},\"totalAmountCharged\":1450.0}}}',2014,8,27,'2014-08-27 18:43:22'),('1409145327269::172.22.163.29::9445::43','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',67,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409145188404PA302','DIALOG','201','','2','{\"amountTransaction\":{\"endUserId\":\"tel:+94773335976\",\"referenceCode\":\"REF-12345\",\"transactionOperationStatus\":\"Processing\",\"clientCorrelator\":\"54321:1409145188404PA302\",\"paymentAmount\":{\"chargingMetaData\":{\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0,\"purchaseCategoryCode\":\"Game\"},\"chargingInformation\":{\"currency\":\"LKR\",\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"amount\":2.0},\"totalAmountCharged\":1450.0}}}',2014,8,27,'2014-08-27 18:45:27'),('1409145584473::172.22.163.29::9445::46','payment','payment:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/payment',200,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409145584231PA300','DIALOG','201','','2','{\"amountTransaction\":{\"endUserId\":\"tel:+94773335976\",\"referenceCode\":\"REF-12345\",\"transactionOperationStatus\":\"Processing\",\"clientCorrelator\":\"54321:1409145584231PA300\",\"paymentAmount\":{\"chargingMetaData\":{\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0,\"purchaseCategoryCode\":\"Game\"},\"chargingInformation\":{\"currency\":\"LKR\",\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"amount\":2.0},\"totalAmountCharged\":1450.0}}}',2014,8,27,'2014-08-27 18:49:44'),('1409292885805::172.22.163.29::9445::11','payment','payment:vv1','v1','carbon.super','ZfnyIW2_7EXx9657tZNSfOENfRYa','celcomonly','/payment',1876,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA1206','CELCOM','400',NULL,'2','{\"requestError\":{\"policyException\":{\"text\":\"User has not been provisioned for Charge Amount\",\"messageId\":\"POL1009\",\"variables\":null}}}',2014,8,29,'2014-08-29 11:44:45'),('1409292938743::172.22.163.29::9445::14','payment','payment:vv1','v1','carbon.super','ZfnyIW2_7EXx9657tZNSfOENfRYa','celcomonly','/payment',56,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA1207','CELCOM','400',NULL,'2','{\"requestError\":{\"policyException\":{\"text\":\"User has not been provisioned for Charge Amount\",\"messageId\":\"POL1009\",\"variables\":null}}}',2014,8,29,'2014-08-29 11:45:38'),('1409293039536::172.22.163.29::9445::18','payment','payment:vv1','v1','carbon.super','bfFwSyRFy4kmAhtMYRYDAR4glAsa','dialogonly','/payment',180,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA809','DIALOG','201','','2','{\"amountTransaction\":{\"transactionOperationStatus\":\"Processing\",\"referenceCode\":\"REF-12345\",\"paymentAmount\":{\"totalAmountCharged\":1450.0,\"chargingMetaData\":{\"channel\":\"WAP\",\"purchaseCategoryCode\":\"Game\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0},\"chargingInformation\":{\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"currency\":\"LKR\",\"amount\":2.0}},\"clientCorrelator\":\"54321:1409292578698PA809\",\"endUserId\":\"tel:+94773335976\"}}',2014,8,29,'2014-08-29 11:47:19'),('1409293088311::172.22.163.29::9445::22','smsmessaging','smsmessaging:vv1','v1','carbon.super','ZfnyIW2_7EXx9657tZNSfOENfRYa','celcomonly','/smsmessaging',82,1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409292578698SM12010','CELCOM','400',NULL,NULL,'{\"requestError\":{\"serviceException\":{\"text\":\"A service error occurred. Error code is %1\",\"messageId\":\"SVC0001\",\"variables\":[\"tel:+60773335976 Not Whitelisted\"]}}}',2014,8,29,'2014-08-29 11:48:08'),('1409293238636::172.22.163.29::9445::26','smsmessaging','smsmessaging:vv1','v1','carbon.super','bfFwSyRFy4kmAhtMYRYDAR4glAsa','dialogonly','/smsmessaging',90,1,'06809-001D-PC.dialog.dialoggsm.com','/inbound/subscriptions','POST','1409292578698RS8012','DIALOG','400',NULL,NULL,'{\"requestError\":{\"serviceException\":{\"text\":\"A service error occurred. Error code is %1\",\"messageId\":\"SVC0001\",\"variables\":[\"tel:+94773335976 Not Provisioned\"]}}}',2014,8,29,'2014-08-29 11:50:38'),('1409293260737::172.22.163.29::9445::29','smsmessaging','smsmessaging:vv1','v1','carbon.super','bfFwSyRFy4kmAhtMYRYDAR4glAsa','dialogonly','/smsmessaging',122,1,'06809-001D-PC.dialog.dialoggsm.com','/inbound/subscriptions','POST','1409292578698RS8013','DIALOG','201',NULL,NULL,'{\"subscription\":{\"resourceURL\":\"http://localhost:18080/mifesandbox/SMSReceiptService/smsmessaging/1/inbound/subscriptions/77\",\"clientCorrelator\":\"12345:1409292578698RS8013\",\"destinationAddress\":\"94773335976\",\"notificationFormat\":\"JSON\",\"criteria\":\"Newtest2\",\"callbackReference\":{\"callbackData\":\"doSomething()\",\"notifyURL\":\"https://gateway1a.mife.sla-mobile.com.my:8243/smsmessaging/v1/DeliveryInfoNotification/119\"}}}',2014,8,29,'2014-08-29 11:51:00'),('1409293283608::172.22.163.29::9445::32','smsmessaging','smsmessaging:vv1','v1','carbon.super','ZfnyIW2_7EXx9657tZNSfOENfRYa','celcomonly','/smsmessaging',15,1,'06809-001D-PC.dialog.dialoggsm.com','/inbound/subscriptions','POST','1409292578698RS12014','CELCOM','400',NULL,NULL,'{\"requestError\":{\"serviceException\":{\"text\":\"A service error occurred. Error code is %1\",\"messageId\":\"SVC0001\",\"variables\":[\"60773335976 Not Provisioned\"]}}}',2014,8,29,'2014-08-29 11:51:23'),('1409293309757::172.22.163.29::9445::35','payment','payment:vv1','v1','carbon.super','N8K4fwGyIwkDg9CZvHr526SNZyYa','dialogcelcom','/payment',89,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA14015','DIALOG','201','','2','{\"amountTransaction\":{\"transactionOperationStatus\":\"Processing\",\"referenceCode\":\"REF-12345\",\"paymentAmount\":{\"totalAmountCharged\":1450.0,\"chargingMetaData\":{\"channel\":\"WAP\",\"purchaseCategoryCode\":\"Game\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0},\"chargingInformation\":{\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"currency\":\"LKR\",\"amount\":2.0}},\"clientCorrelator\":\"54321:1409292578698PA14015\",\"endUserId\":\"tel:+94773335976\"}}',2014,8,29,'2014-08-29 11:51:49'),('1409293331432::172.22.163.29::9445::39','smsmessaging','smsmessaging:vv1','v1','carbon.super','N8K4fwGyIwkDg9CZvHr526SNZyYa','dialogcelcom','/smsmessaging',17,1,'06809-001D-PC.dialog.dialoggsm.com','/outbound/26451/requests','POST','1409292578698SM14016','CELCOM','400',NULL,NULL,'{\"requestError\":{\"serviceException\":{\"text\":\"A service error occurred. Error code is %1\",\"messageId\":\"SVC0001\",\"variables\":[\"tel:+60773335976 Not Whitelisted\"]}}}',2014,8,29,'2014-08-29 11:52:11'),('1409293350405::172.22.163.29::9445::42','smsmessaging','smsmessaging:vv1','v1','carbon.super','N8K4fwGyIwkDg9CZvHr526SNZyYa','dialogcelcom','/smsmessaging',91,1,'06809-001D-PC.dialog.dialoggsm.com','/inbound/subscriptions','POST','1409292578698RS14017','DIALOG','400',NULL,NULL,'{\"requestError\":{\"serviceException\":{\"text\":\"Overlapped criteria %1\",\"messageId\":\"SVC0008\",\"variables\":[\"Newtest2\"]}}}',2014,8,29,'2014-08-29 11:52:30'),('1409293665175::172.22.163.29::9445::45','payment','payment:vv1','v1','carbon.super','bfFwSyRFy4kmAhtMYRYDAR4glAsa','dialogonly','/payment',56,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA8018','DIALOG','201','','2','{\"amountTransaction\":{\"transactionOperationStatus\":\"Processing\",\"referenceCode\":\"REF-12345\",\"paymentAmount\":{\"totalAmountCharged\":1450.0,\"chargingMetaData\":{\"channel\":\"WAP\",\"purchaseCategoryCode\":\"Game\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0},\"chargingInformation\":{\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"currency\":\"LKR\",\"amount\":2.0}},\"clientCorrelator\":\"54321:1409292578698PA8018\",\"endUserId\":\"tel:+94773335976\"}}',2014,8,29,'2014-08-29 11:57:45'),('1409293672810::172.22.163.29::9445::49','payment','payment:vv1','v1','carbon.super','bfFwSyRFy4kmAhtMYRYDAR4glAsa','dialogonly','/payment',209,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409292578698PA8019','DIALOG','201','','2','{\"amountTransaction\":{\"transactionOperationStatus\":\"Processing\",\"referenceCode\":\"REF-12345\",\"paymentAmount\":{\"totalAmountCharged\":1450.0,\"chargingMetaData\":{\"channel\":\"WAP\",\"purchaseCategoryCode\":\"Game\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0},\"chargingInformation\":{\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"currency\":\"LKR\",\"amount\":2.0}},\"clientCorrelator\":\"54321:1409292578698PA8019\",\"endUserId\":\"tel:+94773335976\"}}',2014,8,29,'2014-08-29 11:57:52'),('1409294211681::172.22.163.29::9445::53','payment','payment:vv1','v1','carbon.super','ZfnyIW2_7EXx9657tZNSfOENfRYa','celcomonly','/payment',49,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B60773335976/transactions/amount/','POST','1409292578698PA12020','CELCOM','400',NULL,'2','{\"requestError\":{\"policyException\":{\"text\":\"User has not been provisioned for Charge Amount\",\"messageId\":\"POL1009\",\"variables\":null}}}',2014,8,29,'2014-08-29 12:06:51'),('1409553284917::172.22.163.29::9445::4','payment','payment:vv1','v1','carbon.super','N8K4fwGyIwkDg9CZvHr526SNZyYa','dialogcelcom','/payment',273,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409553230629PA1401','DIALOG','201','SB1-1409552778617','2','{\"amountTransaction\":{\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Processing\",\"referenceCode\":\"REF-12345\",\"serverReferenceCode\":\"SB1-1409552778617\",\"paymentAmount\":{\"totalAmountCharged\":1450.0,\"chargingMetaData\":{\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0,\"purchaseCategoryCode\":\"Game\"},\"chargingInformation\":{\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"currency\":\"LKR\",\"amount\":2.0}},\"clientCorrelator\":\"54321:1409553230629PA1401\"}}',2014,9,1,'2014-09-01 12:04:44'),('1409553290731::172.22.163.29::9445::8','payment','payment:vv1','v1','carbon.super','N8K4fwGyIwkDg9CZvHr526SNZyYa','dialogcelcom','/payment',73,1,'06809-001D-PC.dialog.dialoggsm.com','/tel%3A%2B94773335976/transactions/amount/','POST','1409553230629PA1402','DIALOG','201','SB2-1409552778617','2','{\"amountTransaction\":{\"endUserId\":\"tel:+94773335976\",\"transactionOperationStatus\":\"Processing\",\"referenceCode\":\"REF-12345\",\"serverReferenceCode\":\"SB2-1409552778617\",\"paymentAmount\":{\"totalAmountCharged\":1450.0,\"chargingMetaData\":{\"channel\":\"WAP\",\"onBehalfOf\":\"Example Games Inc\",\"taxAmount\":0.0,\"purchaseCategoryCode\":\"Game\"},\"chargingInformation\":{\"description\":\"[\\\"Alien Invaders Game\\\",\\\"Test array 2\\\"]\",\"currency\":\"LKR\",\"amount\":2.0}},\"clientCorrelator\":\"54321:1409553230629PA1402\"}}',2014,9,1,'2014-09-01 12:04:50');
/*!40000 ALTER TABLE `sb_api_response_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sb_api_response_summary_back`
--

DROP TABLE IF EXISTS `sb_api_response_summary_back`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sb_api_response_summary_back` (
  `messageRowID` varchar(100) NOT NULL,
  `api` varchar(100) NOT NULL DEFAULT '',
  `api_version` varchar(100) NOT NULL DEFAULT '',
  `version` varchar(100) DEFAULT NULL,
  `apiPublisher` varchar(100) NOT NULL DEFAULT '',
  `consumerKey` varchar(100) NOT NULL DEFAULT '',
  `userId` varchar(100) NOT NULL DEFAULT '',
  `context` varchar(100) NOT NULL DEFAULT '',
  `serviceTime_time` bigint(20) DEFAULT NULL,
  `response_count` int(11) DEFAULT NULL,
  `hostName` varchar(100) NOT NULL DEFAULT '',
  `resourcePath` varchar(100) DEFAULT NULL,
  `year` smallint(6) DEFAULT NULL,
  `month` smallint(6) DEFAULT NULL,
  `day` smallint(6) DEFAULT NULL,
  `time` varchar(30) NOT NULL DEFAULT '',
  `requestid` varchar(100) DEFAULT NULL,
  `operatorId` varchar(100) DEFAULT NULL,
  `responseCode` varchar(5) DEFAULT NULL,
  `method` varchar(10) DEFAULT NULL,
  `operatorRef` varchar(100) DEFAULT NULL,
  `chargeAmount` varchar(20) DEFAULT NULL,
  `jsonBody` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sb_api_response_summary_back`
--

LOCK TABLES `sb_api_response_summary_back` WRITE;
/*!40000 ALTER TABLE `sb_api_response_summary_back` DISABLE KEYS */;
INSERT INTO `sb_api_response_summary_back` VALUES ('113','smsmessaging','smsmessaging:vv1','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','admin','/smsmessaging',1407754461782,1,'06809-001D-PC.dialog.dialoggsm.com',NULL,2014,8,11,'2014-08-11 16:24:00','113','DIALOG','200',NULL,NULL,NULL,NULL),('124','smsmessaging','smsmessaging:vv1','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','admin','/smsmessaging',1407754728398,1,'06809-001D-PC.dialog.dialoggsm.com',NULL,2014,8,11,'2014-08-11 16:28:00','124','DIALOG','400',NULL,NULL,NULL,NULL),('125','smsmessaging','smsmessaging:vv1','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','admin','/smsmessaging',1407756190304,1,'06809-001D-PC.dialog.dialoggsm.com',NULL,2014,8,11,'2014-08-11 16:53:00','125','DIALOG','200',NULL,NULL,NULL,NULL),('126','smsmessaging','smsmessaging:vv1','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','admin','/smsmessaging',1407756455307,2,'06809-001D-PC.dialog.dialoggsm.com',NULL,2014,8,11,'2014-08-11 16:57:00','126','CELCOM','201',NULL,NULL,NULL,NULL),('127','smsmessaging','smsmessaging:vv1','v1','carbon.super','fjBuJOfMdKs0Cr7v0oEdVOUq5pka','admin','/smsmessaging',1407756962713,1,'06809-001D-PC.dialog.dialoggsm.com',NULL,2014,8,11,'2014-08-11 17:06:00','127','CELCOM','200',NULL,NULL,NULL,NULL),('128','smsmessaging','smsmessaging:vv1','v1','carbon.super','OXebB4X8aWCWGWBW9EeytbXh6qwa','testusr','/smsmessaging',1407902506133,1,'06809-001D-PC.dialog.dialoggsm.com',NULL,2014,8,13,'2014-08-13 09:31:00','128','DIALOG','200',NULL,NULL,NULL,NULL),('129','smsmessaging','smsmessaging:vv1','v1','carbon.super','OXebB4X8aWCWGWBW9EeytbXh6qwa','testusr','/smsmessaging',1407902587734,1,'06809-001D-PC.dialog.dialoggsm.com',NULL,2014,8,13,'2014-08-13 09:33:00','129','ROBI','200',NULL,NULL,NULL,NULL),('130','smsmessaging','smsmessaging:vv1','v1','carbon.super','Vuf07TBnSvPHs20udVtU3LN2ye8a','admin','/smsmessaging',1407826682142,1,'06809-001D-PC.dialog.dialoggsm.com',NULL,2014,8,12,'2014-08-12 12:28:00','130','ROBI','200',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sb_api_response_summary_back` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_approval_audit`
--

DROP TABLE IF EXISTS `sub_approval_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_approval_audit` (
  `API_PROVIDER` varchar(200) NOT NULL DEFAULT '',
  `API_NAME` varchar(200) NOT NULL DEFAULT '',
  `API_VERSION` varchar(30) NOT NULL DEFAULT '',
  `APP_ID` int(11) NOT NULL,
  `SUB_STATUS` varchar(50) DEFAULT 'ON_HOLD',
  `SUB_APPROVAL_TYPE` varchar(50) DEFAULT NULL,
  `COMPLETED_BY_ROLE` varchar(50) NOT NULL DEFAULT '',
  `COMPLETED_BY_USER` varchar(50) DEFAULT NULL,
  `COMPLETED_ON` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`API_PROVIDER`,`API_NAME`,`API_VERSION`,`COMPLETED_BY_ROLE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_approval_audit`
--

LOCK TABLES `sub_approval_audit` WRITE;
/*!40000 ALTER TABLE `sub_approval_audit` DISABLE KEYS */;
INSERT INTO `sub_approval_audit` VALUES ('admin','payment','v1',113,'APPROVED','HUB_ADMIN','admin','admin','2014-10-02 06:24:36');
/*!40000 ALTER TABLE `sub_approval_audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription_comments`
--

DROP TABLE IF EXISTS `subscription_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscription_comments` (
  `TaskID` varchar(255) NOT NULL,
  `Comment` varchar(1024) NOT NULL,
  `Status` varchar(255) NOT NULL,
  `Description` varchar(1024) NOT NULL,
  PRIMARY KEY (`TaskID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription_comments`
--

LOCK TABLES `subscription_comments` WRITE;
/*!40000 ALTER TABLE `subscription_comments` DISABLE KEYS */;
INSERT INTO `subscription_comments` VALUES ('17775676571','adminComment','status','description'),('50557','fgfgfds','APPROVED','Approve API [ payment - v1 ] subscription creation request from subscriber - admin for the application - demoApp [ Application Description: lklj; ]\n								Subscription Details\n								\n									\n										Tiers:\n										\n                        				SubscriptionBronzeRequestbasedUnlimitedGoldSilverPremium\n											\n												\n							');
/*!40000 ALTER TABLE `subscription_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription_tax`
--

DROP TABLE IF EXISTS `subscription_tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscription_tax` (
  `application_id` int(11) NOT NULL,
  `api_id` int(11) NOT NULL,
  `tax_type` varchar(25) NOT NULL,
  PRIMARY KEY (`application_id`,`api_id`,`tax_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription_tax`
--

LOCK TABLES `subscription_tax` WRITE;
/*!40000 ALTER TABLE `subscription_tax` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscription_tax` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tax`
--

DROP TABLE IF EXISTS `tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tax` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(25) NOT NULL,
  `effective_from` date DEFAULT NULL,
  `effective_to` date DEFAULT NULL,
  `value` decimal(7,6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax`
--

LOCK TABLES `tax` WRITE;
/*!40000 ALTER TABLE `tax` DISABLE KEYS */;
/*!40000 ALTER TABLE `tax` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-03 20:44:24
