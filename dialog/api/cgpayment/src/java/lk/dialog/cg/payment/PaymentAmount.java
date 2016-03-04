package lk.dialog.cg.payment;


public class PaymentAmount {

    private ChargingInformation chargingInformation;

    public ChargingInformation getChargingInformation() {
        return chargingInformation;
    }

    public void setChargingInformation(ChargingInformation chargingInformation) {
        this.chargingInformation = chargingInformation;
    }
    private String totalAmountCharged;

    public String getTotalAmountCharged() {
        return totalAmountCharged;
    }
        
    public void setTotalAmountCharged(String totalAmountCharged) {
        this.totalAmountCharged = totalAmountCharged;
    }
    private ChargingMetaData chargingMetaData;

    public ChargingMetaData getChargingMetaData() {
        return chargingMetaData;
    }

    public void setChargingMetaData(ChargingMetaData chargingMetaData) {
        this.chargingMetaData = chargingMetaData;
    }
}