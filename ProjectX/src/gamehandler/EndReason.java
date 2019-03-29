package gamehandler;

public enum EndReason {
	WIN1("Player 1 won!"), WIN2("Player 2 won!"), DRAW("It's a draw!");
	
	private final String niceString;
	
	private EndReason(String niceString) {
		this.niceString = niceString;
	}
	
	public String getNiceString() {
		return niceString;
	}
}
