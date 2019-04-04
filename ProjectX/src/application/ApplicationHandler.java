package application;

import java.util.HashMap;

import gamehandler.GameModel;
import gamehandler.GamePlayer;
import gamehandler.GameView;
import gamehandler.RealPlayer;
import gamehandler.RemotePlayer;
import reversi.ReversiAI;
import reversi.ReversiModel;
import reversi.ReversiView;
import tictactoe.TicTacToeAI;
import tictactoe.TicTacToeModel;
import tictactoe.TicTacToeView;

public class ApplicationHandler {
	
	private Connection connection;
	// global view, gamemodel, players, view
	private GameModel model;
	private GamePlayer player1;
	private GamePlayer player2;
	private GameView gameView;
	
	public ApplicationHandler() {
		// make window
	}
	
	public void setServer(String name, String address) {
		connection = new Connection(address.split(":")[0], Integer.parseInt(address.split(":")[1]));
		connection.login(name);
	}
	
	public void requestPlayerList() {
		if(connection == null) {
			return;
		} else {
			connection.getGamelist();
		}
	}
	
	public void setUpGame(String game, String playerType1, String playerType2) {
		switch(game) {
		case "Reversi":
			model = new ReversiModel();
			if(playerType1.equals("AI")) {
				player1 = new ReversiAI();
			}
			if(playerType2.equals("AI")) {
				player2 = new ReversiAI();
			}
			gameView = new ReversiView();
			break;
		case "Tic-tac-toe":
			model = new TicTacToeModel();
			if(playerType1.equals("AI")) {
				player1 = new TicTacToeAI();
			}
			if(playerType2.equals("AI")) {
				player2 = new TicTacToeAI();
			}
			gameView = new TicTacToeView();
			break;
		default:
			throw new IllegalArgumentException("Unknown Game-type");
		}
		if(!playerType1.equals("AI")) {
			player1 = getPlayerForType(playerType1);
		}
		if(!playerType2.equals("AI")) {
			player2 = getPlayerForType(playerType2);
		}
		model.setPlayer1(player1);
		model.setPlayer2(player2);
		model.setView(gameView);
		gameView.setModel(model);
		gameView.setPlayer(player1);
		player1.setModel(model);
		player2.setModel(model);
	}
	
	public void recieveMessage(HashMap<String, Object> map) {
		//send to view
		String type = (String) map.get("MESSAGETYPE");
		switch (type) {
		case "MATCH":
			System.out.println(map.toString());
			break;
		case "YOURTURN":
			System.out.println(map.toString());
			break;
		case "MOVE":
			System.out.println(map.toString());
			break;
		case "WIN":
			System.out.println(map.toString());
			break;
		case "LOSS":
			System.out.println(map.toString());
			break;
		case "CHALLENGE":
			System.out.println(map.toString());
			break;
		case "CHALLENGE_CANCELLED":
			System.out.println(map.toString());
			break;
		case "PLAYERLIST":
			System.out.println(map.toString());
			break;
		case "GAMELIST":
			System.out.println(map.toString());
			break;
		default:
			break;
		}
	}
	
	private GamePlayer getPlayerForType(String type) {
		switch(type) {
		case "Real":
			return new RealPlayer();
		case "Remote":
			return new RemotePlayer(connection);
		default:
			throw new IllegalArgumentException("Unknown Player-type");
		}
	}
}
