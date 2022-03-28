package cs320.TBAG.model;

import cs320.TBAG.model.Map;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import junit.framework.TestCase;

public class MoveTest{
	int startingRoom = 1;
	int newRoom;
	String direction = "n";
	Map testMap = new Map();
	
	@Test
	public void testRoomDescrip() {
		String roomDescrip = testMap.currRoomDescrip;
		assertEquals("This is the starting area.  You can go North(n)", roomDescrip);
	}
	
	@Test
	public void testIllegalMove() {
		String roomDescrip = testMap.currRoomDescrip;
		String southDirection = "s";
		testMap.checkMove(southDirection);
		assertEquals("This is the starting area.  You can go North(n)", roomDescrip);
	}
	
	
	@Test
	public void testSuccesfulMove() {
		testMap.checkMove(direction);
		String roomName = testMap.currRoomName;
		assertEquals("second", roomName);
	}
	
	@Test
	public void testSetNewRoom() {
		testMap.setCurrRoom(startingRoom);
		String roomName = testMap.currRoomName;
		assertEquals("starting",roomName);
	}

}
