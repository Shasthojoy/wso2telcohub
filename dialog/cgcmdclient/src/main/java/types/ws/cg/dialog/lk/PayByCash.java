
package types.ws.cg.dialog.lk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lk.dialog.cg.ws.messages.jaws.PayByCashRequest;


/**
 * <p>Java class for payByCash complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="payByCash">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PayByCashRequest_1" type="{http://messages.ws.cg.dialog.lk/jaws}PayByCashRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "payByCash", propOrder = {
    "payByCashRequest1"
})
public class PayByCash {

    @XmlElement(name = "PayByCashRequest_1", required = true, nillable = true)
    protected PayByCashRequest payByCashRequest1;

    /**
     * Gets the value of the payByCashRequest1 property.
     * 
     * @return
     *     possible object is
     *     {@link PayByCashRequest }
     *     
     */
    public PayByCashRequest getPayByCashRequest1() {
        return payByCashRequest1;
    }

    /**
     * Sets the value of the payByCashRequest1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayByCashRequest }
     *     
     */
    public void setPayByCashRequest1(PayByCashRequest value) {
        this.payByCashRequest1 = value;
    }

}
