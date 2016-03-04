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
@Table(name="application_key")
public class ApplicationKey implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="key_id")
    private int keyId;
    
    //@Column(name="app_id")
    @ManyToOne
    @JoinColumn(name="appId", referencedColumnName="app_id")
    private Application application;
    
    @Column(name="app_key")
    private String appKey;
    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="created_date")
    private Date createdOn;
    
    private String duration;
    
    @Column(name="key_version")
    private String version;

    /**
     * @return the keyId
     */
    public int getKeyId() {
        return keyId;
    }

    /**
     * @param keyId the keyId to set
     */
    public void setKeyId(int keyId) {
        this.keyId = keyId;
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
     * @return the appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * @param appKey the appKey to set
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * @return the createdOn
     */
    public Date getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn the createdOn to set
     */
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
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

    
    
}
