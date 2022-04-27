package cs320.TBAG.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs320.TBAG.model.Actor;
import cs320.TBAG.model.ActorStats;
import cs320.TBAG.model.Consumable;
import cs320.TBAG.model.Equipment;
import cs320.TBAG.model.NPC;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Room;
import cs320.TBAG.model.RoomConnection;
import cs320.TBAG.model.Treasure;
import cs320.TBAG.model.Trophy;
import cs320.TBAG.model.Usable;
import cs320.TBAG.model.Weapon;
import cs320.TBAG.model.Convo.ConversationNode;
import cs320.TBAG.model.Convo.ConversationTree;
import cs320.TBAG.model.Convo.DefaultResponse;
import cs320.TBAG.model.Convo.EndResponse;
import cs320.TBAG.model.InteractableObj.Door;
import cs320.TBAG.model.PuzzleType.KeyPuzzle;

public class InitialData {
	
	public static List<RoomConnection> getRoomConnections() throws IOException {
		List<RoomConnection> roomConnectionList = new ArrayList<RoomConnection>();
		ReadCSV readRoomConnection = new ReadCSV("roomConnections1.csv");
		try {
			// auto-generated primary key for rooms table
			//Integer roomId = 1;
			while (true) {
				List<String> tuple = readRoomConnection.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				RoomConnection roomConnection = new RoomConnection();
				roomConnection.setRoomID(Integer.parseInt(i.next()));
				roomConnection.setNorth(Integer.parseInt(i.next()));
				roomConnection.setEast(Integer.parseInt(i.next()));
				roomConnection.setSouth(Integer.parseInt(i.next()));
				roomConnection.setWest(Integer.parseInt(i.next()));
				//roomConnection.setExit(Integer.parseInt(i.next()));
					
				//These next two will not be in the room CSV. We need to figure out how to create these
				//room.setRoomItems(null);
				//room.setNPCsInRoom(null);
				
				//These next two will need to be Lists created from separate CSVs than the current Room CSVs
				//room.setAvailableExits(i.next());
				//room.setOtherExitOptions(i.next());
				
				roomConnectionList.add(roomConnection);
			}
			return roomConnectionList;
		} finally {
			readRoomConnection.close();
		}
	}
	
	public static List<Room> getRooms() throws IOException {
		List<Room> roomList = new ArrayList<Room>();
		ReadCSV readRooms = new ReadCSV("rooms1.csv");
		try {
			// auto-generated primary key for rooms table
			//Integer roomId = 1;
			while (true) {
				List<String> tuple = readRooms.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Room room = new Room();
				String id = i.next();
				System.out.println(id);
				Integer intID = Integer.parseInt(id);
				System.out.println(intID);
				room.setRoomID(intID);
				//room.setRoomID(Integer.parseInt(i.next()));
				room.setRoomName(i.next());
				room.setRoomDescripLong(i.next());
				room.setRoomDescripShort(i.next());
				room.setRoomConnections(Integer.parseInt(i.next()));
				room.setRoomUseable(Integer.parseInt(i.next()));
				room.setRoomTreasure(Integer.parseInt(i.next()));
				room.setRoomTrophy(Integer.parseInt(i.next()));
				room.setRoomEquipment(Integer.parseInt(i.next()));
				room.setRoomWeapon(Integer.parseInt(i.next()));
				room.setRoomActor(Integer.parseInt(i.next()));
				room.setRoomLevel(Integer.parseInt(i.next()));
	
				//These next two will not be in the room CSV. We need to figure out how to create these
				//room.setRoomItems(null);
				//room.setNPCsInRoom(null);
				
				//These next two will need to be Lists created from separate CSVs than the current Room CSVs
				//room.setAvailableExits(i.next());
				//room.setOtherExitOptions(i.next());
				
				roomList.add(room);
			}
			return roomList;
		} finally {
			readRooms.close();
		}
	}
	
	public static List<ActorStats> getActorStats() throws IOException {
		List<ActorStats> actorStatsList = new ArrayList<ActorStats>();
		ReadCSV readActorStats = new ReadCSV("actorStats.csv");
		try {
			Integer statsId = 1;
			while(true) {
				List<String> tuple = readActorStats.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				ActorStats actorStats = new ActorStats();
				
				Integer.parseInt(i.next());
				actorStats.setStatsId(statsId++);
				
				actorStats.setCurHP(Integer.parseInt(i.next()));
				actorStats.setMaxHP(Integer.parseInt(i.next()));
				actorStats.setDmg(Integer.parseInt(i.next()));
				actorStats.setDef(Integer.parseInt(i.next()));
				actorStats.setSpd(Integer.parseInt(i.next()));
				actorStats.setCurExp(Integer.parseInt(i.next()));
				actorStats.setMaxExp(Integer.parseInt(i.next()));
				actorStats.setCurLvl(Integer.parseInt(i.next()));
				
				actorStatsList.add(actorStats);
			}
			return actorStatsList;
		} finally {
			readActorStats.close();
		}
	}
	
	public static List<Player> getPartialPlayers() throws IOException {
		List<Player> partialPlayerList = new ArrayList<Player>();
		ReadCSV readPlayers = new ReadCSV("player.csv");
		try {
			Integer playerId = 1;
			while(true) {
				List<String> tuple = readPlayers.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Player player = new Player();
				
				Integer.parseInt(i.next());
				player.setPlayerId(playerId++);
				
				player.setName(i.next());
				player.setCurrency(Integer.parseInt(i.next()));
				
				partialPlayerList.add(player);
			}
			return partialPlayerList;
		} finally {
			readPlayers.close();
		}
	}
	
	public static List<Player> getFullPlayers() throws IOException {
		List<Player> fullPlayerList = new ArrayList<Player>();
		ReadCSV readFullPlayers = new ReadCSV("fullPlayer.csv");
		try {
			while(true) {
				List<String> tuple = readFullPlayers.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Player player = new Player();
				
				player.setPlayerId(Integer.parseInt(i.next()));
				player.setRoomId(Integer.parseInt(i.next()));
				player.setEqWeapId(Integer.parseInt(i.next()));
				player.setEquippedId(Integer.parseInt(i.next()));
				player.setStatsId(Integer.parseInt(i.next()));
				
				
				fullPlayerList.add(player);
			}
			return fullPlayerList;
		} finally {
			readFullPlayers.close();
		}
	}
	
	public static List<NPC> getPartialNPCs() throws IOException {
		List<NPC> partialNPCList = new ArrayList<NPC>();
		ReadCSV readNPCs = new ReadCSV("npc.csv");
		try {
			Integer NPCId = 1;
			while(true) {
				List<String> tuple = readNPCs.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				NPC npc = new NPC();
				
				Integer.parseInt(i.next());
				npc.setNPCId(NPCId++);
				
				npc.setName(i.next());
				npc.setType(i.next());
				npc.setAggression(Integer.parseInt(i.next()));
				npc.setCurrency(Integer.parseInt(i.next()));
				
				partialNPCList.add(npc);
			}
			return partialNPCList;
		} finally {
			readNPCs.close();
		}
	}
	
	public static List<NPC> getFullNPCs() throws IOException {
		List<NPC> partialNPCList = new ArrayList<NPC>();
		ReadCSV readFullNPCs = new ReadCSV("fullNPC.csv");
		try {
			while(true) {
				List<String> tuple = readFullNPCs.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				NPC npc = new NPC();

				npc.setNPCId(Integer.parseInt(i.next()));
				npc.setRoomId(Integer.parseInt(i.next()));
				npc.setEqWeapId(Integer.parseInt(i.next()));
				npc.setEquippedId(Integer.parseInt(i.next()));
				npc.setStatsId(Integer.parseInt(i.next()));
				npc.setConversationTreeId(Integer.parseInt(i.next()));
				
				partialNPCList.add(npc);
			}
			return partialNPCList;
		} finally {
			readFullNPCs.close();
		}
	}
	
	/*** Conversations */
	public static List<DefaultResponse> getDefaultResponses() throws IOException {
		List<DefaultResponse> defaultResponseList = new ArrayList<DefaultResponse>();
		ReadCSV readDefaultResponses = new ReadCSV("defaultResponse.csv");
		try {
			int responseId = 1;
			while(true) {
				List<String> tuple = readDefaultResponses.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				DefaultResponse resp = new DefaultResponse();

				Integer.parseInt(i.next());
				resp.setResponseId(responseId++);
				
				resp.setConvoTreeId(Integer.parseInt(i.next()));
				resp.setNodeId(Integer.parseInt(i.next()));
				resp.setResponseStr(i.next());
				resp.setResultNode(Integer.parseInt(i.next()));
				
				defaultResponseList.add(resp);
			}
			return defaultResponseList;
		} finally {
			readDefaultResponses.close();
		}
	}
	
	public static List<EndResponse> getEndResponses() throws IOException {
		List<EndResponse> endResponseList = new ArrayList<EndResponse>();
		ReadCSV readEndResponses = new ReadCSV("endResponse.csv");
		try {
			int responseId = 1;
			while(true) {
				List<String> tuple = readEndResponses.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				EndResponse resp = new EndResponse();

				Integer.parseInt(i.next());
				resp.setResponseId(responseId++);
				
				resp.setConvoTreeId(Integer.parseInt(i.next()));
				resp.setNodeId(Integer.parseInt(i.next()));
				resp.setResponseStr(i.next());
				resp.setResultNode(Integer.parseInt(i.next()));
				
				endResponseList.add(resp);
			}
			return endResponseList;
		} finally {
			readEndResponses.close();
		}
	}
	
	public static List<ConversationNode> getConversationNodes() throws IOException {
		List<ConversationNode> conversationNodeList = new ArrayList<ConversationNode>();
		ReadCSV readConversationNodes = new ReadCSV("conversationNode.csv");
		try {
			int nodeId = 1;
			while(true) {
				List<String> tuple = readConversationNodes.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				ConversationNode node = new ConversationNode();

				Integer.parseInt(i.next());
				node.setConvoNodeId(nodeId++);
				node.setConvoTreeId(Integer.parseInt(i.next()));
				node.setConvoNodeKey(Integer.parseInt(i.next()));
				node.setStatement(i.next());
				
				conversationNodeList.add(node);
			}
			return conversationNodeList;
		} finally {
			readConversationNodes.close();
		}
	}
	
	public static List<ConversationTree> getConversationTrees() throws IOException {
		List<ConversationTree> conversationTreeList = new ArrayList<ConversationTree>();
		ReadCSV readConversationTrees = new ReadCSV("conversationTree.csv");
		try {
			int treeId = 1;
			while(true) {
				List<String> tuple = readConversationTrees.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				ConversationTree tree = new ConversationTree();

				Integer.parseInt(i.next());
				tree.setConvoTreeId(treeId++);
				tree.setNPCId(Integer.parseInt(i.next()));
				
				conversationTreeList.add(tree);
			}
			return conversationTreeList;
		} finally {
			readConversationTrees.close();
		}
	}
	
	
	/*** Puzzles */
	public static List<KeyPuzzle> getKeyPuzzles() throws IOException {
		List<KeyPuzzle> keyPuzzleList = new ArrayList<KeyPuzzle>();
		ReadCSV readKeyPuzzles = new ReadCSV("keyPuzzle.csv");
		try {
			while(true) {
				List<String> tuple = readKeyPuzzles.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				KeyPuzzle puzzle = new KeyPuzzle();

				puzzle.setPuzzleId(Integer.parseInt(i.next()));
				puzzle.setInteractableId(Integer.parseInt(i.next()));
				puzzle.setTreasureId(Integer.parseInt(i.next()));
				
				keyPuzzleList.add(puzzle);
			}
			return keyPuzzleList;
		} finally {
			readKeyPuzzles.close();
		}
	}
	
	/*** Interactable Objects */
	public static List<Door> getDoors() throws IOException {
		List<Door> doorList = new ArrayList<Door>();
		ReadCSV readDoors = new ReadCSV("door.csv");
		try {
			int doorId = 1;
			while(true) {
				List<String> tuple = readDoors.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Door door = new Door();
				Integer.parseInt(i.next());
				door.setDoorId(doorId++);
				
				door.setName(i.next());
				door.setDescription(i.next());
				door.setActivated(Boolean.parseBoolean(i.next()));
				door.setRoomId(Integer.parseInt(i.next()));
				door.setPuzzleId(Integer.parseInt(i.next()));
				
				doorList.add(door);
			}
			return doorList;
		} finally {
			readDoors.close();
		}
	}
	
	public static List<Weapon> getWeapons() throws IOException {
		List<Weapon> weaponList = new ArrayList<Weapon>();
		ReadCSV readWeapons = new ReadCSV("Weapons.csv");
		try {
			while(true) {
				List<String> tuple = readWeapons.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				//Integer.parseInt(i.next());
				
				String name = i.next();
				int price = Integer.parseInt(i.next());
				int damage = Integer.parseInt(i.next());
				int playerID = Integer.parseInt(i.next());
				int roomID = Integer.parseInt(i.next());
				int npcID = Integer.parseInt(i.next());
				boolean equipped = Boolean.parseBoolean(i.next());
				
				weaponList.add(new Weapon(name, price, damage, playerID, roomID, npcID, equipped));
			}
			return weaponList;
		} finally {
			readWeapons.close();
		}
	}
	public static List<Equipment> getEquipment() throws IOException {
		List<Equipment> equipmentList = new ArrayList<Equipment>();
		ReadCSV readEquipment = new ReadCSV("Equipment.csv");
		try {
			while(true) {
				List<String> tuple = readEquipment.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				//Integer.parseInt(i.next());
				
				String name = i.next();
				int price = Integer.parseInt(i.next());
				int defenseMod = Integer.parseInt(i.next());
				int hpMod = Integer.parseInt(i.next());
				int speedMod = Integer.parseInt(i.next());
				int playerID = Integer.parseInt(i.next());
				int roomID = Integer.parseInt(i.next());
				int npcID = Integer.parseInt(i.next());
				boolean equipped = Boolean.parseBoolean(i.next());
				
				equipmentList.add(new Equipment(name, price, defenseMod,hpMod, speedMod, playerID, roomID, npcID, equipped));
			}
			return equipmentList;
		} finally {
			readEquipment.close();
		}
	}
	public static List<Usable> getUsables() throws IOException {
		List<Usable> usableList = new ArrayList<Usable>();
		ReadCSV readUsables = new ReadCSV("Usables.csv");
		try {
			while(true) {
				List<String> tuple = readUsables.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				//Integer.parseInt(i.next());
				
				String name = i.next();
				int price = Integer.parseInt(i.next());
				int playerID = Integer.parseInt(i.next());
				int roomID = Integer.parseInt(i.next());
				int npcID = Integer.parseInt(i.next());
				
				usableList.add(new Usable(name, price, playerID, roomID, npcID));
			}
			return usableList;
		} finally {
			readUsables.close();
		}
	}
	public static List<Consumable> getConsumables() throws IOException {
		List<Consumable> consumableList = new ArrayList<Consumable>();
		ReadCSV readConsumables = new ReadCSV("Consumables.csv");
		try {
			while(true) {
				List<String> tuple = readConsumables.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				//Integer.parseInt(i.next());
				
				String name = i.next();
				int price = Integer.parseInt(i.next());
				int curHPMod = Integer.parseInt(i.next());
				int maxHPMod = Integer.parseInt(i.next());
				int dmgMod = Integer.parseInt(i.next());
				int defMod = Integer.parseInt(i.next());
				int spdMod = Integer.parseInt(i.next());
				int playerID = Integer.parseInt(i.next());
				int roomID = Integer.parseInt(i.next());
				int npcID = Integer.parseInt(i.next());
				
				consumableList.add(new Consumable(name, price, curHPMod, maxHPMod, dmgMod, defMod, spdMod, playerID, roomID, npcID));
			}
			return consumableList;
		} finally {
			readConsumables.close();
		}
	}
	public static List<Treasure> getTreasures() throws IOException {
		List<Treasure> treasureList = new ArrayList<Treasure>();
		ReadCSV readTreasures = new ReadCSV("Treasures.csv");
		try {
			while(true) {
				List<String> tuple = readTreasures.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				//Integer.parseInt(i.next());
				
				String name = i.next();
				String num = i.next();
				System.out.println(num);
				Integer numInt = Integer.parseInt(num);
				System.out.println(numInt);
				//int price = Integer.parseInt(i.next());
				int price = numInt;
				int playerID = Integer.parseInt(i.next());
				int roomID = Integer.parseInt(i.next());
				int npcID = Integer.parseInt(i.next());
				
				treasureList.add(new Treasure(name, price, playerID, roomID, npcID));
			}
			return treasureList;
		} finally {
			readTreasures.close();
		}
	}
	public static List<Trophy> getTrophies() throws IOException {
		List<Trophy> trophyList = new ArrayList<Trophy>();
		ReadCSV readTrophies = new ReadCSV("Trophies.csv");
		try {
			while(true) {
				List<String> tuple = readTrophies.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				//Integer.parseInt(i.next());
				
				String name = i.next();
				int price = Integer.parseInt(i.next());
				int playerID = Integer.parseInt(i.next());
				int roomID = Integer.parseInt(i.next());
				int npcID = Integer.parseInt(i.next());
				
				trophyList.add(new Trophy(name, price, playerID, roomID, npcID));
			}
			return trophyList;
		} finally {
			readTrophies.close();
		}
	}
	
	

	
}