package cs320.TBAG.model;

import cs320.TBAG.model.Player;
import cs320.TBAG.model.Actor;

public class LevelUp{
	Player player;
	ActorStats stats;
	int curLvl;
	int maxHP;
	int curHP;
	int dmg;
	int def;
	int spd;
	int curExp;
	int maxExp;
	
	int projLvl;
	int projMaxHP;
	int projCurHP;
	int projDMG;
	int projDEF;
	int projSPD;
	
	
	public LevelUp(Player player) {
		this.player = player;
		stats = player.getActorStats();
		curLvl = stats.getCurLvl();
		maxHP = stats.getMaxHP();
		curHP = stats.getCurHP();
		dmg = stats.getDmg();
		def = stats.getDef();
		spd = stats.getSpd();
		curExp = stats.getCurExp();
		maxExp = stats.getMaxExp();
		
		projLvl = curLvl + 1;
		projMaxHP = maxHP + 25;
		projCurHP = curHP + 25;
		projDMG = dmg + 10;
		projDEF = def + 10;
		projSPD = spd + 10;
	}
	
	public int getProjMaxHP() {
		return projMaxHP;
	}
	
	public int getProjCurHP() {
		return projCurHP;
	}
	
	public int getProjDMG() {
		return projDMG;
	}
	
	public int getProjDEF() {
		return projDEF;
	}
	
	public int getProjSPD() {
		return projSPD;
	}
	
	//Updates stats for multiple level ups at a time
	public void updateStats() {
		stats = player.getActorStats();
		curLvl = stats.getCurLvl();
		maxHP = stats.getMaxHP();
		curHP = stats.getCurHP();
		dmg = stats.getDmg();
		def = stats.getDef();
		spd = stats.getSpd();
		curExp = stats.getCurExp();
		maxExp = stats.getMaxExp();
		
		projLvl = curLvl + 1;
		projMaxHP = maxHP + 25;
		projCurHP = curHP + 25;
		projDMG = dmg + 10;
		projDEF = def + 10;
		projSPD = spd + 10;
	}
	
	public int calcNewMaxExp() {
		int newMaxExp;
		newMaxExp = 100 * (int) Math.pow(2, curLvl);
		return newMaxExp;
	}
	
	public String useLevelUp(String stat) {
		String result;
		
		if(stats.checkLevelUp()) {
			if(stat == "Health" || stat == "HP") {
				result = "Your Health increased from " + maxHP + " to " + projMaxHP + ".";
				stats.setCurExp(curExp - maxExp);
				stats.setMaxHP(projMaxHP);
				stats.setCurHP(projCurHP);
				stats.setMaxExp(calcNewMaxExp());
				stats.setCurLvl(projLvl);
				updateStats();
			} else if(stat == "Damage" || stat == "DMG" || stat == "dmg" || stat == "Dmg") {
				result = "Your Damage increased from " + dmg + " to " + projDMG + ".";
				stats.setCurExp(curExp - maxExp);
				stats.setDmg(projDMG);
				stats.setMaxExp(calcNewMaxExp());
				stats.setCurLvl(projLvl);
				updateStats();
			} else if(stat == "Defense" || stat == "DEF" || stat == "def" || stat == "Def") {
				result = "Your Defense increased from " + def + " to " + projDEF + ".";
				stats.setCurExp(curExp - maxExp);
				stats.setDef(projDEF);
				stats.setMaxExp(calcNewMaxExp());
				stats.setCurLvl(projLvl);
				updateStats();
			} else if(stat == "Speed" || stat == "SPD" || stat == "spd" || stat == "Spd") {
				result = "Your Speed increased from " + spd + " to " + projSPD + ".";
				stats.setCurExp(curExp - maxExp);
				stats.setSpd(projSPD);
				stats.setMaxExp(calcNewMaxExp());
				stats.setCurLvl(projLvl);
				updateStats();
			} else {
				result = "That is not a valid stat.";
			}
		} else {
			result = "You do not have enough XP to level up.";
		}
		
		return result;
	}
}