=====================================
Incident: MIFE-787, MIFE-788 / I-014330, I-014331
Note - 	The portal slow in generating customer care report when high data entry stored in the MySQL DB, The portal slow in generating transaction log report when high data entry stored in the MySQL DB	 
=====================================

Please perform below modifications to the current HUB deployment to reflect the changes;

1) Apply org.dialog.custom.hostobjects-1.0.0.jar to API Manager on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/files/patches".
	b) Create the folder structure "repository/components/dropins" inside the above directory (in step a) if it does not already exist.
	c) If the folder structure already exists, backup the contents under dropins folders mentioned above.
	d) Copy and paste the 'org.dialog.custom.hostobjects-1.0.0.jar' in to puppet master folder navigated to in step b). (Patch source is available under "/wso2am-1.7.0/repository/components/dropins".)
	e) Go to agent node where wso2am-1.7.0 is already deployed and backup the same jar file in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2am-1.7.0/repository/components/dropins

2) Apply 'manage' jaggery app changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/files/patches".
	b) Create the folder structure "repository/deployment/server/jaggeryapps/" inside the above directory if it does not already exist.
	c) If the folder structure already exists, backup the contents under jaggeryapps folder mentioned above.
	d) Copy and paste the folder 'manage' in to pupet master folder navigated to in step b). (Patch source is available under "/wso2am-1.7.0/jaggeryapps/".)
	e) Go to agent node where wso2am-1.7.0 is already deployed and backup the same jaggery files in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2am-1.7.0/repository/deployment/server/jaggeryapps	

2) Sync changes on agent nodes;
	a) Restart apache2 service on puppet master.
	b) Run below puppet agent command on agent containing AM instance for the changes to reflect;
		> puppet agent --enable; puppet agent -vt --http_compression; puppet agent --disable;


