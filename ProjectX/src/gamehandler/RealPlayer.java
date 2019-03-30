package gamehandler;

public class RealPlayer extends GamePlayer {
	
	private boolean ourTurn;
	
	public RealPlayer() {
		ourTurn = false;
	}
	
	@Override
	public void handleClick(Move move) {
		if(ourTurn) {
			if(model.doMove(move)) {
				ourTurn = false;
			}
		}
	}

	@Override
	public void requestMove(Move move) {
		ourTurn = true;
	}

	@Override
	public void endGame() {
		// player doesn't have to do anything
	}

}
