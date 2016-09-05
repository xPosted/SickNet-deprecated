package com.jubaka.sors.sessions;

import com.jubaka.sors.factories.ClassFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class SimpleDataSaver implements Serializable,DataSaver {

//	private Integer srclimitation = 0;
//	private Integer dstlimitation = 0;

	private Long srcDataLen = (long) 0;

	private Long dstDataLen = (long) 0;
	BufferedOutputStream bosSrc =null;
	BufferedOutputStream bosDst =null;
	private String path = "";
	private Session sInst = null;
	private InetAddress srcIP;
	private InetAddress dstIP;
	private String srcDataFileName;
	private String dstDataFileName;
	
	private Integer id;
	
	private TreeMap<Long, Integer> srcDataTimeBinding = new TreeMap<Long, Integer>();
	private TreeMap<Long, Integer> dstDataTimeBinding = new TreeMap<Long, Integer>();
	
	public SimpleDataSaver(Integer id, Session sesInst)  {
		this.id=id;
		sInst= sesInst;
		srcIP = sInst.getSrcIP().getAddr();
		dstIP = sInst.getDstIP().getAddr();
		
		try {

			path= ClassFactory.getInstance().getRawDataPath(id);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("simple data sever created");
		try {
			Date initDate = sInst.getEstablished();
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM_HH:mm:ss");
	
			srcDataFileName = initDate.getTime()+"_"+sdf.format(initDate)+"_"+srcIP.getHostAddress()+"."+sInst.getSrcP()+"-"+dstIP.getHostAddress()+"."+sInst.getDstP()+"_src";
			dstDataFileName = initDate.getTime()+"_"+sdf.format(initDate)+"_"+srcIP.getHostAddress()+"."+sInst.getSrcP()+"-"+dstIP.getHostAddress()+"."+sInst.getDstP()+"_dst";
			File srcData = new File(path+"/"+srcDataFileName);
			File dstData = new File(path+"/"+dstDataFileName);
			
			try {
				dstData.createNewFile();
				srcData.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bosSrc= new BufferedOutputStream(new FileOutputStream(srcData));
			bosDst= new BufferedOutputStream(new FileOutputStream(dstData));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

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
	
	protected void incrementSrcData(Integer len, long time) {
		bindSrcData(len,time);
		srcDataLen = srcDataLen + len;

	}
	

	public void incrementDstData(Integer len,long time) {
		bindDstData(len, time);
		dstDataLen = dstDataLen + len;
	}
	
	public void writeSrcData(byte[] buf) {
		try {
			bosSrc.write(buf);
			bosSrc.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeDstData(byte[] buf) {
		try {
			bosDst.write(buf);
			bosDst.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPath(String newPath) {
		path= newPath;
		
	}

	@Override
	public void putData(byte[] databuf, boolean bySrc,long time) {
		if (bySrc) {
			incrementSrcData(databuf.length,time);
			if (databuf.length != 0)
				writeSrcData(databuf);
		} else {
			incrementDstData(databuf.length,time);
			if (databuf.length != 0)
				writeDstData(databuf);
		}

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
	public byte[] getSrcData() {
		// TODO Auto-generated method stub
		
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path+"/"+srcDataFileName));
			byte[] resBytes = new byte[bis.available()];
			bis.read(resBytes);
			return resBytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void closeConnection() {
		try {
			bosSrc.flush();
			bosDst.flush();
		bosDst.close();
		bosSrc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] getDstData() {
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path+File.separator+dstDataFileName));
			byte[] resBytes = new byte[bis.available()];
			bis.read(resBytes);
			return resBytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public TreeMap<Long, Integer> getSrcTimeBinding() {
		
		return srcDataTimeBinding;
	}

	@Override
	public TreeMap<Long, Integer> getDstTimeBinding() {
		// TODO Auto-generated method stub
		return dstDataTimeBinding;
	}
	
	public InputStream getSrcDataAsStream() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path+File.separator+srcDataFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fis;
		
	}
	
	public InputStream getDstDataAsStream() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path+File.separator+dstDataFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fis;
	}
	
	public String getSrcDataAsFile() {
		return path+File.separator+srcDataFileName;
	}
	
	public String getDstDataAsFile() {
		return path+File.separator+dstDataFileName;
	}
	

}
