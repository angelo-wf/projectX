package reversi;

import gamehandler.GameView;
import gamehandler.Move;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ReversiView extends GameView {
	private Rectangle[] cells;
	private Label stats;
	
	public ReversiView() {
		boardView = new Pane();
		boardView.setPrefSize(400, 400);
		cells = new Rectangle[64];
		for(int i = 0; i < cells.length; i++) {
			final int x = i % 8;
			final int y = i / 8;
			cells[i] = new Rectangle(x * 50, y * 50, 50, 50);
			cells[i].setFill(Color.WHITE);
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
	}
	
	private void updateBoard() {
		// TODO: Should getBoard and getStateString be part of the GameView?
		int[] board = ((ReversiModel) model).getBoard();
		
		for(int i = 0; i < board.length; i++) {
			int value = board[i];
			if(value == 1) {
				cells[i].setFill(Color.RED);
			} else if(value == 2) {
				cells[i].setFill(Color.BLUE);
			} else {
				cells[i].setFill(Color.WHITE);
			}
		}
	}
}
