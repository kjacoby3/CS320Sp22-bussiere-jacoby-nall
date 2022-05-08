package cs320.TBAG.model;

import cs320.TBAG.model.InteractableObj.Interactable;

public interface ActionsInterface {
	void move(int direction);
	void pickUp(Item item);
	void attack();
	void equipWeapon(Weapon weapon);
	void unequipWeapon();
	void equipEquipment(Equipment equipment);
	void unequipEquipment();
	void talk(NPC npc);
	Boolean buy(NPC npc, Item item);
	Boolean sell(NPC npc, Item item);
	String use(Item item);
	String activateObj(String activationStr, Interactable obj);
}
