package com.jubaka.sors.beans.branch;

import com.jubaka.sors.beans.Bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 30.08.16.
 */
public class SessionLightBean extends Bean implements Serializable {

    private static SimpleDateFormat sdf= new SimpleDateFormat("ss:mm:HH dd.mm");
    protected Long srcDataLen = (long) 0;
    protected Long dstDataLen = (long) 0;
    protected Date established;
    protected Date closed;
    protected Long seq;
    protected Long ack;
    protected Long initSeq;
    protected String srcIP;
    protected String dstIP;
    protected Integer srcP;
    protected Integer dstP;
    protected boolean http=false;



    public  String getSrcIP() {
        return srcIP;
    }
    public void setSrcIP(String srcIP) {
        this.srcIP = srcIP;
    }
    public String getDstIP() {
        return dstIP;
    }
    public void setDstIP(String dstIP) {
        this.dstIP = dstIP;
    }
    public Integer getSrcP() {
        return srcP;
    }
    public void setSrcP(Integer srcP) {
        this.srcP = srcP;
    }
    public  Integer getDstP() {
        return dstP;
    }
    public void setDstP(Integer dstP) {
        this.dstP = dstP;
    }
    public  Long getSeq() {
        return seq;
    }
    public void setSeq(Long seq) {
        this.seq = seq;
    }
    public  Long getAck() {
        return ack;
    }
    public void setAck(Long ack) {
        this.ack = ack;
    }


    public Date getClosed() {
        return closed;
    }
    public void setClosed(Date closed) {
        this.closed = closed;

    }
    public  Date getEstablished() {
        return established;
    }
    public void setEstablished(Date established) {
        this.established = established;
    }

    public void  setInitSeq(long seq) {
        initSeq=seq;
    }

    public long getInitSeq() {
        return initSeq;
    }

    public  Long getSrcDataLen() {
        return srcDataLen;
    }
    public  Long getDstDataLen() {
        return dstDataLen;
    }
    public void setDstDataLen(Long dstDataLen) {
        this.dstDataLen = dstDataLen;
    }
    public void setSrcDataLen(Long srcDataLen) {
        this.srcDataLen = srcDataLen;
    }


    public String getNiga() {
        System.out.println("Niga");
        return "Niga";
    }
    public boolean isHttp() {
        if (http) System.out.println("True"); else System.out.println("False");
        return http;
    }

    public void setHttp(boolean http) {
        this.http = http;
    }

    public String getEstablishedWebStr() {
        return sdf.format(established);

    }

    public String getClosedWebStr() {
        if (closed == null) return "Online";
        return sdf.format(closed);
    }
}
