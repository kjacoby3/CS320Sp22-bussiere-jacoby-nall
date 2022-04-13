package cs320.TBAG.model;

import cs320.TBAG.model.Actions;

public class NPC extends Actor{
	
	//private Actions action = new Actions();
	NPCAggression aggression;
	
	public NPC() {
		name = "NPC 1";
		type = "npc";
		location = new Room();
		inventory = new Inventory(100);
		actorStats = new ActorStats(100, 10, 10, 10);
		eqWeap = new Weapon("Fists", 10, 100);
		equipped = new Equipment("Bare", 100, 10, 0);
		aggression = NPCAggression.Neutral;
		currency = 0;
	}
	
	public NPC(String name, Room location, Inventory inventory,
			ActorStats actorStats, String type, Weapon eqWeap, 
			Equipment equipped, NPCAggression aggression,int currency) {
		this.name = name;
		this.type = type;
		this.location = location;
		this.actorStats = actorStats;
		this.eqWeap = eqWeap;
		this.equipped = equipped;
		this.aggression = aggression;
		this.currency = currency;
	}
	
	public NPCAggression getAggression() {
		return aggression;
	}
	
	public void setAggression(NPCAggression aggression) {
		this.aggression = aggression;
	}

}