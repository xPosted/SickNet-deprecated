package com.jubaka.sors.desktop.remote;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.table.DefaultTableModel;

import com.jubaka.sors.appserver.beans.*;
import com.jubaka.sors.appserver.beans.branch.*;
import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.limfo.LoadLimits;
import com.jubaka.sors.desktop.sessions.*;
import com.jubaka.sors.desktop.tcpAnalyse.Controller;
import com.jubaka.sors.desktop.tcpAnalyse.MainWin;
import com.jubaka.sors.desktop.tcpAnalyse.Settings;
import org.jfree.data.time.TimeSeries;

public class ConnectionHandler implements Runnable, Observer {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket s;
	private Integer streamId = 0;
	private BeanConstructor beanConstructor = new BeanConstructor();
	private ReentrantLock sendLock = new ReentrantLock();
	private ExecutorService exec = Executors.newCachedThreadPool();

	public ConnectionHandler(Socket s, Integer streamId) {
		try {
			this.s = s;
			oos = new ObjectOutputStream(new BufferedOutputStream(
					s.getOutputStream()));
			this.streamId = streamId;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Subnet StrToSubnet(Integer brId, String netAddr) {
		SessionsAPI sApi = ClassFactory.getInstance().getSesionInstance(brId);
		String mask = null;
		if (netAddr.contains("/")) {
			String[] addrVSmask = netAddr.split("/");
			netAddr = addrVSmask[0];
			mask = addrVSmask[1];
		}
		
		for (Subnet net : sApi.getAllSubnets()) {
			System.out.println(net.getSubnet().getHostAddress());
			if (net.getSubnet().getHostAddress().equals(netAddr)) {
				if ( mask !=null ) {
					Integer maskInt = Integer.parseInt(mask);
					if (maskInt == net.getSubnetMask()) 
						return net;
					return null;
				}
				return net;
			}
		}
		return null;
	}

	public ConnectionHandler(Socket s, ObjectOutputStream oos) {
		try {
			this.s = s;
			this.oos = oos;
			ois = new ObjectInputStream(s.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}






	private boolean setCapture(Integer brID, String obj, String in, String out) {
		obj = obj.trim();
		ClassFactory cl = ClassFactory.getInstance();
		SessionsAPI sesAPI = cl.getSesionInstance(brID);
		Subnet subnet = sesAPI.getNetByName(obj);
		IPaddr ip = cl.getSesionInstance(brID).getIpInstance(obj);

		if (subnet != null) {
			cl.getDataSaverInfo(brID).addSubnet(subnet, in, out);
			return true;
		}

		if (ip != null) {
			cl.getDataSaverInfo(brID).addItem(ip.getAddr(), in, out);
			return true;
		}
		return false;

	}





	public void listen() {

			String[] command;
			RequestObject ro;


			while (!s.isClosed()) {
				try {
				Object in = ois.readObject();

				if (in instanceof RequestObject) {
					ro = (RequestObject) in;
					exec.submit(new RequestHandler(ro));
				}
				else {
					System.out.println("shit hapened - "+in);
					continue;
				}




			}	catch (Exception e) {
					e.printStackTrace();
					if (sendLock.isLocked()) sendLock.unlock();
					break;


				}
		}
	}



	private void handleRequest(RequestObject ro) {
		try {

		String[] command = ro.getRequestStr();

		if (command[0].equals("getInfo")) {
			InfoBean iBean = beanConstructor.prepareInfoBean();
			iBean.setRequestId(ro.getRequestId());
			sendLock.lock();
			oos.writeObject(iBean);
			oos.flush();
			sendLock.unlock();
			return;
		}

		if (command[0].equals("getSec")) {
			SecPolicyBean secPol = beanConstructor.prepareSecPolBean();
			secPol.setRequestId(ro.getRequestId());
			sendLock.lock();
			oos.writeObject(secPol);
			oos.flush();
			sendLock.unlock();
			return;
		}

		if (command[0].equals("closeConnection")) {
			oos.close();
			ois.close();
			s.close();
			return;
		}

		if (command[0].startsWith("getBranchStat_")) {

			if (command.length == 2) {
				String byUser = command[1];
				BranchStatBean brStat = beanConstructor.prepareBrStat(byUser);
				brStat.setRequestId(ro.getRequestId());
				sendLock.lock();
				oos.writeObject(brStat);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getBaseStat_")) {

			if (command.length == 2) {
				Integer brId = Integer.parseInt(command[1]);
				DefaultTableModel baseStatTModel = StatisticLogic
						.getBaseTableModel(brId);
				Bean transportBean = new Bean();
				transportBean.setObject(baseStatTModel);
				transportBean.setRequestId(ro.getRequestId());
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getSubnetStat_")) {

			if (command.length == 3) {
				Integer brId = Integer.parseInt(command[1]);
				String subnetAddrStr = command[2];
				SessionsAPI sApi = ClassFactory.getInstance()
						.getSesionInstance(brId);

				Subnet net = StrToSubnet(brId, subnetAddrStr);
				sendLock.lock();
				if (net == null)
					oos.writeObject(null);
				else {
					DefaultTableModel subnetStatTModel = StatisticLogic
							.getNetTableModel(net);
					Bean transportBean = new Bean();
					transportBean.setRequestId(ro.getRequestId());
					transportBean.setObject(subnetStatTModel);
					oos.writeObject(transportBean);
				}
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getIpStat_")) {

			if (command.length == 3) {
				Integer brId = Integer.parseInt(command[1]);
				String ipAddrStr = command[2];
				IPaddr addr = ClassFactory.getInstance().getSesionInstance(brId).getIpInstance(ipAddrStr);
				DefaultTableModel ipStatTModel = StatisticLogic
						.getIPTableModel(addr);
				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject(ipStatTModel);
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getDataInChart_")) {

			if (command.length == 2) {
				Integer brId = Integer.parseInt(command[1]);
				TimeSeries ts = StatisticLogic.createDataInSeries(brId,
						null, null);
				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject(ts);
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getDataOutChart_")) {

			if (command.length == 4) {
				Integer brId = Integer.parseInt(command[1]);
				Long timeFrom = Long.valueOf(command[2]);
				Long timeTo = Long.valueOf(command[3]);
				if (timeFrom == -1) timeFrom = null;
				if (timeTo == -1) timeTo = null;

				TimeSeries ts = StatisticLogic.createDataOutSeries(
						brId, timeFrom, timeTo);
				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject(ts);
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getNetworkDataInChart_")) {

			if (command.length == 5) {
				Integer brId = Integer.parseInt(command[1]);
				String netStr = command[2];
				Subnet net = StrToSubnet(brId, netStr);
				Long timeFrom = Long.valueOf(command[3]);
				Long timeTo = Long.valueOf(command[4]);
				if (timeFrom == -1) timeFrom = null;
				if (timeTo == -1) timeTo = null;

				TimeSeries ts = StatisticLogic.createNetworkDataInSeries(
						null,net, timeFrom, timeTo);
				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject(ts);
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getNetworkDataOutChart_")) {

			if (command.length == 3) {
				Integer brId = Integer.parseInt(command[1]);
				String netStr = command[2];
				Subnet net = StrToSubnet(brId, netStr);
				TimeSeries ts = StatisticLogic.createNetworkDataOutSeries(
						null,net, null, null);
				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject(ts);
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getIpDataInChart_")) {

			if (command.length == 3) {
				Integer brId = Integer.parseInt(command[1]);
				String ipStr = command[2];

				IPaddr ip = ClassFactory.getInstance().getSesionInstance(brId).getIpInstance(ipStr);
				TimeSeries ts = StatisticLogic.createIpDataInSeries(
						null,ip, null, null);
				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject(ts);
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getIpDataOutChart_")) {

			if (command.length == 3) {
				Integer brId = Integer.parseInt(command[1]);
				String ipStr = command[2];
				IPaddr ip = ClassFactory.getInstance().getSesionInstance(brId).getIpInstance(ipStr);
				TimeSeries ts = StatisticLogic.createIpDataOutSeries(
						null,ip, null, null);
				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject(ts);
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}
			if (command[0].startsWith("getSessionDstDataChart_")) {

				if (command.length == 4) {
					Integer brId = Integer.parseInt(command[1]);
					String subnetStr = command[2];
					Long sesId = Long.parseLong(command[3]);
					Subnet subnet = StrToSubnet(brId,subnetStr);
					Session ses =  subnet.getSessionById(sesId);
					TimeSeries ts = StatisticLogic.createSessionDstTS(ses);
					Bean transportBean = new Bean();
					transportBean.setRequestId(ro.getRequestId());
					transportBean.setObject(ts);
					sendLock.lock();
					oos.writeObject(transportBean);
					oos.flush();
					sendLock.unlock();
				}
				return;
			}

			if (command[0].startsWith("getSessionSrcDataChart_")) {

				if (command.length == 4) {
					Integer brId = Integer.parseInt(command[1]);
					String subnetStr = command[2];
					Long sesId = Long.parseLong(command[3]);
					Subnet subnet = StrToSubnet(brId,subnetStr);
					Session ses =  subnet.getSessionById(sesId);
					TimeSeries ts = StatisticLogic.createSessionSrcTS(ses);
					Bean transportBean = new Bean();
					transportBean.setRequestId(ro.getRequestId());
					transportBean.setObject(ts);
					sendLock.lock();
					oos.writeObject(transportBean);
					oos.flush();
					sendLock.unlock();
				}
				return;
			}


		if (command[0].startsWith("getDir_")) {

			if (command.length == 3) {
				Integer brId = Integer.parseInt(command[1]);
				String sorsPath = command[2];
				Branch br =  ClassFactory.getInstance().getBranch(brId);
				FileListBean bean = beanConstructor.prepareFileListBean(br, sorsPath);
				bean.setRequestId(ro.getRequestId());
				sendLock.lock();
				oos.writeObject(bean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("loginIncorrect")) {
			WebConnection.closeConnection();
			Settings set = Settings.getInst();
			if (set != null)
				set.loginIncorrect();
		}

		if (command[0].startsWith("getBranchInfoSet_")) {

			if (command.length == 2) {
				String byUser = command[1];
				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject(beanConstructor.prepareBranchInfoSet(byUser));
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getBranchInfo_")) {

			if (command.length == 3) {
				String byUser = command[1];
				Integer brid = Integer.parseInt(command[2]);

				BranchInfoBean infoBean = beanConstructor.prepareBranchInfoBean(byUser, brid);
				infoBean.setRequestId(ro.getRequestId());
				sendLock.lock();
				oos.writeObject(infoBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getSessionData_")) {

			if (command.length == 4) {
				Integer brid = Integer.parseInt(command[1]);
				String net = command[2];
				Long tm = Long.parseLong(command[3]);

				SessionDataBean sessionData = beanConstructor.prepareSessionDataBean(brid, net, tm);
				sessionData.setRequestId(ro.getRequestId());
				sendLock.lock();
				oos.writeObject(sessionData);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("setUnid_")) {

			if (command.length == 2) {
				Long unid = Long.parseLong(command[1]);
				LoadLimits lim = ClassFactory.getInstance().getLimits();
				lim.setUnid(unid);
			}
			return;
		}

		if (command[0].startsWith("getSubnetList_")) {

			if (command.length == 2) {
				Integer brId = Integer.parseInt(command[1]);
				SubnetBeanList subnetBeanList = beanConstructor.prepareSubnetBeanList(brId);
				subnetBeanList.setRequestId(ro.getRequestId());
				sendLock.lock();
				oos.writeObject(subnetBeanList);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("createStream_")) {

			if (command.length == 2) {
				Integer streamId = Integer.parseInt(command[1]);
				String serverName = WebConnection.getInstance()
						.getServerName();
				Integer port = WebConnection.getInstance().getPort();
				Socket separCon = new Socket(serverName, port);
				ConnectionHandler separCH = new ConnectionHandler(
						separCon, streamId);
				Thread th = new Thread(separCH);
				th.start();
			}
			return;
		}

		if (command[0].startsWith("getSesDataCapBean_")) {

			if (command.length == 3) {
				String object = command[1];
				Integer brId = Integer.parseInt(command[2]);
				Branch br = ClassFactory.getInstance().getBranch(brId);
				SesDataCapBean sesDataCapBean = beanConstructor.prepareSesDataCapBean(br,object);
				sesDataCapBean.setRequestId(ro.getRequestId());
				sendLock.lock();
				oos.writeObject(sesDataCapBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getIfsList_")) {

			if (command.length == 2) {
				String byUser = command[1];
				List<String> devs = ClassFactory.getInstance()
						.getDeviceList(byUser);
				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject(devs);
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("recoverSessionData_")) {

			if (command.length == 2) {
				Integer brId = Integer.parseInt(command[1]);
				SessionsAPI sAPI = ClassFactory.getInstance()
						.getSesionInstance(brId);
				sAPI.dataRecovering();
				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject(true);
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("getFile_")) {


			// this fucking shit will not work in multithread version


			if (command.length == 3) {
				sendLock.lock();
				Integer brId = Integer.parseInt(command[1]);
				String sorsPath = command[2];
				String recoveredPath = ClassFactory.getInstance()
						.getRecoveredDataPath(brId);
				File target = new File(recoveredPath + sorsPath);
				if (!target.exists()) {
					oos.writeObject(((long) -1));
					return;
				}

				Long fileLen = target.length();
				oos.writeObject(fileLen);
				oos.flush();
				Object obj = ois.readObject();
				if (!(obj instanceof String))
					return;
				String response = (String) obj;
				if (!response.equals("Length OK"))
					return;
				FileInputStream fis = new FileInputStream(target);
				byte[] buf = new byte[4096];
				Integer readCount = fis.read(buf);
				while (readCount == 4096) {
					oos.write(buf);
					readCount = fis.read(buf);
				}
				oos.write(buf, 0, readCount);
				oos.flush();
				sendLock.unlock();
				fis.close();
			}
		}

		if (command[0].startsWith("setCapture_")) {

			if (command.length == 5) {
				Integer brId = Integer.parseInt(command[1]);
				String object = command[2];
				String inP = command[3];
				String outP = command[4];
				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject(setCapture(brId, object, inP, outP));
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}

		if (command[0].startsWith("delete_")) {

			if (command.length == 3) {
				Integer brId = Integer.parseInt(command[1]);
				String sorsPath = command[2];

				Controller.deleteDir(new File(sorsPath));
			}
			return;
		}

		if (command[0].startsWith("startBranch_")) {
			if (command.length == 2) {
				Integer brId = Integer.parseInt(command[1]);
				Branch b = ClassFactory.getInstance().getBranch(brId);
				Controller cntr = ClassFactory.getInstance().getController(brId);

				if (MainWin.instance.getId() == b.getId())
					cntr.startProcessing(b);
				else
					b.startCapture(null);
			}
		}

		if (command[0].startsWith("stopBranch")) {
			if (command.length == 2) {
				Integer brId = Integer.parseInt(command[1]);
				Branch b = ClassFactory.getInstance().getBranch(brId);
				b.stopCapture();
			}
		}

		if (command[0].startsWith("createLiveBranch_")) {

			if (command.length == 5) {
				String byUser = command[1];
				String ifs = command[2];
				String branchName = command[3];
				String ip = command[4];

				LoadLimits ll = ClassFactory.getInstance().getLimits();
				SecPolicy sc = ll.getPolicyByUser(byUser);
				if (sc.isDenyLiveCap() == false) {
					ClassFactory.getInstance().createBranch(byUser,
							branchName, null, ip, ifs);
				}

			}
			return;
		}

		if (command[0].startsWith("createBranch_")) {
			LoadLimits ll = ClassFactory.getInstance().getLimits();

			if (command.length == 5) {
				long fileLen = Long.parseLong(command[1]);
				String byUser = command[2];
				String fileName = command[3];
				String branchName = command[4];

				if (!ll.checkCreateBranchPolicy(byUser, fileLen))
					return;

				Bean transportBean = new Bean();
				transportBean.setRequestId(ro.getRequestId());
				transportBean.setObject("getPcap");
				sendLock.lock();
				oos.writeObject(transportBean);
				oos.flush();
				sendLock.unlock();
				ClassFactory factory = ClassFactory.getInstance();
				String path = factory.getHomeRemote()
						+ new Date().getTime();
				FileOutputStream fout = new FileOutputStream(new File(
						path));
				Integer counter = 0;
				System.out.println("Len = " + fileLen);
				while (counter < fileLen) {
					byte[] smallBuf;
					byte[] buf = new byte[4096];
					int count = ois.read(buf);
					counter += count;
					if (count < 4096) {
						smallBuf = new byte[count];
						for (int j = 0; j < count; j++) {
							smallBuf[j] = buf[j];
						}
						fout.write(smallBuf);
					} else
						fout.write(buf);

				}

				fout.close();
				Integer branch_id = ClassFactory.getInstance()
						.createBranch(byUser, branchName, path,
								s.getInetAddress().getHostName(), null);
				System.out.println("TaskViewBean " + branch_id
						+ " created");
			}
		}

		if (command[0].startsWith("getBranch_")) {
			if (command.length == 2) {
				Integer id = Integer.parseInt(command[1]);

				BranchBean bean = beanConstructor.prepareBranchBean(id);
				bean.setRequestId(ro.getRequestId());
				sendLock.lock();
				oos.writeObject(bean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}


		if (command[0].startsWith("getSubnetLight_")) {
			if (command.length == 4) {
				Integer id = Integer.parseInt(command[1]);
				String subnetStr = command[2];
				boolean observe = Boolean.valueOf(command[3]);

				SubnetLightBean bean = beanConstructor.prepareSubnetLightBean(id,subnetStr,null);
				bean.setRequestId(ro.getRequestId());
				sendLock.lock();
				oos.writeObject(bean);
				oos.flush();
				sendLock.unlock();

				if (observe) {
					SessionsAPI sApi = ClassFactory.getInstance().getSesionInstance(id);
					Subnet subnet =  sApi.getNetByName(subnetStr);
					subnet.addObserver(this);
				}

			}
			return;
		}

		if (command[0].startsWith("removeObserver_")) {
			if (command.length == 3) {
				Integer id = Integer.parseInt(command[1]);
				String obj = command[2];
				SessionsAPI sApi = ClassFactory.getInstance().getSesionInstance(id);
				Subnet subnet =  sApi.getNetByName(obj);
				IPaddr ipaddr = sApi.getIpInstance(obj);

				if (subnet != null) subnet.deleteObserver(this);
				if (ipaddr != null) {
					ipaddr.deleteObserver(this);
					ipaddr.setRemoteObserver(false);
				}

			}
			return;
		}

		if (command[0].startsWith("getIpBean_")) {
			if (command.length == 4) {
				Integer id = Integer.parseInt(command[1]);
				String ipStr = command[2];
				boolean observe = Boolean.valueOf(command[3]);
				Branch br = ClassFactory.getInstance().getBranch(id);
				IPItemBean bean = beanConstructor.prepareIpBean(br,ipStr);
				bean.setRequestId(ro.getRequestId());
				sendLock.lock();
				oos.writeObject(bean);
				oos.flush();
				sendLock.unlock();

				if (observe) {
					IPaddr ipaddr = ClassFactory.getInstance().getSesionInstance(id).getIpInstance(ipStr);
					ipaddr.addObserver(this);
					ipaddr.setRemoteObserver(true);
				}

			}
			return;
		}


		if (command[0].startsWith("getBranchLight_")) {
			if (command.length == 2) {
				Integer id = Integer.parseInt(command[1]);

				BranchLightBean bean = beanConstructor.prepareLightBranchBean(id);
				bean.setRequestId(ro.getRequestId());
				sendLock.lock();
				oos.writeObject(bean);
				oos.flush();
				sendLock.unlock();
			}
			return;
		}
		} catch (IOException io) {
			io.printStackTrace();
			if (sendLock.isLocked()) sendLock.unlock();
		} catch ( ClassNotFoundException cnf) {
								// very very very very bad idea to read data from stream at the same time from different threads !!!!!!!!!!!!!!!!!!!!!
			cnf.printStackTrace();
		}
	}

	@Override
	public void run() {
		if (streamId != 0)
			try {
				sendLock.lock();
				oos.writeObject(streamId);
				oos.flush();
				sendLock.unlock();
				ois = new ObjectInputStream(new BufferedInputStream(
						s.getInputStream()));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		listen();

	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			if (s == null) return;
			if (s.isClosed()) return;

			if (o instanceof Subnet) {
				Subnet net = (Subnet) o;
				SubnetLightBean bean = beanConstructor.prepareSubnetLightBean(net,null);
				sendLock.lock();
				oos.writeObject(bean);
				oos.flush();
				sendLock.unlock();
			}
			if (o instanceof  IPaddr) {
				System.out.println("iIpaddr update");
				IPaddr ipaddr = (IPaddr) o;
				Integer brId = ipaddr.getNet().getId();
				IPItemLightBean bean = beanConstructor.prepareIPItemLightBean(brId,ipaddr,null);
				sendLock.lock();
				oos.writeObject(bean);
				oos.flush();
				sendLock.unlock();
			}
			if (o instanceof Session) {
				Session ses = (Session) o;
				SessionLightBean bean = beanConstructor.prepareSessionLightBean(ses,null);
				sendLock.lock();
				oos.writeObject(bean);
				oos.flush();
				sendLock.unlock();
			}
		} catch (IOException io) {
			io.printStackTrace();
		}

	}

	class RequestHandler implements Runnable {

		private RequestObject ro;

		public RequestHandler(RequestObject ro) {
			this.ro = ro;
		}

		@Override
		public void run() {
			handleRequest(ro);
		}
	}
}
