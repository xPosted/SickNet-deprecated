package com.jubaka.sors.appserver.serverSide;

import com.jubaka.sors.appserver.beans.branch.IPItemBean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class OneSrv
 */
@MultipartConfig()
@WebServlet("/OneSrv")
public class OneSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OneSrv() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("action") != null) {

			if (request.getParameter("action").equals("resTst")) {
				String width = request.getParameter("width");
				String height = request.getParameter("height");
				request.getSession().setAttribute("width", width);
				request.getSession().setAttribute("height", height);
				getServletContext().getRequestDispatcher("/index.jsp").forward(
						request, response);
			}

			String ip = (String) request.getSession().getAttribute("ip");
			Socket s = new Socket(ip, 1408);
			ObjectOutputStream oos = new ObjectOutputStream(
					new BufferedOutputStream(s.getOutputStream()));
			ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(s.getInputStream()));

			String action = request.getParameter("action");
			Integer branch_id = (Integer) request.getSession().getAttribute("branch_id");
			if (action.startsWith("getSessions")) {
				String ipAddr = (action.split("_"))[1];

				try {
					oos.writeObject("getSessions_"+branch_id+"_" + ipAddr);
					oos.flush();
					IPItemBean ipInfo = (IPItemBean) ois.readObject();
					request.getSession().setAttribute("ipInfo", ipInfo);
					request.getSession().setAttribute("sesView",
							ipInfo.getStoredInSes());
					getServletContext().getRequestDispatcher("/One.jsp")
							.forward(request, response);
					return;

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			if (action.startsWith("getIPlst")) {
				String netAddr = (action.split("_"))[1];
				
				try {
					oos.writeObject("getIPdata_"+branch_id+"_" + netAddr);
					oos.flush();
					Object result = ois.readObject();
					request.getSession().setAttribute("ipDataBean", result);

					getServletContext().getRequestDispatcher("/One.jsp")
							.forward(request, response);
					return;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private static String getFilename(Part part) {

		for (String cd : part.getHeader("content-disposition").split(";")) {
			System.out.println(cd);
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1)
						.substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer branch_id =0;
		String ip = request.getParameter("ip");
		Socket s;
		ObjectOutputStream oos=null;	
		ObjectInputStream ois = null;
		
		if (ip.equals("")) 
			ip="127.0.0.1";
			
		s = new Socket(ip, 1408);
		oos = new ObjectOutputStream(
				new BufferedOutputStream(s.getOutputStream()));
		ois = new ObjectInputStream(
				new BufferedInputStream(s.getInputStream()));
		if (request.getPart("file") != null) {
			Long len = request.getPart("file").getSize();
			BufferedInputStream is = new BufferedInputStream(request.getPart(
					"file").getInputStream());
			if (len > 0) {
				System.out.println("Len = " + len + " available = "
						+ is.available());
				Integer counter = 0;

				File f = new File(
						"/home/sanny/BOOKS/EE/apache-tomcat-8.0.9/upload/upload_"
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
				response.getWriter().println("<h1>" + "Hello niga 1" + "</h1>");

				
				oos.writeObject("analyze_" + len);
				FileInputStream fin = new FileInputStream(f);
				counter = 0;
				while (counter < len) {
					byte[] smallBuf;
					byte[] buf = new byte[4096];
					int count = fin.read(buf);

					counter = counter + count;
					if (count < 4096) {
						smallBuf = new byte[count];
						for (int j = 0; j < count; j++) {
							smallBuf[j] = buf[j];
						}

						oos.write(smallBuf);

					} else
						oos.write(buf);
				}
				oos.flush();

				Integer branchID;
				try {
					System.out.println("Before read branch");
					branchID = (Integer) ois.readObject();
					System.out.println("After read branch");
				} catch (ClassNotFoundException e) {
					branchID = -2;
					e.printStackTrace();
				}
				response.getWriter().println(
						"<h1>" + "Hello niga 2, your id is (" + branchID
								+ ")</h1>");
				request.getSession().setAttribute("branch_id", branchID);
				//oos_a.writeObject("getNetList_"+branchID);
			branch_id=branchID;
			oos.close();
			ois.close();
			s.close();

			}
			

			s = new Socket(ip, 1408);
			oos = new ObjectOutputStream(
					new BufferedOutputStream(s.getOutputStream()));
			ois = new ObjectInputStream(
					new BufferedInputStream(s.getInputStream())); 
			oos.writeObject("getNetList_"+branch_id);
			oos.flush();
			List<String> netList = null;
			try {
				netList = (List<String>) ois.readObject();

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getSession().setAttribute("ip", ip);
			request.getSession().setAttribute("netlist", netList);
			request.getSession().setAttribute("ipInfo", new IPItemBean());
			getServletContext().getRequestDispatcher("/One.jsp").forward(
					request, response);
			oos.flush();
			oos.close();
			ois.close();
		

	}

	}}
