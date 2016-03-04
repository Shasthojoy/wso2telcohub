Incident: MIFE-468

Please follow the below steps to apply the patch;

Steps to install

1. Stop wso2am-1.7.0 server if it is already running

2. Copy "MobileCountryConfig.xml" in to below deployment location (Replace the existing files);
    
  <AM_HOME>/repository/conf

  Note* / Please use endpoint urls lowercase for Dialog plugin (ideaBiz). We have notice an issue when uppercase urls been used in ideabiz network
  eg:   https://ideabiz.lk/apicall/smsmessaging/v1/

3. Replace 'site.json' in <AM_HOME>/repository/deployment/server/jaggeryapps/manage/site/conf with the site.json provided in the patch

4. Add 'mife.xl.co.id' to <AM_HOME>/repository/resources/security/client-truststore.jks 

5. Start wso2am-1.7.0 server

6. Update the content of the rate-card.xml file.
		I. Log in to the API Manager Management Console, click Browse under the Resources menu and select the following file: 	
			/_system/governance/apimgt/applicationdata/rate-card.xml
		II. In the Contents panel, click Edit as text and the throttling policy opens.
		III. Replace the entire content and click Save Content.

 


	