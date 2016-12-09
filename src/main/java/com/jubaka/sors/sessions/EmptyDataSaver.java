package com.jubaka.sors.sessions;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class EmptyDataSaver implements Serializable,DataSaver {
	private Integer srclimitation = 0;
	private Integer dstlimitation = 0;
	
	private TreeMap<Long, Integer> srcDataTimeBinding = new TreeMap<Long, Integer>();
	private TreeMap<Long, Integer> dstDataTimeBinding = new TreeMap<Long, Integer>();

	private Long srcDataLen = (long) 0;

	private Long dstDataLen = (long) 0;

//	private Session sesOwner;
	
	private void bindSrcData(Integer len,long time) {
		Long lastKey;
		try {
			lastKey = srcDataTimeBinding.lastKey();
		} catch (NoSuchElementException e) {
			lastKey = (long) 0;
		}
		
			if (time - lastKey>1000) {
				srcDataTimeBinding.put(time,  len);
			} else {
				Integer dataCount = srcDataTimeBinding.get(lastKey);
				dataCount+=len;
				srcDataTimeBinding.remove(lastKey);
				srcDataTimeBinding.put(lastKey, dataCount);
			}
	}
	
	private void bindDstData(Integer len,long time) {
		Long lastKey;
		try {
			lastKey = dstDataTimeBinding.lastKey();
		} catch (NoSuchElementException e) {
			lastKey = (long) 0;
		}
		
		
			if (time - lastKey>1000) {
				dstDataTimeBinding.put(time,  len);
			} else {
				Integer dataCount = dstDataTimeBinding.get(lastKey);
				dataCount+=len;
				dstDataTimeBinding.remove(lastKey);
				dstDataTimeBinding.put(lastKey, dataCount);
			}
	}
	
	protected void incrementSrcData(Integer len,long time) {
		bindSrcData(len,time);
		srcDataLen = srcDataLen + len;

	}
	
	public void incrementDstData(Integer len,long time) {
		bindDstData(len, time);
		dstDataLen = dstDataLen + len;
	}

	@Override
	public Long putSrcData(byte[] databuf, long time) {
		incrementSrcData(databuf.length,time);
		return null;
	}

	@Override
	public Long putDstData(byte[] databuf, long time) {
		incrementDstData(databuf.length,time);
		return null;
	}


	@Override
	public byte[] getSrcData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getDstData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getSrcDataLen() {
		// TODO Auto-generated method stub
		return srcDataLen;
	}

	@Override
	public Long getDstDataLen() {
		// TODO Auto-generated method stub
		return dstDataLen;
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPath(String newPath) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public TreeMap<Long, Integer> getSrcTimeBinding() {
		return (TreeMap<Long, Integer>) srcDataTimeBinding.clone();
	}
	@Override
	public TreeMap<Long, Integer> getDstTimeBinding() {
		return (TreeMap<Long, Integer>) dstDataTimeBinding.clone();
	}

	@Override
	public String getSrcDataFilePath() {
		return null;
	}

	@Override
	public String getDstDataFilePath() {
		return null;
	}

	@Override
	public File getSrcDataFile() {
		return null;
	}

	@Override
	public File getDstDataFile() {
		return null;
	}

	@Override
	public InputStream getSrcDataAsStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getDstDataAsStream() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
