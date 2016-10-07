package com.jubaka.sors.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

import com.jubaka.sors.beans.*;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.limfo.LoadInfo;
import com.jubaka.sors.limfo.LoadLimits;
import com.jubaka.sors.sessions.*;
import com.jubaka.sors.tcpAnalyse.Controller;
import com.jubaka.sors.tcpAnalyse.MainWin;
import com.jubaka.sors.tcpAnalyse.Settings;
import org.jfree.data.time.TimeSeries;

public class ConnectionHandler implements Runnable, Observer {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket s;
	private Integer streamId = 0;
	private BeanConstructor beanConstructor = new BeanConstructor();

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
		IPaddr ip = IPaddr.getInstance(brID, obj);

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
		try {
			String[] command;
			RequestObject ro;
			while (!s.isClosed()) {
				Object in = ois.readObject();

				if (in instanceof RequestObject) {
					ro = (RequestObject) in;
					command = ro.getRequestStr();
				}
				else
					continue;

				if (command[0].equals("getInfo")) {
					InfoBean iBean = beanConstructor.prepareInfoBean();
					iBean.setRequestId(ro.getRequestId());
					oos.writeObject(iBean);
					oos.flush();
					continue;
				}

				if (command[0].equals("getSec")) {
					SecPolicyBean secPol = beanConstructor.prepareSecPolBean();
					secPol.setRequestId(ro.getRequestId());
					oos.writeObject(secPol);
					oos.flush();
					continue;
				}

				if (command[0].equals("closeConnection")) {
					oos.close();
					ois.close();
					s.close();
					continue;
				}

				if (command[0].startsWith("getBranchStat_")) {

					if (command.length == 2) {
						String byUser = command[1];
						BranchStatBean brStat = beanConstructor.prepareBrStat(byUser);
						brStat.setRequestId(ro.getRequestId());
						oos.writeObject(brStat);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("getBaseStat_")) {

					if (command.length == 2) {
						Integer brId = Integer.parseInt(command[1]);
						DefaultTableModel baseStatTModel = StatisticLogic
								.getBaseTableModel(brId);
						Bean transportBean = new Bean();
						transportBean.setObject(baseStatTModel);
						transportBean.setRequestId(ro.getRequestId());
						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("getSubnetStat_")) {

					if (command.length == 3) {
						Integer brId = Integer.parseInt(command[1]);
						String subnetAddrStr = command[2];
						SessionsAPI sApi = ClassFactory.getInstance()
								.getSesionInstance(brId);

						Subnet net = StrToSubnet(brId, subnetAddrStr);
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
					}
					continue;
				}

				if (command[0].startsWith("getIpStat_")) {

					if (command.length == 3) {
						Integer brId = Integer.parseInt(command[1]);
						String ipAddrStr = command[2];
						IPaddr addr = IPaddr.getInstance(brId, ipAddrStr);
						DefaultTableModel ipStatTModel = StatisticLogic
								.getIPTableModel(addr);
						Bean transportBean = new Bean();
						transportBean.setRequestId(ro.getRequestId());
						transportBean.setObject(ipStatTModel);
						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("getDataInChart_")) {

					if (command.length == 2) {
						Integer brId = Integer.parseInt(command[1]);
						TimeSeries ts = StatisticLogic.createDataInSeries(brId,
								null, null);
						Bean transportBean = new Bean();
						transportBean.setRequestId(ro.getRequestId());
						transportBean.setObject(ts);

						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("getDataOutChart_")) {

					if (command.length == 2) {
						Integer brId = Integer.parseInt(command[1]);
						TimeSeries ts = StatisticLogic.createDataOutSeries(
								brId, null, null);
						Bean transportBean = new Bean();
						transportBean.setRequestId(ro.getRequestId());
						transportBean.setObject(ts);

						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("getNetworkDataInChart_")) {

					if (command.length == 3) {
						Integer brId = Integer.parseInt(command[1]);
						String netStr = command[2];
						Subnet net = StrToSubnet(brId, netStr);
						
						TimeSeries ts = StatisticLogic.createNetworkDataInSeries(
								null,net, null, null);
						Bean transportBean = new Bean();
						transportBean.setRequestId(ro.getRequestId());
						transportBean.setObject(ts);

						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
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

						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
				}
				
				if (command[0].startsWith("getIpDataInChart_")) {

					if (command.length == 3) {
						Integer brId = Integer.parseInt(command[1]);
						String ipStr = command[2];
						IPaddr ip = IPaddr.getInstance(brId, ipStr);
						TimeSeries ts = StatisticLogic.createIpDataInSeries(
								null,ip, null, null);
						Bean transportBean = new Bean();
						transportBean.setRequestId(ro.getRequestId());
						transportBean.setObject(ts);

						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
				}
				
				if (command[0].startsWith("getIpDataOutChart_")) {

					if (command.length == 3) {
						Integer brId = Integer.parseInt(command[1]);
						String ipStr = command[2];
						IPaddr ip = IPaddr.getInstance(brId, ipStr);
						TimeSeries ts = StatisticLogic.createIpDataOutSeries(
								null,ip, null, null);
						Bean transportBean = new Bean();
						transportBean.setRequestId(ro.getRequestId());
						transportBean.setObject(ts);

						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("getDir_")) {

					if (command.length == 3) {
						Integer brId = Integer.parseInt(command[1]);
						String sorsPath = command[2];

						FileListBean bean = beanConstructor.prepareFileListBean(brId, sorsPath);
						bean.setRequestId(ro.getRequestId());
						oos.writeObject(bean);
						oos.flush();
					}
					continue;
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
						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("getBranchInfo_")) {

					if (command.length == 3) {
						String byUser = command[1];
						Integer brid = Integer.parseInt(command[2]);

						BranchInfoBean infoBean = beanConstructor.prepareBranchInfoBean(byUser, brid);
						infoBean.setRequestId(ro.getRequestId());
						oos.writeObject(infoBean);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("getSessionData_")) {

					if (command.length == 4) {
						Integer brid = Integer.parseInt(command[1]);
						String net = command[2];
						Long tm = Long.parseLong(command[3]);

						SessionDataBean sessionData = beanConstructor.prepareSessionDataBean(brid, net, tm);
						sessionData.setRequestId(ro.getRequestId());
						oos.writeObject(sessionData);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("setUnid_")) {

					if (command.length == 2) {
						Long unid = Long.parseLong(command[1]);
						LoadLimits lim = ClassFactory.getInstance().getLimits();
						lim.setUnid(unid);
					}
					continue;
				}

				if (command[0].startsWith("getSubnetList_")) {

					if (command.length == 2) {
						Integer brId = Integer.parseInt(command[1]);
						SubnetBeanList subnetBeanList = beanConstructor.prepareSubnetBeanList(brId);
						subnetBeanList.setRequestId(ro.getRequestId());
						oos.writeObject(subnetBeanList);
						oos.flush();
					}
					continue;
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
					continue;
				}

				if (command[0].startsWith("getSesDataCapBean_")) {

					if (command.length == 3) {
						String object = command[1];
						Integer brId = Integer.parseInt(command[2]);

						SesDataCapBean sesDataCapBean = beanConstructor.prepareSesDataCapBean(object, brId);
						sesDataCapBean.setRequestId(ro.getRequestId());
						oos.writeObject(sesDataCapBean);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("getIfsList_")) {

					if (command.length == 2) {
						String byUser = command[1];
						List<String> devs = ClassFactory.getInstance()
								.getDeviceList(byUser);
						Bean transportBean = new Bean();
						transportBean.setRequestId(ro.getRequestId());
						transportBean.setObject(devs);
						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
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
						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("getFile_")) {

					if (command.length == 3) {
						Integer brId = Integer.parseInt(command[1]);
						String sorsPath = command[2];
						String recoveredPath = ClassFactory.getInstance()
								.getRecoveredDataPath(brId);
						File target = new File(recoveredPath + sorsPath);
						if (!target.exists()) {
							oos.writeObject(((long) -1));
							continue;
						}

						Long fileLen = target.length();
						oos.writeObject(fileLen);
						oos.flush();
						Object obj = ois.readObject();
						if (!(obj instanceof String))
							continue;
						String response = (String) obj;
						if (!response.equals("Length OK"))
							continue;
						FileInputStream fis = new FileInputStream(target);
						byte[] buf = new byte[4096];
						Integer readCount = fis.read(buf);
						while (readCount == 4096) {
							oos.write(buf);
							readCount = fis.read(buf);
						}
						oos.write(buf, 0, readCount);
						oos.flush();
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
						oos.writeObject(transportBean);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("delete_")) {

					if (command.length == 3) {
						Integer brId = Integer.parseInt(command[1]);
						String sorsPath = command[2];

						Controller.deleteDir(new File(sorsPath));
					}
					continue;
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
					continue;
				}

				if (command[0].startsWith("createBranch_")) {
					LoadLimits ll = ClassFactory.getInstance().getLimits();

					if (command.length == 5) {
						long fileLen = Long.parseLong(command[1]);
						String byUser = command[2];
						String fileName = command[3];
						String branchName = command[4];

						if (!ll.checkCreateBranchPolicy(byUser, fileLen))
							continue;

						Bean transportBean = new Bean();
						transportBean.setRequestId(ro.getRequestId());
						transportBean.setObject("getPcap");
						oos.writeObject(transportBean);
						oos.flush();

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
						oos.writeObject(bean);
						oos.flush();
					}
					continue;
				}


				if (command[0].startsWith("getSubnetLight_")) {
					if (command.length == 3) {
						Integer id = Integer.parseInt(command[1]);
						String subnetStr = command[2];

						SubnetLightBean bean = beanConstructor.prepareSubnetLightBean(id,subnetStr,null);
						bean.setRequestId(ro.getRequestId());
						oos.writeObject(bean);
						oos.flush();
					}
					continue;
				}

				if (command[0].startsWith("getIpBean_")) {
					if (command.length == 3) {
						Integer id = Integer.parseInt(command[1]);
						String ipStr = command[2];

						IPItemBean bean = beanConstructor.prepareIpBean(id,ipStr);
						bean.setRequestId(ro.getRequestId());
						oos.writeObject(bean);
						oos.flush();
					}
					continue;
				}


				if (command[0].startsWith("getBranchLight_")) {
					if (command.length == 2) {
						Integer id = Integer.parseInt(command[1]);

						BranchLightBean bean = beanConstructor.prepareLightBranchBean(id);
						bean.setRequestId(ro.getRequestId());
						oos.writeObject(bean);
						oos.flush();
					}
					continue;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		if (streamId != 0)
			try {
				oos.writeObject(streamId);
				oos.flush();
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

			if (o instanceof Subnet) {
				Subnet net = (Subnet) o;
				SubnetLightBean bean = beanConstructor.prepareSubnetLightBean(net,null);
				oos.writeObject(bean);
				oos.flush();
			}
			if (o instanceof  IPaddr) {
				IPaddr ipaddr = (IPaddr) o;
				Integer brId = ipaddr.getNet().getId();
				IPItemLightBean bean = beanConstructor.prepareIPItemLightBean(brId,ipaddr,null);
				oos.writeObject(bean);
				oos.flush();
			}
			if (o instanceof Session) {
				Session ses = (Session) o;
				SessionLightBean bean = beanConstructor.prepareSessionLightBean(ses,null);
				oos.writeObject(bean);
				oos.flush();
			}
		} catch (IOException io) {
			io.printStackTrace();
		}

	}
}
