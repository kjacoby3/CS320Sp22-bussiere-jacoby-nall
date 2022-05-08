package cs320.TBAG.model.PuzzleType;

import cs320.TBAG.model.NPC;

public class EnemyPuzzle extends Puzzle{
	NPC npc;
	int enemyPuzzleId;
	
	public EnemyPuzzle() {
		complete = false;
		completeMSG = "Blank";
		currencyReward = 0;
		expReward = 0;
		rewardItem = null;
	}
	
	
	@Override
	public String checkConditions() {
		String result;
		if(npc.isDead()) {
			complete = true;
		} else {
			complete = false;
		}
		
		
		if(complete == true) {
			result = "Already completed";
		} else {
			result = completeMSG;
		}
		return result;
	}
	
	public void setNPC(NPC npc) {
		this.npc = npc;
	}
	
	public NPC getNPC() {
		return npc;
	}
	
	public void setEnemyPuzzleId(int enemyPuzzleId) {
		this.enemyPuzzleId = enemyPuzzleId;
	}
	
	public int getEnemyPuzzleId() {
		return enemyPuzzleId;
	}
}