package cs320.TBAG.model;

import java.util.ArrayList;

import cs320.TBAG.model.Actor;

public class Game {
	private String username;
	private String password;
	private Save save;
	private ArrayList<Actor> actorsList;
	private ArrayList<Inventory> inventories;
	private Map map;
	private String input;
	private Player player1;
	
	public Game() {
		player1 = new Player();
		map = new Map();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setSave(Save save) {
		this.save = save;
	}
	
	public Save getSave() {
		return save;
	}
	
	public Map getMap() {
		return map;
	}
	
	public void newActorsList() {
		actorsList = new ArrayList<Actor>();
	}
	
	public ArrayList<Actor> getActorsList() {
		return actorsList;
	}
	
	public void newInventoryList() {
		inventories = new ArrayList<Inventory>();
	}
	
	public ArrayList<Inventory> getInventories() {
		return inventories;
	}
	
	public Player getPlayer() {
		return player1;
	}
	
	public void addEmptyPlayer() {
		Player player2 = new Player();
		actorsList.add(player2);
		inventories.add(player2.getInventory());
	}
	
	public void addEmptyNPC() {
		NPC npc1 = new NPC();
		actorsList.add(npc1);
		inventories.add(npc1.getInventory());
	}
	
	public String mesg(String mesg) {
		return mesg;
	}
}