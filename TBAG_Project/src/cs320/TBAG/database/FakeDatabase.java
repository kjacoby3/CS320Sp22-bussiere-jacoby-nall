package cs320.TBAG.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs320.TBAG.model.ActorStats;
import cs320.TBAG.model.NPC;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Room;
import cs320.TBAG.model.RoomConnection;
import cs320.TBAG.model.Convo.ConversationNode;
import cs320.TBAG.model.Convo.ConversationTree;
import cs320.TBAG.model.Convo.DefaultResponse;
import cs320.TBAG.model.Convo.EndResponse;
import cs320.TBAG.model.InteractableObj.Door;
import cs320.TBAG.model.PuzzleType.KeyPuzzle;

public class FakeDatabase implements IDatabase {
	
	private List<Player> playerList;
	private List<NPC> npcList;
	private List<ActorStats> actorStatsList;
	private List<ConversationTree> convoTreeList;
	private List<ConversationNode> convoNodeList;
	private List<DefaultResponse> defaultResponseList;
	private List<EndResponse> endResponseList;
	private List<KeyPuzzle> keyPuzzleList;
	private List<Door> doorList;
	private List<Room> roomList;
	private List<RoomConnection> roomConnectionList = new ArrayList<RoomConnection>();
	
	public FakeDatabase() {
		playerList = new ArrayList<Player>();
		npcList = new ArrayList<NPC>();
		actorStatsList = new ArrayList<ActorStats>();
		convoTreeList = new ArrayList<ConversationTree>();
		convoNodeList = new ArrayList<ConversationNode>();
		defaultResponseList = new ArrayList<DefaultResponse>();
		endResponseList = new ArrayList<EndResponse>();
		keyPuzzleList = new ArrayList<KeyPuzzle>();
		doorList = new ArrayList<Door>();
		roomList = new ArrayList<Room>();
		roomConnectionList = new ArrayList<RoomConnection>();
		
		readInitialData();
		
	}
	
	public void readInitialData() {
		try {
			playerList.addAll(InitialData.getFullPlayers());
			npcList.addAll(InitialData.getFullNPCs());
			convoTreeList.addAll(InitialData.getConversationTrees());
			convoNodeList.addAll(InitialData.getConversationNodes());
			defaultResponseList.addAll(InitialData.getDefaultResponses());
			endResponseList.addAll(InitialData.getEndResponses());
			roomList.addAll(InitialData.getRooms());
			roomConnectionList.addAll(InitialData.getRoomConnections());
		}
		catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
	
	
	
	/*--------------------- IDatabase Methods ------------------------*/
	
	
	
	@Override
	public List<Player> findAllPlayers() {
		List<Player> result = new ArrayList<Player>();
		
		for (Player player : playerList) {
			result.add(player);
		}
		
		return playerList;
	}
	
	@Override
	public List<NPC> findAllNPCs() {
		List<NPC> result = new ArrayList<NPC>();
		
		for (NPC npc : npcList) {
			result.add(npc);
		}
		
		return npcList;
	}
	
	@Override
	public List<ActorStats> findAllActorStats() {
		List<ActorStats> result = new ArrayList<ActorStats>();
		
		for (ActorStats actorStats : actorStatsList) {
			result.add(actorStats);
		}
		
		return actorStatsList;
	}
	
	@Override
	public ConversationTree findConvoTreeByNPCId(int NPCId) {
		ConversationTree result = new ConversationTree();
		
		for (ConversationTree conversationTree : convoTreeList) {
			if (NPCId == conversationTree.getNPCIdId()) {
				result = conversationTree;
			}
		}
		return result;
	}
	
	@Override
	public List<ConversationNode> findConvoNodesByConvoTreeId(int conversationTreeId) {
		List<ConversationNode> result = new ArrayList<ConversationNode>();
		
		for (ConversationNode conversationNode : convoNodeList) {
			if (conversationTreeId == conversationNode.getConvoTreeId()) {
				result.add(conversationNode);
			}
		}
		
		return result;
	}
	
	@Override
	public List<DefaultResponse> findDefaultResponsesByNodeIdAndConvoTreeId(int nodeId, int conversationTreeId){
		List<DefaultResponse> result = new ArrayList<DefaultResponse>();
		
		for (ConversationNode conversationNode : findConvoNodesByConvoTreeId(conversationTreeId)) {
			for (DefaultResponse resp : defaultResponseList) {
				if(resp.getNodeId() == conversationNode.getConvoNodeId()) {
					result.add(resp);
				}
			}
		}
		return result;
	}
	
	@Override
	public List<EndResponse> findEndResponsesByNodeIdAndConvoTreeId(int nodeId, int conversationTreeId) {
		List<EndResponse> result = new ArrayList<EndResponse>();
		
		for (ConversationNode conversationNode : findConvoNodesByConvoTreeId(conversationTreeId)) {
			for (EndResponse resp : endResponseList) {
				if(resp.getNodeId() == conversationNode.getConvoNodeId()) {
					result.add(resp);
				}
			}
		}
		return result;
	}
	
	@Override
	public List<KeyPuzzle> findAllKeyPuzzles(){
		List<KeyPuzzle> result = new ArrayList<KeyPuzzle>();
		
		for (KeyPuzzle puzzle : keyPuzzleList) {
			result.add(puzzle);
		}
		return result;
	}
	
	@Override
	public List<Door> findAllDoors(){
		List<Door> result = new ArrayList<Door>();
		
		for (Door door : doorList) {
			result.add(door);
		}
		return result;
	}
	
	@Override
	public List<Door> findDoorsByRoomId(int roomId){
		List<Door> result = new ArrayList<Door>();
		
		for (Door door : doorList) {
			if (door.getRoomId() == roomId) {
				result.add(door);
			}
		}
		return result;
	}
	
	@Override
	public ActorStats findActorStatsByPlayerId(int playerId) {
		ActorStats result = new ActorStats();
		
		for (Player player : playerList) {
			if(player.getPlayerId() == playerId) {
				for(ActorStats actorStats : actorStatsList) {
					if(player.getStatsId() == actorStats.getStatsId()) {
						result = actorStats;
					}
				}
			}
		}
		return result;
	}
	
	public ActorStats findActorStatsByNPCId(int npcId) {
		ActorStats result = new ActorStats();
		
		for (NPC npc : npcList) {
			if(npc.getNPCId() == npcId) {
				for(ActorStats actorStats : actorStatsList) {
					if(npc.getStatsId() == actorStats.getStatsId()) {
						result = actorStats;
					}
				}
			}
		}
		return result;
	}
	
	/*----------------------------------------------------------------*/
}