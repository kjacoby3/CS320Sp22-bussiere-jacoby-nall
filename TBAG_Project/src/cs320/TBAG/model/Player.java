package cs320.TBAG.model;

import cs320.TBAG.model.Game;

public class Player extends Actor implements Actions{
	
	public Player() {
		name = "Player 1";
		location = new Room();
		inventory = new Inventory(100);
		actorStats = new ActorStats();
	}
	
	public Player(String name, Room location, Inventory inventory, ActorStats actorStats) {
		
	}

	//Actions from Actions interface		
	public void moveActor(String direction) {
		String dir = direction;
		
		Room curLoc = location;
		if (Game.getMap().checkMove() == true) {
			actor.setNewRoom();
			System.out.println(location.roomDescrip);
		}
		else {
			System.out.println("There's no way to go that diretion");
		}
		
		
	}
	
	public void pickUpItem(Item item) {
		inventory.addItem(item);
	}
}