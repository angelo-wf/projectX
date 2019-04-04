package runner;

import application.Connection;
import gamehandler.GameModel;
import gamehandler.GamePlayer;
import gamehandler.GameView;
import gamehandler.RealPlayer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import reversi.ReversiAI;
import reversi.ReversiModel;
import reversi.ReversiView;

public class TicTacToeTest extends Application {

	@Override
	public void start(Stage primaryStage) {
		VBox game = new VBox();
		
//		GameModel model = new TicTacToeModel();
//		GameView view = new TicTacToeView();
//		GamePlayer player1 = new RealPlayer();
//		//GamePlayer player1 = new TicTacToeAI();
//		GamePlayer player2 = new TicTacToeAI();
		
		Connection connection = new Connection("localhost", 7789);
		connection.login("elzo_d");
		connection.getGamelist();
		connection.challengePlayer("user1", "Tic-tac-toe");
		
		GameModel model = new ReversiModel();
		GameView view = new ReversiView();
		GamePlayer player1 = new RealPlayer();
		//GamePlayer player1 = new ReversiAI();
		GamePlayer player2 = new ReversiAI();
		
		model.setView(view);
		model.setPlayer1(player1);
		model.setPlayer2(player2);
		view.setPlayer(player1);
		view.setModel(model);
		player1.setModel(model);
		player2.setModel(model);
		
		game.getChildren().add(view.getBoardView());
		game.getChildren().add(view.getStatsPane());
		
		Scene scene = new Scene(game);
		primaryStage.setScene(scene);
		primaryStage.setTitle("TicTacToe");
		
		model.initGame(1);
		
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
