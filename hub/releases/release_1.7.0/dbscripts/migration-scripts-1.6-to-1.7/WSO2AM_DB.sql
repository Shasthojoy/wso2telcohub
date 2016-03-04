/*
* ==========================================
* MySQL script related to custom tables in
* Api Manager  database
* Database: AM database
* ==========================================
*/

alter table `IDN_OAUTH2_ACCESS_TOKEN` modify column `TOKEN_SCOPE` varchar(255);
