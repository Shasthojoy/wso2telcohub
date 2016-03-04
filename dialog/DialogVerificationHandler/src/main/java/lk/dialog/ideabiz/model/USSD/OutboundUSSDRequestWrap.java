package lk.dialog.ideabiz.model.USSD;

/**
 * Created by Malinda on 7/20/2015.
 */
public class OutboundUSSDRequestWrap {
    OutboundUSSDMessageRequest outboundUSSDMessageRequest;

    public OutboundUSSDRequestWrap(OutboundUSSDMessageRequest outboundUSSDMessageRequest) {
        this.outboundUSSDMessageRequest = outboundUSSDMessageRequest;
    }

    public OutboundUSSDRequestWrap() {
    }

    public OutboundUSSDMessageRequest getOutboundUSSDMessageRequest() {
        return outboundUSSDMessageRequest;
    }

    public void setOutboundUSSDMessageRequest(OutboundUSSDMessageRequest outboundUSSDMessageRequest) {
        this.outboundUSSDMessageRequest = outboundUSSDMessageRequest;
    }
}
