package com.jubaka.sors.beans.branch;

import com.jubaka.sors.beans.Bean;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashSet;

public class BranchBean extends BranchLightBean implements Serializable {

	private HashSet<SubnetBean> subnets = new HashSet<SubnetBean>();

	public SubnetBean getSubnetByAddr(InetAddress addr) {
		for (SubnetBean sb : subnets) {
			if (sb.getSubnet().equals(addr))
				return sb;
		}
		return null;
	}
	public HashSet<SubnetBean> getSubnets() {
		return subnets;
	}
	public void setSubnets(HashSet<SubnetBean> subnets) {
		this.subnets = subnets;
	}
}
