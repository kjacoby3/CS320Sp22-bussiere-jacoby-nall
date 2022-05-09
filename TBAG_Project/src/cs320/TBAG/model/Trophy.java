package cs320.TBAG.model;

public class Trophy extends Item{
	public Trophy(int itemID, String name, int price, int playerID, int roomID, int npcID) {
		this.itemID = itemID;
		this.name = name;
		this.price = price;
		type = "Trophy";
		this.playerID = playerID;
		this.roomID=roomID;
		this.npcID=npcID;
	}
	
	public int getPrice() {
		return (int) (price*.7);
	}
}
