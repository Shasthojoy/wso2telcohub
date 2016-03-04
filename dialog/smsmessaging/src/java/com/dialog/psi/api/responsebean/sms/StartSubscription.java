/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.psi.api.responsebean.sms;

public class StartSubscription {
	private DeliveryReceiptSubscription deliveryReceiptSubscription;

	public DeliveryReceiptSubscription getDeliveryReceiptSubscription() {
		return deliveryReceiptSubscription;
	}

	public void setDeliveryReceiptSubscription(
			DeliveryReceiptSubscription deliveryReceiptSubscription) {
		this.deliveryReceiptSubscription = deliveryReceiptSubscription;
	}
}
