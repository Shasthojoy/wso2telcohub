package com.axiata.dialog.mife.mediator.mediationrule;

import java.util.HashMap;

import org.apache.axis2.addressing.EndpointReference;


public class OriginatingCountryCalculatorGeoLocation extends OriginatingCountryCalculator{

	public void initialize(){
		
		
	    
	    apiEprMap = new HashMap<String, EndpointReference>();
	    
	    apiEprMap.put("Dialog sms",
                             new EndpointReference(DialogSmsApiEndpoint));
	    apiEprMap.put("Dialog payment", new EndpointReference(DialogPaymentApiEndpoint));
            apiEprMap.put("Dialog location", new EndpointReference(DialogLocationApiEndpoint));
	    
	    apiEprMap.put("Celcom sms",
             new EndpointReference(CelcomSmsApiEndpoint));
	    
	    apiEprMap.put("Celcom payment", new EndpointReference(CelcomPaymentApiEndpoint));
            apiEprMap.put("Celcom location", new EndpointReference(CelcomLocationApiEndpoint));
            
            apiEprMap.put("Robi sms", new EndpointReference(RobiSmsApiEndpoint));	    
	    apiEprMap.put("Robi payment", new EndpointReference(RobiPaymentApiEndpoint));
            apiEprMap.put("Robi location", new EndpointReference(RobiLocationApiEndpoint));
 }
	
}
