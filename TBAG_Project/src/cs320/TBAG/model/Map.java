package cs320.TBAG.model;

public class Map{
	private int roomNode;
	private int actorCurrRoom;
	private int actorNewRoom;
	private boolean validMove;
	private String enteredMove;
	
	public int getCurrRoom() {
		return actorCurrRoom;
	}
		
	public boolean checkMove()
	{
		return validMove;
	}
	
	public void setNewRoom(int actorNewRoom) {
		this.actorNewRoom = actorNewRoom;
	}
	
	public void attemptMove(int roomNode, String enteredMove) {
		//check move in CSV
	}
	
	
}