package com.jubaka.sors.desktop.unitTests;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ForemostStdinTest {

	public static void main(String[] args) throws IOException {
		
		String pathToFm = "/home/jubaka/sors/foremost";
		Process proc = Runtime.getRuntime().exec("foremost -Q -d -o "+"/home/jubaka/sors/Live/0/recTest/");
		BufferedOutputStream out = new BufferedOutputStream(proc.getOutputStream());
		
		File srcPath = new File("/home/jubaka/sors/Live/0/rawData");
		File[] rawFiles =  srcPath.listFiles();
		byte[] buf = new byte[4096];
		byte[] smallBuf = null;
		for (File f : rawFiles ) {
			if (f.isFile()) {
				FileInputStream fis = new FileInputStream(f);
				while (fis.available()>0) {
					if (fis.available()<4096) {
						smallBuf = new byte[fis.available()];
						fis.read(smallBuf);
						out.write(smallBuf);
						continue;
					}
					fis.read(buf);
					out.write(buf);
				
				}
				out.flush();
			}
		}
		out.close();

	}

}
