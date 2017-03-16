package com.jubaka.sors.desktop.http;

import java.io.Serializable;

public class HTTPRequest extends HTTP implements Serializable {
	private String Accept; 
 
    private String Accept_Charset; 
    
    private String Accept_Encoding; 
    
    private String Accept_Language; 
    
    private String Accept_Ranges; 
    
    private String Authorization; 
    
    private String Cache_Control; 
    
    private String Connection; 
    
    private String Content_Length; 
    
    private String Content_Type; 
    
    private String Cookie; 
    
    private String Date; 
    
    private String Host; 
    
    private String If_Modified_Since; 
   
    private String If_None_Match; 
    
    private String Proxy_Connection;
    
    private String Referer; 
    
    private String RequestMethod; 
    
    private String RequestUrl; 
    
    private String RequestVersion; 
   
    private String UA_CPU;
   
    private String User_Agent; 
    
	
    public HTTPRequest() {
    	
    }


	public String getAccept() {
		return Accept;
	}



	public void setAccept(String accept) {
		Accept = accept;
	}



	public String getAccept_Charset() {
		return Accept_Charset;
	}



	public void setAccept_Charset(String accept_Charset) {
		Accept_Charset = accept_Charset;
	}



	public String getAccept_Encoding() {
		return Accept_Encoding;
	}



	public void setAccept_Encoding(String accept_Encoding) {
		Accept_Encoding = accept_Encoding;
	}



	public String getAccept_Language() {
		return Accept_Language;
	}



	public void setAccept_Language(String accept_Language) {
		Accept_Language = accept_Language;
	}



	public String getAccept_Ranges() {
		return Accept_Ranges;
	}



	public void setAccept_Ranges(String accept_Ranges) {
		Accept_Ranges = accept_Ranges;
	}



	public String getAuthorization() {
		return Authorization;
	}



	public void setAuthorization(String authorization) {
		Authorization = authorization;
	}



	public String getCache_Control() {
		return Cache_Control;
	}



	public void setCache_Control(String cache_Control) {
		Cache_Control = cache_Control;
	}



	public String getConnection() {
		return Connection;
	}



	public void setConnection(String connection) {
		Connection = connection;
	}



	public String getContent_Length() {
		return Content_Length;
	}



	public void setContent_Length(String content_Length) {
		Content_Length = content_Length;
	}



	public String getContent_Type() {
		return Content_Type;
	}



	public void setContent_Type(String content_Type) {
		Content_Type = content_Type;
	}



	public String getCookie() {
		return Cookie;
	}



	public void setCookie(String cookie) {
		Cookie = cookie;
	}



	public String getDate() {
		return Date;
	}



	public void setDate(String date) {
		Date = date;
	}



	public String getHost() {
		return Host;
	}



	public void setHost(String host) {
		Host = host;
	}



	public String getIf_Modified_Since() {
		return If_Modified_Since;
	}



	public void setIf_Modified_Since(String if_Modified_Since) {
		If_Modified_Since = if_Modified_Since;
	}



	public String getIf_None_Match() {
		return If_None_Match;
	}



	public void setIf_None_Match(String if_None_Match) {
		If_None_Match = if_None_Match;
	}



	public String getProxy_Connection() {
		return Proxy_Connection;
	}



	public void setProxy_Connection(String proxy_Connection) {
		Proxy_Connection = proxy_Connection;
	}



	public String getReferer() {
		return Referer;
	}



	public void setReferer(String referer) {
		Referer = referer;
	}



	public String getRequestMethod() {
		return RequestMethod;
	}



	public void setRequestMethod(String requestMethod) {
		RequestMethod = requestMethod;
	}



	public String getRequestUrl() {
		return RequestUrl;
	}



	public void setRequestUrl(String requestUrl) {
		RequestUrl = requestUrl;
	}



	public String getRequestVersion() {
		return RequestVersion;
	}



	public void setRequestVersion(String requestVersion) {
		RequestVersion = requestVersion;
	}



	public String getUA_CPU() {
		return UA_CPU;
	}



	public void setUA_CPU(String uA_CPU) {
		UA_CPU = uA_CPU;
	}



	public String getUser_Agent() {
		return User_Agent;
	}



	public void setUser_Agent(String user_Agent) {
		User_Agent = user_Agent;
	}

	public String getFullRequestUrl() {
		return getHost()+"/"+getRequestUrl();
	}
}
