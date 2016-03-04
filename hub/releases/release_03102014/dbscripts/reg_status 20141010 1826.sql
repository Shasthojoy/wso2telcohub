-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.51-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema gsma_connect
--

CREATE DATABASE IF NOT EXISTS gsma_connect;
USE gsma_connect;

--
-- Definition of table `multiplepasswords`
--

DROP TABLE IF EXISTS `multiplepasswords`;
CREATE TABLE `multiplepasswords` (
  `username` varchar(255) NOT NULL,
  `pin` int(11) DEFAULT NULL,
  `attempts` int(11) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `multiplepasswords`
--

/*!40000 ALTER TABLE `multiplepasswords` DISABLE KEYS */;
INSERT INTO `multiplepasswords` (`username`,`pin`,`attempts`) VALUES 
 ('94773333428',NULL,1),
 ('9477335360',2222,1),
 ('94777335365',1111,1),
 ('e141501e-65e4-4044-b5e5-3094d32f09eb',NULL,1),
 ('e8dadc06-7f77-45e5-a514-483f2df82e99',NULL,1),
 ('xxxxx',NULL,55);
/*!40000 ALTER TABLE `multiplepasswords` ENABLE KEYS */;


--
-- Definition of table `pin`
--

DROP TABLE IF EXISTS `pin`;
CREATE TABLE `pin` (
  `username` varchar(255) DEFAULT NULL,
  `pin` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pin`
--

/*!40000 ALTER TABLE `pin` DISABLE KEYS */;
INSERT INTO `pin` (`username`,`pin`) VALUES 
 ('tharanga','1234'),
 ('94773333428','77777'),
 ('94773335827','11111'),
 ('user123','67'),
 ('user123','1988'),
 ('curr','1654'),
 ('MobileConnect3665','1667'),
 ('MobileConnect1821','777'),
 ('MobileConnect1352','456'),
 ('MobileConnect9889','11111'),
 ('MobileConnect6549','222'),
 ('MobileConnect2649','44'),
 ('MobileConnect2051','22'),
 ('MobileConnect1378','568'),
 ('MobileConnect5937','444'),
 ('MobileConnect57','6666'),
 ('MobileConnect7748','433'),
 ('MobileConnect7154','55'),
 ('curr','54'),
 ('MobileConnect6334','44');
/*!40000 ALTER TABLE `pin` ENABLE KEYS */;


--
-- Definition of table `regstatus`
--

DROP TABLE IF EXISTS `regstatus`;
CREATE TABLE `regstatus` (
  `username` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `pin` varchar(10) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `regstatus`
--

/*!40000 ALTER TABLE `regstatus` DISABLE KEYS */;
INSERT INTO `regstatus` (`username`,`status`,`pin`) VALUES 
 ('5345','Approved','0'),
 ('777','Approved','0'),
 ('777','Approved','0'),
 ('777','Approved','0'),
 ('777','Approved','0'),
 ('777','Approved','0'),
 ('8aaf2a57-c777-42a2-b0c8-5fb097fafc27','Approved','0'),
 ('844d0ef5-2e4c-4eba-8adf-f1514c53572c','Approved','0'),
 ('844d0ef5-2e4c-4eba-8adf-f1514c53572c','Approved','0'),
 ('35f1ed72-0366-4478-a409-c995bd8f1693','Approved','0'),
 ('525afe7e-a5b6-4b51-aa5b-9d9d6116e847','Approved','0'),
 ('1db22b57-d223-4de4-9784-e169f0746564','Approved','0'),
 ('fe831e6e-0979-46d8-8c35-e84e5980c165','Approved','0'),
 ('02481727-279b-40be-a909-b886829f91ff','Approved','0'),
 ('58cc5130-03ef-48c4-bb0f-62d2a8d0588d','Approved','0'),
 ('60150e75-ea5e-4de3-b1e1-1b1086fe9b7a','Approved','0'),
 ('6135a6eb-5949-4bd5-bd78-52d049922263','Approved','0'),
 ('6d77d9d5-e341-4658-96ca-70555de6045a','Approved','0'),
 ('40751eb5-ce6a-43cf-a117-182cb126d031','Approved','0'),
 ('5333c5f3-2297-4252-8b36-36ae322048ea','Approved','0'),
 ('4fd5e083-9430-439f-9119-9b2d6202c70d','Approved','0'),
 ('4ac7f5c2-b84f-43b8-9d7b-242604eccf63','Approved','0'),
 ('2e5495b2-5980-49fc-a4cd-e377234bb9ec','Approved','0'),
 ('81332f67-5dde-4f67-a720-0301acbb0e10','Approved','0'),
 ('bf0a654c-2b97-4198-a7c4-8e4fd68cfdfc','Approved','0'),
 ('3dfa7e63-4282-4693-b218-496708e66829','Approved','0'),
 ('3dfa7e63-4282-4693-b218-496708e66829','Approved','0'),
 ('2b547caa-5c40-4acf-8dc7-82e596409b86','Approved','0'),
 ('2b547caa-5c40-4acf-8dc7-82e596409b86','Approved','0'),
 ('c53183d3-4b13-4247-876f-b2ec1102496f','Approved','0'),
 ('f3928f1f-ca5e-4352-a45d-ae6f2aa54035','Approved','0'),
 ('e0bca4e3-9c2b-493c-9773-b442d6817337','Approved','0'),
 ('d310c8a1-c8d3-43bc-975e-4f4ea028b074','Approved','0'),
 ('effe9860-8390-4e13-9db6-96eef2ad479c','Approved','0'),
 ('601834bc-f4d9-499f-8346-beee86d0dbd5','Approved','0'),
 ('ca8fd694-3736-41bc-92ab-8d8a7dd49d5e','Approved','0'),
 ('af134226-c312-41cf-93fe-ac18f94a2491','Approved','0'),
 ('b3a4bccc-f67b-4423-b987-ea907fc45dab','Approved','0'),
 ('f55997ed-510f-48cb-b9a7-b0b27522f9b5','Approved','1234'),
 ('02c72079-db64-43cc-a19b-40070f3b302e','Approved','0'),
 ('02c72079-db64-43cc-a19b-40070f3b302e','Approved','0'),
 ('c7c87156-9a01-4bb9-bcdb-b0f13996d5f4','Approved','0'),
 ('c7c87156-9a01-4bb9-bcdb-b0f13996d5f4','Approved','0'),
 ('d4a51203-d2c8-4473-8f10-f8f20343d675','Approved','1111'),
 ('7074c376-ad76-4d7c-b4a7-29fb1f0394d5','Approved','0'),
 ('1c494db1-a9b2-43e7-b938-3d16ad9a49ec','Approved','77777'),
 ('d9c2b9d0-dc24-4683-af76-b2d532364b5b','Approved','0'),
 ('0fcfec03-abd6-473d-ada5-0adc8aaf2c98','Approved','0'),
 ('e8ab18e8-ad16-44c2-a15b-ef601bde8a9a','Approved','0'),
 ('7a8f9c73-c560-4304-9551-0cd1684a1e8c','Approved','0'),
 ('7a8f9c73-c560-4304-9551-0cd1684a1e8c','Approved','0'),
 ('4a2ab508-1cf2-44ba-ad0c-7dde447cd17e','Approved','0'),
 ('eafc4e69-c3fb-4fa5-9ef2-0a2b636079fc','Approved','77777'),
 ('e2c9ea35-ee01-43b3-a462-9d82b74e6903','Approved','77777'),
 ('9734541c-657c-411d-845f-e54541375721','Approved','77777'),
 ('054e0799-4f96-4cfb-b8fb-1e6cbd72858d','Approved','77777'),
 ('2299b270-e90a-4508-8634-1a604089bc72','Approved','77777'),
 ('597cf3fd-55c7-496c-b1a4-219665ae62c6','Approved','77777'),
 ('2a72e862-9faf-4cae-a13f-a257b3c8b32f','Approved','1234'),
 ('0ff43cae-861e-4085-ada0-c96d4b7dd381','Approved','77777'),
 ('a958a926-47a0-4689-8ef7-3c8e1fa3e53a','Approved','77777'),
 ('b6a99a3d-59e0-47ac-a4cd-19870d26b52d','Approved','0'),
 ('95e530a5-6ea3-4cd6-a848-b5763cb1afc1','Approved','0'),
 ('573ff59e-778e-4969-b007-341d9a2ff722','Approved','77777'),
 ('58d952dd-64a4-4425-ae04-42c9a5641cc8','Approved','77777'),
 ('d0d1960b-d7ce-4464-908e-e63181bd8837','Approved','77777'),
 ('a387bfdb-f2bd-447f-99ad-4b80fd8cf187','Approved','77777'),
 ('184a21e0-27ef-4e63-bee6-8b055468dfd1','Approved','2222'),
 ('6735ad50-0169-42a3-a5ea-f81ab5507094','Approved','2222'),
 ('bf99bf85-014e-4ded-981f-d3b080b3ff61','Approved','2222'),
 ('fb6cece4-490b-41f9-869a-45ad6de08bd4','Approved','2222'),
 ('6fe6bd3b-63a8-4305-9643-b7de5d633677','Approved','2222'),
 ('e2a657a1-6d84-4597-9f6b-a1bf8de12eaf','Approved','0'),
 ('33889481-ec2f-413a-88ac-b6c85dd5aa07','Approved','2222'),
 ('f96ea03c-eafc-4d1a-abab-ce881550dfe9','Approved','2222'),
 ('98cd4dc0-abd3-4604-84d8-18d7ba64f4bb','Approved','2222'),
 ('d6f9e14e-7293-4a87-8b2e-61025e9b5094','Approved','2222'),
 ('a058b079-0527-485b-bac6-daccf3d1ad10','Approved','2222'),
 ('1e1a911f-d5db-491b-beed-5d3b4996054f','Approved','2222'),
 ('886eed98-82dc-4c4a-9e63-c85b0f9fd19c','Approved','2222'),
 ('3e7bce99-aac5-4ce3-9a4c-1b2b8022c8fe','Approved','2222'),
 ('12c353d4-1991-4924-80ab-5d4a42d1c8d9','Approved','2222'),
 ('1b4353ad-a4ce-4c44-9b5d-da6dd53dd1f1','Approved','2222'),
 ('1ac5823c-781e-476a-9462-2cff292dc4cf','Approved','2222'),
 ('128f31e3-f154-49b5-8873-10985ffdfd77','Approved','reee'),
 ('8b8d410c-141b-410b-840b-f192652d81fe','Approved','2222'),
 ('6a0eebbd-9f91-4a1e-a8fe-22e510ab1fe3','Approved','rr'),
 ('894a903b-853b-4627-87d9-a15df2585ccf','Approved','0'),
 ('3d45fe5f-f47e-494b-8778-674fd43b9ad6','Approved','2222'),
 ('53ead871-0349-4541-ba3b-2913a3fa4a48','Approved','2222'),
 ('0d7d11ce-6b7b-4cea-9be9-4106a3786fb5','Approved','2222'),
 ('0a35c6d6-2c50-414f-8ad9-4323a3dae6fc','Approved','2222'),
 ('d1fd8286-600f-41ea-9cfb-d81161c69442','Approved','2222'),
 ('3d2249aa-bc75-452e-97a3-00dded66165b','Approved','2222'),
 ('37198e3b-79cb-4c3c-a14f-4ecb33a530f1','Approved','2222'),
 ('7fdb6b63-07e4-4a2d-8080-8d8e6e3d604a','Approved','2222'),
 ('340655f3-7360-4bae-80a7-1a87e3a0b8b3','Approved','2222'),
 ('d0430951-5b51-4a14-8bfd-6b901259a56e','Approved','2222'),
 ('06b738aa-f552-42f3-ae47-e18b3c567de3','Approved','2222'),
 ('ba2c7b82-56a3-40f3-b0d6-9d4f7dc7708f','Approved','2222'),
 ('50e820ec-19da-42bf-986a-a7692d5a5746','Approved','2222'),
 ('f017fa87-e7ed-4df6-8373-7c58a18bdecf','Approved','2222'),
 ('2a1499c4-cac2-465a-822b-425be10768c6','Approved','2222'),
 ('70c4e0c3-3362-41aa-be59-31cc0c234d97','Approved','1758'),
 ('b42df71e-6d00-4961-8fdd-1d4f1c6f1f1b','Approved','1758'),
 ('cb0faf00-157b-4cc3-bdba-9cc5cd362a5d','Approved','0'),
 ('51518f60-45ed-487a-8ee5-04d3e4d4458a','Approved','2222'),
 ('4052f719-0b41-42ce-8604-e1531216adab','Approved','2222'),
 ('d8bd13fd-9d4b-4c1b-b3d2-3ae9adf6da5a','Approved','2222'),
 ('b63886ed-c6d9-4f8c-91c2-172a9ed838c9','Approved','2222'),
 ('7cff085b-6dbb-49dd-86b4-e8aa326a6818','Approved','ddd'),
 ('4b284eba-1663-432c-8dc8-44ccb6e7dfa1','Approved','7777'),
 ('f427b513-e110-4dc1-b6e0-771fbdd1da77','Approved','0'),
 ('4ced224a-7468-4be7-891b-15b74350e1b0','Approved','2222'),
 ('e84158b7-d01a-4a04-a04b-53dd8b8ab4bc','Approved','44'),
 ('eb21ea63-e5a5-423c-9dcc-afbf17c29a41','Approved','2222'),
 ('105daf3d-31e0-4513-aa7c-186d5fa7eeb7','Approved','2222'),
 ('7f99d48a-1cae-4526-8bec-3dfbb3e07207','Approved','0'),
 ('d0dd3b9f-dc9a-4da7-ab68-19b7589bbd08','Approved','0'),
 ('b7775b7f-ee43-4ae2-a18d-19a601147cef','Approved','7777'),
 ('304c63e8-7478-4c80-8454-041695e86eaf','Approved','5555'),
 ('edc48c49-1aed-4c11-bcbe-99e6702c937a','Approved','0'),
 ('9e8cba92-2a88-4ae2-9ec3-05d8efae69fb','Approved','0'),
 ('fcef0499-d4d2-4c74-abd0-d4ac4c6d4ef2','Approved','0'),
 ('dbfb266e-6b65-405b-9bb0-9c1e75d2b234','Approved','0'),
 ('c314a661-4035-4f64-acd6-01c90f8ddd8f','Approved','0'),
 ('fd59424b-9564-4efa-a0f6-3a943deba558','Approved','0'),
 ('b24d739d-ebe6-49b8-a106-9f1bd950a059','Approved','0'),
 ('55f8c86b-dfc2-4438-831d-2ebeeef0feb3','Approved','0'),
 ('55f8c86b-dfc2-4438-831d-2ebeeef0feb3','Approved','0'),
 ('c91b617d-214d-4eca-a524-f31e4fb4b9a3','Approved','0'),
 ('c91b617d-214d-4eca-a524-f31e4fb4b9a3','Approved','0'),
 ('1c6d5449-5cd8-49a5-acea-8d91d0d06c23','Approved','0'),
 ('1c6d5449-5cd8-49a5-acea-8d91d0d06c23','Approved','0'),
 ('9420c6d0-02f7-468c-9510-9b06f504652e','Approved','0'),
 ('fee60262-ba91-4424-aaa9-1cac65cc781f','Approved','0'),
 ('e898f7b3-f83d-443a-b61d-fda7dccf98c3','Approved','0'),
 ('9d017287-523a-4057-8528-0fa77fb84495','Approved','0'),
 ('9d017287-523a-4057-8528-0fa77fb84495','Approved','0'),
 ('9d017287-523a-4057-8528-0fa77fb84495','Approved','0'),
 ('612129e7-32f9-4aea-b561-cc4857a6e8f3','Approved','0'),
 ('46ec9582-f1f3-4cb0-98ee-036b50075a82','Approved','0'),
 ('f24d40b2-8114-4432-a7a0-e52e943c3aab','Approved','0'),
 ('f24d40b2-8114-4432-a7a0-e52e943c3aab','Approved','0'),
 ('cb35b545-163e-4cb2-9587-4ac68fb6c1f0','Approved','0'),
 ('9e5a9ac8-2d62-4810-9108-d10510aaa2af','Approved','0'),
 ('2e42e7d7-253a-4a2f-b252-79f4f8c40f00','Approved','0'),
 ('2e42e7d7-253a-4a2f-b252-79f4f8c40f00','Approved','0'),
 ('abe33b76-e61e-4ad5-b59f-5e9f709fd359','Approved','0'),
 ('abe33b76-e61e-4ad5-b59f-5e9f709fd359','Approved','0'),
 ('38b9dd48-d238-4d0b-b610-88306813cc44','Approved','0'),
 ('38b9dd48-d238-4d0b-b610-88306813cc44','Approved','0'),
 ('c648b489-9f05-4153-b07b-fde3c0050674','Approved','0'),
 ('c648b489-9f05-4153-b07b-fde3c0050674','Approved','0'),
 ('23133ed6-445e-4f42-8618-389c4916ebed','Approved','0'),
 ('23133ed6-445e-4f42-8618-389c4916ebed','Approved','0'),
 ('e8f91f4e-0be7-4bb8-9c04-a6191bc24c99','Approved','0'),
 ('e8f91f4e-0be7-4bb8-9c04-a6191bc24c99','Approved','0'),
 ('9837ad20-dbd9-4bd3-b461-7ce3bfeab080','Approved','0'),
 ('9837ad20-dbd9-4bd3-b461-7ce3bfeab080','Approved','0'),
 ('24cf3090-9b77-4b84-89db-2bf1e1179c3f','Approved','0'),
 ('544370e7-9e16-4745-9a5a-99418357d6ed','Approved','0'),
 ('5e6dfec7-97a5-41f4-bfcb-27b4d0262fc6','Approved','0'),
 ('d7dd163d-9da9-41d9-b4fc-2e93f42471f5','Approved','0'),
 ('8e7324f1-3291-4fd2-aa4c-2257fb9e7b6f','Approved','0'),
 ('d3695aab-0390-4cc9-9ee3-838431793570','Approved','0'),
 ('d3695aab-0390-4cc9-9ee3-838431793570','Approved','0'),
 ('d8e67dca-db10-4d80-b891-fe4029f6b531','Approved','0'),
 ('bd2b8b5a-cf58-4101-b38e-3abad67b2544','Approved','0'),
 ('9062e030-593c-458c-b546-985d8acbb724','Approved','0'),
 ('9062e030-593c-458c-b546-985d8acbb724','Approved','0'),
 ('0f4271a5-8daf-4ed8-add4-f81af684695d','Approved','0'),
 ('8cbcf425-d644-4b25-a556-0d34e279f4b8','Approved','0'),
 ('670671b9-ed73-4846-afac-f51e1e23aeb1','Approved','0'),
 ('4a5abf83-7954-4f88-9dc0-9f20aed72828','Approved','0'),
 ('198f02af-cad3-4071-9c1f-873557cf13b8','Approved','0'),
 ('85862b83-9e6e-4c51-9f39-4185c006502b','Approved','0'),
 ('a2f2ac2f-3366-4c65-9ae1-aa0790d962a9','Approved','0'),
 ('55a2b363-e087-44f7-9a37-abfe9eb1cb17','Approved','0'),
 ('55a2b363-e087-44f7-9a37-abfe9eb1cb17','Approved','0'),
 ('c8fd5b92-044f-4852-833d-f5dc2c2aae99','Approved','0'),
 ('c8fd5b92-044f-4852-833d-f5dc2c2aae99','Approved','0'),
 ('fbaf2707-b171-477b-8bff-d7261812a710','Approved','0'),
 ('fbaf2707-b171-477b-8bff-d7261812a710','Approved','0'),
 ('f78659dd-d895-4713-960a-da0068224866','Approved','0'),
 ('f78659dd-d895-4713-960a-da0068224866','Approved','0'),
 ('2e5bcd3e-5b54-42da-99d8-d736258da5ac','Approved','0'),
 ('7895c9a0-aa42-4520-af69-933de7efbecd','Approved','0'),
 ('7322ab33-e0f6-4272-80f7-b4969998855c','Approved','0'),
 ('7322ab33-e0f6-4272-80f7-b4969998855c','Approved','0'),
 ('bc42e2d2-536c-432e-bc8d-4c9216523f4c','Approved','0'),
 ('bc42e2d2-536c-432e-bc8d-4c9216523f4c','Approved','0'),
 ('7389663c-dc52-43a3-9ba4-aa2c4b026689','Approved','0'),
 ('9ede1839-92fb-4a7d-8f08-3ce219a6bde3','Approved','0'),
 ('9b6ce928-15c4-4ef1-8b6a-84ff45d51d1d','Approved','777'),
 ('610b360d-451c-4ff7-b46d-7e8d02d70904','Approved','0'),
 ('610b360d-451c-4ff7-b46d-7e8d02d70904','Approved','0'),
 ('be292cc6-6dae-45ee-9ba9-9606bb347671','Approved','0'),
 ('331f91a7-a4b9-4b97-ac70-ba0dcafef45f','Approved','0'),
 ('8b5d6af3-cdd6-44d3-a9d1-ccf35c52f301','Approved','0'),
 ('394e35d3-781b-4de7-8e8e-44afa0dcba3f','Approved','0'),
 ('c9bf7c2c-4ba6-4cea-b345-a5cb1f85023a','Approved','0'),
 ('c9bf7c2c-4ba6-4cea-b345-a5cb1f85023a','Approved','0'),
 ('109b390f-8453-42b1-bcbd-7bdbda594db8','Approved','0'),
 ('109b390f-8453-42b1-bcbd-7bdbda594db8','Approved','0'),
 ('ea4104ba-e5c6-482d-943a-d133e3a99977','Approved','0'),
 ('ea4104ba-e5c6-482d-943a-d133e3a99977','Approved','0'),
 ('b267d738-b98e-4f60-ad23-691127d669d8','Approved','0'),
 ('ee51ef49-4c53-4bab-a7c8-6e96b36d77f3','Approved','0'),
 ('fbf18656-9f40-4baf-813a-896270876809','Approved','0'),
 ('3f84d2ec-0edc-4ae9-a7f9-252ea6a456e7','Approved','0'),
 ('bd413e1c-62f8-4dc3-b0c1-34ca59d21449','Approved','0'),
 ('8183951c-7ac7-402e-9534-0a850f3d622c','Approved','0'),
 ('38f7d578-866a-4bb1-903a-03f6a67b4dca','Approved','0'),
 ('4506c24c-ba2b-454e-964b-3440449a30fe','Approved','0'),
 ('c7540e0a-742e-4e5f-bf83-de67fff84588','Approved','7777'),
 ('b69d538a-916e-47f7-8af7-4556d5f40af6','Approved','7777'),
 ('9e5af170-0a61-41b2-81e9-abe2151df4b3','Approved','0'),
 ('69fc5f49-8df9-4409-a05a-9c593453e8af','Approved','0'),
 ('1930471d-4946-41e5-8b6b-2e4c50cc91c6','Approved','0'),
 ('0a05029d-fccf-48e7-b78e-41fc1a4a8d8c','Approved','0'),
 ('0a05029d-fccf-48e7-b78e-41fc1a4a8d8c','Approved','0'),
 ('f02660ac-d395-42e8-95e4-62701cb5bf2d','Approved','0'),
 ('a825ac8f-d360-4d2c-9c59-95fe0d143c54','Approved','0'),
 ('d767b2be-2878-4126-817f-c1b7c2658009','Approved','0'),
 ('e409f3ac-da53-4f45-9d7e-c124b3783a40','Approved','0'),
 ('52c48e97-f2e1-41ce-ac86-a7a132b65457','Approved','0'),
 ('1588a0ed-e20a-46f8-a48e-2f4dea355b81','Approved','0'),
 ('ef680c0e-e7a4-4fa0-9cc5-6ef31d5b9209','Approved','0'),
 ('86d592ad-d251-44fa-a864-b11aa31e23e1','Approved','0'),
 ('91b313e9-5246-4a8f-810a-6fbda93d524d','Approved','0'),
 ('d6eea43a-4463-4cad-9cfb-0190f2546dae','Approved','0'),
 ('05bacf7e-0b5c-428e-bb40-905fc1945467','Approved','0'),
 ('1649628e-52d0-41b8-9e32-f7d2357ada58','Approved','0'),
 ('c9dc0130-22a9-4132-b67c-7cbd5f6e3731','Approved','0'),
 ('a5bc8c46-217e-48d8-aef6-e926b836132d','Approved','0'),
 ('86599c42-f1a4-430d-bc42-607cc0463ae8','Approved','0'),
 ('b0d0d485-feca-472c-81cc-23bb3cb05fe2','Approved','0'),
 ('2cb75f69-a97a-454c-8e5d-96d38c7eba08','Approved','0'),
 ('11803784-ff24-49f9-bf3d-5609cf1945c1','Approved','0'),
 ('76538fd7-a4ff-467f-9a35-763e614f44bc','Approved','0'),
 ('53e740b5-7053-4d7a-b71e-d798be331738','Approved','0'),
 ('14a1ac10-fe77-410b-9875-8920102f06dd','Approved','0'),
 ('e8dadc06-7f77-45e5-a514-483f2df82e99','Approved','0'),
 ('e141501e-65e4-4044-b5e5-3094d32f09eb','Approved','0'),
 ('6d06a7bf-ae97-4baf-8a86-d03a3b374114','Approved','0'),
 ('69b1dfa8-4fd7-4363-8511-62837ec1a50e','Approved','0'),
 ('48aa0f9a-2baf-4e18-b86f-5fc9bd3a104a','Approved','0'),
 ('69b1dfa8-4fd7-4363-8511-62837ec1a50e','Approved','0'),
 ('48aa0f9a-2baf-4e18-b86f-5fc9bd3a104a','Approved','0'),
 ('7402a3c1-3128-4908-a2e2-1f700d4bc6c4','Approved','0'),
 ('1915453c-4018-42ec-ae9a-be1cafc194f9','Approved','0'),
 ('dd0943ac-c3a2-4ce1-b0ac-9973523f5805','Approved','0'),
 ('dd0943ac-c3a2-4ce1-b0ac-9973523f5805','Approved','0'),
 ('8fee7555-0370-47c2-a02e-53693d389a90','Approved','0'),
 ('720b6b10-92e5-46e5-8d66-7caea9ff6cea','Approved','0'),
 ('3b095edd-8cee-4ce5-8e95-fb960ea1e037','Approved','0'),
 ('3b01f9a0-dfda-4ac6-985d-059b8e84cd6d','Approved','0'),
 ('4c73ebab-83f7-4e9e-8835-2f222df920d0','Approved','0'),
 ('e0ca4f16-77cd-471f-a74d-775062801364','Approved','0'),
 ('8a9c0f1e-702c-4c1c-bdb6-aa650f62233c','Approved','0'),
 ('8a9c0f1e-702c-4c1c-bdb6-aa650f62233c','Approved','0'),
 ('b3757c5c-7d30-407d-b0df-464ca5ea5119','Approved','0'),
 ('c6099f1c-189a-43dd-aa83-d9324e6edb78','Approved','0'),
 ('c90b89ea-4fdf-4696-a608-467b29bab1d3','Approved','0'),
 ('8f057987-04e1-4582-a1cd-16a830103563','Approved','0'),
 ('8f057987-04e1-4582-a1cd-16a830103563','Approved','0'),
 ('94773333428','pending','0');
/*!40000 ALTER TABLE `regstatus` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
