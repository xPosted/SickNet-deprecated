package com.jubaka.sors.desktop.sessions;

import java.io.IOException;
import javax.swing.JTextArea;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapDumper;

public class Dumper implements Runnable {
	private String expression;
	private String path;
	private boolean promisc;
	private String iface;
	private String dumpFile = null;
	PcapDumper writer;
	Pcap captor;
	StringBuilder errbuf = new StringBuilder();
	private JTextArea monitor;

	
	public Dumper(String iface,String dumpFile,boolean promisc,String expression,String path,JTextArea monitor) {
		this.expression=expression;
		this.path=path;
		this.iface=iface;
		this.promisc=promisc;
		this.monitor= monitor;
		this.dumpFile=dumpFile;
	}
	
	
	public String getExpression() {
		return expression;
	}

	public String getPath() {
		return path;
	}

	public void breakCapture() throws IOException {
		
		if (captor!=null) {
			captor.breakloop();
			captor.close();
		}
		
			
	}
	
	@Override
	public void run() {
	
	try {
		if (dumpFile==null)
			captor = Pcap.openLive(iface, 80048, Pcap.MODE_PROMISCUOUS,0,errbuf);
		else 
			captor = Pcap.openOffline(dumpFile, errbuf);
		if (expression!=null){
			PcapBpfProgram bpf = new PcapBpfProgram();
			captor.compile(bpf, expression, 0, 0);
			captor.setFilter(bpf);
		}
		if (path!=null) {
			writer= captor.dumpOpen(path);
			captor.loop(-1, writer);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
}
