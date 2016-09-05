package com.jubaka.sors.unitTests;

import java.io.File;

public class MemTester {

	public static void main(String[] args) {
		File f = new File("/home/sanny");
		for (String item : f.list()) {
			System.out.println(item);
		}
		
		System.out.println("=======================");
		
		for (File item :  f.listFiles()) {
			System.out.println(item.getName());
		}

	}

}
