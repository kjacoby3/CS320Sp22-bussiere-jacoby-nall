package cs320.TBAG.model.PuzzleType;

import cs320.TBAG.model.Treasure;

public class KeyPuzzle extends Puzzle {
	Treasure key;
	int keyPuzzleId;
	int itemId;
	int interactableId;
	
	public KeyPuzzle() {
		this.key = new Treasure(0,"Blank Key", 0, 0, 0, 0);
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
	
	public int getItemId() {
		return itemId;
	}
	
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public int getInteractableId() {
		return interactableId;
	}
	
	public void setInteractableId(int interactableId) {
		this.interactableId = interactableId;
	}
	
	public void setKeyPuzzleId(int keyPuzzleId) {
		this.keyPuzzleId = keyPuzzleId;
	}
	
	public int getKeyPuzzleId() {
		return keyPuzzleId;
	}
}