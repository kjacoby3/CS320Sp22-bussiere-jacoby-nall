package cs320.TBAG.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs320.TBAG.database.DerbyDatabase;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("loginServletdoGet");
		session = req.getSession();
		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
	}
		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		System.out.println("loginServletdoPost");
		session = req.getSession();
		System.out.println(session.getAttribute("saveUsername"));
		System.out.println(session.getAttribute("savePassword"));
		if(req.getParameter("username") != null && req.getParameter("password") != null) {
			DerbyDatabase db = new DerbyDatabase();
			String compare=db.selectAccountFromUsername(req.getParameter("username"));
			
			if(req.getParameter("password").equals(compare)) {
				//req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				resp.sendRedirect("/TBAG/game");
			}
			else {
				req.setAttribute("errorMessage", "Incorrect Username or Password");
				req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
			}
			
			
			
			
			/*if(req.getParameter("username").equals("username") && req.getParameter("password").equals("password")) {
				System.out.println("if");
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			}
			else if(req.getParameter("username").equals(session.getAttribute("saveUsername").toString()) && req.getParameter("password").equals(session.getAttribute("savePassword").toString())) {
				System.out.println("else if");
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			}
			else {
				System.out.println("else1");
				req.setAttribute("errorMessage", "Incorrect Username or Password");
				req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
			}*/
		}
		
		else {
			System.out.println("else2");
			req.setAttribute("errorMessage", "Enter a Username and Password");
			
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
		
	}
	
}