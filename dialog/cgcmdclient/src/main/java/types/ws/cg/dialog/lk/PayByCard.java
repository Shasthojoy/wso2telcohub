
package types.ws.cg.dialog.lk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lk.dialog.cg.ws.messages.jaws.PayByCardRequest;


/**
 * <p>Java class for payByCard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="payByCard">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PayByCardRequest_1" type="{http://messages.ws.cg.dialog.lk/jaws}PayByCardRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "payByCard", propOrder = {
    "payByCardRequest1"
})
public class PayByCard {

    @XmlElement(name = "PayByCardRequest_1", required = true, nillable = true)
    protected PayByCardRequest payByCardRequest1;

    /**
     * Gets the value of the payByCardRequest1 property.
     * 
     * @return
     *     possible object is
     *     {@link PayByCardRequest }
     *     
     */
    public PayByCardRequest getPayByCardRequest1() {
        return payByCardRequest1;
    }

    /**
     * Sets the value of the payByCardRequest1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayByCardRequest }
     *     
     */
    public void setPayByCardRequest1(PayByCardRequest value) {
        this.payByCardRequest1 = value;
    }

}
