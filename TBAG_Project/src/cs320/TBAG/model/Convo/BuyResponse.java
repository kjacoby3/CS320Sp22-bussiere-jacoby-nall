package cs320.TBAG.model.Convo;

import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Item;
import cs320.TBAG.model.Weapon;

public class BuyResponse extends ConversationResponse {
	private Item buyItem;
	private int price;
	private Boolean bought;
	private int failedNode;
	
	public BuyResponse() {
		resultNode = 0;
		failedNode = 0;
		buyItem = new Weapon(0,"Weapon", 0, 0, 0, 0, 0, false);
		price = buyItem.getBuyPrice();
		bought = false;
		response = "Buy: " + buyItem.getName() + " for " + price + " coins.";
	}
	
	public BuyResponse(Item buyItem, Boolean bought) {
		this.buyItem = buyItem;
		this.bought = bought;
		price = buyItem.getBuyPrice();
		response = "Buy: " + buyItem.getName() + " for " + price + " coins.";
	}
	
	public void setBuyItem(Item buyItem) {
		this.buyItem = buyItem;
	}
	
	public Item getBuyItem() {
		return buyItem;
	}
	
	public void recalculatePrice() {
		price = buyItem.getBuyPrice();
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setBought(Boolean bought) {
		this.bought = bought;
	}
	
	public Boolean getBought() {
		return bought;
	}
	
	public void setFailedNode(int failedNode) {
		this.failedNode = failedNode;
	}
	
	public int getFailedNode() {
		return failedNode;
	}
	
	public int checkResultNode() {
		int node = resultNode;
		if(bought) {
			node = resultNode;
		} else {
			node = failedNode;
		}
		return node;
	}
}