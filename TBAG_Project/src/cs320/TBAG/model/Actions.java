package cs320.TBAG.model;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Map;

interface ActionsInterface{
	
	void move();
	void pickUp();
}

public class Actions implements ActionsInterface {
	public String direction;
	public String item;
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pickUp() {
		// TODO Auto-generated method stub
		
	}
			
	
}
