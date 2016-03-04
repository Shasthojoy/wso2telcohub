
package types.ws.cg.dialog.lk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lk.dialog.cg.ws.messages.jaws.CreditCheckResponse;


/**
 * <p>Java class for performCreditCheckResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="performCreditCheckResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{http://messages.ws.cg.dialog.lk/jaws}CreditCheckResponse"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "performCreditCheckResponse", propOrder = {
    "result"
})
public class PerformCreditCheckResponse {

    @XmlElement(required = true, nillable = true)
    protected CreditCheckResponse result;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCheckResponse }
     *     
     */
    public CreditCheckResponse getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCheckResponse }
     *     
     */
    public void setResult(CreditCheckResponse value) {
        this.result = value;
    }

}
