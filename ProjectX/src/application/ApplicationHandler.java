package application;

import java.util.HashMap;

import gamehandler.GameModel;
import gamehandler.GamePlayer;
import gamehandler.GameView;
import gamehandler.RealPlayer;
import gamehandler.RemotePlayer;
import javafx.stage.Stage;
import reversi.ReversiAI;
import reversi.ReversiModel;
import tictactoe.TicTacToeAI;
import tictactoe.TicTacToeModel;

public class ApplicationHandler {
	
	private Connection connection;
	// global view, gamemodel, players, view
	private GameModel model;
	private GamePlayer player1;
	private GamePlayer player2;
	private GameView gameView;
	
	private String username;
	private Gui gui;
	
	public ApplicationHandler(Stage primaryStage) {
		
//		// make window
//		VBox game = new VBox();
//		// TEMP: Create gameview here
//		gameView = new ReversiView();
//		game.getChildren().add(gameView.getBoardView());
//		game.getChildren().add(gameView.getStatsPane());
//		
//		Scene scene = new Scene(game);
//		primaryStage.setScene(scene);
//		primaryStage.setTitle("Reversi");
//		
//		primaryStage.show();
//		
//		setServer("b", "localhost:7789");
//		//connection.challengePlayer("b", "Reversi");
//		connection.subscirbe("Reversi");
		
		gui = new Gui(primaryStage, this);
		
	}
	
	public void setServer(String name, String address) {
		username = name;
		connection = new Connection(address.split(":")[0], Integer.parseInt(address.split(":")[1]), this);
		connection.login(name);
	}
	
	public void requestPlayerList() {
		if(connection == null) {
			return;
		} else {
			connection.getGamelist();
		}
	}
	
	public void startGame(int beginningPlayer) {
		setUpGame("Reversi", "Real", "Remote");
		model.initGame(beginningPlayer);
	}
	
	public void setUpGame(String game, String playerType1, String playerType2) {
		switch(game) {
		case "Reversi":
			model = new ReversiModel();
			if(playerType1.equals("AI")) {
				player1 = new ReversiAI(1);
			}
			if(playerType2.equals("AI")) {
				player2 = new ReversiAI(2);
			}
			// TEMP: made in constructor
			//gameView = new ReversiView();
			break;
		case "Tic-tac-toe":
			model = new TicTacToeModel();
			if(playerType1.equals("AI")) {
				player1 = new TicTacToeAI(1);
			}
			if(playerType2.equals("AI")) {
				player2 = new TicTacToeAI(2);
			}
			// TEMP: made in constructor
			//gameView = new TicTacToeView();
			break;
		default:
			throw new IllegalArgumentException("Unknown Game-type");
		}
		if(!playerType1.equals("AI")) {
			player1 = getPlayerForType(playerType1, 1);
		}
		if(!playerType2.equals("AI")) {
			player2 = getPlayerForType(playerType2, 2);
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
			//System.out.println(type);
			if(map.get("PLAYERTOMOVE").equals(username)) {
				startGame(1);
			} else {
				startGame(2);
			}
			break;
		case "YOURTURN":
			//System.out.println(type);
			((RemotePlayer) player2).onMessage(map);
			break;
		case "MOVE":
			//System.out.println(type);
			if(!map.get("PLAYER").equals(username)) {
				((RemotePlayer) player2).onMessage(map);
			}
			break;
		case "WIN":
			System.out.println(type);
			break;
		case "LOSS":
			System.out.println(type);
			break;
		case "DRAW":
			System.out.println(type);
			break;
		case "CHALLENGE":
			System.out.println(type);
			break;
		case "CHALLENGE_CANCELLED":
			System.out.println(type);
			break;
		case "PLAYERLIST":
			System.out.println(type);
			break;
		case "GAMELIST":
			System.out.println(type);
			break;
		default:
			break;
		}
	}
	
	private GamePlayer getPlayerForType(String type, int number) {
		switch(type) {
		case "Real":
			return new RealPlayer(number);
		case "Remote":
			return new RemotePlayer(number, connection);
		default:
			throw new IllegalArgumentException("Unknown Player-type");
		}
	}
}
