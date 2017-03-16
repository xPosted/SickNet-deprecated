package com.jubaka.sors.appserver.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StreamTest
 */
@WebServlet("/StreamTest")
public class StreamTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StreamTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		String nodeName = request.getParameter("nodeServerEndpoint");
		Integer brId =  Integer.parseInt( request.getParameter("brID"));
		String sorsPath = request.getParameter("path");
		String fileName = request.getParameter("fileName");
		NodeServerEndpoint nodeServerEndpoint =ConnectionHandler.getInstance().getNodeServerEndPoint(nodeName);
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		nodeServerEndpoint.getFile(brId, sorsPath, response);
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
