package com.jubaka.sors.beans.branch;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.jubaka.sors.beans.SesDataCapBean;


public class SubnetBean extends SubnetLightBean implements Serializable {
	List<IPItemBean> ips = new ArrayList<>();    // all captured ips
	List<IPItemBean> liveIps = new ArrayList<>();	// ips that are online
	private static final int BYTE_MASK = 0xFF;
	private int subnetInt;
	

    private SesDataCapBean sesDataCapBean=null;
    
    public IPItemBean getIpByStr(String ip) {
    	for (IPItemBean ipInfo : ips) {
    		if (ipInfo.getIp().equals(ip)) return ipInfo;
    	}
    	return null;
    }
    
	public List<IPItemBean> getIps() {
		return ips;
	}
	public void setIps(List<IPItemBean> ips) {
		this.ips = ips;
	}
	public List<IPItemBean> getLiveIps() {
		return liveIps;
	}
	public void setLiveIps(List<IPItemBean> liveIps) {
		this.liveIps = liveIps;
	}
	public SesDataCapBean getSesDataCapBean() {
		return sesDataCapBean;
	}
	public void setSesDataCapBean(SesDataCapBean sesDataCapBean) {
		this.sesDataCapBean = sesDataCapBean;
	}

	public IPItemBean getIpByName(String name) {
		for (IPItemBean ipBean : ips) {
			if (ipBean.getIp().equals(name))
				return ipBean;
		}
		return null;
	}

	/**
	 * Converts an IP address into an integer
	 */
	private int toInt(InetAddress inetAddress) {
		byte[] address = inetAddress.getAddress();
		int result = 0;
		for (int i = 0; i < address.length; i++) {
			result <<= 8;
			result |= address[i] & BYTE_MASK;
		}
		return result;
	}


	/**
	 * Converts an IP address to a subnet using the provided
	 * mask
	 * @param address The address to convert into a subnet
	 * @return The subnet as an integer
	 */
	private int toSubnet(InetAddress address) {
		return toInt(address) & subnetMask;
	}

	/**
	 * Checks if the {@link InetAddress} is within this subnet
	 * @param address The {@link InetAddress} to check
	 * @return True if the address is within this subnet, false otherwise
	 */
	public boolean inSubnet(InetAddress address) {
		this.subnetInt = toInt(subnet);
		return toSubnet(address) == subnetInt;
	}

	public void addIPmanualy(IPItemBean ip) {

		if (ip.getActiveCount()>0) liveIps.add(ip);
		ips.add(ip);

		dataReceive = dataReceive+ip.getDataDown();
		dataSend = dataSend+ip.getDataUp();

		activeInSesCnt += ip.getInputActiveCount();
		activeOutSesCnt += ip.getOutputActiveCount();

		inSesCnt += ip.getInputCount();
		outSesCnt += ip.getOutputCount();

		activeSesCnt += ip.getActiveCount();
		if (ip.getActiveCount()>0) activeAddrCnt++;
		sesCnt += (ip.getActiveCount() + ip.getSavedCount());
		addrCnt++;
	}
	public void deleteIP(IPItemBean ip){
		if (ips.contains(ip)) {
			liveIps.remove(ip);
			ips.remove(ip);

			dataReceive = dataReceive-ip.getDataDown();
			dataSend = dataSend-ip.getDataUp();

			activeInSesCnt -= ip.getInputActiveCount();
			activeOutSesCnt -= ip.getOutputActiveCount();

			inSesCnt -= ip.getInputCount();
			outSesCnt -= ip.getOutputCount();

			activeSesCnt -= ip.getActiveCount();
			if (ip.getActiveCount()>0) activeAddrCnt--;
			sesCnt -= (ip.getActiveCount() + ip.getSavedCount());
			addrCnt--;

		}

	}
}
