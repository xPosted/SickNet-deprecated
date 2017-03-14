package com.jubaka.sors.sessions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

//import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import com.jubaka.sors.factories.ClassFactory;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;


public class SessionsAPI implements Observer, Serializable {

	Integer debug = 0;
	private GlobalFilterCollector glCollector;
	static SessionsAPI instance = null;
	protected HashSet<Subnet> nets = new HashSet<Subnet>();
	// protected HashMap<Long, Session> seqMap = new HashMap<Long, Session>();
	protected HashSet<Long> TmpNumber = new HashSet<Long>();
	private ArrayList<Session> allCapturedSes = new ArrayList<Session>();
	private Tcp tcp = new Tcp();
	private Ip4 ip = new Ip4();
	private Http http = new Http();
	private long seq;
	private long ack;

	// ////
	// //// debug block

	// ///

	Subnet notKnown;
	private Integer id;

	public SessionsAPI(Integer id) throws UnknownHostException {
		this.id = id;
		notKnown = new Subnet(id, InetAddress.getByName("0.0.0.0"), 0);
		nets.add(notKnown);
		glCollector = new GlobalFilterCollector(id);

	}

	public Subnet getNotKnownNet() {
		return notKnown;
	}

	public Integer getSubnetCount() {
		return getAllSubnets().size();
	}

	public Integer getIPsCount() {
		Integer ips_count = 0;
		for (Subnet netItem : getAllSubnets()) {
			ips_count = ips_count + netItem.getIps().size();
		}
		return ips_count;
	}

	public Integer getSessionsCount() {
		Integer ses_count = 0;
		for (Subnet netItem : getAllSubnets()) {
			ses_count = ses_count + netItem.getSesCount();
		}
		return ses_count;
	}

	public void activate() throws Exception {
		ClassFactory.getInstance().getAPIinstance(id).addObserver(this);
	}

	public void deactivate() throws Exception {
		ClassFactory.getInstance().getAPIinstance(id).deleteObserver(this);
	}

	public void addGlobalFilter(GlobalFilter gl) {
		glCollector.addFilter(gl);
		rebuildAll();
	}

	public HashSet<Session> getInputStoredSes(String ip) {
		try {
			InetAddress addr = InetAddress.getByName(ip);
			Subnet net = netDetect(addr);
			IPaddr ipaddr = IPaddr.getInstance(id, ip);
		//	System.out.println(ip + " and ipaddr = " + ipaddr);
			return net.getInputStoredSes(ipaddr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public HashSet<Session> getOutputStoredSes(String ip) {
		try {
			InetAddress addr = InetAddress.getByName(ip);
			Subnet net = netDetect(addr);
			IPaddr ipaddr = IPaddr.getInstance(id, ip);
		//	System.out.println(ip + " and ipaddr = " + ipaddr);
			return net.getOutputStoredSes(ipaddr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public Set<Subnet> getAllSubnets() {
		return nets;

	}

	public HashSet<Session> getInputActiveSes(String ip) {
		try {
			InetAddress addr = InetAddress.getByName(ip);
			Subnet net = netDetect(addr);
			IPaddr ipaddr = IPaddr.getInstance(id, ip);
		//	System.out.println(ip + " and ipaddr = " + ipaddr);
			return ipaddr.getNet().getInputActiveSes(ipaddr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public HashSet<Session> getOutputActiveSes(String ip) {
		try {
			InetAddress addr = InetAddress.getByName(ip);
			IPaddr ipaddr = IPaddr.getInstance(id, ip);
		//	System.out.println(ip + " and ipaddr = " + ipaddr);
			return ipaddr.getNet().getOutputActiveSes(ipaddr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public Subnet addNet(InetAddress addr, int mask) {
		if (addr.getHostAddress().equals("0.0.0.0")) {
			return notKnown;
		}
		Subnet res = new Subnet(id, addr, mask);
		Set<IPaddr> ipToRemoveSet = new HashSet<IPaddr>();
		for (IPaddr item : notKnown.getIps()) {
			if (res.inSubnet(item.getAddr())) {
				res.addIPmanualy(item);

				HashSet<Session> buf = notKnown.getInputActiveSes(item);
				if (buf != null) {
					res.addIp(item);
					for (Session ses : buf) {
						res.save(ses);
					}
				}
				buf = notKnown.getInputStoredSes(item);
				if (buf != null)
					for (Session ses : buf) {
						res.storing(ses);
					}

				buf = notKnown.getOutputStoredSes(item);
				if (buf != null)
					for (Session ses : buf) {
						res.storing(ses);
					}

				buf = notKnown.getOutputActiveSes(item);
				if (buf != null)
					for (Session ses : buf) {
						res.save(ses);
					}
				// res.addIp(item);
				item.setSubnet(res);
				ipToRemoveSet.add(item);

			}

		}
		for (IPaddr removeItem : ipToRemoveSet)
			notKnown.deleteIP(removeItem);

		nets.add(res);
	//	System.out.println("Net added");
		return res;
	}

	/*
	 * private void liveStoring(Session s) {
	 * 
	 * if (!IPnetMap.containsKey(s.getSrcIP().getAddr())) { Subnet net =
	 * netDetect(s.getSrcIP().getAddr()); if (net == null) {
	 * System.out.println("not know network"); net = notKnown;
	 * 
	 * } IPnetMap.put(s.getSrcIP().getAddr(), net);
	 * 
	 * } Subnet net = IPnetMap.get(s.getSrcIP());
	 * 
	 * IPnetMap.put(s.getSrcIP().getAddr(), net);
	 * 
	 * }
	 */
	private Subnet netDetect(InetAddress addr) {
		HashSet<InetAddress> ipList;
		for (Subnet net : nets) {
			if (net.inSubnet(addr)) {
				return net;
			}
		}
		return notKnown;
	}

	public void onCloseConnection(Session s, IPaddr closedBy, long ts) {

		s.closeCon(closedBy, ts);

	}

	// handle tcp
	public void handleTcpIp(PcapPacket packet) {
		try {
			long ts = packet.getCaptureHeader().timestampInMillis();
			packet.getHeader(tcp);
			packet.getHeader(ip);
			InetAddress packetSrcAddr = InetAddress.getByAddress(ip.source());
			IPaddr packetSrcIp = IPaddr.getInstance(id,
					packetSrcAddr.getHostAddress());
			if (packetSrcIp == null) {
				Subnet net = netDetect(packetSrcAddr);
				packetSrcIp = new IPaddr(id, packetSrcAddr, net);
			}

			if ((tcp.flags_SYN()) & (!tcp.flags_ACK())) {

				TmpNumber.add(tcp.seq());
				return;
			}

			if ((tcp.flags_SYN()) & (tcp.flags_ACK())) {

				if (TmpNumber.contains(tcp.ack() - 1)) {
					createSession(new Date(ts), tcp, ip);
					TmpNumber.remove(tcp.ack() - 1);
				}

				return;

			}

			if (tcp.flags_FIN()) {

				Session s = packetSrcIp.getSessionByPacket(tcp, ip, id);
				if (s == null) {
					return;
				}
				onCloseConnection(s, packetSrcIp, ts);
				return;
			}

			if (tcp.flags_RST()) {
				Session s = packetSrcIp.getSessionByPacket(tcp, ip, id);
				if (s == null) {
					return;
				}
				onCloseConnection(s, packetSrcIp, ts);
				return;
			}

			seq = tcp.seq();
			ack = tcp.ack();
			Session ses = null;
			ses = packetSrcIp.getSessionByPacket(tcp, ip, id);

			if (ses != null) { // System.out.println("Ses detected");

				ses.setSeq(seq);
				ses.setAck(ack);

				if (packet.hasHeader(http)) {
					ses.handlePacket(tcp,http,packetSrcAddr,ts);
				} else {
					ses.handlePacket(tcp, null, packetSrcAddr, ts);
				}
				/*
				 try {


					ses.putDataDirect(tcp.getPayload(), packetSrcAddr, ts);

				} catch (Exception e) {
					e.printStackTrace();
				}
				*/

				return;

			} else { // if session opened before program start
				if (tcp.getPayload().length == 0)
					return;
				createSession(new Date(0), tcp, ip);
				InetAddress src = InetAddress.getByAddress(ip.source());
				InetAddress dst = InetAddress.getByAddress(ip.destination());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createSession(Date started, Tcp tcp, Ip4 ip) {

		IPaddr tcpSrc = null;
		IPaddr tcpDst = null;
		InetAddress srcip = null;
		InetAddress dstip = null;
		try {
			srcip = InetAddress.getByAddress(ip.source());
			dstip = InetAddress.getByAddress(ip.destination());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tcpSrc = IPaddr.getInstance(id, srcip.getHostAddress());
		tcpDst = IPaddr.getInstance(id, dstip.getHostAddress());

		if (tcpSrc == null)
			tcpSrc = new IPaddr(id, srcip, null);
		if (tcpDst == null)
			tcpDst = new IPaddr(id, dstip, null);

		Subnet netTarget = netDetect(tcpSrc.getAddr());
		Subnet netInit = netDetect(tcpDst.getAddr());
		tcpSrc.setSubnet(netTarget);
		tcpDst.setSubnet(netInit);

		TmpNumber.remove(tcp.ack() - 1);
		if (netInit == null)
			netInit = notKnown;
		if (netTarget == null)
			netTarget = notKnown;
		Session s = new Session(new EmptyDataSaver());
		s.setSeq(tcp.ack());
		s.setInitSeq(tcp.ack());
		s.setAck(tcp.seq() + 1);
		if (started.getTime() == 0) {
			Integer res = Session.tryToDetectSrc(srcip, dstip);
			switch (res) {
			case 0:
				s.setSrcIP(tcpSrc);
				s.setDstIP(tcpDst);
				s.setSrcP(tcp.source());
				s.setDstP(tcp.destination());
				break;

			case 1:
				s.setSrcIP(tcpDst);
				s.setDstIP(tcpSrc);
				s.setSrcP(tcp.destination());
				s.setDstP(tcp.source());
				break;
			case -1:
				s.setSrcIP(tcpDst);
				s.setDstIP(tcpSrc);
				s.setSrcP(tcp.destination());
				s.setDstP(tcp.source());
				break;
			}
		} else {
			s.setSrcIP(tcpDst);
			s.setDstIP(tcpSrc);
			s.setSrcP(tcp.destination());
			s.setDstP(tcp.source());
		}

		s.setEstablished(started);

		DataSaverInfo DSinfo = ClassFactory.getInstance().getDataSaverInfo(id);

		if (DSinfo.checkInExist(s.getDstIP(), s.getDstP()))
			s.setDataSaver(new SimpleDataSaver(id, s));
		if (DSinfo.checkOutExist(s.getSrcIP(), s.getDstP()))
			s.setDataSaver(new SimpleDataSaver(id, s));

		// save in seq_ack bases...

		if (glCollector.checkSession(s)) {

			s.getSrcIP().handleOutputSession(s);
			s.getDstIP().handleIncomingSession(s);
		}

		allCapturedSes.add(s);
		ObserverTimer.getinstance().addObserver(s);
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		PcapPacket packet = null;

		if (arg0 instanceof API) {

			if (arg1 instanceof ArrayBlockingQueue) {

				ArrayBlockingQueue<PcapPacket> queue = (ArrayBlockingQueue<PcapPacket>) arg1;
				// System.out.println("queue size = "+queue.size());

				while (queue.size() > 0) {
					packet = queue.poll();
					if (packet.hasHeader(ip) & (packet.hasHeader(tcp)))
						handleTcpIp(packet);
				}
			}

		}

	}

	public SesCaptureInfo getCatchInfo(String ip) throws UnknownHostException {
		InetAddress addr = InetAddress.getByName(ip);
		return ClassFactory.getInstance().getDataSaverInfo(id)
				.getCatchInfo(addr);

	}

	public boolean addIP(String netAddr, String ip) throws Exception {
		HashSet<String> res = new HashSet<String>();
		InetAddress net = InetAddress.getByName(netAddr);
		for (Subnet item : nets) {
			if (item.getSubnet().equals(net)) {
				IPaddr ipAddr = IPaddr.getInstance(id, ip);
				if (ipAddr == null)
					ipAddr = new IPaddr(id, InetAddress.getByName(ip), null);
				if (item.inSubnet(ipAddr.getAddr())) {
					item.addIPmanualy(ipAddr);
					ipAddr.setSubnet(item);
					return true;
				} else
					return false;

			}
		}
		return false;

	}

	private boolean makeManifest(Set<Session> set, String path) {
		try {
			String brHome = ClassFactory.getInstance().getBranchPath(id);
			File recoveryManifest = new File(path);
			if (recoveryManifest.exists())
				recoveryManifest.delete();
			recoveryManifest.createNewFile();
			PrintWriter pw = new PrintWriter(recoveryManifest);

			for (Session ses : set) {
				String srcPath = ses.getDataSaver().getSrcDataFilePath();
				String dstPath = ses.getDataSaver().getDstDataFilePath();
				if (srcPath != null)
					pw.println(srcPath);
				if (dstPath != null)
					pw.println(dstPath);

			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void dataRecovering(Set<Session> set) {

		ClassFactory cl = ClassFactory.getInstance();
		cl.getBranch(id).setLastRecovered(new Date());
		String rawDataPath = cl.getRawDataPath(id);
		String currentRecoveryDataPath = cl.getRecoveredDataPath(id)
				+ File.separator + (new Date().getTime()) + File.separator;
		String resultDataPath = currentRecoveryDataPath + File.separator
				+ "result" + File.separator;
		String manifestPath = currentRecoveryDataPath + "recoveryManifest";

		try {
			Files.createDirectories(Paths.get(resultDataPath));
			if (!makeManifest(set, manifestPath))
				return;

			Process proc = Runtime.getRuntime().exec(
					cl.getPathToForemost() + " -Q -d -f " + manifestPath
							+ " -o " + resultDataPath);
			proc.waitFor();
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String brPath = ClassFactory.getInstance().getBranchPath(id);
		try {
			File oldAudit = new File(resultDataPath + "audit.txt");
			if (oldAudit.exists()) {
				Files.copy(oldAudit.toPath(), new File(currentRecoveryDataPath
						+ "audit.txt").toPath());
				oldAudit.delete();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// foremost must read file with file names that would be processed

	}

	public void dataRecovering() {
		ClassFactory cl = ClassFactory.getInstance();
		cl.getBranch(id).setLastRecovered(new Date());
		String rawDataPath = cl.getRawDataPath(id);
		String currentRecoveryDataPath = cl.getRecoveredDataPath(id)
				+ File.separator + (new Date().getTime()) + File.separator;
		String resultDataPath = currentRecoveryDataPath + File.separator
				+ "result" + File.separator;
		try {
			Files.createDirectories(Paths.get(resultDataPath));

			Process proc = Runtime.getRuntime().exec(
					cl.getPathToForemost() + " -Q -d -z " + rawDataPath
							+ " -o " + resultDataPath);
			proc.waitFor();
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String brPath = ClassFactory.getInstance().getBranchPath(id);
		try {
			File oldAudit = new File(resultDataPath + "audit.txt");
			if (oldAudit.exists()) {
				Files.copy(oldAudit.toPath(), new File(currentRecoveryDataPath
						+ "audit.txt").toPath());
				oldAudit.delete();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Set<String> getIPlist(String netAddr) throws UnknownHostException {
		HashSet<String> res = new HashSet<String>();
		InetAddress net = InetAddress.getByName(netAddr);
		for (Subnet item : nets) {
			if (item.getSubnet().equals(net)) {
				Set<IPaddr> ips = item.getIps();
				for (IPaddr item2 : ips) {
					res.add(item2.getAddr().getHostAddress());
				}
				return res;

			}
		}
		return null;
	}

	public Subnet getNetByName(String net) {
		HashSet<Subnet> subset = (HashSet<Subnet>) getAllSubnets();
		for (Subnet item : subset) {
			if (item.getSubnet().getHostAddress().equals(net)) {
				return item;
			}

		}
		return null;
	}

	public List<String> getNetList() {
		List<String> res = new ArrayList<String>();
		for (Subnet item : nets) {
			res.add(item.getSubnet().getHostAddress());
		}
		return res;

	}

	public void rebuildAll() {
		for (Subnet net : nets) {
			net.clearAll();
		}
		Integer len = allCapturedSes.size();
		for (Integer i = (len - 1); i > -1; i--) {
			Session ses = allCapturedSes.get(i);
			if (glCollector.checkSession(ses)) {
				ses.srcIP.handleOutputSession(ses);
				ses.dstIP.handleIncomingSession(ses);

				ses.srcIP.setActivated(ses.established);
				ses.dstIP.setActivated(ses.established);

				ses.srcIP.updateCounters(ses.getDstDataLen(), true);
				ses.srcIP.updateCounters(ses.getSrcDataLen(), false);
				ses.dstIP.updateCounters(ses.getDstDataLen(), false);
				ses.dstIP.updateCounters(ses.getSrcDataLen(), true);

				if (ses.closed != null) {
					ses.closeCon(ses.srcIP, ses.closed.getTime());
					ses.closeCon(ses.dstIP, ses.closed.getTime());
				}

			}
		}
	}

}

/*
 * 1361956785:354911 /192.168.0.228->/192.168.0.120 protocol(6) priority(0)
 * hop(64) offset(0) ident(53356) TCP 55388 > 80 seq(3418911086) win(14600) S
 * 1361956785:357325 /192.168.0.120->/192.168.0.228 protocol(6) priority(0)
 * hop(64) offset(0) ident(0) TCP 80 > 55388 seq(3886807817) win(5792) ack
 * 3418911087 S ses established 1361956785:357398 /192.168.0.228->/192.168.0.120
 * protocol(6) priority(0) hop(64) offset(0) ident(53357) TCP 55388 > 80
 * seq(3418911087) win(913) ack 3886807818 1361956785:357801
 * /192.168.0.228->/192.168.0.120 protocol(6) priority(0) hop(64) offset(0)
 * ident(53358) TCP 55388 > 80 seq(3418911087) win(913) ack 3886807818 P
 * 1361956785:366470 /192.168.0.120->/192.168.0.228 protocol(6) priority(0)
 * hop(64) offset(0) ident(11690) TCP 80 > 55388 seq(3886807818) win(1716) ack
 * 3418911527 Session detected 1361956785:382476 /192.168.0.120->/192.168.0.228
 * protocol(6) priority(0) hop(64) offset(0) ident(11691) TCP 80 > 55388
 * seq(3886807818) win(1716) ack 3418911527 Session detected 1361956785:382521
 * /192.168.0.228->/192.168.0.120 protocol(6) priority(0) hop(64) offset(0)
 * ident(53359) TCP 55388 > 80 seq(3418911527) win(1094) ack 3886809266
 * 1361956785:472089 /192.168.0.120->/192.168.0.228 protocol(6) priority(0)
 * hop(64) offset(0) ident(11692) TCP 80 > 55388 seq(3886809266) win(1716) ack
 * 3418911527 P 1361956785:472142 /192.168.0.228->/192.168.0.120 protocol(6)
 * priority(0) hop(64) offset(0) ident(53360) TCP 55388 > 80 seq(3418911527)
 * win(1094) ack 3886809294 1361956800:370404 /192.168.0.120->/192.168.0.228
 * protocol(6) priority(0) hop(64) offset(0) ident(11693) TCP 80 > 55388
 * seq(3886809294) win(1716) ack 3418911527 F 1361956800:410059
 * /192.168.0.228->/192.168.0.120 protocol(6) priority(0) hop(64) offset(0)
 * ident(53361) TCP 55388 > 80 seq(3418911527) win(1094) ack 3886809295
 */

