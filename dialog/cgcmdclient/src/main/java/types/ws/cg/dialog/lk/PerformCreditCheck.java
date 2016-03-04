
package types.ws.cg.dialog.lk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lk.dialog.cg.ws.messages.jaws.CreditCheckRequest;


/**
 * <p>Java class for performCreditCheck complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="performCreditCheck">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CreditCheckRequest_1" type="{http://messages.ws.cg.dialog.lk/jaws}CreditCheckRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "performCreditCheck", propOrder = {
    "creditCheckRequest1"
})
public class PerformCreditCheck {

    @XmlElement(name = "CreditCheckRequest_1", required = true, nillable = true)
    protected CreditCheckRequest creditCheckRequest1;

    /**
     * Gets the value of the creditCheckRequest1 property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCheckRequest }
     *     
     */
    public CreditCheckRequest getCreditCheckRequest1() {
        return creditCheckRequest1;
    }

    /**
     * Sets the value of the creditCheckRequest1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCheckRequest }
     *     
     */
    public void setCreditCheckRequest1(CreditCheckRequest value) {
        this.creditCheckRequest1 = value;
    }

}
