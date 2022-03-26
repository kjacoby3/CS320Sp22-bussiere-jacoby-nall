package cs320.TBAG.model;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Map;
import cs320.TBAG.model.Item;
import java.util.ArrayList;
import java.util.HashMap;

interface ActionsInterface{
	
	void move(String direction);
	void pickUp(Item item);
	void attack();
}

public class Actions implements ActionsInterface {
	public String direction;
	public Item item;
	Weapon banana = new Weapon("banana", 1000000000, 1000000000);
	
	@Override
	public void move(String direction) {
		Map tempMap = new Map();
		boolean verify = tempMap.checkMove(tempMap.getCurrRoom(), direction);
		if(verify = true) {
			int newID = tempMap.getNewRoomID();
			tempMap.setNewRoom(newID);
			System.out.println(tempMap.getRoomDescription(tempMap.getCurrRoom()));
		}
		else {
			System.out.println("There's no way to go that direction");
		}
		
		
	}
	@Override
	public void pickUp(Item item) {
		Inventory tempInv = new Inventory(1);
		boolean check = tempInv.addItem(banana);
		if(check = true) {
			System.out.println("You put the " + banana.getName() + " in your inventory");
		}
		else {
			System.out.println("You can't add that to your inventory at this time");
		}
		
	}
	
	@Override
	public void attack() {
		System.out.println("You successfully attack and defeat the target");
	}
	
	
	public String equipWeapon(Weapon weapon, Inventory actorWeapons) {
		
		String successOrFail;
		boolean verify = actorWeapons.addItem(weapon);
				
		if (verify == true){
			successOrFail = "Succesfully equipped " + weapon;
			return successOrFail;
		}
		
		successOrFail = "Could not equip " + weapon;
		return successOrFail;
		
	}
	
	public String unequipWeapon(Weapon weapon, Inventory actorWeapons) {
		
		String successOrFail;
		actorWeapons.removeItem(weapon);
		
		successOrFail = "Succesfully unequipped " + weapon;
		return successOrFail;
	
	}
	
	public String equipEquipment(Equipment equipment, Inventory actorWeapons) {
		
		String successOrFail;
		boolean verify = actorWeapons.addItem(equipment);
				
		if (verify == true){
			successOrFail = "Succesfully quipped " + equipment;
			return successOrFail;
		}
		
		successOrFail = "Could not equip " + equipment;
		return successOrFail;
		
	}
	
	public String unequipEquipment(Equipment equipment, Inventory actorWeapons) {
		
		String successOrFail;
		actorWeapons.removeItem(equipment);

		successOrFail = "Succesfully unequipped " + equipment;
		return successOrFail;
		
	}

	
}
