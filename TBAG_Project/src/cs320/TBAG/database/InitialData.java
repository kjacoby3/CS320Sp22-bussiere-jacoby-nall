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
import cs320.TBAG.model.Convo.BuyResponse;
import cs320.TBAG.model.Convo.ConversationNode;
import cs320.TBAG.model.Convo.ConversationTree;
import cs320.TBAG.model.Convo.DefaultResponse;
import cs320.TBAG.model.Convo.EndResponse;
import cs320.TBAG.model.Convo.PuzzleResponse;
import cs320.TBAG.model.Convo.RewardResponse;
import cs320.TBAG.model.Convo.SellResponse;
import cs320.TBAG.model.InteractableObj.Chest;
import cs320.TBAG.model.InteractableObj.Door;
import cs320.TBAG.model.InteractableObj.Keypad;
import cs320.TBAG.model.InteractableObj.Sign;
import cs320.TBAG.model.PuzzleType.EnemyPuzzle;
import cs320.TBAG.model.PuzzleType.KeyPuzzle;
import cs320.TBAG.model.PuzzleType.PinPuzzle;

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
				room.setRoomLevel(Integer.parseInt(i.next()));
				room.setRoomPrevVisit(Boolean.parseBoolean(i.next()));
				room.setRoomGameID(Integer.parseInt(i.next()));
				//room.setRoomInventory(i.next());
				
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
				resp.setDefaultResponseId(responseId++);
				
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
				resp.setEndResponseId(responseId++);
				
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
	
	public static List<PuzzleResponse> getPuzzleResponses() throws IOException {
		List<PuzzleResponse> puzzleResponseList = new ArrayList<PuzzleResponse>();
		ReadCSV readPuzzleResponses = new ReadCSV("puzzleResponse.csv");
		try {
			int puzzleResponseId = 1;
			while(true) {
				List<String> tuple = readPuzzleResponses.next();
				if(tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				PuzzleResponse resp = new PuzzleResponse();
				
				Integer.parseInt(i.next());
				resp.setPuzzleResponseId(puzzleResponseId++);
				
				resp.setConvoTreeId(Integer.parseInt(i.next()));
				resp.setNodeId(Integer.parseInt(i.next()));
				resp.setResponseStr(i.next());
				resp.setResultNode(Integer.parseInt(i.next()));
				resp.setPuzzleId(Integer.parseInt(i.next()));
				resp.setCompleteResultNode(Integer.parseInt(i.next()));
				
				puzzleResponseList.add(resp);
			}
			return puzzleResponseList;
		} finally {
			readPuzzleResponses.close();
		}
	}
	
	public static List<RewardResponse> getRewardResponses() throws IOException {
		List<RewardResponse> rewardResponseList = new ArrayList<RewardResponse>();
		ReadCSV readRewardResponses = new ReadCSV("rewardResponse.csv");
		try {
			int rewardResponseId = 1;
			while(true) {
				List<String> tuple = readRewardResponses.next();
				if(tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				RewardResponse resp = new RewardResponse();
				
				Integer.parseInt(i.next());
				resp.setRewardResponseId(rewardResponseId++);
				
				resp.setConvoTreeId(Integer.parseInt(i.next()));
				resp.setNodeId(Integer.parseInt(i.next()));
				resp.setResponseStr(i.next());
				resp.setResultNode(Integer.parseInt(i.next()));
				resp.setRewardItemId(Integer.parseInt(i.next()));
				resp.setRewardCurrency(Integer.parseInt(i.next()));
				resp.setRewardExp(Integer.parseInt(i.next()));
				resp.setCollected(Boolean.parseBoolean(i.next()));
				
				rewardResponseList.add(resp);
			}
			return rewardResponseList;
		} finally {
			readRewardResponses.close();
		}
	}
	
	public static List<BuyResponse> getBuyResponses() throws IOException {
		List<BuyResponse> buyResponseList = new ArrayList<BuyResponse>();
		ReadCSV readBuyResponses = new ReadCSV("buyResponse.csv");
		try {
			int buyResponseId = 1;
			while(true) {
				List<String> tuple = readBuyResponses.next();
				if(tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				BuyResponse resp = new BuyResponse();
				
				Integer.parseInt(i.next());
				resp.setBuyResponseId(buyResponseId++);
				
				resp.setConvoTreeId(Integer.parseInt(i.next()));
				resp.setNodeId(Integer.parseInt(i.next()));
				//resp.setResponseStr(i.next());
				resp.setResultNode(Integer.parseInt(i.next()));
				resp.setBuyItemId(Integer.parseInt(i.next()));
				resp.setBought(Boolean.parseBoolean(i.next()));
				resp.setFailedNode(Integer.parseInt(i.next()));
				
				buyResponseList.add(resp);
			}
			return buyResponseList;
		} finally {
			readBuyResponses.close();
		}
	}
	
	public static List<SellResponse> getSellResponses() throws IOException {
		List<SellResponse> sellResponseList = new ArrayList<SellResponse>();
		ReadCSV readSellResponses = new ReadCSV("sellResponse.csv");
		try {
			int sellResponseId = 1;
			while(true) {
				List<String> tuple = readSellResponses.next();
				if(tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				SellResponse resp = new SellResponse();
				
				Integer.parseInt(i.next());
				resp.setSellResponseId(sellResponseId++);
				
				resp.setConvoTreeId(Integer.parseInt(i.next()));
				resp.setNodeId(Integer.parseInt(i.next()));
				//resp.setResponseStr(i.next());
				resp.setResultNode(Integer.parseInt(i.next()));
				resp.setSellItemId(Integer.parseInt(i.next()));
				resp.setSold(Boolean.parseBoolean(i.next()));
				resp.setFailedNode(Integer.parseInt(i.next()));
				
				sellResponseList.add(resp);
			}
			return sellResponseList;
		} finally {
			readSellResponses.close();
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
	/*********************************/
	
	/*** Puzzles */
	public static List<KeyPuzzle> getKeyPuzzles() throws IOException {
		List<KeyPuzzle> keyPuzzleList = new ArrayList<KeyPuzzle>();
		ReadCSV readKeyPuzzles = new ReadCSV("keyPuzzle.csv");
		try {
			int keyPuzzleId = 1;
			while(true) {
				List<String> tuple = readKeyPuzzles.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				KeyPuzzle puzzle = new KeyPuzzle();
				Integer.parseInt(i.next());
				puzzle.setKeyPuzzleId(keyPuzzleId++);

				puzzle.setPuzzleId(Integer.parseInt(i.next()));
				puzzle.setTreasureId(Integer.parseInt(i.next()));
				puzzle.setComplete(Boolean.parseBoolean(i.next()));
				puzzle.setHint(i.next());
				puzzle.setCompleteMSG(i.next());
				puzzle.setCurrencyReward(Integer.parseInt(i.next()));
				puzzle.setExpReward(Integer.parseInt(i.next()));
				puzzle.setRewardItemId(Integer.parseInt(i.next()));
				
				keyPuzzleList.add(puzzle);
			}
			return keyPuzzleList;
		} finally {
			readKeyPuzzles.close();
		}
	}
	
	public static List<PinPuzzle> getPinPuzzles() throws IOException {
		List<PinPuzzle> pinPuzzleList = new ArrayList<PinPuzzle>();
		ReadCSV readPinPuzzles = new ReadCSV("pinPuzzle.csv");
		try {
			int pinPuzzleId = 1;
			while(true) {
				List<String> tuple = readPinPuzzles.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				PinPuzzle puzzle = new PinPuzzle();
				Integer.parseInt(i.next());
				puzzle.setPinPuzzleId(pinPuzzleId++);

				puzzle.setPuzzleId(Integer.parseInt(i.next()));
				//puzzle.setTreasureId(Integer.parseInt(i.next()));
				puzzle.setKey(i.next());
				puzzle.setComplete(Boolean.parseBoolean(i.next()));
				puzzle.setHint(i.next());
				puzzle.setCompleteMSG(i.next());
				puzzle.setCurrencyReward(Integer.parseInt(i.next()));
				puzzle.setExpReward(Integer.parseInt(i.next()));
				puzzle.setRewardItemId(Integer.parseInt(i.next()));
				
				pinPuzzleList.add(puzzle);
			}
			return pinPuzzleList;
		} finally {
			readPinPuzzles.close();
		}
	}
	
	public static List<EnemyPuzzle> getEnemyPuzzles() throws IOException {
		List<EnemyPuzzle> enemyPuzzleList = new ArrayList<EnemyPuzzle>();
		ReadCSV readEnemyPuzzles = new ReadCSV("enemyPuzzle.csv");
		try {
			int enemyPuzzleId = 1;
			while(true) {
				List<String> tuple = readEnemyPuzzles.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				EnemyPuzzle puzzle = new EnemyPuzzle();
				Integer.parseInt(i.next());
				puzzle.setEnemyPuzzleId(enemyPuzzleId++);

				puzzle.setPuzzleId(Integer.parseInt(i.next()));
				puzzle.setNPCId(Integer.parseInt(i.next()));
				puzzle.setComplete(Boolean.parseBoolean(i.next()));
				puzzle.setHint(i.next());
				puzzle.setCompleteMSG(i.next());
				puzzle.setCurrencyReward(Integer.parseInt(i.next()));
				puzzle.setExpReward(Integer.parseInt(i.next()));
				puzzle.setRewardItemId(Integer.parseInt(i.next()));
				
				enemyPuzzleList.add(puzzle);
			}
			return enemyPuzzleList;
		} finally {
			readEnemyPuzzles.close();
		}
	}
	/*******************************/
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
				
				door.setInteractableId(Integer.parseInt(i.next()));
				door.setName(i.next());
				door.setDescription(i.next());
				door.setActivated(Boolean.parseBoolean(i.next()));
				door.setRoomId(Integer.parseInt(i.next()));
				door.setPuzzleId(Integer.parseInt(i.next()));
				door.setDirection(i.next());
				
				doorList.add(door);
			}
			return doorList;
		} finally {
			readDoors.close();
		}
	}
	
	public static List<Chest> getChests() throws IOException {
		List<Chest> chestList = new ArrayList<Chest>();
		ReadCSV readChests = new ReadCSV("chest.csv");
		try {
			int chestId = 1;
			while(true) {
				List<String> tuple = readChests.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Chest chest = new Chest();
				Integer.parseInt(i.next());
				chest.setChestId(chestId++);
				
				chest.setInteractableId(Integer.parseInt(i.next()));
				chest.setName(i.next());
				chest.setDescription(i.next());
				chest.setActivated(Boolean.parseBoolean(i.next()));
				chest.setRoomId(Integer.parseInt(i.next()));
				chest.setPuzzleId(Integer.parseInt(i.next()));
				
				chestList.add(chest);
			}
			return chestList;
		} finally {
			readChests.close();
		}
	}
	
	public static List<Keypad> getKeypads() throws IOException {
		List<Keypad> keypadList = new ArrayList<Keypad>();
		ReadCSV readKeypads = new ReadCSV("keypad.csv");
		try {
			int keypadId = 1;
			while(true) {
				List<String> tuple = readKeypads.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Keypad keypad = new Keypad();
				Integer.parseInt(i.next());
				keypad.setKeypadId(keypadId++);
				
				keypad.setInteractableId(Integer.parseInt(i.next()));
				keypad.setName(i.next());
				keypad.setDescription(i.next());
				keypad.setActivated(Boolean.parseBoolean(i.next()));
				keypad.setRoomId(Integer.parseInt(i.next()));
				keypad.setPuzzleId(Integer.parseInt(i.next()));
				
				keypadList.add(keypad);
			}
			return keypadList;
		} finally {
			readKeypads.close();
		}
	}
	
	public static List<Sign> getSigns() throws IOException {
		List<Sign> signList = new ArrayList<Sign>();
		ReadCSV readSigns = new ReadCSV("sign.csv");
		try {
			int signId = 1;
			while(true) {
				List<String> tuple = readSigns.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Sign sign = new Sign();
				Integer.parseInt(i.next());
				sign.setSignId(signId++);
				
				sign.setInteractableId(Integer.parseInt(i.next()));
				sign.setName(i.next());
				sign.setDescription(i.next());
				sign.setActivated(Boolean.parseBoolean(i.next()));
				sign.setMessage(i.next());
				sign.setRoomId(Integer.parseInt(i.next()));
				sign.setPuzzleId(Integer.parseInt(i.next()));
				
				signList.add(sign);
			}
			return signList;
		} finally {
			readSigns.close();
		}
	}
	
	/*************************/
	
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
				int itemID = Integer.parseInt(i.next());
				String name = i.next();
				int price = Integer.parseInt(i.next());
				int damage = Integer.parseInt(i.next());
				int playerID = Integer.parseInt(i.next());
				int roomID = Integer.parseInt(i.next());
				int npcID = Integer.parseInt(i.next());
				boolean equipped = Boolean.parseBoolean(i.next());
				
				weaponList.add(new Weapon(itemID, name, price, damage, playerID, roomID, npcID, equipped));
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
				
				int itemID = Integer.parseInt(i.next());
				String name = i.next();
				int price = Integer.parseInt(i.next());
				int defenseMod = Integer.parseInt(i.next());
				int hpMod = Integer.parseInt(i.next());
				int speedMod = Integer.parseInt(i.next());
				int playerID = Integer.parseInt(i.next());
				int roomID = Integer.parseInt(i.next());
				int npcID = Integer.parseInt(i.next());
				boolean equipped = Boolean.parseBoolean(i.next());
				
				equipmentList.add(new Equipment(itemID, name, price, defenseMod,hpMod, speedMod, playerID, roomID, npcID, equipped));
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
				int itemID = Integer.parseInt(i.next());
				String name = i.next();
				int price = Integer.parseInt(i.next());
				int playerID = Integer.parseInt(i.next());
				int roomID = Integer.parseInt(i.next());
				int npcID = Integer.parseInt(i.next());
				
				usableList.add(new Usable(itemID, name, price, playerID, roomID, npcID));
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
				int itemID = Integer.parseInt(i.next());
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
				
				consumableList.add(new Consumable(itemID, name, price, curHPMod, maxHPMod, dmgMod, defMod, spdMod, playerID, roomID, npcID));
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
				int itemID = Integer.parseInt(i.next());
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
				
				treasureList.add(new Treasure(itemID, name, price, playerID, roomID, npcID));
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
				int itemID = Integer.parseInt(i.next());
				String name = i.next();
				int price = Integer.parseInt(i.next());
				int playerID = Integer.parseInt(i.next());
				int roomID = Integer.parseInt(i.next());
				int npcID = Integer.parseInt(i.next());
				
				trophyList.add(new Trophy(itemID, name, price, playerID, roomID, npcID));
			}
			return trophyList;
		} finally {
			readTrophies.close();
		}
	}
	
	

	
}