package cs320.TBAG.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

class MoveTest {
	int currentRoom = 1;
	int newRoom;
	String direction = "north";
	
	
	@Before
	void setUp() {
		Map map = new Map();
	}
	
	@Test
	void testRoomDescrip() {
		Map map = new Map();
		assertEquals("You find yourself in the starting area.  There is a banana on the floor", map.getRoomDescription(currentRoom));
	}
	
	@Test
	void testSuccesfulMove() {
		Map map = new Map();
		assertTrue(map.checkMove(currentRoom,direction));
	}
	
	@Test
	void testIllegalMove() {
		String southDirection = "south";
		Map map = new Map();
		assertFalse(map.checkMove(currentRoom,southDirection));
	}
	
	@Test
	void testSetNewRoom() {
		Map map = new Map();
		map.checkMove(currentRoom,direction);
		currentRoom = map.setNewRoom(map.getNewRoomID());
		assertEquals(2,currentRoom);
	}

}
