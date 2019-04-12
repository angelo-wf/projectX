package gamehandler;

public enum Turn {
	PLAYER1("Your turn", 1), PLAYER2("Opponents turn", 2), ENDED("Game ended", 0);
	
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
