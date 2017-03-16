package com.jubaka.sors.appserver.serverSide;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Files
 */
@WebServlet("/Files")
public class Files extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Files() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
	
		String brIdStr = request.getParameter("brID");
		String path = request.getParameter("path");
		String nodeName = request.getParameter("nodeServerEndpoint");
		 if ((brIdStr == null ) || (path == null) || (nodeName == null)) {
			 return;
		 }
		 Integer brId = Integer.parseInt(brIdStr);
		 NodeServerEndpoint nodeServerEndpoint = ConnectionHandler.getInstance().getNodeServerEndPoint(nodeName);
		 FileListBean flb = nodeServerEndpoint.getDir(brId, path);
		 if (flb == null) {
			 return;
		 }
		 request.setAttribute("FileListBean", flb);
		 request.getRequestDispatcher("Files.jsp").forward(request, response);
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
