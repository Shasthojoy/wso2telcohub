package com.axiata.dialog.mife.mediator.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "deliveryInfoNotification")
public class OutboundRequest {
	
	private DeliveryInfoNotification deliveryInfoNotification;

	public DeliveryInfoNotification getDeliveryInfoNotification() {
		return deliveryInfoNotification;
	}

	public void setDeliveryInfoNotification(DeliveryInfoNotification deliveryInfoNotification) {
		this.deliveryInfoNotification = deliveryInfoNotification;
	}

}
