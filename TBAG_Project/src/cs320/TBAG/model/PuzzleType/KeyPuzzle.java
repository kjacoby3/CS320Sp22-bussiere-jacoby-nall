package cs320.TBAG.model.PuzzleType;

import cs320.TBAG.model.Treasure;

public class KeyPuzzle extends Puzzle {
	Treasure key;
	
	
	public KeyPuzzle() {
		this.key = new Treasure("Blank Key", 0, 0, 0, 0, 0);
		complete = false;
		hint = "This door needs a key.";
		completeMSG = "You unlocked the door with the key";
		currencyReward = 0;
		expReward = 0;
		rewardItem = null;
	}
	
	public String checkConditions() {
		String result;
		if(complete == true) {
			result = "Already unlocked";
		} else {
			result = completeMSG;
		}
		return result;
	}
	
	public Treasure getKey() {
		return key;
	}
	
	public void setKey(Treasure key) {
		this.key = key;
	}
	
}