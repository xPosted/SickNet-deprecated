package com.jubaka.sors.serverSide;

import com.jubaka.sors.beans.branch.BranchBean;
import com.jubaka.sors.beans.branch.IPItemBean;
import com.jubaka.sors.beans.branch.SubnetBean;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BuildSessionsTemplate
 */
@WebServlet("/BuildSessionsTemplate")
public class BuildSessionsTemplate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildSessionsTemplate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		try {
			
			
			String nodeName = request.getParameter("nodeName");
			Map<String, String[]> params = request.getParameterMap();
				if (params.containsKey("branchId")) {
					Integer brId = Integer.parseInt(request.getParameter("branchId"));
					NodeServerEndpoint nodeServerEndpoint = ConnectionHandler.getInstance().getNodeServerEndPoint(nodeName);
					BranchBean bb = nodeServerEndpoint.getBranch(brId);
					request.setAttribute("branchBean", bb);
					if (params.containsKey("subnet")) {
						InetAddress netAddr = InetAddress.getByName(request.getParameter("subnet"));
						SubnetBean sb = bb.getSubnetByAddr(netAddr);
						request.setAttribute("subnetBean", sb);
						if (params.containsKey("ipaddr")) {
							IPItemBean ipInfo = sb.getIpByStr(request.getParameter("ipaddr"));
							request.setAttribute("ipBean", ipInfo);
						}
					}
				}
			
			request.getRequestDispatcher("SessionsTemplate.jsp").forward(request, response);	
			} catch (Exception e) {
				e.printStackTrace();
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
