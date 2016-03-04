/**
 * Title		:DialogUtilities
 * Description	:<description>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:May 26, 2005
 * @author 		:Administrator
 * @version 	:1.0
 */

package com.dialog.util.bgw;

import com.dialog.util.ObjectPool;
import com.dialog.util.SystemLog;
public class BillGWCtrlPl extends ObjectPool {

    String m_url = "";
    String m_appID = "";
    String m_userName = "";
    String m_password = "";
    int m_size = 0;
    String m_domainID = "";
    
    /**
     * @param size
     * @throws Exception
     */
    public BillGWCtrlPl(int size, String url, String appID, String userName, String password) throws Exception {
        super(size);
        this.m_url = url;
        this.m_appID = appID;
        this.m_userName = userName;
        this.m_password = password;
        this.m_size = size;
    }
    
    public BillGWCtrlPl(int size, String url, String appID, String userName, String password,String domainID) throws Exception {
        super(size);
        this.m_url = url;
        this.m_appID = appID;
        this.m_userName = userName;
        this.m_password = password;
        this.m_size = size;
        this.m_domainID = domainID;
    }

    public Object create() {
        return new BillGWCtrl();
    }
    
    public void init(){
        for(int x=0;x<m_size;x++){
            BillGWCtrl bg = (BillGWCtrl)this.checkOut();
            bg.setUrl(m_url);
            bg.setAppID(m_appID);
            bg.setUserName(m_userName);
            bg.setPassword(m_password);
            bg.setDomain(m_domainID);
            SystemLog.getInstance().getLogger("billgw").debug("INIT,"+m_url+","+m_appID);
            try {
                bg.login();
            } catch (Exception e) {
                SystemLog.getInstance().getLogger("billgwerr").error("error @ BillGWCtrlPl.init",e);
                System.out.println("EERRR:Login"+e);
            }
            this.checkIn(bg);
        }
    }
}
