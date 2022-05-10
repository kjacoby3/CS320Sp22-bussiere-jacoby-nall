package cs320.TBAG.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs320.TBAG.database.DerbyDatabase;

public class MainMenuServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("MainMenuServletdoGet");
		session = req.getSession();
		req.getRequestDispatcher("/_view/mainMenu.jsp").forward(req, resp);
	}
		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		
		
	}
	
}