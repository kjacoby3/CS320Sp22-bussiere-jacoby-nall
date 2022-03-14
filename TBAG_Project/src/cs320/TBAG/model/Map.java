package cs320.TBAG.model;

public class Map{
	private int roomNode;
	private int actorCurrRoom;
	private int actorNewRoom;
	private boolean validMove;
	private String enteredMove;
	int newRoomID;
	
	String[][] mapLayout;
	String[][] mapDescrips;
	
	
	String[][] trialMap = {{"RoomID","RoomName","Move","ID","Move","ID"},{"1","Starting Area","north","2","n","2" },{"2","North Area","south","2","s","2","east","4","e","4"},{"3","West Area","east","1","e","1","south","5","s","5"},{"4","North East Area","west","2","w","2"},{"5","West South","north","3","n","3"}};
	String[][] trialMapDescrip = {{"RoomID","LongDescrip"},{"1","You find yourself in the starting area.  There is a banana on the floor"},{"2","You head North into another area"},{"3","You head West into an area that seems more Western"},{"4","You head East from the North area. "},{"5","You head South from the West area"}};
	
	public Map() {
		actorCurrRoom = 1;
		this.mapLayout = trialMap;
		this.mapDescrips = trialMapDescrip;
	}
	
	public int getCurrRoom() {
		return actorCurrRoom;
	}
		
	public boolean checkMove(int actorCurrRoom, String direction)
	{
		int currRoom = actorCurrRoom;
		
		
		if(trialMap[currRoom][2] == direction){
					int newRoomID = Integer.parseInt(trialMap[currRoom][3]);
					return validMove = true;
			}
		
		else if(trialMap[currRoom][4] == direction) {
			int newRoomID = Integer.parseInt(trialMap[currRoom][5]);
			return validMove = true;
		}
		
		else if(trialMap[currRoom][6] == direction) {
			int newRoomID = Integer.parseInt(trialMap[currRoom][7]);
			return validMove = true;
		}
		
		else if(trialMap[currRoom][8] == direction) {
			int newRoomID = Integer.parseInt(trialMap[currRoom][9]);
			return validMove = true;
		}
		
		else {
					return validMove = false;
			}
		
	}
	
	public int getNewRoomID() {
		return newRoomID;
	}
	
	public int setNewRoom(int newRoomID) {
		this.actorNewRoom = newRoomID;
		return actorNewRoom;
	}
	
	public String getRoomDescription(int actorCurrRoom) {
		int currRoom = actorCurrRoom;
		return trialMapDescrip[currRoom][1];
	}
	
	public void loadLevel(String[][] layout, String[][] descrip) {
		this.mapLayout = layout;
		this.mapDescrips = descrip;
	}
}