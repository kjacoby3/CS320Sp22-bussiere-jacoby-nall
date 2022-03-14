package cs320.TBAG.model;

import cs320.TBAG.model.Game;

public abstract class Actor {
	String name;
	Room location;
	Inventory inventory;
	ActorStats actorStats;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Room getLocation() {
		return location;
	}
	
	public void setLocation(Room location) {
		this.location = location;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public ActorStats getActorStats() {
		return actorStats;
	}
	
}