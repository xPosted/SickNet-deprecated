package com.jubaka.sors.serverSide;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateBranch
 */
@MultipartConfig()
@WebServlet("/CreateBranch")
public class CreateBranch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateBranch() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UploadBLABLA GET");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		System.out.println("UploadBLABLA");
		if (request.getPart("pathToFile") != null) {
			long sz = request.getPart("pathToFile").getSize();
			String fileName = request.getPart("pathToFile").getSubmittedFileName();
			String bname = request.getParameter("bname");
			if (bname==null) bname="Unnamed";
			Long len = request.getPart("pathToFile").getSize();
			BufferedInputStream is = new BufferedInputStream(request.getPart(
					"pathToFile").getInputStream());
			if (len > 0) {
				System.out.println("Len = " + len + " available = "
						+ is.available());
				Integer counter = 0;

				File f = new File(
						"/home/sanny/tmp/uploadTestApache_"
								+ (new Date()));
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
				String userName = (String) request.getSession().getAttribute("user");
				response.getWriter().println("<h1>" + "Branch created on "+request.getParameter("nodeName")+" by "+userName + "</h1>");
				NodeServerEndpoint n = ConnectionHandler.getInstance().getNodeServerEndPoint(request.getParameter("nodeName"));
				
				n.createBranch(f.getAbsolutePath(),userName , fileName, bname);
				
			}
		}
		if (request.getParameter("iface")!=null) {
			String iface = request.getParameter("iface");
			String bname = request.getParameter("bname");
			if (bname==null) bname="Unnamed";
			String userName = (String) request.getSession().getAttribute("user");
			response.getWriter().println("<h1>" + "Branch created on "+request.getParameter("nodeName")+" by "+userName + "</h1>");
			NodeServerEndpoint n = ConnectionHandler.getInstance().getNodeServerEndPoint(request.getParameter("nodeName"));
			
			n.createLiveBranch(iface,userName , bname,request.getRemoteAddr());
			
		}
		
*/
	}
}
