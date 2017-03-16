package com.jubaka.sors.desktop.sessions;

import java.io.File;
import java.io.InputStream;
import java.util.TreeMap;

public interface DataSaver {
	
	//public RandomAccessFile putData(byte[] databuf, boolean bySrc, long time);
	public Long putSrcData(byte[] databuf, long time);
	public Long putDstData(byte[] databuf, long time);
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
	public String getSrcDataFilePath();
	public String getDstDataFilePath();
	public File getSrcDataFile();
	public File getDstDataFile();
	

}
