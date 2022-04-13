package cs320.TBAG.model.Convo;

public abstract class ConversationResponse{
	String response;
	int resultNode;
	
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
}