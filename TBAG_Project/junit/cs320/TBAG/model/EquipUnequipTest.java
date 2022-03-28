package cs320.TBAG.model;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class EquipUnequipTest {
	
	Weapon banana = new Weapon("banana", 1000000, 1000000);
	Equipment flakJacket = new Equipment("Flak Jacket", 10, 10, 100, -1);
	Inventory playerInv = new Inventory(1);
	Player player;
	
	
	@Test
	public void testEquipEmptyWeapons() {
		player.equipWeapon(banana);
		HashMap<String, Weapon> playerWeapons = playerInv.getWeapons();
		assertEquals(playerWeapons.get(0), banana);
	}
	
	@Test
	public void testEquipFullWeapons() {
		String result = Actions.equipWeapon(banana, playerInv);
		assertEquals(result, "Could not equip " + banana);
	}
	
	@Test
	public void testUnequipWeapon() {
		String result = Actions.unequipWeapon(banana, playerInv);
		assertEquals(result, "Succesfully unequipped " + banana);
	}
	
	@Test
	public void testEquipEmptyEquipment() {
		String result = Actions.equipEquipment(flakJacket, playerInv);
		assertEquals(result, "Succesfully equipped " + flakJacket);
	}
	
	@Test
	public void testEquipFullEquipment() {
		String result = Actions.equipEquipment(flakJacket, playerInv);
		assertEquals("Could not equip " + flakJacket, result);
	}
	
	@Test
	public void testUnequipEquipment() {
		String result = Actions.unequipEquipment(flakJacket, playerInv);
		assertEquals(result, "Succesfully unequipped " + flakJacket);
	}
	

}
