===================================================
Fix for MIFE-669
[Store] Token refresh has an issue with release 1.7
===================================================

Please perform below modifications to the current HUB deployment;

2) Apply 'AxiataMediator-1.0.0.jar' changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/files/patches".
	b) Create the folder structure "repository/components/dropins/" inside the above directory. (Backup the content if folder structure already exists.)
	c) Copy and paste the artifact "AxiataMediator-1.0.0.jar" in to pupet master folder navigated to in step b). (Patch source is available under "/wso2am-1.7.0/dropins/".)
 	d) Go to agent node where wso2am-1.7.0 is already deployed and backup the same jar file in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
  
  	The actual deployment path is as follows;
  	<AGNET_NODE>/mnt/<ip_address>/wso2am-1.7.0/repository/components/dropins/


2) Sync changes on agent nodes;
	a) Restart apache2 service on puppet master.
	b) Run below puppet agent command on agent containing AM instance for the changes to reflect;
		> puppet agent --enable; puppet agent -vt --http_compression; puppet agent --disable;

 
