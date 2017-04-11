package com.jubaka.sors.beans.branch;

import com.jubaka.sors.desktop.protocol.application.HTTP;
import com.jubaka.sors.desktop.protocol.tcp.TCP;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

public class SessionBean extends SessionLightBean implements Serializable {

	private TreeMap<Long, Integer> srcDataTimeBinding = null;
	private TreeMap<Long, Integer> dstDataTimeBinding = null;

	private List<HTTP> httpBuf = null;
	private List<TCP> tcpBuf = null;

	public List<HTTP> getHttpBuf() {
		return httpBuf;
	}
	public void setHttpBuf(List<HTTP> httpBuf) {
		this.httpBuf = httpBuf;
	}

	public TreeMap<Long, Integer> getSrcDataTimeBinding() {
		return srcDataTimeBinding;
	}

	public void setSrcDataTimeBinding(TreeMap<Long, Integer> srcDataTimeBinding) {
		this.srcDataTimeBinding = srcDataTimeBinding;
	}

	public TreeMap<Long, Integer> getDstDataTimeBinding() {
		return dstDataTimeBinding;
	}

	public void setDstDataTimeBinding(TreeMap<Long, Integer> dstDataTimeBinding) {
		this.dstDataTimeBinding = dstDataTimeBinding;
	}

	public List<TCP> getTcpBuf() {
		return tcpBuf;
	}

	public void setTcpBuf(List<TCP> tcpBuf) {
		this.tcpBuf = tcpBuf;
	}


	
}
