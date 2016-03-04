Incident: MIFE-685
Steps to configure identity-mgt.properties file

1.Shutdown the IS Server
2.Configure the following parameters in the <IS_HOME>/repository/conf/security/identity-mgt.properties file.
	Identity.Listener.Enable=true
	Notification.Sending.Enable=true
	Notification.Expire.Time=7200
	Notification.Sending.Internally.Managed=true
	Authentication.Policy.Enable=true
	Authentication.Policy.Account.Lock.On.Failure=true
	Authentication.Policy.Account.Lock.On.Failure.Max.Attempts=2
	Authentication.Policy.Account.Lock.Time=2
3.Restart the IS Server
