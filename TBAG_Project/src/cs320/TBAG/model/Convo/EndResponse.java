package cs320.TBAG.model.Convo;

public class EndResponse extends ConversationResponse {
	
	public EndResponse() {
		resultNode = 0;
		response = "Blank end response.";
	}
	
	public EndResponse(String response) {
		resultNode = 0;
		this.response = response;
	}
	
}