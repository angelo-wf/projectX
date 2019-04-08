package gamehandler;

public class RealPlayer extends GamePlayer {
	
	private boolean ourTurn;
	
	public RealPlayer(int playerNum) {
		ourTurn = false;
	}
	
	@Override
	public void handleClick(Move move) {
		if(ourTurn) {
			ourTurn = false;
			if(!model.doMove(move)) {
				ourTurn = true;
			}
		}
	}

	@Override
	public void requestMove(Move move) {

	}

	@Override
	public void endGame() {
		// player doesn't have to do anything
	}

}
