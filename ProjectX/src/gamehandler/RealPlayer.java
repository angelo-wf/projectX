package gamehandler;

public class RealPlayer extends GamePlayer {
	
	private boolean ourTurn;
	
	public RealPlayer(int playerNum) {
		super(playerNum);
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
	public void requestMove() {
		ourTurn = true;
	}

	@Override
	public void endGame() {
		// player doesn't have to do anything
	}

	@Override
	public void tellMove(Move move) {
		// player does nothing with the others move
	}

}
