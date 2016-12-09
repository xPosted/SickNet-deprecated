package com.jubaka.sors.serverSide;

import com.jubaka.sors.entities.User;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String pathToImgs="/home/sanny/tmp/";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		System.out.println(request.getParameter("nickNameff"));
		System.out.println(request.getParameter("fNameff"));
		System.out.println(request.getParameter("sNameff"));
		System.out.println(request.getParameter("emailff"));
		System.out.println(request.getParameter("phoneff"));
		
		
		Set<String> keys = request.getParameterMap().keySet();
		if (!keys.contains("nickNameff")) return;
		if (!keys.contains("fNameff")) return;
		if (!keys.contains("sNameff")) return;
		if (!keys.contains("emailff")) return;
		if (!keys.contains("phoneff")) return;
		if (!keys.contains("passff")) return;
		if (!keys.contains("imageff")) return;
		
		User u = new User();
		u.setNickName(request.getParameter("nickNameff"));
		u.setPass(request.getParameter("passff"));
		u.setFirstName(request.getParameter("fNameff"));
		u.setSecondName(request.getParameter("sNameff"));
		u.setEmail(request.getParameter("emailff"));
		u.setPhone(request.getParameter("phoneff"));
		u.setJoinDate(new Date());
		//u.setLastLogin(new Date());
		u.setImage(request.getParameter("imageff"));
		 boolean res = UserBase.getInstance().addUser(u);
		 if (!res) request.getRequestDispatcher("NullHandler.jsp").forward(request, response);
		request.getSession().setAttribute("user", u.getNickName());
		request.getRequestDispatcher("sors.xhtml").forward(request, response);
		*/
		
	}

}
