
package types.ws.cg.dialog.lk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lk.dialog.cg.ws.messages.jaws.ChargeBySpecialDebitRequest;


/**
 * <p>Java class for chargeBySpecialDebit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="chargeBySpecialDebit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChargeBySpecialDebitRequest_1" type="{http://messages.ws.cg.dialog.lk/jaws}ChargeBySpecialDebitRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chargeBySpecialDebit", propOrder = {
    "chargeBySpecialDebitRequest1"
})
public class ChargeBySpecialDebit {

    @XmlElement(name = "ChargeBySpecialDebitRequest_1", required = true, nillable = true)
    protected ChargeBySpecialDebitRequest chargeBySpecialDebitRequest1;

    /**
     * Gets the value of the chargeBySpecialDebitRequest1 property.
     * 
     * @return
     *     possible object is
     *     {@link ChargeBySpecialDebitRequest }
     *     
     */
    public ChargeBySpecialDebitRequest getChargeBySpecialDebitRequest1() {
        return chargeBySpecialDebitRequest1;
    }

    /**
     * Sets the value of the chargeBySpecialDebitRequest1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargeBySpecialDebitRequest }
     *     
     */
    public void setChargeBySpecialDebitRequest1(ChargeBySpecialDebitRequest value) {
        this.chargeBySpecialDebitRequest1 = value;
    }

}
