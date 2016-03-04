package lk.dialog.ideabiz.model.sms;

import java.util.ArrayList;

/**
 * Created by Malinda on 7/13/2015.
 */
public class OutboundSMSMessageRequest {
    ArrayList<String> address;
    String senderAddress;
    String clientCorrelator;
    String senderName;
    OutboundSMSTextMessage outboundSMSTextMessage;
    ReceiptRequest receiptRequest;

    public OutboundSMSMessageRequest() {
    }

    public OutboundSMSMessageRequest( ArrayList<String> address, String message,String senderAddress, String senderName) {
        this.senderAddress = senderAddress;
        this.address = address;
        this.senderName = senderName;
        this.outboundSMSTextMessage = new OutboundSMSTextMessage(message);
        this.receiptRequest = new ReceiptRequest("", "");
    }

    public OutboundSMSMessageRequest(String address, String message,String senderAddress, String senderName) {

        ArrayList<String> addressL = new ArrayList<String>();
        addressL.add(address);

        this.senderAddress = senderAddress;
        this.address = addressL;
        this.senderName = senderName;
        this.outboundSMSTextMessage = new OutboundSMSTextMessage(message);
        this.receiptRequest = new ReceiptRequest("", "");
    }

    public ArrayList<String> getAddress() {
        return address;
    }

    public void setAddress(ArrayList<String> address) {
        this.address = address;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getClientCorrelator() {
        return clientCorrelator;
    }

    public void setClientCorrelator(String clientCorrelator) {
        this.clientCorrelator = clientCorrelator;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public OutboundSMSTextMessage getOutboundSMSTextMessage() {
        return outboundSMSTextMessage;
    }

    public void setOutboundSMSTextMessage(OutboundSMSTextMessage outboundSMSTextMessage) {
        this.outboundSMSTextMessage = outboundSMSTextMessage;
    }

    public ReceiptRequest getReceiptRequest() {
        return receiptRequest;
    }

    public void setReceiptRequest(ReceiptRequest receiptRequest) {
        this.receiptRequest = receiptRequest;
    }
}

