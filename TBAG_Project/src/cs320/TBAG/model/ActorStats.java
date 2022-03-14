package cs320.TBAG.model;

public class ActorStats {
	private int curHP;
	private int maxHP;
	private int dmg;
	private int def;
	private int spd;
	private int curExp;
	private int maxExp;
	private int curLvl;
	
	// Default stats for a player
	public ActorStats() {
		maxHP = 100;
		curHP = maxHP;
		dmg = 10;
		def = 10;
		spd = 10;
		curExp = 0;
		maxExp = 100;
		curLvl = 1;
	}
	
	// Default stats for an NPC
	public ActorStats(int maxHP, int dmg, int def, int spd) {
		this.maxHP = maxHP;
		curHP = maxHP;
		this.dmg = dmg;
		this.def = def;
		this.spd = spd;
		curExp = 0;
		maxExp = 0;
		curLvl = 0;
	}
	
	
	public ActorStats(int curHP, int maxHP, int dmg, int def, int spd, 
			int curExp, int maxExp, int curLvl) {
		this.curHP = curHP;
		this.maxHP = maxHP;
		this.dmg = dmg;
		this.def = def;
		this.spd = spd;
		this.curExp = curExp;
		this.maxExp = maxExp;
		this.curLvl = curLvl;
		
	}
	
	public void setCurHP(int curHP) {
		this.curHP = curHP;
	}
	
	public int getCurHP() {
		return curHP;
	}
	
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public void setDmg(int dmg) {
		this.dmg = dmg;
	}
	
	public int getDmg() {
		return dmg;
	}
	
	public void setDef(int def) {
		this.def = def;
	}
	
	public int getDef() {
		return def;
	}
	
	public void setSpd(int spd) {
		this.spd = spd;
	}
	
	public int getSpd() {
		return spd;
	}
	
	public void setCurExp(int curExp) {
		this.curExp = curExp;
	}
	
	public int getCurExp() {
		return curExp;
	}
	
	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
	}
	
	public int getMaxExp() {
		return maxExp;
	}
	
	public void setCurLvl(int curLvl) {
		this.curLvl = curLvl;
	}
	
	public int getCurLvl() {
		return curLvl;
	}
}