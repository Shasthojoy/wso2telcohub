
package lk.dialog.cg.ws.messages.jaws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChargedToBillRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChargedToBillRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://messages.ws.cg.dialog.lk/jaws}Request">
 *       &lt;sequence>
 *         &lt;element name="accountRef" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="reasonCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="taxable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargedToBillRequest", propOrder = {
    "accountRef",
    "amount",
    "reasonCode",
    "taxable"
})
public class ChargedToBillRequest
    extends Request
{

    @XmlElement(required = true, nillable = true)
    protected String accountRef;
    protected double amount;
    @XmlElement(required = true, nillable = true)
    protected String reasonCode;
    protected boolean taxable;

    /**
     * Gets the value of the accountRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountRef() {
        return accountRef;
    }

    /**
     * Sets the value of the accountRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountRef(String value) {
        this.accountRef = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonCode(String value) {
        this.reasonCode = value;
    }

    /**
     * Gets the value of the taxable property.
     * 
     */
    public boolean isTaxable() {
        return taxable;
    }

    /**
     * Sets the value of the taxable property.
     * 
     */
    public void setTaxable(boolean value) {
        this.taxable = value;
    }

}
