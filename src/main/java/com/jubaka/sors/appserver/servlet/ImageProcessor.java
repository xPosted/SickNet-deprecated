package com.jubaka.sors.appserver.serverSide;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class ImageProcessor
 */
@MultipartConfig()
@WebServlet("/ImageProcessor")
public class ImageProcessor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String pathToImgs="/home/sanny/tmp/";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageProcessor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imgName = request.getParameter("img");
		if (imgName==null) request.getRequestDispatcher("NullHandler.jsp").forward(request, response);
		File img = new File(pathToImgs+imgName);
		FileInputStream fis = new FileInputStream(img);
		OutputStream os = response.getOutputStream();
		long curLen =0;
		long fileLen = img.length();
		int readCount =0;
		byte[] buf = new byte[4096];
		while (curLen < fileLen) {
			readCount = fis.read(buf);
			os.write(buf, 0, readCount);
			curLen += readCount;
			
		}
		fis.close();
		os.flush();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("new User POST data handling");
		for (Part p : request.getParts())
			System.out.println(p.getName());
		if (request.getPart("newImg") != null) {
			long sz = request.getPart("newImg").getSize();
			System.out.println(sz);

			Long len = request.getPart("newImg").getSize();
			BufferedInputStream is = new BufferedInputStream(request.getPart(
					"newImg").getInputStream());
			if (len > 0) {
				System.out.println("Len = " + len + " available = "
						+ is.available());
				Integer counter = 0;
				Random r = new Random();
				Integer randomInt = r.nextInt();
				if (randomInt<0) randomInt=randomInt*(-1);
				File f = new File(pathToImgs+randomInt+".jpg");
				if (f.exists()) {
					f.delete();
				
				}
				f.createNewFile();
				FileOutputStream fout = new FileOutputStream(f);
				while (counter < len) {
					byte[] smallBuf;
					byte[] buf = new byte[4096];
					int count = is.read(buf);

					counter = counter + count;
					if (count < 4096) {
						smallBuf = new byte[count];
						for (int j = 0; j < count; j++) {
							smallBuf[j] = buf[j];
						}

						fout.write(smallBuf);

					}

					fout.write(buf);
				}
				fout.close();
				response.getWriter().println(randomInt+".jpg");
				
				
			}
		}
	}

}
