package cs320.TBAG.model.Convo;

import cs320.TBAG.model.PuzzleType.Puzzle;

public class PuzzleResponse extends ConversationResponse {
	private Puzzle puzzleLock;
	private int completeResultNode;
	
	
	public PuzzleResponse() {
		resultNode = 0;
		completeResultNode = 1;
		response = "I'm finished";
		puzzleLock = null;
	}
	
	public void setPuzzle(Puzzle puzzleLock) {
		this.puzzleLock = puzzleLock;
	}
	
	public Puzzle getPuzzle() {
		return puzzleLock;
	}
	
	public int checkResultNode() {
		int node = resultNode;
		if(puzzleLock != null) {
			puzzleLock.checkConditions();
			if(puzzleLock.getComplete()) {
				node = completeResultNode;
			} else {
				node = resultNode;
			}
		}
		return node;
	}
}