package cs320.TBAG.model;

import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class BuyTest {

	Inventory playerInventory;
	Inventory npcInventory;
	Weapon Banana = new Weapon("banana", 10000, 2);
	npcInventory.addItem(Banana);
	int currency = 10;
	Player player = new Player();
	
	
	@Test
	void buyItem() {
		
		if (currency >= Banana.getBuyPrice()){
			
			playerInventory.addItem(Banana);
			currency = currency - Banana.getBuyPrice();
			npcInventory.removeItem(Banana);
			}
		
		assertEquals(currency, 8);
	}

}
