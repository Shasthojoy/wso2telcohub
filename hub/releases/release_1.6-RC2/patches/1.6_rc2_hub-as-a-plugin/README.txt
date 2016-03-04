==============================
HUB-as-a-PLUGIN - MIFE-1.6-RC2
==============================

Please perform below modifications to the current HUB deployment to enable PLUGIN functionality;

1) Apply 'store' and 'manage' jaggery app changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/files/patches".
	b) Create the folder structure "repository/deployment/server/jaggeryapps/" inside the above directory. (Backup the content if folder structure already exists.)
	c) Copy and paste the folder "store" and "manage" in to pupet master folder navigated to in step b). (Patch source is available under "/wso2am-1.7.0/jaggeryapps/".)
 	d) Go to agent node where wso2am-1.7.0 is already deployed and backup the same jaggery files in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
  
  	The actual deployment path is as follows;
  	<AGNET_NODE>/mnt/<ip_address>/wso2am-1.7.0/repository/deployment/server/jaggeryapps/


2) Apply billing hostobject changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/files/patches".
	b) Create the folder structure "repository/components/dropins/" inside the above directory. (Backup the content if folder structure already exists.)
	c) Copy and paste the artifact "org.dialog.custom.hostobjects-1.0.0.jar" in to pupet master folder navigated to in step b). (Patch source is available under "/wso2am-1.7.0/dropins/".)
 	d) Go to agent node where wso2am-1.7.0 is already deployed and backup the same jar file in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
  
  	The actual deployment path is as follows;
  	<AGNET_NODE>/mnt/<ip_address>/wso2am-1.7.0/repository/components/dropins/


3) Sync changes on agent nodes;
	a) Restart apache2 service on puppet master.
	b) Run below puppet agent command on agent containing AM instance for the changes to reflect;
		> puppet agent --enable; puppet agent -vt --http_compression; puppet agent --disable;


4) Upload Axiata configurations file in to registry location;
	a) Log in to AM Management Console.
	b) Main --> resources --> Browse
	c) Navigate to "/_system/governance/apimgt/applicationdata".
	d) Upload the file "axiata-configs.xml" available under "<PATCH_HOME>/wso2am-1.7.0/" by adding a new resource.
	e) In the added resource, set "DeploymentType" as "PLUGIN" for the implementation to act as a PLUGIN.
	f) If the above value is set to "PLUGIN", configure "PluginOperator" to contain the operator name. Make sure this value is similar to the value available in rate-card.xml
 and "operators" table in Axiata DB (case sensitive).


5) Upload the process file to BPS;

	a) Log in to BPS Management console.
	b) Main → Manage → Processes → Add.
	c) Add te below process files;
		i)  ApplicationApprovalWorkFlowProcess.zip
		ii) SubscriptionApprovalWorkFlowProcess.zip


Assumption:
-----------

1) 'operators' table of AXIATA_MIFE_DB contains only 1 record which is the expected PLUGIN operator. Sync 'operatorendpoints' table accordingly.
 
