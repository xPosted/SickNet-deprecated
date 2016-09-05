package com.jubaka.sors.beans.branch;

import com.jubaka.sors.beans.Bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by root on 30.08.16.
 */
public class IPItemLightBean extends Bean implements Serializable {

    protected String ip = "0.0.0.0";
    protected String dnsName ="DNS name";
    protected Long dataDown = (long) 0;
    protected Long dataUp = (long) 0;
    protected Date activated = new Date(0);
    protected Integer inputCount=0;
    protected Integer outputCount=0;
    protected Integer activeCount=0;
    protected Integer savedCount=0;

    public synchronized Long getDataDown() {
        return dataDown;
    }
    public synchronized void setDataDown(Long dataDown) {
        this.dataDown = dataDown;
    }
    public synchronized Long getDataUp() {
        return dataUp;
    }
    public synchronized void setDataUp(Long dataUp) {
        this.dataUp = dataUp;
    }
    public synchronized Date getActivated() {
        return activated;
    }
    public synchronized void setActivated(Date activated) {
        this.activated = activated;
    }
    public synchronized Integer getInputCount() {
        return inputCount;
    }
    public synchronized void setInputCount(Integer inputCount) {
        this.inputCount = inputCount;
    }
    public synchronized Integer getOutputCount() {
        return outputCount;
    }
    public synchronized void setOutputCount(Integer outputCount) {
        this.outputCount = outputCount;
    }
    public synchronized Integer getActiveCount() {
        return activeCount;
    }
    public synchronized void setActiveCount(Integer activeCount) {
        this.activeCount = activeCount;
    }
    public synchronized Integer getSavedCount() {
        return savedCount;
    }
    public synchronized void setSavedCount(Integer savedCount) {
        this.savedCount = savedCount;
    }
    public synchronized String getIp() {
        return ip;
    }
    public synchronized void setIp(String ip) {
        this.ip = ip;
    }
    public synchronized String getDnsName() {
        return dnsName;
    }
    public synchronized void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }
}
