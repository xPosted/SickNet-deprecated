package com.jubaka.sors.serverSide;

import com.jubaka.sors.beans.branch.BranchInfoBean;
import com.jubaka.sors.beans.InfoBean;
import com.jubaka.sors.beans.SesDataCapBean;
import com.jubaka.sors.beans.branch.SubnetBeanList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BranchOptions
 */
@WebServlet("/BranchOptions")
public class BranchOptions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BranchOptions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		String currentUser = (String) request.getSession().getAttribute("user");
		String obj = request.getParameter("object");
		String action = request.getParameter("action");
		Integer brId = Integer.parseInt(request.getParameter("branchId"));
		String nodeName = request.getParameter("nodeName");
		NodeServerEndpoint nodeServerEndpoint = ConnectionHandler.getInstance().getNodeServerEndPoint(nodeName);
		
		
		
		if (action!=null) {
			switch (action) {
			case "start":
				nodeServerEndpoint.startBranch(brId);
				break;

			case "stop":
				nodeServerEndpoint.stopBranch(brId);
			
				break;
			case "delData":
				
				break;
			default:
				break;
			}
			return;
		}
		
		if (obj!=null) {
			String in = request.getParameter("inP");
			String out = request.getParameter("outP");
			if ((in!=null) & (out!=null)) {
				boolean res = nodeServerEndpoint.setCapture(brId, obj, in, out);
				if (res) response.getWriter().println("success"); else 
					response.getWriter().println("error");
				return;
			}
			
			SesDataCapBean sdc = nodeServerEndpoint.getSesDataCaptureInfo(obj, brId);
			if (sdc.getObject().equals("capture_not_set")) {
				response.getWriter().println("capture_not_set");
				return;
			} 
			if (sdc.getObject().equals("address_not_found")) {
				response.getWriter().println("address_not_found");
				return;
			} 
			response.getWriter().println("in:"+sdc.getInPort()+"_out:"+sdc.getOutPort());
			return;
			
			
		}
		InfoBean ib = nodeServerEndpoint.getInfo();
		BranchInfoBean bib = nodeServerEndpoint.getBranchInfo(currentUser, brId);
		SubnetBeanList sbl = nodeServerEndpoint.getSubnetBeanList(brId);
		
		request.setAttribute("bib", bib);
		request.setAttribute("ib", ib);
		request.setAttribute("sbl", sbl);
		request.getRequestDispatcher("BranchOptions.jsp").forward(request, response);
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
