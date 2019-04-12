package reversi;

import application.Gui;
import gamehandler.GameView;
import gamehandler.Move;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ReversiView extends GameView {
	
	private ImageView[] cells;
	private Label stats;
	
	private Image empty;
	private Image black;
	private Image white;
	
	public ReversiView(Gui gui) {
		super(gui);
		empty = new Image("assets/reversi_empty.png");
		black = new Image("assets/reversi_black.png");
		white = new Image("assets/reversi_white.png");
		boardView = new GridPane();
		boardView.setPrefSize(400, 400);
		cells = new ImageView[64];
		for(int i = 0; i < cells.length; i++) {
			final int x = i % 8;
			final int y = i / 8;
			cells[i] = new ImageView(empty);
			cells[i].setOnMouseClicked(e -> {
				handleClick(new Move(x, y));
			});
			((GridPane) boardView).add(cells[i], x, y);
		}
		stats = new Label("Stats");
		statsPane = new Pane();
		statsPane.getChildren().add(stats);
	}
	
	@Override
	public void update() {
		Platform.runLater(() -> {
			updateBoard();
		});
		int[] values = ((ReversiModel) model).getStats();
		String reason = ((ReversiModel) model).getEndReason();
		gui.updateStats(values[0], values[1], values[2], reason);
	}
	
	private void updateBoard() {
		int[] board = ((ReversiModel) model).getBoard();
		int startingPlayer = ((ReversiModel) model).getStartingPlayer();
		
		for(int i = 0; i < board.length; i++) {
			int value = board[i];
			if(startingPlayer == 1) {
				if(value == 1) {
					cells[i].setImage(black);
				} else if(value == 2) {
					cells[i].setImage(white);
				} else {
					cells[i].setImage(empty);
				}
			} else {
				if(value == 1) {
					cells[i].setImage(white);
				} else if(value == 2) {
					cells[i].setImage(black);
				} else {
					cells[i].setImage(empty);
				}
			}
		}
	}

	@Override
	public String[] getPieceNames() {
		if(((ReversiModel) model).getStartingPlayer() == 2) {
			return new String[] {"white", "black"};
		}
		return new String[] {"black", "white"};
	}
}
