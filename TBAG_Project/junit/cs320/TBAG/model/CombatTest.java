package cs320.TBAG.model;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import junit.framework.TestCase;

public class CombatTest extends TestCase {
	private Player player1;
	private NPC npc1;
	private Combat combat;
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
		chestArmor = new Equipment("Chest Armor", 20, 0, 0);
		sword = new Weapon("Sword", 15, 450);
		player1.setLocation(location);
		npc1.setLocation(location);
		
		player1.setEqWeap(sword);
		player1.setEquipped(chestArmor);
		combat = new Combat(player1, npc1);
	}
	
	@Test
	public void testGetTurn() {
		assertEquals(combat.getTurn(), 1);
	}
	
	@Test
	public void testActor1Attack() {
		System.out.println(combat.actor1Attack());
	}
	
	@Test
	public void testActor1CalcAttackDMG() {
		System.out.println("Actor 1 Attack DMG: " + combat.actor1CalcAttackDMG());
	}
	
	@Test
	public void testActor1CalcAttackDMGCalculations() {
System.out.println("Actor 1 Calculated Attack Damage Calculations: ");
		System.out.println("actor1TotalDMG: " + combat.actor1TotalDMG());
		System.out.println("actor2TotalDEF: " + combat.actor2TotalDEF());
		System.out.println("z: " + (combat.actor1TotalDMG() * (1 - ((combat.actor2TotalDEF() / (3 * combat.actor1TotalDMG()))))));
		System.out.println("Lower Bound: " + (combat.actor1TotalDMG() * (1 - ((combat.actor2TotalDEF() / (3 * combat.actor1TotalDMG()))))) * .9);
		System.out.println("Upper Bound: " + (combat.actor1TotalDMG() * (1 - ((combat.actor2TotalDEF() / (3 * combat.actor1TotalDMG()))))) * 1.1);
		System.out.println("Dice Roll: " + combat.intDiceRoll( (int) ((combat.actor1TotalDMG() * (1 - ((combat.actor2TotalDEF() / (3 * combat.actor1TotalDMG()))))) * .9),
						(int) ((combat.actor1TotalDMG() * (1 - ((combat.actor2TotalDEF() / (3 * combat.actor1TotalDMG()))))) * 1.1)));
	}
	
	@Test
	public void testActor1TotalDMG() {
		System.out.println("Actor 1 Total Damage: " + combat.actor1TotalDMG());
	}
	
	@Test
	public void testActor2TotalDEF() {
		System.out.println("Actor 2 Total Defense: " + combat.actor2TotalDEF());
	}
	
	@Test
	public void testActor2TotalDEFCalculations() {
		System.out.println("Actor 2 Total Defense Calculations: " + "statDEF: " + combat.getActor2().getActorStats().getDef()
				+ " equipDEF: " + combat.getActor2().getEquipped().getDefenseMod() + 
				" totalDEF: " + (int) ((double) combat.getActor2().getEquipped().getDefenseMod() * (((double) combat.getActor2().getActorStats().getDef() / 100) + 1)));
	}
}

