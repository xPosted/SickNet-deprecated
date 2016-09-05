package com.jubaka.sors.beans.branch;

import com.jubaka.sors.beans.Bean;

import java.io.Serializable;

public class SessionDataBean extends Bean implements Serializable {
	Long tm;
	byte[] srcData;
	byte[] dstData;
	public Long getTm() {
		return tm;
	}
	public void setTm(Long tm) {
		this.tm = tm;
	}
	public byte[] getSrcData() {
		return srcData;
	}
	public void setSrcData(byte[] srcData) {
		this.srcData = srcData;
	}
	public byte[] getDstData() {
		return dstData;
	}
	public void setDstData(byte[] dstData) {
		this.dstData = dstData;
	}
	

}
