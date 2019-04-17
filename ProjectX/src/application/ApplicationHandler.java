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
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
	private boolean closing;
	
	public ApplicationHandler(Stage primaryStage) {
		closing = false;
		gui = new Gui(primaryStage, this);
		
	}
	
	public boolean setServer(String name, String address) {
		username = name;
		String ip = address;
		int port = 7789;
		String[] parts = ip.split(":");
		if(parts.length > 1) {
			ip = parts[0];
			try {
				port = Integer.parseInt(parts[1]);
			} catch(NumberFormatException e) {
				return false;
			}
		}
		try {
			connection = new Connection(ip, port, this);
			connection.login(name);
		} catch(IOException e) {
			System.out.println("Failed to connect to server.");
			return false;
		}
		return true;
	}
	
	public boolean setLocal(String name) {
		username = name;
		return true;
	}
	
	public void disconnect() {
		connection.logout();
		connection.setIntendedClose();
	}
	
	public void appClosing() {
		closing = true;
		if(model != null) {
			model.endGame(EndReason.WIN2, "App closing");
		}
		if(connection != null) {
			connection.close();
		}
	}
	
	public void connectionLost() {
		if(!closing) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR, "Server connection lost", ButtonType.OK);
				alert.showAndWait();
			});
			if(model != null) {
				model.endGame(EndReason.WIN2, "Connection lost");
			}
			gui.straightToLogin();
		}
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
	
	public void forfeit() {
		connection.forfeit();
	}
	
	public void localForfeit() {
		model.endGame(EndReason.WIN2, "player forfeited match");
	}
	
	public void startGame(String gameName, int beginningPlayer, int type, String player2name) {
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
		gui.setGameScreen(gameView, player2name);
	}
	
	private void setUpGame(String game, String playerType1, String playerType2) {
		switch(game) {
		case "Reversi":
			model = new ReversiModel();
			if(playerType1.equals("AI")) {
				player1 = new ReversiAI(1);
			}
			if(playerType2.equals("AI")) {
				player2 = new ReversiAI(2);
			}
			gameView = new ReversiView(gui);
			break;
		case "Tic-tac-toe":
			model = new TicTacToeModel();
			if(playerType1.equals("AI")) {
				player1 = new TicTacToeAI(1);
			}
			if(playerType2.equals("AI")) {
				player2 = new TicTacToeAI(2);
			}
			gameView = new TicTacToeView(gui);
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
			if(map.get("PLAYERTOMOVE").equals(username)) {
				startGame((String) map.get("GAMETYPE"), 1, gui.getMode() ? 2 : 1, (String) map.get("OPPONENT"));
				//startGame((String) map.get("GAMETYPE"), 1, 2);
			} else {
				startGame((String) map.get("GAMETYPE"), 2, gui.getMode() ? 2 : 1, (String) map.get("OPPONENT"));
				//startGame((String) map.get("GAMETYPE"), 2, 2);
			}
			break;
		case "YOURTURN":
			((RemotePlayer) player2).onMessage(map);
			break;
		case "MOVE":
			if(!map.get("PLAYER").equals(username)) {
				((RemotePlayer) player2).onMessage(map);
			}
			break;
		case "WIN":
			model.endGame(EndReason.WIN1, (String) map.get("COMMENT"));
			break;
		case "LOSS":
			model.endGame(EndReason.WIN2, (String) map.get("COMMENT"));
			break;
		case "DRAW":
			model.endGame(EndReason.DRAW, (String) map.get("COMMENT"));
			break;
		case "CHALLENGE":
			gui.setChallenge((String) map.get("CHALLENGER"), (String) map.get("GAMETYPE"), Integer.parseInt((String) map.get("CHALLENGENUMBER")));
			break;
		case "CHALLENGE_CANCELLED":
			gui.cancelChallenge(Integer.parseInt((String) map.get("CHALLENGENUMBER")));
			break;
		case "PLAYERLIST":
			gui.setPlayerList((ArrayList<String>) map.get("LIST"));
			break;
		case "GAMELIST":
			// not used
			break;
		case "ERROR":
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR, "Error from server: " + map.get("ERRORMESSAGE"), ButtonType.OK);
				alert.showAndWait();
			});
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
