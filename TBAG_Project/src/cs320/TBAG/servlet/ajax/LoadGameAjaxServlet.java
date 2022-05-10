package cs320.TBAG.servlet.ajax;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cs320.TBAG.database.DerbyDatabase;

import cs320.TBAG.database.DerbyDatabase;

public class LoadGameAjaxServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("createAccountAjaxdoGet");
		doPost(req,resp);
	}
		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		DerbyDatabase db = new DerbyDatabase();
		ArrayList<Integer> gameIDs = new ArrayList<Integer>();
		System.out.println("CreateAccountAjaxdoPost");
		session = req.getSession();
		System.out.println(req.getParameter("username"));
		if(session.getAttribute("playerID")==null) {
			resp.setContentType("plain/text");
			resp.getWriter().write("login");
			resp.getWriter().close();
		}
		else {
			//gameIDs = db.getGamesbyPlayerID();
		}
	}
	
}