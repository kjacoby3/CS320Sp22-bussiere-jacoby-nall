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
	
	public abstract String activateObj(String activation);
	
	
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
}