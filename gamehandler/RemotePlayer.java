package gamehandler;

import java.awt.*;
import java.util.HashMap;

import application.Connection;

public class RemotePlayer extends GamePlayer {
	
	private boolean moveExpected;
	private Connection connection;
	private boolean yourTurnSend;
	
	public RemotePlayer(Connection connection) {
		super(1);
		moveExpected = false;
		yourTurnSend = false;
		this.connection = connection;
	}
	
	public void onMessage(HashMap<String, Object> map) {
		// depending on message, do stuff
		// if a move expected and move-message
		model.doMove(new Move(0, 0));
		// if a move expected and no move-message, something went wrong
		switch((String) map.get("MESSAGETYPE")) {
		case "MOVE":
			if(moveExpected) {
				moveExpected = false;
				model.doMove(Move.getFromInt(model.getWidth(), Integer.parseInt((String) map.get("MOVE"))));
			} else {
				// something went wrong...
				System.out.println("ERROR: Got move while not expecting one");
			}
			break;
		case "YOURTURN":
			yourTurnSend = true;
			break;
		default:
			System.out.println("Message gotten: " + map.get("MESSAGETYPE"));
		}
	}
	
	@Override
	public void handleClick(Move move) {
		// Clicks are ignored for remote players
	}

	@Override
	public void requestMove(Move move) {
		if(yourTurnSend) {
			connection.move(move.getAsInt(model.getWidth()));
			moveExpected = true;
			yourTurnSend = false;
		} else {
			// something went wrong...
			System.out.println("ERROR: Wanted to do move without it being our turn");
		}
	}

	@Override
	public void endGame() {
		// do nothing
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
