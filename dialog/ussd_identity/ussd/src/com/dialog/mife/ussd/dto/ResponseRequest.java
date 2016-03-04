package com.dialog.mife.ussd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author Charith_02380
 *
 */
@Entity
@Table(name="RESPONSE_REQUEST")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(value={"id"})
public class ResponseRequest {

	/*"responseRequest": {
		"notifyURL":"http://application.example.com/notifications/DeliveryInfoNotification",
		"callbackData":"some-data-useful-to-the-requester"
	}*/
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="REQ_ID", unique=true, insertable=false, updatable=false)
	@JsonIgnore
	@XmlTransient
	private Long id;
	
	@Column(name="NOTIFY_URL")
	private String notifyURL;
	
	@Column(name="CALLBACK_DATA")
	private String callbackData;
	
	public ResponseRequest() {
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the notifyURL
	 */
	public String getNotifyURL() {
		return notifyURL;
	}
	/**
	 * @return the callbackData
	 */
	public String getCallbackData() {
		return callbackData;
	}
	/**
	 * @param notifyURL the notifyURL to set
	 */
	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}
	/**
	 * @param callbackData the callbackData to set
	 */
	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}
}
