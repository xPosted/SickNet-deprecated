package com.jubaka.sors.serverSide;

import com.jubaka.sors.beans.SecPolicy;
import com.jubaka.sors.beans.SecPolicyBean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SecurityVisor {
	private HashMap<String, HashSet<String>> userNodeMap = new HashMap<String, HashSet<String>>(); 
	
	public  void handleNewNode(Node n) {  // add db unid exist check
		/*
		DBManager dbm = ConnectionHandler.getDbManager();
		boolean exist = true;
		Long unid = n.getUnid();
		if (unid==null)  {
			while (exist) {
				unid = getNewUNID();
				exist =  dbm.checkUnidExest(unid);
			}
			n.updateUnid(unid);
			dbm.addNewNode(n);
		}
		*/
		SecPolicyBean scb = n.getSecPolicyBean();
		 HashMap<String, SecPolicy> userPolicy =  scb.getUserPolicy();
		 for (String user: userPolicy.keySet()) {
			 if (!userNodeMap.containsKey(user)) {
				 userNodeMap.put(user, new HashSet<String>());
			 }
			 userNodeMap.get(user).add(n.getFullName());
		 }
	}
	
	
	public long getNewUNID() {
		Random r = new Random();
		Long id =(long) -1;
		while (! checkID(id)) {
			id = r.nextLong();
			if (id<0) id=id*(-1);
		}
		return id;
		
	}
	private boolean checkID(Long id) {
		if (id<0) return false;
		return true;
	}
	
	public Set<Node> getPublicNodes() {
		return getNodes("*");
	}
	
	public Set<Node> getNodes(String userName) {
		HashSet<Node> nodes = new HashSet<Node>();
		if (userNodeMap.get(userName)==null) return nodes;
		ConnectionHandler ch = ConnectionHandler.getInstance();
		for (String n : userNodeMap.get(userName)) {
			Node node = ch.getNode(n);
			if (node!=null)
			nodes.add(node);
		}
		return nodes;
		
	}

}
