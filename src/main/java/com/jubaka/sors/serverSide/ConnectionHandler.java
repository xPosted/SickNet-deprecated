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
import com.jubaka.sors.serverSide.bean.StreamTransportBean;
import com.jubaka.sors.serverSide.dbManagement.DBManager;

public class ConnectionHandler  extends Observable {
	
	private static ConnectionHandler inst=null;
	private static DBManager dbManager = null;
	private ConnectionListener listener = null;
	private SecurityVisor sv = new SecurityVisor();
	private HashMap<String, Node> nodeList = new HashMap<String, Node>();
	private UserBase ub;
	
	
	public static ConnectionHandler getInstance() {
		if (inst==null) {
			inst = new ConnectionHandler();
		}
		return inst;
	}
	private ConnectionHandler() {
		ub = UserBase.getInstance();
		
	}
	public void initDBManager() {
		Properties props = new Properties();
		props.setProperty("user", "sors"); 
		props.setProperty("password", "simpleJubaka"); 
		props.setProperty("useSSL", "true");
		props.setProperty("requireSSL", "true");
		props.setProperty("verifyServerCertificate", "false");
		dbManager = new DBManager("95.47.114.170", "3306", "sors", props);
	}
	
	public Node getNode(String nodeName) {
		return nodeList.get(nodeName);
	}
	
	public void initConnectionListener() {
		if (listener!=null) listener.closeConnection();
		listener = new ConnectionListener();
		
		listener.startListener(this);
		
	}
	
	public void nodeDisconnected(Node n) {
		nodeList.remove(n.getFullName());
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
				User uObj = ub.getUser(auth.getNodeUserName());
				Node node = new Node();
				node.setS(s);
				node.setOwner(auth.getNodeUserName());
				node.setNodeName(auth.getNodeName());
				node.setUnid(auth.getUnid());
				node.createStreams(ois);
				
				if (uObj==null) { node.loginIncorrect(); return;}
				if ( ! uObj.getPass().equals(auth.getNodeUserPass()))  {node.loginIncorrect(); return;}
				
				nodeList.put(node.getFullName(), node);
				sv.handleNewNode(node);
		
	}
	public SecurityVisor getSv() {
		return sv;
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
