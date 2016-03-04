package com.axiata.dialog.mife.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.api.UserStoreException;


public class MSISDNValidator implements MifeValidator {
    private static final Log log = LogFactory.getLog(MSISDNValidator.class);

    @Override
    public boolean validate(MessageContext messageContext) {
        String userMSISDN = (String) messageContext.getProperty("UserMSISDN");
        return isExistingUser(userMSISDN);
    }

    private boolean isExistingUser(String userName) {
        boolean isUser = false;
        try {

            CarbonContext carbonContext = CarbonContext.getThreadLocalCarbonContext();
            UserRealm userRealm = carbonContext.getUserRealm();
            isUser = userRealm.getUserStoreManager().isExistingUser(userName);

        } catch (UserStoreException e) {
            String errorMsg = "Error while getting MSISDN of the user, " + e.getMessage();
            log.error(errorMsg, e);
        }
        return isUser;

    }
}
