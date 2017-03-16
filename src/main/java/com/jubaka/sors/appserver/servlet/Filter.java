package com.jubaka.sors.appserver.serverSide;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jubaka.sors.appserver.beans.branch.IPItemBean;

/**
 * Servlet implementation class Filter
 */
@WebServlet("/Filter")
public class Filter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Filter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hello from FIlter");
		   IPItemBean ipInfo = (IPItemBean) request.getSession().getAttribute("ipInfo");
			String io = request.getParameter("IOfilter");
		    String state = request.getParameter("StateFilter");
		    if ((io.equals("INPUT")) && (state.equals("Active"))) {
		    	request.getSession().setAttribute("sesView", ipInfo.getActiveInSes());
		    }
		    
		    if ((io.equals("INPUT")) && (state.equals("Saved"))) {
		    	request.getSession().setAttribute("sesView", ipInfo.getStoredInSes());
		    }
		    
		    if ((io.equals("OUTPUT")) && (state.equals("Active"))) {
		    	request.getSession().setAttribute("sesView", ipInfo.getActiveOutSes());
		    }
			
		    if ((io.equals("OUTPUT")) && (state.equals("Saved"))) {
		    	request.getSession().setAttribute("sesView", ipInfo.getStoredOutSes());
			}
		    getServletContext().getRequestDispatcher("/One.jsp")
			.forward(request, response);
		
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().println( (request.getParameter("IOfilter")));
	}

}
