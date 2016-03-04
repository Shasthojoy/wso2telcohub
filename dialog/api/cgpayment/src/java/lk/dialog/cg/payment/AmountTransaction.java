package lk.dialog.cg.payment;


public class AmountTransaction {

    private String clientCorrelator;

    public String getClientCorrelator() {
        return clientCorrelator;
    }

    public void setClientCorrelator(String clientCorrelator) {
        this.clientCorrelator = clientCorrelator;
    }
    private String endUserId;

    public String getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(String endUserId) {
        this.endUserId = endUserId;
    }
    private PaymentAmount paymentAmount;

    public PaymentAmount getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(PaymentAmount paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    private String referenceCode;

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
    private String transactionOperationStatus;

    public String getTransactionOperationStatus() {
        return transactionOperationStatus;
    }

    public void setTransactionOperationStatus(String transactionOperationStatus) {
        this.transactionOperationStatus = transactionOperationStatus;
    }
    
    private String serverReferenceCode;

    public String getServerReferenceCode() {
        return serverReferenceCode;
    }

    public void setServerReferenceCode(String serverReferenceCode) {
        this.serverReferenceCode = serverReferenceCode;
    }
    private String resourceURL;

    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }
    
}