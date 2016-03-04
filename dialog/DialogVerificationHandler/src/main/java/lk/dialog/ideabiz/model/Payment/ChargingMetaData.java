package lk.dialog.ideabiz.model.Payment;

/**
 * Created by Malinda on 8/5/2015.
 */
public class ChargingMetaData {
    String onBehalfOf;
    String channel;
    Double taxAmount;
    String purchaseCategoryCode;
    String serviceID;

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setTaxAmount(String taxAmount) {

        try {
            this.taxAmount = Double.parseDouble(taxAmount);
        } catch (Exception e) {
        }
    }

    public String getOnBehalfOf() {
        return onBehalfOf;
    }

    public void setOnBehalfOf(String onBehalfOf) {
        this.onBehalfOf = onBehalfOf;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPurchaseCategoryCode() {
        return purchaseCategoryCode;
    }

    public void setPurchaseCategoryCode(String purchaseCategoryCode) {
        this.purchaseCategoryCode = purchaseCategoryCode;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
}
