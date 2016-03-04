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
@Table(name="acr_history")
public class ACRHistory implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="acr_history_id")
    private int acrHistryId;
    
    @ManyToOne
    @JoinColumn(name="acrId", referencedColumnName="acr_id")
    private ACR acrObj;
    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="effect_date")
    private Date Date;
    
    @Column(name="acr_status")
    private int acrStatus;
    
    @Column(name="acr_version")
    private String acrVersion;
    
    private String msisdn;
    private String acr;

    /**
     * @return the acrHistryId
     */
    public int getAcrHistryId() {
        return acrHistryId;
    }

    /**
     * @param acrHistryId the acrHistryId to set
     */
    public void setAcrHistryId(int acrHistryId) {
        this.acrHistryId = acrHistryId;
    }

    /**
     * @return the acrObj
     */
    public ACR getAcrObj() {
        return acrObj;
    }

    /**
     * @param acrObj the acrObj to set
     */
    public void setAcrObj(ACR acrObj) {
        this.acrObj = acrObj;
    }

    /**
     * @return the Date
     */
    public Date getDate() {
        return Date;
    }

    /**
     * @param Date the Date to set
     */
    public void setDate(Date Date) {
        this.Date = Date;
    }

    /**
     * @return the acrStatus
     */
    public int getAcrStatus() {
        return acrStatus;
    }

    /**
     * @param acrStatus the acrStatus to set
     */
    public void setAcrStatus(int acrStatus) {
        this.acrStatus = acrStatus;
    }

    /**
     * @return the acrVersion
     */
    public String getAcrVersion() {
        return acrVersion;
    }

    /**
     * @param acrVersion the acrVersion to set
     */
    public void setAcrVersion(String acrVersion) {
        this.acrVersion = acrVersion;
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
    
    
}
