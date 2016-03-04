
package types.ws.cg.dialog.lk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for chargeBySpecialDebitResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="chargeBySpecialDebitResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{http://messages.ws.cg.dialog.lk/jaws}ChargeBySpecialDebitResponse"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chargeBySpecialDebitResponse", propOrder = {
    "result"
})
public class ChargeBySpecialDebitResponse {

    @XmlElement(required = true, nillable = true)
    protected lk.dialog.cg.ws.messages.jaws.ChargeBySpecialDebitResponse result;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link lk.dialog.cg.ws.messages.jaws.ChargeBySpecialDebitResponse }
     *     
     */
    public lk.dialog.cg.ws.messages.jaws.ChargeBySpecialDebitResponse getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link lk.dialog.cg.ws.messages.jaws.ChargeBySpecialDebitResponse }
     *     
     */
    public void setResult(lk.dialog.cg.ws.messages.jaws.ChargeBySpecialDebitResponse value) {
        this.result = value;
    }

}
