package cs320.TBAG.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Treasure;
import cs320.TBAG.model.Trophy;
import cs320.TBAG.model.Usable;
import cs320.TBAG.model.Weapon;
import cs320.TBAG.model.Consumable;
import cs320.TBAG.model.Equipment;
//import cs320.TBAG.model.Consumable

public class InventoryServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/*Inventory inventory = new Inventory(5);
		inventory.addItem(new Weapon("Sword", 5, 5));
		inventory.addItem(new Weapon("Axe", 10, 15));
		inventory.addItem(new Equipment("Chest", 10, 25, 35, 40));
		inventory.addItem(new Equipment("Leg", 25, 30, 45, 50));
		
		
		req.setAttribute("weapons", inventory.getWeapons().keySet());
		req.setAttribute("equipment", inventory.getEquipment().keySet());
		req.setAttribute("trophies", inventory.getTrophies().keySet());
		req.setAttribute("usables", inventory.getUsables().keySet());
		req.setAttribute("treasures", inventory.getTreasures().keySet());
		
		req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);*/
		doPost(req, resp);
	}
		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		/*Inventory inventory = new Inventory(5);
		inventory.addItem(new Weapon("Sword", 5, 5,0,0,0,false));
		inventory.addItem(new Weapon("Axe", 10, 15,0,0,0,false));
		inventory.addItem(new Weapon("Blaster", 25, 400,0,0,0,false));
		inventory.addItem(new Weapon("Ice Spike", 400, 200,0,0,0,false));
		inventory.addItem(new Weapon("Sniper Rifle", 600, 1000,0,0,0,false));
		inventory.addItem(new Equipment("Chest", 10, 10, 35, 40,0,0,0,false));
		inventory.addItem(new Equipment("Leg", 25, 25, 45, 50,0,0,0,false));
		inventory.addItem(new Trophy("Raptor Claw", 25,0,0,0));
		inventory.addItem(new Trophy("Raptor Mandible", 53,0,0,0));
		inventory.addItem(new Usable("Red Key", 421,0,0,0));
		inventory.addItem(new Usable("Purple Key", 322,0,0,0));
		inventory.addItem(new Treasure("King's Crown", 322,0,0,0));
		inventory.addItem(new Treasure("Worm Necklace", 10 , 0,0,0));
		inventory.addItem(new Consumable("Health Potion", 10, 400, 0, 0, 0, 0,0,0,0));
		inventory.addItem(new Consumable("Damage Potion", 400, 0,0,10,0,0,0,0,0));*/
		HttpSession session = req.getSession();
		Inventory inventory = ((Player) session.getAttribute("player")).getInventory();
		
		List<Weapon> weapons = new ArrayList<Weapon>(inventory.getWeapons().values());
		List<Equipment> equipment = new ArrayList<Equipment>(inventory.getEquipment().values());
		List<Trophy> trophies = new ArrayList<Trophy>(inventory.getTrophies().values());
		List<Usable> usables = new ArrayList<Usable>(inventory.getUsables().values());
		List<Treasure> treasures = new ArrayList<Treasure>(inventory.getTreasures().values());
		List<Consumable> consumables = new ArrayList<Consumable>(inventory.getConsumables().values());
		
		
		
		req.setAttribute("weapons", weapons);
		req.setAttribute("equipment", equipment);
		req.setAttribute("trophies", trophies);
		req.setAttribute("usables", usables);
		req.setAttribute("treasures", treasures);
		req.setAttribute("consumables", consumables);
		
		req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);
		
		
	}
}
