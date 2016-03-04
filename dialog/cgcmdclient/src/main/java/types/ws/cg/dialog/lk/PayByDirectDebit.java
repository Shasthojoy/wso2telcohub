
package types.ws.cg.dialog.lk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lk.dialog.cg.ws.messages.jaws.PayByDirectDebitRequest;


/**
 * <p>Java class for payByDirectDebit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="payByDirectDebit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PayByDirectDebitRequest_1" type="{http://messages.ws.cg.dialog.lk/jaws}PayByDirectDebitRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "payByDirectDebit", propOrder = {
    "payByDirectDebitRequest1"
})
public class PayByDirectDebit {

    @XmlElement(name = "PayByDirectDebitRequest_1", required = true, nillable = true)
    protected PayByDirectDebitRequest payByDirectDebitRequest1;

    /**
     * Gets the value of the payByDirectDebitRequest1 property.
     * 
     * @return
     *     possible object is
     *     {@link PayByDirectDebitRequest }
     *     
     */
    public PayByDirectDebitRequest getPayByDirectDebitRequest1() {
        return payByDirectDebitRequest1;
    }

    /**
     * Sets the value of the payByDirectDebitRequest1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayByDirectDebitRequest }
     *     
     */
    public void setPayByDirectDebitRequest1(PayByDirectDebitRequest value) {
        this.payByDirectDebitRequest1 = value;
    }

}
