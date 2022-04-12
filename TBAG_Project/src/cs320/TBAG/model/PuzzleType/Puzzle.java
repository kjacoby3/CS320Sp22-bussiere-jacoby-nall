package cs320.TBAG.model.PuzzleType;

import cs320.TBAG.model.Item;

public abstract class Puzzle {
	Boolean complete;
	String hint;
	String completeMSG;
	int currencyReward;
	int expReward;
	Item rewardItem;
	
	public abstract String checkConditions();
	
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	
	public Boolean getComplete() {
		return complete;
	}
	
	public String getHint() {
		return hint;
	}
	
	public void setHint(String hint) {
		this.hint = hint;
	}
	
	public String getCompleteMSG() {
		return completeMSG;
	}
	
	public void setCompleteMSG(String completeMSG) {
		this.completeMSG = completeMSG;
	}
	
	public int getCurrencyReward() {
		return currencyReward;
	}
	
	public void setCurrencyReward(int currencyReward) {
		this.currencyReward = currencyReward;
	}
	
	public int getExpReward() {
		return expReward;
	}
	
	public void setExpReward(int expReward) {
		this.expReward = expReward;
	}
	
	public Item getRewardItem() {
		return rewardItem;
	}
	
	public void setRewardItem(Item rewardItem) {
		this.rewardItem = rewardItem;
	}
}