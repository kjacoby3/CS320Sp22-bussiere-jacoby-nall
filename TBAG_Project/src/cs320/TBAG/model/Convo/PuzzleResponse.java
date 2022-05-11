package cs320.TBAG.model.Convo;

import cs320.TBAG.model.PuzzleType.Puzzle;

public class PuzzleResponse extends ConversationResponse {
	private Puzzle puzzleLock;
	private int completeResultNode;
	private int puzzleResponseId;
	private int puzzleId;
	private int defaultResultNode;
	
	public PuzzleResponse() {
		resultNode = 0;
		//defaultResultNode = resultNode;
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
		defaultResultNode = resultNode;
		//resultNode = defaultResultNode;
		if(puzzleLock != null) {
			puzzleLock.checkConditions();
			if(puzzleLock.getComplete()) {
				System.out.println("PuzzleResponse getComplete: " + puzzleLock.getComplete());
				resultNode = completeResultNode;
			} else {
				resultNode = defaultResultNode;
			}
		}
		return resultNode;
	}
	
	public void setPuzzleResponseId(int puzzleResponseId) {
		this.puzzleResponseId = puzzleResponseId;
	}
	
	public int getPuzzleResponseId() {
		return puzzleResponseId;
	}
	
	public void setPuzzleId(int puzzleId) {
		this.puzzleId = puzzleId;
	}
	
	public int getPuzzleId() {
		return puzzleId;
	}
	
	public void setCompleteResultNode(int completeResultNode) {
		this.completeResultNode = completeResultNode;
	}
	
	public int getCompleteResultNode() {
		return completeResultNode;
	}
}