package com.jubaka.sors.appserver.beans;

import java.io.Serializable;
import java.util.HashMap;

public class SecPolicyBean extends Bean implements Serializable {
	private HashMap<String,SecPolicy> ipPolicy = new HashMap<String, SecPolicy>();
	private HashMap<String,SecPolicy> userPolicy = new HashMap<String, SecPolicy>();
	
	
	
	public HashMap<String, SecPolicy> getIpPolicy() {
		return ipPolicy;
	}
	public void setIpPolicy(HashMap<String, SecPolicy> ipPolicy) {
		this.ipPolicy = ipPolicy;
	}
	public HashMap<String, SecPolicy> getUserPolicy() {
		return userPolicy;
	}
	public void setUserPolicy(HashMap<String, SecPolicy> userPolicy) {
		this.userPolicy = userPolicy;
	}
}
