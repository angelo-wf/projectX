package reversi;

import application.Gui;
import gamehandler.GameView;
import gamehandler.Move;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ReversiView extends GameView {
	private Circle[] cells;
	private Label stats;
	
	public ReversiView(Gui gui) {
		super(gui);
		boardView = new Pane();
		boardView.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		boardView.setPrefSize(400, 400);
		cells = new Circle[64];
		for(int i = 0; i < cells.length; i++) {
			final int x = i % 8;
			final int y = i / 8;
			cells[i] = new Circle(x * 50 + 25, y * 50 + 25, 23);
			cells[i].setFill(Color.GREEN);
			cells[i].setOnMouseClicked(e -> {
				handleClick(new Move(x, y));
			});
			boardView.getChildren().add(cells[i]);
		}
		stats = new Label("Stats");
		statsPane = new Pane();
		statsPane.getChildren().add(stats);
	}
	
	@Override
	public void update() {
		String stateString = ((ReversiModel) model).getStateString();
		Platform.runLater(() -> {
			stats.setText(stateString);
			updateBoard();
		});
		int[] values = ((ReversiModel) model).getStats();
		gui.updateStats(values[0], values[1], values[2]);
	}
	
	private void updateBoard() {
		// TODO: Should getBoard and getStateString be part of the GameView?
		int[] board = ((ReversiModel) model).getBoard();
		int startingPlayer = ((ReversiModel) model).getStartingPlayer();
		
		for(int i = 0; i < board.length; i++) {
			int value = board[i];
			if(startingPlayer == 1) {
				if(value == 1) {
					cells[i].setFill(Color.BLACK);
				} else if(value == 2) {
					cells[i].setFill(Color.WHITE);
				} else {
					cells[i].setFill(Color.GREEN);
				}
			} else {
				if(value == 1) {
					cells[i].setFill(Color.WHITE);
				} else if(value == 2) {
					cells[i].setFill(Color.BLACK);
				} else {
					cells[i].setFill(Color.GREEN);
				}
			}
		}
	}
}
