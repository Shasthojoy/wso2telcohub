=====================================
Incident: MIFE-736 / I-013614
=====================================

Please perform below modifications to the current HUB deployment to rMIFE-736eflect the changes;

1) Apply AxiataMediator-1.0.0.jar to API Manager on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/files/patches".
	b) Create the folder structure "repository/components/dropins" inside the above directory (in step a) if it does not already exist.
	c) If the folder structure already exists, backup the contents under dropins folder mentioned above.
	d) Copy and paste the 'AxiataMediator-1.0.0.jar' in to pupet master folder navigated to in step b). (Patch source is available under "/wso2am-1.7.0/repository/components/dropins".)
	e) Go to agent node where wso2am-1.7.0 is already deployed and backup the same jar file in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2am-1.7.0/repository/components/dropins

2) Sync changes on agent nodes;
	a) Restart apache2 service on puppet master.
	b) Run below puppet agent command on agent containing AM instance for the changes to reflect;
		> puppet agent --enable; puppet agent -vt --http_compression; puppet agent --disable;


