package com.axiata.dialog.mife.mediator.entity;

public class DeliveryInfoList {

    private DeliveryInfo[] deliveryInfo;
    private String resourceURL;

    public DeliveryInfo[] getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo[] deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }
}
