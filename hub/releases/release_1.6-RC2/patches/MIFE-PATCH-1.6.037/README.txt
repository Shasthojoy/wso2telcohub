=====================================
Incident: MIFE-817 / I-015006
Note - mifehub 1.6 prd | null value on MIFE reference code column in transaction log report
       Location and USSD transaction log report showing null value		 
=====================================

Please perform below modifications to the current HUB deployment to reflect the changes;

1) Apply AxiataMediator-1.0.0.jar to API Manager on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/files/patches".
	b) Create the folder structure "AM_HOME/repository/components/dropins" inside the above directory (in step a) if it does not already exist.
	c) If the folder structure already exists, backup the contents under dropins folders mentioned above.
	d) Copy and paste the 'AxiataMediator-1.0.0.jar' in to puppet master folder navigated to in step b). (Patch source is available under "/wso2am-1.7.0/repository/components/dropins".)
	e) Go to agent node where wso2am-1.7.0 is already deployed and backup the same jar file in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2am-1.7.0/repository/components/dropins

3) Sync changes on agent nodes;
	a) Restart apache2 service on puppet master.
	b) Run below puppet agent command on agent containing AM instance for the changes to reflect;
		> puppet agent --enable; puppet agent -vt --http_compression; puppet agent --disable;


