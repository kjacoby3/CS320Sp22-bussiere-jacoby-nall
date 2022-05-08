package cs320.TBAG.model.Convo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class ConversationNode{
	String stmt;
	//TreeMap<Integer, ConversationResponse> responseMap;
	ArrayList<ConversationResponse> responseList;
	int convoTreeId;
	int convoNodeId;
	int convoNodeKey;
	 
	public ConversationNode() {
		stmt = "Blank statement";
		//responseMap = new TreeMap<Integer, ConversationResponse>();
		//responseList = new ArrayList<ConversationResponse>();
		//DefaultResponse response1 = new DefaultResponse();
		//DefaultResponse response2 = new DefaultResponse();
		//responseList.add(response1);
		//responseList.add(response2);
		//responseMap.put(1, response1);
		//responseMap.put(2, response2);
	}
	
	public ConversationNode(String stmt, ArrayList<ConversationResponse> responseList) {
		this.stmt = stmt;
		this.responseList = responseList;
	}
	
//	public ArrayList<ConversationResponse> getResponses() {
//		ArrayList<ConversationResponse> responses = new ArrayList<ConversationResponse>();
//		for(ConversationResponse i : responseMap.values()) {
//			responses.add(i);
//		}
//		return responses;
//	}
	
	public String getStatement() {
		return stmt;
	}
	
	public void setStatement(String stmt) {
		this.stmt = stmt;
	}
	
	public ConversationResponse addResponse(ConversationResponse response) {
		//int responseNum = responseMap.size() + 1;
		//return responseMap.put(responseNum, response);
		responseList.add(response);
		return response;
	}
	
	public ConversationResponse removeResponse(int responseNum) {
		ConversationResponse response;
		for(int i = 0; i < responseList.size(); i++) {
			if(responseNum - 1 == i) {
				response = responseList.get(i);
				responseList.remove(i);
				return response;
			}
		}
		return null;
	}
	
	public void checkResponses() {
		for(ConversationResponse resp : responseList) {
			if(resp instanceof BuyResponse) {
				if(((BuyResponse) resp).getBought()) {
					removeResponse(resp);
				}
			}
		}
	}
	
	public ConversationResponse removeResponse(ConversationResponse response) {
		
		responseList.remove(response);
		
		return response;
	}
	
	public ConversationResponse getResponse(int key) {
		//return responseMap.get(key);
		return responseList.get(key - 1);
	}
	
//	public TreeMap<Integer, ConversationResponse> getResponseMap() {
//		return responseMap;
//	}
	
	public ArrayList<ConversationResponse> getResponseList() {
		return responseList;
	}
	
	public void setConvoTreeId(int convoTreeId) {
		this.convoTreeId = convoTreeId;
	}
	
	public int getConvoTreeId() {
		return convoTreeId;
	}
	
	public void setConvoNodeId(int convoNodeId) {
		this.convoNodeId = convoNodeId;
	}
	
	public int getConvoNodeId() {
		return convoNodeId;
	}
	
	public void setConvoNodeKey(int convoNodeKey) {
		this.convoNodeKey = convoNodeKey;
	}
	
	public int getConvoNodeKey() {
		return convoNodeKey;
	}
}