package com.jubaka.sors.desktop.unitTests;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LocalIPDetection {

	public static void main(String[] args) throws Exception {
		LocalIPDetection ipd = new LocalIPDetection();
		System.out.println(ipd.isLocal (InetAddress.getByName("178.159.225.244")));
			
	}
	
	/*
	 * 10.0.0.0			167772160
	 * 10.255.255.255	184549375
	 * 
	 * 100.64.0.0		1681915904
	 * 100.127.255.255	1686110207
	 * 
	 * 172.16.0.0		-1408237568
	 * 172.31.255.255	-1407188993
	 * 
	 * 192.168.0.0		-1062731776
	 * 192.168.255.255	-1062666241
	 * 
	 * 
	 */
	
	public boolean isLocal(InetAddress addr) {
		 byte[] bAddr = addr.getAddress();
		 ByteBuffer bb = ByteBuffer.wrap(bAddr);
		 bb.order(ByteOrder.BIG_ENDIAN);
		 Integer addrInt = bb.getInt();
		 if ((addrInt<=-1407188993) & (addrInt>=-1408237568)) {
			 return true;
		 }
		 if ((addrInt<=-1062666241) & (addrInt>=-1062731776)) {
			 return true;
		 }
		 if ((addrInt<=1686110207) & (addrInt >= 1681915904)) {
			 return true;
		 }
		 if ((addrInt<=184549375) & (addrInt>=167772160)){
			 return true;
		 }
		 
		return false;
		
	}
	
	

}
