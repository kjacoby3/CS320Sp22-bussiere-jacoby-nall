package cs320.TBAG.model;

import java.util.ArrayList;
import java.util.Iterator;

import cs320.TBAG.database.DatabaseProvider;
import cs320.TBAG.database.DerbyDatabase;
import cs320.TBAG.database.IDatabase;
import cs320.TBAG.dbclass.InitDatabase;

public class Map{
	private int roomNode;
	private int actorCurrRoom;
	private int actorNewRoom;
	private boolean validMove;
	private String enteredMove;
	int newRoomID;
	int prevRoomID;
	String currRoomName;
	String currRoomDescrip;
	private ArrayList<Room> roomList;
	ArrayList<Integer> validExits = new ArrayList<Integer>();
	//ArrayList<Room> trialRooms = new ArrayList<Room>();
	ArrayList<Integer> startRoom = new ArrayList<Integer>();
	
	ArrayList<Room> level1Rooms;
	
	
	ArrayList<Actor> actors = null;
	Inventory roomInventory = null;
	
	
	String[][] mapLayout;
	String[][] mapDescrips;
	
	
	String[][] trialMap = {{"RoomID","RoomName","Move","ID","Move","ID"},{"1","Starting Area","north","2","n","2" },{"2","North Area","south","2","s","2","east","4","e","4"},{"3","West Area","east","1","e","1","south","5","s","5"},{"4","North East Area","west","2","w","2"},{"5","West South","north","3","n","3"}};
	String[][] trialMapDescrip = {{"RoomID","LongDescrip"},{"1","You find yourself in the starting area.  There is a banana on the floor"},{"2","You head North into another area"},{"3","You head West into an area that seems more Western"},{"4","You head East from the North area. "},{"5","You head South from the West area"}};
	
	public static ArrayList<Room> trialRooms = new ArrayList<Room>();
	
	static {
		ArrayList<Integer> startRoom = new ArrayList<Integer>();
		startRoom.add(2);
		startRoom.add(0);
		startRoom.add(0);
		startRoom.add(0);
		Room starting = new Room(1, "starting", "This is the starting area.  You can go North(n)", startRoom);
		trialRooms.add(starting);
		ArrayList<Integer> Room2 = new ArrayList<Integer>();
		Room2.add(0);
		Room2.add(1);
		Room2.add(0);
		Room2.add(4);
		Room second = new Room(2, "second", "North Area. You can go South to Start or East", Room2);
		trialRooms.add(second);
		ArrayList<Integer> Room3 = new ArrayList<Integer>();
		Room3.add(0);
		Room3.add(5);
		Room3.add(0);
		Room3.add(1);
		Room third = new Room(3, "third" ,"West Area. You can go East to Start or South", Room3);
		trialRooms.add(third);
		ArrayList<Integer> Room4 = new ArrayList<Integer>();
		Room4.add(0);
		Room4.add(0);
		Room4.add(2);
		Room4.add(0);
		Room fourth = new Room(4, "fourth", "North East Area. You can only go back West to the previous room", Room4);
		trialRooms.add(fourth);
		ArrayList<Integer> Room5 = new ArrayList<Integer>();
		Room5.add(3);
		Room5.add(0);
		Room5.add(0);
		Room5.add(0);
		Room fifth = new Room(5, "fifth",  "West South Area. You can only go back North to the previous room", Room5);
		trialRooms.add(fifth);
	}
	
	
	public Map() {
		InitDatabase.init(1);
		level1Rooms = new ArrayList<Room>();
		ArrayList<Integer> exits = new ArrayList<Integer>();
		IDatabase db = DatabaseProvider.getInstance();
		
		//DerbyDatabase db = new DerbyDatabase();
		
		/*for (int i = 1; i<21; i++) {
			Room room = db.getRoomByID(i);
			RoomConnection roomConnection = db.getRoomConnectionByID(i);
			
			if (room.getRoomLevel() == 1) {
				exits.add(roomConnection.getNorth());
				exits.add(roomConnection.getEast());
				exits.add(roomConnection.getSouth());
				exits.add(roomConnection.getWest());
				
				Room addToMap = new Room(i, room.getRoomName(), room.getRoomDescripLong(),exits);
				level1Rooms.add(addToMap);
				
				exits.clear();
				
			}
			
			//if (room.getRoomLevel() == 2) {
			//	exits.add(roomConnection.getNorth());
			//	exits.add(roomConnection.getEast());
			//	exits.add(roomConnection.getSouth());
			//	exits.add(roomConnection.getWest());
			//	
			//	Room addToMap = new Room(i, room.getRoomName(), room.getRoomDescripLong(),exits);
			//	level1Rooms.add(addToMap);
			//	
			//	exits.clear();
				
			//}
			
			
			
		}*/
		ArrayList<Room> rooms = db.getRooms();
		ArrayList<RoomConnection> connections= db.getConnections();
		
		Iterator<RoomConnection> i = connections.iterator();
		
		for (Room room : rooms) {
			ArrayList<Integer> exit = new ArrayList<Integer>();
			RoomConnection conn = i.next();
			exit.add(conn.getNorth());
			exit.add(conn.getEast());
			exit.add(conn.getSouth());
			exit.add(conn.getWest());
			room.setAvailableExits(exit);
			
			level1Rooms.add(room);
			
		}
		
		
		
		
		
		this.actorCurrRoom = 1;
		this.currRoomName = "starting";
		this.currRoomDescrip = "This is the starting area.  You can go North(n)";
		this.actors = null;
		this.roomInventory = null;
		//this.validExits = startRoom;
		
	}
	
	public int getCurrRoom() {
		return actorCurrRoom;
	}
	public void setCurrRoom(int currRoom) {
		actorCurrRoom = currRoom;
	}
	
	public String getCurrRoomName() {
		return currRoomName;
	}
	//public boolean checkMove(int actorCurrRoom, String direction)
	/*public boolean checkMove(String direction)
	{
		//int currRoom = actorCurrRoom;
		//if(trialMap[currRoom][2] == direction){
		//			int newRoomID = Integer.parseInt(trialMap[currRoom][3]);
		//			return validMove = true;
		//	}
		
		//else if(trialMap[currRoom][4] == direction) {
		//	int newRoomID = Integer.parseInt(trialMap[currRoom][5]);
		//	return validMove = true;
		//}
		
		//else if(trialMap[currRoom][6] == direction) {
		//	int newRoomID = Integer.parseInt(trialMap[currRoom][7]);
		//	return validMove = true;
		//}
		
		//else if(trialMap[currRoom][8] == direction) {
		//	int newRoomID = Integer.parseInt(trialMap[currRoom][9]);
		//	return validMove = true;
		//}
		
		//else {
		//			return validMove = false;
		//	}
		
		Room directionCheck = trialRooms.get(actorCurrRoom-1);
		if (directionCheck.verifyExit(direction) == true) {
			int connectedRoomID = directionCheck.getConnectingID();
			Room connectedRoom = trialRooms.get(connectedRoomID-1);
			
			prevRoomID = actorCurrRoom;
			actorCurrRoom = connectedRoom.getRoomID();
			currRoomName = connectedRoom.getRoomName();
			currRoomDescrip = connectedRoom.getRoomDescrip();
			return true;
		}
		
		return false;
	}*/
	
	
	public int canMove(int ID, String direction) {
		
		IDatabase db = DatabaseProvider.getInstance();
		Room room = db.getRoomByID(ID);
		RoomConnection roomConnection = db.getRoomConnectionByID(ID);
		int newRoom = -1;
		
		if (direction == "north") {
			//return roomConnection.getNorth();
			if (roomConnection.getNorth() > 0){
				
				/*prevRoomID = actorCurrRoom;
				actorCurrRoom = roomConnection.getNorth();
				Room connectedRoom = db.getRoomByID(actorCurrRoom);
				currRoomName = connectedRoom.getRoomName();
				currRoomDescrip = connectedRoom.getRoomDescrip();*/
				
				newRoom = roomConnection.getNorth();
				return newRoom;
			}
			else {
				return newRoom;
			}
		
		}
		
		if (direction == "east") {
			if (roomConnection.getEast() > 0){
				
				/*prevRoomID = actorCurrRoom;
				actorCurrRoom = roomConnection.getEast();
				Room connectedRoom = db.getRoomByID(actorCurrRoom);
				currRoomName = connectedRoom.getRoomName();
				currRoomDescrip = connectedRoom.getRoomDescrip();*/
				newRoom = roomConnection.getEast();
				return newRoom;
			}
			else {
				return newRoom;
			}
		}
		
		if (direction == "south") {
			if (roomConnection.getSouth() > 0){
				
				/*prevRoomID = actorCurrRoom;
				actorCurrRoom = roomConnection.getSouth();
				Room connectedRoom = db.getRoomByID(actorCurrRoom);
				currRoomName = connectedRoom.getRoomName();
				currRoomDescrip = connectedRoom.getRoomDescrip();*/
				
				newRoom = roomConnection.getSouth();
				return newRoom;
			}
			else {
				return newRoom;
			}
		
		}
		
		if (direction == "west") {
			if (roomConnection.getWest() > 0){
				
				/*prevRoomID = actorCurrRoom;
				actorCurrRoom = roomConnection.getWest();
				Room connectedRoom = db.getRoomByID(actorCurrRoom);
				currRoomName = connectedRoom.getRoomName();
				currRoomDescrip = connectedRoom.getRoomDescrip();*/
				
				newRoom = roomConnection.getWest();
				return newRoom;
			}
			else {
				return newRoom;
			}
		
		}
		
		if (direction == "exit") {
			if (roomConnection.getExit() > 0){
				
				/*prevRoomID = actorCurrRoom;
				actorCurrRoom = roomConnection.getExit();
				Room connectedRoom = db.getRoomByID(actorCurrRoom);
				currRoomName = connectedRoom.getRoomName();
				currRoomDescrip = connectedRoom.getRoomDescrip();*/
				
				newRoom = roomConnection.getExit();
				return newRoom;
			}
			else {
				return newRoom;
			}
		
		}
		
		
		return newRoom;
		
	}
	
	public void setPrevRoomID(int actorCurrRoom) {
		this.prevRoomID = actorCurrRoom;
		}

	public int getNewRoomID() {
		return newRoomID;
	}
	
	public void setNewRoom(int newRoomID) {
		this.actorNewRoom = newRoomID;
	}
	
	public String getRoomDescription() {
		/*Room test = new Room(actorCurrRoom, currRoomName,currRoomDescrip, roomInventory, actors, validExits);
		String roomDescrip = test.getRoomDescrip();
		return roomDescrip;*/
		return trialRooms.get(actorCurrRoom-1).getRoomDescrip();
		//return currRoomDescrip;
	}
	
	public void loadLevel(String[][] layout, String[][] descrip) {
		this.mapLayout = layout;
		this.mapDescrips = descrip;
	}
	
	public void teleport(int ID) {
		prevRoomID = actorCurrRoom;
		actorCurrRoom = ID;
		Room teleportRoom = trialRooms.get(actorCurrRoom-1);
		currRoomName = teleportRoom.getRoomName();
		currRoomDescrip = teleportRoom.getRoomDescrip();
	}
	
	public void addRoom(Room room) {
		roomList.add(room);
	}
	
	
}