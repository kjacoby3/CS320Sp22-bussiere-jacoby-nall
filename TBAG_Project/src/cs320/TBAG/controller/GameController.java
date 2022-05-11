package cs320.TBAG.controller;

import cs320.TBAG.model.Game;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import cs320.TBAG.model.Actor;
import cs320.TBAG.model.ActorStats;
import cs320.TBAG.model.Equipment;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Room;
import cs320.TBAG.model.NPC;
import cs320.TBAG.model.Map;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Save;
import cs320.TBAG.model.Weapon;
import cs320.TBAG.model.Convo.ConversationNode;
import cs320.TBAG.model.Convo.ConversationResponse;
import cs320.TBAG.model.Convo.ConversationTree;
import cs320.TBAG.model.Convo.PuzzleResponse;
import cs320.TBAG.model.InteractableObj.Interactable;
import cs320.TBAG.model.PuzzleType.EnemyPuzzle;
import cs320.TBAG.database.DatabaseProvider;
import cs320.TBAG.database.DerbyDatabase;
import cs320.TBAG.database.IDatabase;
import cs320.TBAG.dbclass.InitDatabase;

public class GameController {
	private Game model;
	private InventoryController invCreator;
	private Map map;
	private Player player;
	DerbyDatabase db = new DerbyDatabase();
	
	public GameController() {
		invCreator = new InventoryController();
		InitDatabase.init(1);
		db = new DerbyDatabase();
	}
	
	public void loadGame(String playerName, int playerID) {
		MapController mapController = new MapController();
		map=mapController.createMap();
		model.setMap(map);
		createPlayers(playerID);
		model.getPlayer().setName(playerName);
		map.setPrevRoomID(player.getPrevRoomId());
		createNPCs();
		player.setPlayerId(playerID);
	}
	
	public void createNewGame(String playerName, int playerID) {
		MapController mapController = new MapController();
		map=mapController.createMap();
		model.setMap(map);
		createPlayers(playerID);
		model.getPlayer().setName(playerName);
		map.setPrevRoomID(player.getPrevRoomId());
		createNPCs();
		player.setPlayerId(playerID);
		
	}
	
	public void saveGame() {
		//Player player = model.getPlayer();
		//System.out.println("roomID" + player.getRoomId());
		//Player testPlayer = db.getPlayerByPlayerID(player.getPlayerId());
		//System.out.println("preSave " + testPlayer.getRoomId());
		List<Room> rooms = new ArrayList<Room>(model.getMap().getRooms().values());
		db.updatePlayerInfo(player.getPlayerId(), player.getName(), player.getRoomId(), player.getStatsId(), player.getCurrency(), player.getPrevRoomId(), player.getActorStats().getCurLvl());
		db.updateInventories(player, model.getNPCs(), rooms);
		db.updateInteractables(rooms);
		//db.updateActorStats(player, model.getNPCs());		
		//testPlayer = db.getPlayerByPlayerID(player.getPlayerId());
		//System.out.println("postSave " + testPlayer.getRoomId());
	}
	
	
	
	public void createPlayers(int playerID) {
		//List<Player> playerList = db.findAllPlayers();
		//Iterator<Player> iterator = playerList.iterator();
		//while (iterator.hasNext()) {
			//Player player = iterator.next();
		//for(Player player : playerList) {
		//player = new Player();
		//player.setRoomId(1);
		
		player = db.getPlayerByPlayerID(playerID);
		ActorStats stats = db.findActorStatsByPlayerId(playerID);

		player.setActorStats(stats);
		Inventory inv = invCreator.getPlayerInventory(playerID);
		player.setInventory(inv);

			
			for(Weapon weap : inv.getWeapons().values()) {
				if(weap.getEquipped()) {
					player.equipWeapon(weap);
				}
			}
			
			for(Equipment equipped : inv.getEquipment().values()) {
				if(equipped.getEquipped()) {
					player.equipEquipment(equipped);
				}
			}
		player.setLocation(map.getRoom(player.getRoomId()));	
		//player.setLocation(db.getRoomByID(player.getRoomId()));	
			//Add player to game list
		model.setPlayer(player);
		//}
	}
	
	public void createNPCs() {
		List<NPC> npcList = db.findAllNPCs();
		Iterator<NPC> iterator = npcList.iterator();
		while (iterator.hasNext()) {
			NPC npc = iterator.next();
			ActorStats stats = db.findActorStatsByNPCId(npc.getNPCId());
			npc.setActorStats(stats);
			Inventory inv = invCreator.getNPCInventory(npc.getNPCId());
			npc.setInventory(inv);
			
			for(Weapon weap : inv.getWeapons().values()) {
				if(weap.getEquipped()) {
					npc.equipWeapon(weap);
				}
			}
			
			for(Equipment equipped : inv.getEquipment().values()) {
				if(equipped.getEquipped()) {
					npc.equipEquipment(equipped);
				}
			}
			
			//ConversationTree convoTree = db.constructConversationTreeByNPCID(npc.getNPCId());
			System.out.println((db.constructConversationTreeByNPCID(npc.getNPCId()).getConversationTreeMap()));
			npc.setConversationTree(db.constructConversationTreeByNPCID(npc.getNPCId()));
			npc.setLocation(map.getRoom(npc.getRoomId()));
			//npc.setLocation(db.getRoomByID(npc.getRoomId()));
			npc.getLocation().addNPCInRoom(npc);
//			System.out.println("Database roomID: " + (db.getRoomByID(npc.getRoomId()).getRoomID()));
//			System.out.println("NPC roomID: " + npc.getRoomId());
//			System.out.println("Location roomID: " + npc.getLocation().getRoomID());
			System.out.println("NPC name: " + npc.getName());
			System.out.println("NPC ID: " + npc.getNPCId());
//			System.out.println("NPC loaded into room: " + npc.getLocation().getRoomID() + " " + npc.getLocation().getNPCsInRoom());
			System.out.println("NPC convoTree: " + npc.getConversationTree());
//			System.out.println("NPC node1: " + npc.getConversationTree().getNode(1));
			//Add npc to game list
			model.addNPC(npc);
			
			for(Room room : map.getRooms().values()) {
				if(room.getNPCsInRoom().size() > 0) {
					for(NPC n : room.getNPCsInRoom()) {
						if(n.getConversationTree() != null) {
							if(n.getConversationTree().getConversationTreeMap() != null) {
								for(ConversationNode node : n.getConversationTree().getConversationTreeMap().values()) {
									for(ConversationResponse resp : node.getResponseList()) {
										if(resp instanceof PuzzleResponse) {
											if(((PuzzleResponse)resp).getPuzzle() instanceof EnemyPuzzle) {
												PuzzleResponse puzzleResp = (PuzzleResponse)resp;
												EnemyPuzzle enemyPuzz = (EnemyPuzzle) puzzleResp.getPuzzle();
												if(enemyPuzz.getNPCId() == npc.getNPCId()) {
													enemyPuzz.setNPC(npc);
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
				if(room.getRoomInteractables().size() > 0) {
					for(Interactable obj : room.getRoomInteractables()) {
						if(obj.getPuzzle() != null) {
							if(obj.getPuzzle() instanceof EnemyPuzzle) {
								if(((EnemyPuzzle) obj.getPuzzle()).getNPCId() == npc.getNPCId()) {
									System.out.println("NPC EnemyPuzzle Found");
									((EnemyPuzzle) obj.getPuzzle()).setNPC(npc);
								}
							}
						}
					}
				}
			}
		}
	}
	

	public void setModel(Game model) {
		this.model = model;
	}

//	public void newGame() {
//		model.addEmptyPlayer();
//		model.addEmptyNPC();
//	}
//	
//	public void saveGame() {
//		model.getSave().setActorsList(model.getActorsList());
//		model.getSave().setInventories(model.getInventories());
//		model.getSave().setMap(model.getMap());
//		
//	}
}