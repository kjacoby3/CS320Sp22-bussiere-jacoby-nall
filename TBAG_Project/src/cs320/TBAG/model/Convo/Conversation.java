package cs320.TBAG.model.Convo;

import java.util.ArrayList;

import cs320.TBAG.model.NPC;
import cs320.TBAG.model.Player;

public class Conversation {
	Player player;
	NPC npc;
	ConversationTree npcDialog;
	ConversationNode selectedNode;
	ConversationResponse selectedResponse;
	
	
	public Conversation(Player player, NPC npc) {
		this.player = player;
		this.npc = npc;
		npcDialog = npc.getConversationTree();
		selectedNode = npcDialog.getConversationTreeMap().get(1);
		displaySelectedNodeMSG();
	}
	
	public ConversationTree getNPCDialog() {
		return npcDialog;
	}
	
	public void setNPCDialog(ConversationTree npcDialog) {
		this.npcDialog = npcDialog;
	}
	
	public void selectNode(int key) {
		selectedNode = npcDialog.getConversationTreeMap().get(key);
	}
	
	public ConversationNode getSelectedNode() {
		return selectedNode;
	}
	
	public ConversationResponse getSelectedResponse() {
		return selectedResponse;
	}
	
//	public ArrayList<String> getNodeResponseStrings() {
//		ArrayList<String> responseStrs = new ArrayList<String>();
//		for(ConversationResponse i : selectedNode.getResponseMap().values()) {
//			responseStrs.add(i.getResponseStr());
//		}
//		return responseStrs;
//	}
	
	public void displaySelectedNodeMSG() {
		System.out.println(selectedNode.getStatement());
		for(int i = 0; i < selectedNode.getResponseList().size(); i++) {
			System.out.println("" + (i + 1) + "| " + selectedNode.getResponseList().get(i).getResponseStr());
		}
		
//		for(int i : selectedNode.getResponseMap().keySet()) {
//			System.out.println("" + i + "| " + selectedNode.getResponseMap().get(i).getResponseStr());
//		}
	}
	
	public ConversationResponse selectResponse(int responseNum) {
		selectedResponse = selectedNode.getResponse(responseNum);
		//if(selectedResponse.getResultNode() != 0) {
			selectNode(selectedResponse.getResultNode());
			System.out.println("      " + responseNum);
			displaySelectedNodeMSG();
		//} else {
		//	selectNode(selectedResponse.getResultNode());
		//	displaySelectedNodeMSG();
		//}
		return selectedResponse;
	}
}