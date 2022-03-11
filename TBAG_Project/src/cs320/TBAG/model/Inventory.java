package cs320.TBAG.model;
import java.util.ArrayList;
import java.util.HashMap;

public class Inventory{
	/*private ArrayList<Equipment> equipment;
	private ArrayList<Weapons> weapons;
	private ArrayList<Trophies> trophies;
	private ArrayList<Usables> usables;
	private ArrayList<Treasures> treasures;*/
	private int maxSize;
	private HashMap<String, Equipment> equipment;
	private HashMap<String, Weapons> weapons;
	private HashMap<String, Trophies> trophies;
	private HashMap<String, Usables> usables;
	private HashMap<String, Treasures> treasures;
	private ArrayList<Equipment> equip= new ArrayList<Equipment>();
	
	public Inventory(int maxSize) {
		/*equipment = new ArrayList<Equipment>();
		weapons = new ArrayList<Weapons>();
		trophies = new ArrayList<Trophies>();
		usables = new ArrayList<Usables>();
		treasures = new ArrayList<Treasures>();*/
		equipment = new HashMap<String, Equipment>();
		weapons = new HashMap<String, Weapons>();
		trophies = new HashMap<String, Trophies>();
		usables = new HashMap<String, Usables>();
		treasures = new HashMap<String, Treasures>();
		this.maxSize = maxSize;
		
	}
	
	public boolean addItem(Items item) {
		
		if(item.getType().equals("Equipment")) {
			if(equipment.size()<maxSize) {
				equipment.put(item.getName(), (Equipment) item);
				return true;
			}
		}
		
		else if(item.getType() == "Weapons") {
			if(weapons.size()<maxSize) {
				weapons.put(item.getName(), (Weapons) item);
				return true;
			}
		}
		
		else if(item.getType().equals("Trophies")) {
			if(trophies.size()<maxSize) {
				trophies.put(item.getName(), (Trophies) item);
				return true;
			}
		}
		
		else if(item.getType().equals("Usables")) {
			if(usables.size()<maxSize) {
				usables.put(item.getName(), (Usables) item);
				return true;
			}
		}
		return false;
	}
	
	public boolean addItem(Treasures treasure) {
		if(treasures.size()<maxSize) {
			treasures.put(treasure.getName(), treasure);
			return true;
		}
		
		return false;
	}
	
	public Items removeItem(Items item) {
		if(item.getType().equals("Equipment")) {
			return equipment.remove(item.getName());
		}
		
		else if(item.getType() == "Weapons") {
			return equipment.remove(item.getName());
		}
		
		else if(item.getType().equals("Trophies")) {
			return equipment.remove(item.getName());
		}
		
		else if(item.getType().equals("Usables")) {
			return equipment.remove(item.getName());
		}
		
		return null;	
	}
	
	public boolean checkFull(String type) {
		if(type.equals("Equipment") && equipment.size() >= maxSize) {
			return true;
				
		}
		
		else if(type.equals( "Weapons") && weapons.size() >= maxSize) {
			return true;
		}
		
		else if(type.equals("Trophies") && trophies.size() >= maxSize) {
			return true;
		}
		
		else if(type.equals("Usables") && usables.size() >= maxSize) {
			return true;
		}
		else if(type.equals("Treasures") && treasures.size() >= maxSize) {
			return true;
		}
		
		return false;
	}
	
	
}