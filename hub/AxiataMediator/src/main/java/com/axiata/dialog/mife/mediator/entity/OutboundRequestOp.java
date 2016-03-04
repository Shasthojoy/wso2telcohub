package com.axiata.dialog.mife.mediator.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "deliveryInfoNotification")
public class OutboundRequestOp {
	
	private DeliveryInfoNotificationOp deliveryInfoNotification;

	public DeliveryInfoNotificationOp getDeliveryInfoNotification() {
		return deliveryInfoNotification;
	}

	public void setDeliveryInfoNotification(DeliveryInfoNotificationOp deliveryInfoNotification) {
		this.deliveryInfoNotification = deliveryInfoNotification;
	}

}
