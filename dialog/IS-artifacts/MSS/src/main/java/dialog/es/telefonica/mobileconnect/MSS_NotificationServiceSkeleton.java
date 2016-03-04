
/**
 * MSS_NotificationServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package dialog.es.telefonica.mobileconnect;

import com.axiata.dialog.dao.SignatureDao;
import com.axiata.dialog.dao.SignatureDaoImpl;
import com.axiata.dialog.model.MssSignatureLog;
import com.axiata.dialog.util.Config;
import dialog.org.etsi.uri.ts102204.v1_1_2.*;
import org.apache.axis2.databinding.types.NCName;
import org.apache.axis2.databinding.types.URI;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
     *  MSS_NotificationServiceSkeleton java skeleton for the axisService
     */
    public class MSS_NotificationServiceSkeleton implements MSS_NotificationServiceSkeletonInterface{
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param mSS_Notification0 
             * @return mSS_NotificationResponse1 
         */
        
                 public dialog.es.telefonica.mobileconnect.MSS_NotificationResponse mSS_Notification
                  (
                  dialog.es.telefonica.mobileconnect.MSS_Notification mSS_Notification0
                  )
            {
                try {
                    SignatureDao signatureDao=new SignatureDaoImpl();
                    MSS_NotificationResponse notificationResponse=new MSS_NotificationResponse();
                    String id = mSS_Notification0.getMSS_StatusResp().getAP_Info().getAP_TransID().toString();
                    String mssp = mSS_Notification0.getMSS_StatusResp().getMobileUser().getMSISDN();
                    String statusCode =mSS_Notification0.getMSS_StatusResp().getStatus().getStatusCode().getValue().toString();
                    String description = mSS_Notification0.getMSS_StatusResp().getStatus().getStatusMessage();


                    MSS_ReceiptReqType mss_receiptReqType=new MSS_ReceiptReqType();

                    mss_receiptReqType.setMajorVersion(new BigInteger("1"));
                    mss_receiptReqType.setMinorVersion(new BigInteger("1"));
                    AP_Info_type0 apInfo=new AP_Info_type0();
                    URI uri=new URI(Config.getInstance().getProperty("mss.signature.service.ap.id"));
                    URI appuri=new URI(Config.getInstance().getProperty("mss.signature.service.response.url"));
                    apInfo.setAP_ID(uri);
                    apInfo.setAP_PWD(Config.getInstance().getProperty("mss.signature.service.ap.pwd"));
                    apInfo.setAP_URL(appuri);
                    NCName nc=new NCName();
                    nc.setValue(id);

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, 1);
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                    Date validitidate = formatter.parse(formatter.format(cal.getTime()));
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(validitidate);
                    apInfo.setAP_TransID(nc);
                    apInfo.setInstant(calendar1);

                    mss_receiptReqType.setAP_Info(apInfo);
                    MSSP_Info_type0 mssp_info_type0=new MSSP_Info_type0();
                    MeshMemberType meshMemberType=new MeshMemberType();
                    mssp_info_type0.setInstant(calendar1);
                    URI uriMSSP=new URI(Config.getInstance().getProperty("mss.signature.service.mssp.info.type"));
                    meshMemberType.setURI(uriMSSP);
                    mssp_info_type0.setMSSP_ID(meshMemberType);
                    mss_receiptReqType.setMSSP_TransID(nc);
                    mss_receiptReqType.setMSSP_Info(mssp_info_type0);
                    StatusType statusType=new StatusType();
                    StatusCodeType statusCodeType=new StatusCodeType();
                    statusCodeType.setValue(mSS_Notification0.getMSS_StatusResp().getStatus().getStatusCode().getValue());
                    statusType.setStatusMessage(description);
                    statusType.setStatusCode(statusCodeType);
                    mss_receiptReqType.setStatus(statusType);



                    MobileUserType mobileUserType=new MobileUserType();
                    mobileUserType.setMSISDN(mssp);
                    mss_receiptReqType.setMobileUser(mobileUserType);
                    MssSignatureLog mssSignatureLog =new MssSignatureLog();

                    mssSignatureLog.setMsisdn(mssp);
                    BigInteger statusCodeBI=new BigInteger(statusCode);
                    mssSignatureLog.setRespStatus(statusCodeBI);
                    mssSignatureLog.setApTranceId(id);
                    mssSignatureLog.setRespDescription(description);
                    Calendar calendar = Calendar.getInstance();
                    mssSignatureLog.setRespTime(calendar.getTime());
                    notificationResponse.setMSS_ReceiptReq(mss_receiptReqType);

                    signatureDao.editSignatureLog(mssSignatureLog);

                    return notificationResponse;

                }catch(Exception ex) {

                    ex.printStackTrace();

                    throw new java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#mSS_Notification");

                }
        }
     
    }
    