package cs320.TBAG.controller;

import cs320.TBAG.database.*;
import cs320.TBAG.model.Inventory;

public class MapController {

	private IDatabase db = null;
	public MapController() {
		db = new DerbyDatabase();
	}
	
	public Inventory getPlayerInventory(int playerID) {
		Inventory inventory = db.constructInventoryByPlayerID(playerID);
		
		return inventory;
		
	}
	
	public Inventory getNPCInventory(int NPCID) {
		Inventory inventory = db.constructInventoryByNPCID(NPCID);
		
		return inventory;
	}
	
	public Inventory getRoomInventory(int roomID) {
		Inventory inventory = db.constructInventoryByRoomID(roomID);
		
		return inventory;
	}
}
