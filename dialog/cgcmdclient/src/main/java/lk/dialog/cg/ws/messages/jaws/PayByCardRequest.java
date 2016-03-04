
package lk.dialog.cg.ws.messages.jaws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayByCardRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayByCardRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://messages.ws.cg.dialog.lk/jaws}Request">
 *       &lt;sequence>
 *         &lt;element name="accountRef" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="cardAgent" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cardNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cardType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reasonCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayByCardRequest", propOrder = {
    "accountRef",
    "amount",
    "cardAgent",
    "cardNo",
    "cardType",
    "reasonCode"
})
public class PayByCardRequest
    extends Request
{

    @XmlElement(required = true, nillable = true)
    protected String accountRef;
    protected double amount;
    @XmlElement(required = true, nillable = true)
    protected String cardAgent;
    @XmlElement(required = true, nillable = true)
    protected String cardNo;
    @XmlElement(required = true, nillable = true)
    protected String cardType;
    @XmlElement(required = true, nillable = true)
    protected String reasonCode;

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
     * Gets the value of the cardAgent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardAgent() {
        return cardAgent;
    }

    /**
     * Sets the value of the cardAgent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardAgent(String value) {
        this.cardAgent = value;
    }

    /**
     * Gets the value of the cardNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * Sets the value of the cardNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNo(String value) {
        this.cardNo = value;
    }

    /**
     * Gets the value of the cardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Sets the value of the cardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardType(String value) {
        this.cardType = value;
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

}
