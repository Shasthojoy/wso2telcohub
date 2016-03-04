package com.axiata.dialog.entity;


import java.util.List;

public class Provisioninfo {

    private String applicationid;

    public String getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(String applicationid) {
        this.applicationid = applicationid;
    }
    private List<String> merchantcode;

    public List<String> getMerchantcode() {
        return merchantcode;
    }

    public void setMerchantcode(List<String> merchantcode) {
        this.merchantcode = merchantcode;
    }
    private String subscriber;
    private String operatorcode;

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getOperatorcode() {
        return operatorcode;
    }

    public void setOperatorcode(String operatorcode) {
        this.operatorcode = operatorcode;
    }    
    
}