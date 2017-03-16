package com.jubaka.sors.appserver.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class jquery_test
 */
@WebServlet("/jquery_test")
public class jquery_test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public jquery_test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> params = request.getParameterNames();
		try {
		while (true) {
			String name = params.nextElement();
			System.out.println(name+" = "+request.getParameter(name));
		}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		response.getWriter().println("This is your resp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
