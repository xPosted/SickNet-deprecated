package com.jubaka.sors.desktop.unitTests;

import java.util.ArrayList;
import java.util.List;

public class HTTPEntryPoint {

	public static void main(String[] args) {
		HTTPReq req1 = new HTTPReq();
		HTTPReq req2 = new HTTPReq();
		HTTPResp resp1 = new HTTPResp();
		List<MyHttp> https = new ArrayList<MyHttp>();
		https.add(resp1);
		https.add(req1);
		https.add(req2);
		
		for (MyHttp http : https) {
			if (http instanceof HTTPResp) {
				System.out.println("this is resp = "+ (HTTPResp) http);
			}
			if (http instanceof HTTPReq) {
				System.out.println("this is req = "+(HTTPReq)http);
			}
		}

	}

}
