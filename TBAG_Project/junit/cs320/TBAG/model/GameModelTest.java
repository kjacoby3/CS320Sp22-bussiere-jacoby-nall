package cs320.TBAG.model;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import junit.framework.TestCase;

public class GameModelTest extends TestCase{
	Game model;
	String username;
	String password;
	Save save;
	Map map;
	ArrayList<Actor> actorsList;
	ArrayList<Inventory> inventories;
	Player player1;
	
	@Before
	public void setUp() {
		model = new Game();
		username = "kjacoby3";
		password = "kjacoby3";
		actorsList = new ArrayList<Actor>();
		inventories = new ArrayList<Inventory>();
		map = new Map();
		save = new Save(actorsList, inventories, map);
	}
	
	@Test
	public void testGetUsername() {
		model.setUsername(username);
		assertEquals(model.getUsername(), "kjacoby3");
	}
	
	@Test
	public void testGetPassword() {
		model.setPassword(password);
		assertEquals(model.getPassword(), "kjacoby3");
	}
	
	@Test 
	public void testGetActorsList() {
		model.newActorsList();
		assertEquals(model.getActorsList(), actorsList);
	}

	@Test
	public void testGetInventoriesList() {
		model.newInventoryList();
		assertEquals(model.getInventories(), inventories);
	}
}
