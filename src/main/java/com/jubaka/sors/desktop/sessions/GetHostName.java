package com.jubaka.sors.desktop.sessions;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;

public class GetHostName {
	private static ExecutorService pool = Executors.newCachedThreadPool();
	private static Runnable theLast;
	private String ip = "127.0.0.1";
	private JLabel lbl;
	private IPaddr addrInst;

	public GetHostName(String ip, JLabel lbl) {
		this.ip = ip;
		this.lbl = lbl;
	}

	public GetHostName(IPaddr addrInst) {
		ip=addrInst.getAddr().getHostAddress();
		this.addrInst=addrInst;
	}

	public void resolve() {
		Runnable th = new Runnable() {

			@Override
			public void run() {
				try {

					InetAddress addr = InetAddress.getByName(ip);
					String hostname = addr.getCanonicalHostName();
					if (addrInst != null)
						addrInst.setDnsName(hostname);
					else if (theLast == this)
						lbl.setText(hostname);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		theLast = th;
		pool.submit(th);

	}

}