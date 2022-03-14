package cs320.TBAG.model;

import cs320.TBAG.model.Actions;

public class Player extends Actor{
	
	private Actions action = new Actions();
	
	public Player() {
		name = "Player 1";
		location = new Room();
		inventory = new Inventory(100);
		actorStats = new ActorStats();
		inventory.addItem(new Equipment("Cloth Armor", 50, 50,50, 50));
	}
	
	public Player(String name, Room location, Inventory inventory, ActorStats actorStats) {
		
	}

	//Actions from Actions interface	
	public void movePlayer(String direction) {
		action.move(direction);
	}
	
	public void pickUpItem(Item item) {
		action.pickUp(item);
	}
	
	public void attack() {
		action.attack();
	}
}