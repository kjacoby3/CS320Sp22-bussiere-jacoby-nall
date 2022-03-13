package cs320.TBAG.model;

import java.util.ArrayList;

public class Save{
	ArrayList<Actor> actorsList;
	ArrayList<Inventory> inventories;
	Map map;
	
	public Save(ArrayList<Actor> actorsList, ArrayList<Inventory> inventories, Map map) {
		this.actorsList = actorsList;
		this.inventories = inventories;
		this.map = map;
	}
	
	public ArrayList<Actor> getActorsList() {
		return actorsList;
	}
	
	public ArrayList<Inventory> getInventories() {
		return inventories;
	}
	
	public Map getMap() {
		return map;
	}
}