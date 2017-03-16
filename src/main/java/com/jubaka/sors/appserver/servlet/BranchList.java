package com.jubaka.sors.appserver.serverSide;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class BranchList
 */
@WebServlet("/BranchList")
public class BranchList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BranchList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	/*	HashSet<BranchInfoBean> bigSet = new HashSet<BranchInfoBean>();
		String user = (String) request.getSession().getAttribute("user");

		Set<NodeServerEndpoint> nodeServerEndpoints = sv.getNodes(user);
		Integer ownCount = 0;
		Integer liveCount = 0;
		for (NodeServerEndpoint n : nodeServerEndpoints) {
			for (BranchInfoBean bib : n.getBranchInfoSet(user)) {
				bigSet.add(bib);
				System.out.println(bib.getUserName()+" "+user);
				if (bib.getUserName().equals(user)) ownCount++;
				if (bib.getIface()!=null) liveCount++;
			}
			
		}
		request.setAttribute("branchList", bigSet);
		request.setAttribute("ownCount", ownCount);
		request.setAttribute("nodeCount", nodeServerEndpoints.size());
		request.setAttribute("liveCount", liveCount);
		
	
		
		request.getRequestDispatcher("Processing.jsp").forward(request, response);
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
