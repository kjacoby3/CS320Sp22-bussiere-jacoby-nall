package cs320.TBAG.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
			
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("roomMessage", "You are in a dungeon");
		req.getRequestDispatcher("/_view/Game.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("Post");
		req.setAttribute("roomMessage", "You are in a dungeon");
		req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
		String input = req.getParameter("command");
		
		System.out.println(input);
	}
		
}
	