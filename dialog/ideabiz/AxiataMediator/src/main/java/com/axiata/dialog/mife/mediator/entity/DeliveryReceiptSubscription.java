package com.axiata.dialog.mife.mediator.entity;

import com.axiata.dialog.mife.mediator.entity.CallbackReference;

public class DeliveryReceiptSubscription {

	private CallbackReference callbackReference;
	private String filterCriteria;
	private String resourceURL;

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

	public String getResourceURL() {
		return resourceURL;
	}

	public void setResourceURL(String resourceURL) {
		this.resourceURL = resourceURL;
	}

}