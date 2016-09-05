package com.jubaka.sors.remote;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

/*
public class HandleConnection implements Runnable {
	SessionsAPI sesApi;
	Socket s;
	ClassFactory factory = ClassFactory.getInstance();

	public HandleConnection(Socket s) {
		this.s = s;
	}
	
	

	public Data_bean ipPack(Subnet net) {
		Data_bean bean = new Data_bean();

		for (IPaddr addr : net.getLiveIps()) {
			bean.addLiveStr(addr.getAddr().getHostAddress());
		}

		for (IPaddr addr : net.getIps()) {
			bean.addIpStr(addr.getAddr().getHostAddress());
		}

		bean.setAddrCount(net.getIps().size());
		bean.setDown(net.getDataReceive());
		bean.setLiveAddr(net.getLiveIps().size());
		bean.setLiveSes(net.getLiveSesCount());
		bean.setSesCount(net.getSesCount());
		bean.setUp(net.getDataSend());

		return bean;
	}
	
	
	

	@Override
	public void run() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(
					s.getInputStream());
			while (s.isConnected()) {
				String command = (String) ois.readObject();
				if (command.startsWith("getNetList")) {
					System.out.println("getnetlist");
					String[] split = command.split("_");
					Integer branch_id = Integer.valueOf(split[1]);
					factory.getBranch(branch_id).incrementReqCount();
					sesApi = ClassFactory.getInstance().getSesionInstance(
							branch_id);
					System.out.println("reguest fro net list");
					oos.writeObject(sesApi.getNetList());
					oos.flush();

				}
				if (command.startsWith("analyze")) {
					SimpleDateFormat formatter = new SimpleDateFormat(
							"hh_mm_ss");
					String ip = s.getInetAddress().getHostAddress();
					String[] split = command.split("_");
					Integer len = Integer.valueOf(split[1]);
					String fileName = ip + "_"
							+ formatter.format(new Date()) + ".pcap";
					String path = factory.getHomeRemote()  + fileName;
					FileOutputStream fout = new FileOutputStream(new File(
							path));
					Integer counter = 0;
					System.out.println("Len = " + len);
					while (counter < len) {
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
							.createBranch(path, ip, len);
					System.out.println("BranchBean " + branch_id + " created");
					oos.writeObject(branch_id);
					oos.flush();
				}

				if (command.startsWith("getIPdata")) {
					String[] split = command.split("_");
					Integer branch_id = Integer.valueOf(split[1]);
					sesApi = ClassFactory.getInstance().getSesionInstance(
							branch_id);
					factory.getBranch(branch_id).incrementReqCount();
					Subnet net = sesApi.translate(split[2]);
					if (net == null)
						oos.writeObject(null);
					Data_bean bean = ipPack(net);
					oos.writeObject(bean);
					oos.flush();

				}
				if (command.startsWith("getSessions")) {
					String[] split = command.split("_");
					Integer branch_id = Integer.valueOf(split[1]);
					factory.getBranch(branch_id).incrementReqCount();
					IPItemBean ipInfo = prepareIpInfo(branch_id, split[2]);
					oos.writeObject(ipInfo);
					oos.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

*/