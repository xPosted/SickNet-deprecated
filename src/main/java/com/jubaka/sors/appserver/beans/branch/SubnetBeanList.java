package com.jubaka.sors.beans.branch;

import com.jubaka.sors.beans.Bean;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SubnetBeanList extends Bean implements Serializable {
	private Integer brId;
	private Set<SubnetBean> nets = null;
	public Integer getBrId() {
		return brId;
	}
	public void setBrId(Integer brId) {
		this.brId = brId;
	}
	public Set<SubnetBean> getNets() {
		return nets;
	}
	public void setNets(Set<SubnetBean> nets) {
		this.nets = nets;
	}
	
	


}
