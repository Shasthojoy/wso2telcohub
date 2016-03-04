/*
* ==========================================
* MySQL script related to tables in
* Axiata database
* Database: Axiata database
* ==========================================
*/

--
-- Table structure for table `endpointapps`
--

CREATE TABLE IF NOT EXISTS `endpointapps` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `endpointid` int(11) DEFAULT NULL,
  `applicationid` int(11) DEFAULT NULL,
  `isactive` int(11) DEFAULT '0',
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_apps_endpoint` (`endpointid`,`applicationid`)
) ENGINE=InnoDB;

--
-- Table structure for table `operatorapps`
--

CREATE TABLE IF NOT EXISTS `operatorapps` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `applicationid` int(11) DEFAULT NULL,
  `operatorid` int(11) DEFAULT NULL,
  `isactive` int(11) DEFAULT '0',
  `note` varchar(255) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB;

--
-- Table structure for table `operatorendpoints`
--

CREATE TABLE IF NOT EXISTS `operatorendpoints` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `operatorid` int(11) DEFAULT NULL,
  `api` varchar(25) DEFAULT NULL,
  `isactive` int(11) DEFAULT '0',
  `endpoint` varchar(255) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB;


--
-- Table structure for table `operators`
--

CREATE TABLE IF NOT EXISTS `operators` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `operatorname` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  `refreshtoken` varchar(255) DEFAULT NULL,
  `tokenvalidity` double DEFAULT NULL,
  `tokentime` double DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `tokenurl` varchar(255) DEFAULT NULL,
  `tokenauth` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `operatorname` (`operatorname`)
) ENGINE=InnoDB;

--
-- Table structure for table `operatorsubs`
--

CREATE TABLE IF NOT EXISTS `operatorsubs` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `axiataid` int(11) DEFAULT NULL,
  `domainurl` varchar(255) DEFAULT NULL,
  `operator` varchar(45) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB;

--
-- Table structure for table `subscriptions`
--

CREATE TABLE IF NOT EXISTS `subscriptions` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `axiataid` int(11) DEFAULT NULL,
  `notifyurl` varchar(255) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB;

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

CREATE TABLE IF NOT EXIST `merchantopco_blacklist` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `application_id` int(20) DEFAULT NULL,
  `operator_id` int(20) DEFAULT NULL,
  `subscriber` varchar(40) DEFAULT NULL,
  `merchant` varchar(255) DEFAULT NULL,
  `isactive` int(11) DEFAULT '1',
  `note` varchar(255) DEFAULT NULL,
  `created` varchar(25) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastupdated` varchar(25) DEFAULT NULL,
  `lastupdated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `constr_ID` (`application_id`,`operator_id`,`subscriber`,`merchant`)
);

/*
* Default validator types provided from mife-validator component
*/
insert into validator (name, class) values ('passthru','com.axiata.dialog.mife.validators.PassThroughValidator');
insert into validator (name, class) values ('msisdn','com.axiata.dialog.mife.validators.MSISDNValidator');

