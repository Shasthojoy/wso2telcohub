package lk.dialog.ideabiz.model.USSD;

/**
 * Created by Malinda on 7/20/2015.
 */
public class ResponseRequest {
    String notifyURL;
    String callbackData;

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

    public ResponseRequest() {
    }

    public ResponseRequest(String notifyURL, String callbackData) {
        this.notifyURL = notifyURL;
        this.callbackData = callbackData;
    }
}

