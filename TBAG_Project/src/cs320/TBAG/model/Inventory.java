package cs320.TBAG.model;
import java.util.ArrayList;
import java.util.HashMap;

public class Inventory{
	private int equipmentMaxSize;
	private int weaponsMaxSize;
	private int trophiesMaxSize;
	private int usablesMaxSize;
	private int treasuresMaxSize;
	private int consumablesMaxSize;
	
	private HashMap<String, Equipment> equipment;
	private HashMap<String, Weapon> weapons;
	private HashMap<String, Trophy> trophies;
	private HashMap<String, Usable> usables;
	private HashMap<String, Treasure> treasures;
	private HashMap<String, Consumable> consumables;
	
	private HashMap<String, Integer> equipmentCount;
	private HashMap<String, Integer> weaponCount;
	private HashMap<String, Integer> trophyCount;
	private HashMap<String, Integer> usableCount;
	private HashMap<String, Integer> treasureCount;
	private HashMap<String, Integer> consumableCount;
	
	public Inventory(int maxSize) {
		equipment = new HashMap<String, Equipment>();
		weapons = new HashMap<String, Weapon>();
		trophies = new HashMap<String, Trophy>();
		usables = new HashMap<String, Usable>();
		treasures = new HashMap<String, Treasure>();
		consumables = new HashMap<String, Consumable>();
		
		equipmentCount = new HashMap<String, Integer>();
		weaponCount = new HashMap<String, Integer>();
		trophyCount = new HashMap<String, Integer>();
		usableCount = new HashMap<String, Integer>();
		treasureCount = new HashMap<String, Integer>();
		consumableCount = new HashMap<String, Integer>();
		
		
		equipmentMaxSize = maxSize;
		weaponsMaxSize = maxSize;
		usablesMaxSize = maxSize;
		trophiesMaxSize = maxSize;
		treasuresMaxSize = maxSize;
		consumablesMaxSize = maxSize;
		
	}
	
	public boolean addItem(Item item) {
		
		if(item.getType().equals("Equipment")) {
			if(equipment.size()<equipmentMaxSize || equipment.containsKey(item.getName())) {
				if(equipment.containsKey(item.getName())) {
					equipmentCount.merge(item.getName(), 1, Integer::sum);
				}
				else {
					equipment.put(item.getName(), (Equipment) item);
					equipmentCount.put(item.getName(), 1);
				}
				return true;
			}
		}
		
		else if(item.getType() == "Weapon") {
			if(weapons.size()<weaponsMaxSize || weapons.containsKey(item.getName())) {
				if(weapons.containsKey(item.getName())) {
					weaponCount.merge(item.getName(), 1, Integer::sum);
				}
				else {
					weapons.put(item.getName(), (Weapon) item);
					weaponCount.put(item.getName(), 1);
				}
				return true;
			}
		}
		
		else if(item.getType().equals("Trophy")) {
			if(trophies.size()<trophiesMaxSize || trophies.containsKey(item.getName())) {
				if(trophies.containsKey(item.getName())) {
					trophyCount.merge(item.getName(), 1, Integer::sum);
				}
				else {
					trophies.put(item.getName(), (Trophy) item);
					trophyCount.put(item.getName(), 1);
				}
				return true;
			}
		}
		
		else if(item.getType().equals("Usable")) {
			if(usables.size()<usablesMaxSize || usables.containsKey(item.getName())) {
				if(usables.containsKey(item.getName())) {
					usableCount.merge(item.getName(), 1, Integer::sum);
				}
				else {
					usables.put(item.getName(), (Usable) item);
					usableCount.put(item.getName(), 1);
				}
				return true;
			}
		}
		else if(item.getType().equals("Consumable")) {
			if(consumables.size()<consumablesMaxSize || consumables.containsKey(item.getName())) {
				if(consumables.containsKey(item.getName())) {
					consumableCount.merge(item.getName(), 1, Integer::sum);
				}
				else {
					consumables.put(item.getName(), (Consumable) item);
					consumableCount.put(item.getName(), 1);
				}
			}	return true;
		}
		
		else if(item.getType().equals("Treasure")) {
			if(treasures.size()<treasuresMaxSize || treasures.containsKey(item.getName())) {
				if(treasures.containsKey(item.getName())) {
					treasureCount.merge(item.getName(), 1, Integer::sum);
				}
				else {
					treasures.put(item.getName(), (Treasure) item);
					treasureCount.put(item.getName(), 1);
				}
				return true;
			}
		}
		return false;
	}
	
//	public boolean addItem(Treasure item) {
//		if(treasures.size()<treasuresMaxSize || treasures.containsKey(item.getName())) {
//			if(treasures.containsKey(item.getName())) {
//				treasureCount.merge(item.getName(), 1, Integer::sum);
//			}
//			else {
//				treasures.put(item.getName(), item);
//				treasureCount.put(item.getName(), 1);
//			}
//			return true;
//		}
//		
//		return false;
//	}
	
	public Item removeItem(Item item) {
		if(item.getType().equals("Equipment")) {
			if(equipmentCount.get(item.getName())>1) {
				equipmentCount.merge(item.getName(), -1, Integer::sum);
				return equipment.get(item.getName());
			}
			else {
				equipmentCount.remove(item.getName());
				return equipment.remove(item.getName());
			}

		}
		
		else if(item.getType() == "Weapon") {
			if(weaponCount.get(item.getName())>1) {
				weaponCount.merge(item.getName(), -1, Integer::sum);
				return weapons.get(item.getName());
			}
			else {
				weaponCount.remove(item.getName());
				return weapons.remove(item.getName());
			}
		}
		
		else if(item.getType().equals("Trophy")) {
			if(trophyCount.get(item.getName())>1) {
				trophyCount.merge(item.getName(), -1, Integer::sum);
				return trophies.get(item.getName());
			}
			else {
				trophyCount.remove(item.getName());
				return trophies.remove(item.getName());
			}
		}
		
		else if(item.getType().equals("Usable")) {
			if(usableCount.get(item.getName())>1) {
				usableCount.merge(item.getName(), -1, Integer::sum);
				return usables.get(item.getName());
			}
			else {
				usableCount.remove(item.getName());
				return usables.remove(item.getName());
			}
		}
		else if(item.getType().equals("Consumable")) {
			if(consumableCount.get(item.getName())>1) {
				consumableCount.merge(item.getName(), -1, Integer::sum);
				return consumables.get(item.getName());
			}
			else {
				consumableCount.remove(item.getName());
				return consumables.remove(item.getName());
			}
		}
		
		return null;	
	}
	
	public Treasure removeItem(Treasure item) {
		if(treasureCount.get(item.getName())>1) {
			treasureCount.merge(item.getName(), -1, Integer::sum);
			return treasures.get(item.getName());
		}
		else {
			treasureCount.remove(item.getName());
			return treasures.remove(item.getName());
		}
	}
	
	public boolean checkFull(String type) {
		if(type.equals("Equipment") && equipment.size() >= equipmentMaxSize) {
			return true;
				
		}
		
		else if(type.equals("Weapon") && weapons.size() >= weaponsMaxSize) {
			return true;
		}
		
		else if(type.equals("Trophy") && trophies.size() >= trophiesMaxSize) {
			return true;
		}
		
		else if(type.equals("Usable") && usables.size() >= usablesMaxSize) {
			return true;
		}
		else if(type.equals("Treasure") && treasures.size() >= treasuresMaxSize) {
			return true;
		}
		else if(type.equals("Consumable") && consumables.size() >= consumablesMaxSize){
			return true;
		}
		
		return false;
	}
	
	public HashMap<String, Equipment> getEquipment() {
		return equipment;
	}
	
	public HashMap<String, Weapon> getWeapons(){
		return weapons;
	}
	
	public HashMap<String, Trophy> getTrophies(){
		return trophies;
	}
	
	public HashMap<String, Usable> getUsables(){
		return usables;
	}
	
	public HashMap<String, Treasure> getTreasures(){
		return treasures;
	}
	
	public HashMap<String, Consumable> getConsumables(){
		return consumables;
	}
	
	public HashMap<String, Integer> getEquipmentCount() {
		return equipmentCount;
	}
	
	public HashMap<String, Integer> getWeaponCount(){
		return weaponCount;
	}
	
	public HashMap<String, Integer> getTrophyCount(){
		return trophyCount;
	}
	
	public HashMap<String, Integer> getUsableCount(){
		return usableCount;
	}
	
	public HashMap<String, Integer> getTreasureCount(){
		return treasureCount;
	}
	
	public HashMap<String, Integer> getConsumableCount(){
		return consumableCount;
	}
	
	public void setEquipmentMaxSize(int size) {
		equipmentMaxSize = size;
	}
	
	public void setWeaponsMaxSize(int size) {
		weaponsMaxSize = size;
	}
	
	public void setTrophiesMaxSize(int size) {
		trophiesMaxSize = size;
	}
	
	public void setUsablesMaxSize(int size) {
		usablesMaxSize = size;
	}
	
	public void setTreasuresMaxSize(int size) {
		treasuresMaxSize = size;
	}
	
	public void setConsumablesMaxSize(int size) {
		consumablesMaxSize = size;
	}
	
	public int getEquipmentMaxSize() {
		return equipmentMaxSize;
	}
	
	public int getWeaponsMaxSize() {
		return weaponsMaxSize;
	}
	
	public int getTrophiesMaxSize() {
		return trophiesMaxSize;
	}
	
	public int getUsablesMaxSize() {
		return usablesMaxSize;
	}
	
	public int getTreasuresMaxSize() {
		return treasuresMaxSize;
	}
	
	public int getConsumablesMaxSize() {
		return consumablesMaxSize;
	}

	public ArrayList<String> getInventoryDescription(){
		ArrayList<String> strList = new ArrayList<String>();
		String itemStr = null;
		
		for(Weapon weap : weapons.values()) {
			itemStr = null;
			if(strList.size() == 0) {
				itemStr = "There is a ";
			} else {
				itemStr = "";
			}
			itemStr = itemStr + weap.getType() + ", " + weap.getName() + " on the ground.";
			strList.add(itemStr);
		}
		
		for(Equipment equip : equipment.values()) {
			itemStr = null;
			if(strList.size() == 0) {
				itemStr = "There is a ";
			} else {
				itemStr = "";
			}
			itemStr = itemStr + equip.getType() + ", " + equip.getName() + " on the ground.";
			strList.add(itemStr);
		}
		
		for(Usable use : usables.values()) {
			itemStr = null;
			if(strList.size() == 0) {
				itemStr = "There is a ";
			} else {
				itemStr = "";
			}
			itemStr = itemStr + use.getType() + ", " + use.getName() + " on the ground.";
			strList.add(itemStr);
		}
		
		for(Consumable consum : consumables.values()) {
			itemStr = null;
			if(strList.size() == 0) {
				itemStr = "There is a ";
			} else {
				itemStr = "";
			}
			itemStr = itemStr + consum.getType() + ", " + consum.getName() + " on the ground.";
			strList.add(itemStr);
		}
		
		for(Treasure treas : treasures.values()) {
			itemStr = null;
			if(strList.size() == 0) {
				itemStr = "There is a ";
			} else {
				itemStr = "";
			}
			itemStr = itemStr + treas.getType() + ", " + treas.getName() + " on the ground.";
			strList.add(itemStr);
		}
		
		
		return strList;
	}
	
}