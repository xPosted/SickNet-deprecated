package com.jubaka.sors.tcpAnalyse.httpView;

import com.jubaka.sors.protocol.http.HTTP;
import com.jubaka.sors.protocol.http.HTTPRequest;
import com.jubaka.sors.protocol.http.HTTPResponse;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import java.awt.SystemColor;
import javax.swing.border.CompoundBorder;
import java.awt.Color;

public class HTTPPanelBody extends JPanel {

	/**
	 * Create the panel.
	 */
	public HTTPPanelBody(HTTP http) {
		setBackground(UIManager.getColor("Button.light"));
		setVisible(false);
		setBorder(new CompoundBorder(new EmptyBorder(0, 70, 0, 0), new LineBorder(new Color(163, 184, 204), 1, true)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		if (http instanceof HTTPRequest) {
			HTTPRequest httpRq = (HTTPRequest) http;
			String ua = httpRq.getUser_Agent();
			if (ua!=null) {
				PanelBodyItem pb = new PanelBodyItem("User Agent", ua);
				add(pb);
			}
			String accept = httpRq.getAccept();
			if (accept!=null) {
				PanelBodyItem pb = new PanelBodyItem("Accept", accept);
				add(pb);
			}
			String acceptChSet = httpRq.getAccept_Charset();
			if (acceptChSet!=null) {
				PanelBodyItem pb = new PanelBodyItem("Accept Charset", acceptChSet);
				add(pb);
			}
			String acceptEncoding = httpRq.getAccept_Encoding();
			if (acceptEncoding!=null) {
				PanelBodyItem pb = new PanelBodyItem("Accept Encoding", acceptEncoding);
				add(pb);
			}
			String acceptLenguage = httpRq.getAccept_Language();
			if (acceptLenguage!=null) {
				PanelBodyItem pb = new PanelBodyItem("Accept Lenguage", acceptLenguage);
				add(pb);
			}
			String acceptRanges = httpRq.getAccept_Ranges();
			if (acceptRanges!=null) {
				PanelBodyItem pb = new PanelBodyItem("Accept Ranges", acceptRanges);
				add(pb);
			}
			String authorization = httpRq.getAuthorization();
			if (authorization!=null) {
				PanelBodyItem pb = new PanelBodyItem("Authorization", authorization);
				add(pb);
			}
			String cacheCntr = httpRq.getCache_Control();
			if (cacheCntr!=null) {
				PanelBodyItem pb = new PanelBodyItem("Cache Control", cacheCntr);
				add(pb);
			}
			String connection = httpRq.getConnection();
			if (connection!=null) {
				PanelBodyItem pb = new PanelBodyItem("Connection", connection);
				add(pb);
			}
			String contentLen = httpRq.getContent_Length();
			if (contentLen!=null) {
				PanelBodyItem pb = new PanelBodyItem("Content Length", contentLen);
				add(pb);
			}
			String contentType = httpRq.getContent_Type();
			if (contentType!=null) {
				PanelBodyItem pb = new PanelBodyItem("Content Type", contentType);
				add(pb);
			}
			String cookie = httpRq.getCookie();
			if (cookie!=null) {
				PanelBodyItem pb = new PanelBodyItem("Cookie", cookie);
				add(pb);
			}
			String date = httpRq.getDate();
			if (date!=null) {
				PanelBodyItem pb = new PanelBodyItem("Date", date);
				add(pb);
			}
			String host = httpRq.getHost();
			if (host!=null) {
				PanelBodyItem pb = new PanelBodyItem("Host", host);
				add(pb);
			}
			String modifiedSince = httpRq.getIf_Modified_Since();
			if (modifiedSince!=null) {
				PanelBodyItem pb = new PanelBodyItem("If Modified Since", modifiedSince);
				add(pb);
			}
			String ifNoneMatch = httpRq.getIf_None_Match();
			if (ifNoneMatch!=null) {
				PanelBodyItem pb = new PanelBodyItem("If None match", ifNoneMatch);
				add(pb);
			}
			String proxy = httpRq.getProxy_Connection();
			if (proxy!=null) {
				PanelBodyItem pb = new PanelBodyItem("Proxy Connection", proxy);
				add(pb);
			}
			String referer = httpRq.getReferer();
			if (referer!=null) {
				PanelBodyItem pb = new PanelBodyItem("Referer", referer);
				add(pb);
			}
			String reqMethod = httpRq.getRequestMethod();
			if (reqMethod!=null) {
				PanelBodyItem pb = new PanelBodyItem("Request Method", reqMethod);
				add(pb);
			}
			String reqVer = httpRq.getRequestVersion();
			if (reqVer!=null) {
				PanelBodyItem pb = new PanelBodyItem("Request Version", reqVer);
				add(pb);
			}
			String uaCPU = httpRq.getUA_CPU();
			if (uaCPU!=null) {
				PanelBodyItem pb = new PanelBodyItem("UA CPU", uaCPU);
				add(pb);
			}
			
		}
		if (http instanceof HTTPResponse) {
			HTTPResponse httpResp = (HTTPResponse) http;

			String acceptRanges = httpResp.getAccept_Ranges();
			if (acceptRanges!=null) {
				PanelBodyItem pb = new PanelBodyItem("Accept Ranges", acceptRanges);
				add(pb);
			}
			String age = httpResp.getAge();
			if (age!=null) {
				PanelBodyItem pb = new PanelBodyItem("Age", age);
				add(pb);
			}
			String allow = httpResp.getAllow();
			if (allow!=null) {
				PanelBodyItem pb = new PanelBodyItem("Allow", allow);
				add(pb);
			}
			String CacheCntr = httpResp.getCache_Control();
			if (CacheCntr!=null) {
				PanelBodyItem pb = new PanelBodyItem("Cache Control", CacheCntr);
				add(pb);
			}
			String contentDispo = httpResp.getContent_Disposition();
			if (contentDispo!=null) {
				PanelBodyItem pb = new PanelBodyItem("Content Disposion", contentDispo);
				add(pb);
			}
			String contentEnc = httpResp.getContent_Encoding();
			if (contentEnc!=null) {
				PanelBodyItem pb = new PanelBodyItem("Content Encoding", contentEnc);
				add(pb);
			}
			String contentLen = httpResp.getContent_Length();
			if (contentLen!=null) {
				PanelBodyItem pb = new PanelBodyItem("Content Length", contentLen);
				add(pb);
			}
			String contentLocation = httpResp.getContent_Location();
			if (contentLocation!=null) {
				PanelBodyItem pb = new PanelBodyItem("Content Location", contentLocation);
				add(pb);
			}
			String contentMd5 = httpResp.getContent_MD5();
			if (contentMd5!=null) {
				PanelBodyItem pb = new PanelBodyItem("Content Md5", contentMd5);
				add(pb);
			}
			String contentRange= httpResp.getContent_Range();
			if (contentRange!=null) {
				PanelBodyItem pb = new PanelBodyItem("Content Range", contentRange);
				add(pb);
			}
			String contentType= httpResp.getContent_Type();
			if (contentType!=null) {
				PanelBodyItem pb = new PanelBodyItem("Content Type", contentType);
				add(pb);
			}
			String expires= httpResp.getExpires();
			if (expires!=null) {
				PanelBodyItem pb = new PanelBodyItem("Expires", expires);
				add(pb);
			}
			String rUrl= httpResp.getRequestUrl();
			if (rUrl!=null) {
				PanelBodyItem pb = new PanelBodyItem("Request URL", rUrl);
				add(pb);
			}
			String rVer= httpResp.getRequestVersion();
			if (rVer!=null) {
				PanelBodyItem pb = new PanelBodyItem("Request Version", rVer);
				add(pb);
			}
			String respCode= httpResp.getResponseCode();
			if (respCode!=null) {
				PanelBodyItem pb = new PanelBodyItem("Response Code", respCode);
				add(pb);
			}
			String respCodeMsg= httpResp.getResponseCodeMsg();
			if (respCodeMsg!=null) {
				PanelBodyItem pb = new PanelBodyItem("Resp Code Msg", respCodeMsg);
				add(pb);
			}
			String server= httpResp.getServer();
			if (server!=null) {
				PanelBodyItem pb = new PanelBodyItem("Server", server);
				add(pb);
			}
			String cookie= httpResp.getSet_Cookie();
			if (cookie!=null) {
				PanelBodyItem pb = new PanelBodyItem("Set Cookie", cookie);
				add(pb);
			}
			
		}
		
		

	}
}
