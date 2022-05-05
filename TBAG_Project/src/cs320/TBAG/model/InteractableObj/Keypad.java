package cs320.TBAG.model.InteractableObj;

import cs320.TBAG.model.Room;
import cs320.TBAG.model.PuzzleType.PinPuzzle;

public class Keypad extends Interactable {
	//String direction;
	int keypadId;
	
	public Keypad() {
		name = "Keypad";
		description = "There is a keypad.";
		location = new Room();
		activated = false;
		puzzleLock = null;
		activation = "enter";
	}

	@Override
	public String activateObj() {
		String result = null;
		Boolean unlocked;
		
		
		if(activated == false) {
			activation = "enter";
			if(puzzleLock != null && puzzleLock instanceof PinPuzzle) {
				if(!puzzleLock.getComplete()) {
					activated = true;
					activation = ((PinPuzzle) puzzleLock).getKey();
					result = puzzleLock.getHint();
				} else {
					result = puzzleLock.checkConditions();
				}
			}
		} else {
			activation = ((PinPuzzle) puzzleLock).getKey();
			if(puzzleLock != null && puzzleLock instanceof PinPuzzle) {
				result = puzzleLock.checkConditions();
				if(!puzzleLock.getComplete()) {
					puzzleLock.setComplete(true);
				} 
			} else {
				result = "Error 1";
			}
		}
		return result;
	}
	
	public void setKeypadId(int keypadId) {
		this.keypadId = keypadId;
	}
	
	public int getKeypadId() {
		return keypadId;
	}
	
//	public void setDirection(String direction) {
//		this.direction = direction;
//	}
//	
//	public String getDirection() {
//		return direction;
//	}
}