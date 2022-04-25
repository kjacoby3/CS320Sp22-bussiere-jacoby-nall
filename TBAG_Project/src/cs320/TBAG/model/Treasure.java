package cs320.TBAG.model;

public class Treasure{
	String name;
	String type;
	int price;
	int hpMod;
	int defMod;
	int spdMod;
	int dmgMod;
	
	public Treasure(String name, int price, int hpMod, int defMod, int spdMod, int dmgMod) {
		this.name = name;
		type = "Treasure";
		this.price = price;
		this.hpMod = hpMod;
		this.defMod = defMod;
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