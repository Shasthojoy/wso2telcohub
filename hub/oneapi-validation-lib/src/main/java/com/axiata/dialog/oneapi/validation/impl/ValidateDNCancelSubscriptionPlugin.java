package com.axiata.dialog.oneapi.validation.impl;

import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.IServiceValidate;
import com.axiata.dialog.oneapi.validation.UrlValidator;
import com.axiata.dialog.oneapi.validation.ValidationNew;
import com.axiata.dialog.oneapi.validation.ValidationRule;

public class ValidateDNCancelSubscriptionPlugin implements IServiceValidate {

    private final String[] validationRules = {"outbound","*", "subscriptions", "*"};
    
    public void validate(String json) throws AxiataException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void validate(String[] params) throws AxiataException {
        
        String subId = nullOrTrimmed(params[0]);
        ValidationRule[] rules = {new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "subscriptionId", subId)};
        ValidationNew.checkRequestParams(rules);
    }

    private static String nullOrTrimmed(String s) {
        String rv = null;
        if (s != null && s.trim().length() > 0) {
            rv = s.trim();
        }
        return rv;
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
}
