package com.jubaka.sors.serverSide;

import com.jubaka.sors.beans.branch.SessionDataBean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetSessionData
 */
@WebServlet("/GetSessionData")
public class GetSessionData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSessionData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		String direction = request.getParameter("direction");
		if (direction==null) return;
		String nodeName = request.getParameter("nodeName");
		if (nodeName==null) return;
		Integer brId = Integer.parseInt(request.getParameter("branchId"));
		if (brId==null) return;
		NodeServerEndpoint nodeServerEndpoint = ConnectionHandler.getInstance().getNodeServerEndPoint(nodeName);
		if (nodeServerEndpoint ==null) return;
		String net = request.getParameter("subnet");
		if (net==null) return;
		Long tm = Long.parseLong(request.getParameter("sessionID"));
		if (tm==null) return;
		SessionDataBean sdb = nodeServerEndpoint.getSessionData(brId, net, tm);
		
		byte[] targetData = null;
		if (direction.equals("src")) {
			targetData = sdb.getSrcData();
		}
		if (direction.equals("dst")) {
			targetData = sdb.getDstData();
		}
		if (targetData == null) {
			response.getWriter().println("<p>Selected session does not have captured data</p>");
			return;
		}
		Scanner scan = new Scanner(new ByteArrayInputStream(targetData));
		while (scan.hasNext()) {
			response.getWriter().println("<p>"+scan.nextLine()+"</p>");
		}
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
