package cs320.TBAG.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

class PickUpTest {
	
	
	@Test
	void testPickUp() {
		Inventory InvenOpen = new Inventory(20);
		Weapon banana = new Weapon("banana", 1000000000, 1000000000);
		boolean check = InvenOpen.addItem(banana);
		assertEquals(check,true);
	}
	
	@Test
	void testFullPickUp() {
		Inventory InvenFull = new Inventory(0);
		Weapon banana = new Weapon("banana", 1000000000, 1000000000);
		boolean check = InvenFull.addItem(banana);
		assertEquals(check,false);
	}

}
