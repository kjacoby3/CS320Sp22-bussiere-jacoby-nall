package cs320.TBAG.model;

import cs320.TBAG.model.Actions;

public class NPC extends Actor{
	
	private Actions action = new Actions();
	
	public NPC() {
		name = "NPC 1";
		type = "npc";
		location = new Room();
		inventory = new Inventory(100);
		actorStats = new ActorStats(100, 10, 10, 10);
		eqWeap = new Weapon("Fists", 0, 100);
		equipped = new Equipment("Bare", 100, 0, 0, 0);
	}
	
	public NPC(String name, Room location, Inventory inventory,
			ActorStats actorStats, String type, Weapon eqWeap, Equipment equipped) {
		
	}

}