package cs320.TBAG.controller;

import java.util.ArrayList;
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
import cs320.TBAG.model.RoomConnection;
import cs320.TBAG.model.Treasure;
import cs320.TBAG.model.Weapon;
import cs320.TBAG.model.InteractableObj.Interactable;
import cs320.TBAG.model.PuzzleType.KeyPuzzle;

public class MapController {
	private Map map;
	private InventoryController invCreator;
	private IDatabase db = null;
	public MapController() {
		invCreator = new InventoryController();
		InitDatabase.init(1);
		db = new DerbyDatabase();
		map = new Map();
	}
	
		
	public String getRoomDescByID(int ID) {
		
		String descrip = db.constructDescripByRoomID(ID); 
		return descrip;
	}
	
	public Map createMap() {
		List<Room> roomList = db.getRooms();
		ArrayList<RoomConnection> connections= db.getConnections();
		Iterator<Room> iterator = roomList.iterator();
		Iterator<RoomConnection> i = connections.iterator();
		
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
			
			room.setRoomInteractables(db.getInteractablesByRoomID(room.getRoomID()));
			//for (room : roomList) {
			//ArrayList<Integer> exit = new ArrayList<Integer>();
			room.setRoomConnections(i.next());
			for(Interactable obj : room.getRoomInteractables()) {
				if(obj.getPuzzle() != null) {
					if(obj.getPuzzle() instanceof KeyPuzzle) {
						for(Room r : roomList) {
							if(r.getRoomInv() != null) {
								if(r.getRoomInv().getTreasures().size() != 0) {
									for(Treasure item : r.getRoomInv().getTreasures().values()) {
										if(item.getItemID() == ((KeyPuzzle)obj.getPuzzle()).getItemId()) {
											((KeyPuzzle)obj.getPuzzle()).setKey(item);
										}
									}
								}
							}
						}
					}
					for(Room r : roomList) {
						for(Interactable obj2 : r.getRoomInteractables()) {
							if(obj2.getPuzzle() != null) {
								if(obj.getPuzzleId() == obj2.getPuzzleId()) {
									obj2.setPuzzle(obj.getPuzzle());
								}
							}
						}
					}
				}
			}
			
			
			/*exit.add(conn.getNorth());
			exit.add(conn.getEast());
			exit.add(conn.getSouth());
			exit.add(conn.getWest());
			room.setAvailableExits(exit);*/
			//}
			
			
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
			map.addRoom(room.getRoomID(), room);
		}
		
		return map;
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