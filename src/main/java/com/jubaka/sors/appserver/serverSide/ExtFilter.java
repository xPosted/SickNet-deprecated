package com.jubaka.sors.serverSide;

import com.jubaka.sors.beans.branch.SessionBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;


public class ExtFilter {
	private HashSet<Integer> srcPort = new HashSet<Integer>();
	private HashSet<Integer> dstPort = new HashSet<Integer>();
	private HashSet<String> srcHost = new HashSet<String>();
	private HashSet<String> dstHost = new HashSet<String>();

	private boolean srcPortType = false;
	private boolean dstPortType = false;
	private boolean srcHostType = false;
	private boolean dstHostType = false;

	public ExtFilter(String srcPortStr, String dstPortStr, String srcHostStr,
			String dstHostStr) {

		if (srcPortStr != null) {
			if ( ! srcPortStr.contains("*")) {
				srcPortStr = srcPortStr.trim();
				if (srcPortStr.startsWith("not_")) {
					srcPortType = false;
					srcPortStr = srcPortStr.substring(4);
				} else srcPortType = true;
				String[] srcPortArr = srcPortStr.split(":");
				for (String srcP : srcPortArr) {
					Integer port = Integer.valueOf(srcP);
					srcPort.add(port);
				}
			}
		}

		if (dstPortStr != null) {
			if ( ! dstPortStr.contains("*")) {
				dstPortStr = dstPortStr.trim();
				if (dstPortStr.startsWith("not_")) {
					dstPortType = false;
					dstPortStr = dstPortStr.substring(4);
				} else dstPortType=true; 
				String[] dstPortArr = dstPortStr.split(":");
				for (String dstP : dstPortArr) {
					Integer port = Integer.valueOf(dstP);
					dstPort.add(port);
				}
			}
		}
		if (srcHostStr != null) {
			if ( ! srcHostStr.contains("*")) {
				srcHostStr = srcHostStr.trim();
				if (srcHostStr.startsWith("not_")) {
					srcHostType = false;
					srcHostStr = srcHostStr.substring(4);
				} else srcHostType = true;
				String[] srcHostArr = srcHostStr.split(":");
				for (String srcH : srcHostArr) {
					try {

						srcH = srcH.trim();
						String host;

						host = srcH;

						InetAddress addr = InetAddress.getByName(host);
						host = addr.getHostAddress();
						srcHost.add(host);
					} catch (UnknownHostException uhe) {
						// do nothing
					}

				}
			}
		}

		if (dstHostStr != null) {
			if ( ! dstHostStr.contains("*")) {
				dstHostStr = dstHostStr.trim();
				if (dstHostStr.startsWith("not_")) {
					dstHostType = false;
					dstHostStr = dstHostStr.substring(4);
				} else dstHostType = true;
				String[] dstHostArr = dstHostStr.split(":");
				for (String dstH : dstHostArr) {
					try {

						dstH = dstH.trim();
						String host;

						host = dstH;

						InetAddress addr = InetAddress.getByName(host);
						host = addr.getHostAddress();
						dstHost.add(host);
					} catch (UnknownHostException uhe) {
						// do nothing
					}

				}
			}
		}
	}

	public boolean checkSession(SessionBean sesBean) {
		if (srcPort.contains(sesBean.getSrcP()) != srcPortType) {
			return false;
		}
		if (dstPort.contains(sesBean.getDstP()) != dstPortType) {
			return false;
		}

		if (srcHost.contains(sesBean.getSrcIP()) != srcHostType) {
			return false;
		}
		if (dstHost.contains(sesBean.getDstIP()) != dstHostType) {
			return false;
		}
		return true;

	}

}
