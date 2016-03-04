/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mifeapitest.api.responsebeans;

/**
 *
 * @author User
 */
public class RetrieveSmsResponse {
    private ReceivingSMSResponse inboundSMSMessageList;

    /**
     * @return the inboundSMSMessageList
     */
    public ReceivingSMSResponse getInboundSMSMessageList() {
        return inboundSMSMessageList;
    }

    /**
     * @param inboundSMSMessageList the inboundSMSMessageList to set
     */
    public void setInboundSMSMessageList(ReceivingSMSResponse inboundSMSMessageList) {
        this.inboundSMSMessageList = inboundSMSMessageList;
    }
    
}
