==================================================
Incident: MIFE-602
Title - Pin Reset is not working for release 1.6.1-release
==================================================

Please perform below modifications to the current HUB deployment;

1) Apply 'UserRegistration-1.0-SNAPSHOT' changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/identity/templates/deployment/server/webapps/UserRegistration-1.0-SNAPSHOT/WEB-INF".
	b) Backup the content under the folder mentioned above.
	c) Copy and paste 'classes' folder in to pupet master folder navigated to in step a). (Patch source is available under "/wso2is-5.0.0/repository/deployment/server/webapps/UserRegistration-1.0-SNAPSHOT/WEB-INF".)
	d) Go to agent node where wso2is-5.0.0 is already deployed and backup the same files in the actual deployment, mapping to the patched source file path. These original files before applying the patch will be needed to revert the patch changes if required.  
		
		The actual deployment path is as follows;
		<AGNET_NODE>/mnt/<ip_address>/wso2is-5.0.0/repository/deployment/server/webapps/UserRegistration-1.0-SNAPSHOT/WEB-INF

2) Apply 'application.properties' changes on puppet master;
	a) On puppet master, navigate to "/mnt/puppet/environments/qa_rel_160/modules/identity/templates/deployment/server/webapps/UserRegistration-1.0-SNAPSHOT/WEB-INF/classes/com/mycompany/mavenproject1".
	b) Back up the file "application.properties.erb" in the above location.
	b) Copy file 'application.properties.erb' to the same location. (Patch source can be found at "/puppetmaster".)


3) Apply configuration changes on puppet master;
	a) On puppet maseter, navigate to "/mnt/puppet/environments/qa_rel_160/manifests".
	b) Open file "site.pp".
	c) Inside the section "node confignode inherits basenode  {" enter below entries (if it does not already exist) and save the file;

  	## Identity specific settings
  	$ussd_send_url        = "http://ideabiz.lk/apicall/ussd/v1/outbound"
 	$ussd_notify_url      = "http://172.26.92.6:9763/HTTPRequestRouter/route?org=http%3A%2F%2F128.199.174.220%2Fideamart%2Froute%2F%3Fx_url%3Dhttps%253A%252F%252Fidentity.qa.example.com%253A443%252FUserRegistration-1.0-SNAPSHOT%252Fwebresources%252Fendpoint%252Fussd%252Freceive"
  	$ussd_access_token    = "d0f865b4453be954b03b8b67556a395"
  	$password             = "3LYzn3ph"
  	$application_id       = "APP_002066"
  	$callback_url         = "http://54.92.164.221:9764/mavenproject1-1.0-SNAPSHOT/webresources/endpoint/sms/response"
  	$sms_endpoint         = "https://apistore.dialog.lk/apicall/sms/1.0/"
  	$login_notify_url     = "http://10.62.96.187:9764/MediationTest/tnspoints/endpoint/ussd/receive"
  	$notify_url_login     = "http://10.62.96.187:9764/MediationTest/tnspoints/endpoint/ussd/pin"

	d) Enter the values for each above entry accordingly.


4) Perform below to reflect the changes on agent nodes;

	a) Restart apache2 service on puppet master.
	b) Run below puppet agent command on agent containing AM instance for the changes to reflect;
		> puppet agent --enable; puppet agent -vt --http_compression; puppet agent --disable;


