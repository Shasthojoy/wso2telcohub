package com.tatadocomo.ussd.api.responsebean.ussd;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Charith_02380
 *
 */
@Entity
@Table(name="OUTBOUND_REQUEST_BODY")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(value={"id"})
public class OutboundUSSDMessageRequest {

	/*{
		"outboundUSSDMessageRequest":{
			"address":"tel:+13500000991",
			"shortCode":"tel:1721",
			"keyword":"123",
			"outboundUSSDMessage":"Hello World",
			"clientCorrelator":"123456",
			"responseRequest": {
					"notifyURL":"http://application.example.com/notifications/DeliveryInfoNotification",
					"callbackData":"some-data-useful-to-the-requester"
			},
			"ussdAction":"mt-init"
		}
	}*/

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="REQ_ID", unique=true, insertable=false, updatable=false)
    	@JsonIgnore
	@XmlTransient
    private Long id; 

	@Column(name="SESSION_ID")
	private String sessionID;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="SHORTCODE")
	private String shortCode;
	
	@Column(name="KEYWORD")
	private String keyword;
	
	@Column(name="DELIVERY_STATUS")
	private String deliveryStatus;
	
	@Column(name="MESSAGE_TEXT")
	private String outboundUSSDMessage;
	
	@Column(name="CLIENT_CORRELATOR")
	private String clientCorrelator;
	
	@OneToOne( cascade = {CascadeType.ALL} )
	@JoinColumn(name="RESPONSE_REQUEST")
	private ResponseRequest responseRequest;
	
	@Column(name="USSD_ACTION")
	private USSDAction ussdAction;
	
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
	 * @return
	 */
	public String getSessionID() {
		return sessionID;
	}
	/**
	 * @param sessionID
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @return the outboundUSSDMessage
	 */
	public String getOutboundUSSDMessage() {
		return outboundUSSDMessage;
	}
	/**
	 * @return the clientCorrelator
	 */
	public String getClientCorrelator() {
		return clientCorrelator;
	}
	/**
	 * @return the responseRequest
	 */
	public ResponseRequest getResponseRequest() {
		return responseRequest;
	}
	/**
	 * @return the ussdAction
	 */
	public USSDAction getUssdAction() {
		return ussdAction;
	}
	/**
	 * @return the shortCode
	 */
	public String getShortCode() {
		return shortCode;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @return the deliveryStatus
	 */
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	/**
	 * @param deliveryStatus the deliveryStatus to set
	 */
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	/**
	 * @param shortCode the shortCode to set
	 */
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @param ussdAction the ussdAction to set
	 */
	public void setUssdAction(USSDAction ussdAction) {
		this.ussdAction = ussdAction;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @param senderAddress the senderAddress to set
	 */
	public void setSenderAddress(String senderAddress) {
		this.shortCode = senderAddress;
	}
	/**
	 * @param outboundUSSDMessage the outboundUSSDMessage to set
	 */
	public void setOutboundUSSDMessage(String outboundUSSDMessage) {
		this.outboundUSSDMessage = outboundUSSDMessage;
	}
	/**
	 * @param clientCorrelator the clientCorrelator to set
	 */
	public void setClientCorrelator(String clientCorrelator) {
		this.clientCorrelator = clientCorrelator;
	}
	/**
	 * @param responseRequest the responseRequest to set
	 */
	public void setResponseRequest(ResponseRequest responseRequest) {
		this.responseRequest = responseRequest;
	}
}
