package com.axiata.dialog.mife.mediator.entity;

public class DeliveryReceiptSubscriptionRequest implements ISMSresponse {

	private DeliveryReceiptSubscription deliveryReceiptSubscription;

	public DeliveryReceiptSubscription getDeliveryReceiptSubscription() {
		return deliveryReceiptSubscription;
	}

	public void setDeliveryReceiptSubscription(DeliveryReceiptSubscription deliveryReceiptSubscription) {
		this.deliveryReceiptSubscription = deliveryReceiptSubscription;
	}

}