package cs320.TBAG.model;

import cs320.TBAG.model.Game;
import cs320.TBAG.model.Inventory;

public abstract class Actor{
	String name;
	String type;
	Room location;
	Inventory inventory;
	ActorStats actorStats;
	Weapon eqWeap;
	Equipment equipped;
	int currency;
	int roomId;
	int equippedId;
	int eqWeapId;
	int statsId;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public void setEquippedId(int equippedId) {
		this.equippedId = equippedId;
	}
	
	public int getEquippedId() {
		return equippedId;
	}
	
	public void setEqWeapId(int eqWeapId) {
		this.eqWeapId = eqWeapId;
	}
	
	public int getEqWeapId() {
		return eqWeapId;
	}
	
	public void setStatsId(int statsId) {
		this.statsId = statsId;
	}
	
	public int getStatsId() {
		return statsId;
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
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public ActorStats getActorStats() {
		return actorStats;
	}
	
	public void setActorStats(ActorStats actorStats) {
		this.actorStats = actorStats;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Weapon getEqWeap() {
		return eqWeap;
	}
	
	public void setEqWeap(Weapon eqWeap) {
		this.eqWeap = eqWeap;
	}
	
	public Equipment getEquipped() {
		return equipped;
	}
	
	public void setEquipped(Equipment equipped) {
		this.equipped = equipped;
	}
	
	public int getCurrency() {
		return currency;
	}
	
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	
	public void addCurrency(int add) {
		currency += add;
	}
	
	public boolean isDead() {
		if(actorStats.getCurHP() <= 0) {
			return true;
		} else {
			return false;
		}
	}
}