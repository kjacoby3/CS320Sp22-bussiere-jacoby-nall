package cs320.TBAG.database;

import java.util.ArrayList;
import java.util.List;

import cs320.TBAG.model.ActorStats;
import cs320.TBAG.model.Equipment;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.NPC;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Room;
import cs320.TBAG.model.RoomConnection;
import cs320.TBAG.model.Weapon;
import cs320.TBAG.model.Convo.ConversationNode;
import cs320.TBAG.model.Convo.ConversationTree;
import cs320.TBAG.model.Convo.DefaultResponse;
import cs320.TBAG.model.Convo.EndResponse;
import cs320.TBAG.model.InteractableObj.Door;
import cs320.TBAG.model.PuzzleType.KeyPuzzle;

public interface IDatabase {
	public List<Player> findAllPlayers();
	public List<NPC> findAllNPCs();
	public List<ActorStats> findAllActorStats();
	public ConversationTree findConvoTreeByNPCId(int NPCId);
	public List<ConversationNode> findConvoNodesByConvoTreeId(int conversationTreeId);
	public List<DefaultResponse> findDefaultResponsesByNodeIdAndConvoTreeId(int nodeId, int conversationTreeId);
	public List<EndResponse> findEndResponsesByNodeIdAndConvoTreeId(int nodeId, int conversationTreeId);
	public List<KeyPuzzle> findAllKeyPuzzles();
	public List<Door> findAllDoors();
	public List<Door> findDoorsByRoomId(int roomId);
	public ActorStats findActorStatsByPlayerId(int playerId);
	public ActorStats findActorStatsByNPCId(int npcId);
	public Inventory constructInventoryByPlayerID(int playerID);
	public Inventory constructInventoryByRoomID(int roomID);
	public Inventory constructInventoryByNPCID(int npcID);
	public Room getRoomByID(int roomID);
	public RoomConnection getRoomConnectionByID(int roomID);
	public ArrayList<Room> getRooms();
	public ArrayList<RoomConnection> getConnections();
	public ConversationTree constructConversationTreeByNPCID(int npcID);
	public String constructDescripByRoomID(int ID);
}