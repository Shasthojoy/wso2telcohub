/*
 * Application.java
 * May 14, 2013  10:22:54 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package com.dialog.psi.api.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <TO-DO> <code>Application</code>
 * @version $Id: Application.java,v 1.00.000
 */
@Entity
@Table(name = "smpp_application")
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a")})
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "REG_ID")
    private String regId;
    @Column(name = "SENDER")
    private String sender;
    @Column(name = "OO_SENDER")
    private String ooSender;
    @Column(name = "APPNAME")
    private String appname;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "DEVMOBILE")
    private String devmobile;
    @Basic(optional = false)
    @Column(name = "APP_ID")
    private String appId;
    @Column(name = "CALLBACKDATA")
    private String callbackdata;
    @Column(name = "CLIENTCORRELATOR")
    private String clientcorrelator;
    @Column(name = "CREATED")
    private String created;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "KEYWORD")
    private String keyword;
    @Column(name = "LASTUPDATED")
    private String lastupdated;
    @Column(name = "LASTUPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdatedDate;
    @Column(name = "MO_URL")
    private String moUrl;
    @Column(name = "NOTIFICATIONFORMAT")
    private String notificationformat;
    @Column(name = "UNIPORT")
    private String uniport;
    @Column(name = "notify")
    private String notify;
    @OneToMany(mappedBy = "regId")
    private Collection<SmppReceiptSubs> smppReceiptSubsCollection;
    

    public Application() {
    }

    public Application(Integer id) {
        this.id = id;
    }

    public Application(Integer id, String regId, String appId) {
        this.id = id;
        this.regId = regId;
        this.appId = appId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getOoSender() {
        return ooSender;
    }

    public void setOoSender(String ooSender) {
        this.ooSender = ooSender;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDevmobile() {
        return devmobile;
    }

    public void setDevmobile(String devmobile) {
        this.devmobile = devmobile;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCallbackdata() {
        return callbackdata;
    }

    public void setCallbackdata(String callbackdata) {
        this.callbackdata = callbackdata;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public String getMoUrl() {
        return moUrl;
    }

    public void setMoUrl(String moUrl) {
        this.moUrl = moUrl;
    }

    public String getNotificationformat() {
        return notificationformat;
    }

    public void setNotificationformat(String notificationformat) {
        this.notificationformat = notificationformat;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public Collection<SmppReceiptSubs> getSmppReceiptSubsCollection() {
        return smppReceiptSubsCollection;
    }

    public void setSmppReceiptSubsCollection(Collection<SmppReceiptSubs> smppReceiptSubsCollection) {
        this.smppReceiptSubsCollection = smppReceiptSubsCollection;
    }

    public String getUniport() {
        return uniport;
    }

    public void setUniport(String uniport) {
        this.uniport = uniport;
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
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dialog.psi.api.entity.Application[ id=" + id + " ]";
    }

}
