package com.axiata.dialog.mife.mediator.mediationrule;


import org.apache.axis2.addressing.EndpointReference;


import java.util.HashMap;

public abstract class OriginatingCountryCalculator {
	/*
     * API EndPoint map.
     * for the same purpose , database can also be used
     * for the POC map is used. In future will shift to database
     */
	 protected HashMap<String, EndpointReference> apiEprMap;
	
     //API End points 
	 protected String DialogSmsApiEndpoint;
	 protected String DialogPaymentApiEndpoint;
         protected String DialogLocationApiEndpoint;
         
	 protected String CelcomSmsApiEndpoint;
	 protected String CelcomPaymentApiEndpoint;
         protected String CelcomLocationApiEndpoint;
         
         protected String RobiSmsApiEndpoint;
	 protected String RobiPaymentApiEndpoint;
         protected String RobiLocationApiEndpoint;
	 
	 /*
	  * Initialize 
	  */
	 public abstract void initialize() throws Exception ;
	 
}
