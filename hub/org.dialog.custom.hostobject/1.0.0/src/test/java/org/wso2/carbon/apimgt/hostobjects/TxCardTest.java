package org.wso2.carbon.apimgt.hostobjects;

import org.dialog.custom.hostobjects.ApiTxCard;
import java.util.HashMap;

public class TxCardTest {
	
	public static void main(String args[]){
		ApiTxCard card = new ApiTxCard();
		HashMap<Integer, Object> txCardTemp = card.getTxCardTemp();
		//TxCardDAO currentDao = (TxCardDAO) txCardTemp.get(new Integer(100));
		//System.out.println(currentDao.getHeaderList().size()+"");
	}
	
}
