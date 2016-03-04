/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.view.request;

import com.dialog.acr.controller.ACRServer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
@WebServlet(name = "DeactivateAPPACRServlet", urlPatterns = {"/DeactivateAPPACRService"})
public class DeactivateAPPACRServlet extends ACRServer {
    
    static Logger logger = Logger.getLogger(DeactivateAPPACRServlet.class);

    public void init() throws ServletException {
        logger.debug("DeactivateAPPACRServlet initialised");
    }
    private final String[] validationRules = {"DeactivateAPPACRService", "V1", "*"};

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        dumpRequestDetails(request, logger);
        String[] requestParts = getRequestParts(request);
        logger.debug("Response JSON: " + "before validate");

        if (validateRequest(request, response, requestParts, validationRules)) {
            
        }
    }
}
