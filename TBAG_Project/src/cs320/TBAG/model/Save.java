package cs320.TBAG.model;

import java.util.ArrayList;

public class Save{
	private ArrayList<Actor> actorsList;
	private ArrayList<Inventory> inventories;
	private Map map;
	
	public Save(ArrayList<Actor> actorsList, ArrayList<Inventory> inventories, Map map) {
		this.actorsList = actorsList;
		this.inventories = inventories;
		this.map = map;
	}
	
	public ArrayList<Actor> getActorsList() {
		return actorsList;
	}
	
	public void setActorsList(ArrayList<Actor> actorsList) {
		this.actorsList = actorsList;
	}
	
	public ArrayList<Inventory> getInventories() {
		return inventories;
	}
	
	public void setInventories(ArrayList<Inventory> inventories) {
		this.inventories = inventories;
	}
	
	public Map getMap() {
		return map;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
}