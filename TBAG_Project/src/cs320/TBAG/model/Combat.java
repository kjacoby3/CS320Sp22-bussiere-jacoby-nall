//package cs320.TBAG.model;
//
//import cs320.TBAG.model.Actor;
//
//public class Combat{
//	
//	private Actor actor1;
//	private Actor actor2;
//	private ActorStats actor1Stats;
//	private ActorStats actor2Stats;
//	private int turn = 1;
//	
//	public Combat(Actor actor1, Actor actor2) {
//		this.actor1 = actor1;
//		this.actor2 = actor2;
//		actor1Stats = actor1.getActorStats();
//		actor2Stats = actor2.getActorStats();
//		
//	}
//	
//	public int attackActor() {
//		int dmgDone = 0;
//		
//		
//		return dmgDone;
//	}
//	
//	
//	public int intDiceRoll(int lowerBound, int upperBound) {
//		int value = (int) (Math.random() * 100);
//		while (value < lowerBound || value > upperBound) {
//			value = (int) (Math.random() * 100);
//		}
//		return value;
//	}
//	
//	public boolean boolDiceRoll(int threshold) {
//		int value = (int) (Math.random() * 100);
//		if (value < threshold) {
//			return false;
//		}
//		else {
//			return true;
//		}
//	}
//}

package cs320.TBAG.model;

import cs320.TBAG.model.Actor;

public class Combat{
	
	private Actor actor1;
	private Actor actor2;
	private ActorStats actor1Stats;
	private ActorStats actor2Stats;
	private int actor1Turn = 1;
	private int actor2Turn = 2;
	private int turn;
	
	private int actor1TotalDMG;
	private int actor2TotalDMG;
	
	private int actor1TotalDEF;
	private int actor2TotalDEF;
	
	private int actor1MoveSPD;
	private int actor2MoveSPD;
	
	private int actor1AttackSPD;
	private int actor2AttackSPD;
	
	private int actor1TotalSPD;
	private int actor2TotalSPD;
	//private boolean inCombat;
	
	public Combat(Actor actor1, Actor actor2) {
		this.actor1 = actor1;
		this.actor2 = actor2;
		actor1Stats = actor1.getActorStats();
		actor2Stats = actor2.getActorStats();
		//if (actor1.getLocation() == actor2.getLocation()) {
		//	inCombat = true;
		//}
		
		// Calculate base stats for combat
		actor1TotalDMG = (int)actor1TotalDMG();
		actor2TotalDMG = (int)actor2TotalDMG();
		
		actor1TotalDEF = (int)actor1TotalDEF();
		actor2TotalDEF = (int)actor2TotalDEF();
		
		actor1MoveSPD = (int)actor1MoveSPD();
		actor2MoveSPD = (int)actor2MoveSPD();
		
		actor1AttackSPD = (int)actor1AttackSPD();
		actor2AttackSPD = (int)actor2AttackSPD();
		
		actor1TotalSPD = (int)actor1TotalSPD();
		actor2TotalSPD = (int)actor2TotalSPD();
	}
	
	// Calculates total damage stat for actor 1
	public double actor1TotalDMG() {
		double statDMG = actor1Stats.getDmg();
		double weapDMG = actor1.getEqWeap().getDamage();
		double totalDMG = statDMG * ((weapDMG / 100) + 1);
		
		
		return totalDMG;
	}
	
	// Calculates total damage stat for actor 2
	public double actor2TotalDMG() {
		double statDMG = actor2Stats.getDmg();
		double weapDMG = actor2.getEqWeap().getDamage();
		double totalDMG = statDMG * ((weapDMG / 100) + 1);
		
		
		return totalDMG;
	}
	
	// Calculates total defense stat for actor 1
	public double actor1TotalDEF() {
		double statDEF = actor1Stats.getDef();
		double equipDEF = actor1.getEquipped().getDefenseMod();
		double totalDEF = statDEF * ((equipDEF / 100) + 1);
		
		return totalDEF;
	}
	
	// Calculates total defense stat for actor 2
	public double actor2TotalDEF() {
		double statDEF = actor2Stats.getDef();
		double equipDEF = actor2.getEquipped().getDefenseMod();
		double totalDEF = statDEF * ((equipDEF / 100) + 1);
		
		return totalDEF;
	}
	
	// Calculates movement speed stat for actor 1
	public double actor1MoveSPD() {
		double statSPD = actor1Stats.getSpd();
		double equipSPD = actor1.getEquipped().getSpeedMod();
		double moveSPD = statSPD * ((equipSPD / 100) + 1);
		
		return moveSPD;
	}
	
	// Calculates movement speed stat for actor 2
	public double actor2MoveSPD() {
		double statSPD = actor2Stats.getSpd();
		double equipSPD = actor2.getEquipped().getSpeedMod();
		double moveSPD = statSPD * ((equipSPD / 100) + 1);
		
		return moveSPD;
	}
	
	// Calculates attack speed stat for actor 1
	public double actor1AttackSPD() {
		double statSPD = actor1Stats.getSpd();
		double weapSPD = actor1.getEqWeap().getDamage(); /* change to getSpd() */
		double atckSPD = statSPD * ((weapSPD / 100) + 1);
		
		return atckSPD;
	}
	
	// Calculates attack speed stat for actor 2
	public double actor2AttackSPD() {
		double statSPD = actor2Stats.getSpd();
		double weapSPD = actor2.getEqWeap().getDamage(); /* change to getSpd() */
		double atckSPD = statSPD * ((weapSPD / 100) + 1);
		
		return atckSPD;
	}

	// Calculates total speed for actor 1 to determine starting turn
	public double actor1TotalSPD() {
		double statSPD = actor1Stats.getSpd();
		double equipSPD = actor1.getEquipped().getSpeedMod();
		double weapSPD = actor1.getEqWeap().getDamage(); /* change to getSpd() */
		
		double totalSPD = statSPD * ((weapSPD / 100) + (equipSPD / 100) + 1);
		
		return totalSPD;
	}
	
	// Calculates total speed for actor 2 to determine starting turn
	public double actor2TotalSPD() {
		double statSPD = actor2Stats.getSpd();
		double equipSPD = actor2.getEquipped().getSpeedMod();
		double weapSPD = actor2.getEqWeap().getDamage(); /* change to getSpd() */
		
		double totalSPD = statSPD * ((weapSPD / 100) + (equipSPD / 100) + 1);
		
		return totalSPD;
	}
	
	
	public double actor1RunChance() {
		double chance;
		double spdDiff = actor1TotalSPD - actor2TotalSPD;
		chance = 100 - (70 * ((spdDiff / 100) + 1));
		
		return chance;
	}
	
	public double actor2RunChance() {
		double chance;
		double spdDiff = actor2TotalSPD - actor1TotalSPD;
		chance = 100 - (70 * ((spdDiff / 100) + 1));
		
		return chance;
	}
	
	public double actor1CalcAttackDMG() {
		double dmgDone;
		double z = actor1TotalDMG * (1 - ((2 * actor2TotalDEF) / (3 * actor1TotalDMG)));
		double lowerBound = z * 0.9;
		double upperBound = z * 1.1;
		dmgDone = intDiceRoll((int) lowerBound, (int) upperBound);
		return dmgDone;
	}
	
	public double actor2CalcAttackDMG() {
		double dmgDone;
		double z = actor2TotalDMG * (1 - ((2 * actor1TotalDEF) / (3 * actor2TotalDMG)));
		double lowerBound = z * 0.9;
		double upperBound = z * 1.1;
		dmgDone = intDiceRoll((int) lowerBound, (int) upperBound);
		return dmgDone;
	}
	
	// Returns random int value within set min and max
	public int intDiceRoll(int lowerBound, int upperBound) {
		int value = (int) (Math.random() * 100);
		while (value < lowerBound || value > upperBound) {
			value = (int) (Math.random() * 100);
		}
		return value;
	}
	
	// Returns true or false based on given chances of event occurring
	public boolean boolDiceRoll(int threshold) {
		int value = (int) (Math.random() * 100);
		if (value < threshold) {
			return false;
		}
		else {
			return true;
		}
	}
}

