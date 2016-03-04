package org.dialog.custom.hostobjects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;
import org.wso2.carbon.apimgt.impl.dto.WorkflowDTO;
import org.dialog.custom.dao.AxiataWorkflowDAO;
import org.mozilla.javascript.*;

/**
 * Host object to hanled Axiata workflow related tasks. 
 */
public class WorkflowHostObject extends ScriptableObject {

	private static final Log log = LogFactory.getLog(WorkflowHostObject.class);
	private String hostobjectName = "AxiataWorkflow";
	private static String CONST_FAILURE = "FAILURE";
	private static String CONST_SUCCESS = "SUCCESS";

	@Override
	public String getClassName() {
		return hostobjectName;
	}

	public WorkflowHostObject() {
		log.info("::: Initialized HostObject ");
	}

	/**
	 * This method sets the specified tier for a given subscription.
	 * @param cx
	 * @param thisObj
	 * @param args
	 * @param funObj
	 * @return
	 * @throws APIManagementException
	 */
	public static String jsFunction_setSubscriptionTier(Context cx,
													Scriptable thisObj, Object[] args, Function funObj)
													throws APIManagementException {
		
		String status = "";
		
		if (args == null || args.length == 0) {
			handleException("Invalid number of parameters.");
		}
		
		String workflowReference = (String) args[0];
		String tierId = (String) args[1];
		
		if(workflowReference != null) {
			ApiMgtDAO apiMgtDAO = new ApiMgtDAO();
			WorkflowDTO workflowDTO = apiMgtDAO.retrieveWorkflow(workflowReference);
			
			if(workflowDTO != null) {
				
				String subscriptionId = workflowDTO.getWorkflowReference();
				AxiataWorkflowDAO axiataWorkflowDAO = new AxiataWorkflowDAO();
				axiataWorkflowDAO.updateSubscriptionTier(subscriptionId, tierId);
				
				status = CONST_SUCCESS;
				
			} else {
				status = CONST_FAILURE;
			}
		} else {
			status = CONST_FAILURE;
		}
		return status;
	}
	
	/**
	 * This method sets the specified tier for a given application.
	 * @param cx
	 * @param thisObj
	 * @param args
	 * @param funObj
	 * @return
	 * @throws APIManagementException
	 */
	public static String jsFunction_setApplicationTier(Context cx,
													Scriptable thisObj, Object[] args, Function funObj)
													throws APIManagementException {
		
		String status = "";
		
		if (args == null || args.length == 0) {
			handleException("Invalid number of parameters.");
		}
		
		String workflowReference = (String) args[0];
		String tierId = (String) args[1];
		
		if(workflowReference != null) {
			ApiMgtDAO apiMgtDAO = new ApiMgtDAO();
			WorkflowDTO workflowDTO = apiMgtDAO.retrieveWorkflow(workflowReference);
			
			if(workflowDTO != null) {
				
				String applicationId = workflowDTO.getWorkflowReference();
				AxiataWorkflowDAO axiataWorkflowDAO = new AxiataWorkflowDAO();
				axiataWorkflowDAO.updateApplicationTier(applicationId, tierId);
				
				status = CONST_SUCCESS;
				
			} else {
				status = CONST_FAILURE;
			}
		} else {
			status = CONST_FAILURE;
		}
		return status;
	}
	
	/**
	 * Handle expection.
	 * @param msg
	 * @throws APIManagementException
	 */
	private static void handleException(String msg) throws APIManagementException {
		log.error(msg);
		throw new APIManagementException(msg);
	}

	/**
	 * Handle expection.
	 * @param msg
	 * @param t
	 * @throws APIManagementException
	 */
	private static void handleException(String msg, Throwable t) throws APIManagementException {
		log.error(msg, t);
		throw new APIManagementException(msg, t);
	}
}
