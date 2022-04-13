package cs320.TBAG.model.Convo;

import java.util.ArrayList;
import java.util.TreeMap;

public class Conversation {
	ConversationNode selectedNode;
	TreeMap<Integer, ConversationNode> conversationTree;
	
	public Conversation() {
		conversationTree = new TreeMap<Integer, ConversationNode>();
		selectedNode = conversationTree.get(1);
	}
	
	public Conversation(TreeMap<Integer, ConversationNode> conversationTree) {
		this.conversationTree = conversationTree;
		selectedNode = conversationTree.get(1);
	}
	
	public ConversationNode getNode(int key) {
		selectedNode = conversationTree.get(key);
		return selectedNode;
	}
	
	public void addNode(int key, ConversationNode node) {
		conversationTree.put(key, node);
	}
	
	public ArrayList<String> getNodeResponseStrings() {
		ArrayList<String> responseStrs = new ArrayList<String>();
		for(ConversationResponse i : selectedNode.getResponseMap().values()) {
			responseStrs.add(i.getResponseStr());
		}
		return responseStrs;
	}
}