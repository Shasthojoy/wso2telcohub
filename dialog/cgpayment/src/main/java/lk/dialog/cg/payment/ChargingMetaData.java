package lk.dialog.cg.payment;


public class ChargingMetaData {

    private String onBehalfOf;

    public String getOnBehalfOf() {
        return onBehalfOf;
    }

    public void setOnBehalfOf(String onBehalfOf) {
        this.onBehalfOf = onBehalfOf;
    }
    private String purchaseCategoryCode;

    public String getPurchaseCategoryCode() {
        return purchaseCategoryCode;
    }

    public void setPurchaseCategoryCode(String purchaseCategoryCode) {
        this.purchaseCategoryCode = purchaseCategoryCode;
    }
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
    private String taxAmount;

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }
    
    /*
    Adding serviceID
    */
    String serviceID;
    
      public String getServiceID() {
        return serviceID;
    }

    public void setServieID(String serviceID) {
        this.serviceID = serviceID;
    }
    
}