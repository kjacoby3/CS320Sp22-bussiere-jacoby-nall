package cs320.TBAG.model;

public class Equipment extends Items{
	int defenseMod;
	int durability;
	int hpMod;
	int speedMod;
	
	public Equipment(String name, int durability, int defenseMod, int hpMod, int speedMod) {
		this.name = name;
		this.type = "Equipment";
		this.durability = durability;
		this.defenseMod = defenseMod;
		this.hpMod = hpMod;
		this.speedMod = speedMod;
	}
	
	public int getDefenseMod() {
		return defenseMod;
	}
	
	public int getDurability() {
		return durability;
	}
	
	public int getHPMod() {
		return hpMod;
	}
	
	public int getSpeedMod() {
		return speedMod;
	}
}
