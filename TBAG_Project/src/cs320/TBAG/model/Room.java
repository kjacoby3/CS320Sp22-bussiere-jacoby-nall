package cs320.TBAG.model;
import java.util.ArrayList;
import java.util.Iterator;

public class Room extends Map{
	int roomID;
	int connectingID;
	String roomName;
	String roomDescripLong;
	String roomDescripShort;
	Inventory roomItems;
	ArrayList<NPC> NPCsInRoom = new ArrayList<NPC>();
	ArrayList<Integer> availableExits = new Arraylist<Integer>();
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
	
	
	
	public Room(int roomID, String roomName, String roomDescripLong, Inventory roomItems, ArrayList<NPC> NPCsInRoom, ArrayList<Integer> exits) { //exits must be entered as four values <0,0,0,0> for n,s,w,e
		this.roomID = roomID;
		this.roomDescripLong = roomDescripLong;
		this.roomItems = roomItems;
		this.NPCsInRoom = NPCsInRoom;
		this.roomExits = exits;
	}
	
	public Room() {
		this.roomID = 1;
		this.roomName = "starting";
		this.roomDescripLong = "This is the starting area.  You can go North(n)";  //"You awaken to sound of explosions and the rocking of the ship.  You shoot up out of bed and notice that your cabinmate is not in their bed.  There is a door leading out into the hall";
		this.roomItems = null;	
		this.NPCsInRoom = null;
		this.roomExits.add(2);
		this.roomExits.add(0);
		this.roomExits.add(3);
		this.roomExits.add(0);
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
	
	public void setRoomItems(Inventory items) {
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
	
	public void addNPCInRoom(NPC npc) {
		NPCsInRoom.add(npc);
	}
	
	public void removeNPCInRoom(NPC npc) {
		Iterator<NPC> itr = NPCsInRoom.iterator();
	    while (itr.hasNext()) {
	      NPC npcName = itr.next();
	      if (npcName.equals(npc)) {
	    	  NPCsInRoom.remove(npcName);
	      }
	    }
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
		
}

