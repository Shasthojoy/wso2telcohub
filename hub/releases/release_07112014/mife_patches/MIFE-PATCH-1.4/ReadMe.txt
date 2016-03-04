Please follow the below steps to apply the patch;

------------------------------------
Fix for Transaction Log Report Issue
------------------------------------

1) Stop wso2am-1.7.0 server if it is already running.

2) Copy "patch/wso2am-1.7.0/jaggeryapps/manage/modules/api-wise-taffic/api-wise-traffic.jag" into following deployment location. (Replace the existing api-wise-traffic.jag file);
	<AM_HOME>/repository/deployment/server/jaggeryapps/manage/modules/api-wise-traffic/
	
3) Copy "patch/wso2am-1.7.0/jaggeryapps/manage/site/blocks/api-wise-traffic/ajax/api-wise-traffic.jag" into following deployment location. (Replace the existing api-wise-traffic.jag file);
	<AM_HOME>/repository/deployment/server/jaggeryapps/manage/site/blocks/api-wise-traffic/ajax/

--------------------------------
Fix for Customer Care Role Issue
--------------------------------

1) Stop wso2am-1.7.0 server if it is already running.

2) Copy "patch/wso2am-1.7.0/jaggeryapps/manage/modules/user/login.jag" into following deployment location. (Replace the existing api-wise-traffic.jag file);
	<AM_HOME>/repository/deployment/server/jaggeryapps/manage/modules/user/
	
3) Copy "patch/wso2am-1.7.0/jaggeryapps/manage/site/themes/default/templates/menu/left-billing-metering/template.jag" into following deployment location. (Replace the existing api-wise-traffic.jag file);
	<AM_HOME>/repository/deployment/server/jaggeryapps/manage/site/themes/default/templates/menu/left-billing-metering/
	
4) Copy "patch/wso2am-1.7.0/jaggeryapps/manage/site/blocks/menu/primary/block.jag" into following deployment location. (Replace the existing api-wise-traffic.jag file);
	<AM_HOME>/repository/deployment/server/jaggeryapps/manage/site/blocks/menu/primary/
	
5) Copy "patch/wso2am-1.7.0/jaggeryapps/manage/site/pages/billing-metering.jag" into following deployment location. (Replace the existing api-wise-traffic.jag file);
	<AM_HOME>/repository/deployment/server/jaggeryapps/manage/site/pages/
	