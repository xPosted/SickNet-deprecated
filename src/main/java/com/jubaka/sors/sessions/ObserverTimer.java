package com.jubaka.sors.sessions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ObserverTimer extends TimerTask {
	
	private Set<CustomObserver> set = Collections.synchronizedSet(new HashSet<CustomObserver>());
	private static ObserverTimer instance = null;
	private static Timer timer = new Timer();
	
	public static ObserverTimer getinstance() {
		if (instance == null) {
			instance = new ObserverTimer();
			timer.schedule(instance, 200, 200);
			
		}
		return instance;
	}
	
	private ObserverTimer() {
		
	}
	
	public synchronized void addObserver(CustomObserver obs) {
		
			set.add(obs);
		
		
	}
	
	@Override
	public void run() {
		synchronized (set) {
			for (CustomObserver obs : set) {
				obs.customUpdate();
			}
		}
		
		
	}

}
