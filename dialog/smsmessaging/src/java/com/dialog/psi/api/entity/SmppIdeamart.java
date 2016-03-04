/*
 * SmppIdeamart.java
 * Jun 4, 2013  1:47:11 PM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package com.dialog.psi.api.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * <TO-DO> <code>SmppIdeamart</code>
 * @version $Id: SmppIdeamart.java,v 1.00.000
 */
@Entity
@Table(name = "smpp_ideamart")
@NamedQueries({
    @NamedQuery(name = "SmppIdeamart.findAll", query = "SELECT s FROM SmppIdeamart s")})
public class SmppIdeamart implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "APP_ID")
    private String appId;
    @Column(name = "SENDER")
    private String sender;
    @Column(name = "OO_SENDER")
    private String ooSender;
    @Column(name = "APPNAME")
    private String appname;

    public SmppIdeamart() {
    }

    public SmppIdeamart(Integer id) {
        this.id = id;
    }

    public SmppIdeamart(Integer id, String appId) {
        this.id = id;
        this.appId = appId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmppIdeamart)) {
            return false;
        }
        SmppIdeamart other = (SmppIdeamart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dialog.psi.api.entity.SmppIdeamart[ id=" + id + " ]";
    }

}
