==================================================
Incident: MIFE-408
Title - MC - Inline registration
==================================================

Please perform below modifications to the current HUB deployment;

1) Apply 'UserRegistration-1.0-SNAPSHOT' changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/identity/templates/deployment/server/webapps/UserRegistration-1.0-SNAPSHOT/WEB-INF".
	b) Backup the content under the folder mentioned above.
	c) Copy and paste 'classes' folder in to pupet master folder navigated to in step a). (Patch source is available under "/wso2is-5.0.0/repository/deployment/server/webapps/UserRegistration-1.0-SNAPSHOT/WEB-INF".)
	d) Go to agent node where wso2is-5.0.0 is already deployed and backup the same files in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2is-5.0.0/repository/deployment/server/webapps/UserRegistration-1.0-SNAPSHOT/WEB-INF

2) Apply 'authenticationendpoint' changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/identity/templates/deployment/server/webapps/authenticationendpoint".
	b) Backup the content under the folder mentioned above.
	c) Copy and paste 'login.jsp' file in to pupet master folder navigated to in step a). (Patch source is available under "/wso2is-5.0.0/repository/deployment/server/webapps/authenticationendpoint".)
	d) Go to agent node where wso2is-5.0.0 is already deployed and backup the same files in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2is-5.0.0/repository/deployment/server/webapps/authenticationendpoint/

3) Apply com.gsma.authenticators-1.0.0.jar to IS on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/identity/files/patches".
	b) Create the folder structure "repository/components/dropins" inside the above directory (in step a) if it does not already exist.
	c) If the folder structure already exists, backup the contents under dropins folders mentioned above.
	d) Copy and paste the 'com.gsma.authenticators-1.0.0.jar' in to pupet master folder navigated to in step b). (Patch source is available under "/wso2is-5.0.0/repository/components/dropins".)
	e) Go to agent node where wso2is-5.0.0 is already deployed and backup the same jar file in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2is-5.0.0/repository/components/dropins

4) Apply 'dashboard' jaggery app changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/identity/files/patches".
	b) Create the folder structure "repository/deployment/server/jaggeryapps" inside the above directory (in step a) if it does not already exist.
	c) If the folder structure already exists, backup the contents under folder mentioned in step b.
	d) Copy and paste the folder "dashboard" in to pupet master folder navigated to in step b). (Patch source is available under "/wso2is-5.0.0/jaggeryapps/".)
	e) Go to agent node where wso2is-5.0.0 is already deployed and backup jaggery files in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2is-5.0.0/repository/deployment/server/jaggeryapps

4) Perform below to reflect the changes on agent nodes;

	a) Restart apache2 service on puppet master.
	b) Run below puppet agent command on agent containing IS instance for the changes to reflect;
		> puppet agent --enable; puppet agent -vt --http_compression; puppet agent --disable;


