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
import cs320.TBAG.model.Convo.BuyResponse;

//import edu.ycp.cs320.booksdb.persist.DerbyDatabase.Transaction;

import cs320.TBAG.model.Convo.ConversationNode;
import cs320.TBAG.model.Convo.ConversationResponse;
import cs320.TBAG.model.Convo.ConversationTree;
import cs320.TBAG.model.Convo.DefaultResponse;
import cs320.TBAG.model.Convo.EndResponse;
import cs320.TBAG.model.Convo.PuzzleResponse;
import cs320.TBAG.model.Convo.RewardResponse;
import cs320.TBAG.model.Convo.SellResponse;
import cs320.TBAG.model.InteractableObj.Chest;
import cs320.TBAG.model.InteractableObj.Door;
import cs320.TBAG.model.InteractableObj.Interactable;
import cs320.TBAG.model.InteractableObj.Keypad;
import cs320.TBAG.model.InteractableObj.Sign;
import cs320.TBAG.model.PuzzleType.EnemyPuzzle;
import cs320.TBAG.model.PuzzleType.KeyPuzzle;
import cs320.TBAG.model.PuzzleType.PinPuzzle;
import cs320.TBAG.model.PuzzleType.Puzzle;


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
						int itemID = weaponSet.getInt(index++);
						String name = weaponSet.getString(index++);
						int damage = weaponSet.getInt(index++);
						int price = weaponSet.getInt(index++);
						int playerID = weaponSet.getInt(index++);
						int roomID = weaponSet.getInt(index++);
						int npcID = weaponSet.getInt(index++);
						boolean equipped = weaponSet.getBoolean(index++);
						
						Weapon weapon = new Weapon(itemID, name, damage, price, playerID, roomID, npcID, equipped);
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
						int itemID = equipmentSet.getInt(index++);
						String name = equipmentSet.getString(index++);
						int price = equipmentSet.getInt(index++);
						int defMod = equipmentSet.getInt(index++);
						int HPMod = equipmentSet.getInt(index++);
						int spdMod = equipmentSet.getInt(index++);
						int playerID = equipmentSet.getInt(index++);
						int roomID = equipmentSet.getInt(index++);
						int npcID = equipmentSet.getInt(index++);
						boolean equipped = equipmentSet.getBoolean(index++);
						Equipment equipment = new Equipment(itemID, name, price, defMod, HPMod, spdMod, playerID, roomID, npcID, equipped);
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
						int itemID = usableSet.getInt(index++);
						String name = usableSet.getString(index++);
						int price = usableSet.getInt(index++);
						int playerID = usableSet.getInt(index++);
						int roomID = usableSet.getInt(index++);
						int npcID = usableSet.getInt(index++);
						Usable usable = new Usable(itemID, name, price, playerID, roomID,npcID);
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
						int itemID = consumableSet.getInt(index++);
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
						
						Consumable consumable = new Consumable(itemID, name, price, curHPMod, maxHPMod, dmgMod, defMod, spdMod, playerID, roomID, npcID);
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
						int itemID = treasureSet.getInt(index++);
						String name = treasureSet.getString(index++);
						int price = treasureSet.getInt(index++);
						int playerID = treasureSet.getInt(index++);
						int roomID = treasureSet.getInt(index++);
						int npcID = treasureSet.getInt(index++);
						
						Treasure treasure = new Treasure(itemID, name, price, playerID, roomID, npcID);
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
						int itemID = trophySet.getInt(index++);
						String name = trophySet.getString(index++);
						int price = trophySet.getInt(index++);
						int playerID = trophySet.getInt(index++);
						int roomID = trophySet.getInt(index++);
						int npcID = trophySet.getInt(index++);
						Trophy trophy = new Trophy(itemID, name, price, playerID, roomID, npcID);
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
							int itemID = weaponSet.getInt(index++);
							String name = weaponSet.getString(index++);
							int damage = weaponSet.getInt(index++);
							int price = weaponSet.getInt(index++);
							int playerID = weaponSet.getInt(index++);
							int roomID = weaponSet.getInt(index++);
							int npcID = weaponSet.getInt(index++);
							boolean equipped = weaponSet.getBoolean(index++);
							Weapon weapon = new Weapon(itemID, name, damage, price, playerID, roomID, npcID, equipped);
							inventory.addItem(weapon);
						}
						
						equipmentstmt = conn.prepareStatement(
								"select equipment.*" +
								"  from  equipment " +
								"  where equipment.npcID = ? "
						);
						
						equipmentstmt.setInt(1, npcID);
						equipmentSet = equipmentstmt.executeQuery();
						
						
						while(equipmentSet.next()) {
							int index=1;
							int itemID = equipmentSet.getInt(index++);
							String name = equipmentSet.getString(index++);
							int price = equipmentSet.getInt(index++);
							int defMod = equipmentSet.getInt(index++);
							int HPMod = equipmentSet.getInt(index++);
							int spdMod = equipmentSet.getInt(index++);
							int playerID = equipmentSet.getInt(index++);
							int roomID = equipmentSet.getInt(index++);
							int npcID = equipmentSet.getInt(index++);
							boolean equipped = equipmentSet.getBoolean(index++);
							Equipment equipment = new Equipment(itemID, name, price, defMod, HPMod, spdMod, playerID, roomID, npcID, equipped);
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
							int itemID = usableSet.getInt(index++);
							String name = usableSet.getString(index++);
							int price = usableSet.getInt(index++);
							int playerID = usableSet.getInt(index++);
							int roomID = usableSet.getInt(index++);
							int npcID = usableSet.getInt(index++);
							Usable usable = new Usable(itemID, name, price, playerID, roomID,npcID);
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
							int itemID = consumableSet.getInt(index++);
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
							
							Consumable consumable = new Consumable(itemID, name, price, curHPMod, maxHPMod, dmgMod, defMod, spdMod, playerID, roomID, npcID);
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
							int itemID = treasureSet.getInt(index++);
							String name = treasureSet.getString(index++);
							int price = treasureSet.getInt(index++);
							int playerID = treasureSet.getInt(index++);
							int roomID = treasureSet.getInt(index++);
							int npcID = treasureSet.getInt(index++);
							
							Treasure treasure = new Treasure(itemID, name, price, playerID, roomID, npcID);
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
							int itemID = trophySet.getInt(index++);
							String name = trophySet.getString(index++);
							int price = trophySet.getInt(index++);
							int playerID = trophySet.getInt(index++);
							int roomID = trophySet.getInt(index++);
							int npcID = trophySet.getInt(index++);
							Trophy trophy = new Trophy(itemID, name, price, playerID, roomID, npcID);
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
							int itemID = weaponSet.getInt(index++);
							String name = weaponSet.getString(index++);
							int damage = weaponSet.getInt(index++);
							int price = weaponSet.getInt(index++);
							int playerID = weaponSet.getInt(index++);
							int roomID = weaponSet.getInt(index++);
							int npcID = weaponSet.getInt(index++);
							boolean equipped = weaponSet.getBoolean(index++);
							
							Weapon weapon = new Weapon(itemID, name, damage, price, playerID, roomID, npcID, equipped);
							inventory.addItem(weapon);
						}
						
						equipmentstmt = conn.prepareStatement(
								"select equipment.*" +
								"  from  equipment " +
								"  where equipment.roomID = ? "
						);
						
						equipmentstmt.setInt(1, roomID);
						equipmentSet = equipmentstmt.executeQuery();
						
						
						while(equipmentSet.next()) {
							int index=1;
							int itemID = equipmentSet.getInt(index++);
							String name = equipmentSet.getString(index++);
							int price = equipmentSet.getInt(index++);
							int defMod = equipmentSet.getInt(index++);
							int HPMod = equipmentSet.getInt(index++);
							int spdMod = equipmentSet.getInt(index++);
							int playerID = equipmentSet.getInt(index++);
							int roomID = equipmentSet.getInt(index++);
							int npcID = equipmentSet.getInt(index++);
							boolean equipped = equipmentSet.getBoolean(index++);
							Equipment equipment = new Equipment(itemID, name, price, defMod, HPMod, spdMod, playerID, roomID, npcID, equipped);
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
							int itemID = usableSet.getInt(index++);
							String name = usableSet.getString(index++);
							int price = usableSet.getInt(index++);
							int playerID = usableSet.getInt(index++);
							int roomID = usableSet.getInt(index++);
							int npcID = usableSet.getInt(index++);
							Usable usable = new Usable(itemID, name, price, playerID, roomID,npcID);
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
							int itemID = consumableSet.getInt(index++);
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
							
							Consumable consumable = new Consumable(itemID, name, price, curHPMod, maxHPMod, dmgMod, defMod, spdMod, playerID, roomID, npcID);
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
							int itemID = treasureSet.getInt(index++);
							String name = treasureSet.getString(index++);
							int price = treasureSet.getInt(index++);
							int playerID = treasureSet.getInt(index++);
							int roomID = treasureSet.getInt(index++);
							int npcID = treasureSet.getInt(index++);
							
							Treasure treasure = new Treasure(itemID, name, price, playerID, roomID, npcID);
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
							int itemID = treasureSet.getInt(index++);
							String name = treasureSet.getString(index++);
							int price = treasureSet.getInt(index++);
							int playerID = treasureSet.getInt(index++);
							int roomID = treasureSet.getInt(index++);
							int npcID = treasureSet.getInt(index++);
							Trophy trophy = new Trophy(itemID, name, price, playerID, roomID, npcID);
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
		
		public boolean insertAccount(String username, String password, String salt) {
			return executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement account = null;
					
					
					try {
						account = conn.prepareStatement(
								"insert into accounts (username, password, salt) values (?,?,?)");
						
						account.setString(1, username);
						account.setString(2, password);
						account.setString(3, salt);
						
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
		
		public String selectPasswordFromUsername(String username) {
			return executeTransaction(new Transaction<String>() {
				@Override
				public String execute(Connection conn) throws SQLException {
					PreparedStatement account = null;
					ResultSet pass = null;
					String password = null;
					
					try {
						account = conn.prepareStatement(
								"select accounts.password from accounts where accounts.username = ?");
						
						account.setString(1, username);
						
						pass = account.executeQuery();
						
						if(pass.next()) {
						password = pass.getString(1);
						}
						
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
		
		public String selectSaltFromUsername(String username) {
			return executeTransaction(new Transaction<String>() {
				@Override
				public String execute(Connection conn) throws SQLException {
					PreparedStatement account = null;
					ResultSet pass = null;
					String salt = null;
					
					try {
						account = conn.prepareStatement(
								"select accounts.salt from accounts where accounts.username = ?");
						
						account.setString(1, username);
						
						pass = account.executeQuery();
						
						if(pass.next()) {
						salt = pass.getString(1);
						}
						
						return salt;
						
						
					} 
						
						finally {
						//DBUtil.closeQuietly(resultSet);
						//DBUtil.closeQuietly(stmt);
							DBUtil.closeQuietly(account);
							DBUtil.closeQuietly(pass);
					}
					
				}
			});
		}
		
		public Player createPlayer(String name, int roomID, int currency, int prevRoomID, int level) {
			return executeTransaction(new Transaction<Player>() {
				@Override
				public Player execute(Connection conn) throws SQLException {
					PreparedStatement playerStmt = null;
					PreparedStatement insertActorStats = null;
					PreparedStatement statsIDQ = null;
					ResultSet pass = null;
					String salt = null;
					
					try {
						ActorStats stats = null;
						try {
							stats = InitialData.getActorStats().get(0);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						insertActorStats = conn.prepareStatement("insert into actorStats (curHP, maxHP, dmg, def, spd, curExp, maxExp, curLvl)"
								+ "values (?, ?, ?, ?, ?, ?, ?, ?)");
						//insertActorStats.setInt(1, stats.getStatsId());
						insertActorStats.setInt(1, stats.getCurHP());
						insertActorStats.setInt(2, stats.getMaxHP());
						insertActorStats.setInt(3, stats.getDmg());
						insertActorStats.setInt(4, stats.getDef());
						insertActorStats.setInt(5, stats.getSpd());
						insertActorStats.setInt(6,  stats.getCurExp());
						insertActorStats.setInt(7,  stats.getMaxExp());
						insertActorStats.setInt(8,  stats.getCurLvl());
						
						insertActorStats.executeUpdate();
						
						statsIDQ = conn.prepareStatement("select max(statsID) from actorStats");
						
						pass = statsIDQ.executeQuery();
						pass.next();
						int statsID=pass.getInt(1);
						
						playerStmt = conn.prepareStatement(
								"insert into player (name, roomID, statsID, currency, prevRoomID, level) values (?,?,?,?,?,?");
						
						playerStmt.setString(1, name);
						playerStmt.setInt(2, roomID);
						playerStmt.setInt(3, statsID);
						playerStmt.setInt(4, currency);
						playerStmt.setInt(5, prevRoomID);
						playerStmt.setInt(6, level);
						
						playerStmt.executeUpdate();
						
						Player player = new Player();
						
						if(pass.next()) {
						salt = pass.getString(1);
						}
						
						return player;
						
						
					} 
						
						finally {
						//DBUtil.closeQuietly(resultSet);
						//DBUtil.closeQuietly(stmt);
							DBUtil.closeQuietly(pass);
					}
					
				}
			});
		}
		
		public Boolean updatePlayerInfo(int playerID,String name, int roomID, int statsID, int currency, int prevRoomID, int level) {
			return executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
					ResultSet resultSet = null;
					
					try {
						stmt = conn.prepareStatement(
								"update players set name = ?, roomID = ?, statsID = ?, currency = ?, prevRoomID = ?, level = ?"
								+ "where playerID = ?"
						);
						
						
						int index = 1;
						stmt.setString(index++, name);
						stmt.setInt(index++, roomID);
						stmt.setInt(index++, statsID);
						stmt.setInt(index++, currency);
						stmt.setInt(index++, prevRoomID);
						stmt.setInt(index++, level);
						stmt.setInt(index++, playerID);
						
						stmt.executeUpdate();
						
						
						return true;
					} finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(stmt);
					}
				}
			});
			
		}
		
		public Boolean updateInventories(Player player, ArrayList<NPC> npcs, List<Room> rooms) {
			return executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement weaponstmt = null;
					PreparedStatement equipmentstmt = null;
					PreparedStatement usablestmt = null;
					PreparedStatement treasurestmt = null;
					PreparedStatement trophystmt = null;
					PreparedStatement consumablestmt = null;
					ResultSet resultSet = null;
					int playerID = player.getPlayerId();
					
					try {
						weaponstmt = conn.prepareStatement(
								"update weapons set playerID = ?, roomID = 0, NPCID = 0, equipped = false where weapons.name = ?");
						for(Weapon weapon : player.getInventory().getWeapons().values()) {
							int index = 1;
							weaponstmt.setInt(index++, playerID);
							weaponstmt.setString(index++, weapon.getName());
							
							weaponstmt.addBatch();
							
						}
						weaponstmt.executeBatch();
						
						
						equipmentstmt = conn.prepareStatement(
								"update equipment set playerID = ?, roomID = 0, NPCID = 0, equipped = false where equipment.name = ?");
						for(Equipment equipment : player.getInventory().getEquipment().values()) {
							int index = 1;
							equipmentstmt.setInt(index++, playerID);
							equipmentstmt.setString(index++, equipment.getName());
							
							equipmentstmt.addBatch();
							
						}
						equipmentstmt.executeBatch();
						
						usablestmt = conn.prepareStatement(
								"update usables set playerID = ?, roomID = 0, NPCID = 0 where usables.name = ?");
						for(Usable usable : player.getInventory().getUsables().values()) {
							int index = 1;
							usablestmt.setInt(index++, playerID);
							usablestmt.setString(index++, usable.getName());
							
							usablestmt.addBatch();
							
						}
						usablestmt.executeBatch();
						
						treasurestmt = conn.prepareStatement(
								"update treasures set playerID = ?, roomID = 0, NPCID = 0 where treasures.name = ?");
						for(Treasure treasure : player.getInventory().getTreasures().values()) {
							int index = 1;
							treasurestmt.setInt(index++, playerID);
							treasurestmt.setString(index++, treasure.getName());
							
							treasurestmt.addBatch();
							
						}
						treasurestmt.executeBatch();
						
						trophystmt = conn.prepareStatement(
								"update trophies set playerID = ?, roomID = 0, NPCID = 0 where trophies.name = ?");
						for(Trophy trophy : player.getInventory().getTrophies().values()) {
							int index = 1;
							trophystmt.setInt(index++, playerID);
							trophystmt.setString(index++, trophy.getName());
							
							trophystmt.addBatch();
							
						}
						trophystmt.executeBatch();
						
						consumablestmt = conn.prepareStatement(
								"update consumables set playerID = ?, roomID = 0, NPCID = 0 where consumables.name = ?");
						for(Consumable consumable : player.getInventory().getConsumables().values()) {
							int index = 1;
							consumablestmt.setInt(index++, playerID);
							consumablestmt.setString(index++, consumable.getName());
							
							consumablestmt.addBatch();
							
						}
						consumablestmt.executeBatch();
						
						
						for(NPC npc : npcs) {
							int npcID = npc.getNPCId();
							weaponstmt = conn.prepareStatement(
									"update weapons set npcID = ?, roomID = 0, PLAYERID = 0, equipped = false where weapons.name = ?");
							for(Weapon weapon : npc.getInventory().getWeapons().values()) {
								int index = 1;
								weaponstmt.setInt(index++, npcID);
								weaponstmt.setString(index++, weapon.getName());
								
								weaponstmt.addBatch();
								
							}
							weaponstmt.executeBatch();
							
							
							equipmentstmt = conn.prepareStatement(
									"update equipment set npcID = ?, roomID = 0, PLAYERID = 0, equipped = false where equipment.name = ?");
							for(Equipment equipment : npc.getInventory().getEquipment().values()) {
								int index = 1;
								equipmentstmt.setInt(index++, npcID);
								equipmentstmt.setString(index++, equipment.getName());
								
								equipmentstmt.addBatch();
								
							}
							equipmentstmt.executeBatch();
							
							usablestmt = conn.prepareStatement(
									"update usables set npcID = ?, roomID = 0, PLAYERID = 0 where usables.name = ?");
							for(Usable usable : npc.getInventory().getUsables().values()) {
								int index = 1;
								usablestmt.setInt(index++, npcID);
								usablestmt.setString(index++, usable.getName());
								
								usablestmt.addBatch();
								
							}
							usablestmt.executeBatch();
							
							treasurestmt = conn.prepareStatement(
									"update treasures set npcID = ?, roomID = 0, PLAYERID = 0 where treasures.name = ?");
							for(Treasure treasure : npc.getInventory().getTreasures().values()) {
								int index = 1;
								treasurestmt.setInt(index++, npcID);
								treasurestmt.setString(index++, treasure.getName());
								
								treasurestmt.addBatch();
								
							}
							treasurestmt.executeBatch();
							
							trophystmt = conn.prepareStatement(
									"update trophies set npcID = ?, roomID = 0, PLAYERID = 0 where trophies.name = ?");
							for(Trophy trophy : npc.getInventory().getTrophies().values()) {
								int index = 1;
								trophystmt.setInt(index++, npcID);
								trophystmt.setString(index++, trophy.getName());
								
								trophystmt.addBatch();
								
							}
							trophystmt.executeBatch();
							
							consumablestmt = conn.prepareStatement(
									"update consumables set npcID = ?, roomID = 0, PLAYERID = 0 where consumables.name = ?");
							for(Consumable consumable : npc.getInventory().getConsumables().values()) {
								int index = 1;
								consumablestmt.setInt(index++, npcID);
								consumablestmt.setString(index++, consumable.getName());
								
								consumablestmt.addBatch();
								
							}
							consumablestmt.executeBatch();
						}
						
						for(Room room : rooms) {
							int roomID = room.getRoomID();
							weaponstmt = conn.prepareStatement(
									"update weapons set roomID = ?, NPCID = 0, PLAYERID = 0, equipped = false where weapons.name = ?");
							for(Weapon weapon : room.getRoomInv().getWeapons().values()) {
								int index = 1;
								weaponstmt.setInt(index++, roomID);
								weaponstmt.setString(index++, weapon.getName());
								
								weaponstmt.addBatch();
								
							}
							weaponstmt.executeBatch();
							
							
							equipmentstmt = conn.prepareStatement(
									"update equipment set roomID = ?, NPCID = 0, PLAYERID = 0, equipped = false where equipment.name = ?");
							for(Equipment equipment : room.getRoomInv().getEquipment().values()) {
								int index = 1;
								equipmentstmt.setInt(index++, roomID);
								equipmentstmt.setString(index++, equipment.getName());
								
								equipmentstmt.addBatch();
								
							}
							equipmentstmt.executeBatch();
							
							usablestmt = conn.prepareStatement(
									"update usables set roomID = ?, NPCID = 0, PLAYERID = 0 where usables.name = ?");
							for(Usable usable : room.getRoomInv().getUsables().values()) {
								int index = 1;
								usablestmt.setInt(index++, roomID);
								usablestmt.setString(index++, usable.getName());
								
								usablestmt.addBatch();
								
							}
							usablestmt.executeBatch();
							
							treasurestmt = conn.prepareStatement(
									"update treasures set roomID = ?, NPCID = 0, PLAYERID = 0 where treasures.name = ?");
							for(Treasure treasure : room.getRoomInv().getTreasures().values()) {
								int index = 1;
								treasurestmt.setInt(index++, roomID);
								treasurestmt.setString(index++, treasure.getName());
								
								treasurestmt.addBatch();
								
							}
							treasurestmt.executeBatch();
							
							trophystmt = conn.prepareStatement(
									"update trophies set roomID = ?, NPCID = 0, PLAYERID = 0 where trophies.name = ?");
							for(Trophy trophy : room.getRoomInv().getTrophies().values()) {
								int index = 1;
								trophystmt.setInt(index++, roomID);
								trophystmt.setString(index++, trophy.getName());
								
								trophystmt.addBatch();
								
							}
							trophystmt.executeBatch();
							
							consumablestmt = conn.prepareStatement(
									"update consumables set roomID = ?, NPCID = 0, PLAYERID = 0 where consumables.name = ?");
							for(Consumable consumable : room.getRoomInv().getConsumables().values()) {
								int index = 1;
								consumablestmt.setInt(index++, roomID);
								consumablestmt.setString(index++, consumable.getName());
								
								consumablestmt.addBatch();
								
							}
							consumablestmt.executeBatch();
						}
						
						
						
						
						
						
						return true;
					} finally {
						DBUtil.closeQuietly(resultSet);
					}
				}
			});
			
		}
		
		public Boolean updateInteractables(List<Room> rooms) {
			return executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement doorstmt = null;
					PreparedStatement cheststmt = null;
					PreparedStatement keypadstmt = null;
					PreparedStatement signstmt = null;
					ResultSet resultSet = null;
				
					try {
						for(Room room : rooms) {
							
						
							doorstmt = conn.prepareStatement(
									"update door set activated = ? "
									+ "where door.doorID = ?"
							);
							cheststmt = conn.prepareStatement(
									"update chest set activated = ? "
									+ "where chest.chestID = ?");
							keypadstmt = conn.prepareStatement(
									"update keypad set activated = ? "
									+ "where keypad.keypadID = ?");
							signstmt = conn.prepareStatement(
									"update sign set activated = ? "
									+ "where sign.signID = ?");
							
							
							
							for(Interactable interactable : room.getRoomInteractables()) {
								if(interactable instanceof Door) {
									doorstmt.setBoolean(1, interactable.getActivated());
									doorstmt.setInt(2, ((Door)interactable).getDoorId());
									doorstmt.addBatch();
								}
								if(interactable instanceof Chest) {
									cheststmt.setBoolean(1, interactable.getActivated());
									cheststmt.setInt(2, ((Chest)interactable).getChestId());
									cheststmt.addBatch();
								}
								if(interactable instanceof Keypad) {
									keypadstmt.setBoolean(1, interactable.getActivated());
									keypadstmt.setInt(2, ((Keypad)interactable).getKeypadId());
									keypadstmt.addBatch();
								}
								if(interactable instanceof Sign) {
									signstmt.setBoolean(1, interactable.getActivated());
									signstmt.setInt(2, ((Sign)interactable).getSignId());
									signstmt.addBatch();
								}
	
							}
							doorstmt.executeBatch();
							cheststmt.executeBatch();
							keypadstmt.executeBatch();
							signstmt.executeBatch();
						
						
						}
						return true;
					} finally {
						DBUtil.closeQuietly(resultSet);
					}
				}
			});
			
		}
		
		public Boolean updateActorStats(Player player, ArrayList<NPC> npcs) {
			return executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement playerstmt = null;
					ResultSet resultSet = null;
					
					try {
						/*playerstmt = conn.prepareStatement(
						);
						
						
						int index = 1;
						stmt.setString(index++, name);
						stmt.setInt(index++, roomID);
						stmt.setInt(index++, statsID);
						stmt.setInt(index++, currency);
						stmt.setInt(index++, prevRoomID);
						stmt.setInt(index++, level);
						stmt.setInt(index++, playerID);
						
						stmt.executeUpdate();
						
						*/
						return true;
					} finally {
						DBUtil.closeQuietly(resultSet);
						//DBUtil.closeQuietly(stmt);
					}
				}
			});
			
		}
		
		public Boolean storeHistory(ArrayList<String> history) {
			return executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement delete = null;
					PreparedStatement create = null;
					PreparedStatement insert = null;
					ResultSet resultSet = null;
					
					try {
						delete = conn.prepareStatement("drop table history");
						delete.executeUpdate();
						
						create = conn.prepareStatement("create table history ("
								+ "inputID integer primary key generated always as identity (start with 1, increment by 1),"
								+ "line varchar(400))");
						create.executeUpdate();
						
						insert = conn.prepareStatement("insert into history (line) values(?)");
						
						for(String string : history) {
							insert.setString(1, string);
							insert.addBatch();
						}
						insert.executeBatch();
						return true;
					} finally {
						DBUtil.closeQuietly(resultSet);
						//DBUtil.closeQuietly(stmt);
					}
				}
			});
			
		}
		
		public ArrayList<String> loadHistory() {
			return executeTransaction(new Transaction<ArrayList<String>>() {
				@Override
				public ArrayList<String> execute(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
					ResultSet resultSet = null;
					ArrayList<String> history = new ArrayList<String>();
					
					try {
						stmt = conn.prepareStatement("select history.line from history");
						resultSet = stmt.executeQuery();
						boolean found = false;
						while(resultSet.next()) {
							history.add(resultSet.getString(1));
							found = true;
						}
						
						if(!found) {
							return null;
						}
						else {
							return history;
						}
					} finally {
						DBUtil.closeQuietly(resultSet);
						//DBUtil.closeQuietly(stmt);
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
				PreparedStatement actorStats = null;
				PreparedStatement connections = null;
				PreparedStatement accounts = null;
				PreparedStatement accountPlayers = null;
				
				PreparedStatement convoTree = null;
				PreparedStatement convoNode = null;
				PreparedStatement defaultResp = null;
				PreparedStatement endResp = null;
				PreparedStatement puzzleResp = null;
				PreparedStatement rewardResp = null;
				PreparedStatement buyResp = null;
				PreparedStatement sellResp = null;
				
				PreparedStatement door = null;
				PreparedStatement chest = null;
				PreparedStatement keypad = null;
				PreparedStatement sign = null;
				
				PreparedStatement keyPuzzle = null;
				PreparedStatement pinPuzzle = null;
				PreparedStatement enemyPuzzle = null;
				PreparedStatement createTable = null;
				
				
				try {
					createTable = conn.prepareStatement("create table history ("
							+ "inputID integer primary key generated always as identity (start with 1, increment by 1),"
							+ "line varchar(400))");
					createTable.executeUpdate();
					
					players = conn.prepareStatement(

						"create table players ("
						+ " playerID integer primary key generated always as identity (start with 0, increment by 1),"
						+ "name varchar(40), roomID integer, statsID integer, currency integer, prevRoomID integer, level integer)"
						);

					players.executeUpdate();
					
					accounts = conn.prepareStatement(
							"create table accounts ("
							+ "accountID integer primary key generated always as identity (start with 0, increment by 1),"
							+ "username varchar(32), password varchar(128), salt varchar(32))"						
							);
					accounts.executeUpdate();
					
					accountPlayers = conn.prepareStatement(
							"create table accountPlayers ("
							+ "accountID integer constraint accountID references accounts,"
							+ "playerID integer constraint playerID references players)"
							);
					accountPlayers.executeUpdate();
							
							
					
					npcs = conn.prepareStatement(
							"create table npcs ("
							+ "npcID integer primary key generated always as identity (start with 1, increment by 1),"
							+ "name varchar(40), type varchar(40),"
							+ "roomID integer, statsID integer, aggression integer, convoTreeID integer, currency integer)"
							);
						npcs.executeUpdate();
					
					rooms = conn.prepareStatement(
						"create table rooms ("
						+ " roomID integer ,"
						+ "roomname varchar(40), short varchar (400), long varchar(800),"
						+ " level integer, prev boolean, gID integer)"
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
						"	itemID integer, name varchar(40), price integer, defMod integer, hpMod integer, spdMod integer, " +									
						"	playerID integer," +
						"	roomID integer," +
						"	npcID integer," +
						"	equipped boolean)"
					);	
					equipment.executeUpdate();
					
					weapons = conn.prepareStatement(
							"create table weapons ("
							+ "itemID integer, name varchar(40), price integer, damage integer, playerID integer ,"
							+ "roomID integer, "
							+ "npcID integer , equipped boolean)"
					);
					weapons.executeUpdate();
					
					usables = conn.prepareStatement(
							"create table usables ("
							+ "itemID integer, name varchar(40), price integer, playerID integer ,"
							+ "roomID integer, "
							+ "npcID integer )"
					);
					usables.executeUpdate();
					
					consumables = conn.prepareStatement(
							"create table consumables ("
							+ "itemID integer, name varchar(40), price integer, curHPMod integer, maxHPMod integer, dmgMod integer, defMod integer, spdMod integer,"
							+ "playerID integer,"
							+ "roomID integer,"
							+ "npcID integer)"
					);
					consumables.executeUpdate();
					
					treasures = conn.prepareStatement(
							"create table treasures ("
							+ "itemID integer, name varchar(40), price integer, playerID integer,"
							+ "roomID integer,"
							+ "npcID integer)"
					);
					treasures.executeUpdate();
					
					trophies = conn.prepareStatement(
							"create table trophies ("
							+ "itemID integer, name varchar(40), price integer,playerID integer,"
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
							+ "convoNodeKey integer, statement varchar(300))"
					);
					convoNode.executeUpdate();
					
					defaultResp = conn.prepareStatement(
							"create table defaultResponse ("
							+ "defaultResponseID integer, convoTreeID integer,"
							+ "convoNodeID integer, response varchar(300), "
							+ "resultNodeID integer)"
					);
					defaultResp.executeUpdate();
					
					endResp = conn.prepareStatement(
							"create table endResponse ("
							+ "endResponseID integer, convoTreeID integer, "
							+ "convoNodeID integer, response varchar(300), "
							+ "resultNodeID integer)"
					);
					endResp.executeUpdate();	
					
					puzzleResp = conn.prepareStatement(
							"create table puzzleResponse ("
							+ "puzzleResponseID integer, convoTreeID integer, "
							+ "convoNodeID integer, response varchar(300), "
							+ "resultNodeID integer, puzzleID integer, completeResultNodeID integer)"
					);
					puzzleResp.executeUpdate();
					
					rewardResp = conn.prepareStatement(
							"create table rewardResponse ("
							+ "rewardResponseID integer, convoTreeID integer, "
							+ "convoNodeID integer, response varchar(300), "
							+ "resultNodeID integer, itemID integer, "
							+ "currency integer, exp integer, collected boolean)"
					);
					rewardResp.executeUpdate();
					
					buyResp = conn.prepareStatement(
							"create table buyResponse ("
							+ "buyResponseID integer, convoTreeID integer, "
							+ "convoNodeID integer, resultNodeID integer, "
							+ "itemID integer, bought boolean, failedResultNodeID integer)"
					);
					buyResp.executeUpdate();
					
					sellResp = conn.prepareStatement(
							"create table sellResponse ("
							+ "sellResponseID integer, convoTreeID integer, "
							+ "convoNodeID integer, resultNodeID integer, "
							+ "itemID integer, sold boolean, failedResultNodeID integer)"
					);
					sellResp.executeUpdate();
					
					door = conn.prepareStatement(
							"create table door ("
							+ "doorID integer, interactableID integer, "
							+ "name varchar(40), description varchar(100), "
							+ "activated boolean, roomID integer, puzzleID integer, "
							+ "direction varchar(40))"
					);
					door.executeUpdate();
					
					chest = conn.prepareStatement(
							"create table chest ( "
							+ "chestID integer, interactableID integer, "
							+ "name varchar(40), description varchar(100), "
							+ "activated boolean, roomID integer, puzzleID integer)"
					);
					chest.executeUpdate();
					
					keypad = conn.prepareStatement(
							"create table keypad ( "
							+ "keypadID integer, interactableID integer, "
							+ "name varchar(40), description varchar(100), "
							+ "activated boolean, roomID integer, puzzleID integer)"
					);
					keypad.executeUpdate();
					
					sign = conn.prepareStatement(
							"create table sign ( "
							+ "signID integer, interactableID integer, "
							+ "name varchar(40), description varchar(100), "
							+ "activated boolean, message varchar(400),"
							+ " roomID integer, puzzleID integer)"
					);
					sign.executeUpdate();
					
					keyPuzzle = conn.prepareStatement( 
							"create table keyPuzzle ( "
							+ "keyPuzzleID integer, puzzleID integer, "
							+ "keyItemID integer, complete boolean, hint varchar(100), "
							+ "completeMSG varchar(200), currency integer, "
							+ "exp integer, itemID integer)"
					);
					keyPuzzle.executeUpdate();
					
					pinPuzzle = conn.prepareStatement( 
							"create table pinPuzzle ( "
							+ "pinPuzzleID integer, puzzleID integer, "
							+ "keyStr varchar(40), complete boolean, hint varchar(100), "
							+ "completeMSG varchar(200), currency integer, "
							+ "exp integer, itemID integer)"
					);
					pinPuzzle.executeUpdate();
					
					enemyPuzzle = conn.prepareStatement( 
							"create table enemyPuzzle ( "
							+ "enemyPuzzleID integer, puzzleID integer, "
							+ "npcID integer, complete boolean, hint varchar(100), "
							+ "completeMSG varchar(200), currency integer, "
							+ "exp integer, itemID integer)"
					);
					enemyPuzzle.executeUpdate();
					
					actorStats = conn.prepareStatement(
							"create table actorStats ( "
							+ "statsID integer, curHP integer, maxHP integer, "
							+ "dmg integer, def integer, spd integer, curExp integer,"
							+ " maxExp integer, curLvl integer)"
					);
					actorStats.executeUpdate();
					
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
					List<PuzzleResponse> puzzleResponseList;
					List<RewardResponse> rewardResponseList;
					List<BuyResponse> buyResponseList;
					List<SellResponse> sellResponseList;
					
					List<Door> doorList;
					List<Chest> chestList;
					List<Keypad> keypadList;
					List<Sign> signList;
					
					List<KeyPuzzle> keyPuzzleList;
					List<PinPuzzle> pinPuzzleList;
					List<EnemyPuzzle> enemyPuzzleList;
					
					List<Player> playerList;
					List<NPC> npcList;
					List<ActorStats> statsList;
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
						puzzleResponseList = InitialData.getPuzzleResponses();
						rewardResponseList = InitialData.getRewardResponses();
						buyResponseList = InitialData.getBuyResponses();
						sellResponseList = InitialData.getSellResponses();
						
						doorList = InitialData.getDoors();
						chestList = InitialData.getChests();
						keypadList = InitialData.getKeypads();
						signList = InitialData.getSigns();
						
						keyPuzzleList = InitialData.getKeyPuzzles();
						pinPuzzleList = InitialData.getPinPuzzles();
						enemyPuzzleList = InitialData.getEnemyPuzzles();
						
						playerList = InitialData.getPlayers();
						npcList = InitialData.getNPCs();
						statsList = InitialData.getActorStats();
						
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
					PreparedStatement insertPuzzleResp = null;
					PreparedStatement insertRewardResp = null;
					PreparedStatement insertBuyResp = null;
					PreparedStatement insertSellResp = null;
					
					PreparedStatement insertDoor = null;
					PreparedStatement insertChest = null;
					PreparedStatement insertKeypad = null;
					PreparedStatement insertSign = null;
					
					PreparedStatement insertKeyPuzzle = null;
					PreparedStatement insertPinPuzzle = null;
					PreparedStatement insertEnemyPuzzle = null;
					
					PreparedStatement insertPlayer = null;
					PreparedStatement insertNPC = null;
					PreparedStatement insertActorStats = null;
					PreparedStatement insertAccountPlayers = null;
					
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
						
						System.out.println("Execute insertAccount");
						insertAccount = conn.prepareStatement("insert into accounts (username, password, salt) values('admin','e9795645e75d58ff42d162afda7e230a84dfd4d359088a748f2448046066727f737cc9269cb692a561e1772fafaf2ea12bcae7991be1154a5deef2f146a4fb53', '3a48442caaed5f0db7f9ea530417969d')");
						insertAccount.executeUpdate();
						
						int index;
						insertWeapon = conn.prepareStatement("insert into weapons (itemID, name, price, damage, playerID, roomID, npcID, equipped) values (?,?,?,?,?,?,?,?)");
						for( Weapon weapon : weaponList) {
							index = 1;
							insertWeapon.setInt(index++, weapon.getItemID());
							insertWeapon.setString(index++, weapon.getName());
							insertWeapon.setInt(index++, weapon.getPrice());
							insertWeapon.setInt(index++, weapon.getDamage());
							insertWeapon.setInt(index++, weapon.getPlayerID());
							insertWeapon.setInt(index++, weapon.getRoomID());
							insertWeapon.setInt(index++, weapon.getNPCID());
							insertWeapon.setBoolean(index++, weapon.getEquipped());
							
							insertWeapon.addBatch();
						}
						insertWeapon.executeBatch();
						
						System.out.println("Weapons table populated");
						
						insertEquipment = conn.prepareStatement("insert into equipment (itemID, name, price, defMod, hpMod, spdMod, playerId, roomID, npcID, equipped) values (?,?,?,?,?,?,?,?,?,?)");
						for(Equipment equipment : equipmentList) {
							index = 1;
							insertEquipment.setInt(index++, equipment.getItemID());
							insertEquipment.setString(index++, equipment.getName());
							insertEquipment.setInt(index++, equipment.getPrice());
							insertEquipment.setInt(index++, equipment.getDefenseMod());
							insertEquipment.setInt(index++, equipment.getHPMod());
							insertEquipment.setInt(index++, equipment.getSpeedMod());
							insertEquipment.setInt(index++, equipment.getPlayerID());
							insertEquipment.setInt(index++, equipment.getRoomID());
							insertEquipment.setInt(index++, equipment.getNPCID());
							insertEquipment.setBoolean(index++, equipment.getEquipped());
							
							insertEquipment.addBatch();
						}
						insertEquipment.executeBatch();
						
						insertUsable = conn.prepareStatement("insert into usables (itemID, name, price, playerId, roomID, npcID) values (?,?,?,?,?,?)");
						for(Usable usables : usableList) {
							index = 1;
							insertUsable.setInt(index++, usables.getItemID());
							insertUsable.setString(index++, usables.getName());
							insertUsable.setInt(index++, usables.getPrice());
							insertUsable.setInt(index++, usables.getPlayerID());
							insertUsable.setInt(index++, usables.getRoomID());
							insertUsable.setInt(index++, usables.getNPCID());
							
							insertUsable.addBatch();
						}
						insertUsable.executeBatch();
						
						insertConsumable = conn.prepareStatement("insert into Consumables (itemID, name, price, curhpMod, maxhpMod, dmgMod, defmod, spdmod, playerId, roomID, npcID) values (?,?,?,?,?,?,?,?,?,?,?)");
						for(Consumable consumable : consumableList) {
							index = 1;
							insertConsumable.setInt(index++, consumable.getItemID());
							insertConsumable.setString(index++, consumable.getName());
							insertConsumable.setInt(index++, consumable.getBuyPrice());
							insertConsumable.setInt(index++, consumable.getCurHPMod());
							insertConsumable.setInt(index++, consumable.getMaxHPMod());
							insertConsumable.setInt(index++, consumable.getdmgMod());
							insertConsumable.setInt(index++, consumable.getdefMod());
							insertConsumable.setInt(index++, consumable.getspdMod());
							insertConsumable.setInt(index++, consumable.getPlayerID());
							insertConsumable.setInt(index++, consumable.getRoomID());
							insertConsumable.setInt(index++, consumable.getNPCID());
							
							insertConsumable.addBatch();
						}
						insertConsumable.executeBatch();
						
						insertTreasure = conn.prepareStatement("insert into Treasures (itemID, name, price, playerId, roomID, npcID) values (?,?,?,?,?,?)");
						for(Treasure treasures : treasureList) {
							index = 1;
							insertTreasure.setInt(index++, treasures.getItemID());
							insertTreasure.setString(index++, treasures.getName());
							insertTreasure.setInt(index++, treasures.getBuyPrice());
							insertTreasure.setInt(index++, treasures.getPlayerID());
							insertTreasure.setInt(index++, treasures.getRoomID());
							insertTreasure.setInt(index++, treasures.getNPCID());
							
							insertTreasure.addBatch();
						}
						insertTreasure.executeBatch();
						
						insertTrophy = conn.prepareStatement("insert into Trophies (itemID, name, price, playerId, roomID, npcID) values (?,?,?,?,?,?)");
						for(Trophy trophies : trophyList) {
							index = 1;
							insertTrophy.setInt(index++, trophies.getItemID());
							insertTrophy.setString(index++, trophies.getName());
							insertTrophy.setInt(index++, trophies.getBuyPrice());
							insertTrophy.setInt(index++, trophies.getPlayerID());
							insertTrophy.setInt(index++, trophies.getRoomID());
							insertTrophy.setInt(index++, trophies.getNPCID());
							
							insertTrophy.addBatch();
						}
						insertTrophy.executeBatch();
						
						
						
						
					} finally {
						//DBUtil.closeQuietly(insertRoomConnections);
					}

					try {
						// populate Rooms table 
						insertRoom = conn.prepareStatement("insert into rooms (roomID,roomName, short, long, Level, prev, gID) values (?,?,?,?,?,?,?)");
						for (Room room : roomList) {
							insertRoom.setInt(1, room.getRoomID());	// auto-generated primary key, don't insert this.  MAY NEED THIS WHEN LOADING MULTIPLE LEVELS
							insertRoom.setString(2, room.getRoomName());
							insertRoom.setString(3, room.getRoomDescripShort());
							insertRoom.setString(4, room.getRoomDescripLong());
							insertRoom.setInt(5, room.getRoomLevel());
							insertRoom.setBoolean(6, room.getRoomPrevVisit());
							insertRoom.setInt(7, room.getRoomGameID());
							
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
					System.out.println("ConversationTree table successfully populated");
					
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
					System.out.println("ConversationNode table successfully populated");
					//return true;
					
					try {
						insertDefaultResp = conn.prepareStatement("insert into defaultResponse (defaultResponseID, convoTreeID, convoNodeID, response, resultNodeID)"
								+ "values (?, ?, ?, ?, ?)");
						for(DefaultResponse defaultResp : defaultRespList) {
							insertDefaultResp.setInt(1, defaultResp.getDefaultResponseId());
							insertDefaultResp.setInt(2, defaultResp.getConvoTreeId());
							insertDefaultResp.setInt(3, defaultResp.getNodeId());
							insertDefaultResp.setString(4, defaultResp.getResponseStr());
							insertDefaultResp.setInt(5, defaultResp.getResultNode());
							
							insertDefaultResp.addBatch();
						}
						insertDefaultResp.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertDefaultResp);
					}
					System.out.println("DefaultResponse table successfully populated");
					
					try {
						insertEndResp = conn.prepareStatement("insert into endResponse (endResponseID, convoTreeID, convoNodeID, response, resultNodeID)"
								+ "values (?, ?, ?, ?, ?)");
						for(EndResponse endResp : endRespList) {
							insertEndResp.setInt(1, endResp.getEndResponseId());
							insertEndResp.setInt(2, endResp.getConvoTreeId());
							insertEndResp.setInt(3, endResp.getNodeId());
							insertEndResp.setString(4, endResp.getResponseStr());
							insertEndResp.setInt(5, endResp.getResultNode());
							
							insertEndResp.addBatch();
						}
						insertEndResp.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertEndResp);
					}
					System.out.println("EndResponse table successfully populated");
					
					try {
						insertPuzzleResp = conn.prepareStatement("insert into puzzleResponse (puzzleResponseID, convoTreeID, convoNodeID, response, resultNodeID, puzzleID, completeResultNodeID)"
								+ "values (?, ?, ?, ?, ?, ?, ?)");
						for(PuzzleResponse puzzleResp : puzzleResponseList) {
							insertPuzzleResp.setInt(1, puzzleResp.getPuzzleResponseId());
							insertPuzzleResp.setInt(2, puzzleResp.getConvoTreeId());
							insertPuzzleResp.setInt(3, puzzleResp.getNodeId());
							insertPuzzleResp.setString(4, puzzleResp.getResponseStr());
							insertPuzzleResp.setInt(5, puzzleResp.getResultNode());
							insertPuzzleResp.setInt(6, puzzleResp.getPuzzleId());
							insertPuzzleResp.setInt(7, puzzleResp.getCompleteResultNode());
							
							insertPuzzleResp.addBatch();
						}
						insertPuzzleResp.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertPuzzleResp);
					}
					System.out.println("PuzzleResponse table successfully populated");
					
					try {
						insertRewardResp = conn.prepareStatement("insert into rewardResponse (rewardResponseID, convoTreeID, convoNodeID, response, resultNodeID, itemID, currency, exp, collected)"
								+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
						for(RewardResponse rewardResp : rewardResponseList) {
							insertRewardResp.setInt(1, rewardResp.getRewardResponseId());
							insertRewardResp.setInt(2, rewardResp.getConvoTreeId());
							insertRewardResp.setInt(3, rewardResp.getNodeId());
							insertRewardResp.setString(4, rewardResp.getResponseStr());
							insertRewardResp.setInt(5, rewardResp.getResultNode());
							insertRewardResp.setInt(6, rewardResp.getRewardItemId());
							insertRewardResp.setInt(7, rewardResp.getRewardCurrency());
							insertRewardResp.setInt(8, rewardResp.getRewardExp());
							insertRewardResp.setBoolean(9, rewardResp.getCollected());
							
							insertRewardResp.addBatch();
						}
						insertRewardResp.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertRewardResp);
					}
					System.out.println("RewardResponse table successfully populated");
					
					try {
						insertBuyResp = conn.prepareStatement("insert into buyResponse (buyResponseID, convoTreeID, convoNodeID, resultNodeID, itemID, bought, failedResultNodeID)"
								+ "values (?, ?, ?, ?, ?, ?, ?)");
						for(BuyResponse buyResp : buyResponseList) {
							insertBuyResp.setInt(1, buyResp.getBuyResponseId());
							insertBuyResp.setInt(2, buyResp.getConvoTreeId());
							insertBuyResp.setInt(3, buyResp.getNodeId());
							insertBuyResp.setInt(4, buyResp.getResultNode());
							insertBuyResp.setInt(5, buyResp.getBuyItemId());
							insertBuyResp.setBoolean(6, buyResp.getBought());
							insertBuyResp.setInt(7, buyResp.getFailedNode());
							
							insertBuyResp.addBatch();
						}
						insertBuyResp.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertBuyResp);
					}
					System.out.println("BuyResponse table successfully populated");
					
					try {
						insertSellResp = conn.prepareStatement("insert into sellResponse (sellResponseID, convoTreeID, convoNodeID, resultNodeID, itemID, sold, failedResultNodeID)"
								+ "values (?, ?, ?, ?, ?, ?, ?)");
						for(SellResponse sellResp : sellResponseList) {
							insertSellResp.setInt(1, sellResp.getSellResponseId());
							insertSellResp.setInt(2, sellResp.getConvoTreeId());
							insertSellResp.setInt(3, sellResp.getNodeId());
							insertSellResp.setInt(4, sellResp.getResultNode());
							insertSellResp.setInt(5, sellResp.getSellItemId());
							insertSellResp.setBoolean(6, sellResp.getSold());
							insertSellResp.setInt(7, sellResp.getFailedNode());
							
							insertSellResp.addBatch();
						}
						insertSellResp.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertSellResp);
					}
					System.out.println("SellResponse table successfully populated");
					
					try {
						insertDoor = conn.prepareStatement("insert into door (doorID, interactableID, name, description, activated, roomID, puzzleID, direction)"
								+ "values (?, ?, ?, ?, ?, ?, ?, ?)");
						for(Door door : doorList) {
							insertDoor.setInt(1, door.getDoorId());
							insertDoor.setInt(2, door.getInteractableId());
							insertDoor.setString(3, door.getName());
							insertDoor.setString(4, door.getDescription());
							insertDoor.setBoolean(5, door.getActivated());
							insertDoor.setInt(6, door.getRoomId());
							insertDoor.setInt(7, door.getPuzzleId());
							insertDoor.setString(8, door.getDirection());
							
							insertDoor.addBatch();
						}
						insertDoor.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertDoor);
					}
					System.out.println("Door table successfully populated");
					
					try {
						insertChest = conn.prepareStatement("insert into chest (chestID, interactableID, name, description, activated, roomID, puzzleID)"
								+ "values (?, ?, ?, ?, ?, ?, ?)");
						for(Chest chest : chestList) {
							insertChest.setInt(1, chest.getChestId());
							insertChest.setInt(2, chest.getInteractableId());
							insertChest.setString(3, chest.getName());
							insertChest.setString(4, chest.getDescription());
							insertChest.setBoolean(5, chest.getActivated());
							insertChest.setInt(6, chest.getRoomId());
							insertChest.setInt(7, chest.getPuzzleId());
							
							insertChest.addBatch();
						}
						insertChest.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertChest);
					}
					System.out.println("Chest table successfully populated");
					
					try {
						insertKeypad = conn.prepareStatement("insert into keypad (keypadID, interactableID, name, description, activated, roomID, puzzleID)"
								+ "values (?, ?, ?, ?, ?, ?, ?)");
						for(Keypad keypad : keypadList) {
							insertKeypad.setInt(1, keypad.getKeypadId());
							insertKeypad.setInt(2, keypad.getInteractableId());
							insertKeypad.setString(3, keypad.getName());
							insertKeypad.setString(4, keypad.getDescription());
							insertKeypad.setBoolean(5, keypad.getActivated());
							insertKeypad.setInt(6, keypad.getRoomId());
							insertKeypad.setInt(7, keypad.getPuzzleId());
							
							insertKeypad.addBatch();
						}
						insertKeypad.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertKeypad);
					}
					System.out.println("Keypad table successfully populated");
					
					try {
						insertSign = conn.prepareStatement("insert into sign (signID, interactableID, name, description, activated, message, roomID, puzzleID)"
								+ "values (?, ?, ?, ?, ?, ?, ?, ?)");
						for(Sign sign : signList) {
							insertSign.setInt(1, sign.getSignId());
							insertSign.setInt(2, sign.getInteractableId());
							insertSign.setString(3, sign.getName());
							insertSign.setString(4, sign.getDescription());
							insertSign.setBoolean(5, sign.getActivated());
							insertSign.setString(6, sign.getMessage());
							insertSign.setInt(7, sign.getRoomId());
							insertSign.setInt(8, sign.getPuzzleId());
							
							insertSign.addBatch();
						}
						insertSign.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertSign);
					}
					System.out.println("Sign table successfully populated");
					
					try {
						insertKeyPuzzle = conn.prepareStatement("insert into keyPuzzle (keyPuzzleID, puzzleID, keyItemID, complete, hint, completeMSG, currency, exp, itemID)"
								+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
						for(KeyPuzzle puzzle : keyPuzzleList) {
							insertKeyPuzzle.setInt(1, puzzle.getKeyPuzzleId());
							insertKeyPuzzle.setInt(2, puzzle.getPuzzleId());
							insertKeyPuzzle.setInt(3, puzzle.getItemId());
							insertKeyPuzzle.setBoolean(4, puzzle.getComplete());
							insertKeyPuzzle.setString(5, puzzle.getHint());
							insertKeyPuzzle.setString(6, puzzle.getCompleteMSG());
							insertKeyPuzzle.setInt(7, puzzle.getCurrencyReward());
							insertKeyPuzzle.setInt(8, puzzle.getExpReward());
							insertKeyPuzzle.setInt(9, puzzle.getRewardItemId());
							
							insertKeyPuzzle.addBatch();
						}
						insertKeyPuzzle.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertKeyPuzzle);
					}
					System.out.println("KeyPuzzle table successfully populated");
					
					try {
						insertPinPuzzle = conn.prepareStatement("insert into pinPuzzle (pinPuzzleID, puzzleID, keyStr, complete, hint, completeMSG, currency, exp, itemID)"
								+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
						for(PinPuzzle puzzle : pinPuzzleList) {
							insertPinPuzzle.setInt(1, puzzle.getPinPuzzleId());
							insertPinPuzzle.setInt(2, puzzle.getPuzzleId());
							insertPinPuzzle.setString(3, puzzle.getKey());
							insertPinPuzzle.setBoolean(4, puzzle.getComplete());
							insertPinPuzzle.setString(5, puzzle.getHint());
							insertPinPuzzle.setString(6, puzzle.getCompleteMSG());
							insertPinPuzzle.setInt(7, puzzle.getCurrencyReward());
							insertPinPuzzle.setInt(8, puzzle.getExpReward());
							insertPinPuzzle.setInt(9, puzzle.getRewardItemId());
							
							insertPinPuzzle.addBatch();
						}
						insertPinPuzzle.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertPinPuzzle);
					}
					System.out.println("PinPuzzle table successfully populated");
					
					try {
						insertEnemyPuzzle = conn.prepareStatement("insert into enemyPuzzle (enemyPuzzleID, puzzleID, npcID, complete, hint, completeMSG, currency, exp, itemID)"
								+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
						for(EnemyPuzzle puzzle : enemyPuzzleList) {
							insertEnemyPuzzle.setInt(1, puzzle.getEnemyPuzzleId());
							insertEnemyPuzzle.setInt(2, puzzle.getPuzzleId());
							insertEnemyPuzzle.setInt(3, puzzle.getNPCId());
							insertEnemyPuzzle.setBoolean(4, puzzle.getComplete());
							insertEnemyPuzzle.setString(5, puzzle.getHint());
							insertEnemyPuzzle.setString(6, puzzle.getCompleteMSG());
							insertEnemyPuzzle.setInt(7, puzzle.getCurrencyReward());
							insertEnemyPuzzle.setInt(8, puzzle.getExpReward());
							insertEnemyPuzzle.setInt(9, puzzle.getRewardItemId());
							
							insertEnemyPuzzle.addBatch();
						}
						insertEnemyPuzzle.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertEnemyPuzzle);
					}
					System.out.println("EnemyPuzzle table successfully populated");
					
					try {
						insertPlayer = conn.prepareStatement("insert into players (name, roomID, statsID, currency, prevRoomID)"
								+ "values (?, ?, ?, ?, ?)");
						for(Player player : playerList) {
							//insertPlayer.setInt(1, player.getPlayerId());
							insertPlayer.setString(1, player.getName());
							insertPlayer.setInt(2, player.getRoomId());
							insertPlayer.setInt(3, player.getStatsId());
							insertPlayer.setInt(4,  player.getCurrency());
							insertPlayer.setInt(5, player.getPrevRoomId());
							
							insertPlayer.addBatch();
						}
						insertPlayer.executeBatch();
						
						insertAccountPlayers = conn.prepareStatement("insert into accountPlayers (accountID, playerID)"
								+ "values (0,1)");
						insertAccountPlayers.executeUpdate();
					} finally {
						DBUtil.closeQuietly(insertPlayer);
					}
					System.out.println("Player table successfully populated");
					
					try {
						insertNPC = conn.prepareStatement("insert into npcs (name, type, roomID, statsID, aggression, convoTreeID, currency)"
								+ "values (?, ?, ?, ?, ?, ?, ?)");
						for(NPC npc : npcList) {
							//insertNPC.setInt(1, npc.getNPCId());
							insertNPC.setString(1, npc.getName());
							insertNPC.setString(2, npc.getType());
							insertNPC.setInt(3, npc.getRoomId());
							insertNPC.setInt(4, npc.getStatsId());
							insertNPC.setInt(5, npc.getAggression());
							insertNPC.setInt(6, npc.getConversationTreeId());
							insertNPC.setInt(7,  npc.getCurrency());
							
							insertNPC.addBatch();
						}
						insertNPC.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertNPC);
					}
					System.out.println("NPC table successfully populated");
					
					try {
						insertActorStats = conn.prepareStatement("insert into actorStats (statsID, curHP, maxHP, dmg, def, spd, curExp, maxExp, curLvl)"
								+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
						for(ActorStats stats : statsList) {
							//insertNPC.setInt(1, npc.getNPCId());
							insertActorStats.setInt(1, stats.getStatsId());
							insertActorStats.setInt(2, stats.getCurHP());
							insertActorStats.setInt(3, stats.getMaxHP());
							insertActorStats.setInt(4, stats.getDmg());
							insertActorStats.setInt(5, stats.getDef());
							insertActorStats.setInt(6, stats.getSpd());
							insertActorStats.setInt(7,  stats.getCurExp());
							insertActorStats.setInt(8,  stats.getMaxExp());
							insertActorStats.setInt(9,  stats.getCurLvl());
							
							insertActorStats.addBatch();
						}
						insertActorStats.executeBatch();
					} finally {
						DBUtil.closeQuietly(insertActorStats);
					}
					System.out.println("ActorStats table successfully populated");
					
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
	public ArrayList<Player> findAllPlayers() {
		return executeTransaction(new Transaction<ArrayList<Player>>() {
			@Override
			public ArrayList<Player> execute(Connection conn) throws SQLException {
				PreparedStatement playerStmt = null;
				ResultSet playerSet = null;
				ArrayList<Player> playerList = new ArrayList<Player>();
				try {
					playerStmt = conn.prepareStatement(
							"select players.* from players"
				);
					
				playerSet = playerStmt.executeQuery();
					
				while(playerSet.next()) {
					Player player = new Player();
					player.setPlayerId(playerSet.getInt(1));
					player.setName(playerSet.getString(2));
					player.setRoomId(playerSet.getInt(3));
					player.setStatsId(playerSet.getInt(4));
					player.setCurrency(playerSet.getInt(5));
					player.setPrevRoomId(playerSet.getInt(6));
					
					playerList.add(player);
				}
					
				return playerList;
				} finally {
					DBUtil.closeQuietly(playerStmt);
				}
				
			}
		});
	}

	@Override
	public ArrayList<NPC> findAllNPCs() {
		return executeTransaction(new Transaction<ArrayList<NPC>>() {
			@Override
			public ArrayList<NPC> execute(Connection conn) throws SQLException {
				PreparedStatement npcStmt = null;
				ResultSet npcSet = null;
				ArrayList<NPC> npcList = new ArrayList<NPC>();
				try {
					npcStmt = conn.prepareStatement(
							"select npcs.* from npcs"
				);
					
				npcSet = npcStmt.executeQuery();
					
				while(npcSet.next()) {
					NPC npc = new NPC();
					npc.setNPCId(npcSet.getInt(1));
					npc.setName(npcSet.getString(2));
					npc.setType(npcSet.getString(3));
					npc.setRoomId(npcSet.getInt(4));
					npc.setStatsId(npcSet.getInt(5));
					npc.setAggression(npcSet.getInt(6));
					npc.setConversationTreeId(7);
					npc.setCurrency(npcSet.getInt(8));
					
					npcList.add(npc);
				}
					
				return npcList;
				} finally {
					DBUtil.closeQuietly(npcStmt);
				}
				
			}
		});
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
		return executeTransaction(new Transaction<ActorStats>() {
			@Override
			public ActorStats execute(Connection conn) throws SQLException {
				PreparedStatement statStmt = null;
				ResultSet statSet = null;
				ActorStats stats = new ActorStats();
				
				try {
					statStmt = conn.prepareStatement(
							"select actorStats.* from actorStats, players "
							+ "where actorStats.statsID = players.statsID and players.playerID = ?"
				);
				statStmt.setInt(1, playerId);
				
				statSet = statStmt.executeQuery();
				
				while(statSet.next()) {
					stats.setCurHP(statSet.getInt(2));
					stats.setMaxHP(statSet.getInt(3));
					stats.setDmg(statSet.getInt(4));
					stats.setDef(statSet.getInt(5));
					stats.setSpd(statSet.getInt(6));
					stats.setCurExp(statSet.getInt(7));
					stats.setMaxExp(statSet.getInt(8));
					stats.setCurLvl(statSet.getInt(9));
				}
					
				return stats;
				}finally {
					DBUtil.closeQuietly(statStmt);
				}
			}
		});
	}

	@Override
	public ActorStats findActorStatsByNPCId(int npcId) {
		return executeTransaction(new Transaction<ActorStats>() {
			@Override
			public ActorStats execute(Connection conn) throws SQLException {
				PreparedStatement statStmt = null;
				ResultSet statSet = null;
				ActorStats stats = new ActorStats();
				
				try {
					statStmt = conn.prepareStatement(
							"select actorStats.* from actorStats, npcs "
							+ "where actorStats.statsID = npcs.statsID and npcs.npcID = ?"
				);
				statStmt.setInt(1, npcId);
				
				statSet = statStmt.executeQuery();
				
				while(statSet.next()) {
					stats.setCurHP(statSet.getInt(2));
					stats.setMaxHP(statSet.getInt(3));
					stats.setDmg(statSet.getInt(4));
					stats.setDef(statSet.getInt(5));
					stats.setSpd(statSet.getInt(6));
					stats.setCurExp(statSet.getInt(7));
					stats.setMaxExp(statSet.getInt(8));
					stats.setCurLvl(statSet.getInt(9));
				}
					
				return stats;
				}finally {
					DBUtil.closeQuietly(statStmt);
				}
			}
		});
	}

	@Override
	public List<Room> findAllRooms(){
		//return roomList;
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
							"select rooms.* from rooms where rooms.roomID = ?"
				);
				
					roomstmt.setInt(1, roomID);
					
					roomSet = roomstmt.executeQuery();
					
					while(roomSet.next()) {
						room.setRoomID(roomID);
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
							"select connections.* from connections where connections.roomID = ?"
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
		return executeTransaction(new Transaction<ConversationTree>() {
			@Override
			public ConversationTree execute(Connection conn) throws SQLException {
				PreparedStatement treeStmt = null;
				PreparedStatement nodeStmt = null;
				PreparedStatement defaultRespStmt = null;
				PreparedStatement endRespStmt = null;
				PreparedStatement puzzleRespStmt = null;
				PreparedStatement rewardRespStmt = null;
				PreparedStatement buyRespStmt = null;
				PreparedStatement sellRespStmt = null;
				
				ResultSet treeIDSet = null;
				ResultSet nodeSet = null;
				ResultSet defaultRespSet = null;
				ResultSet endRespSet = null;
				ResultSet puzzleRespSet = null;
				ResultSet rewardRespSet = null;
				ResultSet buyRespSet = null;
				ResultSet sellRespSet = null;
				
				ConversationTree convoTree = new ConversationTree();
				
				
				try {
					treeStmt = conn.prepareStatement(
							"select convoTreeID from conversationTree "
							+ "where conversationTree.npcID = ?");
					treeStmt.setInt(1, npcID);
					
					treeIDSet = treeStmt.executeQuery();
					TreeMap<Integer, ConversationNode> conversationTreeMap = new TreeMap<Integer, ConversationNode>();
					while(treeIDSet.next()) {
						
						int index = 1;
						int treeID = treeIDSet.getInt(index++);
						
						nodeStmt = conn.prepareStatement(
							"select convoNodeID, convoNodeKey, statement from conversationNode "
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
								"select response, resultNodeID from defaultResponse "
								+ "where defaultResponse.convoNodeID = ? and defaultResponse.convoTreeID = ?");
							defaultRespStmt.setInt(1, convoNodeID);	
							defaultRespStmt.setInt(2, treeID);
							
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
									"select response, resultNodeID from endResponse "
									+ "where endResponse.convoNodeID = ? and endResponse.convoTreeID = ?");
							endRespStmt.setInt(1, convoNodeID);	
							endRespStmt.setInt(2, treeID);	
							
							endRespSet = endRespStmt.executeQuery();
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
								
							puzzleRespStmt = conn.prepareStatement(
									"select response, resultNodeID, puzzleID, completeResultNodeID from PuzzleResponse "
									+ "where puzzleResponse.convoNodeID = ? and puzzleResponse.convoTreeID = ?");
							puzzleRespStmt.setInt(1, convoNodeID);
							puzzleRespStmt.setInt(2, treeID);
							
							puzzleRespSet = puzzleRespStmt.executeQuery();
							while(puzzleRespSet.next()) {
								int puzzleIndex = 1;
								String response = puzzleRespSet.getString(puzzleIndex++);
								int resultNodeID = puzzleRespSet.getInt(puzzleIndex++);
								int puzzleID = puzzleRespSet.getInt(puzzleIndex++);
								int completeResultNodeID = puzzleRespSet.getInt(puzzleIndex++);
								
								
								PuzzleResponse resp = new PuzzleResponse();
								resp.setResultNode(resultNodeID);
								resp.setConvoTreeId(treeID);
								resp.setNodeId(convoNodeID);
								resp.setResponseStr(response);
								resp.setPuzzleId(puzzleID);
								resp.setCompleteResultNode(completeResultNodeID);
								resp.setPuzzle(getPuzzleByPuzzleID(puzzleID));
								
								respList.add(resp);
							}
							
							rewardRespStmt = conn.prepareStatement(
									"select response, resultNodeID, itemID, currency, exp, collected "
									+ "from rewardResponse "
									+ "where rewardResponse.convoNodeID = ? and rewardResponse.convoTreeID = ?");
							rewardRespStmt.setInt(1, convoNodeID);
							rewardRespStmt.setInt(2, treeID);
							
							rewardRespSet = rewardRespStmt.executeQuery();
							while(rewardRespSet.next()) {
								int rewardIndex = 1;
								String response = rewardRespSet.getString(rewardIndex++);
								int resultNodeID = rewardRespSet.getInt(rewardIndex++);
								int itemID = rewardRespSet.getInt(rewardIndex++);
								int currency = rewardRespSet.getInt(rewardIndex);
								int exp = rewardRespSet.getInt(rewardIndex++);
								Boolean collected = rewardRespSet.getBoolean(rewardIndex++);
								
								RewardResponse resp = new RewardResponse();
								resp.setResultNode(resultNodeID);
								resp.setConvoTreeId(treeID);
								resp.setNodeId(convoNodeID);
								resp.setResponseStr(response);
								resp.setRewardItemId(itemID);
								resp.setRewardCurrency(currency);
								resp.setRewardExp(exp);
								resp.setCollected(collected);
								
								respList.add(resp);
							}
							
							buyRespStmt = conn.prepareStatement(
									"select resultNodeID, itemID, bought, failedResultNodeID "
									+ "from buyResponse "
									+ "where buyResponse.convoNodeID = ? and buyResponse.convoTreeID = ?");
							buyRespStmt.setInt(1, convoNodeID);
							buyRespStmt.setInt(2, treeID);
							
							buyRespSet = buyRespStmt.executeQuery();
							while(buyRespSet.next()) {
								int buyIndex = 1;
							
								int resultNodeID = buyRespSet.getInt(buyIndex++);
								int itemID = buyRespSet.getInt(buyIndex++);
								Boolean bought = buyRespSet.getBoolean(buyIndex++);
								int failedResultNodeID = buyRespSet.getInt(buyIndex++);
								
								BuyResponse resp = new BuyResponse();
								resp.setResultNode(resultNodeID);
								resp.setConvoTreeId(treeID);
								resp.setNodeId(convoNodeID);
								resp.setBuyItemId(itemID);
								resp.setFailedNode(failedResultNodeID);
								resp.setBought(bought);
								
								respList.add(resp);
							}
							
							sellRespStmt = conn.prepareStatement(
									"select resultNodeID, itemID, sold, failedResultNodeID "
									+ "from sellResponse "
									+ "where sellResponse.convoNodeID = ? and sellResponse.convoTreeID = ?");
							sellRespStmt.setInt(1, convoNodeID);
							sellRespStmt.setInt(2, treeID);
							
							sellRespSet = sellRespStmt.executeQuery();
							while(sellRespSet.next()) {
								int sellIndex = 1;
							
								int resultNodeID = sellRespSet.getInt(sellIndex++);
								int itemID = sellRespSet.getInt(sellIndex++);
								Boolean sold = sellRespSet.getBoolean(sellIndex++);
								int failedResultNodeID = sellRespSet.getInt(sellIndex++);
								
								SellResponse resp = new SellResponse();
								resp.setResultNode(resultNodeID);
								resp.setConvoTreeId(treeID);
								resp.setNodeId(convoNodeID);
								resp.setSellItemId(itemID);
								resp.setFailedNode(failedResultNodeID);
								resp.setSold(sold);
								
								respList.add(resp);
							}
							for(ConversationResponse respons : respList) {
								System.out.println("Resp Loaded: " + respons.getResponseStr() + " TreeID " + respons.getConvoTreeId() + " Node: " + respons.getNodeId());
							}
							ConversationNode convoNode = new ConversationNode(statement, respList);
							convoNode.setConvoTreeId(treeID);
							convoNode.setConvoNodeKey(convoNodeKey);
							conversationTreeMap.put(convoNodeKey, convoNode);
						}
						//convoTree = new ConversationTree(conversationTreeMap);
						convoTree.setConversationTreeMap(conversationTreeMap);
						convoTree.setNPCId(npcID);
						System.out.println("ConversationTreeLoaded: " + conversationTreeMap);
						System.out.println("GetConversationTreeMap: " + convoTree.getConversationTreeMap());
					}
					//return true;
					System.out.println("ConversationTreeLoaded: " + conversationTreeMap);
					System.out.println("GetConversationTreeMap: " + convoTree.getConversationTreeMap());
					return convoTree;
				} 
					
					finally {
						DBUtil.closeQuietly(treeStmt);
						DBUtil.closeQuietly(nodeStmt);
						DBUtil.closeQuietly(defaultRespStmt);
						DBUtil.closeQuietly(endRespStmt);
						DBUtil.closeQuietly(puzzleRespStmt);
						DBUtil.closeQuietly(rewardRespStmt);
						DBUtil.closeQuietly(buyRespStmt);
						DBUtil.closeQuietly(sellRespStmt);
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
			System.out.println("short" + descrip);
			return descrip;
		}
		
		descrip = room.getRoomDescripLong();
		System.out.println("long" + descrip);
		return descrip;
	}
	
	@Override
	public ArrayList<Interactable> getInteractablesByRoomID(int roomId) {
		return executeTransaction(new Transaction<ArrayList<Interactable>>() {
			@Override
			public ArrayList<Interactable> execute(Connection conn) throws SQLException {
				ArrayList<Interactable> objList = new ArrayList<Interactable>();
				
				PreparedStatement doorStmt = null;
				PreparedStatement keypadStmt = null;
				PreparedStatement chestStmt = null;
				PreparedStatement signStmt = null;
				
				ResultSet doorSet = null;
				ResultSet keypadSet = null;
				ResultSet chestSet = null;
				ResultSet signSet = null;
				
				try {
					doorStmt = conn.prepareStatement(
						"select * from door "
						+ "where door.roomID = ?"	
					);
					doorStmt.setInt(1, roomId);
					
					doorSet = doorStmt.executeQuery();
					while(doorSet.next()) {
						int index = 1;
						int doorID = doorSet.getInt(index++);
						int interactableID = doorSet.getInt(index++);
						String name = doorSet.getString(index++);
						String description = doorSet.getString(index++);
						Boolean activated = doorSet.getBoolean(index++);
						index++;
						int puzzleID = doorSet.getInt(index++);
						String direction = doorSet.getString(index++);
						
						Door door = new Door();
						door.setDoorId(doorID);
						door.setInteractableId(interactableID);
						door.setName(name);
						door.setDescription(description);
						door.setActivated(activated);
						door.setRoomId(roomId);
						door.setPuzzleId(puzzleID);
						door.setDirection(direction);
						door.setPuzzle(getPuzzleByPuzzleID(puzzleID));
						
						objList.add(door);
					}
					
					keypadStmt = conn.prepareStatement(
							"select * from keypad "
							+ "where keypad.roomID = ?");
					keypadStmt.setInt(1, roomId);
					
					keypadSet = keypadStmt.executeQuery();
					while(keypadSet.next()) {
						int index = 1;
						int keypadID = keypadSet.getInt(index++);
						int interactableID = keypadSet.getInt(index++);
						String name = keypadSet.getString(index++);
						String description = keypadSet.getString(index++);
						Boolean activated = keypadSet.getBoolean(index++);
						index++;
						int puzzleID = keypadSet.getInt(index++);
						
						Keypad keypad = new Keypad();
						keypad.setKeypadId(keypadID);
						keypad.setInteractableId(interactableID);
						keypad.setName(name);
						keypad.setDescription(description);
						keypad.setActivated(activated);
						keypad.setRoomId(roomId);
						keypad.setPuzzleId(puzzleID);
						keypad.setPuzzle(getPuzzleByPuzzleID(puzzleID));
						
						objList.add(keypad);
					}
					
					chestStmt = conn.prepareStatement(
							"select * from chest "
							+ "where chest.roomID = ?");
					chestStmt.setInt(1, roomId);
					
					chestSet = chestStmt.executeQuery();
					while(chestSet.next()) {
						int index = 1;
						int chestID = chestSet.getInt(index++);
						int interactableID = chestSet.getInt(index++);
						String name = chestSet.getString(index++);
						String description = chestSet.getString(index++);
						Boolean activated = chestSet.getBoolean(index++);
						index++;
						int puzzleID = chestSet.getInt(index++);
						
						Chest chest = new Chest();
						chest.setChestId(chestID);
						chest.setInteractableId(interactableID);
						chest.setName(name);
						chest.setDescription(description);
						chest.setActivated(activated);
						chest.setRoomId(roomId);
						chest.setPuzzleId(puzzleID);
						chest.setPuzzle(getPuzzleByPuzzleID(puzzleID));
						
						objList.add(chest);
					}
					
					signStmt = conn.prepareStatement(
							"select * from sign "
							+ "where sign.roomID = ?");
					signStmt.setInt(1, roomId);
					
					signSet = signStmt.executeQuery();
					while(signSet.next()) {
						int index = 1;
						int signID = signSet.getInt(index++);
						int interactableID = signSet.getInt(index++);
						String name = signSet.getString(index++);
						String description = signSet.getString(index++);
						Boolean activated = signSet.getBoolean(index++);
						String message = signSet.getString(index++);
						int puzzleID = signSet.getInt(index++);
						
						Sign sign = new Sign();
						sign.setSignId(signID);
						sign.setInteractableId(interactableID);
						sign.setName(name);
						sign.setDescription(description);
						sign.setActivated(activated);
						sign.setRoomId(roomId);
						sign.setMessage(message);
						sign.setPuzzleId(puzzleID);
						sign.setPuzzle(getPuzzleByPuzzleID(puzzleID));
						
						objList.add(sign);
					}
					
				}
				
				finally {
					DBUtil.closeQuietly(doorStmt);
					DBUtil.closeQuietly(keypadStmt);
					DBUtil.closeQuietly(chestStmt);
					DBUtil.closeQuietly(signStmt);
				}
				return objList;
			}
		});
	}
	
	@Override
	public Puzzle getPuzzleByPuzzleID(int puzzleID) {
		return executeTransaction(new Transaction<Puzzle>() {
			@Override
			public Puzzle execute(Connection conn) throws SQLException {
				Puzzle result = null;
				
				PreparedStatement keyPuzzleStmt = null;
				PreparedStatement pinPuzzleStmt = null;
				PreparedStatement enemyPuzzleStmt = null;
				
				ResultSet keyPuzzleSet = null;
				ResultSet pinPuzzleSet = null;
				ResultSet enemyPuzzleSet = null;
				
				try {
					keyPuzzleStmt = conn.prepareStatement(
						"select * from keyPuzzle "
						+ "where keyPuzzle.puzzleID = ?"	
					);
					keyPuzzleStmt.setInt(1, puzzleID);
					KeyPuzzle keyPuzzle = null;
					keyPuzzleSet = keyPuzzleStmt.executeQuery();
					while(keyPuzzleSet.next()) {
						int index = 1;
						int keyPuzzleID = keyPuzzleSet.getInt(index++);
						index++;
						int keyItemID = keyPuzzleSet.getInt(index++);
						Boolean complete = keyPuzzleSet.getBoolean(index++);
						String hint = keyPuzzleSet.getString(index++);
						String completeMSG = keyPuzzleSet.getString(index++);
						int currency = keyPuzzleSet.getInt(index++);
						int exp = keyPuzzleSet.getInt(index++);
						int itemID = keyPuzzleSet.getInt(index++);
						
						keyPuzzle = new KeyPuzzle();
						keyPuzzle.setKeyPuzzleId(keyPuzzleID);
						keyPuzzle.setPuzzleId(puzzleID);
						keyPuzzle.setItemId(keyItemID);
						keyPuzzle.setComplete(complete);
						keyPuzzle.setHint(hint);
						keyPuzzle.setCompleteMSG(completeMSG);
						keyPuzzle.setCurrencyReward(currency);
						keyPuzzle.setExpReward(exp);
						keyPuzzle.setRewardItemId(itemID);
						
						result = keyPuzzle;
					}
					
					pinPuzzleStmt = conn.prepareStatement(
							"select * from pinPuzzle "
							+ "where pinPuzzle.puzzleID = ?");
					pinPuzzleStmt.setInt(1, puzzleID);
					
					PinPuzzle pinPuzzle = null;
					pinPuzzleSet = pinPuzzleStmt.executeQuery();
					while(pinPuzzleSet.next()) {
						int index = 1;
						int pinPuzzleID = pinPuzzleSet.getInt(index++);
						index++;
						String key = pinPuzzleSet.getString(index++);
						Boolean complete = pinPuzzleSet.getBoolean(index++);
						String hint = pinPuzzleSet.getString(index++);
						String completeMSG = pinPuzzleSet.getString(index++);
						int currency = pinPuzzleSet.getInt(index++);
						int exp = pinPuzzleSet.getInt(index++);
						int itemID = pinPuzzleSet.getInt(index++);
						
						pinPuzzle = new PinPuzzle();
						pinPuzzle.setPinPuzzleId(pinPuzzleID);
						pinPuzzle.setPuzzleId(puzzleID);
						pinPuzzle.setKey(key);
						pinPuzzle.setComplete(complete);
						pinPuzzle.setHint(hint);
						pinPuzzle.setCompleteMSG(completeMSG);
						pinPuzzle.setCurrencyReward(currency);
						pinPuzzle.setExpReward(exp);
						pinPuzzle.setRewardItemId(itemID);
						
						result = pinPuzzle;
					}
					
					enemyPuzzleStmt = conn.prepareStatement(
							"select * from enemyPuzzle "
							+ "where enemyPuzzle.puzzleID = ?");
					enemyPuzzleStmt.setInt(1, puzzleID);
					EnemyPuzzle enemyPuzzle = null;
					
					enemyPuzzleSet = enemyPuzzleStmt.executeQuery();
					while(enemyPuzzleSet.next()) {
						int index = 1;
						int enemyPuzzleID = enemyPuzzleSet.getInt(index++);
						index++;
						int npcID = enemyPuzzleSet.getInt(index++);
						Boolean complete = enemyPuzzleSet.getBoolean(index++);
						String hint = enemyPuzzleSet.getString(index++);
						String completeMSG = enemyPuzzleSet.getString(index++);
						int currency = enemyPuzzleSet.getInt(index++);
						int exp = enemyPuzzleSet.getInt(index++);
						int itemID = enemyPuzzleSet.getInt(index++);
						
						enemyPuzzle = new EnemyPuzzle();
						enemyPuzzle.setEnemyPuzzleId(enemyPuzzleID);
						enemyPuzzle.setPuzzleId(puzzleID);
						enemyPuzzle.setNPCId(npcID);;
						enemyPuzzle.setComplete(complete);
						enemyPuzzle.setHint(hint);
						enemyPuzzle.setCompleteMSG(completeMSG);
						enemyPuzzle.setCurrencyReward(currency);
						enemyPuzzle.setExpReward(exp);
						enemyPuzzle.setRewardItemId(itemID);
						
						//enemyPuzzle.setNPC(getNPCByNPCID(npcID));
						
						result = enemyPuzzle;
					}
					
					
					
				}
				
				finally {
					DBUtil.closeQuietly(keyPuzzleStmt);
					DBUtil.closeQuietly(pinPuzzleStmt);
					DBUtil.closeQuietly(enemyPuzzleStmt);
				}
				return result;
			}
		});
	}
	/*--------------------- IDatabase Methods ------------------------*/

	@Override
	public Player getPlayerByPlayerID(int playerID) {
		return executeTransaction(new Transaction<Player>() {
			@Override
			public Player execute(Connection conn) throws SQLException {
				PreparedStatement playerStmt = null;
				ResultSet playerSet = null;
				Player player = new Player();
				
				try {
					playerStmt = conn.prepareStatement(
							"select * from players "
							+ "where players.playerID = ?"
				);
				playerStmt.setInt(1, playerID);
				
				playerSet = playerStmt.executeQuery();
				
				while(playerSet.next()) {
					player.setPlayerId(playerID);
					player.setName(playerSet.getString(2));
					player.setRoomId(playerSet.getInt(3));
					player.setStatsId(playerSet.getInt(4));
					player.setCurrency(playerSet.getInt(5));
					player.setPrevRoomId(playerSet.getInt(6));
				}
					
				return player;
				}finally {
					DBUtil.closeQuietly(playerStmt);
				}
			}
		});
	}
	
	/*@Override
	public NPC getNPCByNPCID(int npcID) {
		return executeTransaction(new Transaction<NPC>() {
			@Override
			public NPC execute(Connection conn) throws SQLException {
				PreparedStatement npcStmt = null;
				ResultSet npcSet = null;
				NPC npc = new NPC();
				
				try {
					npcStmt = conn.prepareStatement(
							"select * from npcs "
							+ "where npcs.npcID = ?"
				);
					npcStmt.setInt(1, npcID);
				
					npcSet = npcStmt.executeQuery();
				
				while(npcSet.next()) {
					npc.setNPCId(npcID);
					npc.setName(npcSet.getString(2));
					npc.setType(npcSet.getString(3));
					npc.setRoomId(npcSet.getInt(4));
					npc.setStatsId(npcSet.getInt(5));
					npc.setAggression(npcSet.getInt(6));
					npc.setConversationTreeId(npcSet.getInt(7));
					npc.setCurrency(npcSet.getInt(8));
				}
					
				return npc;
				}finally {
					DBUtil.closeQuietly(npcStmt);
				}
			}
		});
	}*/
	
	@Override
	public NPC getNPCByNPCID(int npcID) {
		return executeTransaction(new Transaction<NPC>() {
			@Override
			public NPC execute(Connection conn) throws SQLException {
				PreparedStatement npcStmt = null;
				ResultSet npcSet = null;
				NPC npc = new NPC();
				
				try {
					npcStmt = conn.prepareStatement(
							"select * from npcs "
							+ "where npcs.npcID = ?"
				);
					npcStmt.setInt(1, npcID);
				
					npcSet = npcStmt.executeQuery();
				
				while(npcSet.next()) {
					npc.setNPCId(npcID);
					npc.setName(npcSet.getString(2));
					npc.setType(npcSet.getString(3));
					npc.setRoomId(npcSet.getInt(4));
					npc.setStatsId(npcSet.getInt(5));
					npc.setAggression(npcSet.getInt(6));
					npc.setConversationTreeId(npcSet.getInt(7));
					npc.setCurrency(npcSet.getInt(8));
				}
					
				return npc;
				}finally {
					DBUtil.closeQuietly(npcStmt);
				}
			}
		});
	}
	/*----------------------------------------------------------------*/

}
