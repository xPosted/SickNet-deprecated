package com.jubaka.sors.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DirectoryBean extends Bean implements Serializable {
	private String name=null;
	private DirectoryBean parent = null;
	private List<DirectoryBean> dirs = new ArrayList<>();
	private List<FileBean> files = new ArrayList<>();
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
	public List<DirectoryBean> getDirs() {
		return dirs;
	}
	public void setDirs(List<DirectoryBean> dirs) {
		this.dirs = dirs;
	}
	public List<FileBean> getFiles() {
		return files;
	}
	public void setFiles(List<FileBean> files) {
		this.files = files;
	}
	public DirectoryBean getParent() {
		return parent;
	}
	public void setParent(DirectoryBean parent) {
		this.parent = parent;
	}
	
	
	

}
