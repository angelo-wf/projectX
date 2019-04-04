package gamehandler;

public enum EndReason {
	WIN1("player 1 won!"), WIN2("player 2 won!"), DRAW("it's a draw!");
	
	private final String niceString;
	
	private EndReason(String niceString) {
		this.niceString = niceString;
	}
	
	public String getNiceString() {
		return niceString;
	}
}
