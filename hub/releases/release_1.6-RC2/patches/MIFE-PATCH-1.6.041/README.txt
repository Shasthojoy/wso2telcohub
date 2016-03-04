=====================================
Incident: MIFE-913, MIFE-928 / I-015412
=====================================

Please perform below modifications to the current HUB deployment to reflect the changes;

1) Apply AxiataMediator-1.0.0.jar to API Manager on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/files/patches".
	b) Create the folder structure "repository/components/dropins" inside the above directory (in step a) if it does not already exist.
	c) If the folder structure already exists, backup the contents under dropins folders mentioned above.
	d) Copy and paste the 'AxiataMediator-1.0.0.jar' in to puppet master folder navigated to in step b). (Patch source is available under "/wso2am-1.7.0/repository/components/dropins".)
	e) Go to agent node where wso2am-1.7.0 is already deployed and backup the same jar file in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2am-1.7.0/repository/components/dropins

2) Apply configuration changes on puppet master;
	a) On puppet maseter, navigate to "/mnt/puppet/environments/qa_rel_160/manifests".
	b) Open file "site.pp".
	c) At the end of the section "node confignode inherits basenode  {" enter below entries and save the file;

	## Axiata Mediator Properties
  	$ussd_gateway_endpoint="https://gateway1a.mife.sla-mobile.com.my:8243/ussd/v1/inbound/"
  	$hub_gateway_id="0001"

	d) On puppet master, naviagte to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/manifests".
	e) Open file "init.pp".
	f) Under "$service_templates" section add the below entry and save the file;

	"conf/axiataMediator_conf.properties",

3) Sync changes on agent nodes;
	a) Restart apache2 service on puppet master.
	b) Run below puppet agent command on agent containing AM instance for the changes to reflect;
		> puppet agent --enable; puppet agent -vt --http_compression; puppet agent --disable;


