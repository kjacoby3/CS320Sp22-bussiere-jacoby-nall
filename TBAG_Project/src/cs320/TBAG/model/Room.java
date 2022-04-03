package cs320.TBAG.model;
import java.util.ArrayList;
import java.util.Iterator;

public class Room extends Map{
	int roomID;
	int connectingID;
	String roomName;
	String roomDescrip;
	Inventory roomItems;
	ArrayList<NPC> NPCsInRoom = new ArrayList<NPC>();
	
	static ArrayList<String> directionsForValidation = new ArrayList<>();
	static {
		directionsForValidation.add("n");
		directionsForValidation.add("s");
		directionsForValidation.add("w");
		directionsForValidation.add("e");
	}
	
	ArrayList<Integer> roomExits = new ArrayList<Integer>();
	Boolean verifyExit;
	int attemptedExit;
	
	
	
	public Room(int roomID, String roomName, String roomDescrip, Inventory roomItems, ArrayList<NPC> NPCsInRoom, ArrayList<Integer> exits) { //exits must be entered as four values <0,0,0,0> for n,s,w,e
		this.roomID = roomID;
		this.roomDescrip = roomDescrip;
		this.roomItems = roomItems;
		this.NPCsInRoom = NPCsInRoom;
		this.roomExits = exits;
	}
	
	public Room() {
		this.roomID = 1;
		this.roomName = "starting";
		this.roomDescrip = "This is the starting area.  You can go North(n)";  //"You awaken to sound of explosions and the rocking of the ship.  You shoot up out of bed and notice that your cabinmate is not in their bed.  There is a door leading out into the hall";
		this.roomItems = null;	
		this.NPCsInRoom = null;
		this.roomExits.add(2);
		this.roomExits.add(0);
		this.roomExits.add(3);
		this.roomExits.add(0);
	}
	
	public int getRoomID() {
		return roomID;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public String getRoomDescrip() {
		return roomDescrip;
	}
	
	public Inventory getRoomItems() {
		return roomItems;
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
	
	public ArrayList<NPC> getNPCInRoom() {
		return NPCsInRoom;
	}
	
	public ArrayList<Integer> getRoomExits() {
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

