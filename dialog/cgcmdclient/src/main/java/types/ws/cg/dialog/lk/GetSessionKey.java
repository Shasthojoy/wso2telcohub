
package types.ws.cg.dialog.lk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSessionKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSessionKey">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="String_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="arrayOfbyte_2" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSessionKey", propOrder = {
    "string1",
    "arrayOfbyte2"
})
public class GetSessionKey {

    @XmlElement(name = "String_1", required = true, nillable = true)
    protected String string1;
    @XmlElement(name = "arrayOfbyte_2", required = true, nillable = true)
    protected byte[] arrayOfbyte2;

    /**
     * Gets the value of the string1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getString1() {
        return string1;
    }

    /**
     * Sets the value of the string1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setString1(String value) {
        this.string1 = value;
    }

    /**
     * Gets the value of the arrayOfbyte2 property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getArrayOfbyte2() {
        return arrayOfbyte2;
    }

    /**
     * Sets the value of the arrayOfbyte2 property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setArrayOfbyte2(byte[] value) {
        this.arrayOfbyte2 = ((byte[]) value);
    }

}
