package cs320.TBAG.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs320.TBAG.database.IDatabase;
import cs320.TBAG.database.DBUtil;
import cs320.TBAG.database.DerbyDatabase;
import cs320.TBAG.database.PersistenceException;
import cs320.TBAG.model.ActorStats;
import cs320.TBAG.model.Consumable;
import cs320.TBAG.model.Equipment;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.NPC;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Room;
import cs320.TBAG.model.RoomConnection;
import cs320.TBAG.model.Treasure;
import cs320.TBAG.model.Trophy;
import cs320.TBAG.model.Usable;
import cs320.TBAG.model.Weapon;

import edu.ycp.cs320.booksdb.persist.DerbyDatabase.Transaction;

import cs320.TBAG.model.Convo.ConversationNode;
import cs320.TBAG.model.Convo.ConversationTree;
import cs320.TBAG.model.Convo.DefaultResponse;
import cs320.TBAG.model.Convo.EndResponse;
import cs320.TBAG.model.InteractableObj.Door;
import cs320.TBAG.model.PuzzleType.KeyPuzzle;


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
						int index =1;
						String name = weaponSet.getString(index++);
						int damage = weaponSet.getInt(index++);
						int price = weaponSet.getInt(index++);
						int playerID = weaponSet.getInt(index++);
						int roomID = weaponSet.getInt(index++);
						int npcID = weaponSet.getInt(index++);
						boolean equipped = weaponSet.getBoolean(index++)
						
						Weapon weapon = new Weapon(name, damage, price, playerID, roomID, npcID, equipped);
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
						int playerID = equipmentSet.getInt(index++);
						int roomID = equipmentSet.getInt(index++);
						int npcID = equipmentSet.getInt(index++);
						boolean equipped = equipmentSet.getBoolean(index++);
						Equipment equipment = new Equipment(name, price, defMod, HPMod, spdMod, playerID, roomID, npcID, equipped);
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
						int playerID = usableSet.getInt(index++);
						int roomID = usableSet.getInt(index++);
						int npcID = usableSet.getInt(index++);
						Usable usable = new Usable(name, price, playerID, roomID,npcID);
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
						int playerID = consumableSet.getInt(index++);
						int roomID = consumableSet.getInt(index++);
						int npcID = consumableSet.getInt(index++);
						
						Consumable consumable = new Consumable(name, price, curHPMod, maxHPMod, dmgMod, defMod, spdMod, playerID, roomID, npcID);
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
						int playerID = treasureSet.getInt(index++);
						int roomID = treasureSet.getInt(index++);
						int npcID = treasureSet.getInt(index++);
						
						Treasure treasure = new Treasure(name, price, playerID, roomID, npcID);
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
						int index =1;
						String name = trophySet.getString(index++);
						int price = trophySet.getInt(index++);
						int playerID = trophySet.getInt(index++);
						int roomID = trophySet.getInt(index++);
						int npcID = trophySet.getInt(index++);
						Trophy trophy = new Trophy(name, price, playerID, roomID, npcID);
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
							int index =1;
							String name = weaponSet.getString(index++);
							int damage = weaponSet.getInt(index++);
							int price = weaponSet.getInt(index++);
							int playerID = weaponSet.getInt(index++);
							int roomID = weaponSet.getInt(index++);
							int npcID = weaponSet.getInt(index++);
							boolean equipped = weaponSet.getBoolean(index++);
							Weapon weapon = new Weapon(name, damage, price, playerID, roomID, npcID, equipped);
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
							int playerID = equipmentSet.getInt(index++);
							int roomID = equipmentSet.getInt(index++);
							int npcID = equipmentSet.getInt(index++);
							boolean equipped = equipmentSet.getBoolean(index++);
							Equipment equipment = new Equipment(name, price, defMod, HPMod, spdMod, playerID, roomID, npcID, equipped);
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
							int playerID = usableSet.getInt(index++);
							int roomID = usableSet.getInt(index++);
							int npcID = usableSet.getInt(index++);
							Usable usable = new Usable(name, price, playerID, roomID,npcID);
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
							int playerID = consumableSet.getInt(index++);
							int roomID = consumableSet.getInt(index++);
							int npcID = consumableSet.getInt(index++);
							
							Consumable consumable = new Consumable(name, price, curHPMod, maxHPMod, dmgMod, defMod, spdMod, playerID, roomID, npcID);
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
							int playerID = treasureSet.getInt(index++);
							int roomID = treasureSet.getInt(index++);
							int npcID = treasureSet.getInt(index++);
							
							Treasure treasure = new Treasure(name, price, playerID, roomID, npcID);
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
							int index =1;
							String name = trophySet.getString(index++);
							int price = trophySet.getInt(index++);
							int playerID = trophySet.getInt(index++);
							int roomID = trophySet.getInt(index++);
							int npcID = trophySet.getInt(index++);
							Trophy trophy = new Trophy(name, price, playerID, roomID, npcID);
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
							int index =1;
							String name = weaponSet.getString(index++);
							int damage = weaponSet.getInt(index++);
							int price = weaponSet.getInt(index++);
							int playerID = weaponSet.getInt(index++);
							int roomID = weaponSet.getInt(index++);
							int npcID = weaponSet.getInt(index++);
							boolean equipped = weaponSet.getBoolean(index++);
							
							Weapon weapon = new Weapon(name, damage, price, playerID, roomID, npcID, equipped);
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
							int playerID = equipmentSet.getInt(index++);
							int roomID = equipmentSet.getInt(index++);
							int npcID = equipmentSet.getInt(index++);
							boolean equipped = equipmentSet.getBoolean(index++);
							Equipment equipment = new Equipment(name, price, defMod, HPMod, spdMod, playerID, roomID, npcID, equipped);
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
							int playerID = usableSet.getInt(index++);
							int roomID = usableSet.getInt(index++);
							int npcID = usableSet.getInt(index++);
							Usable usable = new Usable(name, price, playerID, roomID,npcID);
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
							int playerID = consumableSet.getInt(index++);
							int roomID = consumableSet.getInt(index++);
							int npcID = consumableSet.getInt(index++);
							
							Consumable consumable = new Consumable(name, price, curHPMod, maxHPMod, dmgMod, defMod, spdMod, playerID, roomID, npcID);
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
							int playerID = treasureSet.getInt(index++);
							int roomID = treasureSet.getInt(index++);
							int npcID = treasureSet.getInt(index++);
							
							Treasure treasure = new Treasure(name, price, playerID, roomID, npcID);
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
							int index =1;
							String name = treasureSet.getString(index++);
							int price = treasureSet.getInt(index++);
							int playerID = treasureSet.getInt(index++);
							int roomID = treasureSet.getInt(index++);
							int npcID = treasureSet.getInt(index++);
							Trophy trophy = new Trophy(name, price, playerID, roomID, npcID);
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
		executeTransaction(new Transaction<Boolean>() {
			@SuppressWarnings("resource")
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement equipment = null;
				PreparedStatement weapons = null;
				PreparedStatement usables = null;
				PreparedStatement consumables = null;
				PreparedStatement treasures = null;
				PreparedStatement trophies = null;
				PreparedStatement players = null;
				PreparedStatement rooms = null;
				PreparedStatement npcs = null;
				
				
				try {
					players = conn.prepareStatement(
						"create table players ("
						+ " playerID integer primary key generated always as identity (start with 0, increment by 1),"
						+ "currency integer, level integer)"
						);
					players.executeUpdate();
					
					npcs = conn.prepareStatement(
							"create table npcs ("
							+ "npcID integer primary key generated always as identity (start with 0, increment by 1),"
							+ "name varchar(40), type varchar(40),"
							+ "roomID integer, currency integer, stats integer, aggression integer)"
							);
						npcs.executeUpdate();
					
					rooms = conn.prepareStatement(
						"create table rooms ("
						+ " roomID integer primary key generated always as identity (start with 0, increment by 1),"
						+ "name varchar(40), short varchar (400), long varchar(800),"
						+ "npcID integer constraint npcID references npcs, level integer)"
						);
					rooms.executeUpdate();
					
					
							
							
					equipment = conn.prepareStatement(
						"create table equipment (" +
						"	name varchar(40), price integer, defMod integer, hpMod integer, spdMod integer, " +									
						"	playerID integer," +
						"	roomID integer," +
						"	npcID integer," +
						"	equipped boolean)"
					);	
					equipment.executeUpdate();
					
					weapons = conn.prepareStatement(
							"create table weapons ("
							+ "name varchar(40), price integer, damage integer, playerID integer ,"
							+ "roomID integer, "
							+ "npcID integer , equipped boolean)"
					);
					weapons.executeUpdate();
					
					usables = conn.prepareStatement(
							"create table usables ("
							+ "name varchar(40), price integer, playerID integer ,"
							+ "roomID integer, "
							+ "npcID integer )"
					);
					usables.executeUpdate();
					
					consumables = conn.prepareStatement(
							"create table consumables ("
							+ "name varchar(40), price integer, curHPMod integer, maxHPMod integer, dmgMod integer, defMod integer, spdMod integer,"
							+ "playerID integer,"
							+ "roomID integer,"
							+ "npcID integer)"
					);
					consumables.executeUpdate();
					
					treasures = conn.prepareStatement(
							"create table treasures ("
							+ "name varchar(40), price integer, playerID integer,"
							+ "roomID integer,"
							+ "npcID integer)"
					);
					treasures.executeUpdate();
					
					trophies = conn.prepareStatement(
							"create table trophies ("
							+ "name varchar(40), price integer,playerID integer,"
							+ "roomID integer, "
							+ "npcID integer )"
					);
					trophies.executeUpdate();
					
					return true;
				} finally {
					//DBUtil.closeQuietly(stmt1);
					//DBUtil.closeQuietly(stmt2);
				}
			}
		});
		
	}
	
	//Populates the tables (Look at lab06)
	public void loadInitialData() {
			
		executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					List<Room> roomList;
					List<RoomConnection> roomConnectionList = new ArrayList<RoomConnection>();
					List<Weapon> weaponList;
					List<Equipment> equipmentList;
					List<Usable> usableList;
					List<Consumable> consumableList;
					List<Treasure> treasureList;
					List<Trophy> trophyList;
					
					try {

						
						//roomList = InitialData.getRooms();
						weaponList = InitialData.getWeapons();
						equipmentList = InitialData.getEquipment();
						usableList = InitialData.getUsables();
						consumableList = InitialData.getConsumables();
						treasureList = InitialData.getTreasures();
						trophyList = InitialData.getTrophies();
						

						roomList = InitialData.getRooms();
						roomConnectionList = InitialData.getRoomConnections();

					} catch (IOException e) {
						throw new SQLException("Couldn't read initial data", e);
					}

					PreparedStatement insertRoom = null; //PreparedStatement needs to be imported?
					PreparedStatement insertRoomConnections = null;
					PreparedStatement insertWeapon;
					PreparedStatement insertEquipment;
					PreparedStatement insertUsable;
					PreparedStatement insertConsumable;
					PreparedStatement insertTreasure;
					PreparedStatement insertTrophy;
					
					
					try {
						/*// populate Rooms table 
						insertRoomConnections = conn.prepareStatement("insert into rooms (roomID, North, East, South, West, exit) values (?,?,?,?,?,?)");
						for (RoomConnection roomConnection : roomConnectionList) {
							
							insertRoomConnections.setInt(1, roomConnection.getRoomID());
							insertRoomConnections.setInt(2, roomConnection.getNorth());
							insertRoomConnections.setInt(3, roomConnection.getEast());
							insertRoomConnections.setInt(4, roomConnection.getSouth());
							insertRoomConnections.setInt(5, roomConnection.getWest());
							insertRoomConnections.setInt(6, roomConnection.getExit());
							
							insertRoomConnections.addBatch();
						}
						insertRoomConnections.executeBatch();*/
						
						insertWeapon = conn.prepareStatement("insert into weapons (name, price, damage, playerID, roomID, npcID, equipped) values (?,?,?,?,?,?,?)");
						for( Weapon weapon : weaponList) {
							insertWeapon.setString(1, weapon.getName());
							insertWeapon.setInt(2, weapon.getPrice());
							insertWeapon.setInt(3, weapon.getDamage());
							insertWeapon.setInt(4, weapon.getPlayerID());
							insertWeapon.setInt(5, weapon.getRoomID());
							insertWeapon.setInt(6, weapon.getNPCID());
							insertWeapon.setBoolean(7, weapon.getEquipped());
							
							insertWeapon.addBatch();
						}
						insertWeapon.executeBatch();
						
						System.out.println("Weapons table populated");
						
						insertEquipment = conn.prepareStatement("insert into equipment (name, price, defMod, hpMod, spdMod, playerId, roomID, npcID, equipped) values (?,?,?,?,?,?,?,?,?)");
						for(Equipment equipment : equipmentList) {
							insertEquipment.setString(1, equipment.getName());
							insertEquipment.setInt(2, equipment.getPrice());
							insertEquipment.setInt(3, equipment.getDefenseMod());
							insertEquipment.setInt(4, equipment.getHPMod());
							insertEquipment.setInt(5, equipment.getSpeedMod());
							insertEquipment.setInt(6, equipment.getPlayerID());
							insertEquipment.setInt(7, equipment.getRoomID());
							insertEquipment.setInt(8, equipment.getNPCID());
							insertEquipment.setBoolean(9, equipment.getEquipped());
							
							insertEquipment.addBatch();
						}
						insertEquipment.executeBatch();
						
						insertUsable = conn.prepareStatement("insert into usables (name, price, playerId, roomID, npcID) values (?,?,?,?,?)");
						for(Usable usables : usableList) {
							insertUsable.setString(1, usables.getName());
							insertUsable.setInt(2, usables.getPrice());
							insertUsable.setInt(3, usables.getPlayerID());
							insertUsable.setInt(4, usables.getRoomID());
							insertUsable.setInt(5, usables.getNPCID());
							
							insertUsable.addBatch();
						}
						insertUsable.executeBatch();
						
						insertConsumable = conn.prepareStatement("insert into Consumables (name, price, curhpMod, maxhpMod, dmgMod, defmod, spdmod, playerId, roomID, npcID) values (?,?,?,?,?,?,?,?,?,?)");
						for(Consumable consumable : consumableList) {
							insertConsumable.setString(1, consumable.getName());
							insertConsumable.setInt(2, consumable.getBuyPrice());
							insertConsumable.setInt(3, consumable.getCurHPMod());
							insertConsumable.setInt(4, consumable.getMaxHPMod());
							insertConsumable.setInt(5, consumable.getdmgMod());
							insertConsumable.setInt(6, consumable.getdefMod());
							insertConsumable.setInt(7, consumable.getspdMod());
							insertConsumable.setInt(8, consumable.getPlayerID());
							insertConsumable.setInt(9, consumable.getRoomID());
							insertConsumable.setInt(10, consumable.getNPCID());
							
							insertConsumable.addBatch();
						}
						insertConsumable.executeBatch();
						
						insertTreasure = conn.prepareStatement("insert into Treasures (name, price, playerId, roomID, npcID) values (?,?,?,?,?)");
						for(Treasure treasures : treasureList) {
							insertTreasure.setString(1, treasures.getName());
							insertTreasure.setInt(2, treasures.getBuyPrice());
							insertTreasure.setInt(3, treasures.getPlayerID());
							insertTreasure.setInt(4, treasures.getRoomID());
							insertTreasure.setInt(5, treasures.getNPCID());
							
							insertTreasure.addBatch();
						}
						insertTreasure.executeBatch();
						
						insertTrophy = conn.prepareStatement("insert into Trophies (name, price, playerId, roomID, npcID) values (?,?,?,?,?)");
						for(Trophy trophies : trophyList) {
							insertTrophy.setString(1, trophies.getName());
							insertTrophy.setInt(2, trophies.getBuyPrice());
							insertTrophy.setInt(3, trophies.getPlayerID());
							insertTrophy.setInt(4, trophies.getRoomID());
							insertTrophy.setInt(5, trophies.getNPCID());
							
							insertTrophy.addBatch();
						}
						insertTrophy.executeBatch();
						
						
					} finally {
						//DBUtil.closeQuietly(insertRoomConnections);
					}

					/*try {
						// populate Rooms table 
						insertRoom = conn.prepareStatement("insert into rooms (roomID,roomName, roomDescripLong, roomDescripShort, roomConnections, roomUseable, roomTreasure, roomTrophy, roomEquipment, roomWeapon, roomActor, roomLevel) values (?,?,?,?,?,?,?,?,?,?,?,?)");
						for (Room room : roomList) {
							insertRoom.setInt(1, room.getRoomID());	// auto-generated primary key, don't insert this.  MAY NEED THIS WHEN LOADING MULTIPLE LEVELS
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
					}*/
					
					return true;
					
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

	@Override
	public List<Player> findAllPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NPC> findAllNPCs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActorStats> findAllActorStats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConversationTree findConvoTreeByNPCId(int NPCId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConversationNode> findConvoNodesByConvoTreeId(int conversationTreeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultResponse> findDefaultResponsesByNodeIdAndConvoTreeId(int nodeId, int conversationTreeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EndResponse> findEndResponsesByNodeIdAndConvoTreeId(int nodeId, int conversationTreeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeyPuzzle> findAllKeyPuzzles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Door> findAllDoors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Door> findDoorsByRoomId(int roomId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActorStats findActorStatsByPlayerId(int playerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActorStats findActorStatsByNPCId(int npcId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room getRoomByID(int roomID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoomConnection getRoomConnectionByID(int roomID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*--------------------- IDatabase Methods ------------------------*/
	
	
	
	
	
	
	
	
	
	
	
	/*----------------------------------------------------------------*/
}