package com.dialog.mife.ussd.dto;


public class Subscription {

	/*
			{
			 "subscription": {
			 "callbackReference": {
			 "callbackData": "doSomething()",
			 "notifyURL": " http://ussd.response.receive.url "
			 },
			 "destinationAddress": "3456",
			 "clientCorrelator": "12345",
			 "resourceURL": "[ussd url=""]/ussd/v1/inbound/subscriptions/sub678"
			 }
			}
			
			
			
						
			{
			    "subscription": {
			    "callbackReference": {
			      "callbackData": "doSomething()",
			      "notifyURL": " http://ussd.response.receive.url "
			    },
			    "destinationAddress": "3456",
			    "clientCorrelator": "12345",
			    "resourceURL": "[USSD URL]/ussd/v1/inbound/subscriptions/sub678"
			  }
			}			
			
			
	 */

	private callbackReference callbackReference;
	private String destinationAddress;
	private String clientCorrelator;
	private String resourceURL;

	public callbackReference getCallbackReference() {
		return callbackReference;
	}

	public void setCallbackReference(callbackReference callbackReference) {
		this.callbackReference = callbackReference;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getClientCorrelator() {
		return clientCorrelator;
	}

	public void setClientCorrelator(String clientCorrelator) {
		this.clientCorrelator = clientCorrelator;
	}

	public String getResourceURL() {
		return resourceURL;
	}

	public void setResourceURL(String resourceURL) {
		this.resourceURL = resourceURL;
	}

}

