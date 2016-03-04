/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mifeapitest.util;

import com.google.gson.Gson;
import mifeapitest.api.requestbeans.AmountTransaction;
import mifeapitest.api.requestbeans.ChargeRequest;
import mifeapitest.api.responsebeans.ChargeResponse;

/**
 *
 * @author User
 */
public class PaymentFunctions {

    public static String getPaymentResponse(ChargeRequest requestObj) {
        ChargeResponse cr = new ChargeResponse();
        cr.setClientCorrelator(requestObj.getAmountTransaction().getClientCorrelator());
        cr.setEndUserId(requestObj.getAmountTransaction().getEndUserId());
        
        AmountTransaction.PaymentAmount i = requestObj.getAmountTransaction().getPaymentAmount();

        ChargeResponse.PaymentAmount.ChargingInformation chargeInfo = new ChargeResponse.PaymentAmount.ChargingInformation();
        chargeInfo.setAmount(Double.parseDouble(i.getChargingInformation().getAmount()));
        chargeInfo.setCurrency(i.getChargingInformation().getCurrency());
        chargeInfo.setDescription(i.getChargingInformation().getDescription().toString());

        ChargeResponse.PaymentAmount.ChargingMetaData chargeMetaData = new ChargeResponse.PaymentAmount.ChargingMetaData();
        chargeMetaData.setChannel(i.getChargingMetaData().getChannel());
        chargeMetaData.setOnBehalfOf(i.getChargingMetaData().getOnBehalfOf());
        chargeMetaData.setPurchaseCategoryCode(i.getChargingMetaData().getPurchaseCategoryCode());
        chargeMetaData.setTaxAmount(Double.parseDouble(i.getChargingMetaData().getTaxAmount()));

        ChargeResponse.PaymentAmount payAmount = new ChargeResponse.PaymentAmount();
        payAmount.setTotalAmountCharged(1450.00);
        payAmount.setChargingInformation(chargeInfo);
        payAmount.setChargingMetaData(chargeMetaData);

        cr.setPaymentAmount(payAmount);

        cr.setReferenceCode(requestObj.getAmountTransaction().getReferenceCode());
        cr.setTransactionOperationStatus(requestObj.getAmountTransaction().getTransactionOperationStatus());

        Gson gson = new Gson();
        String jsonResponse = "{\"amountTransaction\":" + gson.toJson(cr) + "}";
        
        return jsonResponse;
    }
}
