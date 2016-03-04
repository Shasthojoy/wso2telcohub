/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.mife.ussd.dto;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author dialog
 */
@Entity
@Table(name="nimsisdn")
public class NIMsisdn {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", unique=true, insertable=false, updatable=false)
    private Long id; 
    
    
    @Basic(optional = false)
    @Column(name = "MSISDN")
    private String msisdn;
    
    public NIMsisdn() {
	}
	
    /**
     * @return the id
     */
    public Long getId() {
            return id;
    }

    /**
     * @return msisdn
     */
    public String getMSISDN() {
            return msisdn;
    }
    
    
    public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param msisdn 
	 */
    public void setMSISDN(String msisdn) {
		this.msisdn = msisdn;
    }
    
    
}
