package cs320.TBAG.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class EquipUnequipTest {
	
	Weapon banana = new Weapon("banana", 1000000, 1000000);
	Equipment flakJacket = new Equipment("Flak Jacket", 10, 10, 100, -1);
	Inventory playerInv = new Inventory(1);
	
	
	@Test
	public void testEquipEmptyWeapons() {
		String result = Actions.equipWeapon(banana, playerInv);
		assertEquals(result, "Succesfully equipped banana");
	}


	@Test
	public void testEquipFullWeapons() {
		String result = Actions.equipWeapon(banana, playerInv);
		assertEquals(result, "Could not equip banana");
	}
	
	@Test
	public void testUnequipWeapon() {
		String result = Actions.unequipWeapon(banana, playerInv);
		assertEquals(result, "Succesfully unequipped banana");
	}
	
	@Test
	public void testEquipEmptyEquipment() {
		String result = Actions.equipEquipment(flakJacket, playerInv);
		assertEquals(result, "Succesfully equipped Flak Jacket");
	}
	
	@Test
	public void testEquipFullEquipment() {
		String result = Actions.equipEquipment(flakJacket, playerInv);
		assertEquals(result, "Could not equip Flak Jacket");
	}
	
	@Test
	public void testUnequipEquipment() {
		String result = Actions.unequipEquipment(flakJacket, playerInv);
		assertEquals(result, "Succesfully unequipped Flak Jacket");
	}
	

}
