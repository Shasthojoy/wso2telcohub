package com.axiata.dialog.mife.mediator.entity;


public class outboundSMSMessageResponse {

    private String[] address;
    private DeliveryInfoList deliveryInfoList;
    private String senderAddress;
    private outboundSMSTextMessage outboundSMSTextMessage;
    private String clientCorrelator;
    private ReceiptRequest receiptRequest;
    private String senderName;
    private String resourceURL;

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public DeliveryInfoList getDeliveryInfoList() {
        return deliveryInfoList;
    }

    public void setDeliveryInfoList(DeliveryInfoList deliveryInfoList) {
        this.deliveryInfoList = deliveryInfoList;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public com.axiata.dialog.mife.mediator.entity.outboundSMSTextMessage getOutboundSMSTextMessage() {
        return outboundSMSTextMessage;
    }

    public void setOutboundSMSTextMessage(com.axiata.dialog.mife.mediator.entity.outboundSMSTextMessage
                                                  outboundSMSTextMessage) {
        this.outboundSMSTextMessage = outboundSMSTextMessage;
    }

    public String getClientCorrelator() {
        return clientCorrelator;
    }

    public void setClientCorrelator(String clientCorrelator) {
        this.clientCorrelator = clientCorrelator;
    }

    public ReceiptRequest getReceiptRequest() {
        return receiptRequest;
    }

    public void setReceiptRequest(ReceiptRequest receiptRequest) {
        this.receiptRequest = receiptRequest;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }
}
