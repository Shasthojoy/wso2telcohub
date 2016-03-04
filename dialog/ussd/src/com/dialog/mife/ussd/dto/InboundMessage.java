package com.dialog.mife.ussd.dto;

/**
 * @author Charith_02380
 *
 */
public class InboundMessage {

	private InboundUSSDMessageRequest inboundUSSDMessageRequest;
	
	public InboundMessage() {
	}

	/**
	 * @return the outboundUSSDMessageRequest
	 */
	public InboundUSSDMessageRequest getInboundUSSDMessageRequest() {
		return inboundUSSDMessageRequest;
	}

	/**
	 * @param outboundUSSDMessageRequest the outboundUSSDMessageRequest to set
	 */
	public void setInboundUSSDMessageRequest(
			InboundUSSDMessageRequest inboundUSSDMessageRequest) {
		this.inboundUSSDMessageRequest = inboundUSSDMessageRequest;
	}
}
