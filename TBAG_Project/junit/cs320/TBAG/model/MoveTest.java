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
	String direction2 = "e";
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
		testMap.checkMove(direction2);
		String roomName = testMap.currRoomDescrip;
		assertEquals("North East Area. You can only go back West to the previous room", roomName);
	}
	
	@Test
	public void testSetNewRoom() {
		testMap.setCurrRoom(startingRoom);
		String roomName = testMap.currRoomName;
		assertEquals("starting",roomName);
	}

}
