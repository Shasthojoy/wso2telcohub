package com.axiata.dialog.dao;

import com.axiata.dialog.model.MssSignatureLog;

import java.math.BigInteger;

/**
 * Created by nilan on 11/25/14.
 */
public interface SignatureDao {

    public void addSignatureLog(MssSignatureLog mssSignatureLog) throws Exception;

    public void editSignatureLog(MssSignatureLog mssSignatureLog) throws Exception;

    public int getDalyCount(String format);

    public BigInteger getRespStatus(String apTranceId);



}
