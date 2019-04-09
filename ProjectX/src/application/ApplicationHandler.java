package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import gamehandler.EndReason;
import gamehandler.GameModel;
import gamehandler.GamePlayer;
import gamehandler.GameView;
import gamehandler.RealPlayer;
import gamehandler.RemotePlayer;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
	
	public boolean setServer(String name, String address) {
		username = name;
		String ip = address;
		int port = 7789;
		String[] parts = ip.split(":");
		if(parts.length > 1) {
			ip = parts[0];
			port = Integer.parseInt(parts[1]);
		}
		boolean success = true;
		try {
			connection = new Connection(ip, port, this);
			connection.login(name);
		} catch(IOException e) {
			success = false;
			System.out.println("Failed to connect to server.");
		}
		return success;
	}
	
	public void disconnect() {
		connection.logout();
		connection.close();
	}
	
	public void requestPlayerList() {
		if(connection == null) {
			return;
		} else {
			connection.getPlayerlist();
		}
	}
	
	public void challengePlayer(String name, String gameType) {
		connection.challengePlayer(name, gameType);
	}
	
	public void acceptChallenge(int number) {
		connection.challengeAccept(number);
	}
	
	public void subscribe(String gameType) {
		connection.subscirbe(gameType);
	}
	
	public void startGame(String gameName, int beginningPlayer, int type) {
		switch(type) {
		case 0:
			setUpGame(gameName, "Real", "AI");
			break;
		case 1:
			setUpGame(gameName, "Real", "Remote");
			break;
		case 2:
			setUpGame(gameName, "AI", "Remote");
			break;
		case 3:
			setUpGame(gameName, "AI", "AI");
			break;
		default:
			throw new IllegalArgumentException("Unknown match-type");
		}
		model.initGame(beginningPlayer);
		// TODO: tell ui game started, provide pane for board and label for status
		gui.setGameScreen(gameView);
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
			gameView = new ReversiView();
			break;
		case "Tic-tac-toe":
			model = new TicTacToeModel();
			if(playerType1.equals("AI")) {
				player1 = new TicTacToeAI(1);
			}
			if(playerType2.equals("AI")) {
				player2 = new TicTacToeAI(2);
			}
			gameView = new TicTacToeView();
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
			// TODO: Get match-type from gui
			if(map.get("PLAYERTOMOVE").equals(username)) {
				startGame((String) map.get("GAMETYPE"), 1, 2);
			} else {
				startGame((String) map.get("GAMETYPE"), 2, 2);
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
			//System.out.println(type);
			model.endGame(EndReason.WIN1);
			break;
		case "LOSS":
			//System.out.println(type);
			model.endGame(EndReason.WIN2);
			break;
		case "DRAW":
			//System.out.println(type);
			model.endGame(EndReason.DRAW);
			break;
		case "CHALLENGE":
			System.out.println(type);
			gui.setChallenge((String) map.get("CHALLENGER"), (String) map.get("GAMETYPE"), Integer.parseInt((String) map.get("CHALLENGENUMBER")));
			break;
		case "CHALLENGE_CANCELLED":
			System.out.println(type);
			// gui.cancelChallenge(Integer.parseInt((String) map.get("CHALLENGENUMBER")));
			break;
		case "PLAYERLIST":
			//System.out.println(type);
			gui.setPlayerList((ArrayList<String>) map.get("LIST"));
			break;
		case "GAMELIST":
			//System.out.println(type);
			// not used
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
