package cs320.TBAG.model;

public abstract class Item {
	String name;
	String type;
	int price;
	int playerID;
	int roomID;
	int npcID;
	int itemID;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getBuyPrice() {
		return price;
	}
	
	public int getSellPrice() {
		return (int) (price*0.7);
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getItemID() {
		return itemID;
	}
	
	public int getPlayerID(){
		return playerID;
	}
	public int getRoomID() {
		return roomID;
	}
	public int getNPCID() {
		return npcID;
	}
	
	public void setPlayerID(int playerID) {
		this.playerID=playerID;
	}
	public void setRoomID(int roomID) {
		this.roomID=roomID;
	}
	public void setNPCID(int npcID) {
		this.npcID=npcID;
	}
}