package cs320.TBAG.model;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import junit.framework.TestCase;

public class ActorTest extends TestCase {
	private Player player1;
	private NPC npc1;
	String name;
	Room location;
	Inventory inventory;
	ActorStats actorStats;
	
	@Before
	protected void setUp() throws Exception{
		name = "Player 1";
		location = new Room();
		inventory = new Inventory(100);
		actorStats = new ActorStats();
	}
	
	@Test
	public void testGetName() {
		player1 = new Player(name, location, inventory, actorStats);
		player1.setName(name);
		assertEquals(player1.getName(), "Player 1");
		
		npc1 = new NPC(name,location,inventory, actorStats);
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
		player1 = new Player(name, location, inventory, actorStats);
		player1.setInventory(inventory);
		assertEquals(player1.getInventory(), inventory);
		
		npc1 = new NPC(name,location,inventory, actorStats);
		npc1.setInventory(inventory);
		assertEquals(npc1.getInventory(), inventory);
	}
	
	@Test
	public void testGetActorStats() {
		player1 = new Player(name, location, inventory, actorStats);
		player1.setActorStats(actorStats);
		assertEquals(player1.getActorStats(), actorStats);
		
		npc1 = new NPC(name,location,inventory, actorStats);
		npc1.setActorStats(actorStats);
		assertEquals(npc1.getActorStats(), actorStats);
	}
}