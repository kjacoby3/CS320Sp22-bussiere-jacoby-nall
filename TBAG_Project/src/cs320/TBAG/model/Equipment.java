package cs320.TBAG.model;

public class Equipment extends Item{
	int defenseMod;
	int hpMod;
	int speedMod;
	boolean equipped;
	
	public Equipment(String name, int price, int defenseMod, int hpMod, int speedMod, int playerID, int roomID, int npcID, Boolean equipped) {
		this.name = name;
		this.type = "Equipment";
		this.defenseMod = defenseMod;
		this.hpMod = hpMod;
		this.speedMod = speedMod;
		this.price = price;
		this.playerID=playerID;
		this.roomID=roomID;
		this.npcID=npcID;
		this.equipped=equipped;
	}
	
	public int getDefenseMod() {
		return defenseMod;
	}
	
	public int getHPMod() {
		return hpMod;
	}
	
	public int getSpeedMod() {
		return speedMod;
	}
	
	public int getPrice() {
		return (int) (price *0.7);
	}
	public boolean getEquipped() {
		return equipped;
	}
}
