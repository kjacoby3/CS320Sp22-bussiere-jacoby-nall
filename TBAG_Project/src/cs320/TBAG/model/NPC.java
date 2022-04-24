package cs320.TBAG.model;

import cs320.TBAG.model.Actions;
import cs320.TBAG.model.Convo.ConversationTree;

public class NPC extends Actor{
	
	//private Actions action = new Actions();
	NPCAggression aggression;
	ConversationTree conversationTree;
	
	public NPC() {
		name = "NPC 1";
		type = "npc";
		location = new Room();
		inventory = new Inventory(100);
		actorStats = new ActorStats(100, 10, 10, 10);
		eqWeap = new Weapon("Fists", 10, 10);
		equipped = new Equipment("Bare", 10, 10, 0);
		aggression = NPCAggression.Neutral;
		currency = 0;
		conversationTree = new ConversationTree();
	}
	
	public NPC(String name, Room location, Inventory inventory,
			ActorStats actorStats, String type, Weapon eqWeap, 
			Equipment equipped, NPCAggression aggression,int currency, ConversationTree conversationTree) {
		this.name = name;
		this.type = type;
		this.location = location;
		this.actorStats = actorStats;
		this.eqWeap = eqWeap;
		this.equipped = equipped;
		this.aggression = aggression;
		this.currency = currency;
		this.conversationTree = conversationTree;
	}
	
	public NPCAggression getAggression() {
		return aggression;
	}
	
	public void setAggression(NPCAggression aggression) {
		this.aggression = aggression;
	}
	
	public ConversationTree getConversationTree() {
		return conversationTree;
	}
	
	public void setConversationTree(ConversationTree conversationTree) {
		this.conversationTree = conversationTree;
	}

}