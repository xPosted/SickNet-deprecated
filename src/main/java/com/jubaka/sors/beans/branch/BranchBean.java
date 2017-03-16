package com.jubaka.sors.beans.branch;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class BranchBean extends BranchLightBean implements Serializable {

	private List<SubnetBean> subnets = new ArrayList<>();


	public SubnetBean getSubnetByAddr(InetAddress addr) {
		for (SubnetBean sb : subnets) {
			if (sb.getSubnet().equals(addr))
				return sb;
		}
		return null;
	}
	public List<SubnetBean> getSubnets() {
		return subnets;
	}
	public void setSubnets(List<SubnetBean> subnets) {
		this.subnets = subnets;
	}

	public SubnetBean getSubnetByName(String subnet) {
		for (SubnetBean sb : getSubnets()) {
			if (sb.getSubnet().getHostAddress().equals(subnet)) {
				return sb;
			}
		}
		return null;
	}

	public IPItemBean getIPbyName(String ip) {
		IPItemBean ipItemBean = null;
		for (SubnetBean sb : getSubnets()) {
			 ipItemBean = sb.getIpByStr(ip);
			if (ipItemBean != null) return ipItemBean;
		}
		return null;

	}


}
