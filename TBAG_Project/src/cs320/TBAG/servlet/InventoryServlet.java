package cs320.TBAG.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Weapon;
import cs320.TBAG.model.Equipment;

public class InventoryServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Inventory inventory = new Inventory(5);
		inventory.addItem(new Weapon("Sword", 5, 5));
		inventory.addItem(new Weapon("Axe", 10, 15));
		inventory.addItem(new Equipment("Chest", 10, 25, 35, 40));
		inventory.addItem(new Equipment("Leg", 25, 30, 45, 50));
		
		
		req.setAttribute("weapons", inventory.getWeapons().keySet());
		req.setAttribute("equipment", inventory.getEquipment().keySet());
		req.setAttribute("trophies", inventory.getTrophies().keySet());
		req.setAttribute("usables", inventory.getUsables().keySet());
		req.setAttribute("treasures", inventory.getTreasures().keySet());
		
		req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);
	}
		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		Inventory inventory = new Inventory(5);
		inventory.addItem(new Weapon("Sword", 5, 5));
		inventory.addItem(new Weapon("Axe", 10, 15));
		inventory.addItem(new Equipment("Chest", 10, 25, 35, 40));
		inventory.addItem(new Equipment("Leg", 25, 30, 45, 50));
		
		
		req.setAttribute("weapons", inventory.getWeapons().keySet());
		req.setAttribute("equipment", inventory.getEquipment().keySet());
		req.setAttribute("trophies", inventory.getTrophies().keySet());
		req.setAttribute("usables", inventory.getUsables().keySet());
		req.setAttribute("treasures", inventory.getTreasures().keySet());
		
		req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);
		
		
	}
}
