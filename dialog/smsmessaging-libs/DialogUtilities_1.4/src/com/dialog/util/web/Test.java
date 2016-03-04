/**
 * 
 */
package com.dialog.util.web;

import java.nio.charset.Charset;
import java.util.SortedMap;
import java.util.Vector;

import com.dialog.util.SystemConfig;

/**
 * Title		: iodinfoserver	
 * Description	: 
 * Copyright	: Copyright(c) 2009
 * Company		: Dialog Telekom PLC.
 * Created on	: Feb 6, 2009
 * @author 		: charith
 * Comments		: 
 */
public class Test {

    /**
     * 
     */
    public Test() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SortedMap map = Charset.availableCharsets();
        String str = Charset.forName("ISO8859-1").name();
        System.out.println(str);
        SystemConfig.DEFAULT_FILE = "D:/Office/Development/workspace/MyDiaryOngoing/MyDiary/conf/config.xml";
        SystemConfig.init();
        
        Vector v = SystemConfig.getInstance().getChildElementNames("admin.db.My_diary");
        System.out.println(v);
    }

}
