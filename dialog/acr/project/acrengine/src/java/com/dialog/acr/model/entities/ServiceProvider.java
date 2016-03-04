package com.dialog.acr.model.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Hiranya
 */
@Entity
@Table(name="serviceprovider")
public class ServiceProvider implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="provider_id")
    private int providerId;
    
    @Column(name="provider_code")
    private String providerCode;
    
    @Column(name="provider_name")
    private String providerName;
    
    @Column(name="security_key")
    private String securityKey;
    
    @OneToMany(mappedBy="provider")
    private List<Application> appList;

    /**
     * @return the providerId
     */
    public int getProviderId() {
        return providerId;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    /**
     * @return the providerName
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * @param providerName the providerName to set
     */
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    /**
     * @return the SecurityKey
     */
    public String getSecurityKey() {
        return securityKey;
    }

    /**
     * @param SecurityKey the SecurityKey to set
     */
    public void setSecurityKey(String SecurityKey) {
        this.securityKey = SecurityKey;
    }

    /**
     * @return the providerCode
     */
    public String getProviderCode() {
        return providerCode;
    }

    /**
     * @param providerCode the providerCode to set
     */
    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    /**
     * @return the appList
     */
    public List<Application> getAppList() {
        return appList;
    }

    /**
     * @param appList the appList to set
     */
    public void setAppList(List<Application> appList) {
        this.appList = appList;
    }
    
    
    
}
