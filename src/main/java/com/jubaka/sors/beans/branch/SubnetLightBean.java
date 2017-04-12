package com.jubaka.sors.beans.branch;

import com.jubaka.sors.beans.Bean;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by root on 30.08.16.
 */
public class SubnetLightBean extends Bean implements Serializable {
    protected Long dataSend = (long) 0;
    protected Long dataReceive = (long) 0;
    protected Integer activeInSesCnt = 0;
    protected Integer activeOutSesCnt = 0;



    protected  Integer inSesCnt = 0;
    protected Integer outSesCnt = 0;

    protected Integer activeSesCnt=0;
    protected Integer activeAddrCnt=0;
    protected Integer sesCnt=0;
    protected Integer addrCnt=0;

    protected InetAddress subnet;
    protected int subnetMask;
    public boolean selected= false;
    protected Integer brId;
    protected Long dbId;

    private List<IPItemLightBean> ips = new ArrayList<>();// all captured ips
    private List<IPItemLightBean> liveIps = new ArrayList<>();	// ips that are online

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

    public List<IPItemLightBean> getLightIps() {
        return ips;
    }

    public void setLightIps(List<IPItemLightBean> ips) {
        this.ips = ips;
    }

    public List<IPItemLightBean> getLightLiveIps() {
        return liveIps;
    }

    public void setLightLiveIps(List<IPItemLightBean> liveIps) {
        this.liveIps = liveIps;
    }

    public List<IPItemLightBean> getAllIpList() {
        return new ArrayList<>(ips);
    }
    public List<IPItemLightBean> getLiveIpList() {
        return  new ArrayList<IPItemLightBean>(liveIps);
    }

    public Integer getActiveOutSesCnt() {
        return activeOutSesCnt;
    }

    public void setActiveOutSesCnt(Integer activeOutSesCnt) {
        this.activeOutSesCnt = activeOutSesCnt;
    }

    public Integer getActiveInSesCnt() {
        return activeInSesCnt;
    }

    public void setActiveInSesCnt(Integer activeInSesCnt) {
        this.activeInSesCnt = activeInSesCnt;
    }

    public Integer getInSesCnt() {
        return inSesCnt;
    }

    public void setInSesCnt(Integer inSesCnt) {
        this.inSesCnt = inSesCnt;
    }

    public Integer getOutSesCnt() {
        return outSesCnt;
    }

    public void setOutSesCnt(Integer outSesCnt) {
        this.outSesCnt = outSesCnt;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public IPItemLightBean getIpByName(String name) {
        for (IPItemLightBean ipBean : ips) {
            if (ipBean.getIp().equals(name))
                return ipBean;
        }
        return null;
    }



}
