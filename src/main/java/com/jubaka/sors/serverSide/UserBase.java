package com.jubaka.sors.serverSide;

import com.jubaka.sors.entities.User;

import java.util.HashMap;

public class UserBase {
	private static UserBase inst=null;
	private HashMap<String, User> userBase =new  HashMap<String, User>();
	private UserBase() {
	}
	public boolean addUser(User u) {
			// if  mysql == null
	//	DBManager dbm = ConnectionHandler.getDbManager();
	//	int userKey =   dbm.addUser(u);
	//	if (userKey<0) return false;
	//	u.setKey(userKey);
		userBase.put(u.getNickName(), u);
		return true;
		
	}
	public static UserBase getInstance() {
		if (inst==null) {
			inst=new UserBase();
		}
		return inst;
	}
	
	public User getUser(String nickName) {
		User uObj = userBase.get(nickName);
		if (uObj==null) {
		//	if mysql == null 
		//	DBManager dbm = ConnectionHandler.getDbManager();
		//	uObj = dbm.getUserByNick(nickName);
		}
		return uObj;
		
	}

}
