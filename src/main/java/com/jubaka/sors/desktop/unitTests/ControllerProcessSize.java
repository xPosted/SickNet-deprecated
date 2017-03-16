package com.jubaka.sors.unitTests;

import java.net.InetAddress;


public class ControllerProcessSize {

	public static void main(String[] args) throws Exception {
		InetAddress addr1 = InetAddress.getByName("0.0.0.0");
		InetAddress addr2 = InetAddress.getByName("0.0.0.0");
		
		if (addr1.equals(addr2)) System.out.println("Yes");

		String ip = "127.0.0.1";
		String[] splited = ip.split("\\.");
		for (String ipOct : splited) {
			System.out.println(ipOct);
		}
		
	}

}
