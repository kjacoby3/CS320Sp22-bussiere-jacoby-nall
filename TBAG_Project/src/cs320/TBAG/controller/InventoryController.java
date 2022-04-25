package cs320.TBAG.controller;

import cs320.TBAG.database.*;
import cs320.TBAG.model.Inventory;

public class InventoryController {

	private IDatabase db = null;
	public InventoryController() {
		db = new DerbyDatabase();
	}
	
	public Inventory getPlayerInventory(int playerID) {
		Inventory inventory = db.constructInventoryByPlayerID(playerID);
		
		return inventory;
		
	}
}
