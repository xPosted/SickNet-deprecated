package com.jubaka.sors.desktop.http.protocol.tcp;

import com.jubaka.sors.desktop.sessions.IPaddr;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by root on 18.11.16.
 */
public class TCP {
    protected IPaddr srcIP = null;
    protected IPaddr dstIP = null;
    protected Integer srcPort = null;
    protected Integer dstPort = null;
    protected int payloadLen = 0;
    protected Long dataPointer = null;
    protected File dataFile;
    protected Long timestamp = null;
    protected boolean straight;


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public File getDataFile() {
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

    public IPaddr getDstIP() {
        return dstIP;
    }

    public void setDstIP(IPaddr dstIP) {
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

    public IPaddr getSrcIP() {
        return srcIP;
    }

    public void setSrcIP(IPaddr srcIP) {
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

    public byte[] getPayload() {
        byte[] buf = null;
        try {
            if (getDataFile() == null) return null;
            RandomAccessFile  raf = new RandomAccessFile(getDataFile(),"r");
            raf.seek(getDataPointer());
            buf = new byte[getPayloadLen()];
            raf.read(buf);
            raf.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return buf;

    }

}
