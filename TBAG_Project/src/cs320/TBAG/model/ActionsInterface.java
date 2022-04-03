package cs320.TBAG.model;

public interface ActionsInterface {
	void move(String direction);
	void pickUp(Item item);
	void attack();
	void equipWeapon(Weapon weapon);
	void unequipWeapon();
	void equipEquipment(Equipment equipment);
	void unequipEquipment();
	void talk(NPC npc);
	void buy(Item item);
	void sell(Item item);
	void use(Item item);
}
