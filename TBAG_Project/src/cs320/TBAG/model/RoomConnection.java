package cs320.TBAG.model;

public class RoomConnection {
	
	private int roomID;
	private int north;
	private int east;
	private int south;
	private int west;
	private int exit;
	
	public RoomConnection() {
		
	}
	
	public void setRoomID(int ID) {
		this.roomID = ID;
	}
	
	public int getRoomID() {
		return roomID;
	}
	
	public void setNorth(int n) {
		this.north = n;
	}
	
	public int getNorth() {
		return north;
	}
	
	public void setEast(int e) {
		this.east = e;
	}
	
	public int getEast() {
		return east;
	}
	
	public void setSouth(int s) {
		this.south = s;
	}
	
	public int getSouth() {
		return south;
	}
	
	public void setWest(int w) {
		this.west = w;
	}
	
	public int getWest() {
		return west;
	}
	
	public void setExit(int ex) {
		this.exit = ex;
	}
	
	public int getExit() {
		return exit;
	}

}
