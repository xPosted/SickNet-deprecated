package com.jubaka.sors.desktop.limfo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jubaka.sors.beans.SecPolicy;
import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.sessions.Branch;
import com.jubaka.sors.desktop.tcpAnalyse.Controller;
import com.jubaka.sors.desktop.tcpAnalyse.MainWin;

public class LoadLimits {
	private String home = null;
	private Integer status = 0;
	private double homeMaxRemote = -1;
	private HashMap<String, SecPolicy> ipPolicy = new HashMap<String, SecPolicy>();
	private HashMap<String, SecPolicy> userPolicy = new HashMap<String, SecPolicy>();
	private String pathToForemost;
	private Long unid = null;
	private byte sessionViewMode = 0;	// 0 panel 1 compaq 2 table
	private boolean separSessionView = false;
	private boolean separSesDataView = false;
	private ClassFactory factory;

	private String nodeName = "Sors_production";
	private String desc = "SORS analyze node";
	

	public LoadLimits(ClassFactory factory ,String home, Integer status, Long unid,
			double homeMaxRemote, Set<SecPolicy> ipPolicies,
			Set<SecPolicy> usersPolicies, String nodeName, String desc) throws Exception {
		this.home = home;
		this.status = status;
		this.unid = unid;
		this.homeMaxRemote = homeMaxRemote;
		this.nodeName = nodeName;
		this.desc = desc;
		this.factory = factory;

		for (SecPolicy item : ipPolicies)
			addIpPolicy(item);
		for (SecPolicy item : usersPolicies)
			addUserPolicy(item);
		File homeDir = new File(home);

		if (homeDir.list().length != 0) {
			int res = JOptionPane.showConfirmDialog(null,
					"Your home directory is not empty, clear it?");
			if (res == 0) {
				for (File item : homeDir.listFiles()) {
					if (item.isDirectory())
						Controller.deleteDir(item);
					if (item.isFile())
						item.delete();
				}
			}
			if (res == 2) {

				JFrame mainFr = MainWin.instance.getFrame();
				mainFr.dispose();
				
					throw new Exception();
				
			}
		}

		File foremost = new File(home + "/foremost");

		if (!foremost.exists()) {
			URL foremost_url = this.getClass().getResource(
					"/foremost_bin/foremost");
			try {
				foremost.createNewFile();
				FileOutputStream fos = new FileOutputStream(foremost);
				byte[] buf = new byte[4096];

				BufferedInputStream bis = new BufferedInputStream(
						foremost_url.openStream());
				int readCount = bis.read(buf);
				while (readCount > 0) {
					fos.write(buf, 0, readCount);
					readCount = bis.read(buf);
				}
				bis.close();
				fos.flush();
				fos.close();
				Runtime.getRuntime().exec("chmod +x " + home + "/foremost");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setPathToForemost(home + "/foremost");
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer st) {
		status = st;
	}

	public void setHomeRemoteMaxSize(double newSize) {
		homeMaxRemote = newSize;

	}
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean checkHomeRemote(double needed) {
		if (homeMaxRemote == -1)
			return true;
		LoadInfo li = new LoadInfo(factory);

		if ((li.getHomeUsedRemote() + needed) > homeMaxRemote) {
			return false;
		}
		return true;

	}

	public boolean checkBranchAvailable(SecPolicy sp, Branch b) {
		if (b.getUserName().equals(sp.getMyName())) {
			return true;
		} else {
			if ((b.getUserName().equals("local")) || (!sp.isDenyViewLive())) {
				return true;
			} else {
				if (!sp.isDenyViewThird()) {
					return true;
				}
			}
		}
		return false;

	}

	public double getHomeRemoteMaxSize() {
		return homeMaxRemote;
	}

	public SecPolicy getPolicyByUser(String name) {
		return userPolicy.get(name);

	}

	public SecPolicy getPolicyByIP(String ip) {
		return ipPolicy.get(ip);
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
		ClassFactory.getInstance().checkDirsExist();

	}

	public void addIpPolicy(SecPolicy item) {
		ipPolicy.put(item.getMyName(), item);
	}

	public void addUserPolicy(SecPolicy item) {
		userPolicy.put(item.getMyName(), item);
	}

	public HashMap<String, SecPolicy> getIpPolicy() {
		return ipPolicy;
	}

	public HashMap<String, SecPolicy> getUserPolicy() {
		return userPolicy;
	}

	public boolean checkCreateBranchPolicy(String user, Long size) {
		ClassFactory cf = ClassFactory.getInstance();
		SecPolicy sc = getPolicyByUser(user);
		LoadLimits ll = ClassFactory.getInstance().getLimits();
		if (ll.getStatus() == -1)
			return false;
		if (!sc.isDenyLoad()) {
			if (sc.getDumpCountLim() > (cf.getAvailableBranchCntByUser(user) + 1)) {
				if (sc.getHomeMax() > (cf.getAvailableBranchByUserLen(user) + size)) {
					if (sc.getDumpSize() > size) {
						if (checkHomeRemote(size)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public String getPathToForemost() {
		return pathToForemost;
	}

	public void setPathToForemost(String pathToForemost) {
		this.pathToForemost = pathToForemost;
	}

	public Long getUnid() {
		return unid;
	}

	public void setUnid(Long unid) {
		this.unid = unid;
	}

	public byte getSessionViewMode() {
		return sessionViewMode;
	}

	public void setSessionViewMode(byte sessionViewMode) {
		this.sessionViewMode = sessionViewMode;
	}

	public boolean isSeparSessionView() {
		return separSessionView;
	}

	public void setSeparSessionView(boolean separSessionView) {
		this.separSessionView = separSessionView;
	}

	public boolean isSeparSesDataView() {
		return separSesDataView;
	}

	public void setSeparSesDataView(boolean separSesDataView) {
		this.separSesDataView = separSesDataView;
	}

}
