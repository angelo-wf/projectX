package runner;

import gamehandler.GameModel;
import gamehandler.GamePlayer;
import gamehandler.GameView;
import gamehandler.RealPlayer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tictactoe.TicTacToeAI;
import tictactoe.TicTacToeModel;
import tictactoe.TicTacToeView;

public class TicTacToeTest extends Application {

	@Override
	public void start(Stage primaryStage) {
		VBox game = new VBox();
		
		GameModel model = new TicTacToeModel();
		GameView view = new TicTacToeView();
		GamePlayer player1 = new RealPlayer();
		//GamePlayer player1 = new TicTacToeAI();
		GamePlayer player2 = new TicTacToeAI();
		
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
		
		model.initGame();
		
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
