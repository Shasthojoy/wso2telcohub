package com.axiata.dialog.mss;

import com.axiata.dialog.dao.SignatureDao;
import com.axiata.dialog.dao.SignatureDaoImpl;
import com.axiata.dialog.model.MssSignatureLog;
import org.apache.axis2.databinding.types.NCName;
import org.apache.log4j.Logger;
import org.etsi.uri.ts102204.v1_1_2.MSS_StatusReq;
import org.etsi.uri.ts102204.v1_1_2.MSS_StatusReqType;
import org.etsi.uri.ts102204.v1_1_2.MSS_StatusResp;
import org.etsi.uri.ts102204.v1_1_2.MSS_StatusRespType;

import java.math.BigInteger;
import java.util.Calendar;

/**
 * Created by nilan on 12/23/14.
 */
public class MSSStatus {

    SignatureDao signatureDao=new SignatureDaoImpl();
    final static Logger log = Logger.getLogger(MSSStatus.class);

    public void getMssStatus(String apTranceId){

        String id=apTranceId;
        String mssp="";
        String statusCode="";
        String description="";

        //have to get response from the server  for MSS-Status
        MSS_StatusReq mss_statusReq=new MSS_StatusReq();
        MSS_StatusReqType mss_statusReqType=new MSS_StatusReqType();
        NCName nc=new NCName();
        nc.setValue(apTranceId);
        mss_statusReqType.setMSSP_TransID(nc);
        mss_statusReq.setMSS_StatusReq(mss_statusReqType);
        //send reqvest as mss_statusReq


        //get responce as mss_statusResp
        MSS_StatusResp mss_statusResp=new MSS_StatusResp();
        MSS_StatusRespType mssStatusRespType=mss_statusResp.getMSS_StatusResp();
        statusCode= mssStatusRespType.getStatus().getStatusCode().toString();
        description=mssStatusRespType.getStatus().getStatusMessage();

        MssSignatureLog mssSignatureLog =new MssSignatureLog();
        mssSignatureLog.setMsisdn(mssp);
        BigInteger statusCodeBI=new BigInteger(statusCode);
        mssSignatureLog.setRespStatus(statusCodeBI);
        mssSignatureLog.setApTranceId(id);
        mssSignatureLog.setRespDescription(description);
        Calendar calendar = Calendar.getInstance();
        mssSignatureLog.setRespTime(calendar.getTime());
        try {
            signatureDao.editSignatureLog(mssSignatureLog);

        }catch(Exception ex){
            ex.printStackTrace();
            log.error("NotifyServlet Exception :"+ex);
        }

    }
}
