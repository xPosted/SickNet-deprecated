package com.jubaka.sors.appserver.serverSide;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Nodes
 */
@WebServlet("/Nodes")
public class Nodes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Nodes() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void handle(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*
    	ConnectionHandler ch = ConnectionHandler.getInstance();
		SecurityVisor sv = ch.getSv();
		String userName = (String) request.getSession().getAttribute("user");
		String pub = (String) request.getParameter("public");
		if (pub==null) pub="false";
		if (userName==null) {
			request.getRequestDispatcher("sors.xhtml").forward(request, response);
			return;
		}
		if (pub.equals("true")) {
			userName="*";
		}
		Set<NodeServerEndpoint> nodeServerEndpoints = sv.getNodes(userName);
		 Set<InfoBean> ibset = new HashSet<InfoBean>();

		 for (NodeServerEndpoint n : nodeServerEndpoints) {
			 ibset.add(n.getInfo());
		 }
		 request.setAttribute("InfoBeanSet", ibset);
		 
		 if (pub.equalsIgnoreCase("true"))
			 request.getRequestDispatcher("IgorDesigned.jsp?public=true").forward(request, response);
		 else 
			 request.getRequestDispatcher("IgorDesigned.jsp?public=false").forward(request, response);

    	*/
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
		
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
		
	}

}
