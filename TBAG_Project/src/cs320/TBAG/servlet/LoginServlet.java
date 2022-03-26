package cs320.TBAG.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("doGet");
		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
	}
		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		System.out.println("doPost");
		if(req.getParameter("username") != null && req.getParameter("password") != null) {
			
			if(req.getParameter("username").equals("username") && req.getParameter("password").equals("password")) {
				System.out.println("if");
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			}
			else {
				System.out.println("else1");
				req.setAttribute("errorMessage", "Incorrect Username or Password");
				req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
			}
		}
		
		else {
			System.out.println("else2");
			req.setAttribute("errorMessage", "Enter a Username and Password");
			
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
		
	}
	
}