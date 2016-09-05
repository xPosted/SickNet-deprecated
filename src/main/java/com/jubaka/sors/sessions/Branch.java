package com.jubaka.sors.sessions;

import java.io.File;
import java.util.Date;

import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.remote.BeanConstructor;
import com.jubaka.sors.remote.ConnectionHandler;

public class Branch {
	private Integer id;
	private String name;
	private boolean active =false;
	private long dumpLen = 0;
	private Date lastRecovered = null;
	private Integer reqCount = 0;
	private String webIP = "127.0.0.1";
	private String userName = "local";
	private String pathToFile=null;
	private String fileName=null;
	private String iface=null;
	private Date time;
	private String desc="SORS branch description";
	private ClassFactory factory;
	private int state =0;  // 0 created; 1 started; 2 paused; 3 stoped; 

	
	public Branch(Integer id,String name, String ip, String path,String iface) {
		pathToFile=path;
		if (path != null) {
			File dump = new File(path);
			if (dump.exists()) {
				dumpLen = dump.length();
				String[] split = path.split("/");
				fileName = split[split.length - 1];
			}
		}
		this.name=name;
		this.iface=iface;
		this.id = id;
		webIP = ip;
		time = new Date();
		factory = ClassFactory.getInstance();
		if (name==null) {
			if (iface!=null) this.name=iface;
			if (fileName!=null)  this.name=fileName;
		}
	}

	public void startCapture(String pcapExp) {
		state = 1;
		factory.getAPIinstance(id).startCapture(iface, pcapExp, pathToFile);
		active=true;
		
	}
	public void stopCapture() {
		state=-1;
		factory.getAPIinstance(id).breakCapture();
		active=false;
	}
	
	public boolean isDataCapture() {
		factory = ClassFactory.getInstance();
		DataSaverInfo dsi = factory.getDataSaverInfo(id);
		Integer sizeIPcapture = dsi.dataCatchList.size();
		Integer sizeSubnetCapture = dsi.subnetDataCatchList.size();
		if (((sizeIPcapture+sizeSubnetCapture)>0) || dsi.isCatchAll() ) return true;
		return false;
		
	}
	
	public Long getCapturedDataSize() {
		factory = ClassFactory.getInstance();
		String path = factory.getRawDataPath(id);
		File rawPath = new File(path);
		if ( ! rawPath.exists()) return (long)0;
		Long size = BeanConstructor.sizeOf(rawPath);
		return size;
	}
	
	public Long getHomeUsed() {
		factory = ClassFactory.getInstance();
		String pathTo = factory.getBranchPath(id);
		File home = new File(pathTo);
		return BeanConstructor.sizeOf(home);
		
	}
	
	public boolean isActive() {
		return active;
	}
	public String getFileName() {
		return fileName;
	}

	public synchronized Integer getId() {
		return id;
	}

	public synchronized void setId(Integer id) {
		this.id = id;
	}

	public synchronized long getDumpLen() {
		return dumpLen;
	}

	public synchronized Integer getReqCount() {
		return reqCount;
	}

	public synchronized void incrementReqCount() {
		reqCount++;
		;
	}

	public synchronized String getWebIP() {
		return webIP;
	}

	public synchronized Date getTime() {
		return time;
	}

	public synchronized String getUserName() {
		return userName;
	}

	public synchronized void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIface() {
		return iface;
	}

	public void setIface(String iface) {
		this.iface = iface;
	}
	public String getDesc() {
		return desc;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPathToFile() {
		return pathToFile;
	}


	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}


	public Date getLastRecovered() {
		return lastRecovered;
	}


	public void setLastRecovered(Date lastRecovered) {
		this.lastRecovered = lastRecovered;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}
	public boolean isLocal() {
		if (webIP.equals("127.0.0.1")) return true;
		return false;
	}

}
