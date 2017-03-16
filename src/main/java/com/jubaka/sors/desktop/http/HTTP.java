package com.jubaka.sors.desktop.http;

import java.io.Serializable;

import com.jubaka.sors.desktop.http.protocol.tcp.TCP;
import org.jnetpcap.packet.AbstractMessageHeader.MessageType;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Http.Request;
import org.jnetpcap.protocol.tcpip.Http.Response;


public abstract class HTTP extends TCP implements Serializable {

	protected Long httpHeaderPointer;
	protected Long httpDataPointer;

	public Long getHttpDataPointer() {
		return httpDataPointer;
	}

	public void setHttpDataPointer(Long httpDataPointer) {
		this.httpDataPointer = httpDataPointer;
	}

	public Long getHttpHeaderPointer() {
		return httpHeaderPointer;
	}

	public void setHttpHeaderPointer(Long httpHeaderPointer) {
		this.httpHeaderPointer = httpHeaderPointer;
	}


	public static HTTP build(Http http)  {
		if (http.getMessageType() == MessageType.REQUEST) {
			HTTPRequest request = new HTTPRequest();
			request.setAccept(http.fieldValue(Request.Accept));
			request.setAccept_Charset(http.fieldValue(Request.Accept_Charset));
			request.setAccept_Encoding(http.fieldValue(Request.Accept_Encoding));
			request.setAccept_Language(http.fieldValue(Request.Accept_Language));
			request.setAccept_Ranges(http.fieldValue(Request.Accept_Ranges));
			request.setAuthorization(http.fieldValue(Request.Authorization));
			request.setCache_Control(http.fieldValue(Request.Cache_Control));
			request.setConnection(http.fieldValue(Request.Connection));
			request.setContent_Length(http.fieldValue(Request.Content_Length));
			request.setContent_Type(http.fieldValue(Request.Content_Type));
			request.setCookie(http.fieldValue(Request.Cookie));
			request.setDate(http.fieldValue(Request.Date));
			request.setHost(http.fieldValue(Request.Host));
			request.setIf_Modified_Since(http.fieldValue(Request.If_Modified_Since));
			request.setIf_None_Match(http.fieldValue(Request.If_None_Match));
			request.setProxy_Connection(http.fieldValue(Request.Proxy_Connection));
			request.setReferer(http.fieldValue(Request.Referer));
			request.setRequestMethod(http.fieldValue(Request.RequestMethod));
			request.setRequestUrl(http.fieldValue(Request.RequestUrl));
			request.setRequestVersion(http.fieldValue(Request.RequestVersion));
			request.setUA_CPU(http.fieldValue(Request.UA_CPU));
			request.setUser_Agent(http.fieldValue(Request.User_Agent));
			return request;
			
		}
		if (http.getMessageType() == MessageType.RESPONSE) {
			HTTPResponse response = new HTTPResponse();
			response.setAccept_Ranges(http.fieldValue(Response.Accept_Ranges));
			response.setAge(http.fieldValue(Response.Age));
			response.setAllow(http.fieldValue(Response.Allow));
			response.setCache_Control(http.fieldValue(Response.Cache_Control));
			response.setContent_Disposition(http.fieldValue(Response.Content_Disposition));
			response.setContent_Encoding(http.fieldValue(Response.Content_Encoding));
			response.setContent_Length(http.fieldValue(Response.Content_Length));
			response.setContent_Location(http.fieldValue(Response.Content_Location));
			response.setContent_MD5(http.fieldValue(Response.Content_MD5));
			response.setContent_Range(http.fieldValue(Response.Content_Range));
			response.setContent_Type(http.fieldValue(Response.Content_Type));
			response.setExpires(http.fieldValue(Response.Expires));
			response.setRequestUrl(http.fieldValue(Response.RequestUrl));
			response.setRequestVersion(http.fieldValue(Response.RequestVersion));
			response.setResponseCode(http.fieldValue(Response.ResponseCode));
			response.setResponseCodeMsg(http.fieldValue(Response.ResponseCodeMsg));
			response.setServer(http.fieldValue(Response.Server));
			response.setSet_Cookie(http.fieldValue(Response.Set_Cookie));
			return response;
		}
		return null;
	}

	public boolean isRequest() {
		if (this instanceof HTTPRequest) {
			return true;
		}
		return false;
	}

	public boolean isResponse() {
		if (this instanceof HTTPResponse) {
			return true;
		}
		return false;
	}

	public HTTPRequest castToRequest() {
		return (HTTPRequest) this;
	}

	public HTTPResponse castToResponse() {
		return (HTTPResponse) this;
	}

}
