package com.jubaka.sors.appserver.beans;

import java.io.Serializable;
import java.util.HashSet;

public class SesDataCapBean extends Bean implements Serializable {
	private String object = "address_not_found";
	private HashSet<Integer> inPort = new HashSet<Integer>();
	private HashSet<Integer> outPort = new HashSet<Integer>();
	
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public HashSet<Integer> getInPort() {
		return inPort;
	}
	public void setInPort(HashSet<Integer> inPort) {
		this.inPort = inPort;
	}
	public HashSet<Integer> getOutPort() {
		return outPort;
	}
	public void setOutPort(HashSet<Integer> outPort) {
		this.outPort = outPort;
	}
	
	
}
