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

