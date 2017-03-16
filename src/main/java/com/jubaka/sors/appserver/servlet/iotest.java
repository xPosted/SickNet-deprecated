package com.jubaka.sors.appserver.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class iotest
 */
@WebServlet("/iotest")
public class iotest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public iotest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		Random r = new Random();
		while (true) {
			try {
				Thread.sleep((r.nextInt(2000)+1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.println("getInfo");
			pw.flush();
		}
	
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST detected");
		Scanner scan = new Scanner(request.getInputStream());
		while (scan.hasNext()) {
			System.out.println(scan.nextLine());
		}
		System.out.println("fin\n\n");
		
	}

}
