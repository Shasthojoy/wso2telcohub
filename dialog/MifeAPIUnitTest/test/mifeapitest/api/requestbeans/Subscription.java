/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mifeapitest.api.requestbeans;

/**
 *
 * @author Shashika Wijayasekera
 */
public class Subscription {
    
    private String clientCorrelator;
    private String criteria;
    private String destinationAddress;
    private String notificationFormat;
    private CallbackReference callbackReference;

    /**
     * @return the clientCorrelator
     */
    public String getClientCorrelator() {
        return clientCorrelator;
    }

    /**
     * @param clientCorrelator the clientCorrelator to set
     */
    public void setClientCorrelator(String clientCorrelator) {
        this.clientCorrelator = clientCorrelator;
    }

    /**
     * @return the criteria
     */
    public String getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    /**
     * @return the destinationAddress
     */
    public String getDestinationAddress() {
        return destinationAddress;
    }

    /**
     * @param destinationAddress the destinationAddress to set
     */
    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    /**
     * @return the notificationFormat
     */
    public String getNotificationFormat() {
        return notificationFormat;
    }

    /**
     * @param notificationFormat the notificationFormat to set
     */
    public void setNotificationFormat(String notificationFormat) {
        this.notificationFormat = notificationFormat;
    }

    /**
     * @return the callbackReference
     */
    public CallbackReference getCallbackReference() {
        return callbackReference;
    }

    /**
     * @param callbackReference the callbackReference to set
     */
    public void setCallbackReference(CallbackReference callbackReference) {
        this.callbackReference = callbackReference;
    }
    
    public static class CallbackReference{
        
        private String notifyURL;
        private String callbackData;

        /**
         * @return the notifyURL
         */
        public String getNotifyURL() {
            return notifyURL;
        }

        /**
         * @param notifyURL the notifyURL to set
         */
        public void setNotifyURL(String notifyURL) {
            this.notifyURL = notifyURL;
        }

        /**
         * @return the callbackData
         */
        public String getCallbackData() {
            return callbackData;
        }

        /**
         * @param callbackData the callbackData to set
         */
        public void setCallbackData(String callbackData) {
            this.callbackData = callbackData;
        }
        
        
    }
}
