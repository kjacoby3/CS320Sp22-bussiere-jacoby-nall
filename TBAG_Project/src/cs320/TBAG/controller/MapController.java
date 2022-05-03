package cs320.TBAG.controller;

import cs320.TBAG.database.*;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Map;

public class MapController {

	private IDatabase db = null;
	public MapController() {
		db = new DerbyDatabase();
	}
	
	public Inventory getRoomInventory(int roomID) {
		Inventory inventory = db.constructInventoryByRoomID(roomID);
		
		return inventory;
	}
	
	public String getRoomDescByID(int ID) {
		
		String descrip = db.constructDescripByRoomID(ID); 
		return descrip;
	}
	
	public void createMap() {
		Map map = new Map();
		//model.addMap(map);
	}
	
	/*public void createRoom() {
		Room room = new Room();
		}*/
}