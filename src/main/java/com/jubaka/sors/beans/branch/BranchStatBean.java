package com.jubaka.sors.beans.branch;

import com.jubaka.sors.beans.Bean;

import java.io.Serializable;

public class BranchStatBean extends Bean implements Serializable {
	private String byUser=null;
	private Integer branchProcessed =0;
	private double totalBranchLen=0;
	private Integer availableBranchCount=0;
	private double availableBranchLen=0;
	private Integer availableBranchByUser=0;
	private double availableBranchByUserLen=0;
	public String getByUser() {
		return byUser;
	}
	public void setByUser(String byUser) {
		this.byUser = byUser;
	}
	public Integer getBranchProcessed() {
		return branchProcessed;
	}
	public void setBranchProcessed(Integer branchProcessed) {
		this.branchProcessed = branchProcessed;
	}
	public double getTotalBranchLen() {
		return totalBranchLen;
	}
	public void setTotalBranchLen(double totalBranchLen) {
		this.totalBranchLen = totalBranchLen;
	}
	public Integer getAvailableBranchCount() {
		return availableBranchCount;
	}
	public void setAvailableBranchCount(Integer availableBranchCount) {
		this.availableBranchCount = availableBranchCount;
	}
	public double getAvailableBranchLen() {
		return availableBranchLen;
	}
	public void setAvailableBranchLen(double availableBranchLen) {
		this.availableBranchLen = availableBranchLen;
	}
	public Integer getAvailableBranchByUser() {
		return availableBranchByUser;
	}
	public void setAvailableBranchByUser(Integer availableBranchByUser) {
		this.availableBranchByUser = availableBranchByUser;
	}
	public double getAvailableBranchByUserLen() {
		return availableBranchByUserLen;
	}
	public void setAvailableBranchByUserLen(double availableBranchByUserLen) {
		this.availableBranchByUserLen = availableBranchByUserLen;
	}

}
