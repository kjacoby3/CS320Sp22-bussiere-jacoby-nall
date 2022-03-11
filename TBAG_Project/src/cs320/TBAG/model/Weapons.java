package cs320.TBAG.model;

public class Weapons extends Items{
		int damage;
		int durability;
		
		public Weapons(String name, int damage, int durability) {
			this.name = name;
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
