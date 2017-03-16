package com.jubaka.sors.appserver.serverSide;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionListener implements Runnable {
	private String msg = "Hello niga";
	private ServerSocket ss;
	private ConnectionHandler handler;
	private Integer portListenTo;

	public ConnectionListener() {

	}

	public void closeConnection() {
		try {
			if (ss != null)
				ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startListener(ConnectionHandler handler,Integer port) {

		portListenTo = port;
		this.handler = handler;
		Thread th = new Thread(this);
		th.start();

	}

	public String getMsg() {
		return msg;
	}

	@Override
	public void run() {
		try {
			ss = new ServerSocket(portListenTo);
			while (!ss.isClosed()) {
				handler.handleConnection(ss.accept());
			}
			System.out.println("443 handle loop finish");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

}
