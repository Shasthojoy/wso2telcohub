/*
* ==========================================
* MySQL migration script related to tables in
* Dialog Mobile Connect.
* Database: Connect database
* ==========================================
*/

ALTER TABLE `regstatus` 
	MODIFY COLUMN `username` VARCHAR(255),
	MODIFY COLUMN `status` VARCHAR(255),
	ADD `pin` varchar(10),
	DROP PRIMARY KEY;