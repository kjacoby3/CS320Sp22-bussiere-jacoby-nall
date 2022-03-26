package cs320.TBAG.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import junit.framework.TestCase;

public class MoveTest extends TestCase{
	int currentRoom = 1;
	int newRoom;
	String direction = "north";
	
	
	@Before
	protected void setUp() {
		Map map = new Map();
	}
	
	@Test
	public void testRoomDescrip() {
		Map map = new Map();
		assertEquals("You find yourself in the starting area.  There is a banana on the floor", map.getRoomDescription(currentRoom));
	}
	
	@Test
	public void testSuccesfulMove() {
		Map map = new Map();
		assertTrue(map.checkMove(currentRoom,direction));
	}
	
	@Test
	public void testIllegalMove() {
		String southDirection = "south";
		Map map = new Map();
		assertFalse(map.checkMove(currentRoom,southDirection));
	}
	
	@Test
	public void testSetNewRoom() {
		Map map = new Map();
		map.checkMove(currentRoom,direction);
		currentRoom = map.setNewRoom(map.getNewRoomID());
		assertEquals(2,currentRoom);
	}

}
