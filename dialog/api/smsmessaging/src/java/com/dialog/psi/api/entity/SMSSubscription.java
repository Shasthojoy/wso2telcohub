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

@Entity
@Table(name = "smpp_subscription")
@NamedQueries({ @NamedQuery(name = "SMSSubscription.findAll", query = "SELECT s FROM SMSSubscription s") })
public class SMSSubscription implements Serializable {

	private static final long serialVersionUID = -2473828871550879125L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "smsc_ref_number")
	private Integer smscRefNumber;
	
	@Column(name = "deliveryStatus")
	private String deliveryStatus;
	
	@Basic(optional = false)
	@Column(name = "address")
	private String resAddress;
	
	@Column(name = "callbackData")
	private String callbackData;
	
	@Basic(optional = false)
	@Column(name = "senderAddress")
	private String senderAddress;
	
	@Column(name = "clientCorrelator")
	private String clientCorrelator;

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

	@Column(name = "notifyUrl")
	private String notifyUrl;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSmscRefNumber() {
		return smscRefNumber;
	}

	public void setSmscRefNumber(Integer smscRefNumber) {
		this.smscRefNumber = smscRefNumber;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getResAddress() {
		return resAddress;
	}

	public void setResAddress(String resAddress) {
		this.resAddress = resAddress;
	}

	public String getCallbackData() {
		return callbackData;
	}

	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getClientCorrelator() {
		return clientCorrelator;
	}

	public void setClientCorrelator(String clientCorrelator) {
		this.clientCorrelator = clientCorrelator;
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

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	

}
