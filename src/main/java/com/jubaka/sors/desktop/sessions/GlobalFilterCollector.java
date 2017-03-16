package com.jubaka.sors.desktop.sessions;

import java.util.HashSet;

public class GlobalFilterCollector {
	private Branch br=null;
	private HashSet<GlobalFilter> collector = new HashSet<GlobalFilter>();
	
	public GlobalFilterCollector(Branch br) {
		this.br=br;
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
