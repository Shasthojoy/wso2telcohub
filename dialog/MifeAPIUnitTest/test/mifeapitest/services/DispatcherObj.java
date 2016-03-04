/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mifeapitest.services;

/**
 *
 * @author User
 */
public class DispatcherObj {
    private String requestJson;
    private String responseJson;
    private int responseStatus;

    /**
     * @return the requestJson
     */
    public String getRequestJson() {
        return (requestJson == null) ? "NO CONTENT" : requestJson;
    }

    /**
     * @param requestJson the requestJson to set
     */
    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    /**
     * @return the responseJson
     */
    public String getResponseJson() {
        return (responseJson == null) ? "NO RESPONSE" : responseJson;
    }

    /**
     * @param responseJson the responseJson to set
     */
    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    /**
     * @return the responseStatus
     */
    public int getResponseStatus() {
        return responseStatus;
    }

    /**
     * @param responseStatus the responseStatus to set
     */
    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }
    
    
}
