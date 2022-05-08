package cs320.TBAG.model;

import java.util.Iterator;

import cs320.TBAG.model.Actions;
import cs320.TBAG.model.Convo.Conversation;
import cs320.TBAG.model.InteractableObj.Interactable;
import cs320.TBAG.model.InteractableObj.Keypad;
import cs320.TBAG.model.PuzzleType.KeyPuzzle;
import cs320.TBAG.model.PuzzleType.Puzzle;

public class Player extends Actor implements ActionsInterface{
	
	//private Actions action = new Actions();
	private int prevRoomID;
	private int playerId;
	//private int roomID;
	
	public Player() {
		name = "Player 1";
		type = "player";
		location = new Room();
		inventory = new Inventory(100);
		actorStats = new ActorStats();
		inventory.addItem(new Equipment("Cloth Armor", 10, 50, 50, 50,1,0,0,true));
		eqWeap = new Weapon("Fists", 10, 10, 0, 0, 0,true);
		equipped = new Equipment("Bare",0, 10, 10, 0,1,0,0,true);
		roomId = 1;
	}
	
	public Player(String name, Room location, Inventory inventory, ActorStats actorStats,
			Weapon eqWeap, Equipment equipped) {
		type = "player";
		this.inventory = inventory;
		this.actorStats = actorStats;
		this.eqWeap = eqWeap;
		this.equipped = equipped;
		currency = 0;
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public int getPlayerId() {
		return playerId;
	}

	public void setPrevRoomId(int prevRoomID) {
		this.prevRoomID = prevRoomID;
	}
	
	public int getPrevRoomId() {
		return prevRoomID;
	}
	
//	//Actions from Actions interface	
//	public void movePlayer(String direction) {
//		action.move(direction);
//	}
//	
//	public void pickUpItem(Item item) {
//		action.pickUp(item);
//	}
//	
//	public void attack() {
//		action.attack();
//	}
//	
//	public void equipEquipment(Equipment equipment) {
//		
//	}
//	
//	public void unequipEquipment(Equipment equipment) {
//		
//	}
	
	//Actions from Actions Interface
	//@Override
	//public void move(int newRoom) {
		//Map tempMap = new Map();
		//boolean verify = tempMap.checkMove(tempMap.getCurrRoom(), direction);
		//if(verify = true) {
		//	int newID = tempMap.getNewRoomID();
		//	tempMap.setNewRoom(newID);
		//	System.out.println(tempMap.getRoomDescription(tempMap.getCurrRoom()));
		//}
		//else {
		//	System.out.println("There's no way to go that direction");
		//}
		
		//boolean verify = Map.checkMove(direction);
		//prevRoomID = roomID;
		//roomID = newRoom;
				
		
	//}
	@Override
	public void pickUp(Item item) {
		inventory.addItem(item);
	}
	
	@Override
	public void attack() {
		System.out.println("You successfully attack and defeat the target");
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
			} else {
				inventory.removeItem(weapon);
			}
		}
		
	}
	
	@Override
	public void unequipWeapon() {
		Weapon curWeapon = getEqWeap();
		inventory.addItem(curWeapon);
		setEqWeap(new Weapon("Fists", 10, 100, 0, 0, 0, true));
		
	}
	
	@Override
	public void equipEquipment(Equipment equipment) {
		Equipment curEquipment = getEquipped();
		if(inventory.getEquipment().containsValue(equipment)) {
			setEquipped(equipment);
			if(!(curEquipment.getName() == "Bare")) {
				inventory.addItem(curEquipment);
				inventory.removeItem(equipment);
			} else {
				inventory.removeItem(equipment);
			}
		}
	}
	
	@Override
	public void unequipEquipment() {
		Equipment curEquipment = getEquipped();
		inventory.addItem(curEquipment);
		setEquipped(new Equipment("Bare",0, 100, 10, 0, 0, 0, 0, true));
	}

	@Override
	public void talk(NPC npc) {
		if(npc.getLocation() == location) {
			if(npc.getAggression() > 0) {
				//return true;
			} else {
				//return false;
			}
		} else {
			System.out.println("There is no one to talk to");
			//return false;
		}
	}

	@Override
	public Boolean buy(NPC npc, Item item) {
		// TODO Auto-generated method stub
		Boolean bought = false;
		Inventory npcInventory = npc.getInventory();
		
		if(currency >= item.getBuyPrice()) {
			if(item.getType().equals("Weapon")) {
				if(npcInventory.getWeapons().containsValue(item)) {
					subtractCurrency(item.getBuyPrice());
					npc.addCurrency(item.getBuyPrice());
					
					inventory.addItem(item);
					npcInventory.removeItem(item);
					bought = true;
				} else {
					bought = false;
				}
			} else if(item.getType().equals("Equipment")) {
				if(npcInventory.getEquipment().containsValue(item)) {
					subtractCurrency(item.getBuyPrice());
					npc.addCurrency(item.getBuyPrice());
					
					inventory.addItem(item);
					npcInventory.removeItem(item);
					bought = true;
				} else {
					bought = false;
				}
			} else if(item.getType().equals("Usable")) {
				if(npcInventory.getUsables().containsValue(item)) {
					subtractCurrency(item.getBuyPrice());
					npc.addCurrency(item.getBuyPrice());
					
					inventory.addItem(item);
					npcInventory.removeItem(item);
					bought = true;
				} else {
					bought = false;
				}
			} else if(item.getType().equals("Consumable")) {
				if(npcInventory.getConsumables().containsValue(item)) {
					subtractCurrency(item.getBuyPrice());
					npc.addCurrency(item.getBuyPrice());
					
					inventory.addItem(item);
					npcInventory.removeItem(item);
					bought = true;
				} else {
					bought = false;
				}
			} else if(item.getType().equals("Treasure")) {
				if(npcInventory.getTreasures().containsValue(item)) {
					subtractCurrency(item.getBuyPrice());
					npc.addCurrency(item.getBuyPrice());
					
					inventory.addItem(item);
					npcInventory.removeItem(item);
					bought = true;
				} else {
					bought = false;
				}
			} else {
				bought = false;
			}
		} else {
			bought = false;
		}
		
		
		
		return bought;
	}

	@Override
	public Boolean sell(NPC npc, Item item) {
		// TODO Auto-generated method stub
		Boolean sold = false;
		Inventory npcInventory = npc.getInventory();
		
		if(npc.getCurrency() >= item.getSellPrice()) {
			if(item.getType().equals("Weapon")) {
				if(inventory.getWeapons().containsValue(item)) {
					npc.subtractCurrency(item.getSellPrice());
					addCurrency(item.getSellPrice());
					
					inventory.removeItem(item);
					npcInventory.addItem(item);
					sold = true;
				} else {
					sold = false;
				}
			} else if(item.getType().equals("Equipment")) {
				if(inventory.getEquipment().containsValue(item)) {
					npc.subtractCurrency(item.getSellPrice());
					addCurrency(item.getSellPrice());
					
					inventory.removeItem(item);
					npcInventory.addItem(item);
					sold = true;
				} else {
					sold = false;
				}
			} else if(item.getType().equals("Usable")) {
				if(inventory.getUsables().containsValue(item)) {
					npc.subtractCurrency(item.getSellPrice());
					addCurrency(item.getSellPrice());
					
					inventory.removeItem(item);
					npcInventory.addItem(item);
					sold = true;
				} else {
					sold = false;
				}
			} else if(item.getType().equals("Consumable")) {
				if(inventory.getConsumables().containsValue(item)) {
					npc.subtractCurrency(item.getSellPrice());
					addCurrency(item.getSellPrice());
					
					inventory.removeItem(item);
					npcInventory.addItem(item);
					sold = true;
				} else {
					sold = false;
				}
			} else {
				sold = false;
			}
		} else {
			sold = false;
		}
		
		
		return sold;
	}

	@Override
	public String use(Item item) {
		// TODO Auto-generated method stub
		String result = null;
		String change = null;
		if(inventory.getConsumables().size() == 0 && inventory.getTreasures().size() == 0) {
			result = "You do not have any items to use.";
		}
		for(Consumable consum : inventory.getConsumables().values()) {
			if(item == consum) {
				if(consum.getMaxHPMod() != 0) {
					actorStats.setMaxHP((int)((double) actorStats.getMaxHP() * (1.0 + ((double) consum.getMaxHPMod() / 100))));
					
					if(consum.getMaxHPMod() > 0) {
						change = "increase";
					} else {
						change = "decrease";
					}
					
					if(result == null) {
						result = "You used " + consum.getName() + " to " + change + " your maximum health by "
								+ consum.getMaxHPMod() + " percent";
					} else {
						result += ", to " + change + " your maximum health by " + consum.getMaxHPMod() + " percent";
					}
				}
								
				if(consum.getCurHPMod() != 0) {
					//actorStats.setCurHP((int)(actorStats.getCurHP() * (1 + (double) (consum.getCurHPMod() / 100))));
					actorStats.setCurHP(actorStats.getCurHP() + consum.getCurHPMod());
					if(actorStats.getCurHP() > actorStats.getMaxHP()) {
						actorStats.setCurHP(actorStats.getMaxHP());
					}
					
					if(consum.getCurHPMod() > 0) {
						change = "increase";
					} else {
						change = "decrease";
					}
					
					if(result == null) {
						result = "You used " + consum.getName() + " to " + change + " your health by "
								+ consum.getCurHPMod();
					} else {
						result += ", to " + change + " your health by " + consum.getCurHPMod();
					}
				}
				
				if(consum.getdefMod() != 0) {
					actorStats.setDef((int)(actorStats.getDef() * (1 + ((double)consum.getdefMod() / 100))));
					
					if(consum.getdefMod() > 0) {
						change = "increase";
					} else {
						change = "decrease";
					}
					
					if(result == null) {
						result = "You used " + consum.getName() + " to " + change + " your defense by "
								+ consum.getdefMod() + " percent";
					} else {
						result += ", to " + change + " your defense by " + consum.getdefMod() + " percent";
					}
				}
				
				if(consum.getdmgMod() != 0) {
					actorStats.setDmg((int)(actorStats.getDmg() * (1 + ((double)consum.getdmgMod() / 100))));
					
					if(consum.getdmgMod() > 0) {
						change = "increase";
					} else {
						change = "decrease";
					}
					
					if(result == null) {
						result = "You used " + consum.getName() + " to " + change + " your damage by "
								+ consum.getdmgMod() + " percent";
					} else {
						result += ", to " + change + " your damage by " + consum.getdmgMod() + " percent";
					}
				}
				
				if(consum.getspdMod() != 0) {
					actorStats.setSpd((int)(actorStats.getSpd() * (1 + ((double)consum.getspdMod() / 100))));
					
					if(consum.getspdMod() > 0) {
						change = "increase";
					} else {
						change = "decrease";
					}
					
					if(result == null) {
						result = "You used " + consum.getName() + " to " + change + " your speed by "
								+ consum.getspdMod();
					} else {
						result += ", to " + change + " your speed by " + consum.getspdMod() + " percent";
					}
				}
				//System.out.println(inventory.getConsumables());
				inventory.removeItem(consum);
			} else {
				result = "" + item.getName() + " is not a usable item or is not in your inventory.";
			}
		}
		
		for(Treasure treasure : inventory.getTreasures().values()) {
			if(item == treasure) {
				Iterator<Interactable> iter = location.getRoomInteractables().iterator(); //Need getInteractables method in Room
				while(iter.hasNext()) {
					Interactable object = iter.next();
					if(object.getPuzzle() instanceof KeyPuzzle) {
						KeyPuzzle puzzle = ((KeyPuzzle)object.getPuzzle());
						if(puzzle.getKey() == treasure) {
							
							//If the treasure being used is the same as the key needed and
							//puzzle is not already solved, complete the puzzle and remove treasure from inventory.
							if(!puzzle.getComplete()) {
								puzzle.setComplete(true);
								inventory.removeItem(treasure);
							}
							
							//Return condition statement of puzzle.
							result = puzzle.checkConditions();
						} else {
							//Treasure item does not match required key item.
							result = "That does not work here.";
						}
					} else {
						result = "That does not work here.";
					}
				}
			}
		}

		
		return result;
	}
	
	
	public void move(int directionCheck) {

		roomId = directionCheck;
		int actorRoom = roomId;
		Map map;
		int newRoom = 0;//map.canMove(actorRoom, direction);
		String descrip;
		boolean activatedCheck = true;
		
		if (activatedCheck = true) {
			if(newRoom > 0) {
				int prevRoomID = actorRoom;
				//setPrevRoomId(prevRoomID); //actor needs a get/setPrevRoomID(int) method
				int actorCurrRoom = newRoom;
				Room connectedRoom = null;//db.getRoomByID(actorCurrRoom);
				//roomID = actorCurrRoom;     //player.setRoomId(actorCurrRoom); 
				if (connectedRoom.getRoomPrevVisit() == false) {
					connectedRoom.setRoomPrevVisit(true);
					//descrip = getRoomDescByID(actorCurrRoom);
				}
				
				
				String currRoomName = connectedRoom.getRoomName();
				//String currRoomDescrip = getRoomDescByID(actorCurrRoom);
			}
			
			else {
				descrip = "You can't go that way";
			}
		}
		
		else {
			descrip = "This exit is not yet activated"; //use getHint()
		}
		
	}
	
	@Override
	public String activateObj(String activationStr, Interactable obj) {
		String result = null;
		//System.out.println(activationStr);
		//System.out.println(obj.getActivationKeyword());
		if(obj.getActivated()) {
			if(!activationStr.equalsIgnoreCase(obj.getActivationKeyword())) {
				result = "Incorrect.";
			} else {
				result = obj.activateObj();
			}
		
			obj.setActivated(false);
		} else if(activationStr.equalsIgnoreCase(obj.getActivationKeyword())) {
			//System.out.print("Works");
			result = obj.activateObj();
		} else {
			result = "That command doesn't work here.";
		}
		
		return result;
	}
}