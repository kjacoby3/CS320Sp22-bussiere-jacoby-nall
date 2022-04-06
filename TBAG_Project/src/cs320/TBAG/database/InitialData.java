package cs320.TBAG.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs320.TBAG.model.Room;

public class InitialData {
	
	public static List<Room> getRooms() throws IOException {
		List<Room> roomList = new ArrayList<Room>();
		ReadCSV readRooms = new ReadCSV("Room Nodes.csv");
		try {
			// auto-generated primary key for books table
			Integer roomId = 1;
			while (true) {
				List<String> tuple = readRooms.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Room room = new Room();
				room.setRoomId(roomId++);
				room.setRoomName(i.next());
				room.setRoomDescripLong(i.next());
				room.setRoomDescripShort(i.next());
				
				//These next two will not be in the room CSV. We need to figure out how to create these
				room.setRoomItems(null);
				room.setNPCsInRoom(null);
				
				//These next two will need to be Lists created from separate CSVs than the current Room CSVs
				room.setAvailableExits(i.next());
				room.setOtherExitOptions(i.next());
				
				roomList.add(room);
			}
			return roomList;
		} finally {
			readRooms.close();
		}
	}

	
}