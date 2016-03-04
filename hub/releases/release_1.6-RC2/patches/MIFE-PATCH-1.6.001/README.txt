=====================================
Token Generation Issue - MIFE-1.6-RC2
=====================================

Please perform below modifications to the current HUB deployment to reflect the changes;

1) Apply 'store' and 'manage' jaggery app changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/files/patches".
	b) Create the folder structure "repository/deployment/server/jaggeryapps/" inside the above directory if it does not already exist.
	c) If the folder structure already exists, backup the contents under jaggeryapps folder mentioned above.
	d) Copy and paste the folder "store" and 'manage' in to pupet master folder navigated to in step b). (Patch source is available under "/wso2am-1.7.0/jaggeryapps/".)
	e) Go to agent node where wso2am-1.7.0 is already deployed and backup the same jaggery files in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2am-1.7.0/repository/deployment/server/jaggeryapps	

	f) Restart apache2 service on puppet master.
	g) Run below puppet agent command on agent containing AM instance for the changes to reflect;
		> puppet agent --enable; puppet agent -vt --http_compression; puppet agent --disable;


