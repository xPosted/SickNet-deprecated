package com.jubaka.sors.beans.branch;

import com.jubaka.sors.protocol.http.HTTP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionBean extends SessionLightBean implements Serializable {

	public List<HTTP> getHttpBuf() {
		return httpBuf;
	}
	public void setHttpBuf(List<HTTP> httpBuf) {
		this.httpBuf = httpBuf;
	}


	private List<HTTP> httpBuf = null;
	
}
