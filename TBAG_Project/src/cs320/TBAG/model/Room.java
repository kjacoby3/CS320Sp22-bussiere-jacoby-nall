package cs320.TBAG.model;
import java.util.ArrayList;

public class Room extends Map{
	int roomID;
	String roomDescrip;
	Inventory roomItems;
	ArrayList<Actor> actorsInRoom = new ArrayList<Actor>();
	
	public Room(int roomID, String roomDescrip, Inventory roomItems, ArrayList<Actor> actorsInRoom) {
		this.roomID = roomID;
		this.roomDescrip = roomDescrip;
		this.roomItems = roomItems;
		this.actorsInRoom = actorsInRoom;
	}
	
	public Room() {
		this.roomID = 1;
		this.roomDescrip = "You awaken to sound of explosions and the rocking of the ship.  You shoot up out of bed and notice that your cabinmate is not in their bed.  There is a door leading out into the hall";
		this.roomItems = null;	
		this.actorsInRoom = null;
	}
	
	public int getRoomID() {
		return roomID;
	}
	
	public String getRoomDescrip() {
		return roomDescrip;
	}
	
	public Inventory getRoomItems() {
		return roomItems;
	}
	
	public ArrayList<Actor> getActorsInRoom(){
		return actorsInRoom;
	}
	
	
		
}

