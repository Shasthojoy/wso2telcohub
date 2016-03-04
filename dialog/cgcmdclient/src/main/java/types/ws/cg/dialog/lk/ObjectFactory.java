
package types.ws.cg.dialog.lk;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the types.ws.cg.dialog.lk package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ChargeBySpecialDebitResponse_QNAME = new QName("urn:lk.dialog.cg.ws.types", "chargeBySpecialDebitResponse");
    private final static QName _PayByCard_QNAME = new QName("urn:lk.dialog.cg.ws.types", "payByCard");
    private final static QName _ChargeToBillResponse_QNAME = new QName("urn:lk.dialog.cg.ws.types", "chargeToBillResponse");
    private final static QName _PayByDirectDebitResponse_QNAME = new QName("urn:lk.dialog.cg.ws.types", "payByDirectDebitResponse");
    private final static QName _ChargeBySpecialDebit_QNAME = new QName("urn:lk.dialog.cg.ws.types", "chargeBySpecialDebit");
    private final static QName _PayByCashResponse_QNAME = new QName("urn:lk.dialog.cg.ws.types", "payByCashResponse");
    private final static QName _PayByDirectDebit_QNAME = new QName("urn:lk.dialog.cg.ws.types", "payByDirectDebit");
    private final static QName _PerformCreditCheck_QNAME = new QName("urn:lk.dialog.cg.ws.types", "performCreditCheck");
    private final static QName _GetSessionKey_QNAME = new QName("urn:lk.dialog.cg.ws.types", "getSessionKey");
    private final static QName _PerformCreditCheckResponse_QNAME = new QName("urn:lk.dialog.cg.ws.types", "performCreditCheckResponse");
    private final static QName _PayByCardResponse_QNAME = new QName("urn:lk.dialog.cg.ws.types", "payByCardResponse");
    private final static QName _ChargeToBill_QNAME = new QName("urn:lk.dialog.cg.ws.types", "chargeToBill");
    private final static QName _PayByCash_QNAME = new QName("urn:lk.dialog.cg.ws.types", "payByCash");
    private final static QName _GetSessionKeyResponse_QNAME = new QName("urn:lk.dialog.cg.ws.types", "getSessionKeyResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: types.ws.cg.dialog.lk
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetSessionKeyResponse }
     * 
     */
    public GetSessionKeyResponse createGetSessionKeyResponse() {
        return new GetSessionKeyResponse();
    }

    /**
     * Create an instance of {@link GetSessionKey }
     * 
     */
    public GetSessionKey createGetSessionKey() {
        return new GetSessionKey();
    }

    /**
     * Create an instance of {@link PayByCash }
     * 
     */
    public PayByCash createPayByCash() {
        return new PayByCash();
    }

    /**
     * Create an instance of {@link PayByCardResponse }
     * 
     */
    public PayByCardResponse createPayByCardResponse() {
        return new PayByCardResponse();
    }

    /**
     * Create an instance of {@link ChargeBySpecialDebit }
     * 
     */
    public ChargeBySpecialDebit createChargeBySpecialDebit() {
        return new ChargeBySpecialDebit();
    }

    /**
     * Create an instance of {@link ChargeToBill }
     * 
     */
    public ChargeToBill createChargeToBill() {
        return new ChargeToBill();
    }

    /**
     * Create an instance of {@link PerformCreditCheckResponse }
     * 
     */
    public PerformCreditCheckResponse createPerformCreditCheckResponse() {
        return new PerformCreditCheckResponse();
    }

    /**
     * Create an instance of {@link ChargeToBillResponse }
     * 
     */
    public ChargeToBillResponse createChargeToBillResponse() {
        return new ChargeToBillResponse();
    }

    /**
     * Create an instance of {@link PayByDirectDebit }
     * 
     */
    public PayByDirectDebit createPayByDirectDebit() {
        return new PayByDirectDebit();
    }

    /**
     * Create an instance of {@link PerformCreditCheck }
     * 
     */
    public PerformCreditCheck createPerformCreditCheck() {
        return new PerformCreditCheck();
    }

    /**
     * Create an instance of {@link PayByDirectDebitResponse }
     * 
     */
    public PayByDirectDebitResponse createPayByDirectDebitResponse() {
        return new PayByDirectDebitResponse();
    }

    /**
     * Create an instance of {@link PayByCard }
     * 
     */
    public PayByCard createPayByCard() {
        return new PayByCard();
    }

    /**
     * Create an instance of {@link ChargeBySpecialDebitResponse }
     * 
     */
    public ChargeBySpecialDebitResponse createChargeBySpecialDebitResponse() {
        return new ChargeBySpecialDebitResponse();
    }

    /**
     * Create an instance of {@link PayByCashResponse }
     * 
     */
    public PayByCashResponse createPayByCashResponse() {
        return new PayByCashResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChargeBySpecialDebitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "chargeBySpecialDebitResponse")
    public JAXBElement<ChargeBySpecialDebitResponse> createChargeBySpecialDebitResponse(ChargeBySpecialDebitResponse value) {
        return new JAXBElement<ChargeBySpecialDebitResponse>(_ChargeBySpecialDebitResponse_QNAME, ChargeBySpecialDebitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PayByCard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "payByCard")
    public JAXBElement<PayByCard> createPayByCard(PayByCard value) {
        return new JAXBElement<PayByCard>(_PayByCard_QNAME, PayByCard.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChargeToBillResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "chargeToBillResponse")
    public JAXBElement<ChargeToBillResponse> createChargeToBillResponse(ChargeToBillResponse value) {
        return new JAXBElement<ChargeToBillResponse>(_ChargeToBillResponse_QNAME, ChargeToBillResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PayByDirectDebitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "payByDirectDebitResponse")
    public JAXBElement<PayByDirectDebitResponse> createPayByDirectDebitResponse(PayByDirectDebitResponse value) {
        return new JAXBElement<PayByDirectDebitResponse>(_PayByDirectDebitResponse_QNAME, PayByDirectDebitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChargeBySpecialDebit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "chargeBySpecialDebit")
    public JAXBElement<ChargeBySpecialDebit> createChargeBySpecialDebit(ChargeBySpecialDebit value) {
        return new JAXBElement<ChargeBySpecialDebit>(_ChargeBySpecialDebit_QNAME, ChargeBySpecialDebit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PayByCashResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "payByCashResponse")
    public JAXBElement<PayByCashResponse> createPayByCashResponse(PayByCashResponse value) {
        return new JAXBElement<PayByCashResponse>(_PayByCashResponse_QNAME, PayByCashResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PayByDirectDebit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "payByDirectDebit")
    public JAXBElement<PayByDirectDebit> createPayByDirectDebit(PayByDirectDebit value) {
        return new JAXBElement<PayByDirectDebit>(_PayByDirectDebit_QNAME, PayByDirectDebit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PerformCreditCheck }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "performCreditCheck")
    public JAXBElement<PerformCreditCheck> createPerformCreditCheck(PerformCreditCheck value) {
        return new JAXBElement<PerformCreditCheck>(_PerformCreditCheck_QNAME, PerformCreditCheck.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSessionKey }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "getSessionKey")
    public JAXBElement<GetSessionKey> createGetSessionKey(GetSessionKey value) {
        return new JAXBElement<GetSessionKey>(_GetSessionKey_QNAME, GetSessionKey.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PerformCreditCheckResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "performCreditCheckResponse")
    public JAXBElement<PerformCreditCheckResponse> createPerformCreditCheckResponse(PerformCreditCheckResponse value) {
        return new JAXBElement<PerformCreditCheckResponse>(_PerformCreditCheckResponse_QNAME, PerformCreditCheckResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PayByCardResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "payByCardResponse")
    public JAXBElement<PayByCardResponse> createPayByCardResponse(PayByCardResponse value) {
        return new JAXBElement<PayByCardResponse>(_PayByCardResponse_QNAME, PayByCardResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChargeToBill }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "chargeToBill")
    public JAXBElement<ChargeToBill> createChargeToBill(ChargeToBill value) {
        return new JAXBElement<ChargeToBill>(_ChargeToBill_QNAME, ChargeToBill.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PayByCash }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "payByCash")
    public JAXBElement<PayByCash> createPayByCash(PayByCash value) {
        return new JAXBElement<PayByCash>(_PayByCash_QNAME, PayByCash.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSessionKeyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:lk.dialog.cg.ws.types", name = "getSessionKeyResponse")
    public JAXBElement<GetSessionKeyResponse> createGetSessionKeyResponse(GetSessionKeyResponse value) {
        return new JAXBElement<GetSessionKeyResponse>(_GetSessionKeyResponse_QNAME, GetSessionKeyResponse.class, null, value);
    }

}
