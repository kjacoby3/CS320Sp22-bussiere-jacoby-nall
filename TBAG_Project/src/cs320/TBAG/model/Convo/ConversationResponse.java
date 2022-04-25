package cs320.TBAG.model.Convo;

public abstract class ConversationResponse{
	String response;
	int resultNode;
	int convoTreeId;
	int nodeId;
	int responseId;
	
	public String getResponseStr() {
		return response;
	}
	
	public void setResponseStr(String response) {
		this.response = response;
	}
	
	public int getResultNode() {
		return resultNode;
	}
	
	public void setResultNode(int resultNode) {
		this.resultNode = resultNode;
	}
	
	public void setConvoTreeId(int convoTreeId) {
		this.convoTreeId = convoTreeId;
	}
	
	public int getConvoTreeId() {
		return convoTreeId;
	}
	
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	
	public int getNodeId() {
		return nodeId;
	}
	
	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}
	
	public int getResponseId() {
		return responseId;
	}
}