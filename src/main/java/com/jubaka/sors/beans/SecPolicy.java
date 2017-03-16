package com.jubaka.sors.beans;

import java.io.Serializable;

public class SecPolicy extends Bean implements Serializable {
	private String myName="*";
	private double dumpSize=300*1024*1024;
	private double homeMax=-1;
	Integer dumpCountLim=-1;
	boolean denyLoad=false;
	boolean denyViewLive=true;
	boolean denyViewThird=true;
	boolean denyDataCap=true;
	boolean denyLiveCap=true;
	
	public SecPolicy(String name) {
		myName=name;
	}

	public double getDumpSize() {
		return dumpSize;
	}

	public void setDumpSize(double dumpSize) {
		this.dumpSize = dumpSize;
	}

	public double getHomeMax() {
		return homeMax;
	}

	public void setHomeMax(double homeMax) {
		this.homeMax = homeMax;
	}

	public boolean isDenyLoad() {
		return denyLoad;
	}

	public void setDenyLoad(boolean denyLoad) {
		this.denyLoad = denyLoad;
	}

	public boolean isDenyViewLive() {
		return denyViewLive;
	}

	public void setDenyViewLive(boolean denyViewLive) {
		this.denyViewLive = denyViewLive;
	}

	public boolean isDenyViewThird() {
		return denyViewThird;
	}

	public void setDenyViewThird(boolean denyViewThird) {
		this.denyViewThird = denyViewThird;
	}

	public boolean isDenyDataCap() {
		return denyDataCap;
	}

	public void setDenyDataCap(boolean denyDataCap) {
		this.denyDataCap = denyDataCap;
	}

	public Integer getDumpCountLim() {
		return dumpCountLim;
	}

	public void setDumpCountLim(Integer dumpCountLim) {
		this.dumpCountLim = dumpCountLim;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public boolean isDenyLiveCap() {
		return denyLiveCap;
	}

	public void setDenyLiveCap(boolean denyLiveCap) {
		this.denyLiveCap = denyLiveCap;
	}
}