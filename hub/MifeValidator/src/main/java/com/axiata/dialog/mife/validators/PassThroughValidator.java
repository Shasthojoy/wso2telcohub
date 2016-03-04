package com.axiata.dialog.mife.validators;

import org.apache.synapse.MessageContext;


public class PassThroughValidator implements MifeValidator {

    @Override
    public boolean validate(MessageContext messageContext) {
        return true;
    }
}
