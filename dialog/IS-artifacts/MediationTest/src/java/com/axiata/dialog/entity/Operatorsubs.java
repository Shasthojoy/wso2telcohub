/*
 * Operatorsubs.java
 * Sep 2, 2013  4:28:47 PM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package com.axiata.dialog.entity;

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
 * <TO-DO> <code>Operatorsubs</code>
 * @version $Id: Operatorsubs.java,v 1.00.000
 */
@Entity
@Table(name = "operatorsubs")
@NamedQueries({
    @NamedQuery(name = "Operatorsubs.findAll", query = "SELECT o FROM Operatorsubs o")})
public class Operatorsubs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "axiataid")
    private String axiataid;
    @Basic(optional = false)
    @Column(name = "subid")
    private String subid;
    @Column(name = "urldomain")
    private String urldomain;
    @Column(name = "created")
    private String created;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "lastupdated")
    private String lastupdated;
    @Column(name = "lastupdated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdatedDate;

    public Operatorsubs() {
    }

    public Operatorsubs(Integer id) {
        this.id = id;
    }

    public Operatorsubs(Integer id, String axiataid, String subid) {
        this.id = id;
        this.axiataid = axiataid;
        this.subid = subid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAxiataid() {
        return axiataid;
    }

    public void setAxiataid(String axiataid) {
        this.axiataid = axiataid;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getUrldomain() {
        return urldomain;
    }

    public void setUrldomain(String urldomain) {
        this.urldomain = urldomain;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operatorsubs)) {
            return false;
        }
        Operatorsubs other = (Operatorsubs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.axiata.dialog.entity.Operatorsubs[ id=" + id + " ]";
    }

}
