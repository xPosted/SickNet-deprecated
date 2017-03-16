package com.jubaka.sors.appserver.serverSide;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BranchView
 */
@WebServlet("/BranchView")
public class BranchView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BranchView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		try {
			Map<String, String[]> params;
		if (request.getParameterMap().size() == 0)
			params = (Map<String, String[]>) request.getSession().getAttribute("lastView");
		else params = request.getParameterMap();
		String nodeName = params.get("nodeName")[0];
			if (params.containsKey("branchId")) {
				Integer brId = Integer.parseInt(params.get("branchId")[0]);
				NodeServerEndpoint nodeServerEndpoint = ConnectionHandler.getInstance().getNodeServerEndPoint(nodeName);
				BranchBean bb = nodeServerEndpoint.getBranch(brId);
				request.setAttribute("branchBean", bb);
				if (params.containsKey("subnet")) {
					InetAddress netAddr = InetAddress.getByName(params.get("subnet")[0]);
					SubnetBean sb = bb.getSubnetByAddr(netAddr);
					request.setAttribute("subnetBean", sb);
					if (params.containsKey("ipaddr")) {
						IPItemBean ipInfo = sb.getIpByStr(params.get("ipaddr")[0]);
						request.setAttribute("ipBean", ipInfo);
					}
				}
			}
		request.getSession().removeAttribute("lastView");
		request.getSession().setAttribute("lastView", params);
		request.getRequestDispatcher("BranchView.jsp").forward(request, response);	
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
