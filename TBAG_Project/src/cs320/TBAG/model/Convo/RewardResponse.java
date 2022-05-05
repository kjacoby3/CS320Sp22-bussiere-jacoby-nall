package cs320.TBAG.model.Convo;

import cs320.TBAG.model.Item;

public class RewardResponse extends ConversationResponse {
	private Item rewardItem;
	private int rewardCurrency;
	private int rewardExp;
	private Boolean collected;
	
	
	public RewardResponse() {
		resultNode = 0;
		response = "Thank you.";
		rewardItem = null;
		rewardCurrency = 0;
		rewardExp = 0;
		collected = false;
	}
	
	public RewardResponse(String response) {
		resultNode = 0;
		this.response = response;
	}
	
	public void setRewardItem(Item rewardItem) {
		this.rewardItem = rewardItem;
	}
	
	public Item getRewardItem() {
		return rewardItem;
	}
	
	public void setRewardCurrency(int rewardCurrency) {
		this.rewardCurrency = rewardCurrency;
	}
	
	public int getRewardCurrency() {
		return rewardCurrency;
	}
	
	public void setRewardExp(int rewardExp) {
		this.rewardExp = rewardExp;
	}
	
	public int getRewardExp() {
		return rewardExp;
	}
	
	public Boolean collectReward() {
		if(collected == false) {
			collected = true;
			return false;
		} else {
			return collected;
		}
		
	}
	
}