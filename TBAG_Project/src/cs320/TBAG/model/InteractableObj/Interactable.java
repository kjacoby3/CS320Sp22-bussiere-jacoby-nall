package cs320.TBAG.model.InteractableObj;

import cs320.TBAG.model.PuzzleType.Puzzle;
import cs320.TBAG.model.Room;

public abstract class Interactable {
	protected String name;
	protected String description;
	protected String activation;
	protected Room location;
	protected Boolean activated;
	protected Puzzle puzzleLock;
	protected int roomId;
	protected int puzzleId;
	protected int interactableId;
	
	public abstract String activateObj();
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getActivationKeyword() {
		return activation;
	}
	
	public void setActivationKeyword(String activation) {
		this.activation = activation;
	}
	
	public Room getLocation() {
		return location;
	}
	
	public void setLocation(Room location) {
		this.location = location;
	}
	
	public Boolean getActivated() {
		return activated;
	}
	
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
	
	public Puzzle getPuzzle() {
		return puzzleLock;
	}
	
	public void setPuzzle(Puzzle puzzleLock) {
		this.puzzleLock = puzzleLock;
	}
	
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public void setPuzzleId(int puzzleId) {
		this.puzzleId = puzzleId;
	}
	
	public int getPuzzleId() {
		return puzzleId;
	}
	
	public void setInteractableId(int interactableId) {
		this.interactableId = interactableId;
	}
	
	public int getInteractableId() {
		return interactableId;
	}
}