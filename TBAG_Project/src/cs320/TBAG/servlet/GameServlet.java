package cs320.TBAG.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cs320.TBAG.model.Map;
import cs320.TBAG.controller.GameController;
import cs320.TBAG.model.Game;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Weapon;


public class GameServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	Game model = new Game();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doGet");
		Map map = new Map();
		
		
		String roomDesc = map.getRoomDescription(1);
		req.setAttribute("roomMessage", roomDesc);
		req.getRequestDispatcher("/_view/Game.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("doPost");
		GameController controller = new GameController();
		
		controller.setModel(model);
		String input = (String) req.getParameter("command");
		
		System.out.println("t" + input);
		
		Player player= model.getPlayer();
		Map map = model.getMap();
		String error = null;
		
		req.setAttribute("game", model);
		
		if(input != null) {
			if(input.equalsIgnoreCase("north")) {
				if(map.checkMove(map.getCurrRoom(), "north")){
					map.setNewRoom(map.getNewRoomID());
					req.setAttribute("roomMessage", map.getRoomDescription(map.getCurrRoom()+1));
					req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				}
				
			}
			
			else if(input.equalsIgnoreCase("south")) {
				if(input.equalsIgnoreCase("south")) {
					if(map.checkMove(map.getCurrRoom(), "south")){
						map.setNewRoom(map.getNewRoomID());
						req.setAttribute("roomMessage", map.getRoomDescription(map.getCurrRoom()));
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
					}
					
				}
			}
			
			else if(input.equalsIgnoreCase("west")) {
				if(input.equalsIgnoreCase("west")) {
					if(map.checkMove(map.getCurrRoom(), "west")){
						map.setNewRoom(map.getNewRoomID());
						req.setAttribute("roomMessage", map.getRoomDescription(map.getCurrRoom()));
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
					}
					
				}
			}
			
			else if(input.equalsIgnoreCase("east")) {
				if(input.equalsIgnoreCase("east")) {
					if(map.checkMove(map.getCurrRoom(), "east")){
						map.setNewRoom(map.getNewRoomID());
						req.setAttribute("roomMessage", map.getRoomDescription(map.getCurrRoom()));
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
					}
					
				}
			}
			
			else if(input.equalsIgnoreCase("inventory")) {
				req.setAttribute("weapons", player.getInventory().getWeapons().keySet());
				req.setAttribute("equipment", player.getInventory().getEquipment().keySet());
				req.setAttribute("trophies", player.getInventory().getTrophies().keySet());
				req.setAttribute("usables", player.getInventory().getUsables().keySet());
				req.setAttribute("treasures", player.getInventory().getTreasures().keySet());
				req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);
			}
			
			else if(input.equalsIgnoreCase("pickup")) {
				player.getInventory().addItem(new Weapon("banana", 100000000, 1000000));
				req.setAttribute("roomMessage", map.getRoomDescription(map.getCurrRoom()));
				req.getRequestDispatcher("/_view/Game.jsp").forward(req,resp);
			}
			else if(input.equalsIgnoreCase("start")) {
				map.setCurrRoom(1);
				req.setAttribute("roomMessage",map.getRoomDescription(1));
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			}
			else {
				error = "unsupported command";
				req.setAttribute("errorMessage", error);
				req.setAttribute("roomMessage",map.getRoomDescription(1));
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			}
		}
		else {
			System.out.println("else");
			req.setAttribute("roomMessage", map.getRoomDescription(1));
			req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
		}
		
	}
		
}
	