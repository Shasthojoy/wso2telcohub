package lk.dialog.ideabiz.model.Payment;

/**
 * Created by Malinda on 8/5/2015.
 */
public class AmountTransaction {
    String clientCorrelator;
    String endUserId;
    PaymentAmount paymentAmount;
    String referenceCode;

    public String getClientCorrelator() {

        return clientCorrelator;
    }

    public void setClientCorrelator(String clientCorrelator) {
        this.clientCorrelator = clientCorrelator;
    }

    public String getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(String endUserId) {
        this.endUserId = endUserId;
    }

    public PaymentAmount getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(PaymentAmount paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
}
