
package lk.dialog.cg.ws.messages.jaws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Request">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="appID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="domainID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="refAccount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transactionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Request", propOrder = {
    "appID",
    "authKey",
    "domainID",
    "refAccount",
    "transactionID"
})
@XmlSeeAlso({
    ChargeBySpecialDebitRequest.class,
    PayByDirectDebitRequest.class,
    PayByCashRequest.class,
    ChargedToBillRequest.class,
    CreditCheckRequest.class,
    PayByCardRequest.class
})
public class Request {

    @XmlElement(required = true, nillable = true)
    protected String appID;
    @XmlElement(required = true, nillable = true)
    protected String authKey;
    @XmlElement(required = true, nillable = true)
    protected String domainID;
    @XmlElement(required = true, nillable = true)
    protected String refAccount;
    @XmlElement(required = true, nillable = true)
    protected String transactionID;

    /**
     * Gets the value of the appID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppID() {
        return appID;
    }

    /**
     * Sets the value of the appID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppID(String value) {
        this.appID = value;
    }

    /**
     * Gets the value of the authKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthKey() {
        return authKey;
    }

    /**
     * Sets the value of the authKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthKey(String value) {
        this.authKey = value;
    }

    /**
     * Gets the value of the domainID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomainID() {
        return domainID;
    }

    /**
     * Sets the value of the domainID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomainID(String value) {
        this.domainID = value;
    }

    /**
     * Gets the value of the refAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefAccount() {
        return refAccount;
    }

    /**
     * Sets the value of the refAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefAccount(String value) {
        this.refAccount = value;
    }

    /**
     * Gets the value of the transactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionID(String value) {
        this.transactionID = value;
    }

}
