Incident: MIFE-463 

Please follow the below steps to apply the patch;

Steps to install

1. Stop wso2am-1.7.0 server if it is already running.

2. Copy "AxiataMediator-1.0.0.jar" and "mnc-resolver-1.0.0.jar" jar files in to below deployment location (Replace the existing .jar file);

	<AM_HOME>/repository/components/dropins

3. Copy "MobileCountryConfig.xml" in to below deployment location (Replace the existing files);
    
  <AM_HOME>/repository/conf

  Note* / Please use endpoint urls lowercase for Dialog plugin (ideaBiz). We have notice an issue when uppercase urls been used in ideabiz network
  eg:   https://ideabiz.lk/apicall/smsmessaging/v1/