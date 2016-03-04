
package types.ws.cg.dialog.lk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lk.dialog.cg.ws.messages.jaws.ChargedToBillRequest;


/**
 * <p>Java class for chargeToBill complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="chargeToBill">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChargedToBillRequest_1" type="{http://messages.ws.cg.dialog.lk/jaws}ChargedToBillRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chargeToBill", propOrder = {
    "chargedToBillRequest1"
})
public class ChargeToBill {

    @XmlElement(name = "ChargedToBillRequest_1", required = true, nillable = true)
    protected ChargedToBillRequest chargedToBillRequest1;

    /**
     * Gets the value of the chargedToBillRequest1 property.
     * 
     * @return
     *     possible object is
     *     {@link ChargedToBillRequest }
     *     
     */
    public ChargedToBillRequest getChargedToBillRequest1() {
        return chargedToBillRequest1;
    }

    /**
     * Sets the value of the chargedToBillRequest1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargedToBillRequest }
     *     
     */
    public void setChargedToBillRequest1(ChargedToBillRequest value) {
        this.chargedToBillRequest1 = value;
    }

}
