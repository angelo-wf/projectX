package gamehandler;

public class RemotePlayer extends GamePlayer {
	
	private boolean moveExpected;
	
	public RemotePlayer() {
		moveExpected = false;
	}
	
	public void onMessage(String message) {
		// depending on message, do stuff
		// if a move expected and move-message
		model.doMove(new Move(0, 0));
		// if a move expected and no move-message, something went wrong
	}
	
	@Override
	public void handleClick(Move move) {
		// Clicks are ignored for remote players
	}

	@Override
	public void requestMove(Move move) {
		// TODO: peek 'yourmove' from server
		// if not a 'yourmove' something went wrong
		// TODO: send move to server
		moveExpected = true;
	}

	@Override
	public void endGame() {
		// do nothing
	}

}
