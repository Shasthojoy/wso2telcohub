/*
SQLyog Community v8.6 GA
MySQL - 5.6.14 : Database - mife_sandbox_new
*********************************************************************
*/

/*Table structure for table `user` */

ALTER TABLE `user` 
 ADD UNIQUE KEY `UK_lqjrcobrh9jc8wpcar64q1bfh` (`user_name`);

/*Table structure for table `ussd_subscription` */

CREATE TABLE IF NOT EXISTS`ussd_subscription` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `callbackData` varchar(255) DEFAULT NULL,
  `clientCorrelator` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `destinationAddress` varchar(255) DEFAULT NULL,
  `effect_date` date DEFAULT NULL,
  `notifyUrl` varchar(255) DEFAULT NULL,
  `resourceUrl` varchar(255) DEFAULT NULL,
  `subscriptionId` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `subStatus` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ec81kos30iygc5p1gcfq0m9md` (`user_id`),
  CONSTRAINT `FK_ec81kos30iygc5p1gcfq0m9md` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

/*Table structure for table `ussd_transactions` */

CREATE TABLE IF NOT EXISTS`ussd_transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actionStatus` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `callbackData` varchar(255) DEFAULT NULL,
  `clientCorrelator` varchar(255) DEFAULT NULL,
  `effect_date` date DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `notifyUrl` varchar(255) DEFAULT NULL,
  `sessionId` varchar(255) DEFAULT NULL,
  `shortCode` varchar(255) DEFAULT NULL,
  `ussdAction` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_f6e3wvsa0gqeft93d6r8uo4qx` (`user_id`),
  CONSTRAINT `FK_f6e3wvsa0gqeft93d6r8uo4qx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

/*Table structure for table `sms_delivery_status` */

CREATE TABLE IF NOT EXISTS `sms_delivery_status` (
  `transaction_id` varchar(255) NOT NULL,
  `sender_address` varchar(255) DEFAULT NULL,
  `delivery_status` varchar(255) DEFAULT NULL,
  `effect_date` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `FK_sycg0sik20gocmm2v5oqwh2o8` (`user_id`),
  CONSTRAINT `FK_sycg0sik20gocmm2v5oqwh2o8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

/*Table structure for table `mobileidapiencoderequest` */

CREATE TABLE IF NOT EXISTS `mobileidapiencoderequest` (
`mobIdApiId` int(11) NOT NULL AUTO_INCREMENT,
`consumerkey` varchar(255) DEFAULT NULL,
`consumersecret` varchar(255) DEFAULT NULL,
`authcode` varchar(255) DEFAULT NULL,
`granttype` varchar(45) DEFAULT NULL,
`username` varchar(45) DEFAULT NULL,
`password` varchar(45) DEFAULT NULL,
`scope` varchar(45) DEFAULT NULL,
`user` varchar(45) DEFAULT NULL,
`refreshToken` varchar(45) DEFAULT NULL,
`accessToken` varchar(45) DEFAULT NULL,
PRIMARY KEY (`mobIdApiId`)
);

/*Table structure for table `sms_delivery_subscription` */

ALTER TABLE `sms_delivery_subscription` 
 ADD KEY `FK_adwhr1k8dr8pdh9osopmeg6b6` (`user_id`),
 ADD CONSTRAINT `FK_adwhr1k8dr8pdh9osopmeg6b6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

/*Table structure for table `smstransactionlog` */

ALTER TABLE `smstransactionlog` 
 ADD (`transaction_id` varchar(255) DEFAULT NULL, 
     `request_id` varchar(255) DEFAULT NULL);

ALTER TABLE `charge_amount_request` 
 ADD (`code` varchar(255) DEFAULT NULL, 
     UNIQUE KEY `UK_haok1xtx5f32qy18r9yt06p31` (`client_correlator`));


