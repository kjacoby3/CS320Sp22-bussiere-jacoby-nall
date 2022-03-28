package cs320.TBAG.model;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Map;
import cs320.TBAG.model.Item;
import java.util.ArrayList;
import java.util.HashMap;

public class Actions implements ActionsInterface {
	public String direction;
	public Item item;
	public Inventory inventory;
	int currRoom;
		
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
		inventory.addItem(weapon);
	}
	
	@Override
	public void unequipWeapon(Weapon weapon) {
		inventory.removeItem(weapon);
	}
	
	@Override
	public void equipEquipment(Equipment equipment) {
		inventory.addItem(equipment);
	}
	
	@Override
	public void unequipEquipment(Equipment equipment) {
		inventory.removeItem(equipment);
	}

	
}
