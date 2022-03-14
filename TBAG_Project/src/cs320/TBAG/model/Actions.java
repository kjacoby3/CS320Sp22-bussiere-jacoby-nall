package cs320.TBAG.model;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Map;
import cs320.TBAG.model.Item;

interface ActionsInterface{
	
	void move();
	void pickUp();
	void attack();
}

public class Actions implements ActionsInterface {
	public String direction;
	public Item item;
	Weapon banana = new Weapon("banana", 1000000000, 1000000000);
	
	@Override
	public void move() {
		Map tempMap = new Map();
		boolean verify = tempMap.checkMove(tempMap.getCurrRoom(), direction);
		if(verify = true) {
			int newID = tempMap.getNewRoomID();
			tempMap.setNewRoom(newID);
			System.out.println(tempMap.getRoomDescription(tempMap.getCurrRoom()));
		}
		else {
			System.out.println("There's no way to go that direction");
		}
		
		
	}
	@Override
	public void pickUp() {
		Inventory tempInv = new Inventory(1);
		boolean check = tempInv.addItem(banana);
		if(check = true) {
			System.out.println("You put the " + banana.getName() + " in your inventory");
		}
		else {
			System.out.println("You can't add that to your inventory at this time");
		}
		
	}
	
	@Override
	public void attack() {
		System.out.println("You successfully attack and defeat the target");
	}
			
	
}
