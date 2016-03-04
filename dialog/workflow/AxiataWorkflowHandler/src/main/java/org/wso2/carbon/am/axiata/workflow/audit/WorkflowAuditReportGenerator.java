package org.wso2.carbon.am.axiata.workflow.audit;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.wso2.carbon.am.axiata.workflow.model.ApplicationApprovalAuditRecord;
import org.wso2.carbon.am.axiata.workflow.model.SubscriptionApprovalAuditRecord;
import org.wso2.carbon.am.workflow.dao.WorkflowDAO;

public class WorkflowAuditReportGenerator extends AbstractMediator {

	private static Log log = LogFactory.getLog(WorkflowAuditReportGenerator.class);
	
	private static final String APP_APPROVAL_AUDIT_REQUEST = "APP_APPROVAL_AUDIT_REQUEST";
	private static final String SUB_APPROVAL_AUDIT_REQUEST = "SUB_APPROVAL_AUDIT_REQUEST";
	
	public boolean mediate(MessageContext context) {
		
		OMElement rootElement = context.getEnvelope().getBody().getFirstElement();
		String requestType = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","requestType")).getText();
		
		if(APP_APPROVAL_AUDIT_REQUEST.equals(requestType)) {
			
			String appName = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","appName")).getText();
			String appCreator = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","appCreator")).getText();
			String appStatus = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","appStatus")).getText();
			String appApprovalType = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","appApprovalType")).getText();
			String completedByRole = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","completedByRole")).getText();
			String completedByUser = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","completedByUser")).getText();
			String completedOn = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","completedOn")).getText();
			
			ApplicationApprovalAuditRecord record = new ApplicationApprovalAuditRecord();
			record.setAppName(appName);
			record.setAppCreator(appCreator);
			record.setAppStatus(appStatus);
			record.setAppApprovalType(appApprovalType);
			record.setCompletedByRole(completedByRole);
			record.setCompletedByUser(completedByUser);
			record.setCompletedOn(completedOn);
			
			try {
				WorkflowDAO.insertAppApprovalAuditRecord(record);
				
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			
			
		} else if(SUB_APPROVAL_AUDIT_REQUEST.equals(requestType)) {
			
			String apiProvider = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","apiProvider")).getText();
			String apiName = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","apiName")).getText();
			String apiVersion = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","apiVersion")).getText();
			String appId = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","appId")).getText();
			String subStatus = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","subStatus")).getText();
			String subApprovalType = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","subApprovalType")).getText();
			String completedByRole = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","completedByRole")).getText();
			String completedByUser = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","completedByUser")).getText();
			String completedOn = rootElement.getFirstChildWithName(new QName("http://org.wso2.carbon/axiata/workflow/audit","completedOn")).getText();
			
			SubscriptionApprovalAuditRecord record = new SubscriptionApprovalAuditRecord();
			record.setApiProvider(apiProvider);
			record.setApiName(apiName);
			record.setApiVersion(apiVersion);
			record.setAppId(new Integer(appId).intValue());
			record.setSubStatus(subStatus);
			record.setSubApprovalType(subApprovalType);
			record.setCompletedByRole(completedByRole);
			record.setCompletedByUser(completedByUser);
			
			try {
				WorkflowDAO.insertSubApprovalAuditRecord(record);
				
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
}
