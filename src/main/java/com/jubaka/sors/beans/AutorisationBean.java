package com.jubaka.sors.beans;

import java.io.Serializable;

public class AutorisationBean extends Bean implements Serializable {
	private String nodeUserName;
	private String nodeUserPass;
	private String nodeName;
	private Long unid;
	
	
	
	public String getNodeUserName() {
		return nodeUserName;
	}
	public void setNodeUserName(String nodeUserName) {
		this.nodeUserName = nodeUserName;
	}
	public String getNodeUserPass() {
		return nodeUserPass;
	}
	public void setNodeUserPass(String nodeUserPass) {
		this.nodeUserPass = nodeUserPass;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public Long getUnid() {
		return unid;
	}
	public void setUnid(Long unid) {
		this.unid = unid;
	}
	

}
