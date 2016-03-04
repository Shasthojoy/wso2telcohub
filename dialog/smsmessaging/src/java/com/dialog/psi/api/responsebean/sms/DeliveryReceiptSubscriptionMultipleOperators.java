package com.dialog.psi.api.responsebean.sms;

public class DeliveryReceiptSubscriptionMultipleOperators {
	private String operatorCode;
	private String clientCorrelator;
	private String filterCriteria;

	private CallbackReference callbackReference;

	public String getClientCorrelator() {
		return clientCorrelator;
	}

	public void setClientCorrelator(String clientCorrelator) {
		this.clientCorrelator = clientCorrelator;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
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
