package cs320.TBAG.model.Convo;

public class EndResponse extends ConversationResponse {
	int endResponseId;
	
	
	public EndResponse() {
		resultNode = 0;
		response = "Blank end response.";
	}
	
	public EndResponse(String response) {
		resultNode = 0;
		this.response = response;
	}
	
	public void setEndResponseId(int endResponseId) {
		this.endResponseId = endResponseId;
	}
	
	public int getEndResponseId() {
		return endResponseId;
	}
}