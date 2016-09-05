package com.jubaka.sors.beans.branch;

import java.io.Serializable;
import java.util.HashSet;
import com.jubaka.sors.beans.SesDataCapBean;


public class SubnetBean extends SubnetLightBean implements Serializable {
	HashSet<IPItemBean> ips = new HashSet<IPItemBean>();	// all captured ips
	HashSet<IPItemBean> liveIps = new HashSet<IPItemBean>();	// ips that are online
	

    private SesDataCapBean sesDataCapBean=null;
    
    public IPItemBean getIpByStr(String ip) {
    	for (IPItemBean ipInfo : ips) {
    		if (ipInfo.getIp().equals(ip)) return ipInfo;
    	}
    	return null;
    }
    
	public HashSet<IPItemBean> getIps() {
		return ips;
	}
	public void setIps(HashSet<IPItemBean> ips) {
		this.ips = ips;
	}
	public HashSet<IPItemBean> getLiveIps() {
		return liveIps;
	}
	public void setLiveIps(HashSet<IPItemBean> liveIps) {
		this.liveIps = liveIps;
	}
	public SesDataCapBean getSesDataCapBean() {
		return sesDataCapBean;
	}
	public void setSesDataCapBean(SesDataCapBean sesDataCapBean) {
		this.sesDataCapBean = sesDataCapBean;
	}
}
