package lk.dialog.ideabiz.model.sms;

/**
 * Created by Malinda on 7/13/2015.
 */
public class ReceiptRequest {
    String notifyURL;
    String callbackData;

    public ReceiptRequest(String notifyURL, String callbackData) {
        this.notifyURL = notifyURL;
        this.callbackData = callbackData;
    }

    public ReceiptRequest() {
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }
}
