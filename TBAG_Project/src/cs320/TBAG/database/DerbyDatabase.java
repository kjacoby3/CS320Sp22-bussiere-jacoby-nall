package cs320.TBAG.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cs320.TBAG.database.IDatabase;
import cs320.TBAG.database.DBUtil;
import cs320.TBAG.database.DerbyDatabase;
import cs320.TBAG.database.PersistenceException;
import cs320.TBAG.model.Consumable;
import cs320.TBAG.model.Equipment;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Room;
import cs320.TBAG.model.RoomConnection;
import cs320.TBAG.model.Treasure;
import cs320.TBAG.model.Trophy;
import cs320.TBAG.model.Usable;
import cs320.TBAG.model.Weapon;

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
	
	public Inventory constructInventoryByPlayerID(int playerID) {
		return executeTransaction(new Transaction<Inventory>() {
			@Override
			public Inventory execute(Connection conn) throws SQLException {
				PreparedStatement weaponstmt = null;
				PreparedStatement equipmentstmt = null;
				PreparedStatement usablestmt = null;
				PreparedStatement consumablestmt = null;
				PreparedStatement trophystmt = null;
				PreparedStatement treasurestmt = null;
				Inventory inventory = new Inventory(100);
				
				
				ResultSet weaponSet = null;
				ResultSet equipmentSet = null;
				ResultSet usableSet = null;
				ResultSet consumableSet = null;
				ResultSet trophySet = null;
				ResultSet treasureSet = null;
				
				try {
					weaponstmt = conn.prepareStatement(
							"select weapons.*" +
							"  from  weapons " +
							"  where weapons.playerID = ? "
					);
					
					weaponstmt.setInt(1, playerID);
					weaponSet = weaponstmt.executeQuery();
					
					
					while(weaponSet.next()) {
						String name = weaponSet.getString(1);
						int damage = weaponSet.getInt(2);
						int price = weaponSet.getInt(3);
						Weapon weapon = new Weapon(name, damage, price);
						inventory.addItem(weapon);
					}
					
					equipmentstmt = conn.prepareStatement(
							"select equipment.*" +
							"  from  equipment " +
							"  where equipments.playerID = ? "
					);
					
					equipmentstmt.setInt(1, playerID);
					equipmentSet = equipmentstmt.executeQuery();
					
					
					while(equipmentSet.next()) {
						int index=1;
						String name = equipmentSet.getString(index++);
						int price = equipmentSet.getInt(index++);
						int defMod = equipmentSet.getInt(index++);
						int HPMod = equipmentSet.getInt(index++);
						int spdMod = equipmentSet.getInt(index++);
						Equipment equipment = new Equipment(name, price, defMod, HPMod, spdMod);
						inventory.addItem(equipment);
					}
					
					usablestmt = conn.prepareStatement(
							"select usables.*" +
							"  from  usables " +
							"  where usables.playerID = ? "
					);
					
					usablestmt.setInt(1, playerID);
					usableSet = usablestmt.executeQuery();
					
					
					while(usableSet.next()) {
						int index=1;
						String name = usableSet.getString(index++);
						int price = usableSet.getInt(index++);
						Usable usable = new Usable(name, price);
						inventory.addItem(usable);
					}
					
					consumablestmt = conn.prepareStatement(
							"select consumables.*" +
							"  from  consumables " +
							"  where consumables.playerID = ? "
					);
					
					consumablestmt.setInt(1, playerID);
					consumableSet = consumablestmt.executeQuery();
					
					while(consumableSet.next()) {
						int index =1;
						String name = consumableSet.getString(index++);
						int price = consumableSet.getInt(index++);
						int curHPMod = consumableSet.getInt(index++);
						int maxHPMod = consumableSet.getInt(index++);
						int dmgMod = consumableSet.getInt(index++);
						int defMod = consumableSet.getInt(index++);
						int spdMod = consumableSet.getInt(index++);
						
						Consumable consumable = new Consumable(name, price, curHPMod, maxHPMod, dmgMod, defMod, spdMod);
						inventory.addItem(consumable);
					}
					
					treasurestmt = conn.prepareStatement(
							"select treasures.*" +
							"  from  treasures " +
							"  where treasures.playerID = ? "
					);
					
					treasurestmt.setInt(1, playerID);
					treasureSet = treasurestmt.executeQuery();
					
					
					while(treasureSet.next()) {
						int index =1;
						String name = treasureSet.getString(index++);
						int price = treasureSet.getInt(index++);
						int hpMod = treasureSet.getInt(index++);
						int defMod = treasureSet.getInt(index++);
						int spdMod = treasureSet.getInt(index++);
						int dmgMod = treasureSet.getInt(index++);
						
						Treasure treasure = new Treasure(name, price, hpMod, defMod, spdMod, dmgMod);
						inventory.addItem(treasure);
					}
					
					trophystmt = conn.prepareStatement(
							"select trophies.*" +
							"  from  trophies " +
							"  where trophies.playerID = ? "
					);
					
					trophystmt.setInt(1, playerID);
					trophySet = trophystmt.executeQuery();
					
					
					while(trophySet.next()) {
						String name = trophySet.getString(1);
						int price = trophySet.getInt(2);
						Trophy trophy = new Trophy(name, price);
						inventory.addItem(trophy);
					}
					
					
					return inventory;
					
					
					
					
				} finally {
					DBUtil.closeQuietly(weaponSet);
					DBUtil.closeQuietly(equipmentSet);
					DBUtil.closeQuietly(usableSet);
					DBUtil.closeQuietly(consumableSet);
					DBUtil.closeQuietly(trophySet);
					DBUtil.closeQuietly(treasureSet);
					DBUtil.closeQuietly(weaponstmt);
					DBUtil.closeQuietly(equipmentstmt);
					DBUtil.closeQuietly(usablestmt);
					DBUtil.closeQuietly(consumablestmt);
					DBUtil.closeQuietly(trophystmt);
					DBUtil.closeQuietly(treasurestmt);
				}
			}
		});
	}
		
		public Inventory constructInventoryByNPCID(int npcID) {
			return executeTransaction(new Transaction<Inventory>() {
				@Override
				public Inventory execute(Connection conn) throws SQLException {
					PreparedStatement weaponstmt = null;
					PreparedStatement equipmentstmt = null;
					PreparedStatement usablestmt = null;
					PreparedStatement consumablestmt = null;
					PreparedStatement trophystmt = null;
					PreparedStatement treasurestmt = null;
					Inventory inventory = new Inventory(100);
					
					
					ResultSet weaponSet = null;
					ResultSet equipmentSet = null;
					ResultSet usableSet = null;
					ResultSet consumableSet = null;
					ResultSet trophySet = null;
					ResultSet treasureSet = null;
					
					try {
						weaponstmt = conn.prepareStatement(
								"select weapons.*" +
								"  from  weapons " +
								"  where weapons.npcID = ? "
						);
						
						weaponstmt.setInt(1, npcID);
						weaponSet = weaponstmt.executeQuery();
						
						
						while(weaponSet.next()) {
							String name = weaponSet.getString(1);
							int damage = weaponSet.getInt(2);
							int price = weaponSet.getInt(3);
							Weapon weapon = new Weapon(name, damage, price);
							inventory.addItem(weapon);
						}
						
						equipmentstmt = conn.prepareStatement(
								"select equipment.*" +
								"  from  equipment " +
								"  where equipments.npcID = ? "
						);
						
						equipmentstmt.setInt(1, npcID);
						equipmentSet = equipmentstmt.executeQuery();
						
						
						while(equipmentSet.next()) {
							int index=1;
							String name = equipmentSet.getString(index++);
							int price = equipmentSet.getInt(index++);
							int defMod = equipmentSet.getInt(index++);
							int HPMod = equipmentSet.getInt(index++);
							int spdMod = equipmentSet.getInt(index++);
							Equipment equipment = new Equipment(name, price, defMod, HPMod, spdMod);
							inventory.addItem(equipment);
						}
						
						usablestmt = conn.prepareStatement(
								"select usables.*" +
								"  from  usables " +
								"  where usables.npcID = ? "
						);
						
						usablestmt.setInt(1, npcID);
						usableSet = usablestmt.executeQuery();
						
						
						while(usableSet.next()) {
							int index=1;
							String name = usableSet.getString(index++);
							int price = usableSet.getInt(index++);
							Usable usable = new Usable(name, price);
							inventory.addItem(usable);
						}
						
						consumablestmt = conn.prepareStatement(
								"select consumables.*" +
								"  from  consumables " +
								"  where consumables.npcID = ? "
						);
						
						consumablestmt.setInt(1, npcID);
						consumableSet = consumablestmt.executeQuery();
						
						while(consumableSet.next()) {
							int index =1;
							String name = consumableSet.getString(index++);
							int price = consumableSet.getInt(index++);
							int curHPMod = consumableSet.getInt(index++);
							int maxHPMod = consumableSet.getInt(index++);
							int dmgMod = consumableSet.getInt(index++);
							int defMod = consumableSet.getInt(index++);
							int spdMod = consumableSet.getInt(index++);
							
							Consumable consumable = new Consumable(name, price, curHPMod, maxHPMod, dmgMod, defMod, spdMod);
							inventory.addItem(consumable);
						}
						
						treasurestmt = conn.prepareStatement(
								"select treasures.*" +
								"  from  treasures " +
								"  where treasures.npcID = ? "
						);
						
						treasurestmt.setInt(1, npcID);
						treasureSet = treasurestmt.executeQuery();
						
						
						while(treasureSet.next()) {
							int index =1;
							String name = treasureSet.getString(index++);
							int price = treasureSet.getInt(index++);
							int hpMod = treasureSet.getInt(index++);
							int defMod = treasureSet.getInt(index++);
							int spdMod = treasureSet.getInt(index++);
							int dmgMod = treasureSet.getInt(index++);
							
							Treasure treasure = new Treasure(name, price, hpMod, defMod, spdMod, dmgMod);
							inventory.addItem(treasure);
						}
						
						trophystmt = conn.prepareStatement(
								"select trophies.*" +
								"  from  trophies " +
								"  where trophies.npcID = ? "
						);
						
						trophystmt.setInt(1, npcID);
						trophySet = trophystmt.executeQuery();
						
						
						while(trophySet.next()) {
							String name = trophySet.getString(1);
							int price = trophySet.getInt(2);
							Trophy trophy = new Trophy(name, price);
							inventory.addItem(trophy);
						}
						
						
						return inventory;
						
						
						
						
					} finally {
						DBUtil.closeQuietly(weaponSet);
						DBUtil.closeQuietly(equipmentSet);
						DBUtil.closeQuietly(usableSet);
						DBUtil.closeQuietly(consumableSet);
						DBUtil.closeQuietly(trophySet);
						DBUtil.closeQuietly(treasureSet);
						DBUtil.closeQuietly(weaponstmt);
						DBUtil.closeQuietly(equipmentstmt);
						DBUtil.closeQuietly(usablestmt);
						DBUtil.closeQuietly(consumablestmt);
						DBUtil.closeQuietly(trophystmt);
						DBUtil.closeQuietly(treasurestmt);
					}
				}
			});
		}
			public Inventory constructInventoryByRoomID(int roomID) {
				return executeTransaction(new Transaction<Inventory>() {
					@Override
					public Inventory execute(Connection conn) throws SQLException {
						PreparedStatement weaponstmt = null;
						PreparedStatement equipmentstmt = null;
						PreparedStatement usablestmt = null;
						PreparedStatement consumablestmt = null;
						PreparedStatement trophystmt = null;
						PreparedStatement treasurestmt = null;
						Inventory inventory = new Inventory(100);
						
						
						ResultSet weaponSet = null;
						ResultSet equipmentSet = null;
						ResultSet usableSet = null;
						ResultSet consumableSet = null;
						ResultSet trophySet = null;
						ResultSet treasureSet = null;
						
						try {
							weaponstmt = conn.prepareStatement(
									"select weapons.*" +
									"  from  weapons " +
									"  where weapons.roomID = ? "
							);
							
							weaponstmt.setInt(1, roomID);
							weaponSet = weaponstmt.executeQuery();
							
							
							while(weaponSet.next()) {
								String name = weaponSet.getString(1);
								int damage = weaponSet.getInt(2);
								int price = weaponSet.getInt(3);
								Weapon weapon = new Weapon(name, damage, price);
								inventory.addItem(weapon);
							}
							
							equipmentstmt = conn.prepareStatement(
									"select equipment.*" +
									"  from  equipment " +
									"  where equipments.roomID = ? "
							);
							
							equipmentstmt.setInt(1, roomID);
							equipmentSet = equipmentstmt.executeQuery();
							
							
							while(equipmentSet.next()) {
								int index=1;
								String name = equipmentSet.getString(index++);
								int price = equipmentSet.getInt(index++);
								int defMod = equipmentSet.getInt(index++);
								int HPMod = equipmentSet.getInt(index++);
								int spdMod = equipmentSet.getInt(index++);
								Equipment equipment = new Equipment(name, price, defMod, HPMod, spdMod);
								inventory.addItem(equipment);
							}
							
							usablestmt = conn.prepareStatement(
									"select usables.*" +
									"  from  usables " +
									"  where usables.roomID = ? "
							);
							
							usablestmt.setInt(1, roomID);
							usableSet = usablestmt.executeQuery();
							
							
							while(usableSet.next()) {
								int index=1;
								String name = usableSet.getString(index++);
								int price = usableSet.getInt(index++);
								Usable usable = new Usable(name, price);
								inventory.addItem(usable);
							}
							
							consumablestmt = conn.prepareStatement(
									"select consumables.*" +
									"  from  consumables " +
									"  where consumables.roomID = ? "
							);
							
							consumablestmt.setInt(1, roomID);
							consumableSet = consumablestmt.executeQuery();
							
							while(consumableSet.next()) {
								int index =1;
								String name = consumableSet.getString(index++);
								int price = consumableSet.getInt(index++);
								int curHPMod = consumableSet.getInt(index++);
								int maxHPMod = consumableSet.getInt(index++);
								int dmgMod = consumableSet.getInt(index++);
								int defMod = consumableSet.getInt(index++);
								int spdMod = consumableSet.getInt(index++);
								
								Consumable consumable = new Consumable(name, price, curHPMod, maxHPMod, dmgMod, defMod, spdMod);
								inventory.addItem(consumable);
							}
							
							treasurestmt = conn.prepareStatement(
									"select treasures.*" +
									"  from  treasures " +
									"  where treasures.roomID = ? "
							);
							
							treasurestmt.setInt(1, roomID);
							treasureSet = treasurestmt.executeQuery();
							
							
							while(treasureSet.next()) {
								int index =1;
								String name = treasureSet.getString(index++);
								int price = treasureSet.getInt(index++);
								int hpMod = treasureSet.getInt(index++);
								int defMod = treasureSet.getInt(index++);
								int spdMod = treasureSet.getInt(index++);
								int dmgMod = treasureSet.getInt(index++);
								
								Treasure treasure = new Treasure(name, price, hpMod, defMod, spdMod, dmgMod);
								inventory.addItem(treasure);
							}
							
							trophystmt = conn.prepareStatement(
									"select trophies.*" +
									"  from  trophies " +
									"  where trophies.roomID = ? "
							);
							
							trophystmt.setInt(1, roomID);
							trophySet = trophystmt.executeQuery();
							
							
							while(trophySet.next()) {
								String name = trophySet.getString(1);
								int price = trophySet.getInt(2);
								Trophy trophy = new Trophy(name, price);
								inventory.addItem(trophy);
							}
							
							
							return inventory;
							
							
							
							
						} finally {
							DBUtil.closeQuietly(weaponSet);
							DBUtil.closeQuietly(equipmentSet);
							DBUtil.closeQuietly(usableSet);
							DBUtil.closeQuietly(consumableSet);
							DBUtil.closeQuietly(trophySet);
							DBUtil.closeQuietly(treasureSet);
							DBUtil.closeQuietly(weaponstmt);
							DBUtil.closeQuietly(equipmentstmt);
							DBUtil.closeQuietly(usablestmt);
							DBUtil.closeQuietly(consumablestmt);
							DBUtil.closeQuietly(trophystmt);
							DBUtil.closeQuietly(treasurestmt);
						}
					}
				});
			}
		
		
		
		
	
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
					List<RoomConnection> roomConnectionList;
					
					try {
						roomList = InitialData.getRooms();
					} catch (IOException e) {
						throw new SQLException("Couldn't read initial data", e);
					}

					PreparedStatement insertRoom = null; //PreparedStatement needs to be imported?
					PreparedStatement insertRoomConnections = null;
					
					try {
						// populate Rooms table 
						insertRoomConnections = conn.prepareStatement("insert into rooms (roomName, roomDescripLong, roomDescripShort, roomConnections, roomUseable, roomTreasure, roomTrophy, roomEquipment, roomWeapon, roomActor, roomLevel) values (?,?,?,?,?,?,?,?,?,?,?)");
						for (RoomConnection roomConnection : roomConnectionList) {
							
							insertRoomConnections.setInt(1, roomConnection.getRoomID());
							insertRoomConnections.setInt(2, roomConnection.getNorth());
							insertRoomConnections.setInt(3, roomConnection.getEast());
							insertRoomConnections.setInt(4, roomConnection.getSouth());
							insertRoomConnections.setInt(5, roomConnection.getWest());
							insertRoomConnections.setInt(6, roomConnection.getExit());
							
							insertRoomConnections.addBatch();
						}
						insertRoomConnections.executeBatch();
						
						
						
					} finally {
						DBUtil.closeQuietly(insertRoomConnections);
					}

					try {
						// populate Rooms table 
						insertRoom = conn.prepareStatement("insert into rooms (roomName, roomDescripLong, roomDescripShort, roomConnections, roomUseable, roomTreasure, roomTrophy, roomEquipment, roomWeapon, roomActor, roomLevel) values (?,?,?,?,?,?,?,?,?,?,?)");
						for (Room room : roomList) {
							//insertRoom.setInt(1, room.getRoomID());	// auto-generated primary key, don't insert this.  MAY NEED THIS WHEN LOADING MULTIPLE LEVELS
							insertRoom.setString(2, room.getRoomName());
							insertRoom.setString(3, room.getRoomDescripLong());
							insertRoom.setString(4, room.getRoomDescripShort());
							insertRoom.setInt(5, room.getRoomConnections());
							insertRoom.setInt(6, room.getRoomUseable());
							insertRoom.setInt(7, room.getRoomTreasure());
							insertRoom.setInt(8, room.getRoomTrophy());
							insertRoom.setInt(9, room.getRoomEquipment());
							insertRoom.setInt(10, room.getRoomWeapon());
							insertRoom.setInt(11, room.getRoomActor());
							insertRoom.setInt(12, room.getRoomLevel());
							
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