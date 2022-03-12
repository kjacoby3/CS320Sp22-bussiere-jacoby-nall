package cs320.TBAG.model;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import junit.framework.TestCase;

public class InventoryTest extends TestCase {
	private Inventory inventory;
	Weapon weapon;
	Usable usable;
	Trophy trophy;
	Treasure treasure;
	Equipment equipment1;
	Equipment equipment2;
	Equipment equipment3;
	
	@Before
	protected void setUp() throws Exception{
		weapon = new Weapon("Sword", 15, 450);
		usable = new Usable("Key");
		trophy = new Trophy("Claw");
		treasure = new Treasure("Keepsake", 0, 0, 2, 0);
		equipment1 = new Equipment("Chest Armor", 800, 20, 0, 0);
		equipment2 = new Equipment("Leg Armor", 400, 15, 0, 2);
		equipment3 = new Equipment("Boots", 470, 10, 0, 0 );
		
		
	}
	
	@Test
	public void testAddItem() {
		inventory = new Inventory(2);
		assertTrue(inventory.addItem(weapon));
		assertTrue(inventory.addItem(usable));
		assertTrue(inventory.addItem(trophy));
		assertTrue(inventory.addItem(treasure));
		assertTrue(inventory.addItem(equipment1));
		assertTrue(inventory.addItem(equipment2));
		assertFalse(inventory.addItem(equipment3));
		
		Weapon weapon1 = inventory.getWeapons().get("Sword");
		Usable usable1 = inventory.getUsables().get("Key");
		Trophy trophy1 = inventory.getTrophies().get("Claw");
		Treasure treasure1 = inventory.getTreasures().get("Keepsake");
		Equipment equip1 = inventory.getEquipment().get("Chest Armor");
		Equipment equip2 = inventory.getEquipment().get("Leg Armor");
		
		assertTrue(weapon1.equals(weapon));
		assertTrue(usable1.equals(usable));
		assertTrue(trophy1.equals(trophy));
		assertTrue(treasure1.equals(treasure));
		assertTrue(equip1.equals(equipment1));
		assertTrue(equip2.equals(equipment2));
		
		assertTrue(inventory.getWeapons().containsValue(weapon));
		assertTrue(inventory.getUsables().containsValue(usable));
		assertTrue(inventory.getTrophies().containsValue(trophy));
		assertTrue(inventory.getTreasures().containsValue(treasure));
		assertTrue(inventory.getEquipment().containsValue(equipment1));
		assertTrue(inventory.getEquipment().containsValue(equipment2));
		assertFalse(inventory.getEquipment().containsValue(equipment3));
		
		assertFalse(inventory.getEquipment().containsKey("Boots"));
		
	}
	
	
	@Test
	public void testRemoveItem() {
		inventory = new Inventory(3);
		inventory.addItem(weapon);
		inventory.addItem(usable);
		inventory.addItem(trophy);
		inventory.addItem(treasure);
		inventory.addItem(equipment1);
		inventory.addItem(equipment2);
		inventory.addItem(equipment3);
		
		assertTrue(inventory.getEquipment().size() == 3);
		
		assertTrue(inventory.removeItem(weapon).equals(weapon));
		assertTrue(inventory.removeItem(usable).equals(usable));
		assertTrue(inventory.removeItem(trophy).equals(trophy));
		assertTrue(inventory.removeItem(treasure).equals(treasure));
		assertTrue(inventory.removeItem(equipment1).equals(equipment1));
		assertTrue(inventory.removeItem(equipment2).equals(equipment2));
		
		assertFalse(inventory.getWeapons().containsValue(weapon));
		assertFalse(inventory.getUsables().containsValue(usable));
		assertFalse(inventory.getTrophies().containsKey("Claw"));
		assertFalse(inventory.getTreasures().containsKey("Keepsake"));
		assertFalse(inventory.getEquipment().containsValue(equipment1));
		assertFalse(inventory.getEquipment().containsValue(equipment2));
		assertTrue(inventory.getEquipment().containsValue(equipment3));
		
		assertTrue(inventory.getEquipment().size() == 1);
		
		
	}
	
	
	@Test
	public void testCheckFull() {
		inventory = new Inventory(1);
		inventory.addItem(weapon);
		inventory.addItem(usable);
		inventory.addItem(trophy);
		inventory.addItem(treasure);
		inventory.addItem(equipment1);
		
		assertTrue(inventory.checkFull("Weapon"));
		assertTrue(inventory.checkFull("Usable"));
		assertTrue(inventory.checkFull("Trophy"));
		assertTrue(inventory.checkFull("Treasure"));
		assertTrue(inventory.checkFull("Equipment"));
		
		inventory.removeItem(weapon);
		inventory.removeItem(usable);
		inventory.removeItem(trophy);
		inventory.removeItem(treasure);
		inventory.removeItem(equipment1);
		
		assertFalse(inventory.checkFull("Weapon"));
		assertFalse(inventory.checkFull("Usable"));
		assertFalse(inventory.checkFull("Trophy"));
		assertFalse(inventory.checkFull("Treasure"));
		assertFalse(inventory.checkFull("Equipment"));
	}
	
	
}
