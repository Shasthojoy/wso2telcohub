package com.axiata.dialog.services;

import com.axiata.dialog.dao.SignatureDao;
import com.axiata.dialog.dao.SignatureDaoImpl;
import com.axiata.dialog.model.MssSignatureLog;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Calendar;

/**
 * Created by nilan on 11/28/14.
 */
public class NotifyServlet extends HttpServlet {

    final static Logger log = Logger.getLogger(NotifyServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
         String br = request.getReader().readLine();
         SignatureDao signatureDao=new SignatureDaoImpl();
         log.info("request : " + br);
         System.out.println("request : " + br);

    try {

    MessageFactory  factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
    SOAPMessage message = factory.createMessage(null,new ByteArrayInputStream(br.getBytes(Charset.forName("UTF-8"))));
    SOAPBody body = message.getSOAPBody();
    NodeList nodes=body.getFirstChild().getFirstChild().getChildNodes();
    String id="";
    String mssp="";
    String statusCode="";
    String description="";

    for(int i=0;i<nodes.getLength();i++) {
       if(nodes.item(i).getNodeName().equalsIgnoreCase("ns5:AP_Info")) {
            id = nodes.item(i).getAttributes().getNamedItem("AP_TransID").getNodeValue();
        }else if(nodes.item(i).getNodeName().equalsIgnoreCase("ns5:MobileUser")){
            mssp = nodes.item(i).getFirstChild().getTextContent();
        }
        else if(nodes.item(i).getNodeName().equalsIgnoreCase("ns5:Status")) {
            statusCode = nodes.item(i).getFirstChild().getAttributes().getNamedItem("Value").getNodeValue();
        }
        if(nodes.item(i).getNodeName().equalsIgnoreCase("ns5:Status")) {
            description = nodes.item(i).getFirstChild().getNextSibling().getFirstChild().getTextContent();
        }
    }

    log.info("============id==============:"+id);
    log.info("============MSSP============:"+mssp);
    log.info("============Status==========:"+statusCode);
    log.info("============Description=====:"+description);

    MssSignatureLog mssSignatureLog =new MssSignatureLog();
    mssSignatureLog.setMsisdn(mssp);
    BigInteger statusCodeBI=new BigInteger(statusCode);
    mssSignatureLog.setRespStatus(statusCodeBI);
    mssSignatureLog.setApTranceId(id);
    mssSignatureLog.setRespDescription(description);
    Calendar calendar = Calendar.getInstance();
    mssSignatureLog.setRespTime(calendar.getTime());

    signatureDao.editSignatureLog(mssSignatureLog);
    }catch(Exception ex){
         ex.printStackTrace();
         log.error("NotifyServlet Exception :"+ex);

    }

 }

}
