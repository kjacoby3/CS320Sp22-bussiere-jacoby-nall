package cs320.TBAG.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import cs320.TBAG.database.IDatabase;
import cs320.TBAG.database.DBUtil;
import cs320.TBAG.database.DerbyDatabase;
import cs320.TBAG.database.PersistenceException;
import cs320.TBAG.model.Room;

public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	
	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}
	
	
	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	//Create the tables for the data to be loaded into (Look at lab06)
	public void createTables() {
		
		
	}
	
	//Populates the tables (Look at lab06)
	public void loadInitialData() {
			
		executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					List<Room> roomList;
					
					try {
						roomList = InitialData.getRooms();
					} catch (IOException e) {
						throw new SQLException("Couldn't read initial data", e);
					}

					PreparedStatement insertRoom = null; //PreparedStatement needs to be imported?

					try {
						// populate Rooms table 
						insertRoom = conn.prepareStatement("insert into Rooms (Room Info) values (?...)");
						for (Room room : roomList) {
							//insertRoom.setInt(1, room.getRoomID());	// auto-generated primary key, don't insert this.  MAY NEED THIS WHEN LOADING MULTIPLE LEVELS
							insertRoom.setString(1, room.getRoomName());
							insertRoom.setString(2, room.getRoomDescripLong());
							insertRoom.setString(3, room.getRoomDescripShort());
							insertRoom.setInt(4, room.getRoomConnections());
							insertRoom.setInt(5, room.getRoomUseable());
							insertRoom.setInt(6, room.getRoomTreasure());
							insertRoom.setInt(7, room.getRoomTrophy());
							insertRoom.setInt(8, room.getRoomEquipment());
							insertRoom.setInt(9, room.getRoomWeapon());
							insertRoom.setInt(10, room.getRoomActor());
							insertRoom.setInt(11, room.getRoomLevel());
							
							insertRoom.addBatch();
						}
						insertRoom.executeBatch();
						
						
						return true;
					} finally {
						DBUtil.closeQuietly(insertRoom);
					}
				}
			});
		
	}
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Success!");
	}
	
	/*--------------------- IDatabase Methods ------------------------*/
	
	
	
	
	
	
	
	
	
	
	
	/*----------------------------------------------------------------*/
}