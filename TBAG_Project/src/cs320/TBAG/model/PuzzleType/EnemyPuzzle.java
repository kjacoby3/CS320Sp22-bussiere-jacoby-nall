package cs320.TBAG.model.PuzzleType;

import cs320.TBAG.model.NPC;

public class EnemyPuzzle extends Puzzle{
	private NPC npc;
	private int enemyPuzzleId;
	private int npcId;
	
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
		if(npc != null) {
			if(npc.isDead()) {
				complete = true;
			} else {
				complete = false;
			}
		} else {
			complete = true;
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
	
	public void setNPCId(int npcId) {
		this.npcId = npcId;
	}
	
	public int getNPCId() {
		return npcId;
	}
}