/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.view.request;

import com.dialog.acr.controller.ACRServer;
import com.dialog.acr.controller.ValidationRule;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class APPCoupleServlet extends ACRServer {
    static Logger logger = Logger.getLogger(APPCoupleServlet.class);

    public void init() throws ServletException {
        logger.debug("APPCoupleServlet initialised");
    }
    private final String[] validationRules = {"APPCoupleService", "V1", "*"};

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String main_app = request.getParameter("mainAppId");
        String coupling_app = request.getParameter("couplingAppId");

        dumpRequestDetails(request, logger);
        String[] requestParts = getRequestParts(request);
        logger.debug("Response JSON: " + "before validate");
        
        ValidationRule[] rule = {
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "mainAppId", main_app),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "couplingAppId", coupling_app),};

        if (validateRequest(request, response, requestParts, validationRules)) {
            
        }
    }
}
