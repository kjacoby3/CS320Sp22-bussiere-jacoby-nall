package cs320.TBAG.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InitialData {
	
	public static List<Room> getRoom() throws IOException {
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
				room.setAuthorId(Integer.parseInt(i.next()));
				room.setTitle(i.next());
				room.setIsbn(i.next());
				room.setPublished(Integer.parseInt(i.next()));
				roomList.add(book);
			}
			return roomList;
		} finally {
			readRooms.close();
		}
	}

	
}