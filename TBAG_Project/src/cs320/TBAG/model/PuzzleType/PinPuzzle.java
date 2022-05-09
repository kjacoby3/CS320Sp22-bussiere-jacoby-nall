package cs320.TBAG.model.PuzzleType;

public class PinPuzzle extends Puzzle{
	String key;
	int interactableId;
	int pinPuzzleId;
	
	public PinPuzzle() {
		key = "1234";
		hint = "This door requires a password with " + key.length() + " digits.";
		completeMSG = "The pin you entered unlocked the door.";
		currencyReward = 0;
		expReward = 0;
		rewardItem = null;
		complete = false;
	}

	@Override
	public String checkConditions() {
		String result;
		if(complete == true) {
			result = "Already unlocked";
		} else {
			result = completeMSG;
		}
		return result;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setInteractableId(int interactableId) {
		this.interactableId = interactableId;
	}
	
	public int getInteractableId() {
		return interactableId;
	}
	
	public void setPinPuzzleId(int pinPuzzleId) {
		this.pinPuzzleId = pinPuzzleId;
	}
	
	public int getPinPuzzleId() {
		return pinPuzzleId;
	}
}