package cs320.TBAG.model.Convo;

import java.util.ArrayList;
import java.util.TreeMap;

public class ConversationTree {
	TreeMap<Integer, ConversationNode> conversationTreeMap;
	int convoTreeId;
	int NPCId;
	
	public ConversationTree() {
		conversationTreeMap = new TreeMap<Integer, ConversationNode>();
		//ConversationNode node1 = new ConversationNode();
		//ConversationNode endNode = new ConversationNode();
		//endNode.getResponseList().clear();
		//endNode.setStatement("The conversation has ended");
		//conversationTreeMap.put(1, node1);
		//conversationTreeMap.put(0, endNode);
	}
	
	public ConversationTree(TreeMap<Integer, ConversationNode> conversationTreeMap) {
		this.conversationTreeMap = conversationTreeMap;
	}
	
	public TreeMap<Integer, ConversationNode> getConversationTreeMap() {
		return conversationTreeMap;
	}
	
	public void setConversationTreeMap(TreeMap<Integer, ConversationNode> conversationTreeMap) {
		this.conversationTreeMap = conversationTreeMap;
	}
	
	public ConversationNode getNode(int key) {
		return conversationTreeMap.get(key);
	}
	
	public void addNode(int key, ConversationNode node) {
		conversationTreeMap.put(key, node);
	}
	
	public ConversationNode getEndNode() {
		return conversationTreeMap.get(0);
	}
	
	public void setConvoTreeId(int convoTreeId) {
		this.convoTreeId = convoTreeId;
	}
	
	public int getConvoTreeId() {
		return convoTreeId;
	}
	
	public void setNPCId(int NPCId) {
		this.NPCId = NPCId;
	}
	
	public int getNPCIdId() {
		return NPCId;
	}
}