package lk.dialog.ideabiz.model.USSD;

/**
 * Created by Malinda on 7/20/2015.
 */
public class OutboundUSSDMessageRequest {

    String address;
    String shortCode;
    String keyword;
    String outboundUSSDMessage;
    String clientCorrelator;
    String ussdAction;
    ResponseRequest responseRequest;

    public OutboundUSSDMessageRequest() {
    }

    public OutboundUSSDMessageRequest(String outboundUSSDMessage, String address, String shortCode, String keyword) {
        this.outboundUSSDMessage = outboundUSSDMessage;
        this.address = address;
        this.shortCode = shortCode;
        this.keyword = keyword;
        responseRequest  = new ResponseRequest("","");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOutboundUSSDMessage() {
        return outboundUSSDMessage;
    }

    public void setOutboundUSSDMessage(String outboundUSSDMessage) {
        this.outboundUSSDMessage = outboundUSSDMessage;
    }

    public String getClientCorrelator() {
        return clientCorrelator;
    }

    public void setClientCorrelator(String clientCorrelator) {
        this.clientCorrelator = clientCorrelator;
    }

    public String getUssdAction() {
        return ussdAction;
    }

    public void setUssdAction(String ussdAction) {
        this.ussdAction = ussdAction;
    }

    public ResponseRequest getResponseRequest() {
        return responseRequest;
    }

    public void setResponseRequest(ResponseRequest responseRequest) {
        this.responseRequest = responseRequest;
    }
}
