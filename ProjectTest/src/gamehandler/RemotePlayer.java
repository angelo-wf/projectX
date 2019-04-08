package gamehandler;

import java.util.HashMap;

import application.Connection;

public class RemotePlayer extends GamePlayer {
	
	private boolean moveExpected;
	private Connection connection;
	private boolean yourTurnSend;
	
	public RemotePlayer(int playerNum, Connection connection) {
		moveExpected = false;
		yourTurnSend = false;
		this.connection = connection;
	}
	
	public void onMessage(HashMap<String, Object> map) {
		// depending on message, do stuff
		// if a move expected and move-message
		//model.doMove(new Move(0, 0));
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
			System.out.println("Unhandled message gotten: " + map.get("MESSAGETYPE"));
		}
	}
	
	@Override
	public void handleClick(Move move) {
		// Clicks are ignored for remote players
	}

	@Override
	public void requestMove(Move move) {

	}


	@Override
	public void endGame() {
		// do nothing
	}

}
