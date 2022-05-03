package cs320.TBAG.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

//import edu.ycp.cs320.booksdb.persist.DerbyDatabase.Transaction;

import cs320.TBAG.model.Convo.ConversationNode;
import cs320.TBAG.model.Convo.ConversationResponse;
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
						boolean equipped = weaponSet.getBoolean(index++);
						
						Weapon weapon = new Weapon(name, damage, price, playerID, roomID, npcID, equipped);
						inventory.addItem(weapon);
					}
					
					equipmentstmt = conn.prepareStatement(
							"select equipment.*" +
							"  from  equipment " +
							"  where equipment.playerID = ? "
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
		
		public boolean insertAccount(String username, String password) {
			return executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement account = null;
					
					
					try {
						account = conn.prepareStatement(
								"insert into accounts (username, password) values (?,?)");
						
						account.setString(1, username);
						account.setString(2, password);
						
						account.executeUpdate();
						
						return true;
					} 
						
						finally {
						//DBUtil.closeQuietly(resultSet);
						//DBUtil.closeQuietly(stmt);
							DBUtil.closeQuietly(account);
					}
					
				}
			});
		}
		
		public String selectAccountFromUsername(String username) {
			return executeTransaction(new Transaction<String>() {
				@Override
				public String execute(Connection conn) throws SQLException {
					PreparedStatement account = null;
					ResultSet pass = null;
					String password;
					
					try {
						account = conn.prepareStatement(
								"select accounts.password from accounts where accounts.username = ?");
						
						account.setString(1, username);
						
						pass = account.executeQuery();
						
						pass.next();
						password = pass.getString(1);
						
						return password;
						
						
					} 
						
						finally {
						//DBUtil.closeQuietly(resultSet);
						//DBUtil.closeQuietly(stmt);
							DBUtil.closeQuietly(account);
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
				PreparedStatement connections = null;
				PreparedStatement accounts = null;
				PreparedStatement convoTree = null;
				PreparedStatement convoNode = null;
				PreparedStatement defaultResp = null;
				PreparedStatement endResp = null;
				
				
				try {
					players = conn.prepareStatement(
						"create table players ("
						+ " playerID integer primary key generated always as identity (start with 0, increment by 1),"
						+ "currency integer, level integer)"
						);
					players.executeUpdate();
					
					accounts = conn.prepareStatement(
							"create table accounts ("
							+ "playerID integer primary key generated always as identity (start with 0, increment by 1),"
							+ "username varchar(30), password varchar(30))"
							
							);
					accounts.executeUpdate();
							
					
					npcs = conn.prepareStatement(
							"create table npcs ("
							+ "npcID integer primary key generated always as identity (start with 0, increment by 1),"
							+ "name varchar(40), type varchar(40),"
							+ "roomID integer, currency integer, stats integer, aggression integer)"
							);
						npcs.executeUpdate();
					
					rooms = conn.prepareStatement(
						"create table rooms ("
						+ " roomID integer ,"
						+ "roomname varchar(40), short varchar (400), long varchar(800),"
						+ " level integer)"
						);
					rooms.executeUpdate();
					
					connections = conn.prepareStatement(
							"create table connections ("
							+ "roomID integer,"
							+ "north integer, east integer, south integer, west integer, exit integer)"
							);
					connections.executeUpdate();
					
							
							
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
					
					convoTree = conn.prepareStatement(
							"create table conversationTree ("
							+ "convoTreeID integer, npcID integer)"
					);
					convoTree.executeUpdate();
					
					convoNode = conn.prepareStatement(
							"create table conversationNode ("
							+ "convoNodeID integer, convoTreeID integer,"
							+ "convoNodeKey integer, statement varchar(100))"
					);
					convoNode.executeUpdate();
					
					defaultResp = conn.prepareStatement(
							"create table defaultResponse ("
							+ "defaultResponseID integer, convoTreeID integer,"
							+ "convoNodeID integer, response varchar(100), "
							+ "resultNodeID integer)"
					);
					defaultResp.executeUpdate();
					
					endResp = conn.prepareStatement(
							"create table endResponse ("
							+ "endResponseID integer, convoTreeID integer, "
							+ "convoNodeID integer, response varchar(100), "
							+ "resultNodeID integer)"
					);
					endResp.executeUpdate();	
					
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
					List<ConversationTree> convoTreeList;
					List<ConversationNode> convoNodeList;
					List<DefaultResponse> defaultRespList;
					List<EndResponse> endRespList;
					
					try {

						
						//roomList = InitialData.getRooms();
						weaponList = InitialData.getWeapons();
						equipmentList = InitialData.getEquipment();
						usableList = InitialData.getUsables();
						consumableList = InitialData.getConsumables();
						treasureList = InitialData.getTreasures();
						trophyList = InitialData.getTrophies();
						convoTreeList = InitialData.getConversationTrees();
						convoNodeList = InitialData.getConversationNodes();
						defaultRespList = InitialData.getDefaultResponses();
						endRespList = InitialData.getEndResponses();
						
						
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
					PreparedStatement insertAccount;
					PreparedStatement insertConvoTree = null;
					PreparedStatement insertConvoNode = null;
					PreparedStatement insertDefaultResp = null;
					PreparedStatement insertEndResp = null;
					
					
					try {
						// populate Rooms table 
						/*insertRoom = conn.prepareStatement("insert into rooms (roomname, short, long, level) values(?,?,?,?)");
						for(Room room : roomList) {
							insertRoom.setString(1, room.getRoomName());
							insertRoom.setString(2, room.getRoomDescripShort());
							insertRoom.setString(3, room.getRoomDescripLong());
							insertRoom.setInt(4, room.getRoomLevel());
							
							insertRoom.addBatch();
						}
						
						insertRoom.executeBatch();*/
						
						insertRoomConnections = conn.prepareStatement("insert into connections (roomID, North, East, South, West, exit) values (?,?,?,?,?,?)");
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
						
						insertAccount = conn.prepareStatement("insert into accounts (username, password) values('username','password')");
						insertAccount.executeUpdate();
						
						
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

					try {
						// populate Rooms table 
						insertRoom = conn.prepareStatement("insert into rooms (roomID,roomName, short, long, Level) values (?,?,?,?,?)");
						for (Room room : roomList) {
							insertRoom.setInt(1, room.getRoomID());	// auto-generated primary key, don't insert this.  MAY NEED THIS WHEN LOADING MULTIPLE LEVELS
							insertRoom.setString(2, room.getRoomName());
							insertRoom.setString(3, room.getRoomDescripShort());
							insertRoom.setString(4, room.getRoomDescripLong());
							insertRoom.setInt(5, room.getRoomLevel());
							
							insertRoom.addBatch();
						}
						insertRoom.executeBatch();
						
						
						//return true;
					} finally {
						DBUtil.closeQuietly(insertRoom);
					}
					
					try {
						insertConvoTree = conn.prepareStatement("insert into conversationTree (convoTreeID, npcID) values (?, ?)");
						for (ConversationTree convoTree : convoTreeList) {
							insertConvoTree.setInt(1, convoTree.getConvoTreeId());
							insertConvoTree.setInt(2, convoTree.getNPCIdId());
							
							insertConvoTree.addBatch();
						}
						insertConvoTree.executeBatch();
						
						//return true;
					} finally {
						DBUtil.closeQuietly(insertConvoTree);
					}
					
					
					try {
						insertConvoNode = conn.prepareStatement("insert into conversationNode (convoNodeID, convoTreeID, convoNodeKey, statement)"
								+ "values (?, ?, ?, ?)");
						for (ConversationNode convoNode : convoNodeList) {
							insertConvoNode.setInt(1,  convoNode.getConvoNodeId());
							insertConvoNode.setInt(2, convoNode.getConvoTreeId());
							insertConvoNode.setInt(3, convoNode.getConvoNodeKey());
							insertConvoNode.setString(4, convoNode.getStatement());
							
							insertConvoNode.addBatch();
						}
						insertConvoNode.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertConvoNode);
					}
					//return true;
					
					try {
						insertDefaultResp = conn.prepareStatement("insert into defaultResponse (defaultResponseID, convoTreeID, convoNodeID, response, resultNodeID)"
								+ "values (?, ?, ?, ?, ?)");
						for(DefaultResponse defaultResp : defaultRespList) {
							insertDefaultResp.setInt(1, defaultResp.getResponseId());
							insertDefaultResp.setInt(2, defaultResp.getConvoTreeId());
							insertDefaultResp.setInt(3, defaultResp.getNodeId());
							insertDefaultResp.setString(4, defaultResp.getResponseStr());
							insertDefaultResp.setInt(5, defaultResp.getResultNode());
						}
					} finally {
						DBUtil.closeQuietly(insertDefaultResp);
					}
					
					try {
						insertEndResp = conn.prepareStatement("insert into endResponse (endResponseID, convoTreeID, convoNodeID, response, resultNodeID)"
								+ "values (?, ?, ?, ?, ?)");
						for(EndResponse endResp : endRespList) {
							insertEndResp.setInt(1, endResp.getResponseId());
							insertEndResp.setInt(2, endResp.getConvoTreeId());
							insertEndResp.setInt(3, endResp.getNodeId());
							insertEndResp.setString(4, endResp.getResponseStr());
							insertEndResp.setInt(5, endResp.getResultNode());
						}
					} finally {
						DBUtil.closeQuietly(insertEndResp);
					}
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
		
		return executeTransaction(new Transaction<Room>() {
			@Override
			public Room execute(Connection conn) throws SQLException {
				PreparedStatement roomstmt = null;
				ResultSet roomSet = null;
				Room room = new Room();
				
				try {
					roomstmt = conn.prepareStatement(
							"select room.*"
							+ "from room"
							+ "where room.roomID = ?"
							);	
				
					roomstmt.setInt(1, roomID);
					
					roomSet = roomstmt.executeQuery();
					
					while(roomSet.next()) {
						
						room.setRoomName(roomSet.getString(2));
						room.setRoomDescripLong(roomSet.getString(4));
					
					}
					
						return room;
				} 
					
					finally {
					//DBUtil.closeQuietly(resultSet);
					//DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(roomstmt);
						DBUtil.closeQuietly(roomSet);
				}
				
			}
		});
	}
	
	public ArrayList<Room> getRooms(){
		return executeTransaction(new Transaction<ArrayList<Room>>() {
			@Override
			public ArrayList<Room> execute(Connection conn) throws SQLException {
				PreparedStatement roomstmt = null;
				ResultSet roomSet = null;
				ArrayList<Room> roomList = new ArrayList<Room>();
				
				
				try {
					roomstmt = conn.prepareStatement(
							"select rooms.* from rooms");
							
					
					roomSet = roomstmt.executeQuery();
					
					while(roomSet.next()) {
						Room room = new Room();
						room.setRoomID(roomSet.getInt(1));
						room.setRoomName(roomSet.getString(2));
						room.setRoomDescripShort(roomSet.getString(3));
						room.setRoomDescripLong(roomSet.getString(4));
						room.setRoomLevel(roomSet.getInt(5));
						room.setRoomPrevVisit(roomSet.getBoolean(6));
						room.setRoomGameID(roomSet.getInt(7));
						//room.setRoomInv(roomSet.getInventory(8));
						
						roomList.add(room);
						
					}
					
						return roomList;
				} 
					
					finally {
					//DBUtil.closeQuietly(resultSet);
					//DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(roomstmt);
						DBUtil.closeQuietly(roomSet);
				}
				
			}
		});
	}

	@Override
	public RoomConnection getRoomConnectionByID(int roomID) {
		return executeTransaction(new Transaction<RoomConnection>() {
			@Override
			public RoomConnection execute(Connection conn) throws SQLException {
				PreparedStatement roomCon = null;
				ResultSet roomConSet = null;
				RoomConnection connection = new RoomConnection();
				connection.setRoomID(roomID);
				
					
				
				try {
					roomCon = conn.prepareStatement(
							"select connections.*"
							+ "from connections"
							+ "where connections.roomID = ?"
							);	
				
					roomCon.setInt(1, roomID);
					
					roomConSet = roomCon.executeQuery();
					
					while(roomConSet.next()) {
						int index=1;
						connection.setRoomID(roomConSet.getInt(index++));
						connection.setNorth(roomConSet.getInt(index++));
						connection.setEast(roomConSet.getInt(index++));
						connection.setSouth(roomConSet.getInt(index++));
						connection.setWest(roomConSet.getInt(index++));
						connection.setExit(roomConSet.getInt(index++));
					
					}
					return connection;
				} 
					
					finally {
					//DBUtil.closeQuietly(resultSet);
					//DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(roomCon);
						DBUtil.closeQuietly(roomConSet);
				}
				
			}
		});
	}
	public ArrayList<RoomConnection> getConnections(){
		return executeTransaction(new Transaction<ArrayList<RoomConnection>>() {
			@Override
			public ArrayList<RoomConnection> execute(Connection conn) throws SQLException {
				PreparedStatement roomstmt = null;
				ResultSet roomSet = null;
				ArrayList<RoomConnection> connectionList = new ArrayList<RoomConnection>();
				
				
				try {
					roomstmt = conn.prepareStatement(
							"select connections.* from connections");
							
					
					roomSet = roomstmt.executeQuery();
					int i=1;
					while(roomSet.next()) {
						RoomConnection connection = new RoomConnection();
						connection.setRoomID(roomSet.getInt(1));
						connection.setNorth(roomSet.getInt(2));
						connection.setEast(roomSet.getInt(3));
						connection.setSouth(roomSet.getInt(4));
						connection.setWest(roomSet.getInt(5));
						connection.setExit(roomSet.getInt(6));
						
						connectionList.add(connection);
						
					}
					
						return connectionList;
				} 
					
					finally {
					//DBUtil.closeQuietly(resultSet);
					//DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(roomstmt);
						DBUtil.closeQuietly(roomSet);
				}
				
			}
		});
	}

	@Override
	public ConversationTree constructConversationTreeByNPCID(int npcID) {
		// TODO Auto-generated method stub
		return executeTransaction(new Transaction<ConversationTree>() {
			@Override
			public ConversationTree execute(Connection conn) throws SQLException {
				PreparedStatement treeStmt = null;
				PreparedStatement nodeStmt = null;
				PreparedStatement defaultRespStmt = null;
				PreparedStatement endRespStmt = null;
				ResultSet treeIDSet = null;
				ResultSet nodeSet = null;
				ResultSet defaultRespSet = null;
				ResultSet endRespSet = null;
				ConversationTree convoTree = null;
				
				
				try {
					treeStmt = conn.prepareStatement(
							"select convoTreeID from conversationTree"
							+ "where conversationTree.npcID = ?");
					treeStmt.setInt(1, npcID);
					
					treeIDSet = treeStmt.executeQuery();
					
					while(treeIDSet.next()) {
						TreeMap<Integer, ConversationNode> conversationTreeMap = new TreeMap<Integer, ConversationNode>();
						int index = 1;
						int treeID = treeIDSet.getInt(index++);
						
						nodeStmt = conn.prepareStatement(
							"select convoNodeID, convoNodeKey, statement from conversationNode"
							+ "where conversationNode.convoTreeID = ?");
						nodeStmt.setInt(1, treeID);
						
						nodeSet = nodeStmt.executeQuery();
						
						while(nodeSet.next()) {
							ArrayList<ConversationResponse> respList = new ArrayList<ConversationResponse>();
							int nodeIndex = 1;
							int convoNodeID = nodeSet.getInt(nodeIndex++);
							int convoNodeKey = nodeSet.getInt(nodeIndex++);
							String statement = nodeSet.getString(nodeIndex++);
							
							defaultRespStmt = conn.prepareStatement(
								"select response, resultNodeID from defaultResponse"
								+ "where defaultResponse.convoNodeID = ?");
							defaultRespStmt.setInt(1, convoNodeID);	
							
							defaultRespSet = defaultRespStmt.executeQuery();
							while(defaultRespSet.next()) {
								int defaultIndex = 1;
								String response = defaultRespSet.getString(defaultIndex++);
								int resultNodeID = defaultRespSet.getInt(defaultIndex++);
								
								DefaultResponse resp = new DefaultResponse(response, resultNodeID);
								resp.setConvoTreeId(treeID);
								resp.setNodeId(convoNodeID);
								respList.add(resp);
							}
							
							endRespStmt = conn.prepareStatement(
									"select response, resultNodeID from endResponse"
									+ "where endResponse.convoNodeID = ?");
								endRespStmt.setInt(1, convoNodeID);	
								
								endRespSet = defaultRespStmt.executeQuery();
								while(endRespSet.next()) {
									int endIndex = 1;
									String response = endRespSet.getString(endIndex++);
									int resultNodeID = endRespSet.getInt(endIndex++);
									
									EndResponse resp = new EndResponse(response);
									resp.setResultNode(resultNodeID);
									resp.setConvoTreeId(treeID);
									resp.setNodeId(convoNodeID);
									respList.add(resp);
								}
							
							ConversationNode convoNode = new ConversationNode(statement, respList);
							convoNode.setConvoTreeId(treeID);
							convoNode.setConvoNodeKey(convoNodeKey);
							conversationTreeMap.put(convoNodeKey, convoNode);
						}
						convoTree = new ConversationTree(conversationTreeMap);
						
					}
					//return true;
					return convoTree;
				} 
					
					finally {
						DBUtil.closeQuietly(treeStmt);
						DBUtil.closeQuietly(nodeStmt);
						DBUtil.closeQuietly(defaultRespStmt);
				}
				//return null;
			}
		});
	}
	
	
	public String constructDescripByRoomID(int ID) {
		
		Room room = getRoomByID(ID);
		String descrip;
		boolean prevVisitCheck = room.getRoomPrevVisit();
		
		if (prevVisitCheck = true) {
			descrip = room.getRoomDescripShort();
			return descrip;
		}
		
		descrip = room.getRoomDescripLong();
		return descrip;
	}
}
	
	/*--------------------- IDatabase Methods ------------------------*/
	
	
	
	
	
	
	
	
	
	
	/*----------------------------------------------------------------*/
