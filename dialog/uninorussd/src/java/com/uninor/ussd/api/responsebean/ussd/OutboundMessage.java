package com.uninor.ussd.api.responsebean.ussd;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Charith_02380
 *
 */
@Entity
@Table(name="OUTBOUND_REQUEST")
public class OutboundMessage {

	private OutboundUSSDMessageRequest outboundUSSDMessageRequest;
	
	public OutboundMessage() {
	}

	/**
	 * @return the outboundUSSDMessageRequest
	 */
	@XmlElement
	public OutboundUSSDMessageRequest getOutboundUSSDMessageRequest() {
		return outboundUSSDMessageRequest;
	}

	/**
	 * @param outboundUSSDMessageRequest the outboundUSSDMessageRequest to set
	 */
	public void setOutboundUSSDMessageRequest(
			OutboundUSSDMessageRequest outboundUSSDMessageRequest) {
		this.outboundUSSDMessageRequest = outboundUSSDMessageRequest;
	}
}
