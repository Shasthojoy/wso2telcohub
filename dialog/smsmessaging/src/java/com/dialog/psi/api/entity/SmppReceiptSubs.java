/*
 * SmppReceiptSubs.java
 * May 14, 2013  10:22:55 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package com.dialog.psi.api.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <TO-DO> <code>SmppReceiptSubs</code>
 * @version $Id: SmppReceiptSubs.java,v 1.00.000
 */
@Entity
@Table(name = "smpp_receipt_subs")
@NamedQueries({
    @NamedQuery(name = "SmppReceiptSubs.findAll", query = "SELECT s FROM SmppReceiptSubs s")})
public class SmppReceiptSubs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CRITERIA")
    private String criteria;
    @Column(name = "NOTIFYURL")
    private String notifyurl;
    @Column(name = "CALLBACKDATA")
    private String callbackdata;
    @Column(name = "DESTADDRESS")
    private String destaddress;
    @Column(name = "NOTIFICATIONFORMAT")
    private String notificationformat;
    @Column(name = "CLIENTCORRELATOR")
    private String clientcorrelator;
    @Column(name = "CREATED")
    private String created;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "LASTUPDATED")
    private String lastupdated;
    @Column(name = "LASTUPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdatedDate;
    @JoinColumn(name = "REG_ID", referencedColumnName = "ID")
    @ManyToOne
    private Application regId;

    @Column(name = "AMID")
    private Integer AMID;

    public SmppReceiptSubs() {
    }

    public SmppReceiptSubs(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }

    public String getCallbackdata() {
        return callbackdata;
    }

    public void setCallbackdata(String callbackdata) {
        this.callbackdata = callbackdata;
    }

    public String getDestaddress() {
        return destaddress;
    }

    public void setDestaddress(String destaddress) {
        this.destaddress = destaddress;
    }

    public String getNotificationformat() {
        return notificationformat;
    }

    public void setNotificationformat(String notificationformat) {
        this.notificationformat = notificationformat;
    }

    public String getClientcorrelator() {
        return clientcorrelator;
    }

    public void setClientcorrelator(String clientcorrelator) {
        this.clientcorrelator = clientcorrelator;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public Date getLastupdatedDate() {
        return lastupdatedDate;
    }

    public void setLastupdatedDate(Date lastupdatedDate) {
        this.lastupdatedDate = lastupdatedDate;
    }

    public Application getRegId() {
        return regId;
    }

    public void setRegId(Application regId) {
        this.regId = regId;
    }

    public Integer getAMID() {
        return AMID;
    }

    public void setAMID(Integer AMID) {
        this.AMID = AMID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmppReceiptSubs)) {
            return false;
        }
        SmppReceiptSubs other = (SmppReceiptSubs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dialog.psi.api.entity.SmppReceiptSubs[ id=" + id + " ]";
    }

}
