package cs320.TBAG.database;

import java.util.List;

import cs320.TBAG.model.ActorStats;
import cs320.TBAG.model.NPC;
import cs320.TBAG.model.Player;
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
	public ConversationTree findConvoTreeByNPC(int NPCId);
	public List<ConversationNode> findConvoNodesByConvoTreeId(int conversationTreeId);
	public List<DefaultResponse> findDefaultResponsesByNodeKeyAndConvoTreeId(int key, int conversationTreeId);
	public List<EndResponse> findEndResponsesByNodeKeyAndConvoTreeId(int key, int conversationTreeId);
	public List<KeyPuzzle> findAllKeyPuzzles();
	public List<Door> findAllDoors();
	public List<Door> findDoorsByRoomId(int roomId);
}