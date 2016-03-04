package lk.dialog.ideabiz.model.sms;

/**
 * Created by Malinda on 7/13/2015.
 */
public class SMSMessagingRequestWrap {
    OutboundSMSMessageRequest outboundSMSMessageRequest;

    public SMSMessagingRequestWrap() {
    }

    public SMSMessagingRequestWrap(OutboundSMSMessageRequest outboundSMSMessageRequest) {
        this.outboundSMSMessageRequest = outboundSMSMessageRequest;
    }

    public OutboundSMSMessageRequest getOutboundSMSMessageRequest() {
        return outboundSMSMessageRequest;
    }

    public void setOutboundSMSMessageRequest(OutboundSMSMessageRequest outboundSMSMessageRequest) {
        this.outboundSMSMessageRequest = outboundSMSMessageRequest;
    }
}
