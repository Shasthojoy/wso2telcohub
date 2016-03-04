package com.axiata.dialog.mife.mediator.entity;

import com.axiata.dialog.mife.mediator.entity.ISMSresponse;

public class SBOutboundSubscriptionRequest implements ISMSresponse {

	private DeliveryReceiptSubscription deliveryReceiptSubscription;

	public DeliveryReceiptSubscription getDeliveryReceiptSubscription() {
		return deliveryReceiptSubscription;
	}

	public void setDeliveryReceiptSubscription(DeliveryReceiptSubscription deliveryReceiptSubscription) {
		this.deliveryReceiptSubscription = deliveryReceiptSubscription;
	}

}