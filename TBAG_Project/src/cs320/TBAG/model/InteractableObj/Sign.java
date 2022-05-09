package cs320.TBAG.model.InteractableObj;

import cs320.TBAG.model.Room;
import cs320.TBAG.model.PuzzleType.Puzzle;

public class Sign extends Interactable {
	int signId;
	String message;
	
	public Sign() {
		name = "Sign";
		description = "There is a sign.";
		message = "Blank message";
		activation = "read";
		location = new Room();
		activated = false;
		puzzleLock = null;
	}
	
	public Sign(String name, String description, String message, Room location, Puzzle puzzleLock) {
		this.name = name;
		this.description = description;
		this.message = message;
		this.location = location;
		this.puzzleLock = puzzleLock;
		activation = "read";
		activated = false;
	}
	
	@Override
	public String activateObj() {
		String result;
		Boolean unlocked;
		if(puzzleLock != null) {
			unlocked = puzzleLock.getComplete();
		} else {
			unlocked = true;
		}
		
		if(unlocked) {
			result = "The sign reads: " + message;
		} else {
			result = puzzleLock.getHint();
		}
		
		return result;
	}
	
	public void setSignId(int signId) {
		this.signId = signId;
	}
	
	public int getSignId() {
		return signId;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}