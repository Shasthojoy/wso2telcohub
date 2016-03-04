/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.controller.response;

/**
 *
 * @author User
 */
public class ErrorResponse {
    public static final int SERVICEEXCEPTION = 1;
    public static final int POLICYEXCEPTION = 2;
    
    private PolicyException policyException = null;
    private ServiceException serviceException = null;
    
    public ErrorResponse(int type, String id, String text, String[] parameters){
        if(type == SERVICEEXCEPTION){
            serviceException = new ServiceException();
            serviceException.setMessageId(id);
            serviceException.setText(text);
            serviceException.setVariables(parameters);
        }
        else if (type == POLICYEXCEPTION){
            policyException = new PolicyException();
            policyException.setMessageId(id);
            policyException.setText(text);
            policyException.setVariables(parameters);
        }
    }
    
     public ErrorResponse(int type, String id, String text, String parameter){
        if(type == SERVICEEXCEPTION){
            serviceException = new ServiceException();
            serviceException.setMessageId(id);
            serviceException.setText(text);
            String[] parameters = null;
            if (parameter!=null) parameters=new String[] {parameter};
            serviceException.setVariables(parameters);
        }
        else if (type == POLICYEXCEPTION){
            policyException = new PolicyException();
            policyException.setMessageId(id);
            policyException.setText(text);
            String[] parameters = null;
            if (parameter!=null) parameters=new String[] {parameter};
            policyException.setVariables(parameters);
        }
    }
    
    public ErrorResponse(){}

    /**
     * @return the policyException
     */
    public PolicyException getPolicyException() {
        return policyException;
    }

    /**
     * @param policyException the policyException to set
     */
    public void setPolicyException(PolicyException policyException) {
        this.policyException = policyException;
    }

    /**
     * @return the serviceException
     */
    public ServiceException getServiceException() {
        return serviceException;
    }

    /**
     * @param serviceException the serviceException to set
     */
    public void setServiceException(ServiceException serviceException) {
        this.serviceException = serviceException;
    }
    
}
