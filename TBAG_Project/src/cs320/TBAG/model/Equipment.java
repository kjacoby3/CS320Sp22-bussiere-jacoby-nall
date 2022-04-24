package cs320.TBAG.model;

public class Equipment extends Item{
	int defenseMod;
	int hpMod;
	int speedMod;
	
	public Equipment(String name, int defenseMod, int hpMod, int speedMod) {
		this.name = name;
		this.type = "Equipment";
		this.defenseMod = defenseMod;
		this.hpMod = hpMod;
		this.speedMod = speedMod;
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
}
