package com.jubaka.sors.appserver.beans;

import java.io.Serializable;
import java.util.HashSet;

public class DirectoryBean extends Bean implements Serializable {
	private String name=null;
	private DirectoryBean parent = null;
	private HashSet<DirectoryBean> dirs = new HashSet<DirectoryBean>();
	private HashSet<FileBean> files = new HashSet<FileBean>();
	private Long size=null;
	private String fullPath=null;
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
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public void  addDir(DirectoryBean dBean) {
		dirs.add(dBean);
	}
	
	public void addFile(FileBean fBean) {
		files.add(fBean);
	}
	public Integer getDirCount() {
		return dirs.size();
	}
	public Integer getFileCount() {
		return files.size();
	}
	public HashSet<DirectoryBean> getDirs() {
		return dirs;
	}
	public void setDirs(HashSet<DirectoryBean> dirs) {
		this.dirs = dirs;
	}
	public HashSet<FileBean> getFiles() {
		return files;
	}
	public void setFiles(HashSet<FileBean> files) {
		this.files = files;
	}
	public DirectoryBean getParent() {
		return parent;
	}
	public void setParent(DirectoryBean parent) {
		this.parent = parent;
	}
	
	
	

}
