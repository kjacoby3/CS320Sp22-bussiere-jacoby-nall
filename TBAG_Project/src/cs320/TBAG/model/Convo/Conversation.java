package cs320.TBAG.model.Convo;

import java.util.ArrayList;

import cs320.TBAG.model.Item;
import cs320.TBAG.model.NPC;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Treasure;

public class Conversation {
	Player player;
	NPC npc;
	ConversationTree npcDialog;
	ConversationNode selectedNode;
	ConversationResponse selectedResponse;
	Boolean ended;
	ArrayList<String> displayList;
	
	
	public Conversation(Player player, NPC npc) {
		this.player = player;
		this.npc = npc;
		npcDialog = npc.getConversationTree();
		selectedNode = npcDialog.getConversationTreeMap().get(1);
		displayList = displaySelectedNodeMSG();
		ended = false;
	}
	
	public ArrayList<String> getDisplayList(){
		return displayList;
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
	
	public ArrayList<String> displaySelectedNodeMSG() {
		ArrayList<String> stringList = new ArrayList<String>();
		System.out.println(selectedNode.getStatement());
		stringList.add(selectedNode.getStatement());
		for(int i = 0; i < selectedNode.getResponseList().size(); i++) {
			System.out.println("" + (i + 1) + "| " + selectedNode.getResponseList().get(i).getResponseStr());
			stringList.add("" + (i + 1) + "| " + selectedNode.getResponseList().get(i).getResponseStr());
		}
		
//		for(int i : selectedNode.getResponseMap().keySet()) {
//			System.out.println("" + i + "| " + selectedNode.getResponseMap().get(i).getResponseStr());
//		}
		
		return stringList;
	}
	
	public ConversationResponse selectResponse(int responseNum) {
		selectedResponse = selectedNode.getResponse(responseNum);
		//if(selectedResponse.getResultNode() != 0) {
		if(selectedResponse instanceof RewardResponse) {
			if(((RewardResponse) selectedResponse).collectReward() == false) {
				Item rewardItem = ((RewardResponse) selectedResponse).getRewardItem();
				int rewardCurrency = ((RewardResponse) selectedResponse).getRewardCurrency();
				int rewardExp = ((RewardResponse) selectedResponse).getRewardExp();
				
				if(rewardItem instanceof Treasure) {
					player.getInventory().addItem(rewardItem);
				} else if(rewardItem != null) {
					player.getInventory().addItem(rewardItem);
				}
				
				player.addCurrency(rewardCurrency);
				player.getActorStats().addExp(rewardExp);
			}
		}
		
		if(selectedResponse instanceof EndResponse) {
			ended = true;
		}
		
		selectNode(selectedResponse.getResultNode());
		System.out.println("      " + responseNum);
		displayList = displaySelectedNodeMSG();
		displaySelectedNodeMSG();
		//} else {
		//	selectNode(selectedResponse.getResultNode());
		//	displaySelectedNodeMSG();
		//}
			
		return selectedResponse;
	}
	
	public void setEnded(Boolean ended) {
		this.ended = ended;
	}
	
	public Boolean getEnded() {
		return ended;
	}
}