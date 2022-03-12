package cs320.TBAG.model;

public class Weapon extends Item{
		int damage;
		int durability;
		
		public Weapon(String name, int damage, int durability) {
			this.name = name;
			this.type = "Weapon";
			this.damage = damage;
			this.durability = durability;
		}
		
		public int getDamage(){
			return damage;
		}
		
		public int getDurability() {
			return durability;
		}
}
