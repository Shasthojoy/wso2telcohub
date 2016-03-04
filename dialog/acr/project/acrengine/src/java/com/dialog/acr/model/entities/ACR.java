/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author User
 */
@Entity
@Table(name="acr")
public class ACR implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="acr_id")
    private int acrId;
    
    //@Column(name="app_id")
    @ManyToOne
    @JoinColumn(name="appId", referencedColumnName="app_id")
    private Application application;
    
    private String msisdn;
    private String acr;
    private String expire_period;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="created_date")
    private Date createdDate;
    
    @Column(name="acr_version")
    private String version;
    
    @Column(name="acr_status")
    private int status;

    /**
     * @return the acrId
     */
    public int getAcrId() {
        return acrId;
    }

    /**
     * @param acrId the acrId to set
     */
    public void setAcrId(int acrId) {
        this.acrId = acrId;
    }

    /**
     * @return the msisdn
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * @param msisdn the msisdn to set
     */
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
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
     * @return the application
     */
    public Application getApplication() {
        return application;
    }

    /**
     * @param application the application to set
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * @return the acr
     */
    public String getAcr() {
        return acr;
    }

    /**
     * @param acr the acr to set
     */
    public void setAcr(String acr) {
        this.acr = acr;
    }

    /**
     * @return the expire_period
     */
    public String getExpire_period() {
        return expire_period;
    }

    /**
     * @param expire_period the expire_period to set
     */
    public void setExpire_period(String expire_period) {
        this.expire_period = expire_period;
    }
    
    
}
