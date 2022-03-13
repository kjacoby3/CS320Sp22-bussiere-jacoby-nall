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
			tempMap.setNewRoom(newID);
			System.out.println(tempMap.getRoomDescription(actorCurrRoom));
		}
		else {
			System.out.println("There's no way to go that direction");
		}
		
		
	}
	@Override
	public void pickUp() {
		Inventory tempInv = new Inventory(1);
		boolean check = tempInv.addItem(item);
		if(check = true) {
			System.out.println("You put the " + item + " in your inventory");
		}
		else {
			System.out.println("You can't add that to your inventory at this time");
		}
		
	}
			
	
}
