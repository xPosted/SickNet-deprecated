package com.jubaka.sors.appserver.beans;

import java.io.Serializable;


public class FileListBean extends Bean implements Serializable{
	
	Integer brId=null;
	String branchName =null;
	Long filter=null;
	DirectoryBean mainDir=null;
	Long size =(long) 0;
	java.util.Date date = null;
	Integer fileCount = 0;
	
	// set<dirBean>
	// dirBean > map file - SessionBean
	public Integer getBrId() {
		return brId;
	}
	public void setBrId(Integer brId) {
		this.brId = brId;
	}
	public Long getFilter() {
		return filter;
	}
	public void setFilter(Long filter) {
		this.filter = filter;
	}
	public DirectoryBean getMainDir() {
		return mainDir;
	}
	public void setMainDir(DirectoryBean mainDir) {
		this.mainDir = mainDir;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public Integer getFileCount() {
		return fileCount;
	}
	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}
	
	
}
