package com.jubaka.sors.appserver.beans;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;

public class InfoBean extends Bean implements Serializable {
	private String nodeName;
	private String owner;
	private String desc;
	private String osArch;
	private Integer procCount;
	private double maxMem;
	private double usedMem;
	private double availableMem;
	private double homeUsed;
	private Double homeMax;
	private Integer receivedDumpCount;
	private Integer currentDumpCount;
	private double receivedDumpSize;
	private double currentDumpSize;
	private InetAddress pubAddr =null;
	private Integer status=null;
	private Date connectedDate=null;
	private Long unid;

	public Long getUnid() {
		return unid;
	}
	public void setUnid(Long unid) {
		this.unid = unid;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getOsArch() {
		return osArch;
	}
	public void setOsArch(String osArch) {
		this.osArch = osArch;
	}
	public Integer getProcCount() {
		return procCount;
	}
	public void setProcCount(Integer procCount) {
		this.procCount = procCount;
	}
	public double getMaxMem() {
		return maxMem;
	}
	public void setMaxMem(long maxMem) {
		this.maxMem = maxMem;
	}
	public double getUsedMem() {
		return usedMem;
	}
	public void setUsedMem(long usedMem) {
		this.usedMem = usedMem;
	}
	public double getAvailableMem() {
		return availableMem;
	}
	public void setAvailableMem(long availableMem) {
		this.availableMem = availableMem;
	}
	public double getHomeUsed() {
		return homeUsed;
	}
	public void  setHomeUsed(double homeUsed) {
		this.homeUsed = homeUsed;
	}
	public double getHomeMax() {
		return homeMax;
	}
	public void setHomeMax(Double homeMax) {
		this.homeMax = homeMax;
	}
	public Integer getReceivedDumpCount() {
		return receivedDumpCount;
	}
	public void setReceivedDumpCount(Integer receivedDumpCount) {
		this.receivedDumpCount = receivedDumpCount;
	}
	public Integer getCurrentDumpCount() {
		return currentDumpCount;
	}
	public void setCurrentDumpCount(Integer currentDumpCount) {
		this.currentDumpCount = currentDumpCount;
	}
	public double getReceivedDumpSize() {
		return receivedDumpSize;
	}
	public void setReceivedDumpSize(long receivedDumpSize) {
		this.receivedDumpSize = receivedDumpSize;
	}
	public double getCurrentDumpSize() {
		return currentDumpSize;
	}
	public void setCurrentDumpSize(long currentDumpSize) {
		this.currentDumpSize = currentDumpSize;
	}
	public InetAddress getPubAddr() {
		return pubAddr;
	}
	public void setPubAddr(InetAddress pubAddr) {
		this.pubAddr = pubAddr;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getConnectedDate() {
		return connectedDate;
	}
	public void setConnectedDate(Date connectedDate) {
		this.connectedDate = connectedDate;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	

}
