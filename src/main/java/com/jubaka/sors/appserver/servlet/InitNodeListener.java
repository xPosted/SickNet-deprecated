package com.jubaka.sors.appserver.serverSide;

import com.jubaka.sors.appserver.managed.ServerArgumentsBean;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InitNodeListener
 */
@Named
@ApplicationScoped
@WebServlet("/InitNodeListener")
	public class InitNodeListener extends HttpServlet {
	@Inject
	private ConnectionHandler ch;
	@Inject
	private ServerArgumentsBean args;

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitNodeListener() {

        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		ch.initConnectionListener(args.getPortListenTo());
	//	ch.initDBManager();
		System.out.println("NodeListener started");
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
