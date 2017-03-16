package com.jubaka.sors.sessions;

import java.util.HashSet;

public class GlobalFilterCollector {
	private Integer brID=null;
	private HashSet<GlobalFilter> collector = new HashSet<GlobalFilter>();
	
	public GlobalFilterCollector(Integer brID) {
		this.brID=brID;
	} 

	public void addFilter(GlobalFilter filter) {
		collector.add(filter);
		
	}
	public boolean checkSession(Session s) {
		if (collector.size()==0) return true;
		for (GlobalFilter f : collector) {
			if (f.checkSession(s)) return true;
		}
		return false;
	}
}
