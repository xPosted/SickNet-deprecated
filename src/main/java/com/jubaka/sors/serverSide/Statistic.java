package com.jubaka.sors.serverSide;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.DefaultTableModel;

import com.jubaka.sors.beans.branch.BranchInfoBean;
import com.jubaka.sors.beans.InfoBean;
import org.jfree.data.time.TimeSeries;


/**
 * Servlet implementation class Statistic
 */
@WebServlet("/Statistic")
public class Statistic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Statistic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*

		String idStr = request.getParameter("branchId");
		String nodeName = request.getParameter("nodeName");
		String chartType = request.getParameter("type");
		String curUser = (String) request.getSession().getAttribute("user");
		Integer brId = Integer.valueOf(idStr);
		NodeServerEndpoint nodeServerEndpoint = ConnectionHandler.getInstance().getNodeServerEndPoint(nodeName);
		InfoBean ib = nodeServerEndpoint.getInfo();
		BranchInfoBean bib = nodeServerEndpoint.getBranchInfo(curUser, brId);
		
		if (chartType.equals("global")) {
			
			
			TimeSeries tsIn = nodeServerEndpoint.getDataInChart(brId);
			TimeSeries tsdOut = nodeServerEndpoint.getDataOutChart(brId,(long)-1,(long)-1);
			DefaultTableModel model = (DefaultTableModel) nodeServerEndpoint.getBaseTModel(brId);
			request.setAttribute("tsIn", tsIn);
			request.setAttribute("tsOut", tsdOut);
			request.setAttribute("TableModel", model);
			request.setAttribute("SubType", "subnet");
		}
		if (chartType.equals("net")) {
			
			String networkStr = request.getParameter("net");
			
			TimeSeries tsIn = nodeServerEndpoint.getNetworkDataInChart(brId, networkStr,(long)-1,(long)-1);
			TimeSeries tsdOut = nodeServerEndpoint.getNetworkDataOutChart(brId, networkStr);
			DefaultTableModel model = (DefaultTableModel) nodeServerEndpoint.getSubnetTModel(brId, networkStr);
			request.setAttribute("tsIn", tsIn);
			request.setAttribute("tsOut", tsdOut);
			request.setAttribute("TableModel", model);
			request.setAttribute("SubType", "subnet");
		}
		if (chartType.equals("ip")) {
			
			String ip = request.getParameter("ip");
			
			TimeSeries tsIn = nodeServerEndpoint.getIpDataInChart(brId, ip);
			TimeSeries tsdOut = nodeServerEndpoint.getIpDataOutChart(brId, ip);
			DefaultTableModel model = (DefaultTableModel) nodeServerEndpoint.getIpTModel(brId, ip);
			request.setAttribute("tsIn", tsIn);
			request.setAttribute("tsOut", tsdOut);
			request.setAttribute("TableModel", model);
			request.setAttribute("SubType", "subnet");
		}
		
		request.setAttribute("nodeInfoBean", ib);
		request.setAttribute("branchInfoBean", bib);
		
		request.getRequestDispatcher("Statistic.jsp").forward(request, response);
		
		*/
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
