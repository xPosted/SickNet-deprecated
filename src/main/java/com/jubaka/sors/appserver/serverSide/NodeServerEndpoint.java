package com.jubaka.sors.appserver.serverSide;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.table.DefaultTableModel;

import com.jubaka.sors.beans.*;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.appserver.serverSide.bean.StreamTransportBean;
import org.jfree.data.time.TimeSeries;

public class NodeServerEndpoint extends Observable implements EndpointInterface {



	private ConnectionHandler ch;
	private InfoBean info = null;
	private String nodeName = null;
	private Socket s = null;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String owner;
	private Long unid = null;
	private Date createTime = new Date();
	private String fullName;
	private InetAddress addr = null;
	private HashMap<Long, Bean> responseMap = new HashMap<>();
	private Random random = new Random();
	private static ExecutorService executor = Executors.newCachedThreadPool();
	private ReentrantLock lock = new ReentrantLock();




	public void createStreams(ObjectInputStream ois) {
		try {
			if (s != null) {
				oos = new ObjectOutputStream(new BufferedOutputStream(
						s.getOutputStream()));
				this.ois = ois;
			}
			fullName= owner+"@"+nodeName;
			addr=s.getInetAddress();
			Thread th =new Thread( new ServerInListener(ois,this));
			th.start();

		} catch (IOException oie) {
			oie.printStackTrace();
			disconnect();
		}
	}
	public String getFullName() {
		return fullName;
	}
	private Bean getResponse(Long requestId) {
		Bean response = null;
		try {
			while (response == null) {
				response = responseMap.get(requestId);
				if (response == null)
					synchronized (responseMap) {
						responseMap.wait(10000);
					}

			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		return response;
	}

	@Override
	public  SecPolicyBean getSecPolicyBean() {
		if  (checkConnection()==false) {
			return null;
		}
		SecPolicyBean scb = null;
		try {

			RequestObject request = new RequestObject();
			String[] command = new String[1];
			command[0] = "getSec";
			Long requestId = random.nextLong();

			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();

			Bean response = getResponse(requestId);

			if (response instanceof SecPolicyBean) {
				scb = (SecPolicyBean) response;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return scb;
	}
	
	
	@Override
	public   boolean createLiveBranch(String ifsName, String byUser, String branchName, String ip) {
		if  (checkConnection()==false) {
			return false;
		}
		
		try {
			String[] command = new String[5];
			command[0] = "createLiveBranch_";
			command[1] = byUser;
			command[2] = ifsName;
			command[3] = branchName;
			command[4] = ip;

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();


			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return true;
	}
	
	@Override
	public   Integer createBranch(String pathToFile, String byUser, String fileName, String branchName) {
		if  (checkConnection()==false) {
			return -1;
		}
		try {
			File pcap = new File(pathToFile);
			if (!pcap.exists()) return -1;
			long fileLen = pcap.length();
			String[] command = new String[5];
			command[0] = "createBranch_";
			command[1] = Long.toString(fileLen) ;
			command[2] = byUser;
			command[3] = fileName;
			command[4] = branchName;

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);

				if (response.getObject().equals("getPcap")) {
					FileInputStream fin = new FileInputStream(pcap);
					long  counter = 0;
					while (counter < fileLen) {
						byte[] smallBuf;
						byte[] buf = new byte[4096];
						int count = fin.read(buf);

						counter = counter + count;
						if (count < 4096) {
							smallBuf = new byte[count];
							for (int j = 0; j < count; j++) {
								smallBuf[j] = buf[j];
							}

							oos.write(smallBuf);

						} else
							oos.write(buf);
					}
					oos.flush();
				}

			

		} catch (IOException e) {
			
			e.printStackTrace();
			if (lock.isLocked()) lock.unlock();
			disconnect();
			return -1;
		}

		return true;
	}

	@Override
	public   boolean createBranch(String byUser, Part filePart, String branchName) {
		if  (checkConnection()==false) {
			return false;
		}
		try {

			if (filePart == null) return false;
			long fileLen = filePart.getSize();
			String[] command = new String[5];
			command[0] = "createBranch_";
			command[1] = Long.toString(fileLen) ;
			command[2] = byUser;
			command[3] = filePart.getName();
			command[4] = branchName;

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);

			if (response.getObject().equals("getPcap")) {
				InputStream fin = filePart.getInputStream();
				long  counter = 0;
				while (counter < fileLen) {
					byte[] smallBuf;
					byte[] buf = new byte[4096];
					int count = fin.read(buf);

					counter = counter + count;
					if (count < 4096) {
						smallBuf = new byte[count];
						for (int j = 0; j < count; j++) {
							smallBuf[j] = buf[j];
						}

						oos.write(smallBuf);

					} else
						oos.write(buf);
				}
				oos.flush();
			}



		} catch (IOException e) {

			e.printStackTrace();
			if (lock.isLocked()) lock.unlock();
			disconnect();
			return false;
		}

		return true;
	}
	
	@Override
	public  void startBranch(Integer brId) {
		if  (checkConnection()==false) {
			return;
		}
		FileListBean flb = null;
		try {
			String[] command = new String[2];
			command[0] = "startBranch_";
			command[1] = brId.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}
	}


	@Override
	public  void removeObserver(Integer brId, String obj) {
		if  (checkConnection()==false) {
			return;
		}
		try {
			String[] command = new String[3];
			command[0] = "removeObserver_";
			command[1] = brId.toString();
			command[2] = obj;

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}
	}


	
	@Override
	public  void stopBranch(Integer brId) {
		if  (checkConnection()==false) {
			return;
		}
		FileListBean flb = null;
		try {
			String[] command = new String[2];
			command[0] = "stopBranch_";
			command[1] = brId.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);

			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}
	}
	
	
	@Override
	public  FileListBean getDir(Integer brId, String sorsPath) {
		if  (checkConnection()==false) {
			return null;
		}
		FileListBean flb = null;
		try {
			String[] command = new String[3];
			command[0] = "getDir_";
			command[1] = brId.toString();
			command[2] = sorsPath;

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			
			if (response instanceof FileListBean) {
				flb = (FileListBean) response;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return flb;
	}
	
	@Override
	public  void delete(Integer brId, String sorsPath) {
		if  (checkConnection()==false) {
			return;
		}
		try {
			String[] command = new String[3];
			command[0] = "delete_";
			command[1] = brId.toString();
			command[2] = sorsPath;

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return;
	}
	
	@Override
	public  DefaultTableModel getBaseTModel(Integer brId) {
		if  (checkConnection()==false) {
			return null;
		}
		DefaultTableModel tableResult = new DefaultTableModel();
		try {
			String[] command = new String[2];
			command[0] = "getBaseStat_";
			command[1] = brId.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);

				if (response.getObject() instanceof DefaultTableModel) {
					tableResult = (DefaultTableModel) response.getObject();
				}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return tableResult;
	}
	
	
	@Override
	public  TimeSeries getDataInChart(Integer brId) {
		if  (checkConnection()==false) {
			return null;
		}
		TimeSeries ts = new TimeSeries("NULL");
		try {
			String[] command = new String[2];
			command[0] = "getDataInChart_";
			command[1] = brId.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response.getObject() instanceof TimeSeries) {
				ts = (TimeSeries) response.getObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return ts;
	}
	
	@Override
	public  TimeSeries getDataOutChart(Integer brId, Long timeFrom, Long timeTo) {
		if  (checkConnection()==false) {
			return null;
		}
		TimeSeries ts = new TimeSeries("NULL");
		try {
			String[] command = new String[4];
			command[0] = "getDataOutChart_";
			command[1] = brId.toString();
			command[2] = timeFrom.toString();
			command[3] = timeTo.toString();
			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);

			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();

			Bean response = getResponse(requestId);

			if (response.getObject() instanceof TimeSeries) {
				ts = (TimeSeries) response.getObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return ts;
	}
	
	@Override
	public  TimeSeries getNetworkDataInChart(Integer brId, String netStr, Long timeFrom, Long timeTo) {
		if  (checkConnection()==false) {
			return null;
		}
		TimeSeries ts = new TimeSeries("NULL");
		try {
			String[] command = new String[5];
			command[0] = "getNetworkDataInChart_";
			command[1] = brId.toString();
			command[2] = netStr;
			command[3] = timeFrom.toString();
			command[4] = timeTo.toString();
			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);

			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();

			Bean response = getResponse(requestId);
			if (response.getObject() instanceof TimeSeries) {
				ts = (TimeSeries) response.getObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}
		return ts;
	}
	
	@Override
	public  TimeSeries getNetworkDataOutChart(Integer brId, String netStr) {
		if  (checkConnection()==false) {
			return null;
		}
		TimeSeries ts = new TimeSeries("NULL");
		try {
			String[] command = new String[3];
			command[0] = "getNetworkDataOutChart_";
			command[1] = brId.toString();
			command[2] = netStr;
			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response.getObject() instanceof TimeSeries) {
				ts = (TimeSeries) response.getObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return ts;
	}
	
	@Override
	public  TimeSeries getIpDataInChart(Integer brId, String ipStr) {
		if  (checkConnection()==false) {
			return null;
		}
		TimeSeries ts = new TimeSeries("NULL");
		try {
			String[] command = new String[3];
			command[0] = "getIpDataInChart_";
			command[1] = brId.toString();
			command[2] = ipStr;
			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response.getObject() instanceof TimeSeries) {
				ts = (TimeSeries) response.getObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return ts;
	}
	
	@Override
	public  TimeSeries getIpDataOutChart(Integer brId, String ipStr) {
		if  (checkConnection()==false) {
			return null;
		}
		TimeSeries ts = new TimeSeries("NULL");
		try {
			String[] command = new String[3];
			command[0] = "getIpDataOutChart_";
			command[1] = brId.toString();
			command[2] = ipStr;
			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response.getObject() instanceof TimeSeries) {
				ts = (TimeSeries) response.getObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return ts;
	}

	@Override
	public  TimeSeries getSesDstDataChart(Integer brId, String network, Long sesId) {
		if  (checkConnection()==false) {
			return null;
		}
		TimeSeries ts = new TimeSeries("NULL");
		try {
			String[] command = new String[4];
			command[0] = "getSessionDstDataChart_";
			command[1] = brId.toString();
			command[2] = network;
			command[3] = sesId.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response.getObject() instanceof TimeSeries) {
				ts = (TimeSeries) response.getObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return ts;
	}

	@Override
	public  TimeSeries getSesSrcDataChart(Integer brId, String network, Long sesId) {
		if  (checkConnection()==false) {
			return null;
		}
		TimeSeries ts = new TimeSeries("NULL");
		try {
			String[] command = new String[4];
			command[0] = "getSessionSrcDataChart_";
			command[1] = brId.toString();
			command[2] = network;
			command[3] = sesId.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response.getObject() instanceof TimeSeries) {
				ts = (TimeSeries) response.getObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return ts;
	}


	@Override
	public  DefaultTableModel getSubnetTModel(Integer brId, String subnet) {
		if  (checkConnection()==false) {
			return null;
		}
		DefaultTableModel tableResult = new DefaultTableModel();
		try {
			String[] command = new String[3];
			command[0] = "getSubnetStat_";
			command[1] = brId.toString();
			command[2] = subnet;

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response.getObject() instanceof DefaultTableModel) {
				tableResult = (DefaultTableModel) response.getObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return tableResult;
	}
	
	@Override
	public  DefaultTableModel getIpTModel(Integer brId, String ip) {
		if  (checkConnection()==false) {
			return null;
		}
		DefaultTableModel tableResult = new DefaultTableModel();
		try {
			String[] command = new String[3];
			command[0] = "getIpStat_";
			command[1] = brId.toString();
			command[2] = ip;

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response.getObject() instanceof DefaultTableModel) {
				tableResult = (DefaultTableModel) response.getObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return tableResult;
	}
	
	@Override
	public   void updateUnid(Long unid) {
		if  (checkConnection()==false) {
			return;
		}
		try {
			String[] command = new String[2];
			command[0] = "setUnid_";
			command[1] = unid.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();

			setUnid(unid);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return;
	}
	
	@Override
	public   List<String> getIfsList(String byUser) {
		List<String> ifs = new ArrayList<String>();
		if  (checkConnection()==false) {
			return null;
		}
		try {
			String[] command = new String[2];
			command[0] = "getIfsList_";
			command[1] = byUser;


			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response.getObject() instanceof List<?>) {
				ifs = (List<String>) response.getObject();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return ifs;
	}
	
	
	@Override
	public  SessionDataBean getSessionData(Integer brId, String net, Long tm) {
		if  (checkConnection()==false) {
			return null;
		}
		SessionDataBean sdb = null;
		try {
			String[] command = new String[4];
			command[0] = "getSessionData_";
			command[1] = brId.toString();
			command[2] = net;
			command[3] = tm.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			
			if (response instanceof SessionDataBean) {
				sdb = (SessionDataBean) response;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return sdb;
	}
	
	@Override
	public  void getFile(Integer brId, String sorsPath, HttpServletResponse response) {
		if  (checkConnection()==false) {
			return;
		}
		
		try {
			StreamCreator sc = new StreamCreator();
			StreamTransportBean stb = sc.getStream(this);
			if (stb==null) return;
			ObjectOutputStream separOutStream = stb.getOos();
			ObjectInputStream separInStream = stb.getOis();
			
			OutputStream os = response.getOutputStream();
			byte[] buf = new byte[4096];
			
			String[] command = new String[3];
			command[0] = "getFile_";
			command[1] = brId.toString();
			command[2] = sorsPath;
			
			separOutStream.writeObject(command);
			separOutStream.flush();
			Object lenObj = separInStream.readObject();
			if (lenObj instanceof Long) {
						
						Long length = (Long) lenObj;
						if (length ==-1) { response.setStatus(400); return;}
						separOutStream.writeObject("Length OK");
						separOutStream.flush();
						response.setHeader("Content-Length", length.toString());
						Integer readCount = 0;
						Integer readAtOnce =0;
						while (readCount<length) {
							readAtOnce = separInStream.read(buf);
							readCount = readCount + readAtOnce;
							os.write(buf,0,readAtOnce);
						}
						os.flush();
			} else response.setStatus(400);
			separOutStream.writeObject("closeConnection");
			separOutStream.flush();
			separOutStream.close();
			separInStream.close();
			stb.getS().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}
	
	
	
	
	@Override
	public  SubnetBeanList getSubnetBeanList(Integer brId) {
		if  (checkConnection()==false) {
			return null;
		}
		SubnetBeanList sbl = null;
		try {
			String[] command = new String[2];
			command[0] = "getSubnetList_";
			command[1] = brId.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			
			if (response instanceof SubnetBeanList) {
				sbl = (SubnetBeanList) response;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return sbl;
	}
	
	@Override
	public  SesDataCapBean getSesDataCaptureInfo(String target, Integer brId) {
		if  (checkConnection()==false) {
			return null;
		}
		SesDataCapBean sdc=null;
		try {

			
			String[] command = new String[3];
			command[0] = "getSesDataCapBean_";
			command[1] = target;
			command[2] = brId.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			
			if (response instanceof SesDataCapBean) {
				sdc = (SesDataCapBean) response;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return sdc;
	}

	
	@Override
	public  InfoBean getInfo() {
		if  (checkConnection()==false) {
			return null;
		}
		try {
			String[] command = new String[1];
			command[0] = "getInfo";

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			///

			if (response instanceof InfoBean) {
				info = (InfoBean) response;
			}

			info.setPubAddr(s.getInetAddress());
			info.setConnectedDate(createTime);
			info.setUnid(unid);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return info;
	}
	
	
	
	@Override
	public  boolean recoverSessionData(Integer brID) {
		if  (checkConnection()==false) {
			return false;
		}
		try {
			String[] command = new String[2];
			command[0] = "recoverSessionData_";
			command[1] = brID.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			return (boolean) response.getObject();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return false;
		
	}
	
	@Override
	public   boolean setCapture(Integer brId, String obj, String in, String out) {
		if  (checkConnection()==false) {
			return false;
		}
		try {
			String[] command = new String[5];
			command[0] = "setCapture_";
			command[1] = brId.toString();
			command[2] = obj;
			command[3] = in;
			command[4] = out;

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			return (boolean) response.getObject();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return false;
	}

	@Override
	public  BranchLightBean getBranchLight(Integer id) {
		if  (checkConnection()==false) {
			return null;
		}
		BranchLightBean bb = null;
		try {
			String[] command = new String[2];
			command[0] = "getBranchLight_";
			command[1] = id.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response instanceof BranchLightBean) {
				bb = (BranchLightBean) response;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return bb;
	}

	@Override
	public  IPItemBean getIpItemBean(Integer id, String ip, boolean observe) {
		if  (checkConnection()==false) {
			return null;
		}
		IPItemBean ipBean = null;
		try {
			String[] command = new String[4];
			command[0] = "getIpBean_";
			command[1] = id.toString();
			command[2] = ip;
			command[3] = Boolean.toString(observe);

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response instanceof IPItemBean) {
				ipBean = (IPItemBean) response;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return ipBean;
	}

	@Override
	public  SubnetLightBean getSubnetLight(Integer id, String subnet, boolean observe) {
		if  (checkConnection()==false) {
			return null;
		}
		SubnetLightBean subnetLight = null;
		try {
			String[] command = new String[4];
			command[0] = "getSubnetLight_";
			command[1] = id.toString();
			command[2] = subnet;
			command[3] = Boolean.toString(observe);

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response instanceof SubnetLightBean) {
				subnetLight = (SubnetLightBean) response;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return subnetLight;
	}

	@Override
	public  BranchBean getBranch(Integer id) {
		if  (checkConnection()==false) {
			return null;
		}
		BranchBean bb = null;
		try {
			String[] command = new String[2];
			command[0] = "getBranch_";
			command[1] = id.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response instanceof BranchBean) {
				bb = (BranchBean) response;
				editInfoBean(bb.getBib());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return bb;
	}
	
	@Override
	public  void loginIncorrect() {
		if  (checkConnection()==false) {
			return;
		}
		try {
			String[] command = new String[1];
			command[0] = "loginIncorrect";

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}
	}

	@Override
	public  Set<BranchInfoBean> getBranchInfoSet(String byUser) {
		if  (checkConnection()==false) {
			return null;
		}
		Set<BranchInfoBean> bibSet = null;
		try {
			String[] command = new String[2];
			command[0] = "getBranchInfoSet_";
			command[1] = byUser;

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response.getObject() instanceof Set) {
				bibSet = (Set<BranchInfoBean>) response.getObject();
				for (BranchInfoBean bib : bibSet) {
					editInfoBean(bib);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return bibSet;
	}
	
	public    void createStream(Integer id) {
		if  (checkConnection()==false) {
			return;
		}
		try {
			String[] command = new String[2];
			command[0] = "createStream_";
			command[1] = id.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}
		
	}
	
	@Override
	public  BranchInfoBean getBranchInfo(String byUser, Integer brid) {
		if  (checkConnection()==false) {
			return null;
		}
		BranchInfoBean bib = null;
		try {
			
			String[] command = new String[3];
			command[0] = "getBranchInfo_";
			command[1] = byUser;
			command[2] = brid.toString();

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);

			if (response instanceof BranchInfoBean) {
				bib = (BranchInfoBean) response;
				editInfoBean(bib);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return bib;
	}

	@Override
	public BranchStatBean getBranchStat(String byUser) {
		if  (checkConnection()==false) {
			return null;
		}
		BranchStatBean bsb = null;
		try {
			String[] command = new String[2];
			command[0] = "getBranchStat_";
			command[1] = byUser;

			Long requestId = random.nextLong();
			RequestObject request = new RequestObject();
			request.setRequestId(requestId);
			request.setRequestStr(command);
			lock.lock();
			oos.writeObject(request);
			oos.flush();
			lock.unlock();
			Bean response = getResponse(requestId);
			if (response instanceof BranchStatBean) {
				bsb = (BranchStatBean) response;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}

		return bsb;
	}
	private  void disconnect() {
		try {
			ois.close();
			oos.close();
		} catch (IOException io) {
			io.printStackTrace();
		}


		ch.nodeDisconnected(this);
	}

	private void editInfoBean(BranchInfoBean bib) {
		bib.setNodeId(unid);
		if (bib.getUserName().equals("local"))
			bib.setUserName(owner);
	}

	public  boolean checkConnection() {
		if (s.isClosed()) {
			disconnect();
			return false;
		}
		return true;
	}


	@Override
	public void setInfo(InfoBean info) {
		this.info = info;
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	@Override
	public String getOwner() {
		return owner;
	}

	@Override
	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String getNodeName() {
		return nodeName;
	}

	@Override
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
	public Long getUnid() {
		return unid;
	}

	@Override
	public void setUnid(Long unid) {
		this.unid = unid;
	}

	@Override
	public InetAddress getAddr() {
		return addr;
	}

	@Override
	public void setAddr(InetAddress addr) {
		this.addr = addr;
	}

	@Override
	public ConnectionHandler getCh() {
		return ch;
	}

	@Override
	public void setCh(ConnectionHandler ch) {
		this.ch = ch;
	}

	class ServerInListener implements  Runnable {

		private ObjectInputStream ois;
		private NodeServerEndpoint nse = null;

		public ServerInListener(ObjectInputStream ois,NodeServerEndpoint nse) {

			this.ois = ois;
			this.nse = nse;
		}

		@Override
		public void run() {

				while(true) {
					try {

					Object input = ois.readObject();
					if (input instanceof Bean) {

						Bean bean = (Bean) input;
						if (bean.getRequestId()!=-1) {
							synchronized (responseMap) {
								responseMap.put(bean.getRequestId(),bean);
								responseMap.notifyAll();
							}

						}else {
							setChanged();
							notifyObservers(bean);
						}
					} else System.err.println("not a bean instance handled!!");
					} catch (EOFException e) {
						e.printStackTrace();
						nse.disconnect();
						break;
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
						break;
					}
				}



		}
	}

}
