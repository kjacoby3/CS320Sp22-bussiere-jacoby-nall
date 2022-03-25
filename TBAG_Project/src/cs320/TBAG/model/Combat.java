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
	private int turn = 1;
	private boolean inCombat;
	
	public Combat(Actor actor1, Actor actor2) {
		this.actor1 = actor1;
		this.actor2 = actor2;
		actor1Stats = actor1.getActorStats();
		actor2Stats = actor2.getActorStats();
		if (actor1.getLocation() == actor2.getLocation()) {
			inCombat = true;
		}
		
	}
	
	public int actor1TotalDMG() {
		int statDMG = actor1Stats.getDmg();
		int weapDMG = 10;
		int totalDMG = statDMG * ((weapDMG / 100) + 1);
		
		
		return totalDMG;
	}
	
	public int actor2TotalDMG() {
		int statDMG = actor1Stats.getDmg();
		int weapDMG = 10;
		int totalDMG = statDMG * ((weapDMG / 100) + 1);
		
		
		return totalDMG;
	}
	
	public int intDiceRoll(int lowerBound, int upperBound) {
		int value = (int) (Math.random() * 100);
		while (value < lowerBound || value > upperBound) {
			value = (int) (Math.random() * 100);
		}
		return value;
	}
	
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

