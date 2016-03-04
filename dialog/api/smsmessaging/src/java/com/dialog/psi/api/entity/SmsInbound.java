/*
 * SmsInbound.java
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <TO-DO> <code>SmsInbound</code>
 * @version $Id: SmsInbound.java,v 1.00.000
 */
@Entity
@Table(name = "sms_inbound")
@NamedQueries({
    @NamedQuery(name = "SmsInbound.findAll", query = "SELECT s FROM SmsInbound s")})
public class SmsInbound implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CREATED")
    private String created;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "DELIVERYTIME")
    private String deliverytime;
    @Column(name = "DESTADDR")
    private String destaddr;
    @Column(name = "ERROCODE")
    private Integer errocode;
    @Column(name = "LASTUPDATED")
    private String lastupdated;
    @Column(name = "LASTUPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdatedDate;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "MESSAGEID")
    private String messageid;
    @Column(name = "MSGENCODING")
    private String msgencoding;
    @Column(name = "MSGREF")
    private String msgref;
    @Column(name = "MSGSTATUS")
    private Integer msgstatus;
    @Column(name = "SENDERADDR")
    private String senderaddr;
    @Column(name = "APPID")
    private Integer appid;

    public SmsInbound() {
    }

    public SmsInbound(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getDestaddr() {
        return destaddr;
    }

    public void setDestaddr(String destaddr) {
        this.destaddr = destaddr;
    }

    public Integer getErrocode() {
        return errocode;
    }

    public void setErrocode(Integer errocode) {
        this.errocode = errocode;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getMsgencoding() {
        return msgencoding;
    }

    public void setMsgencoding(String msgencoding) {
        this.msgencoding = msgencoding;
    }

    public String getMsgref() {
        return msgref;
    }

    public void setMsgref(String msgref) {
        this.msgref = msgref;
    }

    public Integer getMsgstatus() {
        return msgstatus;
    }

    public void setMsgstatus(Integer msgstatus) {
        this.msgstatus = msgstatus;
    }

    public String getSenderaddr() {
        return senderaddr;
    }

    public void setSenderaddr(String senderaddr) {
        this.senderaddr = senderaddr;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
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
        if (!(object instanceof SmsInbound)) {
            return false;
        }
        SmsInbound other = (SmsInbound) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dialog.psi.api.entity.SmsInbound[ id=" + id + " ]";
    }

}
