package com.jubaka.sors.sessions;

import java.io.InputStream;
import java.util.TreeMap;

public interface DataSaver {
	
	public void putData(byte[] databuf,boolean bySrc, long time);
	public Long getSrcDataLen();
	public Long getDstDataLen();
	public byte [] getSrcData();
	public byte [] getDstData();
	public InputStream getSrcDataAsStream();
	public InputStream getDstDataAsStream();
	public void closeConnection();
	public void setPath(String newPath);
	public TreeMap<Long,Integer> getSrcTimeBinding();
	public TreeMap<Long, Integer> getDstTimeBinding();
	public String getSrcDataAsFile();
	public String getDstDataAsFile();
	

}
