/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mifeapitest.api.requestbeans;

/**
 *
 * @author User
 */
public class ChargeRequest {
    private AmountTransaction amountTransaction;

    /**
     * @return the amountTransaction
     */
    public AmountTransaction getAmountTransaction() {
        return amountTransaction;
    }

    /**
     * @param amountTransaction the amountTransaction to set
     */
    public void setAmountTransaction(AmountTransaction amountTransaction) {
        this.amountTransaction = amountTransaction;
    }
    
}
