package com.jubaka.sors.desktop.sessions;

import java.io.Serializable;
import java.util.HashSet;

public class SesCaptureInfo implements Serializable {

	private HashSet<Integer> inPort = new HashSet<Integer>();
	private HashSet<Integer> outPort = new HashSet<Integer>();

	public SesCaptureInfo(String srcPorts, String dstPorts) {

		srcPorts.trim();
		if (!srcPorts.equals(""))
			if (srcPorts.equals("-1"))
				addInPort(-1);
			else {

				if (srcPorts.contains(",")) {
					String[] src = srcPorts.split(",");
					for (String item : src) {

						addInPort(Integer.parseInt(item));
					}

				} else
					addInPort(Integer.parseInt(srcPorts));
			}
		dstPorts.trim();
		if (!dstPorts.equals(""))
			if (dstPorts.equals("-1"))
				addOutPort(-1);
			else {
				if (dstPorts.contains(",")) {
					String[] dst = dstPorts.split(",");
					for (String item : dst) {
						addOutPort(Integer.parseInt(item));
					}
				} else
					addOutPort(Integer.parseInt(dstPorts));

			}

	}

	public void clear() {
		inPort.clear();
		outPort.clear();
	}

	public HashSet<Integer> getInPorts() {
		return (HashSet<Integer>) inPort.clone();
	}

	public HashSet<Integer> getOutPorts() {
		return (HashSet<Integer>) outPort.clone();
	}

	public void addInPort(Integer port) {
		inPort.add(port);
	}

	public void removeInPort(Integer port) {
		inPort.remove(port);
	}

	public void addOutPort(Integer port) {
		outPort.add(port);

	}

	public void removeOutPort(Integer port) {
		outPort.remove(port);
	}

	public boolean checkInPort(Integer port) {
		if (inPort.contains(-1))
			return true;
		if (inPort.contains(port))
			return true;
		else
			return false;
	}

	public boolean checkOutPort(Integer port) {
		if (outPort.contains(-1))
			return true;
		if (outPort.contains(port))
			return true;
		else
			return false;
	}

}
