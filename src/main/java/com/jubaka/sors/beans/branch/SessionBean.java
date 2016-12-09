package com.jubaka.sors.beans.branch;

import com.jubaka.sors.protocol.http.HTTP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class SessionBean extends SessionLightBean implements Serializable {

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

	private TreeMap<Long, Integer> srcDataTimeBinding = null;
	private TreeMap<Long, Integer> dstDataTimeBinding = null;
	private List<HTTP> httpBuf = null;
	
}
