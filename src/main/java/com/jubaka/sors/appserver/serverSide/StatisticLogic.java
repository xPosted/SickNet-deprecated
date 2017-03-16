package com.jubaka.sors.appserver.serverSide;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.table.DefaultTableModel;

import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.desktop.tcpAnalyse.Controller;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;

public class StatisticLogic {

	private static boolean checkSessionInTime(SessionLightBean ses, Long timeFrom, Long timeTo) {

		if (timeFrom == null)
			timeFrom = (long) 0;
		if (timeTo == null)
			timeTo = Long.MAX_VALUE;

		Date established = ses.getEstablished();
		if (established.getTime() > timeFrom & established.getTime() < timeTo) {
			return true;
		} else
			return false;
	}

	public static TimeSeries createDataOutSeries(BranchBean bb, Long timeFrom, Long timeTo) {
		TimeSeries ts = new TimeSeries("Data out");

		for (SubnetBean net : bb.getSubnets()) {
			createNetworkDataOutSeries(ts, net, timeFrom, timeTo);
		}
		return ts;

	}

	public static TimeSeries createDataInSeries(BranchBean bb, Long timeFrom, Long timeTo) {
		TimeSeries ts = new TimeSeries("Data in");
		for (SubnetBean net : bb.getSubnets()) {
			createNetworkDataInSeries(ts, net, timeFrom, timeTo);
		}
		return ts;

	}

	public static TimeSeries createNetworkDataInSeries(TimeSeries ts, SubnetBean net, Long timeFrom, Long timeTo) {
		if (ts == null)
			ts = new TimeSeries("Subnet chart in");
		for (IPItemBean ip : net.getIps()) {
			createIpDataInSeries(ts, ip, timeFrom, timeTo);
		}
		return ts;
	}

	public static TimeSeries createNetworkDataOutSeries(TimeSeries ts, SubnetBean net, Long timeFrom, Long timeTo) {
		if (ts == null)
			ts = new TimeSeries("Subnet chart out");
		for (IPItemBean ip : net.getIps()) {
			createIpDataOutSeries(ts, ip, timeFrom, timeTo);
		}
		return ts;
	}

	public static TimeSeries createIpDataOutSeries(TimeSeries ts, IPItemBean ip, Long timeFrom, Long timeTo) {


		if (ts == null)
			ts = new TimeSeries("IP chart out");

		Set<SessionBean> sessioSet = ip.getActiveInSes();
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, false, timeFrom, timeTo);

		sessioSet = ip.getStoredInSes();
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, false, timeFrom, timeTo);

		sessioSet = ip.getActiveOutSes();
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, true, timeFrom, timeTo);

		sessioSet = ip.getStoredOutSes();
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, true, timeFrom, timeTo);

		return ts;

	}

	private static void handleSessionSet(Set<SessionBean> set, TimeSeries ts, boolean bySrc, Long timeFrom, Long timeTo) {
		if (set != null)
			for (SessionBean ses : set) {
				if (timeFrom != null || timeTo != null) {
					boolean skip = checkSessionInTime(ses, timeFrom, timeTo);
					if (skip)
						continue;
				}
				tsFill(ses, ts, timeFrom, timeTo, bySrc);
			}
	}

	public static TimeSeries createIpDataInSeries(TimeSeries ts, IPItemBean ip, Long timeFrom, Long timeTo) {

		if (ts == null)
			ts = new TimeSeries("IP chart in");

		Set<SessionBean> sessioSet = ip.getActiveInSes();
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, true, timeFrom, timeTo);

		sessioSet = ip.getStoredInSes();
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, true, timeFrom, timeTo);

		sessioSet = ip.getActiveOutSes();
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, false, timeFrom, timeTo);

		sessioSet = ip.getStoredOutSes();
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, false, timeFrom, timeTo);

		return ts;

	}

	public static TimeSeries createSessionDstTS(SessionBean ses) {

		TimeSeries tsDst = new TimeSeries("dst");
		tsFill(ses, tsDst, null, null, false);

		return tsDst;
	}

	public static TimeSeries createSessionSrcTS(SessionBean ses) {

		TimeSeries tsSrc = new TimeSeries("src");
		tsFill(ses, tsSrc, null, null, true);

		return tsSrc;
	}

/*	private static DefaultTableModel createStatisticMaintableModel(SubnetBean net) {

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Host");
		model.addColumn("Data IN");
		model.addColumn("Data OUT");
		model.addColumn("HTTP");
		model.addColumn("Sessions IN");
		model.addColumn("Sessions OUT");

		for (IPaddr ip : net.ips) {

		}
		return null;
	}
*/
	private static void tsFill(SessionBean ses, TimeSeries ts, Long timeFrom, Long timeTo, boolean bySrc) {
		if (timeFrom == null)
			timeFrom = (long) 0;
		if (timeTo == null)
			timeTo = Long.MAX_VALUE;

		TreeMap<Long, Integer> dataTimeBinding = null;
		if (bySrc) {
			dataTimeBinding = ses.getSrcDataTimeBinding();
		} else {
			dataTimeBinding = ses.getDstDataTimeBinding();
		}

		if (dataTimeBinding.size() == 0)
			return;
		Long key = dataTimeBinding.firstKey();

		while (key != null) {
			if (key > timeFrom & key < timeTo) {
				Second s = new Second(new Date(key));
				Integer index = ts.getIndex(s);
				if (index >= 0) {
					Integer val = (Integer) ts.getValue(index);
					val += dataTimeBinding.get(key);
					ts.update(s, val);
				} else {
					ts.add(s, dataTimeBinding.get(key));
				}
			}
			key = dataTimeBinding.higherKey(key);
		}
		fillTSwithZero(ts);
		

	}
	
	public static void fillTSwithZero(TimeSeries ts) {
		Date started = new Date();
		Second currentSec = null;
		Second previousSec = null;
		for (int item =1;item<ts.getItemCount();item++) {
			 currentSec = (Second) ts.getTimePeriod(item);
			 previousSec = (Second) ts.getTimePeriod(item-1);
			while ( ! previousSec.next().equals(currentSec)) {
				previousSec = (Second) previousSec.next();
				if (ts.getIndex(previousSec)<0) ts.add(previousSec, new Integer(0));
			}
		}
		System.out.println("fillTSwithZero time "+(new Date().getTime() - started.getTime()));
	}

	public static DefaultTableModel getIPTableModel(IPItemBean ip) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("IP address");
		model.addColumn("Hostname");
		model.addColumn("Received from");
		model.addColumn("Send to");
		model.addColumn("Input ses from");
		model.addColumn("Output ses to");
		String[] row = new String[6];
		HashMap<String, StatSmartIpItem> tableLogic = new HashMap<>();
		for (SessionBean ses : ip.getActiveInSes()) {
			String ipItem = ses.getSrcIP();
			StatSmartIpItem statSmartIp;
			if (!tableLogic.containsKey(ipItem)) {
				statSmartIp = new StatSmartIpItem(ip, ipItem);
				tableLogic.put(ipItem, statSmartIp);
			}
			statSmartIp = tableLogic.get(ipItem);
			statSmartIp.processSession(ses);
		}

		for (SessionBean ses : ip.getStoredInSes()) {
			String ipItem = ses.getSrcIP();
			StatSmartIpItem statSmartIp;
			if (!tableLogic.containsKey(ipItem)) {
				statSmartIp = new StatSmartIpItem(ip, ipItem);
				tableLogic.put(ipItem, statSmartIp);
			}
			statSmartIp = tableLogic.get(ipItem);
			statSmartIp.processSession(ses);
		}

		for (SessionBean ses : ip.getActiveOutSes()) {
			String ipItem = ses.getDstIP();
			StatSmartIpItem statSmartIp;
			if (!tableLogic.containsKey(ipItem)) {
				statSmartIp = new StatSmartIpItem(ip, ipItem);
				tableLogic.put(ipItem, statSmartIp);
			}
			statSmartIp = tableLogic.get(ipItem);
			statSmartIp.processSession(ses);
		}

		for (SessionBean ses : ip.getStoredOutSes()) {
			String ipItem = ses.getDstIP();
			StatSmartIpItem statSmartIp;
			if (!tableLogic.containsKey(ipItem)) {
				statSmartIp = new StatSmartIpItem(ip, ipItem);
				tableLogic.put(ipItem, statSmartIp);
			}
			statSmartIp = tableLogic.get(ipItem);
			statSmartIp.processSession(ses);
		}

		for (String ipRow : tableLogic.keySet()) {
			StatSmartIpItem statSmartIp = tableLogic.get(ipRow);

			row[0] = ipRow;
			row[1] = "no logic to do this...";
			row[2] = Controller.processSize(statSmartIp.getDataDown(), 2);
			row[3] = Controller.processSize(statSmartIp.getDataUp(), 2);
			row[4] = statSmartIp.getInputActiveCount() + "/" + statSmartIp.getInputCount();
			row[5] = statSmartIp.getOutputActiveCount() + "/" + statSmartIp.getOutputCount();
			model.addRow(row);
		}
		return model;

	}

	public static DefaultTableModel getNetTableModel(SubnetBean net) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("IP address");
		model.addColumn("Hostname");
		model.addColumn("Data IN");
		model.addColumn("Data OUT");
		model.addColumn("HTTP");
		model.addColumn("Ses IN");
		model.addColumn("Ses OUT");

		String[] row = new String[7];
		for (IPItemBean ip : net.getIps()) {
			row[0] = ip.getIp();
			row[1] = ip.getDnsName();
			row[2] = Controller.processSize(ip.getDataDown(), 2);
			row[3] = Controller.processSize(ip.getDataUp(), 2);
			row[4] = "[no data]";
			row[5] = ip.getInputActiveCount() + "/" + (ip.getInputCount());
			row[6] = ip.getOutputActiveCount() + "/" + ip.getOutputCount();
			model.addRow(row);
		}
		return model;
	}

	public static DefaultTableModel getBaseTableModel(BranchBean bb) {


		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Network");
		model.addColumn("MB IN");
		model.addColumn("MB OUT");
		model.addColumn("HTTP's");
		model.addColumn("Ses IN");
		model.addColumn("Ses OUT");
		String[] row = new String[6];
		for (SubnetBean net : bb.getSubnets()) {
			row[0] = net.getSubnet().getHostAddress();
			row[1] = Controller.processSize(net.getDataReceive(), 2);
			row[2] = Controller.processSize(net.getDataSend(), 2);
			row[3] = "null";
			row[4] = net.getActiveInSesCnt() + "/" + net.getInSesCnt();
			row[5] = net.getActiveOutSesCnt() + "/" + net.getOutSesCnt();
			model.addRow(row);
		}

		return model;
	}

}

class StatSmartIpItem {

	private IPItemBean ipMain;
	private String ipItem;
	private Long dataDown = (long) 0;
	private Long dataUp = (long) 0;
	private Integer inputCount = 0;
	private Integer outputCount = 0;
	private Integer inputActiveCount = 0;
	private Integer outputActiveCount = 0;

	public StatSmartIpItem(IPItemBean ipMain, String ipItem) {
		this.ipMain = ipMain;
		this.ipItem = ipItem;

	}

	public Long getDataDown() {
		return dataDown;
	}

	private void incDataDown(Long dataDown) {
		this.dataDown += dataDown;
	}

	public Long getDataUp() {
		return dataUp;
	}

	private void incDataUp(Long dataUp) {
		this.dataUp += dataUp;
	}

	public Integer getInputCount() {
		return inputCount;
	}

	private void incInputCount() {
		this.inputCount++;
	}

	public Integer getOutputCount() {
		return outputCount;
	}

	private void incOutputCount() {
		this.outputCount++;
	}

	public Integer getInputActiveCount() {
		return inputActiveCount;
	}

	private void incInputActiveCount() {
		this.inputActiveCount++;
	}

	public Integer getOutputActiveCount() {
		return outputActiveCount;
	}

	private void incOutputActiveCount() {
		this.outputActiveCount++;
	}

	public void processSession(SessionBean ses) {
		if (ses.getSrcIP().equals(ipMain.getIp()) & ses.getDstIP().equals(ipItem)) {
			incOutputCount();
			incDataUp(ses.getSrcDataLen());
			incDataDown(ses.getDstDataLen());

			if (ses.getClosed() == null)
				incOutputActiveCount();

		}
		if (ses.getSrcIP().equals(ipItem) & ses.getDstIP().equals(ipMain.getIp())) {
			incInputCount();
			incDataDown(ses.getSrcDataLen());
			incDataUp(ses.getDstDataLen());

			if (ses.getClosed() == null)
				incInputActiveCount();
		}

	}

}
