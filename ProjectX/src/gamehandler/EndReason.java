package gamehandler;

public enum EndReason {
	WIN1("You won!"), WIN2("The opponent won."), DRAW("It's a draw!");
	
	private final String niceString;
	
	private EndReason(String niceString) {
		this.niceString = niceString;
	}
	
	public String getNiceString() {
		return niceString;
	}
}
