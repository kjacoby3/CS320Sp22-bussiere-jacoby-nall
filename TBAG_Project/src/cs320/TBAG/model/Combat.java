package cs320.TBAG.model;

import cs320.TBAG.model.Actor;
import java.util.HashMap;

public class Combat{
	
	private Actor actor1;
	private Actor actor2;
	private ActorStats actor1Stats;
	private ActorStats actor2Stats;
	private int actor1Turn = 1;
	private int actor2Turn = 2;
	private int turn;
	private int turnCount = 0;
	
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
	
	private boolean actor1Defeated;
	private boolean actor2Defeated;
	
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
		
		actor1Defeated = false;
		actor2Defeated = false;
		
		turn = 1;
		turnCount = 0;
		
	}
	
	public int getTurn() {
		return turn;
	}
	
	//Returns if actor 1 has been defeated or not
	public boolean getActor1Defeated() {
		return actor1Defeated;
	}
	
	//Returns if actor 2 has been defeated or not
	public boolean getActor2Defeated() {
		return actor2Defeated;
	}
	
	//Updates turn and adds to total count of turns made
	public void updateTurn() {
		if (turn == 1) {
			turn = 2;
			turnCount += 1;
		} else if (turn == 2){
			turn = 1;
			turnCount += 1;
		} else {
			turn = turn;
		}
		
	}
	
	public Actor getActor1() {
		return actor1;
	}
	
	public Actor getActor2() {
		return actor2;
	}
	
	// Calculates total damage stat for actor 1
	public double actor1TotalDMG() {
		double statDMG = actor1Stats.getDmg();
		double weapDMG = actor1.getEqWeap().getDamage();
		double totalDMG = weapDMG * ((statDMG / 100) + 1);
		
		
		return totalDMG;
	}
	
	// Calculates total damage stat for actor 2
	public double actor2TotalDMG() {
		double statDMG = actor2Stats.getDmg();
		double weapDMG = actor2.getEqWeap().getDamage();
		double totalDMG = weapDMG * ((statDMG / 100) + 1);
		
		
		return totalDMG;
	}
	
	// Calculates total defense stat for actor 1
	public double actor1TotalDEF() {
		double statDEF = actor1Stats.getDef();
		double equipDEF = actor1.getEquipped().getDefenseMod();
		double totalDEF = equipDEF * ((statDEF / 100) + 1);
		
		return totalDEF;
	}
	
	// Calculates total defense stat for actor 2
	public double actor2TotalDEF() {
		double statDEF = actor2Stats.getDef();
		double equipDEF = actor2.getEquipped().getDefenseMod();
		double totalDEF = equipDEF * ((statDEF / 100) + 1);
		
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
	
	//Calculates probability of actor 1 successfully running
	public double actor1RunChance() {
		double chance;
		double spdDiff = actor1TotalSPD - actor2TotalSPD;
		chance = 100 - (70 * ((spdDiff / 100) + 1));
		
		return chance;
	}
	
	//Calculates probability of actor 2 successfully running
	public double actor2RunChance() {
		double chance;
		double spdDiff = actor2TotalSPD - actor1TotalSPD;
		chance = 100 - (70 * ((spdDiff / 100) + 1));
		
		return chance;
	}
	
	
	//Calculates probability of actor 1 landing an attack
	public double actor1HitChance() {
		double chance;
		double spdDiff = actor1AttackSPD - actor2MoveSPD;
		chance = 100 - (90 * ((spdDiff / 100) + 1));
		
		return chance;
	}
	
	//Calculates probability of actor 2 landing an attack
	public double actor2HitChance() {
		double chance;
		double spdDiff = actor2AttackSPD - actor1MoveSPD;
		chance = 100 - (90 * ((spdDiff / 100) + 1));
		
		return chance;
	}
	
	//Calculates damage done by actor 1
	public double actor1CalcAttackDMG() {
		double dmgDone;
		double z = actor1TotalDMG * (1 - ((actor2TotalDEF) / (3 * actor1TotalDMG)));
		if (z <= 0) {
			return 0;
		}
		double lowerBound = z * 0.9;
		double upperBound = z * 1.1;
		dmgDone = intDiceRoll((int) lowerBound, (int) upperBound);
		return dmgDone;
	}
	
	//Calculates damage done by actor 2
	public double actor2CalcAttackDMG() {
		double dmgDone;
		double z = actor2TotalDMG * (1 - (actor1TotalDEF / (3 * actor2TotalDMG)));
		if (z <= 0) {
			return 0;
		}
		double lowerBound = z * 0.9;
		double upperBound = z * 1.1;
		dmgDone = intDiceRoll((int) lowerBound, (int) upperBound);
		return dmgDone;
	}
	
	// Returns random int value within set min and max
	public int intDiceRoll(int lowerBound, int upperBound) {
		int value = (int) (Math.random() * 100);
		while (value < lowerBound || value > upperBound) {
			if(lowerBound < 100 && upperBound < 100) {
				value = (int) (Math.random() * 100);
			} else if (upperBound >= 100 && upperBound < 1000) {
				value = (int) (Math.random() * 1000);
			} else if (upperBound >= 1000 && upperBound < 10000) {
				value = (int) (Math.random() * 10000);
			} else if (upperBound >= 10000 && upperBound < 100000) {
				value = (int) (Math.random() * 100000);
			} else if (upperBound >= 100000 && upperBound < 1000000) {
				value = (int) (Math.random() * 1000000);
			} else if (upperBound >= 1000000 && upperBound < 10000000) {
				value = (int) (Math.random() * 10000000);
			} else if (upperBound >= 10000000 && upperBound < 100000000) {
				value = (int) (Math.random() * 100000000);
			} else if (upperBound >= 100000000 && upperBound < 1000000000) {
				value = (int) (Math.random() * 1000000000);
			}
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
	
	//Returns if actor 1's attack lands or not
	public boolean actor1HitAttempt() {
		return boolDiceRoll((int) actor1HitChance());
	}
	
	//Returns if actor 2's attack lands or not
	public boolean actor2HitAttempt() {
		return boolDiceRoll((int) actor2HitChance());
	}
	
	//Returns if actor 1's run attempt is successful or not
	public boolean actor1RunAttempt() {
		Boolean run = boolDiceRoll((int) actor1RunChance());
		if (run == true) {
			turn = actor1Turn;
		} else {
			updateTurn();
		}
		return run;
	}
	
	//Returns if actor 2's run attempt is successful or not
	public boolean actor2RunAttempt() {
		Boolean run = boolDiceRoll((int) actor2RunChance());
		if (run == true) {
			turn = actor2Turn;
		} else {
			updateTurn();
		}
		return run;
	}
	
	//Returns result string of actor 1's attack
	public String actor1Attack() {
		String result;
		int dmg;
		if(turn == 1) {
			if(actor1HitAttempt()) {
				dmg = (int)actor1CalcAttackDMG();
				if(dmg <= 0) {
					result = actor2.getName() + " was too strong and your attack did 0 damage.";
				} else {
					result = "You attacked " + actor2.getName() + " for " + dmg + " damage.";
					actor2Stats.subtractHP(dmg);
					if(actor2Stats.getCurHP() <= 0) {
						actor2Defeated = true;
					}
				}
			} else {
				result = "You missed your attack.";
			}
		} else if(turn == 2){
			result = "It is not your turn.";
		} else {
			result = "The combat is over, please go back.";
		}
		
		updateTurn();
		return result;
	}
	
	public String actor2Attack() {
		String result;
		int dmg;
		if(turn == 2) {
			if(actor2HitAttempt()) {
				dmg = (int)actor2CalcAttackDMG();
				if(dmg <= 0) {
					result = "You were too strong and " + actor2.getName() + "'s attack did 0 damage.";
					updateTurn();
				} else {
					result = actor2.getName() + " attacked you for " + dmg + " damage.";
					actor1Stats.subtractHP(dmg);
					updateTurn();
					if(actor1Stats.getCurHP() <= 0) {
						actor1Defeated = true;
					}
				}
			} else {
				result = actor2.getName() + " missed their attack.";
				updateTurn();
			}
		} else if(turn == 1){
			result = "It is not your turn.";
		} else {
			result = "The combat is over, please go back";
		}
		
		return result;
	}
	
	//Returns result string of actor 1 defeating actor 2
	public String actor1DefeatsActor2() {
		String result;
		actor2Defeated = true;
		actor2Stats.setCurHP(0);
		Inventory actor1Inv = actor1.getInventory();
		Inventory actor2Inv = actor2.getInventory();
		Inventory roomInv = actor1.getLocation().getRoomInv();
		actor1Stats.addExp(actor2Stats.getCurExp());
		result = "Congrats, you have defeated " + actor2.getName() +
				"and have earned " + actor2Stats.getCurExp() + " XP!";
		
		//actor2.getLocation().removeNPCInRoom((NPC) actor2);
		
		//Add dropped equipment to actor 1 inventory
		for(Equipment i : actor2Inv.getEquipment().values()) {
			if(!actor1.getInventory().checkFull("Equipment")) {
				actor1Inv.addItem(i);
				actor2DropMessage(i, false);
			} else {
				roomInv.addItem(i);
				actor2DropMessage(i, true);
			}
		}
		
		//Add dropped weapons to actor 1 inventory
		for(Weapon i : actor2Inv.getWeapons().values()) {
			if(!actor1.getInventory().checkFull("Weapons")) {
				actor1Inv.addItem(i);
				actor2DropMessage(i, false);
			} else {
				roomInv.addItem(i);
				actor2DropMessage(i, true);
			}
		}
		
		//Add dropped trophies to actor 1 inventory
		for(Trophy i : actor2Inv.getTrophies().values()) {
			if(!actor1.getInventory().checkFull("Trophies")) {
				actor1Inv.addItem(i);
				actor2DropMessage(i, false);
			} else {
				roomInv.addItem(i);
				actor2DropMessage(i, true);
			}
		}
		
		//Add dropped usables to actor 1 inventory (without message)
		for(Usable i : actor2Inv.getUsables().values()) {
			if(!actor1.getInventory().checkFull("Usables")) {
				actor1Inv.addItem(i);
				//actor2DropMessage(i, false);
			} else {
				roomInv.addItem(i);
				//actor2DropMessage(i, true);
			}
		}
		turn = 0;
		return result;
	}
	
	
	public String actor2DefeatsActor1() {
		String result;
		result = actor2.getName() + " killed you.";
		turn = 0;
		return result;
	}
	
	//Returns result string of actor 2 dropping an item on death
	public String actor2DropMessage(Item item, boolean fullInventory) {
		String result;
		if(!fullInventory) {
			result = actor2.getName() + " dropped " + item;
		} else {
			result = "You cannot carry anymore " + item.getType() +
					", but" + actor2.getName() + " dropped " + item +
					" on the ground";
		}
		return result;
	}
	
	public String player1UsesItem(Item item) {
		String result = null;
		
		return result;
	}
	
}

