package com.jubaka.sors.desktop.unitTests;

import java.net.InetAddress;
import java.rmi.dgc.VMID;
import java.util.Random;

public class VmID {

	public static void main(String[] args) throws Exception {
		InetAddress addr1 = InetAddress.getByName("192.168.0.1");
		InetAddress addr2 = InetAddress.getByName("192.168.0.1");
		if (addr1.equals(addr2)) {
			System.out.println("Niga");
		}
		
		
		Random r = new Random();
		Random r2 = new Random();

		Long lId = r.nextLong();
		Long lId2 = r.nextLong();
		if (lId<0) lId=lId*(-1);
		System.out.println(lId);


		if (lId2<0) lId2=lId2*(-1);
		System.out.println(lId2);
		
		VMID id = new VMID();
		System.out.println( id.toString()+" "+id.hashCode()+" ");

	}

}
