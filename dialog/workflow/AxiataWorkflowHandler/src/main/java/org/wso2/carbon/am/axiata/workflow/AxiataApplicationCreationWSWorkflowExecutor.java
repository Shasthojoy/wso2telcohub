package org.wso2.carbon.am.axiata.workflow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.wso2.carbon.apimgt.api.APIConsumer;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.Application;
import org.wso2.carbon.apimgt.api.model.Tier;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.APIManagerFactory;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;
import org.wso2.carbon.apimgt.impl.dto.ApplicationWorkflowDTO;
import org.wso2.carbon.apimgt.impl.dto.WorkflowDTO;
import org.wso2.carbon.am.axiata.workflow.internal.ServiceReferenceHolder;
import org.wso2.carbon.apimgt.impl.workflow.WorkflowConstants;
import org.wso2.carbon.apimgt.impl.workflow.WorkflowExecutor;
import org.wso2.carbon.apimgt.impl.workflow.WorkflowStatus;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.impl.workflow.WorkflowException;

public class AxiataApplicationCreationWSWorkflowExecutor extends WorkflowExecutor {
	
	private String serviceEndpoint;

	private String username;

	private String password;
	
	private String contentType;
	
	private static final Log log = LogFactory.getLog(AxiataApplicationCreationWSWorkflowExecutor.class);

	@Override
	public String getWorkflowType() {
		return WorkflowConstants.WF_TYPE_AM_APPLICATION_CREATION;
	}

	@Override
	public void execute(WorkflowDTO workflowDTO) throws WorkflowException {

		if (log.isDebugEnabled()) {
			log.info("Executing Axiata Application creation Workflow..");
		}
		super.execute(workflowDTO);
		try {
			ServiceClient client = new ServiceClient(ServiceReferenceHolder.getContextService()
			                                                               .getClientConfigContext(),
			                                         null);

			Options options = new Options();
            options.setAction("http://workflow.application.apimgt.carbon.wso2.org/initiate");
			options.setTo(new EndpointReference(serviceEndpoint));

			if (contentType != null) {
				options.setProperty(Constants.Configuration.MESSAGE_TYPE, contentType);
			} else {
				options.setProperty(Constants.Configuration.MESSAGE_TYPE,
				                    HTTPConstants.MEDIA_TYPE_APPLICATION_XML);
			}

			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();

			if (username != null && password != null) {
				auth.setUsername(username);
				auth.setPassword(password);
				auth.setPreemptiveAuthentication(true);
				List<String> authSchemes = new ArrayList<String>();
				authSchemes.add(HttpTransportProperties.Authenticator.BASIC);
				auth.setAuthSchemes(authSchemes);
				options.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE,
				                    auth);
				options.setManageSession(true);
			}

			client.setOptions(options);

			String payload =
			                 "<wor:ApplicationApprovalWorkFlowProcessRequest xmlns:wor=\"http://workflow.application.apimgt.carbon.wso2.org\">\n"
			                         + "        <wor:applicationName>$1</wor:applicationName>\n"			                         
			                         + "        <wor:applicationId>$2</wor:applicationId>\n"
			                         + "        <wor:applicationTier>$3</wor:applicationTier>\n"
			                         + "        <wor:applicationCallbackUrl>$4</wor:applicationCallbackUrl>\n"
			                         + "        <wor:applicationDescription>$5</wor:applicationDescription>\n"	
			                         + "        <wor:tenantDomain>$6</wor:tenantDomain>\n"	
			                         + "        <wor:userName>$7</wor:userName>\n"
			                         + "        <wor:workflowExternalRef>$8</wor:workflowExternalRef>\n"
			                         + "        <wor:callBackURL>$9</wor:callBackURL>\n"
			                         + "        <wor:tiers>$a</wor:tiers>\n"
			                         + "      </wor:ApplicationApprovalWorkFlowProcessRequest>";

			// Obtain application details.
			ApplicationWorkflowDTO appWorkFlowDTO = (ApplicationWorkflowDTO) workflowDTO;
			Application application = appWorkFlowDTO.getApplication();			
			String callBackURL = appWorkFlowDTO.getCallbackUrl();
			String appID = appWorkFlowDTO.getWorkflowReference();
			
			// Obtain list of tiers.
			APIConsumer consumer = APIManagerFactory.getInstance().getAPIConsumer();
			Set<Tier> tierSet = consumer.getTiers();
			
			String tiersStr = "";
            
            for (Iterator iterator = tierSet.iterator(); iterator.hasNext();) {
				Tier tier = (Tier) iterator.next();
				String tierName = tier.getName();
				String tierDisplayName = tier.getDisplayName();
				
				tiersStr = tiersStr + "         <wor:tier>\n" 
									+ "             <wor:tierName>$b</wor:tierName>\n"
									+ "             <wor:tierDisplayName>$c</wor:tierDisplayName>\n"
									+ "         </wor:tier>\n";
				
				tiersStr = tiersStr.replace("$b", tierName);
				tiersStr = tiersStr.replace("$c", tierDisplayName);
			}
			
			payload = payload.replace("$1", application.getName());	
			payload = payload.replace("$2", appID);
			payload = payload.replace("$3", application.getTier());
			payload = payload.replace("$4", application.getCallbackUrl());
			payload = payload.replace("$5", application.getDescription());
			payload = payload.replace("$6", appWorkFlowDTO.getTenantDomain());	
			payload = payload.replace("$7", appWorkFlowDTO.getUserName());
			payload = payload.replace("$8", appWorkFlowDTO.getExternalWorkflowReference());
			payload = payload.replace("$9", callBackURL != null ? callBackURL : "?");
			payload = payload.replace("$a", tiersStr);

			client.fireAndForget(AXIOMUtil.stringToOM(payload));	
			
		} catch (AxisFault axisFault) {
			log.error("Error sending out message", axisFault);
			throw new WorkflowException("Error sending out message", axisFault);
		} catch (XMLStreamException e) {
			log.error("Error converting String to OMElement", e);
			throw new WorkflowException("Error converting String to OMElement", e);
		} catch (APIManagementException e) {
			log.error("Error in obtaining APIConsumer", e);
			throw new WorkflowException("Error in obtaining APIConsumer", e);
		}

	}

	/**
	 * Complete the external process status.
	 * Based on the workflow , we will update the status column of the
	 * Application table
	 * 
	 * @param workFlowDTO
	 */
	@Override
	public void complete(WorkflowDTO workFlowDTO) throws WorkflowException {
        workFlowDTO.setUpdatedTime(System.currentTimeMillis());
		super.complete(workFlowDTO);
        log.info("Application Creation [Complete] Workflow Invoked. Workflow ID : " + workFlowDTO.getExternalWorkflowReference() + "Workflow State : "+ workFlowDTO.getStatus());

        if(WorkflowStatus.APPROVED.equals(workFlowDTO.getStatus())){
            String status = null;
            if ("CREATED".equals(workFlowDTO.getStatus().toString())) {
                status = APIConstants.ApplicationStatus.APPLICATION_CREATED;
            } else if ("REJECTED".equals(workFlowDTO.getStatus().toString())) {
                status = APIConstants.ApplicationStatus.APPLICATION_REJECTED;
            } else if ("APPROVED".equals(workFlowDTO.getStatus().toString())) {
                status = APIConstants.ApplicationStatus.APPLICATION_APPROVED;
            }

            ApiMgtDAO dao = new ApiMgtDAO();

            try {
                dao.updateApplicationStatus(Integer.parseInt(workFlowDTO.getWorkflowReference()),
                                            status);
            } catch (APIManagementException e) {
                String msg = "Error occured when updating the status of the Application creation " +
                        "process";
                log.error(msg, e);
                throw new WorkflowException(msg, e);
            }
        }
	}

	@Override
	public List<WorkflowDTO> getWorkflowDetails(String workflowStatus) throws WorkflowException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServiceEndpoint() {
		return serviceEndpoint;
	}

	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
