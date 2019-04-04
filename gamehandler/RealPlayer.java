package gamehandler;

import java.awt.*;

public class RealPlayer extends GamePlayer {
	
	private boolean ourTurn;
	
	public RealPlayer() {
		super(1);
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
		ourTurn = true;
	}

	@Override
	public void endGame() {
		// player doesn't have to do anything
	}

	@Override
	public boolean isUserPlayer() {
		return false;
	}

	@Override
	public String playerName() {
		return null;
	}

	@Override
	public Point play(int[][] board) {
		return null;
	}
}

