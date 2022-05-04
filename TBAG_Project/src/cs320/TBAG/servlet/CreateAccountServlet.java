package cs320.TBAG.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs320.TBAG.database.DerbyDatabase;

public class CreateAccountServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("doGet");
		req.getRequestDispatcher("/_view/createAccount.jsp").forward(req, resp);
	}
		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		System.out.println("doPost");
		session = req.getSession();
		System.out.println(req.getParameter("username"));
		if(req.getParameter("username").equals("")  || req.getParameter("password").equals("") || req.getParameter("confirmPassword").equals("")) {
			System.out.println("else3");
			req.setAttribute("errorMessage", "Enter a Username and your password twice");
			
			req.getRequestDispatcher("/_view/createAccount.jsp").forward(req, resp);
		}
		
		if(req.getParameter("username") != null  && req.getParameter("password") != null && req.getParameter("confirmPassword")!= null) {
			
			if(req.getParameter("password").equals(req.getParameter("confirmPassword"))) {
				System.out.println("if");
				DerbyDatabase db = new DerbyDatabase();
				db.insertAccount(req.getParameter("username"), req.getParameter("password"));
				
				
				/*session.setAttribute("saveUsername", req.getParameter("username"));
				session.setAttribute("savePassword", req.getParameter("password"));*/
				req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
			}
			else {
				System.out.println("else1");
				req.setAttribute("errorMessage", "Passwords do not match");
				req.getRequestDispatcher("/_view/createAccount.jsp").forward(req, resp);
			}
		}
		
		else {
			System.out.println("else2");
			req.setAttribute("errorMessage", "Enter a Username and your password twice");
			
			req.getRequestDispatcher("/_view/createAccount.jsp").forward(req, resp);
		}
		
	}
	
}