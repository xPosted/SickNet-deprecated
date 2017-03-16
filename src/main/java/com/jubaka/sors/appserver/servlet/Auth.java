package com.jubaka.sors.appserver.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/Auth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()   
	 */
	public Auth() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @throws IOException
	 * @throws ServletException
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	private void handle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		
		System.out.println("sesID="+request.getSession().getId());
		String signout = (String) request.getParameter("signout");
		if (signout != null) {
			request.getSession().removeAttribute("user");
			request.getRequestDispatcher("sors.xhtml").forward(request, response);
			return;
		}
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		UserBase ub = UserBase.getInstance();
		User uObj = ub.getUser(user);
		if (uObj.getPass().equals(pass)) {
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("sors.xhtml").forward(request, response);
		}

		*/
		

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);

	}

}
