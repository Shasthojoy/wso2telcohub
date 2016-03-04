==================
MIFE-PATCH-1.4-001
==================

Please follow the below steps to apply the patch;

--------------------------
Fix for SP Blacklist Issue
--------------------------

1) Stop wso2am-1.7.0 server if it is already running.

2) Copy "mifepatch001/wso2am-1.7.0/webapps/locationrest.war" in to below deployment location (Replace the existing .war file);

	<AM_HOME>/repository/deployment/server/webapps/

Delete the existing "locationrest" folder.

3) Copy "mifepatch001/wso2am-1.7.0/jaggeryapps/manage/modules/blacklist/blacklist.jag" in to below deployment location (Replace the existing .jag file);

	<AM_HOME>/repository/deployment/server/jaggeryapps/manage/modules/blacklist/


--------------------------------
'axiata_db' Script with Metadata 
--------------------------------

1) Run the following updated database script against the existing axiata_db;

	mifepatch001/dbscripts/axiata_db.sql

Please note that this script additionally contains the sample metadata scripts for several tables, apart from the initially released database table scripts.
Running the script against the existing axiata_db would insert the sample metadata entries in to relevant database tables.


------------------------------------------------
Fix for Wokflow Cookie Issue & Admin Login Issue
------------------------------------------------

1) Copy "mifepatch001/wso2am-1.7.0/jaggeryapps/manage/modules/user/login.jag" in to below deployment location (Replace the existing .jag file);

	<AM_HOME>/repository/deployment/server/jaggeryapps/manage/modules/user/

2) Configure "adminRole" entry in the below delpoyment file;

	<AM_HOME>/repository/deployment/server/jaggeryapps/manage/site/conf/site.json

	>> Add below entry after "allowedRoles";

		    "adminRole":"admin",

A sample configuration file containing the "adminRole" configuration entry is available in below patch file. Check for the entry and configure accordningly;

	mifepatch001/wso2am-1.7.0/jaggeryapps/manage/modules/site/conf/site.json
