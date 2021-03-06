/*
* ==========================================
* MySQL  script related to tables in
* Dialog Mobile Connect.
* Database: Connect database
* ==========================================
*/


-- Table structure for table `mepin_accounts`
--
CREATE TABLE IF NOT EXISTS `mepin_accounts` (
  `user_id` varchar(25) DEFAULT NULL,
  `mepin_id` varchar(255) DEFAULT NULL,
  `created_at` timestamp,
  `updated_at` timestamp,
  PRIMARY KEY (`user_id`)
);

--
-- Table structure for table `mepin_transactions`
--
CREATE TABLE IF NOT EXISTS `mepin_transactions` (
  `session_id` varchar(255) DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `mepin_id` varchar(255) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `created_at` timestamp,
  `updated_at` timestamp,
  PRIMARY KEY (`session_id`)
);
