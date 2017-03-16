package com.jubaka.sors.appserver.beans.branch;

import com.jubaka.sors.appserver.beans.SesDataCapBean;

import java.io.Serializable;
import java.util.HashSet;


public class IPItemBean extends IPItemLightBean implements Serializable {

	private SesDataCapBean sdcb;
	private HashSet<SessionBean> activeInSes = new HashSet<SessionBean>();
	private HashSet<SessionBean> activeOutSes = new HashSet<SessionBean>();

	private HashSet<SessionBean> storedInSes = new HashSet<SessionBean>();
	private HashSet<SessionBean> storedOutSes = new HashSet<SessionBean>();
	
	public IPItemBean() {

	}


	public synchronized HashSet<SessionBean> getActiveInSes() {
		return activeInSes;
	}
	public synchronized void setActiveInSes(HashSet<SessionBean> activeInSes) {
		this.activeInSes = activeInSes;
	}
	public synchronized HashSet<SessionBean> getActiveOutSes() {
		return activeOutSes;
	}
	public synchronized void setActiveOutSes(HashSet<SessionBean> activeOutSes) {
		this.activeOutSes = activeOutSes;
	}
	public synchronized HashSet<SessionBean> getStoredInSes() {
		return storedInSes;
	}
	public synchronized void setStoredInSes(HashSet<SessionBean> storedInSes) {
		this.storedInSes = storedInSes;
	}
	public synchronized HashSet<SessionBean> getStoredOutSes() {
		return storedOutSes;
	}
	public synchronized void setStoredOutSes(HashSet<SessionBean> storedOutSes) {
		this.storedOutSes = storedOutSes;
	}

	public SesDataCapBean getDataCapInfo() {
		return sdcb;
	}
	public void setDataCapInfo(SesDataCapBean sdcb) {
		this.sdcb = sdcb;
	}

	public void update(IPItemLightBean bean) {
		setIp(bean.getIp());
		setDnsName(bean.getDnsName());
		setDataDown(bean.getDataDown());
		setDataUp(bean.getDataUp());
		setActivated(bean.getActivated());
		setInputCount(bean.getInputCount());
		setOutputCount(bean.getOutputCount());
		setSavedCount(bean.getSavedCount());
		setActiveCount(bean.getActiveCount());
		setBrId(bean.getBrId());

	}

}
