package com.dialog.acr.controller.response;

/**
 *
 * @author User
 */
public class DecodeACRResponse {
    private String appId;
    private String providerId;
    private String MSISDN;
    private String timestamp;

    /**
     * @return the appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return the providerId
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    /**
     * @return the MSISDN
     */
    public String getMSISDN() {
        return MSISDN;
    }

    /**
     * @param MSISDN the MSISDN to set
     */
    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
