package lk.dialog.ideabiz.model.sms;

/**
 * Created by Malinda on 7/13/2015.
 */
public class OutboundSMSTextMessage {
    String message;

    public OutboundSMSTextMessage(String message) {
        this.message = message;
    }

    public OutboundSMSTextMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
