package com.jubaka.sors.appserver.beans.branch;

import com.jubaka.sors.appserver.beans.Bean;

import java.io.Serializable;
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
