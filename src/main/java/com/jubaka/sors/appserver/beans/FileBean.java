package com.jubaka.sors.appserver.beans;

import com.jubaka.sors.appserver.beans.branch.SessionBean;

import java.io.Serializable;
import java.util.Date;

public class FileBean extends Bean implements Serializable {
	
	private String name;
	private String fullPath;
	private Long size=null;
	private SessionBean session =  null;
	private boolean transmittedBySrc = false;
	private Date lastModify;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public SessionBean getSession() {
		return session;
	}
	public void setSession(SessionBean session) {
		this.session = session;
	}
	public Date getLastModify() {
		return lastModify;
	}
	public void setLastModify(Long lastModify) {
		
		this.lastModify = new Date(lastModify);
	}
	public boolean isTransmittedBySrc() {
		return transmittedBySrc;
	}
	public void setTransmittedBySrc(boolean transmittedBySrc) {
		this.transmittedBySrc = transmittedBySrc;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

}
