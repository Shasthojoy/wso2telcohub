package com.dialog.airtel.ussd.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author Charith_02380
 *
 */
@Entity
@Table(name="OUTBOUND_REQUEST")
public class OutboundRequest {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="REQ_ID", unique=true, insertable=false, updatable=false)
    private Long id; 
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="APP_ID")
	private Application application;
	
	@OneToOne( cascade = {CascadeType.ALL} )
	@JoinColumn(name="MESSAGE")
	private OutboundUSSDMessageRequest outboundUSSDMessageRequest;
	
	public OutboundRequest() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * @return the outboundUSSDMessageRequest
	 */
	public OutboundUSSDMessageRequest getOutboundUSSDMessageRequest() {
		return outboundUSSDMessageRequest;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(Application application) {
		this.application = application;
	}

	/**
	 * @param outboundUSSDMessageRequest the outboundUSSDMessageRequest to set
	 */
	public void setOutboundUSSDMessageRequest(
			OutboundUSSDMessageRequest outboundUSSDMessageRequest) {
		this.outboundUSSDMessageRequest = outboundUSSDMessageRequest;
	}
}
