package cs320.TBAG.model;

public class Weapon extends Item{
		int damage;
		
		public Weapon(String name, int damage, int price) {
			this.name = name;
			this.price = price;
			this.type = "Weapon";
			this.damage = damage;
		}
		
		public int getDamage(){
			return damage;
		}
}
