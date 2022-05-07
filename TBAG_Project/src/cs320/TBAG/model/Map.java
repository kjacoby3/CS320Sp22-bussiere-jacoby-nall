package cs320.TBAG.model;

import java.util.HashMap;

public class Map{
	int newRoomID;
	int prevRoomID;
	String currRoomName;
	String currRoomDescrip;
	HashMap<Integer, Room> rooms;
	
	
	public Map() {
		rooms = new HashMap<Integer, Room>();
	}
	
	public int canMove(int roomID, String direction) {
		
		Room room = rooms.get(roomID);
		RoomConnection roomConnection = room.getRoomConnection();
		int newRoom = -1;
		
		if (direction == "north") {
			if (roomConnection.getNorth() > 0){
				newRoom = roomConnection.getNorth();
				return newRoom;
			}
			else {
				return newRoom;
			}
		
		}
		
		if (direction == "east") {
			if (roomConnection.getEast() > 0){
				newRoom = roomConnection.getEast();
				return newRoom;
			}
			else {
				return newRoom;
			}
		}
		
		if (direction == "south") {
			if (roomConnection.getSouth() > 0){
				newRoom = roomConnection.getSouth();
				return newRoom;
			}
			else {
				return newRoom;
			}
		
		}
		
		if (direction == "west") {
			if (roomConnection.getWest() > 0){
				newRoom = roomConnection.getWest();
				return newRoom;
			}
			else {
				return newRoom;
			}
		
		}
		
		if (direction == "exit") {
			if (roomConnection.getExit() > 0){
				newRoom = roomConnection.getExit();
				return newRoom;
			}
			else {
				return newRoom;
			}
		
		}
		
		
		return newRoom;
		
	}
	
	public Room getRoom(int roomID) {
		return rooms.get(roomID);
	}
	
	public void addRoom(int roomID, Room room) {
		rooms.put(roomID, room);
	}
	
	
}