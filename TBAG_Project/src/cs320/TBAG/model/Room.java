package cs320.TBAG.model;
import java.util.ArrayList;
import java.util.Iterator;

import cs320.TBAG.model.InteractableObj.Door;
import cs320.TBAG.model.InteractableObj.Interactable;

public class Room extends Map{
	int roomID;
	String roomName;
	String roomDescripLong;
	String roomDescripShort;
	int roomLevel;
	ArrayList<Interactable> roomInteractables;
	boolean roomPrevVisit;
	boolean roomActivatedCheck;
	int roomGameID;
	Inventory roomInv;
	RoomConnection roomConnection;
	ArrayList<NPC> NPCsInRoom = new ArrayList<NPC>();
	
	
	
	public Room(int ID, String name, String shortDescrip, String longDescrip, int level, boolean prev, int gID, Inventory inv) { //exits must be entered as four values <0,0,0,0> for n,s,w,e
		this.roomID = ID;
		this.roomName = name;
		this.roomDescripShort = shortDescrip;
		this.roomDescripLong = longDescrip;
		this.roomLevel = level;
		this.roomInv = inv;
		this.roomPrevVisit = prev;
		this.roomGameID = gID;
		
	}
	
	public Room() {
		this.roomInteractables = new ArrayList<Interactable>();
		this.NPCsInRoom = new ArrayList<NPC>();
	}
	
	public void setRoomID(int ID) {
		this.roomID = ID;
	}
	
	public int getRoomID() {
		return roomID;
	}
	
	public void setRoomName(String name){
		this.roomName = name;
	}
	public String getRoomName() {
		return roomName;
	}
	
	public void setRoomDescripLong(String longDescrip) {
		this.roomDescripLong = longDescrip;
	}
	
	public String getRoomDescripLong() {
		return roomDescripLong;
	}
	
	public void setRoomDescripShort(String shortDescrip) {
		this.roomDescripShort = shortDescrip;
	}
	
	public String getRoomDescripShort() {
		return roomDescripShort;
	}
	
	public void setRoomConnections(RoomConnection connections) {
		this.roomConnection = connections;
	}
	
	public RoomConnection getRoomConnection() {
		return roomConnection;
	}
	
	
	public void setRoomLevel(int level) {
		this.roomLevel = level;
	}
	
	public int getRoomLevel() {
		return roomLevel;
	}
	
	public void setRoomPrevVisit(boolean prev) {
		this.roomPrevVisit = prev;
	}
	
	public boolean getRoomPrevVisit() {
		return roomPrevVisit;
	}
	
	public void setRoomInv(Inventory inv) {
		this.roomInv = inv;
	}
	
	public Inventory getRoomInv() {
		return roomInv;
	}
	
	public void setRoomGameID(int ID) {
		this.roomGameID = ID;
	}
	
	public int getRoomGameID() {
		return roomGameID;
	}
	
	public void setRoomActivatedCheck(boolean activated) {
		this.roomActivatedCheck = activated;
	}
	
	public boolean getRoomActivatedCheck() {
		return roomActivatedCheck;
	}
	
	public void setNPCsInRoom(ArrayList<NPC> NPCs) {
		this.NPCsInRoom = NPCs;
	}
	
	public ArrayList<NPC> getNPCsInRoom() {
		return NPCsInRoom;
	}

	public void setRoomInteractables(ArrayList<Interactable> Interactable) {
		this.roomInteractables = Interactable;
	}
	
	public ArrayList<Interactable> getRoomInteractables() {
		return roomInteractables;
	}
	
	public void addInteractable(Interactable interactable) {
		roomInteractables.add(interactable);
	}
	
	public void removeInteractable(Interactable interactable) {
		roomInteractables.remove(interactable);
	}

	public void addNPCInRoom(NPC npc) {
		NPCsInRoom.add(npc);
	}
	
	public void removeNPCInRoom(NPC npc) {
//		Iterator<NPC> itr = NPCsInRoom.iterator();
//	    while (itr.hasNext()) {
//	      NPC npcName = itr.next();
//	      if (npcName.equals(npc)) {
//	    	  NPCsInRoom.remove(npcName);
//	      }
//	    }
		NPCsInRoom.remove(npc);
	}
	
	public Door getDoor(String direction) {
		Door door = null;
		for(Interactable obj : roomInteractables) {
			if(obj instanceof Door) {
				if(((Door) obj).getDirection().equalsIgnoreCase(direction)) {
					door = (Door) obj;
				}
			}
		}
		return door;
	}
	
	public ArrayList<String> getRoomNPCDescription(){
		ArrayList<String> strList = new ArrayList<String>();
		String npcStr;
		String aggroStr = null;
					
		for(int i = 0; i < NPCsInRoom.size(); i++) {
			npcStr = null;
			System.out.println("Got here" + NPCsInRoom.get(i).getName());
			if(NPCsInRoom.get(i).getAggression() == 1) {
				aggroStr = "a friendly";
			} else if(NPCsInRoom.get(i).getAggression() == -1) {
				aggroStr = "an angry";
			} else {
				aggroStr = "a";
			}
			if(strList.size() == 0) {
				npcStr = "There is "; 
			} else { 
				npcStr = "";
			}
			npcStr = npcStr + aggroStr + " " +
						NPCsInRoom.get(i).getType() + " " + NPCsInRoom.get(i).getName();
			if(i < NPCsInRoom.size() - 1) {
				npcStr = npcStr + ", ";
			} else {
				npcStr = npcStr + ".";
			}
			strList.add(npcStr);
		}
		System.out.println(strList);
		return strList;
	}
	
	public ArrayList<String> getObjDescription(){
		ArrayList<String> strList = new ArrayList<String>();
		String objStr;
		String locked = null;
		
		for(int i = 0; i < roomInteractables.size(); i++) {
			objStr = null;
			if(!roomInteractables.get(i).getActivated()) {
				if(roomInteractables.get(i).getPuzzle() != null) {
					if(!roomInteractables.get(i).getPuzzle().getComplete()) {
						locked = "a locked";
					} else {
						locked = "an unlocked";
					}
				} else {
					locked = "a closed";
				}
			} else {
				locked = "an open";
			}
			if(strList.size() == 0) {
				objStr = "There is ";
			} else {
				objStr = "";
			}
			objStr = objStr + locked + " " + roomInteractables.get(i).getName();
			
			if(i < roomInteractables.size() - 1) {
				objStr = objStr + ", ";
			} else {
				objStr = objStr + ".";
			}
			strList.add(objStr);
		}
		return strList;
	}
}

