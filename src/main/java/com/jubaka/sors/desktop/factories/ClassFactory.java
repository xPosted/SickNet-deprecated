package com.jubaka.sors.factories;

import com.jubaka.sors.appserver.beans.SecPolicy;
import com.jubaka.sors.limfo.LoadInfo;
import com.jubaka.sors.limfo.LoadLimits;
import com.jubaka.sors.sessions.API;
import com.jubaka.sors.sessions.Branch;
import com.jubaka.sors.sessions.DataSaverInfo;
import com.jubaka.sors.sessions.SessionsAPI;
import com.jubaka.sors.tcpAnalyse.Controller;
import org.jnetpcap.PcapIf;

import java.io.File;
import java.io.Serializable;
import java.util.*;


public class ClassFactory extends Observable implements Serializable {

	private Integer id = 0;
	private Integer remDumpCount=0;
	private long receivedDumpSize=0;
	private String pathToForemost;
	private String homeLive = "Live";
	private String homeRemote = "Remote";

	/**
	 * 
	 * 
	 */
	static ClassFactory instance = null;
	static ClassFactory standaloneInstance = null;
	private boolean alowRemote = false;
	private LoadLimits ll;
	private HashMap<Integer, Controller> cntrList = new HashMap<Integer, Controller>();
	private HashMap<Integer, API> apiList = new HashMap<Integer, API>();
	private HashMap<Integer, SessionsAPI> sesApiList = new HashMap<Integer, SessionsAPI>();
	private HashMap<Integer, Branch> branchList = new HashMap<Integer, Branch>();
	private HashMap<Integer, DataSaverInfo> dsList = new HashMap<Integer, DataSaverInfo>();
	private String configPath = "/usr/local/etc/sors/sors.cfg";


	private ClassFactory(String home, boolean singleton) {
		standaloneInstance = this;
		if (singleton) instance = this;
		try {
			ll = new LoadLimits(home,-1,null,-1,new HashSet<>(),new HashSet<>());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private ClassFactory() {

			LoadInfo li = new LoadInfo();
			String osarch = li.getOsArch();
			ConfigIO rc;
			if (osarch.contains("Linux")) {
				File cfg = new File(configPath);
				rc = new ConfigIO(cfg);
				ll = rc.read();
				if (ll==null) return;
				checkDirsExist();
			}
			instance = this;
			setPathToForemost(ll.getPathToForemost());
			// Thread th = new Thread(new memViewer()); curent memory loading
			// th.start();
		

	}

	public static ClassFactory getStandaloneInstance(String home,boolean singleton) {
		if (standaloneInstance == null) {
			standaloneInstance = new ClassFactory(home,singleton);
		}
		return standaloneInstance;
	}

	public static ClassFactory getInstance() {
		if (instance == null) {
			instance = new ClassFactory();
		//	instance.init();
			// if (instance.getController(0)==null) System.out.println("NULL");
		}
		return instance;
	}

	private void init() {

		try {
			Controller cntr = new Controller();

			apiList.put(id,new API(id));
			sesApiList.put(id,new SessionsAPI(id));
			cntrList.put(id,cntr);

			cntr.init(id, true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveConfig() {
		File cfg = new File(configPath);
		ConfigIO cio = new ConfigIO(cfg);
		cio.saveLimits(ll);
	}
	
	public String getHome() {
		return ll.getHome();
	}
	public String getHomeLive() {
		String home = ll.getHome();
		if (home==null) return null;
		if (home.endsWith("/"))
			return home + homeLive;
		else
			return home + "/" + homeLive;
	}
	
	public String getRawDataPath(Integer id) {
		String resPath = getBranchPath(id)+"rawData/";
		File file = new File(resPath);
		file.mkdirs();
			return resPath;
		
	}
	public String getRecoveredDataPath(Integer id) {
		String resPath = getBranchPath(id)+"recoveredData/";
		File file = new File(resPath);
		if (file.exists()==false)
			file.mkdirs();
		return resPath;
		
	}
	
	
	public String getBranchPath(Integer id) {
		Branch br =  getBranch(id);
		String resPath;
		if (br.getUserName().equals("local")) {
			if (getHomeLive()==null) return null;
			resPath = getHomeLive()+"/"+id+"/";
			File hLive = new File(resPath);
			hLive.mkdirs();
			return resPath;
		} else {
			if (getHomeRemote()==null) return null;
			resPath = getHomeRemote()+"/"+id+"/";
			File hRemote = new File(resPath);
			hRemote.mkdirs();
			
			return resPath;
		}
	}

	public String getHomeRemote() {
		String home = ll.getHome();
		if (home==null) return null;
		String resPath;
		if (home.endsWith("/"))
			resPath = home + homeRemote;
		else
			resPath = home + "/" + homeRemote;
		return resPath;
	}

	public Integer serializeBranch(Integer id) {

		return -1;
	}

	public Integer createLocalBranch(String name,String iface, String path_toDump) {
	

		try {
			Branch b = new Branch(id,name, "localhost", path_toDump, iface);
			branchList.put(id,b);
			
			Controller cntr = new Controller();
			apiList.put(id,new API(id));
			sesApiList.put(id,new SessionsAPI(id));
			cntrList.put(id,cntr);
			dsList.put(id,new DataSaverInfo(id));
			cntr.init(id, true);
			
			setChanged();
			notifyObservers(b);
			
			return id++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -3;
		}
		

	}

	public Integer createBranch(String owner, String name,String path_toDump, String ip,String ifs) {
		
		try {
			Controller cntr = new Controller();

			apiList.put(id,new API(id));
			sesApiList.put(id,new SessionsAPI(id));
			cntrList.put(id,cntr);
			dsList.put(id,new DataSaverInfo(id));
			
			cntr.init(id, false);
			Branch b = new Branch(id,name, ip, path_toDump, ifs);
			b.setUserName(owner);
			branchList.put(id,b);
		//	getAPIinstance(id).startCapture(null, null, path_toDump);

			setChanged();
			notifyObservers(b);
			receivedDumpSize+=b.getDumpLen();
			remDumpCount++;
			return id++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -3;
		}

	}

	public SessionsAPI getSesionInstance(Integer id) {
		return sesApiList.get(id);

	}

	public API getAPIinstance(Integer id) {
		return apiList.get(id);
	}

	public DataSaverInfo getDataSaverInfo(Integer id) {
		return dsList.get(id);
	}
	public Controller getController(Integer id) {
	//	System.out.println("get cntr id = " + id);
		return cntrList.get(id);
	}

	public Branch getBranch(Integer id) {

		return branchList.get(id);
	}

	public Collection<Branch> getBranches() {
		return (Collection<Branch>) branchList.values();
	}


	public void removeFromMem(String ip) {
		for (Integer i = 1; i < apiList.size(); i++) {
			String brIp = branchList.get(i).getWebIP();
			if (brIp.equals(ip)) {
				apiList.remove(i);
				sesApiList.remove(i);
				cntrList.remove(i);
				branchList.remove(i);
				dsList.remove(i);
			}
		}

	}
	public Integer getAllReceivedDumpCount() {
		return remDumpCount;
		
	}
	
	
	public long getAvailableBranchLen() {
		long cds =0;
		Set<Integer> keys = branchList.keySet();
		for (Integer key : keys) {
			cds=cds+branchList.get(key).getDumpLen();
		}
		return cds;
	}
	public Integer getAvailableBranchCnt() {
		return branchList.size();
	}
	public Integer getAvailableBranchCntByUser(String userName) {
		Integer cnt=0;
		Set<Integer> keys = branchList.keySet();
		for (Integer key : keys) {
			Branch br = branchList.get(key);
			if (br.getUserName().equals(userName)) cnt++;
		}
		return cnt;
	}
	public long getAvailableBranchByUserLen(String userName) {
		long len=0;
		Set<Integer> keys = branchList.keySet();
		for (Integer key : keys) {
			Branch br = branchList.get(key);
			if (br.getUserName().equals(userName)) {
				len=len+br.getDumpLen();
			}
		}
		return len;
	}

	public void removeFromMem(Integer id) {
		apiList.remove(id + 1);
		sesApiList.remove(id + 1);
		cntrList.remove(id + 1);
		branchList.remove(id);

	}
	public void deleteBranch(int brId) {
		Branch b = getBranch(brId);
		if (b.getState() == 1) b.stopCapture();
		String brPath =  getBranchPath(brId);
		Controller.deleteDir(new File(brPath));
		removeFromMem(brId);
		
	}

	public Integer getBranchRceivedCount() {
		return remDumpCount;
	}

	public LoadLimits getLimits() {
		return ll;
	}

	public void checkDirsExist() {
		if (ll.getHome()==null) return;
		File f = new File(getHomeLive());
		if (!f.exists())
			f.mkdirs();
		f = new File(getHomeRemote());
		if (!f.exists())
			f.mkdirs();
	}
	public long getTotalReceivedBrLen() {
		return receivedDumpSize;
	}

	public String getPathToForemost() {
		return pathToForemost;
	}

	public void setPathToForemost(String pathToForemost) {
		this.pathToForemost = pathToForemost;
	}
	public List<String> getDeviceList(String byUser) {
		 SecPolicy sc = ll.getPolicyByUser(byUser);
		 List<String> ifss = new ArrayList<String>();
		 if (sc.isDenyLiveCap() == false) {
			List<PcapIf> devices = API.getDeviceList();
			for (PcapIf ifs : devices) {
				ifss.add(ifs.getName());
			}
			
		}
		 return ifss;
	}
}

class memViewer implements Runnable {
	public void run() {
		LoadInfo li = new LoadInfo();

		li.print();

	}
}