/*
* ==========================================
* MySQL script related to custom tables in
* Api Manager Stats database
* Database: AM stats database
* ==========================================
*/

ALTER TABLE `subscription_rates` CHANGE `rate_name` `rate_id_sb` varchar( 50 ) ;


ALTER TABLE `subscription_rates` ADD `operation_id` int( 11 ) ;


ALTER TABLE `subscription_rates` DROP PRIMARY KEY ,
ADD PRIMARY KEY ( `application_id` , `api_id` , `operator_name` , `operation_id` ) ;

