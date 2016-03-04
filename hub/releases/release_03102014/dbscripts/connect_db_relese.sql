-- MySQL dump 10.13  Distrib 5.1.61, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: isam_usersession
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
-- Dumping data for table `clientstatus`
--

LOCK TABLES `clientstatus` WRITE;
/*!40000 ALTER TABLE `clientstatus` DISABLE KEYS */;
INSERT INTO `clientstatus` VALUES ('5345','Rejected','0'),('777','YES','0'),('777','YES','0'),('777','YES','0'),('777','YES','0'),('777','YES','0'),('8aaf2a57-c777-42a2-b0c8-5fb097fafc27','Approved','0'),('844d0ef5-2e4c-4eba-8adf-f1514c53572c','PENDING','0'),('844d0ef5-2e4c-4eba-8adf-f1514c53572c','PENDING','0'),('35f1ed72-0366-4478-a409-c995bd8f1693','PENDING','0'),('525afe7e-a5b6-4b51-aa5b-9d9d6116e847','PENDING','0'),('1db22b57-d223-4de4-9784-e169f0746564','PENDING','0'),('fe831e6e-0979-46d8-8c35-e84e5980c165','Approved','0'),('02481727-279b-40be-a909-b886829f91ff','PENDING','0'),('58cc5130-03ef-48c4-bb0f-62d2a8d0588d','Approved','0'),('60150e75-ea5e-4de3-b1e1-1b1086fe9b7a','Approved','0'),('6135a6eb-5949-4bd5-bd78-52d049922263','Approved','0'),('6d77d9d5-e341-4658-96ca-70555de6045a','PENDING','0'),('40751eb5-ce6a-43cf-a117-182cb126d031','PENDING','0'),('5333c5f3-2297-4252-8b36-36ae322048ea','PENDING','0'),('4fd5e083-9430-439f-9119-9b2d6202c70d','PENDING','0'),('4ac7f5c2-b84f-43b8-9d7b-242604eccf63','PENDING','0'),('2e5495b2-5980-49fc-a4cd-e377234bb9ec','PENDING','0'),('81332f67-5dde-4f67-a720-0301acbb0e10','PENDING','0'),('bf0a654c-2b97-4198-a7c4-8e4fd68cfdfc','PENDING','0'),('3dfa7e63-4282-4693-b218-496708e66829','PENDING','0'),('3dfa7e63-4282-4693-b218-496708e66829','PENDING','0'),('2b547caa-5c40-4acf-8dc7-82e596409b86','PENDING','0'),('2b547caa-5c40-4acf-8dc7-82e596409b86','PENDING','0'),('c53183d3-4b13-4247-876f-b2ec1102496f','PENDING','0'),('f3928f1f-ca5e-4352-a45d-ae6f2aa54035','PENDING','0'),('e0bca4e3-9c2b-493c-9773-b442d6817337','PENDING','0'),('d310c8a1-c8d3-43bc-975e-4f4ea028b074','PENDING','0'),('effe9860-8390-4e13-9db6-96eef2ad479c','PENDING','0'),('601834bc-f4d9-499f-8346-beee86d0dbd5','PENDING','0'),('ca8fd694-3736-41bc-92ab-8d8a7dd49d5e','PENDING','0'),('af134226-c312-41cf-93fe-ac18f94a2491','PENDING','0'),('b3a4bccc-f67b-4423-b987-ea907fc45dab','PENDING','0'),('f55997ed-510f-48cb-b9a7-b0b27522f9b5','tharanga','1234'),('02c72079-db64-43cc-a19b-40070f3b302e','PENDING','0'),('02c72079-db64-43cc-a19b-40070f3b302e','PENDING','0'),('c7c87156-9a01-4bb9-bcdb-b0f13996d5f4','PENDING','0'),('c7c87156-9a01-4bb9-bcdb-b0f13996d5f4','PENDING','0'),('d4a51203-d2c8-4473-8f10-f8f20343d675','Approved','1111'),('7074c376-ad76-4d7c-b4a7-29fb1f0394d5','PENDING','0'),('1c494db1-a9b2-43e7-b938-3d16ad9a49ec','Approved','77777'),('d9c2b9d0-dc24-4683-af76-b2d532364b5b','Approved','0'),('0fcfec03-abd6-473d-ada5-0adc8aaf2c98','PENDING','0'),('e8ab18e8-ad16-44c2-a15b-ef601bde8a9a','PENDING','0'),('7a8f9c73-c560-4304-9551-0cd1684a1e8c','PENDING','0'),('7a8f9c73-c560-4304-9551-0cd1684a1e8c','PENDING','0'),('4a2ab508-1cf2-44ba-ad0c-7dde447cd17e','Approved','0'),('eafc4e69-c3fb-4fa5-9ef2-0a2b636079fc','Approved','77777'),('e2c9ea35-ee01-43b3-a462-9d82b74e6903','Approved','77777'),('9734541c-657c-411d-845f-e54541375721','Approved','77777'),('054e0799-4f96-4cfb-b8fb-1e6cbd72858d','Approved','77777'),('2299b270-e90a-4508-8634-1a604089bc72','Approved','77777'),('597cf3fd-55c7-496c-b1a4-219665ae62c6','Approved','77777'),('2a72e862-9faf-4cae-a13f-a257b3c8b32f','Approved','1234'),('0ff43cae-861e-4085-ada0-c96d4b7dd381','Approved','77777'),('a958a926-47a0-4689-8ef7-3c8e1fa3e53a','Approved','77777'),('b6a99a3d-59e0-47ac-a4cd-19870d26b52d','PENDING','0'),('95e530a5-6ea3-4cd6-a848-b5763cb1afc1','PENDING','0'),('573ff59e-778e-4969-b007-341d9a2ff722','Approved','77777'),('58d952dd-64a4-4425-ae04-42c9a5641cc8','Approved','77777'),('d0d1960b-d7ce-4464-908e-e63181bd8837','Approved','77777'),('a387bfdb-f2bd-447f-99ad-4b80fd8cf187','Approved','77777'),('184a21e0-27ef-4e63-bee6-8b055468dfd1','Approved','2222'),('6735ad50-0169-42a3-a5ea-f81ab5507094','Approved','2222'),('bf99bf85-014e-4ded-981f-d3b080b3ff61','Approved','2222'),('fb6cece4-490b-41f9-869a-45ad6de08bd4','Approved','2222'),('6fe6bd3b-63a8-4305-9643-b7de5d633677','Approved','2222'),('e2a657a1-6d84-4597-9f6b-a1bf8de12eaf','PENDING','0'),('33889481-ec2f-413a-88ac-b6c85dd5aa07','Approved','2222'),('f96ea03c-eafc-4d1a-abab-ce881550dfe9','Approved','2222'),('98cd4dc0-abd3-4604-84d8-18d7ba64f4bb','Approved','2222'),('d6f9e14e-7293-4a87-8b2e-61025e9b5094','Approved','2222'),('a058b079-0527-485b-bac6-daccf3d1ad10','Approved','2222'),('1e1a911f-d5db-491b-beed-5d3b4996054f','Approved','2222'),('886eed98-82dc-4c4a-9e63-c85b0f9fd19c','Approved','2222'),('3e7bce99-aac5-4ce3-9a4c-1b2b8022c8fe','Approved','2222'),('12c353d4-1991-4924-80ab-5d4a42d1c8d9','Approved','2222'),('1b4353ad-a4ce-4c44-9b5d-da6dd53dd1f1','Approved','2222'),('1ac5823c-781e-476a-9462-2cff292dc4cf','Approved','2222'),('128f31e3-f154-49b5-8873-10985ffdfd77','Approved','reee'),('8b8d410c-141b-410b-840b-f192652d81fe','Approved','2222'),('6a0eebbd-9f91-4a1e-a8fe-22e510ab1fe3','Approved','rr'),('894a903b-853b-4627-87d9-a15df2585ccf','PENDING','0'),('3d45fe5f-f47e-494b-8778-674fd43b9ad6','Approved','2222'),('53ead871-0349-4541-ba3b-2913a3fa4a48','Approved','2222'),('0d7d11ce-6b7b-4cea-9be9-4106a3786fb5','Approved','2222'),('0a35c6d6-2c50-414f-8ad9-4323a3dae6fc','Approved','2222'),('d1fd8286-600f-41ea-9cfb-d81161c69442','Approved','2222'),('3d2249aa-bc75-452e-97a3-00dded66165b','Approved','2222'),('37198e3b-79cb-4c3c-a14f-4ecb33a530f1','Approved','2222'),('7fdb6b63-07e4-4a2d-8080-8d8e6e3d604a','Approved','2222'),('340655f3-7360-4bae-80a7-1a87e3a0b8b3','Approved','2222'),('d0430951-5b51-4a14-8bfd-6b901259a56e','Approved','2222'),('06b738aa-f552-42f3-ae47-e18b3c567de3','Approved','2222'),('ba2c7b82-56a3-40f3-b0d6-9d4f7dc7708f','Approved','2222'),('50e820ec-19da-42bf-986a-a7692d5a5746','Approved','2222'),('f017fa87-e7ed-4df6-8373-7c58a18bdecf','Approved','2222'),('2a1499c4-cac2-465a-822b-425be10768c6','Approved','2222'),('70c4e0c3-3362-41aa-be59-31cc0c234d97','Approved','1758'),('b42df71e-6d00-4961-8fdd-1d4f1c6f1f1b','Approved','1758'),('cb0faf00-157b-4cc3-bdba-9cc5cd362a5d','PENDING','0'),('51518f60-45ed-487a-8ee5-04d3e4d4458a','Approved','2222'),('4052f719-0b41-42ce-8604-e1531216adab','Approved','2222'),('d8bd13fd-9d4b-4c1b-b3d2-3ae9adf6da5a','Approved','2222'),('b63886ed-c6d9-4f8c-91c2-172a9ed838c9','Approved','2222'),('7cff085b-6dbb-49dd-86b4-e8aa326a6818','Approved','ddd'),('4b284eba-1663-432c-8dc8-44ccb6e7dfa1','Approved','7777'),('f427b513-e110-4dc1-b6e0-771fbdd1da77','PENDING','0'),('4ced224a-7468-4be7-891b-15b74350e1b0','Approved','2222'),('e84158b7-d01a-4a04-a04b-53dd8b8ab4bc','Approved','44'),('eb21ea63-e5a5-423c-9dcc-afbf17c29a41','Approved','2222'),('105daf3d-31e0-4513-aa7c-186d5fa7eeb7','Approved','2222'),('7f99d48a-1cae-4526-8bec-3dfbb3e07207','PENDING','0'),('d0dd3b9f-dc9a-4da7-ab68-19b7589bbd08','PENDING','0'),('b7775b7f-ee43-4ae2-a18d-19a601147cef','Approved','7777'),('304c63e8-7478-4c80-8454-041695e86eaf','Approved','5555'),('36589c7b-8bc1-461a-afda-b15e31cdf061','Approved','7778'),('e260d7ef-8c6c-4a92-b825-089180de67f7','Approved','7777'),('51567729-feef-4cef-86a3-4b68b41dd20a','Approved','5555'),('1a511293-f681-4344-8c3a-22abfc409b55','Approved','7777'),('0a92a408-c2d8-4fe4-a7c8-4a48dd0b641c','PENDING','0'),('32a4986e-82fd-4554-88aa-f756507b0076','Approved','7777'),('fc192570-c4f8-4328-8dab-036aae3ba604','Approved','7777'),('1652071a-933c-4d55-8755-137a65dccac5','PENDING','0'),('aa719d5e-146d-4714-886f-d0d3ad9f0c8d','Approved','7777'),('bd7b9f18-6e97-48fe-9452-c6088d8ee51e','Approved','7777'),('becb174e-8032-45c6-9ff4-8f13b2e8d3c6','Approved','7777'),('c0483eae-ec9c-4776-a313-2c539089e6c8','Approved','6547'),('db2c803a-60e8-441c-8383-173132d6fada','Approved','6547'),('861f4c28-2953-4fc6-a0ab-21748b3f1c0e','Approved','6547'),('c1e4c48e-6b12-4656-8139-9a6bf2cd2342','Approved','7777'),('6d945f25-1dda-4bcc-9cda-04016a82e58e','Approved','2222'),('d198a73d-1023-4f19-8e7a-4925fc332f38','Approved','7777'),('8bf5eee9-dffe-4ed2-9325-e206a4228485','PENDING','0'),('57401cfc-c722-4cf2-b0d5-f33c86c03407','Approved','7777'),('4c1d2fac-931f-4f73-8590-595ee8f1c112','Approved','6666'),('fb0dfa76-1c28-40e4-8ba0-9d549d7e0ea7','Approved','6666'),('01c5f980-6b2d-4ac7-a381-69dc18e5aa9b','Approved','6666'),('584bbc4a-c75b-4414-b135-51c15abb144a','Approved','7777'),('ca189787-8256-49ed-9a59-e79c87380418','PENDING','0'),('2d6cbf65-b02c-4ed6-8db9-2d8c38d7ceb1','Approved','7777'),('2d445a7b-e38f-43e5-bffc-a3a8e08602f1','Approved','666'),('e7171508-6933-40e8-8cc3-fc1f9e009045','PENDING','0'),('8d170683-5712-4f0d-91ff-cff4bf8f8665','PENDING','0'),('ce361460-2298-4d87-8765-5ae8364710fb','Approved','5555'),('26f8daf5-e301-4097-8bee-a9ced02fd51f','PENDING','0'),('7a776f6e-5815-4c8c-8116-3f2687236233','PENDING','0');
/*!40000 ALTER TABLE `clientstatus` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `multiplepasswords`
--

LOCK TABLES `multiplepasswords` WRITE;
/*!40000 ALTER TABLE `multiplepasswords` DISABLE KEYS */;
INSERT INTO `multiplepasswords` VALUES ('94772423064',NULL,1),('94773333428',NULL,1),('9477335360',2222,1),('94777335365',1111,1);
/*!40000 ALTER TABLE `multiplepasswords` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `pin`
--

LOCK TABLES `pin` WRITE;
/*!40000 ALTER TABLE `pin` DISABLE KEYS */;
INSERT INTO `pin` VALUES ('tharanga','1234'),('94773333428','77777'),('94773335827','11111'),('user123','67'),('user123','1988'),('curr','1654'),('MobileConnect3665','1667'),('admin','2234'),('MobileConnect1821','777'),('admin','1234'),('MobileConnect1352','456'),('MobileConnect9889','11111'),('MobileConnect6549','222'),('MobileConnect2649','44'),('MobileConnect2051','22'),('MobileConnect1378','568'),('MobileConnect5937','444'),('MobileConnect57','6666'),('MobileConnect7748','433'),('MobileConnect7154','55'),('curr','54'),('MobileConnect6334','44'),('94773333428','6666'),('94773333428','444');
/*!40000 ALTER TABLE `pin` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-03 20:41:44
