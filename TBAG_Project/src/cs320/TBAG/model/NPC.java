package cs320.TBAG.model;

import cs320.TBAG.model.Actions;
import cs320.TBAG.model.Convo.ConversationTree;
import cs320.TBAG.model.InteractableObj.Interactable;

public class NPC extends Actor implements ActionsInterface{
	
	//private Actions action = new Actions();
	int aggression;
	ConversationTree conversationTree;
	int NPCId;
	int conversationTreeId;
	
	public NPC() {
		name = "NPC 1";
		type = "npc";
		location = new Room();
		inventory = new Inventory(100);
		actorStats = new ActorStats(100, 10, 10, 10);
		eqWeap = new Weapon(0,"Fists", 10, 10,0,0,0,true);
		equipped = new Equipment(0,"Bare",0, 10, 10, 0,0,0,0,true);
		aggression = 0;
		currency = 0;
		conversationTree = new ConversationTree();
	}
	
	public NPC(String name, Room location, Inventory inventory,
			ActorStats actorStats, String type, Weapon eqWeap, 
			Equipment equipped, int aggression,int currency, ConversationTree conversationTree) {
		this.name = name;
		this.type = type;
		this.inventory = inventory;
		this.location = location;
		this.actorStats = actorStats;
		this.eqWeap = eqWeap;
		this.equipped = equipped;
		this.aggression = aggression;
		this.currency = currency;
		this.conversationTree = conversationTree;
	}
	
	public int getAggression() {
		return aggression;
	}
	
	public void setAggression(int aggression) {
		this.aggression = aggression;
	}
	
	public ConversationTree getConversationTree() {
		return conversationTree;
	}
	
	public void setConversationTree(ConversationTree conversationTree) {
		this.conversationTree = conversationTree;
	}
	
	public void setNPCId(int NPCId) {
		this.NPCId = NPCId;
	}
	
	public int getNPCId() {
		return NPCId;
	}
	
	public void setConversationTreeId(int conversationTreeId) {
		this.conversationTreeId = conversationTreeId;
	}
	
	public int getConversationTreeId() {
		return conversationTreeId;
	}
	
	@Override
	public void equipWeapon(Weapon weapon) {
		Weapon curWeapon = getEqWeap();
		if(inventory.getWeapons().containsValue(weapon)) {
			setEqWeap(weapon);
			if(!(curWeapon.getName() == "Fists")) {
				//if(!(inventory.addItem(curWeapon))){
				inventory.addItem(curWeapon);
				inventory.removeItem(weapon);
				//}
			}
		}
		
	}
	
	@Override
	public void unequipWeapon() {
		Weapon curWeapon = getEqWeap();
		inventory.addItem(curWeapon);
		setEqWeap(new Weapon(0,"Fists", 10, 100, 0, 0, 0, true));
		
	}
	
	@Override
	public void equipEquipment(Equipment equipment) {
		Equipment curEquipment = getEquipped();
		if(inventory.getEquipment().containsValue(equipment)) {
			setEquipped(equipment);
			if(!(curEquipment.getName() == "Bare")) {
				inventory.addItem(curEquipment);
				inventory.removeItem(equipment);
			}
		}
	}
	
	@Override
	public void unequipEquipment() {
		Equipment curEquipment = getEquipped();
		inventory.addItem(curEquipment);
		setEquipped(new Equipment(0,"Bare",0, 100, 10, 0, 0, 0, 0, true));
	}

	@Override
	public void move(int direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickUp(Item item) {
		inventory.addItem(item);
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void talk(NPC npc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean buy(NPC npc, Item item) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Boolean sell(NPC npc, Item item) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String use(Item item) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public String activateObj(String activationStr, Interactable obj) {
		// TODO Auto-generated method stub
		return null;
	}

}