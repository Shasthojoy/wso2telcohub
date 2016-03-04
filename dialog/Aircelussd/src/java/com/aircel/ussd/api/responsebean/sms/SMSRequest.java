package com.aircel.ussd.api.responsebean.sms;

import java.util.List;

/**
 * @author Charith_02380
 *
 */
public class SMSRequest {

    private String applicationId = "";
    private String password = "";
    private String version = "";
    private List<String> destinationAddresses;
    private String message = "";
    private String sourceAddress;
    private String deliveryStatusRequest = "";
    private String encoding = "";
    private String chargingAmount = "";
    private String binaryHeader = "";

    /**
     *
     */
    public SMSRequest() {
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDeliveryStatusRequest() {
        return deliveryStatusRequest;
    }

    public void setDeliveryStatusRequest(String deliveryStatusRequest) {
        this.deliveryStatusRequest = deliveryStatusRequest;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getChargingAmount() {
        return chargingAmount;
    }

    public void setChargingAmount(String chargingAmount) {
        this.chargingAmount = chargingAmount;
    }

    public String getBinaryHeader() {
        return binaryHeader;
    }

    public void setBinaryHeader(String binaryHeader) {
        this.binaryHeader = binaryHeader;
    }

    @Override
    public String toString() {
        return (applicationId == null ? "NULL" : applicationId) + ","
                + (password == null ? "NULL" : password) + ","
                + (version == null ? "NULL" : version) + ","
                + (destinationAddresses == null ? "NULL" : destinationAddresses) + ","
                + (message == null ? "NULL" : message) + ","
                + (sourceAddress == null ? "NULL" : sourceAddress) + ","
                + (deliveryStatusRequest == null ? "NULL" : deliveryStatusRequest) + ","
                + (encoding == null ? "NULL" : encoding) + ","
                + (chargingAmount == null ? "NULL" : chargingAmount) + ","
                + (binaryHeader == null ? "NULL" : binaryHeader);
    }
}
