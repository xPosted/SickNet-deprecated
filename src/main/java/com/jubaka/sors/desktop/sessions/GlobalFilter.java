package com.jubaka.sors.sessions;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GlobalFilter {
	private static boolean enabled = false;
	private String hfrom = null;
	private String hto = null;
	private Subnet sfrom = null;
	private Subnet sto = null;

	private Integer port = null;

	public GlobalFilter(String from, String to, Integer port) {
		this.hfrom = from;
		this.hto = to;
		this.port=port;
	}

	public GlobalFilter(Subnet from, String to, Integer port) {
		sfrom = from;
		hto = to;
		this.port=port;
	}

	public GlobalFilter(String from, Subnet to, Integer port) {
		hfrom = from;
		sto = to;
		this.port=port;
	}

	public GlobalFilter(Subnet from, Subnet to, Integer port) {
		sfrom = from;
		sto = to;
		this.port=port;
	}

	public boolean checkSession(Session s) {

		if (checkFrom(s)) {
			if (checkTo(s)) {
				if ((s.getDstP()==port) || (s.getSrcP()==port) || (port==null)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkFrom(Session s) {

		if ((hfrom == null) & (sfrom == null))
			return true;
		if (hfrom != null) {
			try {
				InetAddress[] addrs = InetAddress.getAllByName(hfrom);
				for (InetAddress addr : addrs) {
					if ((addr == s.getSrcIP().getAddr()) || (hfrom == null)) {
						return true;
					}
				}
			} catch (UnknownHostException uhe) {
				uhe.printStackTrace();
			}

		}
		if (sfrom != null) {
			if (s.getNetInit() == sfrom) {
				return true;
			}
		}
		return false;
	}

	
	private boolean checkTo(Session s) {

		if ((hto == null) & (sto == null))
			return true;
		if (hto != null) {
			try {
				InetAddress[] addrs = InetAddress.getAllByName(hto);
				for (InetAddress addr : addrs) {
					if ((addr == s.getDstIP().getAddr()) || (hto == null)) {
						return true;
					}
				}
			} catch (UnknownHostException uhe) {
				uhe.printStackTrace();
			}

		}
		if (sto != null) {
			if (s.getNetTarget() == sto) {
				return true;
			}
		}
		return false;
	}
}
