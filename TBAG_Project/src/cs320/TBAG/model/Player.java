package cs320.TBAG.model;

import cs320.TBAG.model.Actions;
import cs320.TBAG.model.Convo.Conversation;

public class Player extends Actor implements ActionsInterface{
	
	//private Actions action = new Actions();
	private int playerId;
	private int roomId;
	
	public Player() {
		name = "Player 1";
		type = "player";
		location = new Room();
		inventory = new Inventory(100);
		actorStats = new ActorStats();
		inventory.addItem(new Equipment("Cloth Armor", 50, 50, 50));
		eqWeap = new Weapon("Fists", 10, 10);
		equipped = new Equipment("Bare", 10, 10, 0);
	}
	
	public Player(String name, Room location, Inventory inventory, ActorStats actorStats,
			Weapon eqWeap, Equipment equipped) {
		type = "player";
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public int getPlayerId() {
		return playerId;
	}

//	//Actions from Actions interface	
//	public void movePlayer(String direction) {
//		action.move(direction);
//	}
//	
//	public void pickUpItem(Item item) {
//		action.pickUp(item);
//	}
//	
//	public void attack() {
//		action.attack();
//	}
//	
//	public void equipEquipment(Equipment equipment) {
//		
//	}
//	
//	public void unequipEquipment(Equipment equipment) {
//		
//	}
	
	//Actions from Actions Interface
	@Override
	public void move(String direction) {
		//Map tempMap = new Map();
		//boolean verify = tempMap.checkMove(tempMap.getCurrRoom(), direction);
		//if(verify = true) {
		//	int newID = tempMap.getNewRoomID();
		//	tempMap.setNewRoom(newID);
		//	System.out.println(tempMap.getRoomDescription(tempMap.getCurrRoom()));
		//}
		//else {
		//	System.out.println("There's no way to go that direction");
		//}
		
		//boolean verify = Map.checkMove(direction);
		boolean verify = true;
		
	}
	@Override
	public void pickUp(Item item) {
		inventory.addItem(item);
	}
	
	@Override
	public void attack() {
		System.out.println("You successfully attack and defeat the target");
	}
	
	@Override
	public void equipWeapon(Weapon weapon) {
		Weapon curWeapon = getEqWeap();
		if(inventory.getWeapons().containsValue(weapon)) {
			setEqWeap(weapon);
			if(!(curWeapon.getName() == "Fists")) {
				//if(!(inventory.addItem(curWeapon))){
				inventory.addItem(curWeapon);
				inventory.removeItem(weapon);
				//}
			}
		}
		
	}
	
	@Override
	public void unequipWeapon() {
		Weapon curWeapon = getEqWeap();
		inventory.addItem(curWeapon);
		setEqWeap(new Weapon("Fists", 10, 100));
		
	}
	
	@Override
	public void equipEquipment(Equipment equipment) {
		Equipment curEquipment = getEquipped();
		if(inventory.getEquipment().containsValue(equipment)) {
			setEquipped(equipment);
			if(!(curEquipment.getName() == "Bare")) {
				inventory.addItem(curEquipment);
				inventory.removeItem(equipment);
			}
		}
	}
	
	@Override
	public void unequipEquipment() {
		Equipment curEquipment = getEquipped();
		inventory.addItem(curEquipment);
		setEquipped(new Equipment("Bare", 100, 10, 0));
	}

	@Override
	public void talk(NPC npc) {
		if(npc.getLocation() == location) {
			if(npc.getAggression() != NPCAggression.Aggressive) {
				//return true;
			} else {
				//return false;
			}
		} else {
			System.out.println("There is no one to talk to");
			//return false;
		}
	}
	
}