package com.dialog.rnd.iris.smpp.db;

import ie.omk.smpp.message.DeliverSM;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.hsqldb.jdbc.JDBCBlob;

import com.dialog.rnd.iris.smpp.core.PacketObject;
import com.dialog.util.SystemConfig;
import com.dialog.util.SystemDB;
import com.dialog.util.SystemLog;
import com.dialog.util.Utility;

/**
 * Title		: SmppCompIrisV2	
 * Description	: 
 * Copyright	: Copyright(c) 2009
 * Company		: Dialog Telekom PLC.
 * Created on	: Nov 26, 2009
 * @author 		: charith
 * Comments		: 
 */
public class HSQLManager {
    private int m_logFileSize = 10; //in MB 
    /**
     * 
     */
    public HSQLManager() {       
        
    }
    
    public HSQLManager(int logFileSize) {
        m_logFileSize = logFileSize;
    }
    
    /**
     * 
     */
    public void setup() {
        Connection conn         = null;
        PreparedStatement stmt  = null;
        try {            
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            
            stmt = conn.prepareStatement("SET FILES LOG SIZE "+m_logFileSize);
            stmt.execute();        
            stmt.clearBatch();
            stmt.clearParameters();
            
            stmt = conn.prepareStatement("SET DATABASE DEFAULT TABLE TYPE CACHED");
            stmt.execute();        
            stmt.clearBatch();
            stmt.clearParameters();
            
            stmt = conn.prepareStatement("CREATE TABLE INCOMING (MSG_ID BIGINT NOT NULL IDENTITY, MSG_OBJECT LONGVARBINARY)");
            stmt.executeUpdate();            
            stmt.clearBatch();
            stmt.clearParameters();
            System.out.println(Utility.formatToScreen("Setting up inner DB", 75)+"[OK]");            
            
            stmt = conn.prepareStatement(
                        "CREATE TABLE CORP_MSG_SRESP ("+
                        "        CORP_MSG_ID         NUMERIC        NOT NULL,"+
                        "        CORP_MSG_SMSCREF    VARCHAR(20)    DEFAULT 0,"+
                        "        SUBMIT_RESP_TIME    timestamp);");
            stmt.executeUpdate();
            
            stmt.clearBatch();
            stmt.clearParameters();
            stmt = conn.prepareStatement(
                    "CREATE TABLE CORP_MSG_DRESP ("+
                    "        CORP_MSG_SMSCREF    VARCHAR(20)    DEFAULT 0,"+
                    "        DELIVERED           BOOLEAN        DEFAULT false,"+
                    "        RETRIES             INTEGER        DEFAULT 0,"+    
                    "        DELIVER_RESP_TIME   timestamp);");
            stmt.executeUpdate();
            System.out.println(Utility.formatToScreen("Setting up inner DB", 75)+"[OK]");
            
            stmt.clearBatch();
            stmt.clearParameters();
            stmt = conn.prepareStatement("CREATE INDEX INDEX_CORP_MSG_P_KEY ON CORP_MSG_SRESP(CORP_MSG_ID)");
            stmt.executeUpdate();
            System.out.println(Utility.formatToScreen("Indexing inner DB [1/5]", 75)+"[OK]");
            
            stmt.clearBatch();
            stmt.clearParameters();
            stmt = conn.prepareStatement("CREATE INDEX INDEX_CORP_MSG_SMSCREF ON CORP_MSG_SRESP (CORP_MSG_SMSCREF)");
            stmt.executeUpdate();
            System.out.println(Utility.formatToScreen("Indexing inner DB [2/5]", 75)+"[OK]");
            
            stmt.clearBatch();
            stmt.clearParameters();
            stmt = conn.prepareStatement("CREATE INDEX INDEX_CORP_MSG_DELIVERED ON CORP_MSG_DRESP (DELIVERED)");
            stmt.executeUpdate();
            System.out.println(Utility.formatToScreen("Indexing inner DB [3/5]", 75)+"[OK]");
            
            stmt.clearBatch();
            stmt.clearParameters();
            stmt = conn.prepareStatement("CREATE INDEX INDEX_CORP_MSG_CORP_MSG_SMSCREF ON CORP_MSG_DRESP (CORP_MSG_SMSCREF)");
            stmt.executeUpdate();
            System.out.println(Utility.formatToScreen("Indexing inner DB [4/5]", 75)+"[OK]");
            
            stmt.clearBatch();
            stmt.clearParameters();
            stmt = conn.prepareStatement("CREATE INDEX INDEX_INCOM_MSG_P_KEY ON INCOMING(MSG_ID)");
            stmt.executeUpdate();
            System.out.println(Utility.formatToScreen("Indexing inner DB [5/5]", 75)+"[OK]");
        }
        catch (Exception e) {
            if(e.getMessage().indexOf("Table already exists")>-1) {
                System.out.println(Utility.formatToScreen("Setting up inner DB", 75)+"[OK]");
            } else {
                System.out.println(Utility.formatToScreen("Setting up inner DB", 75)+"[ERROR]");
                e.printStackTrace();   
            }            
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"setup",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"setup",ex);
                }
                conn = null;
            }   
        }
    }
    /**
     * 
     */
    public void shutdown() {
        Connection conn         = null;
        PreparedStatement stmt  = null;
        try {
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            stmt = conn.prepareStatement("SHUTDOWN COMPACT");            
            stmt.executeUpdate();            
            System.out.println(Utility.formatToScreen("Inner DB shutdown in progress", 75)+"[OK]");            
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"shutdown",e);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"shutdown",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"shutdown",ex);
                }
                conn = null;
            }   
        }
    }
    /**
     * 
     * @param obj
     * @return
     */
    public int saveSubmitResp(PacketObject obj) {
        Connection conn         = null;
        PreparedStatement stmt  = null;
        int count               = -1;
        try {
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            stmt = conn.prepareStatement("INSERT INTO CORP_MSG_SRESP(CORP_MSG_ID,CORP_MSG_SMSCREF,SUBMIT_RESP_TIME) VALUES (?, ?, now)");
            stmt.setLong(1, obj.getMessageID());
            stmt.setString(2, obj.getSmscRef());            
            count = stmt.executeUpdate();          
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_incoming").error("SUBMIT_SM_QUEUE,"+obj+","+e.getMessage(),e);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"saveSubmitResp",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"saveSubmitResp",ex);
                }
                conn = null;
            }   
        }
        return count;
    }
    /**
     * 
     * @param obj
     * @return
     */
    public int saveDeliveryResp(PacketObject obj) {
        Connection conn         = null;
        PreparedStatement stmt  = null;
        int count               = -1;
        try {
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            stmt = conn.prepareStatement("INSERT INTO CORP_MSG_DRESP(CORP_MSG_SMSCREF,DELIVERED,DELIVER_RESP_TIME) VALUES(?,?,now)");
            stmt.setString(1, obj.getSmscRef());
            stmt.setBoolean(2, true);                        
            count = stmt.executeUpdate();          
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_incoming").error("DELIVER_REPORT_QUEUE,"+obj+","+e.getMessage(),e);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"saveDeliveryResp",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"saveDeliveryResp",ex);
                }
                conn = null;
            }   
        }
        return count;
    }    
    
    /**
     * 
     * @param rowCount
     * @return
     */
    public ArrayList<PacketObject> getDeliveredMessages(int rowCount) {
        Connection conn              = null;
        PreparedStatement stmt       = null;      
        ArrayList<PacketObject> list = new ArrayList<PacketObject>();
        try {
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            stmt = conn.prepareStatement("SELECT S.CORP_MSG_ID,D.CORP_MSG_SMSCREF,D.RETRIES FROM CORP_MSG_DRESP D, CORP_MSG_SRESP S WHERE D.CORP_MSG_SMSCREF = S.CORP_MSG_SMSCREF AND DELIVERED=TRUE LIMIT ?");
            stmt.setInt(1, rowCount);
            ResultSet rs = stmt.executeQuery();   
            while(rs.next()) {
                PacketObject obj = new PacketObject();
                obj.setMessageID(rs.getLong("CORP_MSG_ID"));
                obj.setSmscRef(rs.getString("CORP_MSG_SMSCREF"));
                obj.setRetryCount(rs.getInt("RETRIES"));
                list.add(obj);
            }
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"getDeliveredMessages",e);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"getDeliveredMessages",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"getDeliveredMessages",ex);
                }
                conn = null;
            }   
        }
        return list;
    }
    /**
     * 
     * @param messageID
     * @return
     */
    /*public int deleteMessages(long messageID) {
        Connection conn              = null;
        PreparedStatement stmt       = null;      
        int count                    = -1;
        try {
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            stmt = conn.prepareStatement("DELETE FROM CORP_MSG_SRESP WHERE CORP_MSG_ID=?");
            stmt.setLong(1, messageID);
            count = stmt.executeUpdate();  
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"deleteMessages",e);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"deleteMessages",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"deleteMessages",ex);
                }
                conn = null;
            }   
        }
        return count;
    }*/
    /**
     * 
     * @param list
     * @return
     */
    public boolean deleteBatch(ArrayList<PacketObject> list) {
        Connection conn              = null;
        PreparedStatement stmt       = null;      
        boolean saved                = false;
        try {
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            stmt = conn.prepareStatement("DELETE FROM CORP_MSG_SRESP WHERE CORP_MSG_ID=?");
            conn.setAutoCommit(false);
            for(PacketObject obj : list) {
                stmt.setLong(1, obj.getMessageID());
                stmt.addBatch();
            }            
            int[] count = stmt.executeBatch();
            for (int i = 0; i < count.length; i++) {
                if(count[i]==1){
                    saved = true;
                }else{
                    conn.rollback();
                    saved = false;
                    break;
                }
            }
            
            stmt.clearBatch();
            stmt.clearParameters();
            stmt = conn.prepareStatement("DELETE FROM CORP_MSG_DRESP WHERE CORP_MSG_SMSCREF=?");
            conn.setAutoCommit(false);
            for(PacketObject obj : list) {
                stmt.setString(1, obj.getSmscRef());
                stmt.addBatch();
            }            
            count = stmt.executeBatch();
            for (int i = 0; i < count.length; i++) {
                if(count[i]==1){
                    saved = true;
                }else{
                    conn.rollback();
                    saved = false;
                    break;
                }
            }
            
            conn.commit();
            conn.setAutoCommit(true);
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"deleteBatch",e);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"deleteBatch",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"deleteBatch",ex);
                }
                conn = null;
            }   
        }
        return saved;
    }
    /**
     *     
     * @param list
     * @return
     */
    public boolean batchUpdateRetryCount(ArrayList<PacketObject> list) {
        Connection conn              = null;
        PreparedStatement stmt       = null;      
        boolean saved                = false;
        try {
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            stmt = conn.prepareStatement("UPDATE CORP_MSG_DRESP SET RETRIES=? WHERE CORP_MSG_SMSCREF=?");
            conn.setAutoCommit(false);
            for(PacketObject obj : list) {
                stmt.setInt(1, (obj.getRetryCount()+1));
                stmt.setString(2, obj.getSmscRef());
                stmt.addBatch();
            }            
            int[] count = stmt.executeBatch();
            for (int i = 0; i < count.length; i++) {
                if(count[i]==1){
                    saved = true;
                }else{
                    conn.rollback();
                    saved = false;
                    break;
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"batchUpdateRetryCount",e);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"batchUpdateRetryCount",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[HSQLManager],"+"deleteBatch",ex);
                }
                conn = null;
            }   
        }
        return saved;
    }
    
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        SystemConfig.DEFAULT_FILE = "D:/Office/Development/workspace/SmppCompIrisV2/config.xml";
        SystemConfig.init();
        
        HSQLManager h = new HSQLManager();
        h.setup();    
        
        /*System.out.println(h.saveSubmitResp(new PacketObject("123456789", 1000)));
        System.out.println(h.saveSubmitResp(new PacketObject("223456789", 2000)));
        System.out.println(h.saveSubmitResp(new PacketObject("323456789", 3000)));*/
        
        /*System.out.println(h.saveDeliveryResp(new PacketObject("123456789")));
        System.out.println(h.saveDeliveryResp(new PacketObject("223456789")));
        System.out.println(h.saveDeliveryResp(new PacketObject("323456789")));*/
        
        /*ArrayList<PacketObject> packets1 = h.getDeliveredMessages(10);
        ArrayList<PacketObject> list = new ArrayList<PacketObject>();
        for(PacketObject obj : packets1) {
            System.out.println(obj.getSmscRef()+" - "+obj.getMessageID()+" - "+obj.getRetryCount());
            obj.updateRetryCount();
            list.add(obj);
        }
        
        h.batchUpdateRetryCount(list);*/
        
        /*ArrayList<PacketObject> packets2 = h.getDeliveredMessages(10);
        for(PacketObject obj : packets2) {
            System.out.println(obj.getSmscRef()+" - "+obj.getMessageID()+" - "+obj.getRetryCount());
        }*/
        
        /*h.deleteMessages(1000);*/
        
        ArrayList<PacketObject> packets2 = h.getDeliveredMessages(10);
        for(PacketObject obj : packets2) {
            System.out.println(obj.getSmscRef()+" - "+obj.getMessageID());
        }
        
        h.deleteBatch(packets2);
        
        ArrayList<PacketObject> packets3 = h.getDeliveredMessages(10);
        for(PacketObject obj : packets3) {
            System.out.println(obj.getSmscRef()+" - "+obj.getMessageID());
        }
        
        h.shutdown();
    }
    
    /**
     * 
     * @param obj
     * @return
     */
    public int saveObject(Object obj) {
        Connection conn         = null;
        PreparedStatement stmt  = null;
        int count               = -1;
        try {
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            stmt = conn.prepareStatement("INSERT INTO INCOMING (MSG_OBJECT) VALUES (?);");
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] bytes = baos.toByteArray();
            stmt.setBlob(1, new JDBCBlob(bytes));
            count = stmt.executeUpdate();          
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_incoming").error("saveObject,"+obj+","+e.getMessage(),e);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"saveObject",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"saveObject",ex);
                }
                conn = null;
            }   
        }
        return count;
    }
        
    /**
     * 
     * @param rowCount
     * @return
     */
    public ArrayList<PacketObject> getMessages(int rowCount) {
        Connection conn              = null;
        PreparedStatement stmt       = null;      
        ArrayList<PacketObject> list = new ArrayList<PacketObject>();
        try {
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            stmt = conn.prepareStatement("SELECT MSG_ID, MSG_OBJECT FROM INCOMING LIMIT ?");
            stmt.setInt(1, rowCount);
            ResultSet rs = stmt.executeQuery();   
            while(rs.next()) {
                PacketObject obj = new PacketObject();
                obj.setMessageID(rs.getLong("MSG_ID"));
                
                ObjectInputStream oip = new ObjectInputStream(rs.getBlob("MSG_OBJECT").getBinaryStream());                              
                obj.setPacket((DeliverSM) oip.readObject());
                list.add(obj);
            }
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"getMessages",e);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"getMessages",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"getMessages",ex);
                }
                conn = null;
            }   
        }
        return list;
    }    
    /**
     * 
     * @param list
     * @return
     */
    public boolean deleteIncomingBatch(ArrayList<PacketObject> list) {
        Connection conn              = null;
        PreparedStatement stmt       = null;      
        boolean saved                = false;
        try {
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            stmt = conn.prepareStatement("DELETE FROM INCOMING WHERE MSG_ID=?");
            conn.setAutoCommit(false);
            for(PacketObject obj : list) {
                stmt.setLong(1, obj.getMessageID());
                stmt.addBatch();
            }            
            int[] count = stmt.executeBatch();
            for (int i = 0; i < count.length; i++) {
                if(count[i]==1){
                    saved = true;
                }else{
                    conn.rollback();
                    saved = false;
                    break;
                }
            }            
            conn.commit();
            conn.setAutoCommit(true);
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"deleteBatch",e);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"deleteBatch",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"deleteBatch",ex);
                }
                conn = null;
            }   
        }
        return saved;
    }
}
