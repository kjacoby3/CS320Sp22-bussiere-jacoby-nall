package cs320.TBAG.model;
import java.util.HashMap;

public class Inventory{
	/*private ArrayList<Equipment> equipment;
	private ArrayList<Weapons> weapons;
	private ArrayList<Trophies> trophies;
	private ArrayList<Usables> usables;
	private ArrayList<Treasures> treasures;*/
	private int maxSize;
	private HashMap<String, Equipment> equipment;
	private HashMap<String, Weapon> weapons;
	private HashMap<String, Trophy> trophies;
	private HashMap<String, Usable> usables;
	private HashMap<String, Treasure> treasures;
	
	public Inventory(int maxSize) {
		/*equipment = new ArrayList<Equipment>();
		weapons = new ArrayList<Weapons>();
		trophies = new ArrayList<Trophies>();
		usables = new ArrayList<Usables>();
		treasures = new ArrayList<Treasures>();*/
		equipment = new HashMap<String, Equipment>();
		weapons = new HashMap<String, Weapon>();
		trophies = new HashMap<String, Trophy>();
		usables = new HashMap<String, Usable>();
		treasures = new HashMap<String, Treasure>();
		this.maxSize = maxSize;
		
	}
	
	public boolean addItem(Item item) {
		
		if(item.getType().equals("Equipment")) {
			if(equipment.size()<maxSize) {
				equipment.put(item.getName(), (Equipment) item);
				return true;
			}
		}
		
		else if(item.getType() == "Weapon") {
			if(weapons.size()<maxSize) {
				weapons.put(item.getName(), (Weapon) item);
				return true;
			}
		}
		
		else if(item.getType().equals("Trophy")) {
			if(trophies.size()<maxSize) {
				trophies.put(item.getName(), (Trophy) item);
				return true;
			}
		}
		
		else if(item.getType().equals("Usable")) {
			if(usables.size()<maxSize) {
				usables.put(item.getName(), (Usable) item);
				return true;
			}
		}
		return false;
	}
	
	public boolean addItem(Treasure treasure) {
		if(treasures.size()<maxSize) {
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
		
		return null;	
	}
	
	public Treasure removeItem(Treasure treasure) {
		return (treasures.remove(treasure.getName()));
	}
	
	public boolean checkFull(String type) {
		if(type.equals("Equipment") && equipment.size() >= maxSize) {
			return true;
				
		}
		
		else if(type.equals( "Weapon") && weapons.size() >= maxSize) {
			return true;
		}
		
		else if(type.equals("Trophy") && trophies.size() >= maxSize) {
			return true;
		}
		
		else if(type.equals("Usable") && usables.size() >= maxSize) {
			return true;
		}
		else if(type.equals("Treasure") && treasures.size() >= maxSize) {
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
	
	public void setMaxSize(int size) {
		maxSize = size;
	}
	
	
	
}