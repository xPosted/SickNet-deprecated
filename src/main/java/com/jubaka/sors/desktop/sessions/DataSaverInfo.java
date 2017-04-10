package com.jubaka.sors.desktop.sessions;

import java.net.InetAddress;
import java.util.HashMap;

public  class DataSaverInfo {
	HashMap<InetAddress, SesCaptureInfo> dataCatchList = new HashMap<InetAddress, SesCaptureInfo>();
	HashMap<Subnet, SesCaptureInfo> subnetDataCatchList = new HashMap<Subnet, SesCaptureInfo>();
	
	
	boolean catchAll=true;
	boolean catchNothing = false;
	static DataSaverInfo instance = null;
	private Integer id;
	
	
	public DataSaverInfo(Integer id) {
		this.id=id;
	}
	public boolean isCatchNothing() {
		return catchNothing;
	}
	public boolean isCatchAll() {
		return catchAll;
	}
	public void catchAll() {
		catchNothing=false;
		catchAll=true;
	}
	
	public void catchSelcted() {
		catchNothing=false;
		catchAll=false;
	}
	public SesCaptureInfo getCatchInfo(Subnet net) {
		return subnetDataCatchList.get(net);
	}
	public SesCaptureInfo getCatchInfo(InetAddress addr) {
		return dataCatchList.get(addr);
	}
	
	public void addItem(InetAddress addr,String in, String out) {
		catchNothing=false;
		dataCatchList.put(addr, new SesCaptureInfo(in, out));
		
	}
	public void addSubnet(Subnet addr,String in, String out) {
		catchNothing=false;
		subnetDataCatchList.put(addr, new SesCaptureInfo(in, out));
	}
	
	public void removeItem(InetAddress addr) {
		dataCatchList.remove(addr);
		
	}
	public void removeItem(Subnet net) {
		subnetDataCatchList.remove(net);
		
	}
	public boolean checkInExist(IPaddr addr, Integer port) {
		
		if (catchAll) return true;
		if (checkInExist(addr.getNet(), port)) return true;
		SesCaptureInfo item = dataCatchList.get(addr.getAddr()); 
		if (item!=null) {
			return item.checkInPort(port);
		}
		return false;
		
	}
	
	public boolean checkOutExist(IPaddr addr, Integer port) {
		
		if (catchAll) return true;
		if (checkOutExist(addr.getNet(), port)) return true;
		SesCaptureInfo item = dataCatchList.get(addr.getAddr()); 
		if (item!=null) {
			return item.checkOutPort(port);
		}
		return false;
	}
	public boolean checkInExist(Subnet net, Integer port) {
		
		if (catchAll) return true;
		SesCaptureInfo item = subnetDataCatchList.get(net); 
		if (item!=null) {
			return item.checkInPort(port);
		}
		return false;
		
	}
	
	public boolean checkOutExist(Subnet net, Integer port) {
		
		if (catchAll) return true;
		SesCaptureInfo item = subnetDataCatchList.get(net); 
		if (item!=null) {
			return item.checkOutPort(port);
		}
		return false;
	}
	
	
	
	
	}
