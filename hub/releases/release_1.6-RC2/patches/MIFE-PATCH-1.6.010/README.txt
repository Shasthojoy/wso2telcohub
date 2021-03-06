=====================================
Incident: MIFE-684
Title - error occurs in USSDInboundHandler class when attempting to register via USSD PIN
Description - error occurs in USSDInboundHandler class when attempting to register via USSD PIN
=====================================

Please perform below modifications to the current HUB deployment;

1) Apply 'AxiataMediator-1.0.0.jar' changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/files/patches".
	b) Create the folder structure "repository/components/dropins/" inside the above directory if it does not already exist.
	c) If the folder structure already exists, backup the contents under jaggeryapps folder mentioned above.
	d) Copy and paste 'AxiataMediator-1.0.0.jar' in to pupet master folder navigated to in step b). (Patch source is available under "/wso2am-1.7.0/dropins/".)
	e) Go to agent node where wso2am-1.7.0 is already deployed and backup the same file in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2am-1.7.0/repository/components/dropins/

2) Apply 'axiataMediator_conf.properties' changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/templates/conf".
	b) Copy file 'axiataMediator_conf.properties.erb' to above location. (Patch source can be found at "/wso2am-1.7.0/repository/conf".)


3) Apply configuration changes on puppet master;
	a) On puppet maseter, navigate to "/mnt/puppet/environments/qa_rel_160/manifests".
	b) Open file "site.pp".
	c) At the end of the section "node confignode inherits basenode  {" enter below entries and save the file;

	## Axiata Mediator Properties
  	$ussd_gateway_endpoint="https://gateway1a.mife.sla-mobile.com.my:8243/ussd/v1/inbound/"

	d) On puppet master, naviagte to "/mnt/puppet/environments/qa_rel_160/modules/apimanager/manifests".
	e) Open file "init.pp".
	f) Under "$service_templates" section add the below entry and save the file;

	"conf/axiataMediator_conf.properties",


4) Perform below to reflect the changes on agent nodes;

	a) Restart apache2 service on puppet master.
	b) Run below puppet agent command on agent containing AM instance for the changes to reflect;
		> puppet agent --enable; puppet agent -vt --http_compression; puppet agent --disable;


