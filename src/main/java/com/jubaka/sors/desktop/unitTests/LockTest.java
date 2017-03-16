package com.jubaka.sors.desktop.unitTests;

import java.util.HashSet;


public class LockTest {
public HashSet<String> set = new HashSet<String>();
	public static void main(String[] args) {
		LockTest l = new LockTest();
		l.set.add("dsffddddddddddddddddd");
		someClass sc = new someClass(l.set);
		sc.someInit();
		System.out.println(l.set);
		
	}
	
	

}
class someClass {
	private HashSet<String> setIn;
	public someClass(HashSet<String> in) {
		this.setIn = in;
		
	}
	public void someInit() {
	//	setIn= new HashSet<String>();
		setIn.add("sdfgsdfgsdfg");
		System.out.println(setIn);
	}
}