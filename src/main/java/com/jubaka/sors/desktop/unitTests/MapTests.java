package com.jubaka.sors.unitTests;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.TreeMap;


public class MapTests {

	public static void main(String[] args) {
		Date t1 = new Date();
		TreeMap<Integer, Long> testMap = new TreeMap<Integer, Long>();
		for (Integer i =0; i<10000; i++) {
			Random r = new Random();
			testMap.put(i, r.nextLong());
		}
		Date t2 = new Date();
	//	System.out.println(testMap);
		System.out.println((t2.getTime()-t1.getTime()));
		
		
		Date l1 = new Date();
		LinkedHashMap<Integer, Long> testMap2 = new LinkedHashMap<Integer, Long>();
		for (Integer i =0; i<10000; i++) {
			Random r = new Random();
			testMap2.put(i, r.nextLong());
		}
		Date l2 = new Date();
	//	System.out.println(testMap2);
		System.out.println((l2.getTime()-l1.getTime()));
	}

}
