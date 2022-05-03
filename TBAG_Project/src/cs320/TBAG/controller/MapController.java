package cs320.TBAG.controller;

import cs320.TBAG.database.*;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Map;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Room;

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
	
	public void move(Player player, String direction) {
		
		int actorRoom = player.getRoomId();
		Map map;
		int newRoom = map.canMove(actorRoom, direction);
		
		if(newRoom > 0) {
			int prevRoomID = actorRoom;
			player.setPrevRoomId(prevRoomID); //actor needs a get/setPrevRoomID(int) method
			int actorCurrRoom = newRoom;
			Room connectedRoom = db.getRoomByID(actorCurrRoom);
			player.setRoomId(actorCurrRoom); 
			if (connectedRoom.getRoomPrevVisit() == false) {
				connectedRoom.setRoomPrevVisit(true);
				String descrip = getRoomDescByID(actorCurrRoom);
			}
			
			
			String currRoomName = connectedRoom.getRoomName();
			String currRoomDescrip = getRoomDescByID(actorCurrRoom);
		}
		
		else {
			String descrip = "You can't go that way";
		}
	}
}