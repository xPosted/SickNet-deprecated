package com.jubaka.sors.beans.branch;

import com.jubaka.sors.beans.Bean;
import com.jubaka.sors.beans.SesDataCapBean;

import java.io.Serializable;
import java.util.Date;
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
	

}
