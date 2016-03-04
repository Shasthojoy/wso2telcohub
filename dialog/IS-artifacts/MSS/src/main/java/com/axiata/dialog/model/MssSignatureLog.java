package com.axiata.dialog.model;

/**
 * Created by nilan on 11/25/14.
 */

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name="MSS_SIGNATURE_LOG")
public class MssSignatureLog {


   @Id
   @Column(name="AP_TRANS_ID")
   private String apTranceId;

   @Column(name="MSISDN")
   private String msisdn;

   @Column(name="REQ_STATUS")
   private BigInteger reaStatus;

   @Column(name="RESP_STATUS")
   private BigInteger respStatus;

   @Column(name="DESCRIPTION")
   private String description;

   @Column(name="REQ_TIME")
   private Date reqTime;

   @Column(name="RESP_TIME")
   private Date respTime;

   @Column(name="RESP_DESCRIPTION")
   private String respDescription;

    public String getRespDescription() {
        return respDescription;
    }

    public void setRespDescription(String respDescription) {
        this.respDescription = respDescription;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getApTranceId() {
        return apTranceId;
    }

    public void setApTranceId(String apTranceId) {
        this.apTranceId = apTranceId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public BigInteger getReaStatus() {
        return reaStatus;
    }

    public void setReaStatus(BigInteger reaStatus) {
        this.reaStatus = reaStatus;
    }

    public BigInteger getRespStatus() {
        return respStatus;
    }

    public void setRespStatus(BigInteger respStatus) {
        this.respStatus = respStatus;
    }




}
