package com.jubaka.sors.appserver.serverSide.bean;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class StreamTransportBean {
	
private Integer id =0;
private ObjectInputStream ois =null;
private ObjectOutputStream oos = null;
private Socket s = null;


public ObjectInputStream getOis() {
	return ois;
}
public void setOis(ObjectInputStream ois) {
	this.ois = ois;
}
public Socket getS() {
	return s;
}
public void setS(Socket s) {
	this.s = s;
}
public ObjectOutputStream getOos() {
	return oos;
}
public void setOos(ObjectOutputStream oos) {
	this.oos = oos;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
}
