package com.jubaka.sors.serverSide.dbManagement;

import com.jubaka.sors.beans.InfoBean;
import com.jubaka.sors.serverSide.Node;
import com.jubaka.sors.serverSide.User;
import com.jubaka.sors.serverSide.UserBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class DBManager {
	private Connection dbCon = null;

	public DBManager(String host, String port, String dbName, Properties props) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbCon = DriverManager.getConnection("jdbc:mysql://" + host + ":"
					+ Integer.valueOf(port) + "/" + dbName, props);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int addUser(User u) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String joinDate = sdf.format(u.getJoinDate());
		String lastLogin = sdf.format(u.getLastLogin());
		try {
			PreparedStatement pStat = dbCon
					.prepareStatement(
							"insert into Users (nickName, firstName, secondName, phone, email, pass, joinDate, lastLogin, online,image) "
									+ " values ('"
									+ u.getNickName()
									+ "','"
									+ u.getFirstName()
									+ "','"
									+ u.getSecondName()
									+ "', '"
									+ u.getPhone()
									+ "', '"
									+ u.getEmail()
									+ "','"
									+ u.getPass()
									+ "','"
									+ joinDate
									+ "','"
									+ lastLogin + "',1,'" + u.getImage() + "')",
							Statement.RETURN_GENERATED_KEYS);

			pStat.executeUpdate();
			ResultSet key = pStat.getGeneratedKeys();
			if (key.next()) {
				Integer userKey = key.getInt(1);
				key.close();
				pStat.close();
				return userKey;
			}
			pStat.close();
			return -1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public User getUserByNick(String nickName) {
		try {
			Statement stat = dbCon.createStatement();
			ResultSet res;

			res = stat.executeQuery("select * from Users where nickName='"
					+ nickName + "';");

			if (res.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss.S");
				User u = new User();

				u.setNickName(res.getString("nickName"));
				u.setFirstName(res.getString("firstName"));
				u.setSecondName(res.getString("secondName"));
				u.setEmail(res.getString("email"));
				u.setPhone(res.getString("phone"));
				u.setPass(res.getString("pass"));
				u.setJoinDate(sdf.parse(res.getString("joinDate")));
				u.setLastLogin(sdf.parse(res.getString("lastLogin")));
				u.setImage(res.getString("image"));
				String online = res.getString("online");
				if (online.equals("1"))
					u.setOnline(true);
				if (online.equals("0"))
					u.setOnline(true);
				stat.close();
				return u;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean addNewNode(Node n) {
		try {
		InfoBean ib = n.getInfo();
		String userName = ib.getOwner();
		User u = UserBase.getInstance().getUser(userName);
		Statement stat = dbCon.createStatement();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH-mm-ss");
		
			stat.executeQuery("insert into Nodes (id, name, owner, note, osArch, procCount, maxMem, rdc, rds, ipAddr, lastConnect, active)"+
							"values ("+n.getUnid()+",'"+ib.getNodeName()+"','"+u.getKey()+"','"+ib.getDesc()+"','"+ib.getOsArch()+"',"+ib.getProcCount()+","+ib.getMaxMem()+","+ib.getReceivedDumpCount()+","+ib.getReceivedDumpSize()+",'"+n.getAddr().getHostAddress()+"','"+sdf.format(new Date())+"',1");
		stat.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	
	public boolean checkUnidExest(Long unid) {
		try {
		Statement stat = dbCon.createStatement();
		
			ResultSet res = stat.executeQuery("select id from Nodes where id="+unid);
			if (res.next()) {
				stat.close();
				return true;
			}
		stat.close();
		return false;
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return false; 
	
	}

}
