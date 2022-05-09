package cs320.TBAG.model;

public class Treasure extends Item{
	/*int hpMod;
	int defMod;
	int spdMod;
	int dmgMod;*/
	
	public Treasure(int itemID, String name, int price, int playerID, int roomID, int npcID) {
		this.itemID=itemID;
		this.name = name;
		type = "Treasure";
		this.price = price;
		this.playerID =playerID;
		this.roomID=roomID;
		this.npcID = npcID;
		
		/*this.hpMod = hpMod;
		this.defMod = defMod;
		this.spdMod = spdMod;
		this.dmgMod = dmgMod;*/
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	/*public int getHPMod() {
		return hpMod;
	}
	
	public int getDefMod() {
		return defMod;
	}
	
	public int getSpdMod() {
		return spdMod;
	}
	
	public int getDmgMod() {
		return dmgMod;
	}
	*/
}