package cs320.TBAG.controller;

import java.util.Iterator;
import java.util.List;

import cs320.TBAG.database.*;
import cs320.TBAG.dbclass.InitDatabase;
import cs320.TBAG.model.ActorStats;
import cs320.TBAG.model.Equipment;
import cs320.TBAG.model.Game;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Map;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Room;
import cs320.TBAG.model.Weapon;

public class MapController {
	private Map model;
	private InventoryController invCreator;
	private IDatabase db = null;
	public MapController() {
		invCreator = new InventoryController();
		InitDatabase.init(1);
		db = new DerbyDatabase();
	}
	
		
	public String getRoomDescByID(int ID) {
		
		String descrip = db.constructDescripByRoomID(ID); 
		return descrip;
	}
	
	public void createMap() {
		List<Room> roomList = db.findAllRooms();
		Iterator<Room> iterator = roomList.iterator();
		while (iterator.hasNext()) {
			Room room = iterator.next();
			
			/*room.setRoomID(roomSet.getInt(1));
			room.setRoomName(roomSet.getString(2));
			room.setRoomDescripShort(roomSet.getString(3));
			room.setRoomDescripLong(roomSet.getString(4));
			room.setRoomLevel(roomSet.getInt(5));
			room.setRoomPrevVisit(roomSet.getBoolean(6));
			room.setRoomGameID(roomSet.getInt(7));*/
			
			Inventory inv = invCreator.getRoomInventory(room.getRoomID());
			room.setRoomInv(inv);
			
			
			
			/*for(Weapon weap : inv.getWeapons().values()) {
				if(weap.getEquipped()) {
					player.equipWeapon(weap);
				}
			}
			
			for(Equipment equipped : inv.getEquipment().values()) {
				if(equipped.getEquipped()) {
					player.equipEquipment(equipped);
				}
			}*/
			
			//Add room to room list
			model.addRoom(room);
		}
	}
	
	/*public void move(Player player, String direction) {
		
		int actorRoom = player.getRoomId();
		Map map;
		int newRoom = map.canMove(actorRoom, direction);
		String descrip;
		boolean activatedCheck = true;
		
		if (activatedCheck = true) {
			if(newRoom > 0) {
				int prevRoomID = actorRoom;
				player.setPrevRoomId(prevRoomID); //actor needs a get/setPrevRoomID(int) method
				int actorCurrRoom = newRoom;
				Room connectedRoom = db.getRoomByID(actorCurrRoom);
				player.setRoomId(actorCurrRoom); 
				if (connectedRoom.getRoomPrevVisit() == false) {
					connectedRoom.setRoomPrevVisit(true);
					descrip = getRoomDescByID(actorCurrRoom);
				}
				
				
				String currRoomName = connectedRoom.getRoomName();
				String currRoomDescrip = getRoomDescByID(actorCurrRoom);
			}
			
			else {
				descrip = "You can't go that way";
			}
		}
		
		else {
			descrip = "This exit is not yet activated"; //use getHint()
		}
		
	}*/
}