/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.psi.api.responsebean.sms;

public class DeliveryReceiptSubscription {
	private String filterCriteria;
	private String clientCorrelator;
	private CallbackReference callbackReference;

	public String getClientCorrelator() {
		return clientCorrelator;
	}

	public void setClientCorrelator(String clientCorrelator) {
		this.clientCorrelator = clientCorrelator;
	}

	public String getFilterCriteria() {
		return filterCriteria;
	}

	public void setFilterCriteria(String filterCriteria) {
		this.filterCriteria = filterCriteria;
	}

	public CallbackReference getCallbackReference() {
		return callbackReference;
	}

	public void setCallbackReference(CallbackReference callbackReference) {
		this.callbackReference = callbackReference;
	}
}
