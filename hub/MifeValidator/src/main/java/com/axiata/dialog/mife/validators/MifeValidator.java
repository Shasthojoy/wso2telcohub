package com.axiata.dialog.mife.validators;

import org.apache.synapse.MessageContext;

public interface MifeValidator {

    public boolean validate(MessageContext messageContext);
}
