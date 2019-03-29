package tictactoe;

import gamehandler.GamePlayer;
import gamehandler.Move;

public class TicTacToeAI extends GamePlayer {

	@Override
	public void handleClick(Move move) {
		// clicks are ignored for AI's
	}

	@Override
	public void requestMove(Move move) {
		System.out.println("Requested move from AI");
		while(true) {
			int x = (int) (Math.random() * 3);
			int y = (int) (Math.random() * 3);
			Move tryMove = new Move(x, y);
			if(model.doMove(tryMove)) {
				break;
			}
		}
	}

}
