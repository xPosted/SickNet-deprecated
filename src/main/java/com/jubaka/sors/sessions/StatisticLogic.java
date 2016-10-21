package com.jubaka.sors.sessions;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.table.DefaultTableModel;

import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.tcpAnalyse.Controller;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;

public class StatisticLogic {

	private static boolean checkSessionInTime(Session ses, Long timeFrom, Long timeTo) {

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

	public static TimeSeries createDataOutSeries(Integer brId, Long timeFrom, Long timeTo) {
		TimeSeries ts = new TimeSeries("Data out");
		SessionsAPI sapi = ClassFactory.getInstance().getSesionInstance(brId);
		for (Subnet net : sapi.getAllSubnets()) {
			createNetworkDataOutSeries(ts, net, timeFrom, timeTo);
		}
		return ts;

	}

	public static TimeSeries createDataInSeries(Integer brId, Long timeFrom, Long timeTo) {
		TimeSeries ts = new TimeSeries("Data in");
		SessionsAPI sapi = ClassFactory.getInstance().getSesionInstance(brId);
		for (Subnet net : sapi.getAllSubnets()) {
			createNetworkDataInSeries(ts, net, timeFrom, timeTo);
		}
		return ts;

	}

	public static TimeSeries createNetworkDataInSeries(TimeSeries ts, Subnet net, Long timeFrom, Long timeTo) {
		if (ts == null)
			ts = new TimeSeries("Subnet chart in");
		for (IPaddr ip : net.getIps()) {
			createIpDataInSeries(ts, ip, timeFrom, timeTo);
		}
		return ts;
	}

	public static TimeSeries createNetworkDataOutSeries(TimeSeries ts, Subnet net, Long timeFrom, Long timeTo) {
		if (ts == null)
			ts = new TimeSeries("Subnet chart out");
		for (IPaddr ip : net.getIps()) {
			createIpDataOutSeries(ts, ip, timeFrom, timeTo);
		}
		return ts;
	}

	public static TimeSeries createIpDataOutSeries(TimeSeries ts, IPaddr ip, Long timeFrom, Long timeTo) {

		Subnet net = ip.getNet();
		if (ts == null)
			ts = new TimeSeries("IP chart out");

		Set<Session> sessioSet = net.getInputActiveSes(ip);
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, false, timeFrom, timeTo);

		sessioSet = net.getInputStoredSes(ip);
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, false, timeFrom, timeTo);

		sessioSet = net.getOutputActiveSes(ip);
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, true, timeFrom, timeTo);

		sessioSet = net.getOutputStoredSes(ip);
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, true, timeFrom, timeTo);

		return ts;

	}

	private static void handleSessionSet(Set<Session> set, TimeSeries ts, boolean bySrc, Long timeFrom, Long timeTo) {
		if (set != null)
			for (Session ses : set) {
				if (timeFrom != null || timeTo != null) {
					boolean skip = checkSessionInTime(ses, timeFrom, timeTo);
					if (skip)
						continue;
				}
				tsFill(ses, ts, timeFrom, timeTo, bySrc);
			}
	}

	public static TimeSeries createIpDataInSeries(TimeSeries ts, IPaddr ip, Long timeFrom, Long timeTo) {

		Subnet net = ip.getNet();
		if (ts == null)
			ts = new TimeSeries("IP chart in");

		Set<Session> sessioSet = net.getInputActiveSes(ip);
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, true, timeFrom, timeTo);

		sessioSet = net.getInputStoredSes(ip);
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, true, timeFrom, timeTo);

		sessioSet = net.getOutputActiveSes(ip);
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, false, timeFrom, timeTo);

		sessioSet = net.getOutputStoredSes(ip);
		if (sessioSet != null)
			handleSessionSet(sessioSet, ts, false, timeFrom, timeTo);

		return ts;

	}

	public static TimeSeries createSessionDstTS(Session ses) {

		TimeSeries tsDst = new TimeSeries("dst");
		tsFill(ses, tsDst, null, null, false);

		return tsDst;
	}

	public static TimeSeries createSessionSrcTS(Session ses) {

		TimeSeries tsSrc = new TimeSeries("src");
		tsFill(ses, tsSrc, null, null, true);

		return tsSrc;
	}

	private static DefaultTableModel createStatisticMaintableModel(Subnet net) {

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

	private static void tsFill(Session ses, TimeSeries ts, Long timeFrom, Long timeTo, boolean bySrc) {
		if (timeFrom == null)
			timeFrom = (long) 0;
		if (timeTo == null)
			timeTo = Long.MAX_VALUE;
		DataSaver ds = ses.getDataSaver();
		TreeMap<Long, Integer> dataTimeBinding = null;
		if (bySrc) {
			dataTimeBinding = ds.getSrcTimeBinding();
		} else {
			dataTimeBinding = ds.getDstTimeBinding();
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

	public static DefaultTableModel getIPTableModel(IPaddr ip) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("IP address");
		model.addColumn("Hostname");
		model.addColumn("Received from");
		model.addColumn("Send to");
		model.addColumn("Input ses from");
		model.addColumn("Output ses to");
		String[] row = new String[6];
		HashMap<IPaddr, StatSmartIpItem> tableLogic = new HashMap<IPaddr, StatSmartIpItem>();
		for (Session ses : ip.getInputActiveSessions()) {
			IPaddr ipItem = ses.getSrcIP();
			StatSmartIpItem statSmartIp;
			if (!tableLogic.containsKey(ipItem)) {
				statSmartIp = new StatSmartIpItem(ip, ipItem);
				tableLogic.put(ipItem, statSmartIp);
			}
			statSmartIp = tableLogic.get(ipItem);
			statSmartIp.processSession(ses);
		}

		for (Session ses : ip.getInputStoredSessions()) {
			IPaddr ipItem = ses.getSrcIP();
			StatSmartIpItem statSmartIp;
			if (!tableLogic.containsKey(ipItem)) {
				statSmartIp = new StatSmartIpItem(ip, ipItem);
				tableLogic.put(ipItem, statSmartIp);
			}
			statSmartIp = tableLogic.get(ipItem);
			statSmartIp.processSession(ses);
		}

		for (Session ses : ip.getOutputActiveSessions()) {
			IPaddr ipItem = ses.getDstIP();
			StatSmartIpItem statSmartIp;
			if (!tableLogic.containsKey(ipItem)) {
				statSmartIp = new StatSmartIpItem(ip, ipItem);
				tableLogic.put(ipItem, statSmartIp);
			}
			statSmartIp = tableLogic.get(ipItem);
			statSmartIp.processSession(ses);
		}

		for (Session ses : ip.getOutputStoredSessions()) {
			IPaddr ipItem = ses.getDstIP();
			StatSmartIpItem statSmartIp;
			if (!tableLogic.containsKey(ipItem)) {
				statSmartIp = new StatSmartIpItem(ip, ipItem);
				tableLogic.put(ipItem, statSmartIp);
			}
			statSmartIp = tableLogic.get(ipItem);
			statSmartIp.processSession(ses);
		}

		for (IPaddr ipRow : tableLogic.keySet()) {
			StatSmartIpItem statSmartIp = tableLogic.get(ipRow);

			row[0] = ipRow.getAddr().getHostAddress();
			row[1] = ipRow.getDnsName();
			row[2] = Controller.processSize(statSmartIp.getDataDown(), 2);
			row[3] = Controller.processSize(statSmartIp.getDataUp(), 2);
			row[4] = statSmartIp.getInputActiveCount() + "/" + statSmartIp.getInputCount();
			row[5] = statSmartIp.getOutputActiveCount() + "/" + statSmartIp.getOutputCount();
			model.addRow(row);
		}
		return model;

	}

	public static DefaultTableModel getNetTableModel(Subnet net) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("IP address");
		model.addColumn("Hostname");
		model.addColumn("Data IN");
		model.addColumn("Data OUT");
		model.addColumn("HTTP");
		model.addColumn("Ses IN");
		model.addColumn("Ses OUT");

		String[] row = new String[7];
		for (IPaddr ip : net.getIps()) {
			row[0] = ip.getAddr().getHostAddress();
			row[1] = ip.getDnsName();
			row[2] = Controller.processSize(ip.getDataDown(), 2);
			row[3] = Controller.processSize(ip.getDataUp(), 2);
			row[4] = Integer.toString(ip.getHTTPs().size());
			row[5] = ip.getInputActiveSessions().size() + "/" + ip.getInSessionCount();
			row[6] = ip.getOutputActiveSessions().size() + "/" + ip.getOutSessionCount();
			model.addRow(row);
		}
		return model;
	}

	public static DefaultTableModel getBaseTableModel(Integer brId) {
		SessionsAPI sapi = ClassFactory.getInstance().getSesionInstance(brId);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Network");
		model.addColumn("MB IN");
		model.addColumn("MB OUT");
		model.addColumn("HTTP's");
		model.addColumn("Ses IN");
		model.addColumn("Ses OUT");
		String[] row = new String[6];
		for (Subnet net : sapi.getAllSubnets()) {
			row[0] = net.toString();
			row[1] = Controller.processSize(net.getInputData(), 2);
			row[2] = Controller.processSize(net.getOutptData(), 2);
			row[3] = "null";
			row[4] = net.getInputActiveSesCount() + "/" + net.getInputSesCount();
			row[5] = net.getOutputActiveSesCount() + "/" + net.getOutputSesCount();
			model.addRow(row);
		}

		return model;
	}

}

class StatSmartIpItem {

	private IPaddr ipMain;
	private IPaddr ipItem;
	private Long dataDown = (long) 0;
	private Long dataUp = (long) 0;
	private Integer inputCount = 0;
	private Integer outputCount = 0;
	private Integer inputActiveCount = 0;
	private Integer outputActiveCount = 0;

	public StatSmartIpItem(IPaddr ipMain, IPaddr ipItem) {
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

	public void processSession(Session ses) {
		if (ses.getSrcIP() == ipMain & ses.getDstIP() == ipItem) {
			incOutputCount();
			incDataUp(ses.getSrcDataLen());
			incDataDown(ses.getDstDataLen());

			if (ses.getClosed() == null)
				incOutputActiveCount();

		}
		if (ses.getSrcIP() == ipItem & ses.getDstIP() == ipMain) {
			incInputCount();
			incDataDown(ses.getSrcDataLen());
			incDataUp(ses.getDstDataLen());

			if (ses.getClosed() == null)
				incInputActiveCount();
		}

	}

}
