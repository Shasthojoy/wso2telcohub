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
public class EPFHSQLManager {

    /**
     * 
     */
    public EPFHSQLManager() {        
    }
    
    /**
     * 
     */
    public void setup() {
        Connection conn         = null;
        PreparedStatement stmt  = null;
        try {            
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            
            stmt = conn.prepareStatement("SET FILES LOG SIZE 10");
            stmt.execute();        
            stmt.clearBatch();
            stmt.clearParameters();
            
            stmt = conn.prepareStatement("SET DATABASE DEFAULT TABLE TYPE CACHED");
            stmt.execute();        
            stmt.clearBatch();
            stmt.clearParameters();
            
            stmt = conn.prepareStatement("CREATE TABLE INCOMING (MSG_ID BIGINT NOT NULL IDENTITY, MSG_OBJECT LONGVARBINARY)");
            stmt.executeUpdate();            
            System.out.println(Utility.formatToScreen("Setting up inner DB", 75)+"[OK]");
            
            stmt.clearBatch();
            stmt.clearParameters();
            stmt = conn.prepareStatement("CREATE INDEX INDEX_INCOM_MSG_P_KEY ON INCOMING(MSG_ID)");
            stmt.executeUpdate();
            System.out.println(Utility.formatToScreen("Indexing inner DB [1/1]", 75)+"[OK]");            
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
                    SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"setup",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"setup",ex);
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
            SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"shutdown",e);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"shutdown",ex);
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    SystemLog.getInstance().getLogger("smscomp_event").error("[EPFHSQLManager],"+"shutdown",ex);
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
    public int saveObject(Object obj) {
        Connection conn         = null;
        PreparedStatement stmt  = null;
        int count               = -1;
        try {
            conn = SystemDB.getInstance().getDBController("HSQL").getConnection();
            stmt = conn.prepareStatement("INSERT INTO PUBLIC.INCOMING (MSG_OBJECT) VALUES (?);");
            
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
    public boolean deleteBatch(ArrayList<PacketObject> list) {
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
    
    public static void main(String[] args) {
        SystemConfig.DEFAULT_FILE = "D:/Office/Development/workspace/SmppCompIrisV2/config.xml";
        SystemConfig.init();
        
        EPFHSQLManager h = new EPFHSQLManager();
        h.setup();    
        h.shutdown();
    }
}
