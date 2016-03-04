How to Deploy Aggregatorblacklist?
=========================================

###########################################################################################
###########################################################################################


Steps:
------

1. enable user claims in JWT token header,

uncomment following line in the <AM-HOME>\repository\conf\api-manager.xml

<ClaimsRetrieverImplClass>org.wso2.carbon.apimgt.impl.token.DefaultClaimsRetriever</ClaimsRetrieverImplClass>

2. Create new role 'aggregator' and attached with the aggregator service providers using carbon console,

3. Deploy Aggregatorblacklist.war module to API manager

4. Provision aggregator merchants blacklist entries using following RESTful web service call,

curl -H "Content-Type: application/json" -d @data.txt "http://<api-mgr-server>:9763/Aggregatorblacklist/aggregator/merchant/blacklist"

data.txt file contain the provision request payload.

(** NOTE: If blacklisting is for all application related to a service provider, remove the applicationid attribute from the payload request )

eg:
{"provisioninfo":{
  "subscriber":"admin",
  "applicationid":"8",
  "merchantcode":["ROSHAN","MAHESH"],
  "operatorcode":"DIALOG"
  }
}