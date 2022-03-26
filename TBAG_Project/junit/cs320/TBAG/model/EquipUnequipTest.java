package cs320.TBAG.model;

import static org.junit.Assert.*;

import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Actions;
import org.junit.Test;

public class EquipUnequipTest {
	
	Weapon banana = new Weapon("banana", 1000000, 1000000);
	Equipment flakJacket = new Equipment("Flak Jacket", 10, 10, 100, -1);
	Inventory playerInv = new Inventory(1);
	
	
	@Test
	public void equipEmptyWeapons() {
		String result = equipWeapon(banana, playerInv);
		assertEquals(result, "Succesfully equipped banana");
	}


	@Test
	public void equipFullWeapons() {
		String result = equipWeapon(banana, playerInv);
		assertEquals(result, "Succesfully equipped banana");
	}
	
	@Test
	public void unequipWeapon() {
		String result = unequipWeapon(banana, playerInv);
		assertEquals(result, "Succesfully equipped banana");
	}
	
	@Test
	public void equipEmptyEquipment() {
		String result = equipEquipment(flakJacket, playerInv);
		assertEquals(result, "Succesfully equipped banana");
	}
	
	@Test
	public void equipFullEquipment() {
		String result = equipEquipment(flakJacket, playerInv);
		assertEquals(result, "Succesfully equipped banana");
	}
	
	@Test
	public void unequipEquipment() {
		String result = unequipEquipment(flakJacket, playerInv);
		assertEquals(result, "Succesfully equipped banana");
	}
	

}
