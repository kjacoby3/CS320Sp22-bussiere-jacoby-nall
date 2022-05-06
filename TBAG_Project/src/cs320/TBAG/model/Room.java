package cs320.TBAG.model;
import java.util.ArrayList;
import java.util.Iterator;

import cs320.TBAG.model.InteractableObj.Door;
import cs320.TBAG.model.InteractableObj.Interactable;

public class Room extends Map{
	int roomID;
	int connectingID;
	String roomName;
	String roomDescription;
	String roomDescripLong;
	String roomDescripShort;
	int roomConnections;
	int roomUseable;
	int roomTreasure;
	int roomTrophy;
	int roomEquipment;
	int roomWeapon;
	int roomActor;
	int roomLevel;
	ArrayList<Interactable> roomInteractables;
	boolean roomPrevVisit;
	boolean roomActivatedCheck;
	int roomGameID;
	Inventory roomInv;

	
	Inventory roomItems;
	ArrayList<NPC> NPCsInRoom = new ArrayList<NPC>();
	ArrayList<Integer> availableExits = new ArrayList<Integer>();
	ArrayList<String> otherExitOptions = new ArrayList<String>();
	
	static ArrayList<String> directionsForValidation = new ArrayList<>();
	static {
		directionsForValidation.add("n");
		directionsForValidation.add("s");
		directionsForValidation.add("w");
		directionsForValidation.add("e");
	}
	
	ArrayList<Integer> roomExits = new ArrayList<Integer>(); //Will be replaced with availableExits and otherExitOptions
	Boolean verifyExit;
	int attemptedExit;
	
	public Room(int ID, String name, String descrip, ArrayList<Integer> exits) {
		this.roomID = ID;
		this.roomName = name;
		this.roomDescription = descrip;
		this.availableExits = exits;
		roomLevel = 1;
	}
	
	
	
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
		this.roomID = 1;
		this.roomName = "starting";
		this.roomDescripShort = "Starting Room";
		this.roomDescripLong = "This is the starting area.  You can go North(n)";  //"You awaken to sound of explosions and the rocking of the ship.  You shoot up out of bed and notice that your cabinmate is not in their bed.  There is a door leading out into the hall";
		this.roomLevel = 1;
		this.roomInv = null;
		this.roomPrevVisit = false;
		this.roomGameID = 1;
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
	
	public String getRoomDescrip() {
		return roomDescription;
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
	
	public void setRoomConnections(int connections) {
		this.roomConnections = connections;
	}
	
	public int getRoomConnections() {
		return roomConnections;
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
	/*public void setRoomItems(Inventory items) {
		this.roomItems = items;
	}
	
	public Inventory getRoomItems() {
		return roomItems;
	}
	
	public void setNPCsInRoom(ArrayList<NPC> npc) {
		this.NPCsInRoom = npc;
	}
	
	public ArrayList<NPC> getNPCsInRoom() {
		return NPCsInRoom;
	}
	*/
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
	
	public void setAvailableExits(ArrayList<Integer> exits) {
		this.availableExits = exits;
	}
	
	public ArrayList<Integer> getAvailableExits(){
		return availableExits;
	}
	
	public void setOtherExitOptions(ArrayList<String> others) {
		this.otherExitOptions = others;
	}
	
	public ArrayList<String> getOtherExitOptions(){
		return otherExitOptions;
	}
	
	public ArrayList<Integer> getRoomExits() { //this will be removed after exits moves over to availableExits
		return roomExits;
	}
	
	public int getConnectingID() {
		return connectingID;
	}
	
	public void setAttemptedExit(int attExit) {
		this.attemptedExit = attExit;
	}
	
	public boolean verifyExit(String direction) {
		int i=4;
		if (direction == "n") {
			i=0;
		}
		if (direction == "s") {
			i=1;
		}
		if (direction == "w") {
			i=2;
		}
		if (direction == "e") {
			i=3;
		}
		
		if (i >= roomExits.size()) {
			return false;
		}
		
		if (roomExits.get(i) != 0) {
			this.connectingID = roomExits.get(i);
			return true;
		}

		return false;
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
}

