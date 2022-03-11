package cs320.TBAG.model;

public class Treasures{
	String name;
	String type;
	int hpMod;
	int defMod;
	int spdMod;
	int dmgMod;
	
	public Treasures(String name, int hpMod, int defMod, int spdMod, int dmgMod) {
		this.name = name;
		type = "Treasures";
		this.hpMod = hpMod;
		this.defMod = hpMod;
		this.spdMod = spdMod;
		this.dmgMod = dmgMod;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public int getHPMod() {
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
	
}