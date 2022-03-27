package cs320.TBAG.model;

public interface ActionsInterface {
	void move(String direction);
	void pickUp(Item item);
	void attack();
	void equipWeapon(Weapon weapon);
	void unequipWeapon(Weapon weapon);
	void equipEquipment(Equipment equipment);
	void unequipEquipment(Equipment equipment);
}
