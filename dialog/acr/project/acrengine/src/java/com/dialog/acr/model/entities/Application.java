package com.dialog.acr.model.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Hiranya
 */
@Entity
@Table(name="application")
public class Application implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="app_id")
    private int appId;
    
    @Column(name="app_code")
    private String appCode;
    
    @Column(name="app_name")
    private String appName;
    
    @Column(name="provider_app_id")
    private String serviceProviderAppId;
    
    @Column(name="app_description")
    private String appDescription;
    
    @Column(name="app_creater")
    private String creater;
    
    @Column(name="app_status")
    private int status;
    
    @ManyToOne
    @JoinColumn(name="provider_id", referencedColumnName="provider_id")
    private ServiceProvider provider;
    
    @OneToMany(mappedBy="application")
    private List<ACR> acrList;
    
    @OneToMany(mappedBy="application")
    private List<ApplicationKey> applicationKeyList;
    
    @OneToMany(mappedBy="currentApp")
    private List<ApplicationCoupling> appCouplingList;

    /**
     * @return the appId
     */
    public int getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(int appId) {
        this.appId = appId;
    }

    /**
     * @return the appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName the appName to set
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * @return the appDescription
     */
    public String getAppDescription() {
        return appDescription;
    }

    /**
     * @param appDescription the appDescription to set
     */
    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    /**
     * @return the creater
     */
    public String getCreater() {
        return creater;
    }

    /**
     * @param creater the creater to set
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the acrList
     */
    public List<ACR> getAcrList() {
        return acrList;
    }

    /**
     * @param acrList the acrList to set
     */
    public void setAcrList(List<ACR> acrList) {
        this.acrList = acrList;
    }

    /**
     * @return the applicationKeyList
     */
    public List<ApplicationKey> getApplicationKeyList() {
        return applicationKeyList;
    }

    /**
     * @param applicationKeyList the applicationKeyList to set
     */
    public void setApplicationKeyList(List<ApplicationKey> applicationKeyList) {
        this.applicationKeyList = applicationKeyList;
    }

    /**
     * @return the appCouplingList
     */
    public List<ApplicationCoupling> getAppCouplingList() {
        return appCouplingList;
    }

    /**
     * @param appCouplingList the appCouplingList to set
     */
    public void setAppCouplingList(List<ApplicationCoupling> appCouplingList) {
        this.appCouplingList = appCouplingList;
    }

    /**
     * @return the appCode
     */
    public String getAppCode() {
        return appCode;
    }

    /**
     * @param appCode the appCode to set
     */
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    /**
     * @return the serviceProviderAppId
     */
    public String getServiceProviderAppId() {
        return serviceProviderAppId;
    }

    /**
     * @param serviceProviderAppId the serviceProviderAppId to set
     */
    public void setServiceProviderAppId(String serviceProviderAppId) {
        this.serviceProviderAppId = serviceProviderAppId;
    }

    /**
     * @return the providerId
     */
    public ServiceProvider getProvider() {
        return provider;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProvider(ServiceProvider providerId) {
        this.provider = providerId;
    }

    
    
}
