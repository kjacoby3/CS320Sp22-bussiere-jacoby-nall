package cs320.TBAG.model.Convo;

public class DefaultResponse extends ConversationResponse {
	
	
	public DefaultResponse(){
		resultNode = 1;
		response = "Blank response.";
	}
	
	public DefaultResponse(String response, int resultNode) {
		this.response = response;
		this.resultNode = resultNode;
	}
	
	
}