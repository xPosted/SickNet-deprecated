package com.jubaka.sors.sessions;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JTextArea;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

public class API extends Observable implements PcapPacketHandler<String>, Serializable {

	static ExecutorService exec = Executors.newCachedThreadPool();
	API instance = null;
	Integer count = 0;
	// SessionsAPI sAPI;
	private ArrayList<Dumper> dumps = new ArrayList<Dumper>();
	static private HashMap<InetAddress, Long> uploaddb = new HashMap<InetAddress, Long>();
	static private HashMap<InetAddress, Long> downloaddb = new HashMap<InetAddress, Long>();
	private ArrayBlockingQueue<PcapPacket> queue = new ArrayBlockingQueue<PcapPacket>(131072);
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Notifyier n = new Notifyier(); 
	//ExecutorService notifierExec = Executors.newSingleThreadExecutor();
	ReentrantLock mutex = new ReentrantLock();
	Timer timer = new Timer();
	TimerTask ttask = null;
	Pcap captor = null;
	InetAddress srcIP, dstIP;

	Integer bytes;
	private boolean simpleInsp = false;
	private boolean sessionInsp = false;
	private Integer id;
	
	
	//////	debug block
	private Integer counter =0;
	//////

	public API(Integer id) throws Exception {
		this.id = id;
		instance = this;
	}

	public void sessionInsp(boolean val) {
		sessionInsp = val;
	}

	public void simpleInsp(boolean val) {
		simpleInsp = val;
	}

	public Dumper setDump(String iface,String dumpFile, boolean promisc,
			String expression, String path,JTextArea monitor) {
		Dumper dump = new Dumper(iface,dumpFile, promisc, expression, path,monitor);
		Thread th = new Thread(dump);
		th.start();
		dumps.add(dump);

		return dump;

	}

	public void stopDump(Dumper dump) throws IOException {
		dump.breakCapture();
		
		dumps.remove(dump);
	
	}

	public void startCapture(String dev, String expression, String fileName) {
		/*		ttask = new TimerTask() {

					@Override
					public void run() {
						
						
							if (mutex.isLocked()) return;
							mutex.lock();
							setChanged();
							instance.notifyObservers(queue);
							mutex.unlock();
						}
						
					
						
					
				};
				timer.schedule(ttask, 0,100);
			*/	
				CaptureThread capTh = new CaptureThread(captor, dev, expression, fileName, this);
				exec.submit(capTh);
				captor = capTh.getCaptor();
	}
	
	public static  List<PcapIf> getDeviceList() {
		StringBuilder errbuf = new StringBuilder();
		List<PcapIf> devices = new ArrayList<PcapIf>();
		Pcap.findAllDevs(devices, errbuf);
		return devices;
	}

	public void breakCapture() {
		if (captor != null) {
			captor.breakloop();
			captor = null;
		}

	}


	public synchronized HashMap<InetAddress, Long> getUploadDBinfo() {
		return (HashMap<InetAddress, Long>) uploaddb.clone();
	}

	public synchronized HashMap<InetAddress, Long> getDownloadDBinfo() {
		return (HashMap<InetAddress, Long>) downloaddb.clone();
	}

	public void clearUploadDB() {
		uploaddb.clear();
	}

	public void clearDownloadDB() {
		downloaddb.clear();
	}

	@Override
	public void nextPacket(PcapPacket arg0, String arg1) {
		System.out.println("-");
		queue.add(new PcapPacket(arg0));
		
		if (!mutex.isLocked()) {
			setChanged();
			executor.submit(n);
		}
		
		
	}
	class Notifyier implements Runnable {

		@Override
		public void run() {
			if (mutex.isLocked()) return;
				mutex.lock();
				instance.notifyObservers(queue);
				mutex.unlock();
			
		}
		
	}

}

class CaptureThread implements Runnable {
	Pcap captor;
	String dev;
	String pcapExp;
	String fileName;
	API api;
	ReentrantLock lock = new ReentrantLock();
	//Semaphore sem = new Semaphore(1);
	StringBuilder errbuf = new StringBuilder();

	public CaptureThread(Pcap captor, String dev, String pcapExp,
			String fileName, API api) {
		this.captor = captor;
		this.dev = dev;
		this.pcapExp = pcapExp;
		this.fileName = fileName;
		this.api = api;
	/*	try {
			sem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
	}

	public Pcap getCaptor() {
	/*	try {
			sem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sem.release();
		*/
		return captor;
	}
	
	public void run() {
			PcapBpfProgram bpf = new PcapBpfProgram();
			if (fileName == null)
				
				captor = Pcap.openLive(dev, 8192, Pcap.MODE_PROMISCUOUS,0,errbuf);

			else
				captor = Pcap.openOffline(fileName,errbuf);
	//		sem.release();
			
			if (pcapExp != null) {
				captor.compile(bpf, pcapExp, 0, 0);
				captor.setFilter(bpf);
			}
				
			captor.loop(-1, api, null);
	}
}
