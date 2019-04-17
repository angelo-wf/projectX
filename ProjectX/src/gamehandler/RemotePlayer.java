package gamehandler;

import java.util.HashMap;

import application.Connection;

public class RemotePlayer extends GamePlayer {
	
	private boolean moveExpected;
	private Connection connection;
	private boolean yourTurnSend;
	
	public RemotePlayer(int playerNum, Connection connection) {
		super(playerNum);
		moveExpected = false;
		yourTurnSend = false;
		this.connection = connection;
	}
	
	public void onMessage(HashMap<String, Object> map) {
		// depending on message, do stuff
		// if a move expected and move-message
		// if a move expected and no move-message, something went wrong
		switch((String) map.get("MESSAGETYPE")) {
		case "MOVE":
			if(moveExpected) {
				moveExpected = false;
				int moveNum;
				try {
					moveNum = Integer.parseInt((String) map.get("MOVE"));
				} catch(NumberFormatException e) {
					moveNum = -1;
				}
				model.doMove(Move.getFromInt(model.getWidth(), moveNum));
			} else {
				// something went wrong...
				System.out.println("ERROR: Got move while not expecting one");
			}
			break;
		case "YOURTURN":
			yourTurnSend = true;
			break;
		default:
			System.out.println("Unhandled message gotten: " + map.get("MESSAGETYPE"));
		}
	}
	
	@Override
	public void handleClick(Move move) {
		// Clicks are ignored for remote players
	}

	@Override
	public void requestMove() {
		moveExpected = true;
	}

	@Override
	public void endGame() {
		// do nothing
	}

	@Override
	public void tellMove(Move move) {
		if(yourTurnSend) {
			yourTurnSend = false;
			connection.move(move.getAsInt(model.getWidth()));
		} else {
			// something went wrong...
			System.out.println("ERROR: Wanted to send move without it being our turn");
		}
	}

}
