package com.jubaka.sors.beans.branch;

import com.jubaka.sors.beans.Bean;

import java.io.Serializable;
import java.util.Date;

public class BranchInfoBean extends Bean implements Serializable {
	private Integer id;
	private Long dbid = null;
	private Long nodeId;
	private String nodeName;
	private String branchName;
	private long uploadSize = 0;
	private Integer reqCount = 0;
	private String webIP = "127.0.0.1";
	private String userName = "local";
	private String fileName=null;
	private String iface=null;
	private Date time;
	private String desc="SORS branch description";
	private int state= 0;
	private Integer subnetCount =0;
	private Integer hostsCount = 0;
	private Integer sessionsCount =0;
	private String recoveredDataPath;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public long getUploadSize() {
		return uploadSize;
	}
	public void setUploadSize(long uploadSize) {
		this.uploadSize = uploadSize;
	}
	public Integer getReqCount() {
		return reqCount;
	}
	public void setReqCount(Integer reqCount) {
		this.reqCount = reqCount;
	}
	public String getWebIP() {
		return webIP;
	}
	public void setWebIP(String webIP) {
		this.webIP = webIP;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getIface() {
		return iface;
	}
	public void setIface(String iface) {
		this.iface = iface;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public Long getDbid() {
		return dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	public Integer getHostsCount() {
		return hostsCount;
	}

	public void setHostsCount(Integer hostsCount) {
		this.hostsCount = hostsCount;
	}

	public Integer getSessionsCount() {
		return sessionsCount;
	}

	public void setSessionsCount(Integer sessionsCount) {
		this.sessionsCount = sessionsCount;
	}

	public Integer getSubnetCount() {
		return subnetCount;
	}

	public void setSubnetCount(Integer subnetCount) {
		this.subnetCount = subnetCount;
	}


	public String getRecoveredDataPath() {
		return recoveredDataPath;
	}

	public void setRecoveredDataPath(String recoveredDataPath) {
		this.recoveredDataPath = recoveredDataPath;
	}
}
