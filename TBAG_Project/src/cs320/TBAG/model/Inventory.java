package cs320.TBAG.model;
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
	
	public Inventory(int maxSize) {
		equipment = new HashMap<String, Equipment>();
		weapons = new HashMap<String, Weapon>();
		trophies = new HashMap<String, Trophy>();
		usables = new HashMap<String, Usable>();
		treasures = new HashMap<String, Treasure>();
		consumables = new HashMap<String, Consumable>();
		
		
		equipmentMaxSize = maxSize;
		weaponsMaxSize = maxSize;
		usablesMaxSize = maxSize;
		trophiesMaxSize = maxSize;
		treasuresMaxSize = maxSize;
		consumablesMaxSize = maxSize;
		
	}
	
	public boolean addItem(Item item) {
		
		if(item.getType().equals("Equipment")) {
			if(equipment.size()<equipmentMaxSize) {
				equipment.put(item.getName(), (Equipment) item);
				return true;
			}
		}
		
		else if(item.getType() == "Weapon") {
			if(weapons.size()<weaponsMaxSize) {
				weapons.put(item.getName(), (Weapon) item);
				return true;
			}
		}
		
		else if(item.getType().equals("Trophy")) {
			if(trophies.size()<trophiesMaxSize) {
				trophies.put(item.getName(), (Trophy) item);
				return true;
			}
		}
		
		else if(item.getType().equals("Usable")) {
			if(usables.size()<usablesMaxSize) {
				usables.put(item.getName(), (Usable) item);
				return true;
			}
		}
		else if(item.getType().equals("Consumable")) {
			if(consumables.size()<consumablesMaxSize) {
				consumables.put(item.getName(), (Consumable) item);
			}
		}
		return false;
	}
	
	public boolean addItem(Treasure treasure) {
		if(treasures.size()<treasuresMaxSize) {
			treasures.put(treasure.getName(), treasure);
			return true;
		}
		
		return false;
	}
	
	public Item removeItem(Item item) {
		if(item.getType().equals("Equipment")) {
			return equipment.remove(item.getName());
		}
		
		else if(item.getType() == "Weapon") {
			return weapons.remove(item.getName());
		}
		
		else if(item.getType().equals("Trophy")) {
			return trophies.remove(item.getName());
		}
		
		else if(item.getType().equals("Usable")) {
			return usables.remove(item.getName());
		}
		else if(item.getType().equals("Consumable")) {
			return consumables.remove(item.getName());
		}
		
		return null;	
	}
	
	public Treasure removeItem(Treasure treasure) {
		return (treasures.remove(treasure.getName()));
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

	
	
}