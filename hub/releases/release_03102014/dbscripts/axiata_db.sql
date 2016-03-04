/*
* ==========================================
* MySQL script related to tables in
* Axiata database
* Database: Axiata database
* ==========================================
*/

/*
* Table for subscription approval operator list.
*/
CREATE TABLE IF NOT EXISTS `sub_approval_operators` (
	`API_NAME` varchar(200) DEFAULT NULL,
	`API_VERSION` varchar(30) DEFAULT NULL,
	`API_PROVIDER` varchar(200) DEFAULT NULL,
	`APP_ID` int(11) DEFAULT NULL,
	`OPERATOR_LIST` varchar(512) DEFAULT NULL,
	PRIMARY KEY (`API_NAME`, `API_VERSION`, `API_PROVIDER`, `APP_ID`)
);

/*
* Table for validator definitions used in AxiataMediator
*/
CREATE TABLE IF NOT EXISTS validator ( 
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    class VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

/*
* Table for API subscriptions to validator mapping
*/
CREATE TABLE IF NOT EXISTS subscription_validator ( 
    application_id INT NOT NULL,
    api_id INT NOT NULL,
    validator_id INT NOT NULL,
    PRIMARY KEY (application_id, api_id),
    FOREIGN KEY (validator_id) REFERENCES validator (id) ON UPDATE CASCADE
);

/*
* Default validator types provided from mife-validator component
*/
insert into validator (name, class) values ('passthru','com.axiata.dialog.mife.validators.PassThroughValidator');
insert into validator (name, class) values ('msisdn','com.axiata.dialog.mife.validators.MSISDNValidator');

/*
* Default operators
*/
INSERT INTO `operators` (`ID`, `operatorname`, `description`, `created`, `created_date`, `lastupdated`, `lastupdated_date`, `refreshtoken`, `tokenvalidity`, `tokentime`, `token`, `tokenurl`, `tokenauth`) VALUES
  (1, 'DIALOG', 'Dialog Opearator', 'axatauser', NULL, 'axatauser', NULL, 'gGgvUANAGhRUzWTyXwYoGuk3WzQa', 157680000, 1395135145139, '4fb164d70def9f37b2f8e2f1daf467', 'http://localhost:8281/token', 'Basic U1JObDQzXzRTVks5MjZaVnNteXExOU1JNVFRYTpEV1Flb2NDeUVyN0lHYk8zRHJxRDc5SmtzVFVh'),
  (2, 'CELCOM', 'Celcom Opearator', 'axatauser', NULL, 'axatauser', NULL, 'gGgvUANAGhRUzWTyXwYoGuk3WzQa', 157680000, 1395135145139, '4fb164d70def9f37b2f8e2f1daf467', 'http://localhost:8281/token', 'Basic U1JObDQzXzRTVks5MjZaVnNteXExOU1JNVFRYTpEV1Flb2NDeUVyN0lHYk8zRHJxRDc5SmtzVFVh'),
  (3, 'ROBI', 'Robi Opearator', 'axatauser', NULL, 'axatauser', NULL, 'gGgvUANAGhRUzWTyXwYoGuk3WzQa', 157680000, 1395135145139, '4fb164d70def9f37b2f8e2f1daf467', 'http://localhost:8281/token', 'Basic U1JObDQzXzRTVks5MjZaVnNteXExOU1JNVFRYTpEV1Flb2NDeUVyN0lHYk8zRHJxRDc5SmtzVFVh');

/*
* Default operator endpoints point to the sandbox
*/
INSERT INTO `operatorendpoints` (`ID`, `operatorid`, `api`, `isactive`, `endpoint`, `created`, `created_date`, `lastupdated`, `lastupdated_date`) VALUES
  (1, 1, 'smsmessaging', 1, 'http://localhost:8081/mifesandbox/SandboxController/smsmessaging/1', NULL, '2014-03-04 11:36:23', NULL, NULL),
  (2, 1, 'payment', 1, 'http://localhost:8081/mifesandbox/SandboxController/payment/1', NULL, '2014-03-04 11:36:58', NULL, NULL),
  (3, 3, 'location', 1, 'http://localhost:8081/mifesandbox/SandboxController/location/1', NULL, '2014-10-02 10:33:41', NULL, NULL);

