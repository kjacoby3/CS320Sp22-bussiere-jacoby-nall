package cs320.TBAG.model;

import java.util.ArrayList;
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
		
		Room prevRoom = rooms.get(prevRoomID);
		RoomConnection prevRoomConnection = prevRoom.getRoomConnection();
		int newRoom = -1;
		
		Boolean aggroInRoom = false;
		
		ArrayList<NPC> npcList = room.getNPCsInRoom();
		for(NPC npc : npcList) {
			if(npc.getAggression() < 0) {
				aggroInRoom = true;
			}
		}
		
		if (direction == "north") {
			if (roomConnection.getNorth() > 0){
				//newRoom = roomConnection.getNorth();
				//return newRoom;
				
				if(aggroInRoom == false) {
					if(room.getDoor(direction) != null){
						System.out.println("Door Open? " + room.getDoor(direction).getActivated());
						if(room.getDoor(direction).getActivated()) {
							
							prevRoomID = roomID;
							newRoom = roomConnection.getNorth();
						} else {
							return newRoom;
						}
					} else {
						prevRoomID = roomID;
						newRoom = roomConnection.getNorth();
						return newRoom;
					}
				} else if(aggroInRoom == true && prevRoomConnection.getSouth() == roomID){
					if(room.getDoor(direction) != null){
						System.out.println("Door Open? " + room.getDoor(direction).getActivated());
						if(room.getDoor(direction).getActivated()) {
							prevRoomID = roomID;
							newRoom = roomConnection.getNorth();
						} else {
							return newRoom;
						}
					} else {
						prevRoomID = roomID;
						newRoom = roomConnection.getNorth();
						return newRoom;
					}
				} else {
					return newRoom;
				}
			}
			else {
				return newRoom;
			}
		
		}
		
		if (direction == "east") {
			if (roomConnection.getEast() > 0){
				//newRoom = roomConnection.getEast();
				//return newRoom;
				if(aggroInRoom == false) {
					if(room.getDoor(direction) != null){
						System.out.println("Door Open? " + room.getDoor(direction).getActivated());
						if(room.getDoor(direction).getActivated()) {
							prevRoomID = roomID;
							newRoom = roomConnection.getEast();
						} else {
							return newRoom;
						}
					} else {
						prevRoomID = roomID;
						newRoom = roomConnection.getEast();
						return newRoom;
					}
				} else if(aggroInRoom == true && prevRoomConnection.getWest() == roomID) {
					if(room.getDoor(direction) != null){
						System.out.println("Door Open? " + room.getDoor(direction).getActivated());
						if(room.getDoor(direction).getActivated()) {
							prevRoomID = roomID;
							newRoom = roomConnection.getEast();
						} else {
							return newRoom;
						}
					} else {
						prevRoomID = roomID;
						newRoom = roomConnection.getEast();
						return newRoom;
					}
				} else {
					return newRoom;
				}
			}
			else {
				return newRoom;
			}
		}
		
		if (direction == "south") {
			if (roomConnection.getSouth() > 0){
				//newRoom = roomConnection.getSouth();
				//return newRoom;
				if(aggroInRoom == false) {
					if(room.getDoor(direction) != null){
						System.out.println("Door Open? " + room.getDoor(direction).getActivated());
						if(room.getDoor(direction).getActivated()) {
							prevRoomID = roomID;
							newRoom = roomConnection.getSouth();
						} else {
							return newRoom;
						}
					} else {
						prevRoomID = roomID;
						newRoom = roomConnection.getSouth();
						return newRoom;
					}
				} else if(aggroInRoom == true && prevRoomConnection.getNorth() == roomID) {
					if(room.getDoor(direction) != null){
						System.out.println("Door Open? " + room.getDoor(direction).getActivated());
						if(room.getDoor(direction).getActivated()) {
							prevRoomID = roomID;
							newRoom = roomConnection.getSouth();
						} else {
							return newRoom;
						}
					} else {
						prevRoomID = roomID;
						newRoom = roomConnection.getSouth();
						return newRoom;
					}
				} else {
					return newRoom;
				}
			}
			else {
				return newRoom;
			}
		
		}
		
		if (direction == "west") {
			if (roomConnection.getWest() > 0){
				//newRoom = roomConnection.getWest();
				//return newRoom;
				if(aggroInRoom == false) {
					if(room.getDoor(direction) != null){
						System.out.println("Door Open? " + room.getDoor(direction).getActivated());
						if(room.getDoor(direction).getActivated()) {
							prevRoomID = roomID;
							newRoom = roomConnection.getWest();
						} else {
							return newRoom;
						}
					} else {
						prevRoomID = roomID;
						newRoom = roomConnection.getWest();
						return newRoom;
					}
				} else if(aggroInRoom == true && prevRoomConnection.getEast() == roomID) {
					if(room.getDoor(direction) != null){
						System.out.println("Door Open? " + room.getDoor(direction).getActivated());
						if(room.getDoor(direction).getActivated()) {
							prevRoomID = roomID;
							newRoom = roomConnection.getWest();
						} else {
							return newRoom;
						}
					} else {
						prevRoomID = roomID;
						newRoom = roomConnection.getWest();
						return newRoom;
					}
				} else {
					return newRoom;
				}
			}
			else {
				return newRoom;
			}
		
		}
		
		if (direction == "exit") {
			if (roomConnection.getExit() > 0){
				prevRoomID = roomID;
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
	
	public HashMap<Integer, Room> getRooms(){
		return rooms;
	}
	
	public void setPrevRoomID(int prevRoomID) {
		this.prevRoomID = prevRoomID;
	}
	
	public int getPrevRoomID() {
		return prevRoomID;
	}
	
}