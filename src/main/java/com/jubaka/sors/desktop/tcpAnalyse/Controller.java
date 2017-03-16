package com.jubaka.sors.desktop.tcpAnalyse;

import java.awt.Component;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.limfo.LoadLimits;
import com.jubaka.sors.desktop.sessions.*;

public class Controller {
	private static API api;
//	private  static Controller inst;
	private static MainWin view;
	ClassFactory factory;
	Socket servSoc;
	Dumper dump = null;
	NetViewItemPanel curNetView = null;
	private Integer id;
	private SessionsAPI sesApi;
	private boolean nowActive = false;

	/**
	 * On subnet panel(view) click event handler
	 * 
	 * @param net
	 */
	public void updateIpLists(Subnet net) {
		try {
			clearIPsView();
			Set<IPaddr> ipLst = net.getIps();
			MyTableModel model = (MyTableModel) MainWin.instance.tbl_1.getModel();

			TreeMap<Long, Set<IPaddr>> sorted = prepareTree(ipLst);
			Set<Long> keys = sorted.keySet();
			for (long key : keys) {
		//		System.out.println("ip osrt key " + key);
				for (IPaddr item : sorted.get(key)) {
					String ip = item.getAddr().getHostAddress();
					model.addIP(ip);
				

				}

			}
			clearLiveIPsView();
			ipLst = net.getLiveIps();
			model = (MyTableModel) MainWin.instance.tbl_0.getModel();
		

			sorted = prepareTree(ipLst);
			keys = sorted.keySet();
			for (long key : keys) {
				for (IPaddr item : sorted.get(key)) {
					String ip = item.getAddr().getHostAddress();
					model.addIP(ip);

				}

			}
		} catch (Exception ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}

	}
	public static void setSessionViewPanel() {
		LoadLimits ll =  ClassFactory.getInstance().getLimits();
		MainWin.instance.sessionView.setListModeOn();
		MainWin.instance.sessionView.setCompaq(false);
		MainWin.instance.onIPclick(MainWin.instance.selectedIP);
		byte b =0;
		ll.setSessionViewMode(b);
		
	}
	public static void setSessionViewCompaq() {
		LoadLimits ll =  ClassFactory.getInstance().getLimits();
		MainWin.instance.sessionView.setListModeOn();
		MainWin.instance.sessionView.setCompaq(true);
		MainWin.instance.onIPclick(MainWin.instance.selectedIP);
		byte b =1;
		ll.setSessionViewMode(b);
	}
	public static void setSessionViewTable() {
		LoadLimits ll =  ClassFactory.getInstance().getLimits();
		MainWin.instance.sessionView.setTableModeOn();
		MainWin.instance.onIPclick(MainWin.instance.selectedIP);
		byte b =2;
		ll.setSessionViewMode(b);
	}
	
	public static void showIntegratedSessionView() {
		LoadLimits ll =  ClassFactory.getInstance().getLimits();
		MainWin.instance.showIntegratedSesView();
		ll.setSeparSessionView(false);
	}
	public static void showSeparateSessionView() {
		LoadLimits ll =  ClassFactory.getInstance().getLimits();
		MainWin.instance.showSeparateSesView();
		ll.setSeparSessionView(true);
	}
	public static void showSeparateDataView() {
		LoadLimits ll =  ClassFactory.getInstance().getLimits();
		MainWin.instance.showSeparateSessionDataWindow();
		ll.setSeparSesDataView(true);
	}
	public static void showIntagratedDataView() {
		LoadLimits ll =  ClassFactory.getInstance().getLimits();
		MainWin.instance.showIntegratedSessionDataWindow();
		ll.setSeparSesDataView(false);
	}
	
	public static void resetView() {
		clearIPsView();
		clearLiveIPsView();
		
		view.setId(null, false);
	}
	
	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			for (File item : dir.listFiles()) {
				if (item.isDirectory())
					deleteDir(item);
				if (item.isFile())
					item.delete();
			}
		}
		dir.delete();
	}
	
	public void startProcessing(Branch br) {
		br.startCapture(null);
		if (view.id == br.getId())
			view.onBranchStateChanged(br);
		
	}

	public void pauseBranch(Branch br) {
		br.stopCapture();
		br.setState(2);
		if (view.id == br.getId())
			view.onBranchStateChanged(br);
		
	}
	
	public void stopBranch(Branch br) {
		br.stopCapture();
		br.setState(3);
		if (view.id == br.getId())
			view.onBranchStateChanged(br);
	}
	
	public static List<String> decodeData (byte[] buf) {
		ArrayList<String> text = new ArrayList<String>();
		if (buf==null) return text;
		if (buf.length != 0) {
			Scanner srcScan = new Scanner(new ByteArrayInputStream(buf));
			while (srcScan.hasNext())
				text.add(srcScan.nextLine());

		}
		return text;
	}
	/**
	 * Sort ipaddresses by key and store in map
	 * 
	 * @param buf
	 *            container with IPaddr objects
	 * @return Sorted map
	 */
	public TreeMap<Long, Set<IPaddr>> prepareTree(Set<IPaddr> buf) {
		TreeMap<Long, Set<IPaddr>> res = new TreeMap<Long, Set<IPaddr>>();
		if (buf == null)
			return res;
		for (IPaddr addr : buf) {
			long key = getSortValue(addr);
			if (res.containsKey(key)) {
				res.get(key).add(addr);
			} else {
				Set<IPaddr> set = new HashSet<IPaddr>();
				set.add(addr);
				res.put(key, set);
			}

		}
		return res;

	}
	
	public static void createLocalBranch(String name, String dev,  String fileName) {
		
		Integer bID = ClassFactory.getInstance().createLocalBranch(name,dev,
				fileName);
		
		MainWin.instance.setId(bID,false);			
	}

	/**
	 * Is neded for prepareTree method
	 * 
	 * @param addr
	 *            IPaddr object
	 * @return Sort key
	 */
	public Long getSortValue(IPaddr addr) {
		long result;
		switch (view.comboIPsort.getSelectedIndex()) {
		case 0:
			result = addr.getDataUp();
			break;

		case 1:
			result = addr.getDataDown();
			break;

		case 2:
			result = (long) addr.getActivated().getTime();
			break;

		default:
			result = addr.getDataDown();
			break;
		}
		return result;

	}

	/**
	 * 
	 * @param id
	 *            TaskViewBean id
	 * @param nowActive
	 *            Active flag
	 * @throws Exception
	 */
	public void init(Integer id, boolean nowActive) throws Exception {
		this.id = id;
		this.nowActive = nowActive;
		sesApi = ClassFactory.getInstance().getSesionInstance(id);
		factory = ClassFactory.getInstance();
		api = factory.getAPIinstance(id);
		factory.getSesionInstance(id).activate();
		view = MainWin.instance;
	}

	/**
	 * Display ip in ip-view lists
	 * 
	 * @param ip
	 *            ip address to add
	 */
	public void addIP(String ip) {
		MyTableModel model = (MyTableModel) view.tbl_0.getModel();
		if (model.exist(ip))  return;
		model.addIP(ip);

		model = (MyTableModel) view.tbl_1.getModel();
		if (model.exist(ip))  return;
		model.addIP(ip);
	}

	/**
	 * Remove ip address from ip-view lists
	 * 
	 * @param ip
	 *            - ip address to removw
	 */
	public void removeIP(String ip) {
		
		SwingUtilities.invokeLater(new RemoveIpFromUIList(ip));
		
	}

	public List<String> getSubnetList() {
		return sesApi.getNetList();

	}

	/**
	 * Binding socket, running dump in a Thread, listening socket and append to
	 * dumpArea(jTextArea)
	 * 
	 * @param exp
	 *            pcap expression
	 * @param iface
	 *            interfece identifier
	 */
	public void setDump(String exp, String iface,String path,JTextArea monitor) {


			String dumpFile = ClassFactory.getInstance().getBranch(id).getFileName();
			dump = api.setDump(iface,dumpFile, true, exp, path,monitor);

	}

	/**
	 * Stoping currently running pcap dump</br> Currently running pcap dump
	 * instance is stored in [dump] varible.
	 */
	public void stopDump() {

		try {
			api.stopDump(dump);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * change current state of the subnet view[NetViewItemPanel instance]
	 * 
	 * @param view
	 *            NetViewItemPanel instance
	 */
	public void changeCurrentNetView(NetViewItemPanel netView) {
		if (curNetView == netView)
			return;
		if (curNetView != null) {
			curNetView.net.setSelected(false);
			curNetView.panel.setBackground(UIManager
					.getColor("Button.background"));

		}
		
		clearIPsView();
		clearLiveIPsView();

		curNetView = netView;

		if (curNetView != null) {
			curNetView.net.setSelected(true);
			curNetView.panel.setBackground(UIManager
					.getColor("Slider.altTrackColor"));
			updateIpLists(netView.net);
		}

	}

	/**
	 *
	 * @return API instance according to the current branch
	 */
	public API getAPI() {
		return api;
	}

	/**
	 * 
	 * @param address
	 * @param mask
	 * @throws UnknownHostException
	 */
	public void addNet(String address, Integer mask)
			throws UnknownHostException {
		InetAddress addr = InetAddress.getByName(address);
	//	if (sesApi == null)
		//	System.out.println("sesAPI=null");
		Subnet net = sesApi.addNet(addr, mask);
		if (nowActive) {

			view.updateNetView();
		}
	}

	/**
	 * Breaks live capture
	 */
	public void breakCapture() {
		api.breakCapture();
	}

	/**
	 * Clear view with captured IPs
	 * 
	 * 
	 */
	public static void clearIPsView() {

		view.sessionView.clearView();

		MyTableModel model = (MyTableModel) view.tbl_1.getModel();
		model.removeAll();
		
		

	}
	
	public static void clearSessionListView() {
		view.clearSessioListView();
	}
	
	public static void clearIPInfoView() {
		view.clearIPInfoView();
	}

	/**
	 * Claear view with live IPs
	 */
	public static void clearLiveIPsView() {

		view.sessionView.clearView();
	
		MyTableModel model = (MyTableModel) view.tbl_0.getModel();
		model.removeAll();

	}

	/**
	 * 
	 * @param ip
	 *            address
	 * @return session objects in container
	 * @throws UnknownHostException
	 */
	public HashSet<Session> getInputActiveSes(String ip)
			throws UnknownHostException {
		return sesApi.getInputActiveSes(ip);

	}

	public HashSet<Session> getInputStoredSes(String ip)
			throws UnknownHostException {
		return sesApi.getInputStoredSes(ip);

	}

	public HashSet<Session> getOutputActiveSes(String ip)
			throws UnknownHostException {
		return sesApi.getOutputActiveSes(ip);

	}

	public HashSet<Session> getOutputStoredSes(String ip)
			throws UnknownHostException {
		return sesApi.getOutputStoredSes(ip);

	}

	public synchronized boolean isNowActive() {
		return nowActive;
	}

	public static void EnableSwitch(boolean enable, HashSet<Component> comps) {
		for (Component item : comps) {
			item.setEnabled(enable);
		}

	}

	public static HashSet<Component> getComponents(Component container,
			HashSet<Component> list) {

		if (list == null)
			list = new HashSet<Component>();

		try {
			for (Component contItem : ((java.awt.Container) container)
					.getComponents()) {
				list.add(contItem);
				getComponents(contItem, list);
			}

		} catch (ClassCastException e) {

		}

		return list;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static String processSize(double size,int places) {
		double newSize=size;
		
		if ( ((Math.pow(2, 20))<size) & (size<(Math.pow(2, 30))) ) {
			newSize = processSize(newSize, "b", "mb");
			newSize = round(newSize, places);
			return newSize+" MB";
			
		}
			
		if ( size>(Math.pow(2, 30)) ) {
			newSize = processSize(newSize, "b", "gb");
			newSize = round(newSize, places);
			return newSize+" GB";
			
		}
		newSize = processSize(newSize, "b", "kb");
		newSize = round(newSize, places);
		return newSize+" KB";
	}
	public static double processSize(double size, String formatFrom,
			String formatTo) {
		double newSize = size;
		switch (formatFrom) {
		case "kb":
			newSize = size * 1024;
			break;
		case "mb":
			newSize = size * Math.pow(2, 20);
			break;
		case "gb":
			newSize = size * Math.pow(2, 30);
			break;
		}

		switch (formatTo) {
		case "kb":
			newSize = newSize / 1024;
			break;
		case "mb":
			newSize = newSize / Math.pow(2, 20);
			break;
		case "gb":
			newSize = newSize / Math.pow(2, 30);
			break;
		}
		return newSize;

	}

	public static double processSize(double size,
			DefaultComboBoxModel<String> model) {
		double newSize=size;
		if (size == -1) {
			model.setSelectedItem("kb");
			return size;
		}
		
		if ( ((10*Math.pow(2, 20))<size) & (size<(Math.pow(2, 30))) ) {
			newSize = processSize(newSize, "b", "mb");
			model.setSelectedItem("mb");
			return newSize;
			
		}
			
		if ( size>(Math.pow(2, 30)) ) {
			newSize = processSize(newSize, "b", "gb");
			model.setSelectedItem("gb");
			return newSize;
			
		}
		model.setSelectedItem("kb");
		return processSize(newSize, "b", "kb");
	}
	class dumpPrinter implements Runnable,Observer{
		public dumpPrinter()  {
			
		}

		@Override
		public void update(Observable arg0, Object arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
	class RemoveIpFromUIList implements Runnable {
		String ip ;
		public RemoveIpFromUIList(String ip) {
			this.ip=ip;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			MyTableModel model = (MyTableModel) view.tbl_0.getModel();
			model.removeIP(ip);
			
		}
		
	}

}