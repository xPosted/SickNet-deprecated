package com.jubaka.sors.desktop.protocol.tcp;

import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.desktop.sessions.IPaddr;
import com.jubaka.sors.desktop.sessions.PayloadAcquirer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by root on 18.11.16.
 */
public class TCP {
    protected String srcIP = null;
    protected String dstIP = null;
    protected Integer srcPort = null;
    protected Integer dstPort = null;
    protected int payloadLen = 0;
    protected PayloadAcquirer payloadAcquirer;
    protected Long timestamp = null;
    protected boolean straight;



    protected Long sessionId;

//    protected SessionBean sessionBean;

    protected int sequence;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

 /*   public File getDataFile() {
        return dataFile;
    }

    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }

    public Long getDataPointer() {
        return dataPointer;
    }

    public void setDataPointer(Long dataPointer) {
        this.dataPointer = dataPointer;
    }
*/
    public String getDstIP() {
        return dstIP;
    }

    public void setDstIP(String dstIP) {
        this.dstIP = dstIP;
    }

    public Integer getDstPort() {
        return dstPort;
    }

    public void setDstPort(Integer dstPort) {
        this.dstPort = dstPort;
    }

    public int getPayloadLen() {
        return payloadLen;
    }

    public void setPayloadLen(int payloadLen) {
        this.payloadLen = payloadLen;
    }

    public String getSrcIP() {
        return srcIP;
    }

    public void setSrcIP(String srcIP) {
        this.srcIP = srcIP;
    }

    public Integer getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(Integer srcPort) {
        this.srcPort = srcPort;
    }

    public boolean isStraight() {
        return straight;
    }

    public void setStraight(boolean straight) {
        this.straight = straight;
    }

    public PayloadAcquirer getPayloadAcquirer() {
        return payloadAcquirer;
    }

    public void setPayloadAcquirer(PayloadAcquirer payloadAcquirer) {
        this.payloadAcquirer = payloadAcquirer;
    }


    public byte[] getPayload() {
      return payloadAcquirer.getPayload();
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

}
