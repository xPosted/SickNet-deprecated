package com.jubaka.sors.serverSide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Properties;

import com.jubaka.sors.beans.AutorisationBean;
import com.jubaka.sors.entities.Node;
import com.jubaka.sors.entities.User;
import com.jubaka.sors.managed.PassEncoder;
import com.jubaka.sors.serverSide.bean.StreamTransportBean;
import com.jubaka.sors.serverSide.dbManagement.DBManager;
import com.jubaka.sors.service.NodeActiveCheckPointService;
import com.jubaka.sors.service.NodeService;
import com.jubaka.sors.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@ApplicationScoped
public class ConnectionHandler  extends Observable {

	@Inject
	private UserService userService;
	@Inject
	private NodeService nodeService;

	@Inject
	private NodeActiveCheckPointService checkPointService;


	
	private static ConnectionHandler inst=null;
	private static DBManager dbManager = null;
	private ConnectionListener listener = null;
	private static HashMap<String, NodeServerEndpoint> nodeList = new HashMap<String, NodeServerEndpoint>();
	private static HashMap<Long, NodeServerEndpoint> idNodeList = new HashMap<Long, NodeServerEndpoint>();

	
	/*
	public static ConnectionHandler getInstance() {
		if (inst==null) {
			inst = new ConnectionHandler();
		}
		return inst;
	}
*/
/*	public void initDBManager() {
		Properties props = new Properties();
		props.setProperty("user", "sors"); 
		props.setProperty("password", "simpleJubaka"); 
		props.setProperty("useSSL", "true");
		props.setProperty("requireSSL", "true");
		props.setProperty("verifyServerCertificate", "false");
		dbManager = new DBManager("95.47.114.170", "3306", "sors", props);
	}
*/
	public NodeServerEndpoint getNodeServerEndPoint(String nodeName) {
		return nodeList.get(nodeName);
	}

	public NodeServerEndpoint getNodeServerEndPoint(Long id) {
		return idNodeList.get(id);
	}
	
	public void initConnectionListener(Integer port) {
		if (listener!=null) return;
		listener = new ConnectionListener();
		listener.startListener(this,port);
		
	}

	public void updateConnectionListener(Integer port) {
		if (listener!=null) listener.closeConnection();
		listener = new ConnectionListener();

		listener.startListener(this,port);
	}
	
	public void nodeDisconnected(NodeServerEndpoint n) {
		Node node = nodeService.getNodeByUnid(n.getUnid());
		checkPointService.ifExistMoveToHistory(node);
		nodeList.remove(n.getFullName());
		idNodeList.remove(n.getUnid());
	}


	public void handleConnection(Socket s) {
		ObjectInputStream ois;
		System.out.println(this);
		try {
			ois = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
			Object obj = ois.readObject();
			if (obj instanceof AutorisationBean) {
				AutorisationBean auth = (AutorisationBean) obj;
				autorise(auth,ois,s);
			}
			if (obj instanceof Integer) {
				StreamTransportBean stb = new  StreamTransportBean();
				stb.setId((int)obj);
				stb.setOis(ois);
				stb.setS(s);
							// something with observers;
				setChanged();
				notifyObservers(stb);
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void autorise(AutorisationBean auth, ObjectInputStream ois, Socket s) {
				User uObj = userService.getUserByNick(auth.getNodeUserName());
				NodeServerEndpoint nodeServerEndpoint = new NodeServerEndpoint();
				nodeServerEndpoint.setS(s);
				nodeServerEndpoint.setOwner(auth.getNodeUserName());
				nodeServerEndpoint.setNodeName(auth.getNodeName());
				nodeServerEndpoint.setUnid(auth.getUnid());
				nodeServerEndpoint.createStreams(ois);
				nodeServerEndpoint.setCh(this);


				if (uObj==null) { nodeServerEndpoint.loginIncorrect(); return;}
				String encodedPass = PassEncoder.encode(auth.getNodeUserPass());
				if ( ! uObj.getPass().equals(encodedPass))  {
					nodeServerEndpoint.loginIncorrect(); return;}

				nodeService.handleNewNode(nodeServerEndpoint,uObj); 				 // here is unid creates
				nodeList.put(nodeServerEndpoint.getFullName(), nodeServerEndpoint);
				idNodeList.put(nodeServerEndpoint.getUnid(), nodeServerEndpoint);
		
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
	public static DBManager getDbManager() {
		return dbManager;
	}
	public static void setDbManager(DBManager dbManager) {
		ConnectionHandler.dbManager = dbManager;
	}
	

}
