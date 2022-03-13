package cs320.TBAG.model;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Map;

interface ActionsInterface{
	
	void move();
	void pickUp();
}

public class Actions implements ActionsInterface {
	public String direction;
	public Item item;
	
	@Override
	public void move() {
		Map tempMap = new Map();
		boolean verify = tempMap.checkMove(actorCurrRoom, direction);
		if(verify = true) {
			int newID = tempMap.getNewRoomID();
			tempMap.setNewRoom();
		}
		
		
	}
	@Override
	public void pickUp() {
		Inventory tempInv = new Inventory(1);
		tempInv.addItem(item);
		
	}
			
	
}
