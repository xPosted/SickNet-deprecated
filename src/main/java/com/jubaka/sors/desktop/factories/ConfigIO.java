package com.jubaka.sors.desktop.factories;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.jubaka.sors.beans.SecPolicy;
import com.jubaka.sors.desktop.limfo.LoadInfo;
import com.jubaka.sors.desktop.limfo.LoadLimits;
import com.jubaka.sors.desktop.remote.WebConnection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigIO {
	File cfg = null;

	String home = null;
	Integer status = -1;
	double homeMaxRemote = -1;
	double dumpSizeMax = -1;
	String allowedIP = "*";
	String allowedUsers = "*";
	String desc = "SORS analyze node";
	String nodeName = "Sors_production";
	String userName = null;
	byte sessionViewMode;
	boolean separSessionView;
	boolean separSesDataView;
	
	
	Long unid = null;
	HashSet<SecPolicy> ipPoliciesTmp = new HashSet<SecPolicy>();
	HashSet<SecPolicy> usersPoliciesTmp = new HashSet<SecPolicy>();

	public ConfigIO(File cfg) {
		this.cfg = cfg;

	}

	public void saveLimits(LoadLimits item) {
		PrintWriter pw = null;
		cfg.delete();
		try {

			try {
				String path = cfg.getAbsolutePath();
				path = path.substring(0, path.lastIndexOf("/"));
				File cfgDir = new File(path);
				if (!cfgDir.exists())
					cfgDir.mkdirs();
				cfg.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}

			pw = new PrintWriter(cfg);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		pw.println("<root>");
		home = item.getHome();
		if (home != null) {

			pw.println("<home>");
			pw.println(home);
			pw.println("</home>");

		}

		status = item.getStatus();
		pw.println("<status>");
		pw.println(status);
		pw.println("</status>");

		homeMaxRemote = item.getHomeRemoteMaxSize();
		pw.println("<homeRemoteMax>");
		pw.println(homeMaxRemote);
		pw.println("</homeRemoteMax>");

		desc = LoadInfo.getDesc();
		pw.println("<desc>");
		pw.println(desc);
		pw.println("</desc>");

		nodeName = LoadInfo.getNodeName();
		pw.println("<nodeName>");
		pw.println(nodeName);
		pw.println("</nodeName>");
		
		pw.println("<sessionViewMode>");
		pw.println(item.getSessionViewMode());
		pw.println("</sessionViewMode>");
		
		pw.println("<separSessionView>");
		pw.println(item.isSeparSessionView());
		pw.println("</separSessionView>");
		
		pw.println("<separSesDataView>");
		pw.println(item.isSeparSesDataView());
		pw.println("</separSesDataView>");

		userName = WebConnection.getUserName();
		if (userName != null) {
			pw.println("<userName>");
			pw.println(userName);
			pw.println("</userName>");
		}
		unid=item.getUnid();
		if (unid!=null) {
			pw.println("<unid>");
			pw.println(unid);
			pw.println("</unid>");
		}
		
		saveSecPol(item, pw);

		pw.println("</root>");
		pw.flush();
		pw.close();

	}

	public LoadLimits read() {
		LoadLimits li = null;
		if (!cfg.exists()) {
			JOptionPane.showMessageDialog(null,
					"Config file not found, default settings loaded.");

			ipPoliciesTmp.add(new SecPolicy("*"));
			usersPoliciesTmp.add(new SecPolicy("*"));

			try {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(null);
				chooser.setToolTipText("Select home folder");
				chooser.setName("Select home folder");
				home = chooser.getSelectedFile().getAbsolutePath();
				li = new LoadLimits(home, status,unid, homeMaxRemote, ipPoliciesTmp,
						usersPoliciesTmp);
			} catch (Exception e) {
				return null;
				
			}
			saveLimits(li);
			return li;

		}
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(cfg);
			Node node = doc.getDocumentElement();
			NodeList nList = node.getChildNodes();
			Integer len = nList.getLength();
			for (Integer j = 0; j < len; j++) {

				if (!(nList.item(j) instanceof Element)) {
					continue;
				}
				Element elem = (Element) nList.item(j);
				if (elem.getNodeName().equals("home")) {
					String newHome = elem.getTextContent().trim();
					if (!newHome.equals(""))
						home = newHome;
				}
				if (elem.getNodeName().equals("status")) {
					String str = elem.getTextContent().trim();
					if (!str.equals(""))
						status = Integer.parseInt(str);
					System.out.println("status: " + status);

				}
				if (elem.getNodeName().equals("userName")) {
					String str = elem.getTextContent().trim();
					if (!str.equals(""))
						userName = str;
					System.out.println("userOwner: " + userName);

				}
				if (elem.getNodeName().equals("homeRemoteMax")) {
					String str = elem.getTextContent().trim();
					if (!str.equals(""))
						homeMaxRemote = Double.parseDouble(str);
					// homeMaxRemote = readSize(str);
				}
				if (elem.getNodeName().equals("dump_max")) {
					String str = elem.getTextContent().trim();
					if (!str.equals(""))
						dumpSizeMax = Double.parseDouble(str);
					// dumpSizeMax = readSize(str);
				}
				if (elem.getNodeName().equals("allowed_ip")) {
					String newAllowedIPs = elem.getTextContent().trim();
					if (!newAllowedIPs.equals(""))
						allowedIP = newAllowedIPs;
					System.out.println("allowed_ip: " + allowedIP);
				}
				if (elem.getNodeName().equals("allowed_users")) {
					String newAllowedUsers = elem.getTextContent().trim();
					if (!allowedUsers.equals(""))
						allowedUsers = newAllowedUsers;
					System.out.println("allowed_users: " + allowedUsers);
				}
				if (elem.getNodeName().equals("desc")) {
					String newDesc = elem.getTextContent().trim();
					if (!newDesc.equals(""))
						desc = newDesc;
					System.out.println("deslic class ReadConfig {c: " + desc);
					LoadInfo.setDesc(desc);
				}
				if (elem.getNodeName().equals("nodeName")) {
					String newNodeName = elem.getTextContent().trim();
					if (!newNodeName.equals(""))
						nodeName = newNodeName;

					LoadInfo.setNodeName(nodeName);
				}
				if (elem.getNodeName().equals("sessionViewMode")) {
					String sessionViewModeStr = elem.getTextContent().trim();
					if (sessionViewModeStr.equals("")) continue;
					sessionViewMode = Byte.parseByte(sessionViewModeStr);
					
				}
				if (elem.getNodeName().equals("separSessionView")) {
					String separSessionViewStr = elem.getTextContent().trim();
					if (separSessionViewStr.equals("")) continue;
					separSessionView = Boolean.parseBoolean(separSessionViewStr);
					
				}
				if (elem.getNodeName().equals("separSesDataView")) {
					String separSesDataViewStr = elem.getTextContent().trim();
					if (separSesDataViewStr.equals("")) continue;
					separSesDataView = Boolean.parseBoolean(separSesDataViewStr);
					
				}
				
				
				if (elem.getNodeName().equals("unid")) {
					String unidStr = elem.getTextContent().trim();
					if (!unidStr.equals(""))
						unid = Long.parseLong(unidStr);
				}

				if (elem.getNodeName().equals("SecPolicy")) {
					handleSecPol(elem);

				}

			}
			li = new LoadLimits(home, status,unid, homeMaxRemote, ipPoliciesTmp,
					usersPoliciesTmp);
			li.setSessionViewMode(sessionViewMode);
			li.setSeparSesDataView(separSesDataView);
			li.setSeparSessionView(separSessionView);
			
			if (userName!=null)
				WebConnection.setUserName(userName);
		} catch ( IOException | ParserConfigurationException | SAXException  e) {
			JOptionPane.showMessageDialog(null,
					"Config file error, resave your "
							+ "configurations!/nDefault settings loaded.");

			e.printStackTrace();
			ipPoliciesTmp.add(new SecPolicy("*"));
			usersPoliciesTmp.add(new SecPolicy("*"));
			try {
				li = new LoadLimits(home, status,unid, homeMaxRemote, ipPoliciesTmp,
						usersPoliciesTmp);
			} catch (Exception e1) {
				
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		return li;

	}

	private void saveSecPol(LoadLimits li, PrintWriter pw) {
		pw.println("<SecPolicy>");
		Collection<SecPolicy> ipPolicy = li.getIpPolicy().values();
		for (SecPolicy secPolItem : ipPolicy) {
			pw.println("<IP name=\"" + secPolItem.getMyName() + "\">");
			saveSecPolInst(secPolItem, pw);
			pw.println("</IP>");
		}
		Collection<SecPolicy> userPolicy = li.getUserPolicy().values();
		for (SecPolicy secPolItem : userPolicy) {
			pw.println("<User name=\"" + secPolItem.getMyName() + "\">");
			saveSecPolInst(secPolItem, pw);
			pw.println("</User>");
		}

		pw.println("</SecPolicy>");

	}

	private void saveSecPolInst(SecPolicy secPolItem, PrintWriter pw) {

		pw.println("<dumpSizeLim>");
		pw.println(secPolItem.getDumpSize());
		pw.println("</dumpSizeLim>");
		pw.println("<homeSizeLim>");
		pw.println(secPolItem.getHomeMax());
		pw.println("</homeSizeLim>");
		pw.println("<dumpCountLim>");
		pw.println(secPolItem.getDumpCountLim());
		pw.println("</dumpCountLim>");
		pw.println("<denyLoad>");
		pw.println(secPolItem.isDenyLoad());
		pw.println("</denyLoad>");
		pw.println("<denyViewLive>");
		pw.println(secPolItem.isDenyViewLive());
		pw.println("</denyViewLive>");
		pw.println("<denyViewThird>");
		pw.println(secPolItem.isDenyViewThird());
		pw.println("</denyViewThird>");
		pw.println("<denyDataCap>");
		pw.println(secPolItem.isDenyDataCap());
		pw.println("</denyDataCap>");
		pw.println("<denyLiveCap>");
		pw.println(secPolItem.isDenyLiveCap());
		pw.println("</denyLiveCap>");
	}

	private void handleSecPol(Element elem) {
		double dumpSizeLim = 0;
		double homeSizeLim = 0;
		Integer dumpCountLim = 0;
		boolean denyLoad = false;
		boolean denyViewLive = false;
		boolean denyViewThird = false;
		boolean denyDataCap = false;
		boolean denyLiveCap = false;

		NodeList childs = elem.getChildNodes();
		Integer len = childs.getLength();
		for (Integer pos = 0; pos < len; pos++) {
			Node node = childs.item(pos);
			if (node instanceof Element) {
				Element child = (Element) node;
				String name = child.getAttribute("name");
				if (name == null)
					continue;
				SecPolicy secPol = new SecPolicy(name);
				NodeList subChilds = child.getChildNodes();
				Integer subLen = subChilds.getLength();
				for (Integer i = 0; i < subLen; i++) {
					Node subNode = subChilds.item(i);
					if (subNode instanceof Element) {
						Element subElem = (Element) subNode;
						if (subElem.getNodeName().equals("dumpSizeLim")) {
							String data = subElem.getTextContent().trim();
							dumpSizeLim = Double.parseDouble(data);
						}
						if (subElem.getNodeName().equals("homeSizeLim")) {
							String data = subElem.getTextContent().trim();
							homeSizeLim = Double.parseDouble(data);

						}
						if (subElem.getNodeName().equals("dumpCountLim")) {
							String data = subElem.getTextContent().trim();
							dumpCountLim = Integer.parseInt(data);
						}
						if (subElem.getNodeName().equals("denyLoad")) {
							String data = subElem.getTextContent().trim();
							if (data.equals("true"))
								denyLoad = true;
							else
								denyLoad = false;

						}
						if (subElem.getNodeName().equals("denyViewLive")) {
							String data = subElem.getTextContent().trim();
							if (data.equals("true"))
								denyViewLive = true;
							else
								denyViewLive = false;
						}
						if (subElem.getNodeName().equals("denyViewThird")) {
							String data = subElem.getTextContent().trim();
							if (data.equalsIgnoreCase("true"))
								denyViewThird = true;
							else
								denyViewThird = false;
						}
						if (subElem.getNodeName().equals("denyDataCap")) {
							String data = subElem.getTextContent().trim();
							if (data.equalsIgnoreCase("true"))
								denyDataCap = true;
							else
								denyDataCap = false;

						}
						
						if (subElem.getNodeName().equals("denyLiveCap")) {
							String data = subElem.getTextContent().trim();
							if (data.equalsIgnoreCase("true"))
								denyLiveCap = true;
							else
								denyLiveCap = false;

						}
					}

				}
				secPol.setHomeMax(homeSizeLim);
				secPol.setDumpSize(dumpSizeLim);
				secPol.setDumpCountLim(dumpCountLim);
				secPol.setDenyDataCap(denyDataCap);
				secPol.setDenyLoad(denyLoad);
				secPol.setDenyViewLive(denyViewLive);
				secPol.setDenyViewThird(denyViewThird);
				secPol.setDenyLiveCap(denyLiveCap);
				if (child.getNodeName().equals("IP")) {
					ipPoliciesTmp.add(secPol);
				}
				if (child.getNodeName().equals("User")) {
					usersPoliciesTmp.add(secPol);
				}

			}
		}
	}

	private double readSize(String str) {
		double res = -1;
		if (str.contains("m") || str.contains("M")) {
			Integer pos = -1;
			pos = str.indexOf("M");
			if (pos == -1)
				pos = str.indexOf("m");
			String numeral = str.substring(0, pos);
			res = Double.parseDouble(numeral);
			res = res * Math.pow(2, 20);

		}
		if (str.contains("g") || str.contains("G")) {
			Integer pos = -1;
			pos = str.indexOf("G");
			if (pos == -1)
				pos = str.indexOf("g");
			String numeral = str.substring(0, pos);
			res = Double.parseDouble(numeral);
			res = res * Math.pow(2, 30);

		}
		if (str.contains("k") || str.contains("K")) {
			Integer pos = -1;
			pos = str.indexOf("K");
			if (pos == -1)
				pos = str.indexOf("k");
			String numeral = str.substring(0, pos);
			res = Double.parseDouble(numeral);
			res = res * Math.pow(2, 10);

		}
		return res;
	}

}