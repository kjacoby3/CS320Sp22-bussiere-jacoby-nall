package cs320.TBAG.model;

import java.util.ArrayList;

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
	ArrayList<Integer> validExits = new ArrayList<Integer>();
	
	ArrayList<Actor> actors = null;
	Inventory roomInventory = null;
	
	
	String[][] mapLayout;
	String[][] mapDescrips;
	
	
	String[][] trialMap = {{"RoomID","RoomName","Move","ID","Move","ID"},{"1","Starting Area","north","2","n","2" },{"2","North Area","south","2","s","2","east","4","e","4"},{"3","West Area","east","1","e","1","south","5","s","5"},{"4","North East Area","west","2","w","2"},{"5","West South","north","3","n","3"}};
	String[][] trialMapDescrip = {{"RoomID","LongDescrip"},{"1","You find yourself in the starting area.  There is a banana on the floor"},{"2","You head North into another area"},{"3","You head West into an area that seems more Western"},{"4","You head East from the North area. "},{"5","You head South from the West area"}};
	
	public static ArrayList<Room> trialRooms = new ArrayList<Room>();
	

	
	
	static {
		//Room starting = new Room();
		ArrayList<Integer> startRoom = new ArrayList<Integer>();
		startRoom.set(0,2);
		startRoom.set(1,0);
		startRoom.set(2,0);
		startRoom.set(3,0);
		Room starting = new Room(1, "starting", "This is the starting area.  You can go North(n)", null, null, startRoom);
		trialRooms.add(starting);
		ArrayList<Integer> Room2 = new ArrayList<Integer>();
		Room2.set(0,0);
		Room2.set(1,2);
		Room2.set(2,0);
		Room2.set(3,4);
		Room second = new Room(2, "second", "North Area. You can go South to Start or East", null, null, Room2);
		trialRooms.add(second);
		ArrayList<Integer> Room3 = new ArrayList<Integer>();
		Room3.set(0,0);
		Room3.set(1,5);
		Room3.set(2,0);
		Room3.set(3,1);
		Room third = new Room(3, "third" ,"West Area. You can go East to Start or South", null, null, Room3);
		trialRooms.add(third);
		ArrayList<Integer> Room4 = new ArrayList<Integer>();
		Room4.set(0,0);
		Room4.set(1,0);
		Room4.set(2,2);
		Room4.set(3,0);
		Room fourth = new Room(4, "fourth", "North East Area. You can only go back West to the previous room", null, null, Room4);
		trialRooms.add(fourth);
		ArrayList<Integer> Room5 = new ArrayList<Integer>();
		Room5.set(0, 3);
		Room5.set(1, 0);
		Room5.set(2, 0);
		Room5.set(3, 0);
		Room fifth = new Room(5, "fifth",  "West South Area. You can only go back North to the previous room", null, null, Room5);
		trialRooms.add(fifth);
	}
	
	
	
	public Map() {
		actorCurrRoom = 1;
		this.mapLayout = trialMap;
		this.mapDescrips = trialMapDescrip;
		//Room starting = new Room();
		ArrayList<Integer> startRoom = new ArrayList<Integer>();
		startRoom.set(0,2);
		startRoom.set(1,0);
		startRoom.set(2,0);
		startRoom.set(3,0);
		Room starting = new Room(1, "starting", "This is the starting area.  You can go North(n)", null, null, startRoom);
		trialRooms.add(starting);
		ArrayList<Integer> Room2 = new ArrayList<Integer>();
		Room2.set(0,0);
		Room2.set(1,2);
		Room2.set(2,0);
		Room2.set(3,4);
		Room second = new Room(2, "second", "North Area. You can go South to Start or East", null, null, Room2);
		trialRooms.add(second);
		ArrayList<Integer> Room3 = new ArrayList<Integer>();
		Room3.set(0,0);
		Room3.set(1,5);
		Room3.set(2,0);
		Room3.set(3,1);
		Room third = new Room(3, "third" ,"West Area. You can go East to Start or South", null, null, Room3);
		trialRooms.add(third);
		ArrayList<Integer> Room4 = new ArrayList<Integer>();
		Room4.set(0,0);
		Room4.set(1,0);
		Room4.set(2,2);
		Room4.set(3,0);
		Room fourth = new Room(4, "fourth", "North East Area. You can only go back West to the previous room", null, null, Room4);
		trialRooms.add(fourth);
		ArrayList<Integer> Room5 = new ArrayList<Integer>();
		Room5.set(0, 3);
		Room5.set(1, 0);
		Room5.set(2, 0);
		Room5.set(3, 0);
		Room fifth = new Room(5, "fifth",  "West South Area. You can only go back North to the previous room", null, null, Room5);
		trialRooms.add(fifth);
		
		this.actorCurrRoom = 1;
		this.currRoomName = "starting";
		this.currRoomDescrip = "This is the starting area.  You can go North(n)";
		this.actors = null;
		this.roomInventory = null;
		this.validExits = startRoom;
		
	}
	
	public int getCurrRoom() {
		return actorCurrRoom;
	}
	public void setCurrRoom(int currRoom) {
		this.actorCurrRoom = currRoom;
	}
		
	//public boolean checkMove(int actorCurrRoom, String direction)
	public boolean checkMove(String direction)
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
			this.prevRoomID = actorCurrRoom;
			this.actorCurrRoom = directionCheck.getConnectingID();
			currRoomName = directionCheck.getRoomName();
			currRoomDescrip = directionCheck.getRoomDescrip();
			return true;
		}
		
		return false;
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
		Room test = new Room(actorCurrRoom, currRoomName,currRoomDescrip, roomInventory, actors, validExits);
		String roomDescrip = test.getRoomDescrip();
		return roomDescrip;
	}
	
	public void loadLevel(String[][] layout, String[][] descrip) {
		this.mapLayout = layout;
		this.mapDescrips = descrip;
	}
}