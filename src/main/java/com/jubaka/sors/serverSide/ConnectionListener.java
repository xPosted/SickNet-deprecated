package com.jubaka.sors.serverSide;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionListener implements Runnable {
	private String msg = "Hello niga";
	private ServerSocket ss;
	private ConnectionHandler handler;

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

	public void startListener(ConnectionHandler handler) {

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
			ss = new ServerSocket(443);
			while (!ss.isClosed()) {
				handler.handleConnection(ss.accept());
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

}
