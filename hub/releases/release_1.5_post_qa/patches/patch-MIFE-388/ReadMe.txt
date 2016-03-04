Incident: MIFE-388
Steps to install

1. Shut down API manager.
2. Add provided 'dialog-handler-1.0.0.jar' to <AM_HOME>/repository/components/dropins 
3. Add following handler to 'admin--location_vv1.xml' in <AM_HOME>repository/deployment/server/synapse-configs/default/api
		 <handler class="org.wso2.carbon.apimgt.axiata.dialog.verifier.DialogBlacklistHandler"/>
4. Restart the server

NOTE --------------------->
Use above handler only for admin--location_vv1.xml. 
