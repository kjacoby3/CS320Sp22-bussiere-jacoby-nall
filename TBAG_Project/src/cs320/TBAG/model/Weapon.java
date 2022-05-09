package cs320.TBAG.model;

public class Weapon extends Item{
		int damage;
		boolean equipped;
		
		public Weapon(int itemID, String name, int damage, int price, int playerID, int roomID, int npcID, boolean equipped) {
			this.itemID = itemID;
			this.name = name;
			this.price = price;
			this.type = "Weapon";
			this.damage = damage;
			this.playerID=playerID;
			this.roomID=roomID;
			this.npcID=npcID;
			this.equipped = equipped;
		}
		
		public int getDamage(){
			return damage;
		}
		
		public int getPrice() {
			return price;
		}
		public boolean getEquipped() {
			return equipped;
		}
}
