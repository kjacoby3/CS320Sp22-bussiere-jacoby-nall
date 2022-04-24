package cs320.TBAG.model.InteractableObj;

import cs320.TBAG.model.PuzzleType.Puzzle;
import cs320.TBAG.model.Room;
import cs320.TBAG.model.InteractableObj.Interactable;

public class Door extends Interactable {
	String direction;
	
	public Door() {
		name = "Door";
		description = "There is an unlocked door.";
		activation = "open";
		location = new Room();
		activated = false;
		puzzleLock = null;
	}
	
	public Door(String name, String description, Room location, Boolean activated, Puzzle puzzleLock) {
		this.name = name;
		this.description = description;
		activation = "open";
		this.location = location;
		this.activated = activated;
		this.puzzleLock = puzzleLock;
	}
	
	
	public String activateObj(String activation) {
		String result;
		Boolean unlocked;
		if(activated == false) {
			if(puzzleLock != null) {
				unlocked = puzzleLock.getComplete();
			} else {
				unlocked = true;
			}
			
			if(unlocked) {
				result = "You opened the door";
			} else {
				result = puzzleLock.getHint();
			}
		} else {
			result = "The door is already open.";
		}
		
		
		return result;
	}
}