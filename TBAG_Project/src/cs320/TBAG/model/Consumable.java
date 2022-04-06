package cs320.TBAG.model;

public class Consumable extends Item{
	private int curHPMod;
	private int maxHPMod;
	private int dmgMod;
	private int defMod;
	private int spdMod;
	
	public Consumable(String name, int price, int curHPMod, int maxHPMod, int dmgMod, int defMod, int spdMod) {
		this.name = name;
		this.price = price;
		this.type = "Consumable";
		this.curHPMod=curHPMod;
		this.maxHPMod=maxHPMod;
		this.dmgMod=dmgMod;
		this.defMod = spdMod;
		this.spdMod = spdMod;
	}
	
	public int getCurHPMod() {
		return curHPMod;
	}
	
	public int getMaxHPMod() {
		return maxHPMod;
	}
	
	public int getdmgMod() {
		return dmgMod;
	}
	
	public int getdefMod() {
		return defMod;
	}
	
	public int getspdMod() {
		return spdMod;
	}
	
	public void setCurHPMod(int curHPMod) {
		this.curHPMod=curHPMod;
	}
	public void setMaxHPMod(int maxHPMod) {
		this.maxHPMod=maxHPMod;
	}
	public void setdmgMod(int dmgMod) {
		this.dmgMod=dmgMod;
	}
	public void setspdMod(int spdMod) {
		this.spdMod=spdMod;
	}
	public void setdefMod(int defMod) {
		this.defMod = defMod;
	}
	
}
