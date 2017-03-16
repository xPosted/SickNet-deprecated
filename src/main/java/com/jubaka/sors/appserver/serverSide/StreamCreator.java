package com.jubaka.sors.appserver.serverSide;

import com.jubaka.sors.appserver.serverSide.bean.StreamTransportBean;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.Semaphore;

@Named
public class StreamCreator implements Observer {
	private Integer id = -1;
	private Semaphore sem = new Semaphore(1);
	private StreamTransportBean stb = null;

	@Inject
	private ConnectionHandler ch;
	
	
	public StreamCreator() {
	
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void test2Lock() {
		System.out.println("testLock");
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("FInish");
	}
	
	public StreamTransportBean getStream(NodeServerEndpoint nodeServerEndpoint) {
		Random r = new Random();
		id =r.nextInt();
		ch.addObserver(this);

		nodeServerEndpoint.createStream(id);
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stb;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof ConnectionHandler) 
			if (arg1 instanceof StreamTransportBean) {
				stb = (StreamTransportBean) arg1;
				Integer receivedId = stb.getId();
				if (receivedId.equals(id)) {
					try {
						ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream( stb.getS().getOutputStream()));
						stb.setOos(oos);
						sem.release();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
	}

}
