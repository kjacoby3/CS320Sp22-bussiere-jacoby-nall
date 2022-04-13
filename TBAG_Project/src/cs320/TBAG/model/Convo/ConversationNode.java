package cs320.TBAG.model.Convo;

import java.util.ArrayList;
import java.util.HashMap;

public class ConversationNode{
	String stmt;
	HashMap<Integer, ConversationResponse> responseMap;
	 
	public ConversationNode() {
		stmt = "Blank statement";
		responseMap = new HashMap<Integer, ConversationResponse>();
		 
	}
	
	public ArrayList<ConversationResponse> getResponses() {
		ArrayList<ConversationResponse> responses = new ArrayList<ConversationResponse>();
		for(ConversationResponse i : responseMap.values()) {
			responses.add(i);
		}
		return responses;
	}
	
	public void addResponse(int responseNum, ConversationResponse response) {
		responseMap.put(responseNum, response);
	}
	
	public ConversationResponse getResponse(int key) {
		return responseMap.get(key);
	}
	
	public HashMap<Integer, ConversationResponse> getResponseMap() {
		return responseMap;
	}
}