package com.jubaka.sors.serverSide;

import com.jubaka.sors.beans.SecPolicy;
import com.jubaka.sors.beans.SecPolicyBean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SecurityVisor {
	/*
	private HashMap<String, HashSet<String>> userNodeMap = new HashMap<String, HashSet<String>>(); 
	
	public  void handleNewNode(NodeServerEndpoint n) {  // add db unid exist check

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



		//  temporary unid procesing
		Long unid = n.getUnid();
		if (unid==null)  {
				unid = getNewUNID();
				n.updateUnid(unid);

		}



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
	
	public Set<NodeServerEndpoint> getPublicNodes() {
		return getNodes("*");
	}
	
	public Set<NodeServerEndpoint> getNodes(String userName) {
		HashSet<NodeServerEndpoint> nodeServerEndpoints = new HashSet<NodeServerEndpoint>();
		if (userNodeMap.get(userName)==null) return nodeServerEndpoints;
		ConnectionHandler ch = ConnectionHandler.getInstance();
		for (String n : userNodeMap.get(userName)) {
			NodeServerEndpoint nodeServerEndpoint = ch.getNode(n);
			if (nodeServerEndpoint !=null)
			nodeServerEndpoints.add(nodeServerEndpoint);
		}
		return nodeServerEndpoints;
		
	}
	*/

}
