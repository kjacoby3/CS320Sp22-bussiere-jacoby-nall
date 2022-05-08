package cs320.TBAG.model;
import org.junit.Before;
import org.junit.Test;

import cs320.TBAG.model.Convo.ConversationTree;

import org.junit.Assert.*;

import junit.framework.TestCase;

public class ActorTest extends TestCase {
	private Player player1;
	private NPC npc1;
	String name;
	Room location;
	Inventory inventory;
	ActorStats actorStats;
	Weapon fists;
	Equipment bare;
	//Consumable healthPotion;
	
	@Before
	protected void setUp() throws Exception{
		name = "Player 1";
		location = new Room();
		inventory = new Inventory(100);
		actorStats = new ActorStats();
		fists = new Weapon("Fists", 0, 0, 0, 0, 0, true);
		bare = new Equipment("Bare", 100, 0, 0, 0, 0, 0, 0, true);
		//healthPotion = new Consumable("Health Potion", 0, 10, 0, 0, 0, 0, 0, 0, 0);
		
	}
	
	@Test
	public void testGetName() {
		player1 = new Player(name, location, inventory, actorStats, fists, bare);
		player1.setName(name);
		assertEquals(player1.getName(), "Player 1");
		
		npc1 = new NPC(name,location,inventory, actorStats,"npc", fists, bare, 0, 0, new ConversationTree());
		npc1.setName(name);
		assertEquals(npc1.getName(), "Player 1");
	}
	
	@Test
	public void testGetLocation() {
		player1 = new Player();
		player1.setLocation(location);
		assertEquals(player1.getLocation(), location);
		
		npc1 = new NPC();
		npc1.setLocation(location);
		assertEquals(npc1.getLocation(), location);
	}
	
	@Test
	public void testGetInventory() {
		player1 = new Player(name, location, inventory, actorStats, fists, bare);
		player1.setInventory(inventory);
		assertEquals(player1.getInventory(), inventory);
		
		npc1 = new NPC(name,location,inventory, actorStats, "npc", fists, bare, 0, 0, new ConversationTree());
		npc1.setInventory(inventory);
		assertEquals(npc1.getInventory(), inventory);
	}
	
	@Test
	public void testGetActorStats() {
		player1 = new Player(name, location, inventory, actorStats, fists, bare);
		player1.setActorStats(actorStats);
		assertEquals(player1.getActorStats(), actorStats);
		
		npc1 = new NPC(name,location,inventory, actorStats, "npc", fists, bare, 0, 0, new ConversationTree());
		npc1.setActorStats(actorStats);
		assertEquals(npc1.getActorStats(), actorStats);
	}
	
	@Test
	public void testUseConsumable() {
		player1 = new Player(name, location, inventory, actorStats, fists, bare);
		Consumable healthPotion = new Consumable("Health Potion", 0, 10, 20, 0, 0, 0, 0, 0, 0);
		player1.getInventory().addItem(healthPotion);
		player1.getActorStats().setCurHP(50);
		System.out.println(player1.getActorStats().getCurHP());
		System.out.println(player1.use(healthPotion));
		player1.getInventory().addItem(healthPotion);
		System.out.println(player1.getActorStats().getCurHP());
		System.out.println(player1.use(healthPotion));
		System.out.println(player1.getActorStats().getCurHP());
		System.out.println(player1.use(healthPotion));
		System.out.println(player1.getActorStats().getCurHP());
		
		Consumable superPotion = new Consumable("Super Potion", 0, 1000, 10, 100, 100, 100, 0, 0, 0);
		player1.getInventory().addItem(superPotion);
		
		System.out.println("Max HP " + player1.getActorStats().getMaxHP());
		System.out.println("Current HP " + player1.getActorStats().getCurHP());
		System.out.println("DEF " + player1.getActorStats().getDef());
		System.out.println("DMG " + player1.getActorStats().getDmg());
		System.out.println("SPD " + player1.getActorStats().getSpd());
		
		System.out.println(player1.use(superPotion));
		
		System.out.println("Max HP " + player1.getActorStats().getMaxHP());
		System.out.println("Current HP " + player1.getActorStats().getCurHP());
		System.out.println("DEF " + player1.getActorStats().getDef());
		System.out.println("DMG " + player1.getActorStats().getDmg());
		System.out.println("SPD " + player1.getActorStats().getSpd());
	}
	
	
	@Test
	public void testBuy() {
		player1 = new Player(name, location, inventory, actorStats, fists, bare);
		npc1 = new NPC(name,location,inventory, actorStats, "npc", fists, bare, 0, 0, new ConversationTree());
		Consumable consum = new Consumable("Consum", 100, 0, 0, 0, 0, 0, 0, 0, 0);
		
		npc1.pickUp(consum);
		player1.setCurrency(5);
		System.out.println("" + player1.getInventory().getConsumables().keySet());
		System.out.println("Currency" + player1.getCurrency());
		assertFalse(player1.buy(npc1, consum));
		player1.addCurrency(500);
		assertTrue(player1.buy(npc1, consum));
		System.out.println("Currency" + player1.getCurrency());
		System.out.println("" + player1.getInventory().getConsumables().keySet());
		
		
	}
	
	@Test
	public void testSell() {
		player1 = new Player(name, location, inventory, actorStats, fists, bare);
		npc1 = new NPC(name,location,inventory, actorStats, "npc", fists, bare, 0, 0, new ConversationTree());
		Consumable consum = new Consumable("Consum", 100, 0, 0, 0, 0, 0, 0, 0, 0);
		
		player1.pickUp(consum);
		npc1.setCurrency(5);
		System.out.println("" + npc1.getInventory().getConsumables().keySet());
		System.out.println("Currency" + player1.getCurrency());
		assertFalse(player1.sell(npc1, consum));
		npc1.addCurrency(500);
		assertTrue(player1.sell(npc1, consum));
		System.out.println("Currency" + player1.getCurrency());
		System.out.println("" + npc1.getInventory().getConsumables().keySet());
		
		
	}
}