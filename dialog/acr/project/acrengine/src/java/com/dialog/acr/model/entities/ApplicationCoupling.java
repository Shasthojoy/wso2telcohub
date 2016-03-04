package com.dialog.acr.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name="application_couple")
public class ApplicationCoupling implements Serializable {
    
    @Id
    @Column(name="couple_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    
    //@Column(name="current_app_id")
    @ManyToOne
    @JoinColumn(name="appId", referencedColumnName="app_id")
    private Application currentApp;
    
    @Column(name="coupling_app_id")
    private Application coupleAppId;
    
    @Column(name="couple_status")
    private int status;

    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the currentApp
     */
    public Application getCurrentApp() {
        return currentApp;
    }

    /**
     * @param currentApp the currentApp to set
     */
    public void setCurrentApp(Application currentApp) {
        this.currentApp = currentApp;
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
     * @return the coupleAppId
     */
    public Application getCoupleAppId() {
        return coupleAppId;
    }

    /**
     * @param coupleAppId the coupleAppId to set
     */
    public void setCoupleAppId(Application coupleAppId) {
        this.coupleAppId = coupleAppId;
    }
    
}
