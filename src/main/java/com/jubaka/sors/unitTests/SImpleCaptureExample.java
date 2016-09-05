package com.jubaka.sors.unitTests;

import javax.lang.model.element.PackageElement;

import org.jnetpcap.Pcap;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

public class SImpleCaptureExample implements PcapPacketHandler<String> {
	static StringBuilder errbuf = new StringBuilder();
	public static void main(String[] args) {
		Pcap captor  = Pcap.openLive("lo", 8192, Pcap.MODE_PROMISCUOUS,0,errbuf);
		captor.loop(-1, new SImpleCaptureExample(), null);
		
	}

	@Override
	public void nextPacket(PcapPacket arg0, String arg1) {
		//System.out.println(arg0);
		
	}

}
