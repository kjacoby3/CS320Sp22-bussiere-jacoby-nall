package cs320.TBAG.model;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Map;

public class Actions {
	public String direction;
	public String item;
			
	public void moveActor(String currActor, String direction) {
		String actor = currActor;
		String dir = direction;
		
		int currloc = actor.getCurrRoom();
		if (dir.checkMove() == true) {
			actor.setNewRoom();
			System.out.println(roomDescrip());
		}
		else {
			System.out.println("There's no way to go that diretion");
		}
		
		
	}
	
	public void pickUpItem(String currActor, String item) {
		currActor.Inventory.addItem(item);
	}
	
	
}
