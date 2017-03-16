package com.jubaka.sors.tcpAnalyse;

import com.jubaka.sors.sessions.Session;

import java.util.HashSet;

public class SessionFilter {
	private HashSet<Integer> srcPort;
	private HashSet<Integer> dstPort;
	private HashSet<String> srcIP;
	private HashSet<String> dstIP;

	public SessionFilter() {
		cleanFilter();

	}

	public void cleanFilter() {
		
		 setSrcPortFilter("*");
		 setDstPortFilter("*");
		 setSrcIpFilter("*");
		 setDstIpFilter("*");
		 
		
	}

	public void setSrcPortFilter(String f) {
		f=f.trim();
		srcPort = new HashSet<Integer>();
		if (f.equals("*")) {srcPort.add(-1); return;}
		String[] fplit = f.split(":");
		for (Integer i = 0; i < fplit.length; i++) {
			if (fplit[i].equals("")) continue;
			srcPort.add(Integer.valueOf(fplit[i]));

		}
		
	}

	public void setDstPortFilter(String f) {
		f=f.trim();
		dstPort = new HashSet<Integer>();
		if (f.equals("*")) {dstPort.add(-1); return;}
		String[] fplit = f.split(":");
		for (Integer i = 0; i < fplit.length; i++) {
			if (fplit[i].equals("")) continue;
			dstPort.add(Integer.valueOf(fplit[i]));

		}

	}

	public void setSrcIpFilter(String f) {
		f=f.trim();
		srcIP = new HashSet<String>();
		if (f.equals("*"))  {srcIP.add("*"); return;}
		String[] fplit = f.split(":");
		for (Integer i = 0; i < fplit.length; i++) {
			if (fplit[i].equals("")) continue;
			srcIP.add((fplit[i]));

		}

	}

	public void setDstIpFilter(String f) {
		f=f.trim();
		dstIP = new HashSet<String>();
		if (f.equals("*")) {dstIP.add("*"); return;}
		String[] fplit = f.split(":");
		for (Integer i = 0; i < fplit.length; i++) {
			if (fplit[i].equals("")) continue;
			dstIP.add((fplit[i]));

		}

	}

	public String getSrcPortFilter() {
		if (srcPort.contains(-1)) return "*";
		String res = new String();
		for (Integer port : srcPort) {
			res = res + ":" + port;
		}
		return res;

	}

	public String getDstPortFilter() {
		if (dstPort.contains(-1)) return "*";
		String res = new String();
		for (Integer port : dstPort) {
			res = res + ":" + port;
		}
		return res;

	}

	public String getSrcIpFilter() {
		if (srcIP.contains("*")) return "*";
		String res = new String();
		for (String ip : srcIP) {
			res = res + ":" + ip;
		}
		return res;
	}

	public String getDstIpFilter() {
		if (dstIP.contains("*")) return "*";
		String res = new String();
		for (String ip : dstIP) {
			res = res + ":" + ip;
		}
		return res;

	}

	public boolean checkSession(Session s) {
		if (!srcPort.contains(-1))
			if (!srcPort.contains(s.getSrcP()))
					return false;
		if (!dstPort.contains(-1))
			if (!dstPort.contains(s.getDstP()))
			return false;
		if (!srcIP.contains("*"))
			if (!srcIP.contains(s.getSrcIP().getAddr().getHostAddress()))
			return false;
		if (!dstIP.contains("*"))
			if (!dstIP.contains(s.getDstIP().getAddr().getHostAddress()))
			return false;
		return true;
	}

}
