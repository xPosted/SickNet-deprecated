package com.jubaka.sors.remote;

import com.jubaka.sors.beans.AutorisationBean;
import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.limfo.LoadInfo;
import com.jubaka.sors.limfo.LoadLimits;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import javax.swing.JOptionPane;



public class WebConnection implements Runnable {
	
	private static Socket socket;
	private static String serverName=null;
	private static Integer port=443; 
	private static String userName=null;
	private static String password=null;
	private static boolean isRun=false;
	private ConnectionHandler ch;
	private static WebConnection instance;
	
	public static WebConnection getInstance() {
		if (instance==null) instance=new WebConnection();
		return instance;
	
	}
	private WebConnection() {
		
	}
	
private AutorisationBean prepareAutorisationBean() {
	AutorisationBean auth = new AutorisationBean();
	LoadLimits lim = ClassFactory.getInstance().getLimits();
	auth.setNodeUserName(userName);			
	auth.setNodeUserPass(password);				
	auth.setNodeName(LoadInfo.getNodeName());
	auth.setUnid(lim.getUnid());
	return auth;
}
	public void run() {
		try {
			socket = new Socket(serverName,port);
			isRun=true;
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				oos.writeObject(prepareAutorisationBean());
				oos.flush();
				ch = new ConnectionHandler(socket,oos);
				ch.listen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getUserName() {
		return userName;
	}
	public static boolean setUserName(String userName) {
		if (isRun) return false;
		WebConnection.userName = userName;
		return true;
	}
	public static String getPassword() {
		return password;
	}
	public static boolean setPassword(String password) {
		if (isRun) return false;
		WebConnection.password = password;
		return true;
	}
	public static boolean isRun() {
		return isRun;
	}
	
	public static void closeConnection() {
		try {
			socket.close();

			socket = null;
			isRun=false;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getServerName() {
		return serverName;
	}
	public static void setServerName(String serverName) {
		WebConnection.serverName = serverName;
	}
	public static Integer getPort() {
		return port;
	}
	public static void setPort(Integer port) {
		WebConnection.port = port;
	}
	
	

}
