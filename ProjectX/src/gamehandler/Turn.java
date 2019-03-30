package gamehandler;

public enum Turn {
	PLAYER1("Player 1's turn", 1), PLAYER2("Player's 2 turn", 2), ENDED("Game ended", 0);
	
	private String niceString;
	private int pieceNum;
	
	private Turn(String niceString, int num) {
		this.niceString = niceString;
		this.pieceNum = num;
	}
	
	public String getNiceString() {
		return niceString;
	}
	
	public int getPieceNum() {
		return pieceNum;
	}
}
