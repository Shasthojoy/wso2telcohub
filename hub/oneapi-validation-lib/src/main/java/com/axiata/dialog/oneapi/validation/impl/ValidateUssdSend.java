/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.oneapi.validation.impl;

import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.IServiceValidate;
import com.axiata.dialog.oneapi.validation.UrlValidator;
import com.axiata.dialog.oneapi.validation.ValidationNew;
import com.axiata.dialog.oneapi.validation.ValidationRule;
import org.json.JSONObject;

/**
 *
 * @author User
 */
public class ValidateUssdSend implements IServiceValidate {

    private final String[] validationRules = {"ussd", "*", "outbound"};

    public void validate(String json) throws AxiataException {
        String address = null;
        String shortCode = null;
        String keyword = null;
        String message = null;
        String clientCorrelator = null;
        String notifyUrl = null;
        String callbackData = null;
        String ussdAction = null;

        try {
            JSONObject objJSONObject = new JSONObject(json);
            JSONObject requestData = objJSONObject.getJSONObject("outboundUSSDMessageRequest");

            if (requestData.has("address")) {
                address = nullOrTrimmed(requestData.getString("address"));
            }
            if (requestData.has("shortCode")) {
                shortCode = nullOrTrimmed(requestData.getString("shortCode"));
            }

            if (requestData.has("keyword")) {
                keyword = nullOrTrimmed(requestData.getString("keyword"));
            }
            if (requestData.has("outboundUSSDMessage")) {
                message = nullOrTrimmed(requestData.getString("outboundUSSDMessage"));
            }
            if (requestData.has("clientCorrelator")) {
                clientCorrelator = nullOrTrimmed(requestData.getString("clientCorrelator"));
            }
            if (requestData.has("ussdAction")) {
                ussdAction = nullOrTrimmed(requestData.getString("ussdAction"));
            }

            JSONObject responseRequest = requestData.getJSONObject("responseRequest");
            if (responseRequest.has("notifyURL")) {
                notifyUrl = nullOrTrimmed(responseRequest.getString("notifyURL"));
            }
            if (responseRequest.has("callbackData")) {
                callbackData = nullOrTrimmed(responseRequest.getString("callbackData"));
            }

        } catch (Exception e) {
            System.out.println("Manipulating recived JSON Object: " + e);
            throw new AxiataException("POL0299", "Unexpected Error", new String[]{""});
        }

        ValidationRule[] rules = null;

        rules = new ValidationRule[]{
            new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL_END_USER_ID, "address", address),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "shortCode", shortCode),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "keyword", keyword),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "outboundUSSDMessage", message),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "clientCorrelator", clientCorrelator),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "ussdAction", ussdAction),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "notifyURL", notifyUrl),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "callbackData", callbackData),};
        ValidationNew.checkRequestParams(rules);
    }

    public void validateUrl(String pathInfo) throws AxiataException {
        String[] requestParts = null;
        if (pathInfo != null) {
            if (pathInfo.startsWith("/")) {
                pathInfo = pathInfo.substring(1);
            }
            requestParts = pathInfo.split("/");
        }

        UrlValidator.validateRequest(requestParts, validationRules);

    }

    public void validate(String[] params) throws AxiataException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static String nullOrTrimmed(String s) {
        String rv = null;
        if (s != null && s.trim().length() > 0) {
            rv = s.trim();
        }
        return rv;
    }
}
