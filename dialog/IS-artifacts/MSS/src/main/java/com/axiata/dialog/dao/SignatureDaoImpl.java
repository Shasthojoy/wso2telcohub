package com.axiata.dialog.dao;

import com.axiata.dialog.model.MssSignatureLog;
import com.axiata.dialog.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import java.math.BigInteger;
import java.util.List;


/**
 * Created by nilan on 11/25/14.
 */
public class SignatureDaoImpl implements SignatureDao {

    @Override
    public void addSignatureLog(MssSignatureLog mssSignatureLog) throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(mssSignatureLog);
        session.getTransaction().commit();

    }

    @Override
    public void editSignatureLog(MssSignatureLog mssSignatureLog) throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        String apTranceId=mssSignatureLog.getApTranceId();

       /* Query querySelect = session.createQuery("Select apTranceId,msisdn  From MssSignatureLog Where msisdn ='"+mssSignatureLog.getMsisdn()+"' " +
                "AND reqTime=(SELECT MAX(reqTime) FROM MssSignatureLog)");

        List<Object[]> MssSignatureLogs= (List<Object[]>)querySelect.list();
        for(Object[] MssSignatureLog: MssSignatureLogs){
            apTranceId = (String)MssSignatureLog[0];
         }
            */
        Query query = session.createQuery("update MssSignatureLog set respStatus = :respStatus,respDescription=:respDescription,respTime=:respTime" + " where apTranceId='"+apTranceId+"'");

        query.setParameter("respStatus",mssSignatureLog.getRespStatus());
        query.setParameter("respDescription",mssSignatureLog.getRespDescription());
        query.setParameter("respTime",mssSignatureLog.getRespTime());

        int result = query.executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public int getDalyCount(String format){

        int count=0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("from MssSignatureLog where apTranceId like '"+format+"%'");

        if(query.list().isEmpty()){
            count=1;
        }else{
            count=query.list().size()+1;

        }

      return count;

    }

    @Override
    public BigInteger getRespStatus(String apTranceId){

        BigInteger respStatus=new BigInteger("0");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("from MssSignatureLog where apTranceId ='"+apTranceId+"'");
        List<?> list = query.list();
        MssSignatureLog mssSignatureLog = (MssSignatureLog)list.get(0);
        respStatus=mssSignatureLog.getRespStatus();
        return respStatus;

    }


}
