-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.28 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table mife_sandbox_new.charge_amount_request
CREATE TABLE IF NOT EXISTS `charge_amount_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `effect_date` date DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `channel` varchar(255) DEFAULT NULL,
  `client_correlator` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_user_id` varchar(255) DEFAULT NULL,
  `notify_url` varchar(255) DEFAULT NULL,
  `on_behalf_of` varchar(255) DEFAULT NULL,
  `purchase_cat_code` varchar(255) DEFAULT NULL,
  `reference_code` varchar(255) DEFAULT NULL,
  `tax_amount` double DEFAULT NULL,
  `tran_oper_status` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `callback_data` varchar(255) DEFAULT NULL,
  `mandate_id` varchar(255) DEFAULT NULL,
  `notification_format` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `reference_sequence` int(11) DEFAULT NULL,
  `original_server_reference_code` varchar(255) DEFAULT NULL,
  `service_id` varchar(255) DEFAULT NULL,
  `total_amount_charged` double DEFAULT NULL,
  `amount_reserved` double DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `payment_transaction_type` int(11) DEFAULT NULL,
  `refund_status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FKB48C1E939E083448` (`user_id`),
  CONSTRAINT `FKB48C1E939E083448` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.locationparam
CREATE TABLE IF NOT EXISTS `locationparam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `altitude` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `loc_ret_status` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `locationparam_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.locationtransactionlog
CREATE TABLE IF NOT EXISTS `locationtransactionlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `requested_accuracy` double DEFAULT NULL,
  `tran_oper_status` varchar(255) DEFAULT NULL,
  `effect_date` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `locationtransactionlog_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.numbers
CREATE TABLE IF NOT EXISTS `numbers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(255) DEFAULT NULL,
  `num_balance` double DEFAULT NULL,
  `reserved_amount` double NOT NULL DEFAULT '0',
  `num_description` varchar(255) DEFAULT NULL,
  `num_status` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK88C28E4A9E083448` (`user_id`),
  CONSTRAINT `FK88C28E4A9E083448` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.paymentparam
CREATE TABLE IF NOT EXISTS `paymentparam` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `created` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `lastupdated` varchar(255) DEFAULT NULL,
  `lastupdated_date` datetime DEFAULT NULL,
  `maxamt` double(11,2) DEFAULT NULL,
  `maxtrn` int(11) DEFAULT NULL,
  `paystatus` varchar(255) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.payment_gen
CREATE TABLE IF NOT EXISTS `payment_gen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `delivery_status` varchar(255) DEFAULT NULL,
  `max_pay_amount` varchar(255) DEFAULT NULL,
  `max_tx_perday` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA4347D979E083448` (`user_id`),
  CONSTRAINT `FKA4347D979E083448` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.payment_transaction
CREATE TABLE IF NOT EXISTS `payment_transaction` (
  `transaction_id` varchar(255) NOT NULL,
  `effect_date` date DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `currency` varchar(50) DEFAULT NULL,
  `end_user_id` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `FKB154785395263845` (`user_id`),
  CONSTRAINT `FKB48C1E55465448` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.sender_address
CREATE TABLE IF NOT EXISTS `sender_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `shortcode` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB79857EA9E083448` (`user_id`),
  CONSTRAINT `FKB79857EA9E083448` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.send_sms_to_application
CREATE TABLE IF NOT EXISTS `send_sms_to_application` (
  `sms_id` int(11) NOT NULL AUTO_INCREMENT,
  `effect_date` date DEFAULT NULL,
  `destination_address` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `sender_address` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`sms_id`),
  KEY `FKEBE4BF499E083448` (`user_id`),
  CONSTRAINT `FKEBE4BF499E083448` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.sms
CREATE TABLE IF NOT EXISTS `sms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deliveryStatus` varchar(255) DEFAULT NULL,
  `maxNotifications` varchar(255) DEFAULT NULL,
  `notificationDelay` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1BD599E083448` (`user_id`),
  CONSTRAINT `FK1BD599E083448` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.smsparam
CREATE TABLE IF NOT EXISTS `smsparam` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `deliveryStatus` varchar(45) DEFAULT NULL,
  `maxNotifications` varchar(11) DEFAULT NULL,
  `notificationDelay` varchar(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `created` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `lastupdated` varchar(255) DEFAULT NULL,
  `lastupdated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.smstransactionlog
CREATE TABLE IF NOT EXISTS `smstransactionlog` (
  `sms_id` int(11) NOT NULL AUTO_INCREMENT,
  `effect_date` date DEFAULT NULL,
  `addresses` varchar(255) DEFAULT NULL,
  `callback_data` varchar(255) DEFAULT NULL,
  `client_correlator` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `notify_url` varchar(255) DEFAULT NULL,
  `sender_address` varchar(255) DEFAULT NULL,
  `sender_name` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `batchsize` int(11) DEFAULT NULL,
  `criteria` varchar(255) DEFAULT NULL,
  `notificationFormat` varchar(255) DEFAULT NULL,
  `trnstatus` varchar(255) DEFAULT NULL,
  `txntype` int(11) DEFAULT NULL,
  PRIMARY KEY (`sms_id`),
  KEY `FK2A1D0F729E083448` (`user_id`),
  CONSTRAINT `FK2A1D0F729E083448` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.



CREATE TABLE IF NOT EXISTS `sms_delivery_subscription` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `sender_address` varchar(225) DEFAULT NULL,
  `sub_status` int(11) NOT NULL DEFAULT '0',
  `notify_url` varchar(225) DEFAULT NULL,
  `filter` varchar(225) DEFAULT NULL,
  `callbackdata` varchar(225) DEFAULT NULL,
  `clientcorrelator` varchar(225) DEFAULT NULL,
  `request` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- Dumping structure for table mife_sandbox_new.sms_subscription
CREATE TABLE IF NOT EXISTS `sms_subscription` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sub_number` varchar(255) DEFAULT NULL,
  `sub_status` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA48A3E439E083448` (`user_id`),
  CONSTRAINT `FKA48A3E439E083448` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.subscribe_sms_request
CREATE TABLE IF NOT EXISTS `subscribe_sms_request` (
  `subscribe_id` int(11) NOT NULL AUTO_INCREMENT,
  `effect_date` date DEFAULT NULL,
  `callback_data` varchar(255) DEFAULT NULL,
  `client_correlator` varchar(255) DEFAULT NULL,
  `criteria` varchar(255) DEFAULT NULL,
  `destination_address` varchar(255) DEFAULT NULL,
  `notification_format` varchar(255) DEFAULT NULL,
  `notify_url` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`subscribe_id`),
  KEY `FKC8368A349E083448` (`user_id`),
  CONSTRAINT `FKC8368A349E083448` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table mife_sandbox_new.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `user_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
