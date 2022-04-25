package cs320.TBAG.model;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Map;
import cs320.TBAG.model.Item;
import java.util.ArrayList;
import java.util.HashMap;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Consumable;
import cs320.TBAG.model.Usable;

public class Actions implements ActionsInterface {
	public String direction;
	public Item item;
	public Inventory inventory;
	int currRoom;
	int currency;
		
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
	public void unequipWeapon() {
		Weapon curWeapon = getEqWeap();
		inventory.addItem(curWeapon);
		setEqWeap(new Weapon("Fists", 10, 100));
	}
	
	@Override
	public void equipEquipment(Equipment equipment) {
		inventory.addItem(equipment);
	}
	
	@Override
	public void unequipEquipment() {
		Equipment curEquipment = getEquipped();
		inventory.addItem(curEquipment);
		setEquipped(new Equipment("Bare", 100, 10, 0, 0));
	}
	
	@Override 
	public void buy(Item item) {
		if (currency >= item.getBuyPrice()){
		
		inventory.addItem(item);
		currency = currency - item.getBuyPrice();
		npcInventory.removeItem(item);
		}
		
	}
	
	@Override 
	public void sell(Item item) {
		inventory.removeItem(item);
		npcInventory.addItem(item);
		currency = currency - item.getSellPrice();
	}
	
	@Override 
	public void use(Item item) {
		//TODO
		if (item instanceof Consumable) {
			inventory.removeItem(item);
		}
		
		else if (item instanceof Usable) {
			//item.checkIfUseable();
		}
		
		else if (item instanceof Interactable) {
			//set player room id
			//check it can be used
		}
		
		else {
			System.out.println("That " + " can't be used at this time");
		}
	}
	
}
