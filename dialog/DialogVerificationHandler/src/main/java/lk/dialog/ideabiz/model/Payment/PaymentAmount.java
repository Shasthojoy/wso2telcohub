package lk.dialog.ideabiz.model.Payment;

/**
 * Created by Malinda on 8/5/2015.
 */
public class PaymentAmount {

    Double amount;
    ChargingInformation chargingInformation;
    ChargingMetaData chargingMetaData;
    String transactionOperationStatus;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setAmount(String amount) {

        try {
            this.amount = Double.parseDouble(amount);
        } catch (Exception e) {
        }
    }

    public ChargingInformation getChargingInformation() {
        return chargingInformation;
    }

    public void setChargingInformation(ChargingInformation chargingInformation) {
        this.chargingInformation = chargingInformation;
    }

    public ChargingMetaData getChargingMetaData() {
        return chargingMetaData;
    }

    public void setChargingMetaData(ChargingMetaData chargingMetaData) {
        this.chargingMetaData = chargingMetaData;
    }

    public String getTransactionOperationStatus() {
        return transactionOperationStatus;
    }

    public void setTransactionOperationStatus(String transactionOperationStatus) {
        this.transactionOperationStatus = transactionOperationStatus;
    }
}
