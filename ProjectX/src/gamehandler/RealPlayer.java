package gamehandler;

public class RealPlayer extends GamePlayer {
	
	private boolean ourTurn;
	
	public RealPlayer() {
		ourTurn = false;
	}
	
	@Override
	public void handleClick(Move move) {
		System.out.println("Clickhandler called with move " + move.getAsInt(3) + "; ourTurn: " + ourTurn + "; player: " + this);
		if(ourTurn) {
			if(model.doMove(move)) {
				ourTurn = false;
			}
		}
	}

	@Override
	public void requestMove(Move move) {
		ourTurn = true;
		System.out.println("Requested move from Player; ourTurn: " + ourTurn + "; player: " + this);
	}

}
