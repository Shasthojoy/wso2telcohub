package com.dialog.acr.view.request;

import com.dialog.acr.controller.ACRServer;
import com.dialog.acr.controller.Getters;
import com.dialog.acr.controller.ValidationRule;
import com.dialog.acr.controller.functions.ACRController;
import com.dialog.acr.view.RequestHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author Hiranya
 */
public class ACRBatchDeleteServlet extends ACRServer {

    static Logger logger = Logger.getLogger(ACRDecodeServlet.class);

    public void init() throws ServletException {
        logger.debug("ACRBatchDeleteServlet initialised");
    }
    private final String[] validationRules = {"V1", "*"};

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dumpRequestDetails(request, logger);
        String[] requestParts = getRequestParts(request);
        logger.debug("Response JSON: " + "before validate");

        if (validateRequest(request, response, requestParts, validationRules)) {
            String developerCode = null;
            String msisdn = null;

            if (requestParts[1] != null) {
                developerCode = requestParts[1]; //refreshAcrRequest.get("serviceProviderID").toString();
            }

            JSONParser objJSONParser = new JSONParser();
            String requestString = RequestHandler.getRequestJSON(request);

            try {
                JSONObject refreshJsonObj = (JSONObject) objJSONParser.parse(requestString);
                JSONObject refreshAcrRequest = (JSONObject) refreshJsonObj.get("deleteAcrRequest");

                if (refreshAcrRequest.get("MSISDN") != null) {
                    msisdn = refreshAcrRequest.get("MSISDN").toString();
                }
            } catch (Exception e) {
                logger.debug("Manipulating request JSON: " + e);
            }

            ValidationRule[] rule = {
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "serviceProviderID", developerCode),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "MSISDN", msisdn),};

            int providerId = Getters.getServiceProviderIdByProviderCode(developerCode);

            if (checkRequestParameters(response, rule)) {
                try {
                    String responseJson = ACRController.deleteACRBatch(providerId, msisdn, developerCode);
                    String resourceURL = getRequestHostnameAndContext(request) + request.getServletPath() + "/V1/" + urlEncode(developerCode);
                    sendJSONResponse(response, responseJson, ACCEPTED, resourceURL);
                } catch (Exception e) {
                    logger.debug("ACRBatchDeleteServlet: " + e);
                }
            }
        }
    }
}
