package com.jubaka.sors.desktop.sessions;

import java.io.Serializable;
import java.util.ArrayList;

public class Data_bean implements Serializable {
	Integer liveSes;
	Integer liveAddr;
	Integer AddrCount;
	Integer SesCount; 
	long down; 
	long up; 
	
	ArrayList<String> allips = new ArrayList<String>();
	ArrayList<String> liveips = new ArrayList<String>();
	
	
	
	
	public Data_bean() {
		
	}
	
	public void addLiveStr(String str) {
		liveips.add(str);
	}
	
	public void addIpStr(String str) {
		allips.add(str);
	}
	
	
	
	public ArrayList<String> getLiveData() {
		return (ArrayList<String>) liveips.clone();
	}
	
	public ArrayList<String> getipData() {
		return (ArrayList<String>) allips.clone();
	}

	public synchronized Integer getLiveSes() {
		return liveSes;
	}

	public synchronized void setLiveSes(Integer liveSes) {
		this.liveSes = liveSes;
	}

	public synchronized Integer getLiveAddr() {
		return liveAddr;
	}

	public synchronized void setLiveAddr(Integer liveAddr) {
		this.liveAddr = liveAddr;
	}

	public synchronized Integer getAddrCount() {
		return AddrCount;
	}

	public synchronized void setAddrCount(Integer addrCount) {
		AddrCount = addrCount;
	}

	public synchronized Integer getSesCount() {
		return SesCount;
	}

	public synchronized void setSesCount(Integer sesCount) {
		SesCount = sesCount;
	}

	public synchronized Long getDown() {
		return down;
	}

	public synchronized void setDown(Long down) {
		this.down = down;
	}

	public synchronized Long getUp() {
		return up;
	}

	public synchronized void setUp(Long up) {
		this.up = up;
	}
	

}
