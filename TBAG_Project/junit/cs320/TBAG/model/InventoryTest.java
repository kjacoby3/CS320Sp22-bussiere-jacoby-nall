package cs320.TBAG.model;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import junit.framework.TestCase;

public class InventoryTest extends TestCase {
	private Inventory inventory;
	Weapon weapon;
	Weapon sword2;
	Usable usable;
	Trophy trophy;
	Treasure treasure;
	Equipment equipment1;
	Equipment equipment2;
	Equipment equipment3;
	
	@Before
	protected void setUp() throws Exception{
		weapon = new Weapon("Sword", 15, 450,0,0,0,true);
		usable = new Usable("Key",0,0,0,0);
		trophy = new Trophy("Claw", 10, 10, 10, 10);
		treasure = new Treasure("Keepsake", 0, 0, 2, 0);
		equipment1 = new Equipment("Chest Armor", 800, 20, 0, 0,0,0,0,false);
		equipment2 = new Equipment("Leg Armor", 400, 15, 0, 2,0,0,0,false);
		equipment3 = new Equipment("Boots", 470, 10, 0, 0,0,0,0,false);
		
		
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
		
		System.out.println(inventory.getWeaponCount().get(weapon.getName()));
		assertTrue(inventory.getWeaponCount().get(weapon.getName()).equals(1));
		
		assertTrue(inventory.getUsableCount().get(usable.getName())==1);
		assertTrue(inventory.getTrophyCount().get(trophy.getName())==1);
		assertTrue(inventory.getTreasureCount().get(treasure.getName())==1);
		assertTrue(inventory.getEquipmentCount().get(equipment1.getName())==1);
		
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
		
		
		assertTrue(inventory.addItem(weapon));
		assertTrue(inventory.addItem(usable));
		assertTrue(inventory.addItem(trophy));
		assertTrue(inventory.addItem(treasure));
		assertTrue(inventory.addItem(equipment1));
		
		assertTrue(inventory.getWeaponCount().get(weapon.getName())==2);
		assertTrue(inventory.getUsableCount().get(usable.getName())==2);
		assertTrue(inventory.getTrophyCount().get(trophy.getName())==2);
		assertTrue(inventory.getTreasureCount().get(treasure.getName())==2);
		assertTrue(inventory.getEquipmentCount().get(equipment1.getName())==2);
		
	}
	
	@Test
	public void addMultipleItems() {
		inventory = new Inventory(2);
		assertTrue(inventory.addItem(weapon));
		assertTrue(inventory.addItem(usable));
		assertTrue(inventory.addItem(trophy));
		assertTrue(inventory.addItem(treasure));
		assertTrue(inventory.addItem(equipment1));
		
		assertTrue(inventory.getWeaponCount().get(weapon.getName())==1);
		assertTrue(inventory.getWeaponCount().get(usable.getName())==1);
		assertTrue(inventory.getWeaponCount().get(trophy.getName())==1);
		assertTrue(inventory.getWeaponCount().get(treasure.getName())==1);
		assertTrue(inventory.getWeaponCount().get(equipment1.getName())==1);
		
		assertTrue(inventory.addItem(weapon));
		assertTrue(inventory.addItem(usable));
		assertTrue(inventory.addItem(trophy));
		assertTrue(inventory.addItem(treasure));
		assertTrue(inventory.addItem(equipment1));
		
		assertTrue(inventory.getWeaponCount().get(weapon.getName())==2);
		assertTrue(inventory.getWeaponCount().get(usable.getName())==2);
		assertTrue(inventory.getWeaponCount().get(trophy.getName())==2);
		assertTrue(inventory.getWeaponCount().get(treasure.getName())==2);
		assertTrue(inventory.getWeaponCount().get(equipment1.getName())==2);
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
		assertFalse(inventory.getWeaponCount().containsKey(weapon.getName()));
		assertFalse(inventory.getUsableCount().containsKey(usable.getName()));
		assertFalse(inventory.getTrophyCount().containsKey(trophy.getName()));
		assertFalse(inventory.getTreasureCount().containsKey(treasure.getName()));
		assertFalse(inventory.getEquipmentCount().containsKey(equipment1.getName()));
		
		assertTrue(inventory.getEquipment().size() == 1);
		
		
		inventory.addItem(weapon);
		inventory.addItem(usable);
		inventory.addItem(trophy);
		inventory.addItem(treasure);
		inventory.addItem(equipment3);
		inventory.addItem(weapon);
		inventory.addItem(usable);
		inventory.addItem(trophy);
		inventory.addItem(treasure);
		inventory.addItem(equipment3);
		
		assertTrue(inventory.getWeaponCount().get(weapon.getName())==2);
		assertTrue(inventory.getUsableCount().get(usable.getName())==2);
		assertTrue(inventory.getTrophyCount().get(trophy.getName())==2);
		assertTrue(inventory.getTreasureCount().get(treasure.getName())==2);
		assertTrue(inventory.getEquipmentCount().get(equipment3.getName())==3);

		
		assertTrue(inventory.removeItem(weapon).equals(weapon));
		assertTrue(inventory.removeItem(usable).equals(usable));
		assertTrue(inventory.removeItem(trophy).equals(trophy));
		assertTrue(inventory.removeItem(treasure).equals(treasure));
		assertTrue(inventory.removeItem(equipment3).equals(equipment3));
		
		assertTrue(inventory.getWeaponCount().get(weapon.getName())==1);
		assertTrue(inventory.getUsableCount().get(usable.getName())==1);
		assertTrue(inventory.getTrophyCount().get(trophy.getName())==1);
		assertTrue(inventory.getTreasureCount().get(treasure.getName())==1);
		assertTrue(inventory.getEquipmentCount().get(equipment3.getName())==2);
		
		
		
		
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
