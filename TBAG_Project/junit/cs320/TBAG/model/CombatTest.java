package cs320.TBAG.model;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import junit.framework.TestCase;

public class CombatTest extends TestCase {
	private Player player1;
	private NPC npc1;
	Weapon sword;
	Weapon fists;
	Equipment bare;
	Equipment chestArmor;
	Room location;
	
	
	@Before
	protected void setUp() throws Exception{
		player1 = new Player();
		npc1 = new NPC();
		location = new Room();
		chestArmor = new Equipment("Chest Armor", 800, 20, 0, 0);
		sword = new Weapon("Sword", 15, 450);
		player1.setLocation(location);
		npc1.setLocation(location);
		
		player1.setEqWeap(sword);
		player1.setEquipped(chestArmor);
	}
}

