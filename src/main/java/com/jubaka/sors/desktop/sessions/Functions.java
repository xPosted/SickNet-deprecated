package com.jubaka.sors.desktop.sessions;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;

public class Functions {
	public InetAddress maxDataDownload(
			HashMap<InetAddress, HashSet<Session>> input) {
		HashMap<InetAddress, Long> dataUsageMap = buildIPdataUsageMap(input);
		HashSet<InetAddress> keys = (HashSet<InetAddress>) dataUsageMap
				.keySet();
		InetAddress max;
		long maxVal = 0;
		long minVal = 0;
		InetAddress min;
		min = keys.iterator().next();
		max = min;
		maxVal = dataUsageMap.get(max);
		minVal = dataUsageMap.get(min);

		for (InetAddress item : keys) {
			if (dataUsageMap.get(item) > maxVal) {
				max = item;
				maxVal = dataUsageMap.get(item);
			}

		}
		return max;

	}

	public InetAddress minDataDownload(
			HashMap<InetAddress, HashSet<Session>> input) {
		HashMap<InetAddress, Long> dataUsageMap = buildIPdataUsageMap(input);
		HashSet<InetAddress> keys = (HashSet<InetAddress>) dataUsageMap
				.keySet();
		InetAddress max;
		long maxVal = 0;
		long minVal = 0;
		InetAddress min;
		min = keys.iterator().next();
		max = min;
		maxVal = dataUsageMap.get(max);
		minVal = dataUsageMap.get(min);

		for (InetAddress item : keys) {

			if (dataUsageMap.get(item) < minVal) {
				min = item;
				minVal = dataUsageMap.get(item);
			}
		}
		return max;
		
		
	}

	private HashMap<InetAddress, Long> buildIPdataUsageMap(
			HashMap<InetAddress, HashSet<Session>> input) {
		HashMap<InetAddress, Long> res = new HashMap<InetAddress, Long>();
		HashSet<InetAddress> keys = (HashSet<InetAddress>) input.keySet();
		for (InetAddress item : keys) {
			HashSet<Session> sesMas = input.get(item);
			long count = 0;
			for (Session subItem : sesMas) {
				count = subItem.getDstDataLen() + count;
			}
			res.put(item, count);
		}
		return res;
	}

}
