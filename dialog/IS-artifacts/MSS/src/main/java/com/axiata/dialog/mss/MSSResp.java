package com.axiata.dialog.mss;

import com.axiata.dialog.dao.SignatureDao;
import com.axiata.dialog.dao.SignatureDaoImpl;
import com.axiata.dialog.util.Config;

import java.util.concurrent.Callable;

/**
 * Created by nilan on 11/26/14.
 */
public class MSSResp implements Callable<String> {

    private SignatureDao signatureDao=new SignatureDaoImpl();
    private String apTranceId;

    public MSSResp(String apTranceId){
        this.apTranceId=apTranceId;

    }

    @Override
    public String call() throws Exception {
       String respStatus= Config.getInstance().getProperty("mss.signature.response.fail.status");
       int increment =0;
       while(true) {
           increment =increment +1;
           try {
               respStatus = signatureDao.getRespStatus(apTranceId).toString();
           } catch (Exception ex) {
               ex.printStackTrace();
           }
           if (respStatus.equalsIgnoreCase(Config.getInstance().getProperty("mss.signature.response.success.status"))) {
               return Config.getInstance().getProperty("mss.signature.response.success.status");
           }
           else if(respStatus.equalsIgnoreCase(Config.getInstance().getProperty("mss.signature.response.cancel.status"))){
               return Config.getInstance().getProperty("mss.signature.response.cancel.status");
           }
           else if(respStatus.equalsIgnoreCase(Config.getInstance().getProperty("mss.signature.response.expired.status"))){
               return Config.getInstance().getProperty("mss.signature.response.expired.status");
           }
           else if(!respStatus.equalsIgnoreCase(Config.getInstance().getProperty("mss.signature.response.fail.status"))){
               return respStatus;
           }
           Thread.sleep(Long.parseLong(Config.getInstance().getProperty("mss.signature.response.sleep.time")));
           if(increment==Integer.parseInt(Config.getInstance().getProperty("mss.signature.response.increment"))){
               return respStatus;
           }
       }


    }
}
