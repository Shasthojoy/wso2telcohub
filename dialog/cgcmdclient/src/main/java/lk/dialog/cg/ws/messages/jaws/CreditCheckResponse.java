
package lk.dialog.cg.ws.messages.jaws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditCheckResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCheckResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://messages.ws.cg.dialog.lk/jaws}Response">
 *       &lt;sequence>
 *         &lt;element name="creditlimit" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="outStanding" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCheckResponse", propOrder = {
    "creditlimit",
    "outStanding"
})
public class CreditCheckResponse
    extends Response
{

    protected double creditlimit;
    protected double outStanding;

    /**
     * Gets the value of the creditlimit property.
     * 
     */
    public double getCreditlimit() {
        return creditlimit;
    }

    /**
     * Sets the value of the creditlimit property.
     * 
     */
    public void setCreditlimit(double value) {
        this.creditlimit = value;
    }

    /**
     * Gets the value of the outStanding property.
     * 
     */
    public double getOutStanding() {
        return outStanding;
    }

    /**
     * Sets the value of the outStanding property.
     * 
     */
    public void setOutStanding(double value) {
        this.outStanding = value;
    }

}
