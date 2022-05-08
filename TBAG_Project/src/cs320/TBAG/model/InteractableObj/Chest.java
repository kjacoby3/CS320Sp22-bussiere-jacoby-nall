package cs320.TBAG.model.InteractableObj;

import cs320.TBAG.model.Consumable;
import cs320.TBAG.model.Equipment;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Room;
import cs320.TBAG.model.Treasure;
import cs320.TBAG.model.Usable;
import cs320.TBAG.model.Weapon;

public class Chest extends Interactable {
	int chestId;
	Inventory inventory;
	
	public Chest() {
		name = "Chest";
		description = "There is a chest.";
		activation = "open";
		location = new Room();
		activated = false;
		puzzleLock = null;
		inventory = new Inventory(100);
	}
	
	@Override
	public String activateObj() {
		// TODO Auto-generated method stub
		String result = null;
		Boolean unlocked;
		
		if(activated == false) {
			if(puzzleLock != null) {
				unlocked = puzzleLock.getComplete();
			} else {
				unlocked = true;
			}
			
			if(unlocked) {
				result = "You opened the chest and its contents spill onto the floor.";
				for(Weapon weap : inventory.getWeapons().values()) {
					location.getRoomInv().addItem(weap);
				}
				
				for(Equipment equip : inventory.getEquipment().values()) {
					location.getRoomInv().addItem(equip);
				}
				
				for(Usable usable : inventory.getUsables().values()) {
					location.getRoomInv().addItem(usable);
				}
				
				for(Consumable consum : inventory.getConsumables().values()) {
					location.getRoomInv().addItem(consum);
				}
				
				for(Treasure treasure : inventory.getTreasures().values()) {
					location.getRoomInv().addItem(treasure);
				}
				activated = true;
			} else {
				result = puzzleLock.getHint();
			}
		} else {
			result = "The chest is already open.";
		}
		return result;
	}
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void setChestId(int chestId) {
		this.chestId = chestId;
	}
	
	public int getChestId() {
		return chestId;
	}
	
}