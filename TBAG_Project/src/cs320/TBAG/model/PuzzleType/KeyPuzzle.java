package cs320.TBAG.model.PuzzleType;

import cs320.TBAG.model.Treasure;

public class KeyPuzzle extends Puzzle {
	Treasure key;
	int keyPuzzleId;
	int treasureId;
	int interactableId;
	
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
	
	public int getTreasureId() {
		return treasureId;
	}
	
	public void setTreasureId(int treasureId) {
		this.treasureId = treasureId;
	}
	
	public int getInteractableId() {
		return interactableId;
	}
	
	public void setInteractableId(int interactableId) {
		this.interactableId = interactableId;
	}
}