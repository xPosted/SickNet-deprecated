package com.jubaka.sors.beans.branch;

import com.jubaka.sors.beans.Bean;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashSet;

/**
 * Created by root on 30.08.16.
 */
public class SubnetLightBean extends Bean implements Serializable {
    protected Long dataSend = (long) 0;
    protected Long dataReceive = (long) 0;
    protected Integer activeSesCnt=0;
    protected Integer activeAddrCnt=0;
    protected Integer sesCnt=0;
    protected Integer addrCnt=0;

    protected InetAddress subnet;
    protected int subnetMask;
    public boolean selected= false;
    protected Integer brId;

    private HashSet<IPItemLightBean> ips = new HashSet<IPItemLightBean>();	// all captured ips
    private HashSet<IPItemLightBean> liveIps = new HashSet<IPItemLightBean>();	// ips that are online

    public Long getDataSend() {
        return dataSend;
    }
    public void setDataSend(Long dataSend) {
        this.dataSend = dataSend;
    }
    public Long getDataReceive() {
        return dataReceive;
    }
    public void setDataReceive(Long dataReceive) {
        this.dataReceive = dataReceive;
    }
    public InetAddress getSubnet() {
        return subnet;
    }
    public void setSubnet(InetAddress subnet) {
        this.subnet = subnet;
    }
    public int getSubnetMask() {
        return subnetMask;
    }
    public void setSubnetMask(int subnetMask) {
        this.subnetMask = subnetMask;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public Integer getBrId() {
        return brId;
    }
    public void setBrId(Integer brId) {
        this.brId = brId;
    }
    public Integer getActiveSesCnt() {
        return activeSesCnt;
    }
    public void setActiveSesCnt(Integer activeSesCnt) {
        this.activeSesCnt = activeSesCnt;
    }
    public Integer getActiveAddrCnt() {
        return activeAddrCnt;
    }
    public void setActiveAddrCnt(Integer activeAddrCnt) {
        this.activeAddrCnt = activeAddrCnt;
    }
    public Integer getSesCnt() {
        return sesCnt;
    }
    public void setSesCnt(Integer sesCnt) {
        this.sesCnt = sesCnt;
    }
    public Integer getAddrCnt() {
        return addrCnt;
    }
    public void setAddrCnt(Integer addrCnt) {
        this.addrCnt = addrCnt;
    }

    public HashSet<IPItemLightBean> getLightIps() {
        return ips;
    }

    public void setLightIps(HashSet<IPItemLightBean> ips) {
        this.ips = ips;
    }

    public HashSet<IPItemLightBean> getLightLiveIps() {
        return liveIps;
    }

    public void setLightLiveIps(HashSet<IPItemLightBean> liveIps) {
        this.liveIps = liveIps;
    }

}
