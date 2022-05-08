package cs320.TBAG.model.Convo;

import cs320.TBAG.model.Item;
import cs320.TBAG.model.Weapon;

public class SellResponse extends ConversationResponse {
	private Item sellItem;
	private int price;
	private Boolean sold;
	private int failedNode;
	
	public SellResponse() {
		resultNode = 0;
		failedNode = 0;
		sellItem = new Weapon("Weapon", 0, 0, 0, 0, 0, false);
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
}