package cs320.TBAG.model.Convo;

import cs320.TBAG.model.Item;
import cs320.TBAG.model.Weapon;

public class SellResponse extends ConversationResponse {
	private int sellResponseId;
	private Item sellItem;
	private int price;
	private Boolean sold;
	private int failedNode;
	private int sellItemId;
	
	public SellResponse() {
		resultNode = 0;
		failedNode = 0;
		sellItem = new Weapon(0,"Weapon", 0, 0, 0, 0, 0, false);
		price = sellItem.getSellPrice();
		sold = false;
		response = "Sell: " + sellItem.getName() + " for " + price + " coins.";
	}
	
	public SellResponse(Item sellItem, Boolean sold) {
		this.sellItem = sellItem;
		this.sold = sold;
		price = sellItem.getSellPrice();
		response = "Sell: " + sellItem.getName() + " for " + price + " coins.";
	}
	
	public void setSellItem(Item sellItem) {
		this.sellItem = sellItem;
	}
	
	public Item getSellItem() {
		return sellItem;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setSold(Boolean sold) {
		this.sold = sold;
	}
	
	public Boolean getSold() {
		return sold;
	}
	
	public void setFailedNode(int failedNode) {
		this.failedNode = failedNode;
	}
	
	public int getFailedNode() {
		return failedNode;
	}
	
	public int checkResultNode() {
		int node = resultNode;
		if(sold) {
			node = resultNode;
		} else {
			node = failedNode;
		}
		return node;
	}
	
	public void setSellResponseId(int sellResponseId) {
		this.sellResponseId = sellResponseId;
	}
	
	public int getSellResponseId() {
		return sellResponseId;
	}
	
	public void setSellItemId(int sellItemId) {
		this.sellItemId = sellItemId;
	}
	
	public int getSellItemId() {
		return sellItemId;
	}
}