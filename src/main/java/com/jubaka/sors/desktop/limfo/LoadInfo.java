package com.jubaka.sors.limfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;

import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.sessions.Branch;

import com.sun.management.OperatingSystemMXBean;


//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
//import org.apache.*;

public class LoadInfo {
	static String nodeName = "Sors_production";
	static String desc = "SORS analyze node";

	public boolean checkMem() {

		return false;
	}

	public void print() {
		byte[] buf;
		MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage memUseHeap = memBean.getHeapMemoryUsage();
		MemoryUsage memUseNonHeap = memBean.getNonHeapMemoryUsage();
		while (true) {
			try {
			Thread.sleep(500);
			} catch(Exception e) {
				e.printStackTrace();
			}
		
		memBean = ManagementFactory.getMemoryMXBean();
		memUseHeap = memBean.getHeapMemoryUsage();
		memUseNonHeap = memBean.getNonHeapMemoryUsage();
		System.out
				.println("\n-----------------------------------------------------------");
		System.out.println("memBean say that HEAP memory Max= "
				+ (memUseHeap.getMax() / 1024) + " kb");
		System.out.println("memBean say that HEAP memory Used= "
				+ (memUseHeap.getUsed() / 1024) + " kb");
		System.out.println("memBean say that HEAP memory Init= "
				+ (memUseHeap.getInit() / 1024) + " kb \n");

		System.out.println("memBean say that NON HEAP memory Max= "
				+ (memUseNonHeap.getMax() / 1024) + " kb");
		System.out.println("memBean say that NON HEAP memory Used= "
				+ (memUseNonHeap.getUsed() / 1024) + " kb");
		System.out.println("memBean say that NON HEAP memory Init= "
				+ (memUseNonHeap.getInit() / 1024) + " kb \n");

		System.out.println("runtime total mem "
				+ Runtime.getRuntime().totalMemory() / 1024 + " kb");
		System.out.println("runtime free mem "
				+ Runtime.getRuntime().freeMemory() / 1024 + " kb");
		System.out.println("runtime max mem "
				+ Runtime.getRuntime().maxMemory() / 1024 + " kb");
		System.out.println("runtime used mem "
				+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
						.freeMemory()) / 1024 + " kb");

		System.out.println(getOsArch());
		System.out.println((getAvailableMem() / 1024) + " kb");
		System.out
				.println("-----------------------------------------------------------");
		}

	}

	public long getUsedMem() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
				.freeMemory());
	}

	public long getAvailableMem() {
		return (getMaxMem() - getUsedMem());
	}

	public int getProcCount() {
		return Runtime.getRuntime().availableProcessors();
	}

	public long getMaxMem() {
		return Runtime.getRuntime().maxMemory();

	}

	public String getOsArch() {

		return (System.getProperty("os.name") + "/" + System
				.getProperty("os.arch"));

	}

	public static   String getDesc() {

		return desc;
	}

	public Collection<Branch> getBranches() {

		return ClassFactory.getInstance().getBranches();
	}

	
	public static  void setDesc(String d) {
		desc = d;
	}
	public long getHomeUsedRemote() {
		
		ClassFactory cf = ClassFactory.getInstance();
		String hf = cf.getHomeRemote();
		File directory = new File(hf);
		Long size = folderSize(directory);
		return size;
	}
	
	public long getHomeUsedLive() {
		
		ClassFactory cf = ClassFactory.getInstance();
		String hf = cf.getHomeLive();
		File directory = new File(hf);
		Long size = folderSize(directory);
		return size;
	}
	private long folderSize(File directory){
		long length = 0;
	    for (File file : directory.listFiles()) {
	        if (file.isFile())
	            length += file.length();
	        else
	            length += folderSize(file);
	    }
	    return length;
	}

	public static String getNodeName() {
		return nodeName;
	}

	public static void setNodeName(String nodeName) {
		LoadInfo.nodeName = nodeName;
	}

}
